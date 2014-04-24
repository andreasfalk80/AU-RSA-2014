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
 
package cs.rsa.ts14dist.appserver; 
 
import org.json.simple.JSONObject; 

/** The ServerRequestHandler is responsible for handling
 * requests from the client and computing a reply. This
 * pattern is described in Patterns of Software Architecture
 * Volume 4.
 * 
 * The requests and replies are encoded as JSON and
 * a simple marshalling and unmarshalling is given in
 * the link below.
 * 
 * @see cs.rsa.ts14dist.common.CommandLanguage
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
 
public interface ServerRequestHandler { 
 
  /** Handle the request that is encoded in the request
   * object, and compute an answer as a reply object.  
   * @param request the request encoded as JSON
   * @return reply encoded as JSON
   */ 
  JSONObject handleRequest(JSONObject request); 
 
} 
