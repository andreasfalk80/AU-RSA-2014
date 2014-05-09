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

import cs.rsa.ts14dist.appserver.*;
import cs.rsa.ts14dist.common.Constants;
import cs.rsa.ts14dist.cookie.*;
import cs.rsa.ts14dist.database.*;
import cs.rsa.ts14dist.doubles.*;

/** Level 1 testing factory, creating a real RabbitMQ and fortune cookie
 * service connection, however the storage is a fake object.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class Level1ServerFactory implements ServerAbstractFactory {

  @Override
  public ServerRequestHandler createServerRequestHandler() {
    // Replace actual database with fake object/spy 
    Storage storage = createStorage();   

    // The TS14 domain system  
    TS14Facade facade = new TS14FacadeDouble(); 
    
    //The reliable fortune cookieservice
    CookieService cookieService = 
            new ReliableCookieService(Constants.DIGITALOCEAN_INSTANCE_IP, 
                Constants.COOKIE_REST_PORT);


    return new StandardServerRequestHandler(storage, facade, cookieService);
  }

  protected Storage createStorage() {
    return new FakeObjectStorage();
  }

}
