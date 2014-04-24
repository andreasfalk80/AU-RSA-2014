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
 
package cs.rsa.ts14dist.common; 
 
import org.json.simple.JSONObject; 
 
/** Small collection of functions (static methods) 
 * that defines the command language of TS14-D's 
 * distributed communication, i.e. the JSON objects
 * that define requests and replies.
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 * 
 */ 
public class CommandLanguage { 
   
  @SuppressWarnings("unchecked") 
  /** Create a request object */
  public static JSONObject createRequestObject(String user,  
      String commandKey, 
      String parameter) { 
    JSONObject requestJson = new JSONObject(); 
    requestJson.put(Constants.USER_KEY, user); 
    requestJson.put(Constants.COMMAND_KEY, commandKey); 
    requestJson.put(Constants.PARAMETER_KEY, parameter ); 
 
    return requestJson; 
  } 
   
  @SuppressWarnings("unchecked") 
  /** Create a valid reply object */
  public static JSONObject createValidReplyWithReturnValue(Object returnValkue) { 
    JSONObject reply = new JSONObject(); 
    reply.put("error", false); 
    reply.put("errorMsg", "OK"); 
    reply.put(Constants.RETURNVALUE_KEY, returnValkue); 
 
    return reply; 
  } 
 
  @SuppressWarnings("unchecked") 
  /** Create an invalid reply object */
  public static JSONObject createInvalidReplyWithExplantion(String errorMsg) { 
    JSONObject reply = new JSONObject(); 
    reply.put("error", true); 
    reply.put("errorMsg", errorMsg ); 
    return reply; 
  } 
} 
