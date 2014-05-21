package cs.rsa.ts14dist.circuitbreakableClientResource;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Define a ConcreteState for the CircuitBreaker
 *
 * Open is the state, in which the calls get denied right away. The ClientResource is not even called<br/>
 * If the time passed since the open state got entered, exceeds the timelimit, a transition to the HalfOpenState is performed,
 * and an attempt is made via the HalfOpenState.  
 */


public class OpenCircuitBreaker implements CircuitBreaker {
	static Logger log = LoggerFactory.getLogger(OpenCircuitBreaker.class);
	private int waitTime = 10000; //in millisecond
	private long creationTime; 
	
	OpenCircuitBreaker() {
		log.debug("CircuitBreaker entering Open state");
		creationTime = System.currentTimeMillis();
	}
	
	public Representation call(CircuitbreakableClientResource conn) {
		Representation result = null;
		//Hvis vi har ventet lang tid nok
		long now = System.currentTimeMillis();
		long diff = now-creationTime;
		if(diff > waitTime){
			log.debug("Attempted call, after timeout periode completed. Attempting reset");
			
			//Vi fors�ger om forbindelsen virker igen
			CircuitBreaker tester = new HalfOpenCircuitBreaker();
			conn.setBreaker(tester);
			result = tester.call(conn);
		}
		else{
			log.debug("Attempted call, before timeout periode completed. ClientResource not called");
			log.trace("now: " + now + " creationTime: "+creationTime + " waitTime: " + waitTime +" difference: " + diff);
			//Her faker vi bare en exception fra Restlet frameworket, da det er denne type exception der ville komme normalt.
			//TODO er det en god ide, eller skal den kaldende vide at der circuitbreakeren er åben??
			throw new ResourceException(1000,"Connection error","Unable to establish a connection","unknown");
		}
		return result;
	}
	
	@Override
	public CircuitBreakerStates getBreakerState() {
		return CircuitBreakerStates.OPEN;
	}

}
