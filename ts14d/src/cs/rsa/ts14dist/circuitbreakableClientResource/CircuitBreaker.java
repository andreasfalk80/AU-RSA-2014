package cs.rsa.ts14dist.circuitbreakableClientResource;

import org.restlet.representation.Representation;

/**
 * Define the State role of the State pattern, which controls how to handle calls to the ClientResource.<br/>
 * This is an implementation of the CircuitBreaker pattern suggested on page 94 in 'Release It!' by Michael T. Nygard<br/>
 * <br/>
 * The number of call failures to trigger transition to the open state is set to two.<br/>
 * The timelimit before the open state attempts a reset is 10000 ms.<br/>
 * <br/>
 * An instance can perform the change to a new State, by using the method setBreaker(CircuitBreaker breaker)
 * defined for the State context: CircuitbreakableClientResource<br/>
 */

public interface CircuitBreaker {

	
	
	/**
	 * 
	 * @param conn
	 * @return the instance of Representation, that got returned by conn.  
	 */
	public Representation call(CircuitbreakableClientResource conn); 
}
