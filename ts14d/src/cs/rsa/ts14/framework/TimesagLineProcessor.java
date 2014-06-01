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

/** This is the central abstraction in TS14 that
 * is responsible for accepting each line in a text file
 * and build a report. Normally you should not implement
 * this interface but rely on the framework implementation
 * in the standard package.
 * 
 * The default processing algorithm shall look like:
 * 
 * TimesagLineProcessor tlp = new ...;
 * tlp.beginProcess();
 * while ( (l = nextLineInFile()) != end-of-file ) {
 *   tlp.process(l);
 * }
 * tlp.endProcess();
 * 
 * report = tlp.getReport();
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public interface TimesagLineProcessor {

  /** Initialize the processing, must be invoked
   * once only, before any calls to process()
   */
  void beginProcess();
  
  /** Process a single input line, once for every 
   * line in the input file 
   * @return the type of line processed, if
   * LineType.INVALID_LINE is returned, then the
   * input format is incorrect, see the lastError()
   * method */
  LineType process(String line);

  /** Terminate the processing, must
   * be invoked once only, after the last
   * line of the input file has been read
   */
  void endProcess();

  /** return a string telling the last error encountered
   * @return error description
   */
  String lastError();

  /** if processing has been made for the entire set of
   * lines, then this string contains the final report
   * @return the report built
   */
  String getReport();

}
