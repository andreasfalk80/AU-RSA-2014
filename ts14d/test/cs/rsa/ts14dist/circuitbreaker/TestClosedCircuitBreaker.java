package cs.rsa.ts14dist.circuitbreaker;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.circuitbreakableClientResource.CircuitBreakerStates;
import cs.rsa.ts14dist.circuitbreakableClientResource.CircuitbreakableClientResource;
import cs.rsa.ts14dist.circuitbreakableClientResource.ClientResourceInterface;
import cs.rsa.ts14dist.doubles.FakeClientResource;

public class TestClosedCircuitBreaker {
	static Logger log = LoggerFactory.getLogger(TestClosedCircuitBreaker.class);
	FakeClientResource res;
	CircuitbreakableClientResource resource;

	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		res = new FakeClientResource();
		resource = new CircuitbreakableClientResource((ClientResourceInterface)res);
	}

	/*
	 * Testing that transition from Closed to Open works, and that it happens after 2 failed calls
	 */
	@Test  
	public void testClosedToOpenTransitionAfter2FailedCalls() {
		res.setResourceToFail();
		for(int i=1; i<=2;i++){
			try{
				//	log.debug("TestCase sent get # " +i);
				resource.get();
			}
			catch(ResourceException e){
				//log.debug("Exception");
			}
			finally{
				switch (i) {
					case 1 :
						assertEquals(CircuitBreakerStates.CLOSED, resource.getCircuitBreakerState());
						break;
					case 2 :
						assertEquals(CircuitBreakerStates.OPEN, resource.getCircuitBreakerState());
						break;
					default :
						break;
				}
			}
		}
	}

	/*
	 * Testing that number of failures get reset by a successfull call
	 */
	@Test  
	public void testFailcounterResetsAfterSucces() {
		
		
		//first call fails, Breaker is still closed ***********************************
		res.setResourceToFail();
		try{
			resource.get();
		}
		catch(ResourceException e){
			//log.debug("Exception");
		}
		finally{
			assertEquals(CircuitBreakerStates.CLOSED, resource.getCircuitBreakerState());
		}

		
		//second call succeeds, Breaker is still closed *********************************** 
		res.setResourceToSucceed();
		try{
			resource.get();
		}
		catch(ResourceException e){
			//log.debug("Exception");
		}
		finally{
			assertEquals(CircuitBreakerStates.CLOSED, resource.getCircuitBreakerState());
		}	

		
		//third call fails, Breaker is still closed ***********************************
		res.setResourceToFail();
		try{
			resource.get();
		}
		catch(ResourceException e){
			//log.debug("Exception");
		}
		finally{
			assertEquals(CircuitBreakerStates.CLOSED, resource.getCircuitBreakerState());
		}	
		
		
		//fourth call fails,, Breaker is now open ***********************************
		res.setResourceToFail();
		try{
			resource.get();
		}
		catch(ResourceException e){
			//log.debug("Exception");
		}
		finally{
			assertEquals(CircuitBreakerStates.OPEN, resource.getCircuitBreakerState());
		}	
	}
	
	/*
	 * Testing that all calls actually gets delegated to the ClientResource
	 * This is done by the FakeResourceClient's extra test method getCount()
	 */
	@Test  
	public void testThatCallsAreDelegatedToClientResource() {
		res.setResourceToSucceed();
		try{
			resource.get();
		}
		catch(ResourceException e){
			//log.debug("Exception");
		}
		finally{
			assertEquals(1, res.getCount());
		}	
	}

}
