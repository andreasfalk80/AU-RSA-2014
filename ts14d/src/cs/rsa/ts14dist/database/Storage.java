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
 
package cs.rsa.ts14dist.database; 
 
import com.mongodb.BasicDBObject; 
 
/** The storage role, that can store and access documents in
 * a MongoDB storage.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public interface Storage { 
 
  /** Get the storage object for the given user
   * 
   * @param userName user to get the document for
   * @return the document for the given user
   */
  BasicDBObject getDocumentFor(String userName); 
 
  /** Update the document for the given user - essentially
   * a complete overwrite! (Which is a bad idea, but
   * we will probably return to this point)
   * @param user the user in question
   * @param document the document to replace the original one
   */
  void updateDocument(String user, BasicDBObject document); 
 
} 
