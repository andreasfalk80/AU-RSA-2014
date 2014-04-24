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
 
package cs.rsa.ts14dist.factory;

import java.io.IOException;

import cs.rsa.ts14dist.appserver.*;
import cs.rsa.ts14dist.client.*;
import cs.rsa.ts14dist.cookie.CookieService;
import cs.rsa.ts14dist.database.Storage;
import cs.rsa.ts14dist.doubles.*;

/** Level 0 testing factory, creates all test double delegates. 
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class Level0ClientFactory implements ClientAbstractFactory {

  public Storage createStorage() {
    return new FakeObjectStorage();
  }

  public TS14Facade createTS14Domain() {
    return new TS14FacadeDouble();
  }

  public CookieService createCookieService() {
    return new StubCookieService();
  }

  public Connector createConnector(ServerRequestHandler serverRequestHandler) throws IOException {
    Connector connector;
    connector = new InMemoryCallConnector(serverRequestHandler); 
    return connector;
  }

  @Override
  public StandardClientRequestHandler createClientRequestHandler() throws IOException {
    Storage storage; 
    ServerRequestHandler serverRequestHandler; 
    TS14Facade facade; 

    // Replace actual database with fake object/spy 
    storage = createStorage(); 

    // The TS14 domain system  
    facade = createTS14Domain(); 

    // The fortune cookie service
    CookieService cookieService = createCookieService(); 

    // Couple the server side request handler (POSA 4) 
    // to the storage system and to the TS14 domain 
    serverRequestHandler =  
        new StandardServerRequestHandler(storage, facade, cookieService); 

    // Define a local in memory call connector to tie client 
    // side with server side 
    Connector connector; 

    connector = createConnector( serverRequestHandler );

    return new StandardClientRequestHandler(connector);
  }

}
