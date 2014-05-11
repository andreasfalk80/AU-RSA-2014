package cs.rsa.ts14dist.circuitbreakableconnection;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCircuitBreaker implements CircuitBreaker {
	static Logger log = LoggerFactory.getLogger(OpenCircuitBreaker.class);
	private int waitTime = 10000; //in millisecond
	private long creationTime; 
	
	OpenCircuitBreaker() {
		log.info("CircuitBreaker entering Open state");
		creationTime = System.currentTimeMillis();
	}
	
	public Representation call(CircuitbreakableConnection conn) {
		Representation result = null;
		//Hvis vi har ventet lang tid nok
		long now = System.currentTimeMillis();
		long diff = now-creationTime;
		if(diff > waitTime){
			log.info("Attempted call, after timeout periode completed. Attempting reset");
			
			//Vi fors�ger om forbindelsen virker igen
			CircuitBreaker tester = new HalfOpenCircuitBreaker();
			conn.setBreaker(tester);
			result = tester.call(conn);
		}
		else{
			log.info("Attempted call, before timeout periode completed. Call fail");
			log.debug("now: " + now + " creationTime: "+creationTime + " waitTime: " + waitTime +" difference: " + diff);
			//Her faker vi bare en exception fra Restlet frameworket, da det er sådan en exception der ville komme normalt.
			//TODO er det en god ide, eller skal den kaldende vide at der circuitbreakeren er åbnet??
			throw new ResourceException(1000,"Connection error","Unable to establish a connection","unknown");
		}
		return result;
	}

}
