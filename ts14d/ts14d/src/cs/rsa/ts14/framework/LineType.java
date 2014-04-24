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

/** Enumeration of the types of lines
 * that can be parsed in a TS14 timesag file.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public enum LineType {
  WEEK_SPECIFICATION,    // Ex: Week 1 :  3 : 0
  WEEKDAY_SPECIFICATION, // Ex: Mon   Ca        8.00-
  WORK_SPECIFICATION,    // Ex:   adm   -   0.5
  ASSIGNMENT_LINE,       // Ex: HoursOvertime = 502.2
  EMPTY_LINE,
  COMMENT_LINE,          // Ex: # Timesag system for NN 2013
  INVALID_LINE,          // Line that is one of the above
  INVALID_SEQUENCE,      // Not really a line type but line sequence error
}
