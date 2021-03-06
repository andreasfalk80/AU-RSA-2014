package cs.rsa.ts14dist.clientresourcedecorator;

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
	private CircuitBreakerConfiguration conf;
	private int waitTime = 10000; //in millisecond
	private long creationTime; 
	
	OpenCircuitBreaker(CircuitBreakerConfiguration conf) {
		log.debug("CircuitBreaker entering Open state");
		this.conf = conf;
		waitTime = this.conf.getWaitUntilReset();
		creationTime = System.currentTimeMillis();
	}
	
	public Representation call(CircuitbreakableClientResource conn) {
		Representation result = null;
		//Hvis vi har ventet lang tid nok
		long now = System.currentTimeMillis();
		long diff = now-creationTime;
		if(diff > waitTime){
			log.debug("Attempted call, after timeout periode completed. Attempting reset");
			
			//Vi forsøger om forbindelsen virker igen
			result = attemptReset(conn);
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
	
	private Representation attemptReset(CircuitbreakableClientResource conn){
		CircuitBreaker tester = new HalfOpenCircuitBreaker(conf);
		conn.setBreaker(tester);
		return tester.call(conn);
	}

	
	@Override
	public CircuitBreakerStates getBreakerState() {
		return CircuitBreakerStates.OPEN;
	}

}
