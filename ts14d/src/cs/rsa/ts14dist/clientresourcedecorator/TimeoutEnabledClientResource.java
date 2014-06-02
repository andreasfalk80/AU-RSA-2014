package cs.rsa.ts14dist.clientresourcedecorator;

import java.io.IOException;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.cookie.ReliableCookieService;

/**
 * The role as ConcreteDecorator in the Decorator pattern.<br/>
 * This class decorates any class that implements the ClientResourceInterface,
 * and more precisely its purpose is to decorate the SimpleClientResource(and in effect also the ClientResource)
 * with a time out functionality, that enables a call to be aborted after a designated time.<br/>
 * <br/>
 * Note that only the function get(), without parameters is extended with a time out. 
 * All other calls to the resource will act as the original ClientResource.
 */

public class TimeoutEnabledClientResource extends ClientResourceDecorator{
	static org.slf4j.Logger log = LoggerFactory.getLogger(TimeoutEnabledClientResource.class);
	//this is the name of the parameter that will get added to the request, so that we can identify the response
	private static final String paramName = TimeoutEnabledClientResource.class.getSimpleName()+"RequestID";
	//timeout value. default 4000 milliseconds
	private final int timeoutLimit;
	
	//this variable contains the result for the last sent request.
	public static volatile Representation result;
	//ID of the last request sent, to make sure we don't process an old response.
	//This also means that there can only be one valid request at a time.
	public volatile int currentRequest = 1;
	
	
	/**
	 * 
	 * @param originalClientResource is the ClientResource, that needs a Timeout.
	 * @param timeout is milliseconds to wait for a response
	 */
	public TimeoutEnabledClientResource(ClientResourceInterface originalClientResource,int timeout){
		resource = originalClientResource;
		timeoutLimit = timeout;
		
		//We declare a callback function to make this call async. This means we can keep track of timeouts ourselfs.
  		resource.setOnResponse(new UniformImpl());
  		
  	    //Make sure that the ClientResource won't do automatic retries, since that would mean x*timeoutlimit seconds for x retries.
  	    resource.setRetryAttempts(0);
	}

	@Override
	public Representation get(){
		try {
			TimeoutEnabledClientResource.result = null;
	  		synchronized (TimeoutEnabledClientResource.class) {
	  			//we set the currentrequestnumber, so we can identify the response belonging to the request
				resource.setAttribute(paramName, currentRequest);
				log.debug("Sending a request with id: " + currentRequest);
	  		}
			long start = System.currentTimeMillis();
			resource.get(); //async call!!
			long end = System.currentTimeMillis();
			//We wait for x milliseconds, or until we receive a response
			int sleeptime = timeoutLimit/20; //this seems to be a good sleeping period
			while(end-start < timeoutLimit && TimeoutEnabledClientResource.result == null){
				try{
					Thread.sleep(sleeptime); 
				}
				catch(InterruptedException e ){
					//do nothing
				}
				end = System.currentTimeMillis();
			}
		} catch (ResourceException e) {
			throw e; 
		} 
		//We increment the currentRequest, to invalidate any old response recieved
		synchronized (TimeoutEnabledClientResource.class) {
			currentRequest++;
//			log.debug("We updated currentRequest to : " +currentRequest);
		}
		if(TimeoutEnabledClientResource.result == null){
			log.debug("Time out occured");
			//Her faker vi bare en exception fra Restlet frameworket, da det er denne type exception der ville komme normalt.
			//TODO er det en god ide, eller skal den kaldende vide at der circuitbreakeren er åben??
			throw new ResourceException(1000,"Connection error","Unable to establish a connection","unknown");
		}
		log.debug("Result is recieved");
		return TimeoutEnabledClientResource.result;
	}
	

	public Integer getCurrentRequest(){
		return Integer.valueOf(currentRequest);
	}
	
	
	//************************************************************************************************************
	
			public class UniformImpl implements Uniform {

				@Override
			public void handle(Request arg0, Response arg1){
				Integer requestID = (Integer)arg0.getAttributes().get(paramName);
				log.debug("handling the response for ID: " +requestID);
				synchronized (ReliableCookieService.class) {
					//we only save the response if the ID for the handler corresponds to the current request.
					if(getCurrentRequest().equals(requestID) ){
						TimeoutEnabledClientResource.result = arg1.getEntity();
						//We cant fetch and log the resposne, since that means it is not available for the main application
						//log.debug("Correct ID on handler - response is saved " + requestID + " response recieved " + arg1.getEntityAsText());
					}
					else{
						//else the response came to late
						log.debug("Wrong ID on handler response is discarded. Found value " + requestID+ " Expected value was " + getCurrentRequest()  + " response recieved " + arg1.getEntityAsText());
					}
				}
			}
		
		}
	
}
