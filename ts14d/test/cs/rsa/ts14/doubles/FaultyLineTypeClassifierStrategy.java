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


package cs.rsa.ts14.doubles;

import cs.rsa.ts14.framework.*;

/** Temporary test stub in my TDD development of the
 * StandardTimesagLineProcessor implementation.
 * 
 * Is absolutely not correct but can handle the basic
 * test cases.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */

public class FaultyLineTypeClassifierStrategy implements
    LineTypeClassifierStrategy {
  
  LineType lastSeen;

  @Override
  public LineType classify(String line) {
    lastSeen = LineType.INVALID_LINE;
    if ( line.length() < 1 ) return LineType.EMPTY_LINE;
    if ( line.charAt(0) == '#' ) lastSeen = LineType.COMMENT_LINE;
    if ( line.charAt(0) == 'W' && line.charAt(2) == 'e' ) lastSeen = LineType.WEEK_SPECIFICATION;
    if ( line.charAt(0) == ' ') lastSeen = LineType.WORK_SPECIFICATION;
    if ( line.charAt(0) == 'H') lastSeen = LineType.ASSIGNMENT_LINE;
    if ( lastSeen == LineType.INVALID_LINE ) lastSeen = LineType.WEEKDAY_SPECIFICATION;
    if ( line.length() < 5 ) lastSeen = LineType.EMPTY_LINE;
    return lastSeen;
  }

  @Override
  public String lastError() {
    if ( lastSeen == LineType.INVALID_LINE ) return "Invalid weekday: Fre";
    return "No error";
  }

}
