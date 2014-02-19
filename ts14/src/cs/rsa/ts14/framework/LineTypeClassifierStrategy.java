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

package cs.rsa.ts14.framework;

/** Define the role of a strategy to classify a given line
 * as one of the LineType used in the TS14 input format.
 * 
 * This is the strategy role in the Strategy pattern.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */

public interface LineTypeClassifierStrategy {

  /** Given a line from the input file, classify it
   * as one of the valid (or invalid) formats for
   * TS14. If the line is invalid, the lastError()
   * method will contain a description of the faulty
   * line.
   * @param line the line
   * @return the type of the line
   */
  LineType classify(String line);

  /** Describe the error encountered last 
   * 
   * @return the offending line followed by a
   * description of the error
   */
  String lastError();

}
