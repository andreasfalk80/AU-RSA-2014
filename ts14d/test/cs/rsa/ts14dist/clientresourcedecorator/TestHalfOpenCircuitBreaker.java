package cs.rsa.ts14dist.clientresourcedecorator;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.clientresourcedecorator.CircuitBreakerStates;
import cs.rsa.ts14dist.clientresourcedecorator.CircuitbreakableClientResource;
import cs.rsa.ts14dist.clientresourcedecorator.ClientResourceInterface;
import cs.rsa.ts14dist.doubles.FakeClientResource;

public class TestHalfOpenCircuitBreaker {
	static Logger log = LoggerFactory.getLogger(TestHalfOpenCircuitBreaker.class);
	FakeClientResource res;
	CircuitbreakableClientResource resource;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		res = new FakeClientResource();
		resource = new CircuitbreakableClientResource((ClientResourceInterface)res, new CircuitBreakerConfiguration(1,1));
		
		//Bringing the Breaker into the Open state
		res.setResourceToFail();
		for(int i=1; i<=2;i++){
			try{
				resource.get();
			}
			catch(ResourceException e){
				//log.debug("Exception");
			}
		}
		Thread.sleep(10); //to make sure the next call get delegated to HalfOpenCirciutBreaker
	}

	/*
	 * Testing that failing calls in the HalfOpneState triggers a transition to the Open state 
	 */
	@Test  
	public void testFailingCallsAreLeadToOpenCircuitBreaker() {
		try{
			resource.get();
		}
		catch(ResourceException e){
			
		}
		finally{
			assertEquals(CircuitBreakerStates.OPEN, resource.getCircuitBreakerState());  
		}
	}
	
	/*
	 * Testing that successful calls in the HalfOpenState triggers a transition to the Closed state 
	 */
	@Test  
	public void testSuccesfulCallsAreLeadToClosedCircuitBreaker() {
		try{
			res.setResourceToSucceed();
			resource.get();
		}
		catch(ResourceException e){
			
		}
		finally{
			assertEquals(CircuitBreakerStates.CLOSED, resource.getCircuitBreakerState());  
		}
	}

}
