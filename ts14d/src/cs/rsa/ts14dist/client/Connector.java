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
 
package cs.rsa.ts14dist.client; 
 
import java.io.IOException; 
 
import org.json.simple.JSONObject; 
 
/** The role of the connector, connecting the client with
 * its server via a remote procedure call.
 * 
 * Mashalling is manual in this small system and request and reply
 * are represented by JSON objects.
 * 
 * @see cs.rsa.ts14dist.common.CommandLanguage for functions to help marshal
 * and unmarshal the JSON request and reply.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public interface Connector { 
 
  /** send a request to the server and block (synchroneous call) until
   * a reply has been received.
   * @param request JSON object representing the request
   * @return the reply as JSON object
   * @throws IOException
   */
  public JSONObject sendRequestAndBlockUntilReply(JSONObject request) throws IOException;  
  
   
} 
