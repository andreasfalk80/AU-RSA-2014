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
 
package cs.rsa.ts14dist.doubles; 
 
import cs.rsa.ts14.framework.ReportBuilder; 
import cs.rsa.ts14dist.common.Constants; 
 
/** Completely incorrect stub, as it only allows us to see what happened. 
 * Note that it is also a wrong builder implementation as we 
 * store 'required type' information in the wantedType field. 
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 * 
 */ 
public class StubBuilder implements ReportBuilder { 
 
  private String wantedType; 
  
  private String result = null;
   
  public StubBuilder(String type) { 
    wantedType = type; 
  } 
 
  @Override 
  public void buildBegin() { 
  } 
 
  @Override 
  public void buildWeekSpecification(int weekNo, int countWorkdays, 
      int countUsedVacationdays) { 
  } 
 
  @Override 
  public void buildWorkSpecification(String category, String subCategory, 
      double hours) { 
  } 
 
  @Override 
  public void buildWeekDaySpecification(String weekDay, String transportMode) { 
  } 
 
  @Override 
  public void buildAssignment(String variable, double value) { 
  } 
 
  @Override 
  public void buildEnd() { 
    result = "Week overview stub: 8 hours in total"; 
    if ( wantedType.equals(Constants.CATEGORYOVERVIEW_REPORT) ) { 
      result = "Category overview stub"; 
    } 
  } 
 
  @Override 
  public String getResult() { 
    return result; 
  } 
 
} 
