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
 
package cs.rsa.ts14dist.client; 
 
import java.io.IOException; 
 
import cs.rsa.ts14.framework.LineType; 
 
/** Client request handler is responsible for mediating contact
 * from the client to the server. Pattern is described in
 * Patterns of Software Architecture, Volume 4, p. 246.
 *  
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public interface ClientRequestHandler { 
 
  /** Add a line to the timesag file for given user
   * 
   * @param user the given user
   * @param ts14Line the line to add to the users timesag file
   * @return the type of line
   * @throws IOException
   */
  LineType addLine(String user, String ts14Line) throws IOException; 
 
  /** Get a report based on the given user's timesag file
   * 
   * @param user the given user
   * @param reportType type of report to retrieve, see
   * Constants for the list of defined types
   * @return the report as a string
   * @throws IOException
   */
  String getReport(String user, String reportType) throws IOException; 
 
  /** Get the contents of the timesag file for given user
   * 
   * @param user the given user
   * @return the contents of the timesag file
   * @throws IOException
   */
  String getContents(String user) throws IOException; 
 
} 
