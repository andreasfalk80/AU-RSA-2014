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

import org.restlet.Context;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.circuitbreakableClientResource.CircuitbreakableClientResource;
import cs.rsa.ts14dist.circuitbreakableClientResource.ClientResourceInterface;
import cs.rsa.ts14dist.circuitbreakableClientResource.SimpleClientResource;



/** Reliable implementation of the CookieService that
 * makes a REST call to http://(hostname):(port)/rsa/cookie
 * and convert the returned representation into the
 * fortune cookie string.
 * 
 * It uses the newly developed CircuitBreakableClientResource to assure stability.
 * 
 * An instance is deployed on a Digital Ocean instance,
 * see the Constants class for IP and port.
 * 
 * @see cs.rsa.ts14dist.common.Constants
 * 
 */
public class ReliableCookieService implements CookieService {
	Logger log = LoggerFactory.getLogger(ReliableCookieService.class);
  private CircuitbreakableClientResource conn;

  
  public ReliableCookieService(String hostname, String port) {
    // Create the client resource  
    String resourceHost = "http://"+hostname+":"+port+"/rsa/cookie";
    /*
     *  By setting SocketTimeout to 4 seconds, we make sure that the total call, including access to MONGO and transmitting by MQ, properly won't take more than 6 seconds.
     */
    Context context = new Context();
    context.getParameters().add("socketTimeout", "4000");
    
    /*
     * Since there is no official interface to code up against for ClientResource, the SimpleClientResource is used instead.
     * Part of the Decorator pattern solution
     */
    ClientResourceInterface resource = new SimpleClientResource(context,resourceHost);
    //Make sure that the ClientResource won't do automatic retries, since that would mean x*6 seconds for x retries.
    resource.setRetryAttempts(0);
    
    conn = new CircuitbreakableClientResource(resource);
  }

  @Override
  public String getNextCookie() throws IOException {
    String result = "Today's fortune cookie is unfortunately unavailable."; 
    // Write the response entity on the console
    try {
      
      Representation repr = conn.get();
   	
      StringWriter writer = new StringWriter();
      repr.write(writer);
      result = writer.toString();
      repr.release();
      
    } catch (Exception e) {
      /* No exception are sent any further, since they have all been dealt with in the Circuitbreaker.
       * Instead the default result text is returned.
       */
    } 

    return result;
  }

}
