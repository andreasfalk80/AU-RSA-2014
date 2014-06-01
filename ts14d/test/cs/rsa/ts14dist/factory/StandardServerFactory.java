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

import cs.rsa.ts14dist.appserver.ServerAbstractFactory;
import cs.rsa.ts14dist.database.*;

/** Create a 'standard' server factory - with real MongoDB connection;
 * however the TS14 sub system is just simple test doubles.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class StandardServerFactory extends Level1ServerFactory implements
    ServerAbstractFactory {

  private String dbip;

  public StandardServerFactory(String dbip) {
    this.dbip = dbip;
  }

  @Override
  protected Storage createStorage() {
    Storage storage = new MongoStorage(dbip, "ts14db");

    return storage;
  }

}
