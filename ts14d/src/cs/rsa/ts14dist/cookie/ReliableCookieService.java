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
 
package cs.rsa.ts14dist.cookie;

import java.io.IOException;
import java.io.StringWriter;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.clientresourcedecorator.CircuitBreakerConfiguration;
import cs.rsa.ts14dist.clientresourcedecorator.CircuitbreakableClientResource;
import cs.rsa.ts14dist.clientresourcedecorator.ClientResourceInterface;
import cs.rsa.ts14dist.clientresourcedecorator.SimpleClientResource;
import cs.rsa.ts14dist.clientresourcedecorator.TimeoutEnabledClientResource;



/** Reliable implementation of the CookieService that
 * makes a REST call to http://(hostname):(port)/rsa/cookie
 * and convert the returned representation into the
 * fortune cookie string.
 * 
 * It uses the newly developed CircuitBreakableClientResource to assure stability.
 * It uses an asynchronous request, to implement a timeout feature as well.
 * 
 * An instance is deployed on a Digital Ocean instance,
 * see the Constants class for IP and port.
 * 
 * @see cs.rsa.ts14dist.common.Constants
 * 
 */
public class ReliableCookieService implements CookieService {
	Logger log = LoggerFactory.getLogger(ReliableCookieService.class);
	
	private ClientResourceInterface resource;

  /**
   * Creates a ReliableCookieService, with a default timeout of 4000 milliseconds
   * @param hostname
   * @param port
   */
	  public ReliableCookieService(String hostname, String port) {
		  this(hostname,port,4000);
	  }
	
	/**
	 * Creates a ReliableCookieService, with the designated timeout in milliseconds
	 * @param hostname
	 * @param port
	 * @param timeout
	 */
	  public ReliableCookieService(String hostname, String port, int timeout) {
    // Create the client resource  
    String resourceHost = "http://"+hostname+":"+port+"/rsa/cookie";
    
    
    /*
     * Since there is no official interface to code up against for ClientResource, the SimpleClientResource is used instead.
     * Part of the Decorator pattern solution
     */
    resource = new SimpleClientResource(resourceHost);
    /*The ordering of the decorators are very important,
     * since the circuitbreaker needs to call through the timeoutenabled Clientresource, 
     * to let the timeouts be considered as failed calls. 
     */
     //Decorate with timeout
    resource = new TimeoutEnabledClientResource(resource,timeout);
    //Decorate with Circuitbreaker, with faultThreshold 2 and resettimelimit 10 seconds
    resource = new CircuitbreakableClientResource(resource,new CircuitBreakerConfiguration(2, 10000));
    
  }
    
  @Override
	public String getNextCookie() throws IOException {
		String result = "Today's fortune cookie is unfortunately unavailable.";
		try {
			Representation repr = resource.get(); 
			if(repr != null){
				StringWriter writer = new StringWriter();
				repr.write(writer);
				result = writer.toString();
			}
		} catch (ResourceException e) {
			//Do nothing 
		} 

		return result;
	}
}
