/*
 * Copyright 2014 Henrik Baerbak Christensen, Aarhus University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cs.rsa.ts14dist.manual;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import cs.rsa.ts14dist.cookie.CookieService;
import cs.rsa.ts14dist.cookie.ReliableCookieService;
import cs.rsa.ts14dist.doubles.StubCookieServiceServerFastResponse;
import cs.rsa.ts14dist.doubles.StubCookieServiceServerSlowResponse;

/** MANUAL test of the Reliable CookieService
 * - you have to visually inspect that
 * something is coming back from the Cookie REST realService.
 * 
 * And - the rest realService must be running :)
 *  
 */

public class TeztReliableCookieService {

	static private CookieService dummyServiceFastResponse;
	static private CookieService dummyServiceSlowresponse;

	//On localhost:8182 
	static StubCookieServiceServerFastResponse fastLocalServer; 
	//On localhost:8183
	static StubCookieServiceServerSlowResponse slowLocalServer;


	@BeforeClass
	public static void init() {
		BasicConfigurator.configure();
		
		//Setup test servers
		fastLocalServer = new StubCookieServiceServerFastResponse(); 
		slowLocalServer = new StubCookieServiceServerSlowResponse();
		Thread fast = new Thread(fastLocalServer);
		fast.run();
		Thread slow = new Thread(slowLocalServer);
		slow.run();

	}


	@Before
	public void setup() {
		//create ReliableCookieService based on the fast server
		dummyServiceFastResponse = new ReliableCookieService("localhost", "8182");

		//create ReliableCookieService based on the slow server
		dummyServiceSlowresponse = new ReliableCookieService("localhost", "8183");

	}

	@Test
	public void shouldGetCookieFromService() throws IOException {

		String cookie = dummyServiceFastResponse.getNextCookie();
		System.out.println(cookie);

		//test that we got a different response
		assertTrue(!cookie.equals("Today's fortune cookie is unfortunately unavailable."));
	}

	@Test
	public void shouldTimeoutAfter4Seconds() throws IOException {

		long start = System.currentTimeMillis();  
		dummyServiceSlowresponse.getNextCookie();
		long end = System.currentTimeMillis();
		assertTrue(end-start>4000 && end-start<4200);	
	}


	//because of the Circuitbreaker the third failing call should be alot faster
	@Test
	public void shouldNotCallServiceTheThirdTime() throws IOException {
		String cookie;

		long start = System.currentTimeMillis();  
		cookie = dummyServiceSlowresponse.getNextCookie();
		long end = System.currentTimeMillis();
		System.out.println("cookie 1, after "+(end-start)+": " +cookie);

		start = System.currentTimeMillis();  
		cookie = dummyServiceSlowresponse.getNextCookie();
		end = System.currentTimeMillis();
		System.out.println("cookie 2, after "+(end-start)+": " +cookie);

		start = System.currentTimeMillis();  
		cookie = dummyServiceSlowresponse.getNextCookie();
		end = System.currentTimeMillis();
		System.out.println("cookie 3, after "+(end-start)+": " +cookie);
		assertTrue("Circuitbreaker properly didn't switch to Open",end-start<10); //more than 10 ms is to long for a shortcircuit call

	}


}
