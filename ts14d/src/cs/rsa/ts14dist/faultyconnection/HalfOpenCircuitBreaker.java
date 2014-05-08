package cs.rsa.ts14dist.faultyconnection;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class HalfOpenCircuitBreaker implements CircuitBreaker {
	
	HalfOpenCircuitBreaker() {
	}
	
	public Representation call(FaultyConnection conn) {
		Representation result =null;
		try{
			result = conn.executeGet();
			//hvis det gik godt
			conn.setBreaker(new ClosedCircuitBreaker());
		}
		catch(ResourceException e){
			//log incident	
		}
		return result; 
	}
}
