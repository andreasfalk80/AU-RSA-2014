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
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cs.rsa.ts14dist.circuitbreakableconnection.CircuitbreakableConnection;
import cs.rsa.ts14dist.circuitbreakableconnection.WrappedClientResource;



/** Standard implementation of the CookieService that
 * makes a REST call to http://(hostname):(port)/rsa/cookie
 * and convert the returned representation into the
 * fortune cookie string.
 * 
 * An instance is deployed on a Digital Ocean instance,
 * see the Constants class for IP and port.
 * 
 * @see cs.rsa.ts14dist.common.Constants
 * 
 * Todo: The logger still writes to stderr :(
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */

public class ReliableCookieService implements CookieService {
	Logger log = LoggerFactory.getLogger(ReliableCookieService.class);
  private CircuitbreakableConnection conn;

  public ReliableCookieService(String hostname, String port) {
    // Create the client resource  
    String resourceHost = "http://"+hostname+":"+port+"/rsa/cookie";
    
    Context cnt = new Context();
    cnt.getParameters().add("socketTimeout", "1000");//TODO der mangler håndtering af automatisk retry.
    
    //Since i have no official interface to code up against!!
    WrappedClientResource resource = new WrappedClientResource(cnt,resourceHost);
    conn = new CircuitbreakableConnection(resource);
  }

  @Override
  public String getNextCookie() throws IOException {
    String result = "fault"; 
    // Write the response entity on the console
    try {
      
      Representation repr = conn.get();
   	
      StringWriter writer = new StringWriter();
      repr.write(writer);
      result = writer.toString();
      
    } catch (Exception e) {
      // TODO Auto-generated catch block
    } 

    return result;
  }

}
