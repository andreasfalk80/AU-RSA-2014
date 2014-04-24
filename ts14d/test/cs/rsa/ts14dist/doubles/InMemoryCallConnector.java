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
 
package cs.rsa.ts14dist.doubles; 
 
import java.io.IOException; 
 
import org.json.simple.*; 
 
import cs.rsa.ts14dist.appserver.ServerRequestHandler; 
import cs.rsa.ts14dist.client.Connector; 
 
/** This is essentially a fake object as it is an implementation
 * of the client-to-server connector that is just a local method
 * call. Thus it works correctly (except for error conditions).
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class InMemoryCallConnector implements Connector { 
   
  private ServerRequestHandler serverRequestHandler; 
 
  public InMemoryCallConnector(ServerRequestHandler serverRequestHandler) { 
    this.serverRequestHandler = serverRequestHandler; 
  } 
 
  @Override 
  public JSONObject sendRequestAndBlockUntilReply(JSONObject request) throws IOException { 
 
    // Very simple - the request reply protocol boils down to a simple method call... 
    JSONObject reply = serverRequestHandler.handleRequest( request ); 
     
    return reply; 
  } 
 
} 
