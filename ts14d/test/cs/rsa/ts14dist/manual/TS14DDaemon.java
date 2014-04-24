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
 
import cs.rsa.ts14dist.appserver.RabbitMQDaemon;
import cs.rsa.ts14dist.appserver.ServerAbstractFactory;
import cs.rsa.ts14dist.appserver.ServerRequestHandler;
import cs.rsa.ts14dist.factory.Level1ServerFactory;
import cs.rsa.ts14dist.factory.StandardServerFactory;
 
/** The main class of the TS14-D daemon. All delegates are
 * configured and the daemon thread started.
 * 
 * Requires two arguments: 
 *   Arg 1: IP/name of rabbitmq node
 *   Arg 2: IP/name of mongodb node
 *   
 *   if the latter is 'none' then a fake object storage is used
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class TS14DDaemon { 
  
  private static Thread daemon; 
 
  public static void main(String[] args) throws InterruptedException { 
    String RabbitMQ_IPAddress = args[0]; 
    String MongoDB_IPAddress = args[1];
     
    ServerAbstractFactory factory;
    
    if ( MongoDB_IPAddress.equals("none") ) {
    factory = new Level1ServerFactory();
    } else {
      factory = new StandardServerFactory(MongoDB_IPAddress);
    }
 
    // Couple the server side request handler (POSA 4) 
    // to the storage system and to the TS14 domain 
    ServerRequestHandler serverRequestHandler = factory.createServerRequestHandler(); 
 
    System.out.println("=== TS14-D Server side Daemon ==="); 
    System.out.println(" RabbitMQ on IP: "+ RabbitMQ_IPAddress); 
    System.out.println(" MongoDB  on IP: "+ MongoDB_IPAddress); 
    System.out.println("  All logging going to log file...");
    System.out.println(" Use ctrl-c to terminate!"); 
     
    // Create the server side daemon, and start it in a thread 
    Runnable mqDaemon = new RabbitMQDaemon(RabbitMQ_IPAddress, serverRequestHandler); 
    daemon = new Thread(mqDaemon); 
    daemon.start(); 
     
    daemon.join(); 
  } 
} 
