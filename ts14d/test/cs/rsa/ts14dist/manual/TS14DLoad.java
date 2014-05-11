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

import java.io.IOException;

import cs.rsa.ts14.framework.LineType;
import cs.rsa.ts14dist.client.*;

/** A simple load generator; given address of MQ and a user
 * name, generate 'add a line' requests every 1 second.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class TS14DLoad {
  public static void main(String[] args) throws IOException {
    System.out.println("=== TS14-D Load Generator ===");
    String mqip = args[0];
    String user = args[1];
    
    System.out.println("  MQ at   : "+ mqip);
    System.out.println("  User is : "+ user);
    System.out.println("Hit Ctrl-c to terminate...");
    
    ClientAbstractFactory factory = new StandardClientFactory( mqip );
    
    TS14DLoad loader = new TS14DLoad(factory, user);
    loader.generateLoad();
  }

  private ClientRequestHandler requestHandler; 
  private String user; 

  public TS14DLoad(ClientAbstractFactory factory, String user) throws IOException {
    this.user = user;
    requestHandler = factory.createClientRequestHandler();
  }

  private final String weekline = "Week 2 : 5 : 0";
  private final String weekdayline = "Mon   Ca        8.00-";
  private final String workline = "   sa    exam    2";
  private int count = 0;
  
  private void generateLoad() {
    System.out.println("Generating load at one item per second...");
    while (true) {
      if ( count % 5 == 0 ) {
        sendALine(weekline);
      } else if ( count % 5 == 1 ) {
        sendALine(weekdayline);
      } else {
        sendALine(workline + " hard work of "+user);
      }
      count++;
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
    	  //Do nothing
      }
    }
     
  }
  private void sendALine(String line) {
    try {
      LineType lineType = requestHandler.addLine(user, line);
      System.out.println(""+count+": "+line + " / "+ lineType);
    } catch (IOException e) {
      // Do nothing
    } 
  }

}
