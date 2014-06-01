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
 
package cs.rsa.ts14dist.scenario; 
 
import static org.junit.Assert.*;  
 
import java.io.IOException; 
 
import org.junit.*;  
 
import com.mongodb.BasicDBObject; 
 
import cs.rsa.ts14.framework.*; 
import cs.rsa.ts14dist.appserver.*; 
import cs.rsa.ts14dist.client.*; 
import cs.rsa.ts14dist.common.*; 
import cs.rsa.ts14dist.cookie.CookieService;
import cs.rsa.ts14dist.database.Storage; 
import cs.rsa.ts14dist.doubles.*; 
 
/** TDD test cases for developing the distribution 
 * infrastructure for TS14D. The End2End test case 
 * shouldStoreAddedLineInDatabase invokes the 
 * client-request handler's addLine methods for all 
 * lines in a small timesag file, and verifies that 
 * all are written to the database. All distributed 
 * aspects (client-server connector and database) 
 * are abstracted by 'programming to an interface' and 
 * replaced by in-memory equivalents and fake object 
 * storage. 
 *  
 * Once this aspect is in place, intensive refactoring 
 * has trimmed served side processing and marshalling, 
 * and next the remaining use cases are introduced to 
 * provide near full behavior of the client. 
 * 
 * Note: Abstract factories have been introduced for
 * both client and server side request handlers, however I have kept
 * the 'raw' initialization of delegates in this
 * test case to demonstrate the process from
 * first principles.
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 */ 
 
public class TestEnd2End { 
 
  private String timesagLines[]; 
  private String user; 
 
  private ClientRequestHandler requestHandler; 
  private ServerRequestHandler serverRequestHandler; 
  private Connector connector; 
   
  private Storage storage;
  private CookieService cookieService; 
   
  /** Configure the fake-objects that
   * represent the abstractions representing
   * distribution aspects.
   */
  @Before 
  public void setup() { 
    // Define the input lines... 
    timesagLines = new String[] { 
        "Week 2 :  5 : 0", 
        " ", 
        "Fri    Bi        8.30-16.30", 
        "  n4c   -   2", 
        "  n4c   -   6" 
    }; 
    // Define user 
    user = "rsa"; 
     
    // Replace actual database with fake object/spy 
    storage = new FakeObjectStorage();  
    
    // Replace fortune cookie service with stub
    cookieService = new StubCookieService();
     
    // The TS14 domain system is replaced by a double 
    // (using the doubles of the original TS14 system) 
    TS14Facade facade; 
    facade = new TS14FacadeDouble(); 
 
    // Couple the server side request handler (POSA 4) 
    // to the storage system and to the TS14 domain 
    serverRequestHandler =  
        new StandardServerRequestHandler(storage, facade, cookieService); 
     
    // Define a local in memory call connector to tie client 
    // side with server side 
    connector = new InMemoryCallConnector(serverRequestHandler); 
     
    // Finally define the client side request handler, and 
    // couple it to the defined connector. 
    requestHandler = new StandardClientRequestHandler(connector); 
  } 
   
  // Use case 1: User adds a single line to his timesag file 
  // AND end-2-end testing - validate that stuff is stored in 
  // the storage tier. 
  @Test 
  public void shouldStoreAddedLineInDatabase() throws IOException { 
    callAddlineForEachLineInTimesag(); 
     
    // Validate that the object has been stored in the data storage  
    BasicDBObject dboStored;  
      
    dboStored = storage.getDocumentFor(user);  
    String timeSagContents = dboStored.getString("contents"); 
 
    assertFullTimesagContents(timeSagContents); 
  } 
 
  private void callAddlineForEachLineInTimesag() throws IOException { 
    for ( String line: timesagLines ) { 
      LineType lineType = requestHandler.addLine(user, line); 
      assertTrue(lineType != LineType.INVALID_LINE); 
    } 
  } 
   
  private void assertFullTimesagContents(String timeSagContents) { 
    assertTrue( timeSagContents.contains("Week 2 :  5 : 0")); 
    assertTrue( timeSagContents.contains(" ")); 
    assertTrue( timeSagContents.contains("Fri    Bi        8.30-16.30")); 
    assertTrue( timeSagContents.contains("  n4c   -   2")); 
    assertTrue( timeSagContents.contains("  n4c   -   6")); 
  } 
 
  // Use case 2: User lists the contents of the timesag  
  @Test 
  public void shouldListTimesag() throws IOException { 
    callAddlineForEachLineInTimesag(); 
     
    String timeSagContents = requestHandler.getContents(user); 
     
    // System.out.println( timeSagContents ); 
 
    assertFullTimesagContents(timeSagContents); 
  } 
   
  // User case 3: User request his week overview 
  @Test 
  public void shouldReturnWeekOverview() throws IOException { 
    callAddlineForEachLineInTimesag(); 
     
    String report; 
     
    report = requestHandler.getReport(user, Constants.WEEKOVERVIEW_REPORT); 
    // System.out.println( report ); 
    
    assertTrue( "Week overview missing or wrong", 
        report.contains("Week overview stub: 8 hours in total")); 
    
    // assert that the fortune cookie service is called
    assertTrue( "Fortune cookie header missing or wrong", 
        report.contains("Today's Cookie:")); 
    assertTrue( "Fortune cookie contents missing or wrong", 
        report.contains("Cookie no.")); 
  } 
   
  // User case 4: User request his category overview 
  @Test 
  public void shouldReturnCategoryOverview() throws IOException { 
    callAddlineForEachLineInTimesag(); 
     
    String report; 
     
    report = requestHandler.getReport(user, Constants.CATEGORYOVERVIEW_REPORT); 
    // System.out.println( report ); 
 
    assertTrue( report.contains("Category overview stub")); 
    
    // assert that the fortune cookie service is called
    assertTrue( "Fortune cookie header missing or wrong", 
        report.contains("Today's Cookie:")); 
    assertTrue( "Fortune cookie contents missing or wrong", 
        report.contains("Cookie no.")); 
  } 
 
} 
