package cs.rsa.ts14dist.clientresourcedecorator;

import static org.junit.Assert.*;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.resource.ResourceException;

import cs.rsa.ts14dist.doubles.StubCookieServiceServerFastResponse;
import cs.rsa.ts14dist.doubles.StubCookieServiceServerSlowResponse;

public class TestTimeoutEnabledClientResource {

	
	//On localhost:8183
	static StubCookieServiceServerSlowResponse slowLocalServer;
	static TimeoutEnabledClientResource ter;

	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
		
		//Setup test servers
		slowLocalServer = new StubCookieServiceServerSlowResponse();
		Thread slow = new Thread(slowLocalServer);
		slow.run();

	}

	
	@Before
	public void setUp() {
		String resourceHost = "http://localhost:8183";
		ter = new TimeoutEnabledClientResource(new SimpleClientResource(resourceHost),1000);
		
	}

	/**
	 * A testserver, that wait 10 seconds before responding is called.
	 * In this testcase the TimeoutEnabledClientResource should abort the call after 500 ms.
	 * Abortion is by throwing a ResourceException
	 */
	@Test(expected = ResourceException.class)
	public void testThatTimeoutOccursafter500ms() {
		long start, end;

		start = System.currentTimeMillis();
		ter.get();
		end = System.currentTimeMillis();
		long timediff = end-start;
		assertTrue("Failed to timout after 500 ms",500 <= timediff && timediff <= 600);
		
	}
}
