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
 
import cs.rsa.ts14dist.appserver.*; 
import cs.rsa.ts14dist.common.Constants;
import cs.rsa.ts14dist.cookie.*;
import cs.rsa.ts14dist.database.Storage;
import cs.rsa.ts14dist.doubles.*; 
 
/** The main class of the TS14-D daemon. All delegates are
 * configured and the daemon thread started.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class TS14DDaemon { 
  
  private static Thread daemon; 
 
  public static void main(String[] args) throws InterruptedException { 
    String RabbitMQ_IPAddress = args[0]; 
     
    // Replace actual database with fake object/spy 
    Storage storage = new FakeObjectStorage();  
     
    // The TS14 domain system  
    TS14Facade facade = new TS14FacadeDouble(); 
    
    // The fortune cookie service
    CookieService cookieService = 
        new StandardCookieService(Constants.DIGITALOCEAN_INSTANCE_IP, 
            Constants.COOKIE_REST_PORT);
 
      // Couple the server side request handler (POSA 4) 
    // to the storage system and to the TS14 domain 
    ServerRequestHandler serverRequestHandler = 
        new StandardServerRequestHandler(storage, facade, cookieService); 
 
    System.out.println("=== TS14-D Server side Daemon ==="); 
    System.out.println(" RabbitMQ on IP: "+ RabbitMQ_IPAddress); 
    System.out.println("  All logging going to log file...");
    System.out.println(" Use ctrl-c to terminate!"); 
     
    // Create the server side daemon, and start it in a thread 
    Runnable mqDaemon = new RabbitMQDaemon(RabbitMQ_IPAddress, serverRequestHandler); 
    daemon = new Thread(mqDaemon); 
    daemon.start(); 
     
    daemon.join(); 
  } 
} 
