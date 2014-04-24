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
 
/* 
 * Copyright 2013 Henrik Baerbak Christensen, Aarhus University 
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
  
package cs.rsa.ts14dist.doubles;  
  
import java.util.*;  
 
import com.mongodb.*; 
 
import cs.rsa.ts14dist.database.Storage; 
  
  
/** Fake object implementation of the MongoDB storage.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University  
 *  
 */  
public class FakeObjectStorage implements Storage {  
   
  private Map<String,BasicDBObject> database; 
  
  public FakeObjectStorage() {  
    database = new HashMap<String, BasicDBObject>(10); 
  }  
  
  public BasicDBObject getDocumentFor(String userName) {  
    return database.get(userName);  
  } 
 
  @Override 
  public void updateDocument(String user, BasicDBObject document) { 
    database.put(user, document); 
  }  
  
}  
