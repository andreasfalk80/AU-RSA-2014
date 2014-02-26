/*
 * Copyright RSA 2014 Group Bravo, Aarhus University
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

package cs.rsa.ts14.bravo;

import cs.rsa.ts14.framework.ClassType;
import cs.rsa.ts14.framework.ReportBuilder;
import cs.rsa.ts14.standard.ClassMap;

import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author RSA 2014 Group Bravo
 */

public class BravoCategoryOverviewBuilder implements ReportBuilder {
  // Data structure for mapping hours by work category
  // Use LinkedHashMap to maintain the order of insertion
  LinkedHashMap<String, Double> workCategoryToHoursMap;

  // Data structure for mapping hours by work class
  HashMap<ClassType, Double> workClassToHoursMap;

  // The total work hours from all work categories
  double totalWorkHours;

  // Result string
  String resultString;
  // Error string
  String errorString;

  
  @Override
  public void buildBegin() {
    workCategoryToHoursMap = new LinkedHashMap<String, Double>();
    workClassToHoursMap = new HashMap<ClassType, Double>();
    totalWorkHours = 0;
    resultString = "";
    errorString = "";
    
    // Initialize work categories to ensure that all categories are shown + in the specified order
    workCategoryToHoursMap.put("saip", 0.0);
    workCategoryToHoursMap.put("censor", 0.0);
    workCategoryToHoursMap.put("sa", 0.0);
    workCategoryToHoursMap.put("mtt", 0.0);
    workCategoryToHoursMap.put("es", 0.0);
    workCategoryToHoursMap.put("book2", 0.0);
    workCategoryToHoursMap.put("n4c", 0.0);
    workCategoryToHoursMap.put("syg", 0.0);
    workCategoryToHoursMap.put("terna", 0.0);
    workCategoryToHoursMap.put("itevmd", 0.0);
    workCategoryToHoursMap.put("adm", 0.0);

    // Initialize all work classes
    for(ClassType classType : ClassType.values())
    {
      workClassToHoursMap.put(classType, 0.0);
    }
  }

  @Override
  public void buildWeekSpecification(int weekNo, int countWorkdays,
      int countUsedVacationdays) {
  }

  @Override
  public void buildWorkSpecification(String category, String subCategory,
      double hours) {
	  
    ClassType classType = ClassMap.mapCategoryToClass(category);
/*
 * input validation
 */
    if(hours < 0 && errorString.equals("")){ //if error already set, then keep that error message
    	errorString = "Illegal value for hours found: \""+hours + "\". The record was ignored.";
    }
    else{
  	if(classType == null && errorString.equals("")){ //if error already set, then keep that error message
    		errorString = "Unknown category found: \""+category + "\". The record was ignored.";
   	}
  	else{
    if(classType != null && workCategoryToHoursMap != null && workClassToHoursMap != null)
    {
      // Add hours to work category
      double workCategoryHours = hours;
      if(workCategoryToHoursMap.containsKey(category))
      {
        workCategoryHours += workCategoryToHoursMap.get(category);
      }
      workCategoryToHoursMap.put(category, workCategoryHours);

      // Add hours to work class
      double workClassHours = hours;
      if(workClassToHoursMap.containsKey(classType))
      {
        workClassHours += workClassToHoursMap.get(classType);
      }
      workClassToHoursMap.put(classType, workClassHours);

      // Add hours to total work hours
      totalWorkHours += hours;
    }
  	}
  	}
  }

  @Override
  public void buildWeekDaySpecification(String weekDay, String transportMode) {
  }

  @Override
  public void buildAssignment(String variable, double value) {
  }

  @Override
  public void buildEnd() {
    StringBuilder resultStringBuilder = new StringBuilder();
    //create new Locale, to make sure the deciml point is '.' and not ','
    Locale engLocale = new Locale("ENG");

    
    // The percentace calculations are apparently done without calculating the cosultancy hours
    double totalWorkHoursWithoutConsultancy = totalWorkHours-workClassToHoursMap.get(ClassType.CONSULENT);

    // Build result, first append header to result
    resultStringBuilder.append("-- Time spent on classes and categories --\n");

    // Iterate through all ClassType enum values
    for(ClassType classType : ClassType.values())
    {
      // Workaround for ClassType enum items not being in the specified order
      if(classType == ClassType.TEACHING)
        classType = ClassType.RESEARCH;
      else if(classType == ClassType.RESEARCH)
        classType = ClassType.TEACHING;

      double classHours = workClassToHoursMap.get(classType);
      
      //handle if no categories other than consulent is found.
      double classPercentOfTotal;
      if(totalWorkHoursWithoutConsultancy != 0){
    	  classPercentOfTotal = (classHours/totalWorkHoursWithoutConsultancy) * 100;
      }
      else{
    	  classPercentOfTotal = 0;
      }
    	  

	  // Append work class line to result
      resultStringBuilder.append(String.format(engLocale,"%-21s%9.1f (%3.0f%%)\n",
                                               getClassString(classType),
                                               classHours,
                                               classPercentOfTotal));

      // Iterate through all workCategoryToHoursMap items, and append the work categories that belongs to this work class
      for (Map.Entry<String, Double> entry : workCategoryToHoursMap.entrySet()) {
        if(ClassMap.mapCategoryToClass(entry.getKey()) == classType) {
          // Append work category line to result
          resultStringBuilder.append(String.format(engLocale,"    %-9s:%8.1f\n",
                                                   entry.getKey(),
                                                   entry.getValue()));
        }
      }
    }

    // Append total to result
    resultStringBuilder.append(String.format(engLocale,"Total:              %10.1f (%s)\n",
                                             totalWorkHoursWithoutConsultancy,
                                             totalWorkHours));
    resultStringBuilder.append("                          ===============\n");

    
    //setup errorstring
    if(!errorString.equals("")){
    	resultStringBuilder.append(errorString).append("\n");
    }
    resultString = resultStringBuilder.toString();
  }

  @Override
  public String getResult() {
  return resultString;
  }

  private String getClassString(ClassType classType)
  {
  switch(classType)
  {
    case TEACHING:
      return "teaching";
    case RESEARCH:
      return "research";
    case MISC:
      return "misc";
    case CONSULENT:
      return "consulent";
    case ADM:
      return "adm";
    default:
      return "unknown class";
    }
  }

}
