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

import cs.rsa.ts14.framework.ClassType;
import cs.rsa.ts14.framework.ReportBuilder;
import cs.rsa.ts14.standard.ClassMap;

/** A Spy test double that spies on the processing made in the
 * StandardTimesagLineProcesser to allow test-driven development
 * of some of the features in the central time line processor. 
 * 
 * It uses the
 * Special access interface tactic for testability.
 * 
 * Also a partial
 * implementation of the Weekly overview builder.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 *
 */
public class SpyWorkloadBuilder implements ReportBuilder {

  private WeekData thisWeek = new WeekData(); // to avoid null pointers during testing
  private double valueOfLastAssignment = -999.9;
  private String lastSeenCategory;
 
  @Override
  public void buildBegin() {
  }

  @Override
  public void buildWeekSpecification(int weekNo, int countWorkdays,
      int countUsedVacationdays) {
    thisWeek = new WeekData();
    thisWeek.weekNumber = weekNo;
    thisWeek.workDays = countWorkdays;
    thisWeek.hoursWorked = 0.0;  
  }

  @Override
  public void buildWorkSpecification(String category, String subCategory,
      double hours) {
    lastSeenCategory = category;
    thisWeek.hoursWorked += hours;    
  }


  @Override
  public void buildWeekDaySpecification(String weekDay, String transportMode) {
  }

  @Override
  public void buildAssignment(String variable, double value) {
    valueOfLastAssignment = value; 
  }

  @Override
  public void buildEnd() {
  }
  
  @Override
  public String getResult() {
    String result = String.format("Week %3d : %6.1f hours   ( %2d Wdays of %5.1f  d=%3.1f)", 
        thisWeek.weekNumber, thisWeek.hoursWorked, thisWeek.workDays, 8.0, -29.4 );
    return result;
  }

  // === Specialized Access interface / Testability tactic
  
  public WeekData getWeekData() { return thisWeek; }

  public double getValueOfLastAssignment() {
    return valueOfLastAssignment;
  }

  public ClassType getCategoryOfLastWork() {
    return ClassMap.mapCategoryToClass( lastSeenCategory );
  }

}
