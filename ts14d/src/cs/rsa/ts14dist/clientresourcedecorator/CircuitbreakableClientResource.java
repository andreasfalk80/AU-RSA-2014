package cs.rsa.ts14dist.clientresourcedecorator;

import org.restlet.representation.Representation;
import org.slf4j.LoggerFactory;

/**
 * The role as Context in the State pattern regarding CircuitBreaker.<br/>
 * The role as ConcreteDecorator in the Decorator pattern.<br/>
 * This class decorates any class that implements the ClientResourceInterface,
 * and more precisely its purpose is to decorate the SimpleClientResource(and in effect also the ClientResource)
 * with a CircuitBreaker, that enables cutting of the external component if problems are registered.<br/>
 * <br/>
 * Note that only the function get(), without parameters is extended with a circuitBreaker. 
 * All other calls to the resource will act as the original ClientResource.
 */

public class CircuitbreakableClientResource extends ClientResourceDecorator{
	static org.slf4j.Logger log = LoggerFactory.getLogger(CircuitbreakableClientResource.class);
	
	private CircuitBreaker breaker;
	
	/**
	 * 
	 * @param originalClientResource is the ClientResource, that needs a CircuitBreaker.
	 */
	public CircuitbreakableClientResource(ClientResourceInterface originalClientResource, CircuitBreakerConfiguration conf){
		resource = originalClientResource;
		breaker = new ClosedCircuitBreaker(conf);
	}

	/**
	 * get delegates the control of when to attempt a call to the external system to the circuitbreaker. 
	 */
	
	@Override
	public Representation get(){
			return breaker.call(this);
	}
	
	/**
	 * Package only method, to allow the CircuitBreaker's to transition.
	 * @param breaker
	 */
	void setBreaker(CircuitBreaker breaker){
		this.breaker = breaker;
	}

	/**
	 * package only method, which acts as a handle, the Circuitbreaker's can use to execute the real call, 
	 * when it is decided that the call can be done. 
	 * @return Representation the result from the call of ClientResource.
	 */
	Representation executeGet(){
		//call the real ClientResource
		return resource.get();
	}	
	
	/**
	 * 
	 * @return the current CircuitBreaker state
	 */
	public CircuitBreakerStates getCircuitBreakerState(){
		return breaker.getBreakerState();
	}
}
