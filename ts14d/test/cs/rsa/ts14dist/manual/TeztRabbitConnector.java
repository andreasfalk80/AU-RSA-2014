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
 
import static org.junit.Assert.*; 
 
import java.io.IOException; 
 
import org.json.simple.*; 
import org.junit.*; 
 
import cs.rsa.ts14dist.appserver.*; 
import cs.rsa.ts14dist.client.Connector; 
import cs.rsa.ts14dist.common.*; 
 
/** TDD development of the RabbitMQ Connector 
 * and the RabbitMQ daemon thread. 
 *  
 * This is a MANUAL test in the sense that a 
 * RabbitMQ must be running on the indicated IP for this 
 * 'automatic' test to run. Set the constant in the 
 * code below to the proper IP to make it work. 
 *  
 * The weird naming of the class is to avoid the Ant 
 * script automatically running it. Sorry :) 
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 * 
 */ 
public class TeztRabbitConnector { 
 
  private String RabbitMQ_IPAddress = "192.168.49.129"; 
 
  private Connector connector; 
  private Thread daemon; 
   
  private SpyServerRequestHandler serverRequestHandler; 
 
  private String timesagLineOne; 
 
  private String user; 
   
  public static final String THE_ANSWER_IS_42 = "the-answer-is-42"; 
   
  @Before 
  public void setup() throws IOException, InterruptedException { 
    // Define the input lines... 
    timesagLineOne = "Week 2 :  5 : 0"; 
    // Define user 
    user = "rsa"; 
     
    // Create our connector for using RabbitMQ RPC 
    connector =  
        new RabbitMQRPCConnector(RabbitMQ_IPAddress); 
 
    // Create the server request handler, as a spy 
    serverRequestHandler = new SpyServerRequestHandler(); 
     
    // Create the server side deamon, and start it in a thread 
    Runnable mqDaemon = new RabbitMQDaemon(RabbitMQ_IPAddress, serverRequestHandler); 
    daemon = new Thread(mqDaemon); 
    daemon.start(); 
  } 
   
  /** Test that we can get a request-reply safely 
   * over the RabbitMQ connector... 
   * @throws IOException  
   * @throws InterruptedException  
   */ 
  @Test 
  public void shouldGetAnAnswerOverRabbitMQRPC() throws IOException { 
    JSONObject requestJson =  
        CommandLanguage.createRequestObject(user,  
            Constants.ADDLINE_REQUEST,  
            timesagLineOne); 
 
    JSONObject replyJson; 
    replyJson = connector.sendRequestAndBlockUntilReply(requestJson); 
 
    boolean error = (boolean) replyJson.get("error"); 
    assertFalse("The returned error flag is not false", error); 
 
    String report = (String) replyJson.get(Constants.RETURNVALUE_KEY); 
    assertEquals(THE_ANSWER_IS_42, report); 
     
    // assert that the MQ actually called the server request handler 
    assertNotNull("Server request handler not called", serverRequestHandler.lastCommandObject); 
  }  
} 
 
/** Spy implementation - always replies the same answer */ 
 
class SpyServerRequestHandler implements ServerRequestHandler { 
 
  public JSONObject lastCommandObject = null; 
   
  @SuppressWarnings("unchecked") 
  @Override 
  public JSONObject handleRequest(JSONObject commandObject) { 
    JSONObject reply; 
    lastCommandObject = commandObject; 
     
    reply = new JSONObject(); 
    reply.put("error", false); 
    reply.put("errorMsg", "OK"); 
    reply.put(Constants.RETURNVALUE_KEY,  
        TeztRabbitConnector.THE_ANSWER_IS_42); 
    return reply; 
  } 
   
} 
