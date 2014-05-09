package cs.rsa.ts14dist.circuitbreakableconnection;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.slf4j.LoggerFactory;

public class CircuitbreakableConnection {
	
	static org.slf4j.Logger log = LoggerFactory.getLogger(CircuitbreakableConnection.class);
	private CircuitBreaker breaker = new ClosedCircuitBreaker();
	private PartialClientResource resource;
	
	public CircuitbreakableConnection(PartialClientResource originalClientResource){
		resource = originalClientResource;
	}

	void setBreaker(CircuitBreaker breaker){
		this.breaker = breaker;
	}

	public Representation get(){
			return breaker.call(this);
	}
	
	public Representation executeGet(){
		//call the real ClientResource
		return resource.get();
	}	
}
