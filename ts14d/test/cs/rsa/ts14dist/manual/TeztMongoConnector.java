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

import org.junit.*;

import com.mongodb.BasicDBObject;

import cs.rsa.ts14dist.database.*;

public class TeztMongoConnector {

  private Storage storage;
  private String timesagLines;
  private String user;

  private String IPOfMongo = "192.168.237.130";
  
  @Before
  public void setUp() throws Exception {
    // Define the input lines... 
    timesagLines = 
        "Week 2 :  5 : 0\n"+ 
        " \n"+
        "Fri    Bi        8.30-16.30\n"+ 
        "  n4c   -   2\n"+ 
        "  n4c   -   7\n"; 
    // Define user 
    user = "rsa"; 

    // Instantiate the real Mongo connector
    storage = new MongoStorage(IPOfMongo, "ts14db");  
  }

  @Ignore
  @Test
  public void shouldStoreADocument() {
    // This is partly the updateContentsForUserWithNewLine
    // method in the StandardServerRequestHandler code
    BasicDBObject persistentObject;
    persistentObject = new BasicDBObject(); 
    persistentObject.put("user", user); 
    persistentObject.put("contents", timesagLines);
    
    // finally, write the document back into the document database 
    storage.updateDocument(user, persistentObject); 
  }
  
  // @Ignore
  @Test
  public void shouldStoreAndRetrieveADocument() {

    shouldStoreADocument();
    
    // Validate that the object has been stored in the data storage  
    BasicDBObject dboStored;  
      
    dboStored = storage.getDocumentFor(user);  
    String timeSagContents = dboStored.getString("contents"); 

    assertTrue( timeSagContents.contains("Week 2 :  5 : 0")); 
    assertTrue( timeSagContents.contains("Fri    Bi        8.30-16.30")); 
    
    System.out.println(timeSagContents);
  }

}
