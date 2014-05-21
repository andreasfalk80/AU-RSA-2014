package cs.rsa.ts14dist.circuitbreakableClientResource;

import java.io.StringWriter;
import java.sql.Time;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.cookie.ReliableCookieService;

/**
 * TODO rewrite the javadoc for this class
 * The role as Context in the State pattern regarding CircuitBreaker.<br/>
 * The role as ConcreteDecorator in the Decorator pattern.<br/>
 * This class decorates any class that implements the ClientResourceInterface,
 * and more precisely its purpose is to decorate the SimpleClientResource(and in effect also the ClientResource)
 * with a CircuitBreaker, that enables cutting of the external component if problems are registered.<br/>
 * <br/>
 * Note that only the function get(), without parameters is extended with a circuitBreaker. 
 * All other calls to the resource will act as the original ClientResource.
 */

public class TimeoutEnabledClientResource extends ClientResourceDecorator{
	static org.slf4j.Logger log = LoggerFactory.getLogger(TimeoutEnabledClientResource.class);
	
	//this variable contains the result for the last sent request.
	public static volatile Representation result;
	//ID of the last request given to a callbackhandler, to make sure we don't process an old response.
	//This also means that there can only be one valid request at a time.
	public static volatile int currentRequest = 1;
	
	/**
	 * 
	 * @param originalClientResource is the ClientResource, that needs a Timeout.
	 */
	public TimeoutEnabledClientResource(ClientResourceInterface originalClientResource){
		resource = originalClientResource;
		
		//We declare a callback function to make this call async. This means we can keep track of timeouts ourselfs.
  		UniformWithID callback = new UniformWithIDImpl();
  		synchronized (TimeoutEnabledClientResource.class) {
			callback.setID(TimeoutEnabledClientResource.currentRequest);
			TimeoutEnabledClientResource.currentRequest++;
		}
  		resource.setOnResponse(callback);
  		
  	    //Make sure that the ClientResource won't do automatic retries, since that would mean x*6 seconds for x retries.
  	    resource.setRetryAttempts(0);
	}

	@Override
	public Representation get(){
		try {
			TimeoutEnabledClientResource.result = null;
			long start = System.currentTimeMillis();
			resource.get(); //async call!!
			long end = System.currentTimeMillis();
			//We wait for 4 seconds, or until we recieve a response
			while(end-start < 4000 && TimeoutEnabledClientResource.result == null){
				try{
					Thread.sleep(200);
				}
				catch(InterruptedException e ){
					//do nothing
				}
				end = System.currentTimeMillis();
			}
		} catch (ResourceException e) {
			throw e; 
		} 
		if(TimeoutEnabledClientResource.result == null){
			//Her faker vi bare en exception fra Restlet frameworket, da det er denne type exception der ville komme normalt.
			//TODO er det en god ide, eller skal den kaldende vide at der circuitbreakeren er åben??
			throw new ResourceException(1000,"Connection error","Unable to establish a connection","unknown");
		}
		
		return TimeoutEnabledClientResource.result;
	}
	
	//************************************************************************************************************
	
	/**
	 * This interface allows an ID to be saved with the Uniform. This is used in context of a callbackfunction, 
	 * where several simultaneous calls and callbackhandlers get there own ID.   
	 * @author Andreas
	 *
	 */
		public interface UniformWithID extends Uniform{
			public int getID();
			public void setID(int id);
		}

		public class UniformWithIDImpl implements UniformWithID {
			int ID;
			
			@Override
			public int getID(){
				return ID;
			}
			@Override
			public void setID(int id){
				ID = id;
			}
			@Override
			public void handle(Request arg0, Response arg1){
				//System.err.println("handling the response for ID: " +ID );
				synchronized (ReliableCookieService.class) {
					//we only save the response if the ID for the handler corresponds to the current request.
					if(TimeoutEnabledClientResource.currentRequest == ID){
						TimeoutEnabledClientResource.result = arg1.getEntity();
					}
					//else the response came to late
				}
			}
		}
	
}
