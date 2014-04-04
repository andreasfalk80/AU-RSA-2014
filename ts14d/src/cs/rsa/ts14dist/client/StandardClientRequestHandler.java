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
 
import java.io.*; 
 
import org.json.simple.JSONObject; 
 
import cs.rsa.ts14.framework.LineType; 
import cs.rsa.ts14dist.common.*; 
 
/** Standard framework implementation of the client request handler role.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class StandardClientRequestHandler implements ClientRequestHandler { 
 
  private Connector connector; 
 
  public StandardClientRequestHandler(Connector connector) { 
    this.connector = connector; 
  } 
 
  @Override 
  public LineType addLine(String user, String ts14Line) throws IOException { 
    // Build the json request object 
    JSONObject requestJson =  
        CommandLanguage.createRequestObject(user, Constants.ADDLINE_REQUEST, ts14Line); 
     
    // send the request over the connector and retrieve the reply object 
    JSONObject replyJson = requestAndAwaitReply(requestJson); 
     
    // no error, so extract the reply from the reply object 
    // and cast it to its proper type - bit tedious as the 
    // values is SOMETIMES long and SOMETIMES int depending 
    // on the distributed or non-distributed case. 
    String asString = replyJson.get(Constants.RETURNVALUE_KEY).toString(); 
    int boxedIndex = Integer.parseInt(asString); 
    LineType lineType = LineType.values()[boxedIndex]; 
 
    return lineType; 
  } 
 
  @Override 
  public String getContents(String user) throws IOException { 
    // Build the json request object 
    JSONObject requestJson =  
        CommandLanguage.createRequestObject(user, Constants.GETCONTENTS_REQUEST, "notused"); 
 
    // send the request over the connector and retrieve the reply object 
    JSONObject replyJson = requestAndAwaitReply(requestJson); 
 
    // Extract the return value 
    String report = (String) replyJson.get(Constants.RETURNVALUE_KEY); 
    return report; 
  } 
 
 
  @Override 
  public String getReport(String user, String overviewType) throws IOException { 
    // Build the json request object 
    JSONObject requestJson =  
        CommandLanguage.createRequestObject(user, Constants.GETREPORT_REQUEST, overviewType); 
 
    // send the request over the connector and retrieve the reply object 
    JSONObject replyJson = requestAndAwaitReply(requestJson); 
 
    // Extract the return value 
    String report = (String) replyJson.get(Constants.RETURNVALUE_KEY); 
    return report; 
  } 
   
  // === Helper methods  
 
  private JSONObject requestAndAwaitReply(JSONObject requestJson) 
      throws IOException { 
    JSONObject replyJson; 
    replyJson = connector.sendRequestAndBlockUntilReply(requestJson); 
    // throw an exception in case an error occurred 
    boolean error = (boolean) replyJson.get("error"); 
    if ( error ) { 
      String errMsg = replyJson.get("errorMsg").toString(); 
      throw new RuntimeException("In requestAndAwaitReply: Server returned error with msg: "+ errMsg); 
    } 
    return replyJson; 
  } 
} 
