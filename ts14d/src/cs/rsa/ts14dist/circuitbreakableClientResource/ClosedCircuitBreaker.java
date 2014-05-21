package cs.rsa.ts14dist.circuitbreakableClientResource;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define a ConcreteState for the CircuitBreaker
 *
 * Closed is the initial state, in which the calls get delegated to the ClientResource.<br/>
 * If the number of failures in subsequent calls exceed the limit, a transition to the openState is performed. 
 */

public class ClosedCircuitBreaker implements CircuitBreaker {

	static Logger log = LoggerFactory.getLogger(ClosedCircuitBreaker.class);
	private int threshold = 2;
	private int faultCount = 0;

	public ClosedCircuitBreaker() {
		log.debug("CircuitBreaker entering Closed state");
	}

	@Override
	public Representation call(CircuitbreakableClientResource conn) {
		Representation result = null;
		// do all the stuff to call the connetion.
		try {
			result = conn.executeGet();
			// Følgende udføres kun hvis der ikke er exception
			log.debug("successfull get");
			faultCount = 0;
		} catch (Exception e) {
			faultCount++;
			log.debug("unsuccecssfull get, faultCount: "
					+ faultCount);
			if (faultCount >= threshold) {
				log.debug("number of faults passed threshold. Set to Open state");
				// update state
				conn.setBreaker(new OpenCircuitBreaker());
			}
			
			// re throw exception
			throw e;
		}
		return result;

	}

	@Override
	public CircuitBreakerStates getBreakerState() {
		return CircuitBreakerStates.CLOSED;
	}
	
	
	

}
