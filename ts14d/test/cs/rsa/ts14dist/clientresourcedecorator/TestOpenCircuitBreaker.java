package cs.rsa.ts14dist.clientresourcedecorator;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.clientresourcedecorator.CircuitbreakableClientResource;
import cs.rsa.ts14dist.clientresourcedecorator.ClientResourceInterface;
import cs.rsa.ts14dist.doubles.FakeClientResource;

public class TestOpenCircuitBreaker {
	static Logger log = LoggerFactory.getLogger(TestOpenCircuitBreaker.class);
	FakeClientResource res;
	CircuitbreakableClientResource resource;

	
	@BeforeClass
	public static void beforeClass(){
		BasicConfigurator.configure();
		
	}
	
	@Before
	public void setUp() throws Exception {
		res = new FakeClientResource();
		resource = new CircuitbreakableClientResource((ClientResourceInterface)res,new CircuitBreakerConfiguration(2,500));
		
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
	}

	/*
	 * Testing that calls received before the timeout/Timelimit is passed will be rejected, without calling the ClientResource
	 */
	@Test  
	public void testCallsAreRejectedBeforeTimeLimit() {
		try{
			resource.get();
		}
		catch(ResourceException e){
			
		}
		finally{
			assertEquals(2, res.getCount()); //2 because of the initial 2 calls to get into the Open state 
		}
	}
	
	
	/*
	 * Testing that calls received after the timeout/Timelimit is passed will get delegates to the ClientResource
	 * Note, this test take 10 second, because of the wait for the circuitbreaker. 
	 * (This could be faster if the timeout was coded as a parameter) 
	 */
	@Test  
	public void testCallsAreTriedAfterTimeLimit() throws InterruptedException{
		try{
			Thread.sleep(550);
			
			resource.get();
		}
		catch(ResourceException e){
			
		}
		catch (InterruptedException e) {
			//Not good
			throw e;
		}
		finally{
			assertEquals(3, res.getCount()); //3 because of the initial 2 calls to get into the Open state, and the trial call
		}
	}

}
