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
 
import java.io.IOException;

import org.json.simple.JSONObject; 
 
import com.mongodb.BasicDBObject; 
 
import cs.rsa.ts14.framework.*; 
import cs.rsa.ts14dist.common.*; 
import cs.rsa.ts14dist.cookie.CookieService;
import cs.rsa.ts14dist.database.Storage; 
 
/** Default framework implementation of the server request
 * handler role.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class StandardServerRequestHandler implements ServerRequestHandler { 
 
  private Storage storage; 
  private TS14Facade ts14facade;
  private CookieService cookieService; 
 
  public StandardServerRequestHandler(Storage storage, 
      TS14Facade facade, 
      CookieService cookieService) { 
    this.storage = storage; 
    this.ts14facade = facade; 
    this.cookieService = cookieService;
  } 
 
  @Override 
  public JSONObject handleRequest(JSONObject request) { 
    // System.out.println("ServerRequestHandler/handling request: "+ requestObject); 
 
    JSONObject reply;   
 
    // Extract the user and command from the request object 
    String user = request.get(Constants.USER_KEY).toString(); 
    String command = request.get(Constants.COMMAND_KEY).toString(); 
 
    // Switch on the type of request from the client 
    if ( command.equals(Constants.ADDLINE_REQUEST) ) { 
      // Get the actual line to add from the request object 
      String newLineToAdd = request.get(Constants.PARAMETER_KEY).toString(); 
 
      // Compute an answer for the given added line 
      LineType lineType = ts14facade.classify(newLineToAdd); 
       
      // if the line type is legal, compose a reply, and update 
      // the database 
      if ( lineType != LineType.INVALID_LINE ) {  
        // define the return value of this method 
        reply = CommandLanguage.createValidReplyWithReturnValue( lineType.ordinal() ); 
 
        // update the database with the new contents 
        updateContentsForUserWithNewLine(user, newLineToAdd); 
      } else { 
        // invalid line type - tell the client 
        reply = CommandLanguage.createInvalidReplyWithExplantion("Line: #"+newLineToAdd+"# is invalid line type"); 
      } 
    } else if ( command.equals(Constants.GETREPORT_REQUEST) ) { 
      // get the contents of the timesag 
      BasicDBObject dbo = storage.getDocumentFor(user); 
      String contents = dbo.getString("contents"); 
 
      // get the required type of report 
      String reporttype = request.get(Constants.PARAMETER_KEY).toString(); 
       
      // Run contents through the processor 
      String report = generateReport(contents, reporttype); 
       
      reply = CommandLanguage.createValidReplyWithReturnValue(report); 
    } else if ( command.equals(Constants.GETCONTENTS_REQUEST) ) { 
      // get the contents of the timesag for the given user 
      BasicDBObject dbo = storage.getDocumentFor(user); 
      String contents = dbo.getString("contents"); 
       
      reply = CommandLanguage.createValidReplyWithReturnValue(contents); 
    } else { 
      // huh?? Not a known command
      reply = CommandLanguage.createInvalidReplyWithExplantion("Unknown command received: "+ command+ "/obj="+request ); 
    } 
     
    return reply; 
     
  } 
   
  private void updateContentsForUserWithNewLine(String user, String newLineToAdd) { 
    // query the database for the document for this user 
    // and update the contents 
    BasicDBObject persistentObject = storage.getDocumentFor(user); 
    if ( persistentObject == null ) { 
      persistentObject = new BasicDBObject(); 
      persistentObject.put("user", user); 
      persistentObject.put("contents", newLineToAdd); 
    } else { 
      String contents = persistentObject.getString("contents"); 
      contents += "\n"+newLineToAdd; 
      persistentObject.put("contents", contents); 
    } 
    // finally, write the document back into the document database 
    storage.updateDocument(user, persistentObject); 
  } 
 
  private String generateReport(String timesagContents, String reporttype) { 
    String[] splitData = timesagContents.split("\n"); 
    String report = ts14facade.generateReport( splitData, reporttype ) + "\n";
    String cookie =  getFortuneCookie();
    return report + cookie; 
  }

  private String getFortuneCookie() {
    String cookie = "Today's Cookie:\n";
    try {
      cookie += cookieService.getNextCookie();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return cookie;
  } 
 
} 
