package cs.rsa.ts14dist.clientresourcedecorator;

import static org.junit.Assert.*;

import javax.management.RuntimeErrorException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

import cs.rsa.ts14dist.doubles.FakeClientResource;
import cs.rsa.ts14dist.doubles.StubCookieServiceServerFastResponse;
import cs.rsa.ts14dist.doubles.StubCookieServiceServerSlowResponse;

public class TestTimeoutEnabledClientResource {

	
	//On localhost:8183
	static StubCookieServiceServerSlowResponse slowLocalServer;
	static StubCookieServiceServerFastResponse fastLocalServer;
	static TimeoutEnabledClientResource slowResource;
	static TimeoutEnabledClientResource fastResource;

	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
		
		//Setup test servers
		slowLocalServer = new StubCookieServiceServerSlowResponse();
		Thread slow = new Thread(slowLocalServer);
		slow.run();

		fastLocalServer = new StubCookieServiceServerFastResponse();
		Thread fast = new Thread(fastLocalServer);
		fast.run();


	}

	
	@Before
	public void setUp() {
		String slowResourceHost = "http://localhost:8183";
		slowResource = new TimeoutEnabledClientResource(new SimpleClientResource(slowResourceHost),500);
		String fastResourceHost = "http://localhost:8182";
		fastResource = new TimeoutEnabledClientResource(new SimpleClientResource(fastResourceHost),500);

		
	}

	/**
	 * A testserver, that wait 10 seconds before responding is called.
	 * In this testcase the TimeoutEnabledClientResource should abort the call after 500 ms.
	 * Abortion is by throwing a ResourceException
	 */
	@Test
	public void testThatTimeoutOccursafter500ms() {
		long start, end;

		start = System.currentTimeMillis();
		try{
			slowResource.get();
		}
		catch(ResourceException e){
			//do nothing
		}
		end = System.currentTimeMillis();
		long timediff = end-start;
		assertTrue("Failed to timout after 500 ms",500 <= timediff && timediff <= 600);
		
	}
	
	/**
	 * A testserver, that response immediately
	 * In this testcase the TimeoutEnabledClientResource should receive the response before timeout is reached.
	 */
	@Test
	public void testThatResponseisReceivedBeforeTimeout() throws Exception{
		Representation result = null;
		try{
			result = fastResource.get();
		}
		catch(ResourceException e){
			//do nothing
		}
		try {
			assertNotNull("Response not received - NULL",result);
			assertTrue("Response not received - wrong text","FastResponse 1".equals(result.getText()));
		} catch (Exception e) {
			throw e;
		}
		
	}

	
}
