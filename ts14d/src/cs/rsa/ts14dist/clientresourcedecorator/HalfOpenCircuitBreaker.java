package cs.rsa.ts14dist.clientresourcedecorator;

import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Define a ConcreteState for the CircuitBreaker
 *
 * HalfOpen is the state, in which an attempt to reclose the circuit happens. 
 * A single call to ClientResource is performed to test the status of the remote compontent.
 * If the call is succesfull, a transition to the ClosedState is performed,
 * otherwise a transition returns to OpenState (where a new timeout period starts).  
 */

public class HalfOpenCircuitBreaker implements CircuitBreaker {
	static Logger log = LoggerFactory.getLogger(HalfOpenCircuitBreaker.class);
	private CircuitBreakerConfiguration conf;

	HalfOpenCircuitBreaker(CircuitBreakerConfiguration conf) {
		log.debug("CircuitBreaker entering HalfOpen state");
		this.conf = conf;
	}

	public Representation call(CircuitbreakableClientResource conn) {
		Representation result = null;
		try {
			result = conn.executeGet();
			// hvis det gik godt
			resetBreaker(conn);
		} catch (Exception e) {
			tripBreaker(conn);
			throw e;
		}
		return result;
	}
	
	private void tripBreaker(CircuitbreakableClientResource conn){
		log.debug("unsuccessfull get. Return to Open state");
		conn.setBreaker(new OpenCircuitBreaker(conf));
	}

	private void resetBreaker(CircuitbreakableClientResource conn){
		log.debug("successfull get. Resetting to Closed state");
		conn.setBreaker(new ClosedCircuitBreaker(conf));
	}

	
	@Override
	public CircuitBreakerStates getBreakerState() {
		return CircuitBreakerStates.HALFOPEN;
	}
}
