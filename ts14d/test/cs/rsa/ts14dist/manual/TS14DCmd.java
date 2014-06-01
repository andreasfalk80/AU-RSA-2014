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
 
import static org.junit.Assert.assertTrue; 

import java.io.*; 

import cs.rsa.ts14.framework.LineType; 
import cs.rsa.ts14dist.client.*; 
import cs.rsa.ts14dist.common.Constants; 
import cs.rsa.ts14dist.factory.*;
 
/** A very simple driver for the client side of TS14-D. A classic
 * read-eval-loop command line shell.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class TS14DCmd { 
   
  public static void main(String[] args) throws IOException { 
    ClientAbstractFactory factory = null;
    String mqip = "none"; 
    if ( args.length > 0 ) { 
      mqip = args[0]; 
    }
    if ( mqip.equals("none") ) {
      factory = new Level0ClientFactory();
    } else {
      factory = new StandardClientFactory( mqip );
    }
    
    System.out.println("=== Welcome to TS14-D command line ==="); 
    System.out.println("-- RabbitMQ connector at: "+ mqip); 

    new TS14DCmd(factory).readEvalLoop(); 
  } 
 
  private ClientRequestHandler requestHandler; 
  private String user = "rsa"; 
   
  public TS14DCmd(ClientAbstractFactory factory) throws IOException { 
    // Define the client side request handler, and 
    // couple it to the defined connector. 
    requestHandler = factory.createClientRequestHandler();  
  } 
   
  public void readEvalLoop() { 
    showHelp(); 
     
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in)); 
     
    System.out.println("Entering command loop, type \"q\" to quit."); 
     
    // and enter the command processing loop 
    String line, argument; 
    try { 
      do { 
        System.out.print("> "); 
        line = bf.readLine(); 
        if ( line.length() > 0 ) { 
          switch ( line.charAt(0) ) { 
          case 'a': { 
            argument = line.substring(2); 
            LineType linetype = requestHandler.addLine(user, argument); 
            System.out.println(" Return value: LineType = "+ linetype);  
            break; }          
          case 'u': { 
            user = line.substring(2); 
            System.out.println(" Changed user to: "+ user);  
            break; }          
          case 'l': { 
            String contents = requestHandler.getContents(user); 
            System.out.println(contents); 
            break; } 
          case '1': { 
            printReport(Constants.WEEKOVERVIEW_REPORT); 
            break; } 
          case '2': { 
            printReport(Constants.CATEGORYOVERVIEW_REPORT); 
            break; } 
          case '3': { 
            printReport(Constants.TRANSPORTMODE_REPORT); 
            break; } 
          case 'c' : {
            cheat(); 
            break; }
          case 'h' : {
            showHelp(); 
            break; }
          } 
        } 
        System.out.println(); 
      } while (!line.equals("q")); 
    } catch (IOException e) { 
      System.out.println("Exception found: " + e); 
    } 
    System.out.println("Leaving TS14-D."); 
 
  }

  private void showHelp() {
    System.out.println("TS14-D Command line tool"); 
    System.out.println("Syntax: [command] [line]"); 
    System.out.println(" [command]: exactly one character (followed by exactly 0-1 space) "); 
    System.out.println(" [line]   : the parameter of the command (potentially empty)"); 
    System.out.println("Commands: ");
    System.out.println("  a = add a line to timesag");
    System.out.println("  l = list timesag contents");
    System.out.println("  1 = generate weekly overview");
    System.out.println("  2 = generate category overview");
    System.out.println("  3 = generate transport mode overview");
    System.out.println("  u = set new user name");
    System.out.println("  q = quit");
    System.out.println("  h = show this help");
    System.out.println("  c = cheat - fill a lot of contents into database for current user");
    System.out.println("Current user: "+ user);
  } 
 
 
  private void printReport(String reportType) throws IOException { 
    String report = requestHandler.getReport(user, reportType); 
    System.out.println(report); 
  } 
 
 
  private void cheat() throws IOException { 
    for ( String line: timesagLines ) { 
      LineType lineType = requestHandler.addLine(user, line); 
      assertTrue(lineType != LineType.INVALID_LINE); 
    } 
  } 
   
  String[] timesagLines = new String[] { 
    "# Timesag system for NN 2013", 
    "	", 
    "# ===================================== Karakteristika for year", 
    "HoursOvertime = 502.2", 
    "Year = 2013", 
    "", 
    "# ============================================================", 
    "", 
    "Week 1 :	3	:	0", 
    "", 
    "Wed    		Ca				8.00", 
    "	adm		-		1", 
    "	sa		exam		3	forb question", 
    "", 
    "	sa		exam		1", 
    "", 
    "	sa		exam		2", 
    "Thu		No		", 
    "	syg		-		4.5", 
    "	sa		exam		3", 
    "Fri		Ho				8-", 
    "	sa		exam		1", 
    "	n4c		-		1", 
    "	itevmd		-		1", 
    "	mtt		-		1", 
    "", 
    "	mtt		plan		2", 
    "	saip		plan		1.5", 
    "", 
    "", 
    "Week 2 :	5	:	0", 
    "", 
    "Mon		Ca				8.00-", 
    "	sa		exam		4", 
    "	", 
    "	sa		exam		2", 
    "", 
    "	adm		-		1", 
    "Tue		Ca				8.30", 
    "	sa		exam		3.5", 
    "	", 
    "	sa		exam		2.5", 
    "	adm		-		0.5", 
    "	itevmd		-		0.5", 
    "", 
    "Wed		Ca				8.00", 
    "	adm		-		0.5", 
    "	sa		exam		3.5", 
    "", 
    "	sa		exam		3", 
    "	itevmd		-		0.5", 
    "	", 
    "Thu		Ca				8.00", 
    "	adm		-		0.5", 
    "	sa		exam		3.5", 
    "", 
    "	sa		exam		4", 
    "	", 
    "Fri		Ca				8.00-16.30", 
    "	adm		-		0.5", 
    "	censor		-		0.5", 
    "	n4c		e2e		3", 
    "", 
    "	sa		protokol	2", 
    "	saip		-		0.5", 
    "	itevmd		-		0.5", 
    "	sa		social		1.5", 
    "", 
    "Sat		No", 
    "	sa		hotciv		1	rette hotciv kode til", 
    "", 
    "	es		litt		1.5", 
    "", 
    "Week 3 :	5	:	0", 
    "", 
    "Mon		Ca				8.30-15.30", 
    "	adm		-		0.5", 
    "	es		-		0.5", 
    "	sa		-		1", 
    "	adm		-		1.5", 
    "", 
    "	book2		errata		1", 
    "	es		-		2.5", 
    "", 
    "", 
    "	terna		course		1.5", 
  }; 
} 
