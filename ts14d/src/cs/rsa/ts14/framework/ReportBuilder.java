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

/** The specification of the builder which produces
 * a report. 
 * 
 * This is the Builder role of the Builder pattern and defines
 * the parts of the report that can be built.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public interface ReportBuilder {
  /** Mark the beginning of the building process. */
  void buildBegin();
  
  /** Build the part associated with a week specification 
   * 
   * @param weekNo the number of the week
   * @param countWorkdays the number of working days in the week
   * @param countUsedVacationdays the number of vacation days used
   */
  void buildWeekSpecification(int weekNo, int countWorkdays,
      int countUsedVacationdays);
  
  /** Build the part associated with concrete work on a category
   * 
   * @param category the main category worked on
   * @param subCategory the subcategory worked on (or "-")
   * @param hours the amount of hours worked
   */
  void buildWorkSpecification(String category, String subCategory, double hours);

  /** Build the part associated with a week day
   * 
   * @param weekDay the weekday ("Mon", "Tue", etc.)
   * @param transportMode the transportation mode used
   * to get to work ("Ca", "Bi", etc.)
   */
  void buildWeekDaySpecification(String weekDay, String transportMode);

  /** Build the part associated with an assignemnt
   * 
   * @param variable the variable assigned
   * @param value the value assigend
   */
  void buildAssignment(String variable, double value);

  /** Mark the ending of the build process */
  void buildEnd();

  /** Return the report built, is only valid after
   * buildEnd() has been invoked
   * @return
   */
  String getResult();
}
