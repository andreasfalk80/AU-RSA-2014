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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cs.rsa.ts14dist.common.Constants;
import cs.rsa.ts14dist.cookie.CookieService;
import cs.rsa.ts14dist.cookie.ReliableCookieService;
import cs.rsa.ts14dist.doubles.StubCookieServiceTimeout;

/** MANUAL test of the Reliable CookieService
 * - you have to visually inspect that
 * something is coming back from the Cookie REST realService.
 * 
 * And - the rest realService must be running :)
 *  
 */

public class TeztReliableCookieService {

	private CookieService realService;
	private CookieService dummyTimeOutService;

	@Before
	public void setup() {
		BasicConfigurator.configure();
	}
	
	@Test @Ignore
	public void shouldGetCookieFromService() throws IOException {
		//this test needs an actual connection
		realService = new ReliableCookieService(Constants.DIGITALOCEAN_INSTANCE_IP, Constants.COOKIE_REST_PORT);
		
		String cookie = realService.getNextCookie();

		assertNotNull(cookie);

		System.out.println(cookie);
	}

	@Test @Ignore
	public void shouldTimeoutAfter4Seconds() throws IOException {
		//Start local running dummyserver
		Thread localServer = new Thread(new StubCookieServiceTimeout()); 
		localServer.run();
		dummyTimeOutService = new ReliableCookieService("localhost", "8182"); //Called with a local server running with 10 seconds delay for a response
		
		long start = System.currentTimeMillis();  
		dummyTimeOutService.getNextCookie();
		long end = System.currentTimeMillis();
		assertTrue(end-start>4000 && end-start<4200);
	}


	//because of the Circuitbreaker the third failing call should be alot faster
	@Test
	public void shouldNotCallServiceTheThirdTime() throws IOException {
		//Start local running dummyserver
		Thread localServer = new Thread(new StubCookieServiceTimeout()); 
		localServer.run();
		dummyTimeOutService = new ReliableCookieService("localhost", "8182"); //Called with a local server running with 10 seconds delay for a response

		String cookie;

		long start = System.currentTimeMillis();  
		cookie = dummyTimeOutService.getNextCookie();
		long end = System.currentTimeMillis();
		System.out.println("cookie 1, after "+(end-start)+": " +cookie);
		
		start = System.currentTimeMillis();  
		cookie = dummyTimeOutService.getNextCookie();
		end = System.currentTimeMillis();
		System.out.println("cookie 2, after "+(end-start)+": " +cookie);
		
		start = System.currentTimeMillis();  
		cookie = dummyTimeOutService.getNextCookie();
		end = System.currentTimeMillis();
		System.out.println("cookie 3, after "+(end-start)+": " +cookie);
		assertTrue("Circuitbreaker properly didn't switch to Open",end-start<10); //more than 10 ms is to long for a shortcircuit call
	}


}
