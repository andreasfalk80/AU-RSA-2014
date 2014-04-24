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
 
package cs.rsa.ts14dist.appserver; 
 
import cs.rsa.ts14.framework.LineType; 
 
/** Facade pattern - a facade to the core TS14 domain processing from
 * the TS14 project.
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 */ 
public interface TS14Facade { 
 
  /** Facade method to the TS14's line type classfier
   *  
   * @param newLineToAdd the timesag line to classify
   * @return the line type
   */
  LineType classify(String newLineToAdd); 
 
  /** Facade method to the TS14's report generator
   * 
   * @param timesagContentsArray the timesag contents
   * to process, as an array of individual contents
   * lines
   * @param reportType a type string, see the
   * Constant type for legal types
   * @return the report as a single string with
   * embedded EOL markers.
   */
  String generateReport(String[] timesagContentsArray, String reportType); 
 
} 
