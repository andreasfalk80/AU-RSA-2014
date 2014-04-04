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

/** Handy little data class for keeping week data.
 * Maybe interesting to move to the source tree?
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class WeekData {
  // The number of the week
  public int weekNumber;
  // The number of hours worked this week
  public double hoursWorked;
  // The number of working days this week
  public int workDays;
  
  public String toString() {
    return "WeekData: ("+weekNumber+","+hoursWorked+","+workDays+")";
  }
}
