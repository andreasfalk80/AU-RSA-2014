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

package cs.rsa.ts14.standard;

import java.util.StringTokenizer;

import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.statemachine.IllegalState;

/** Default framework implementation of the TimesagLineProcessor. The
 * line type classifier and the report builder are hotspots.
 * 
 * Roles:
 *   This class plays the Context role of the Strategy pattern
 *   with respect to the LineTypeClassifierStrategy.
 *   
 *   This class plays the Director role of the Builder pattern
 *   with respect to the ReportBuilder.
 *
 *   This class plays the Context role of the State pattern
 *   with respect to the LineSequenceState
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class StandardTimesagLineProcessor implements TimesagLineProcessor {
  /** The builder object which holds the currently built report */
  private ReportBuilder builder;
  /** The strategy that defines the algorithm for classifying read lines */
  private LineTypeClassifierStrategy lineTypeClassifierStrategy;
  /** The current state of the processor, reflecting the last
      read line (reading an assignment line changes state into assignment
      state, reading a third empty line changes state into invalid state,
      etc. */
  private LineSequenceState state;
  
  private String lastError;

  /** Default implementation of the TimesagLineProcessor. You must
   * configure this framework implementation with two roles: the
   * concrete strategy for classifying lines into their respective line type; and
   * the concrete builder for generating a proper output report.
   * 
   * @param linetypeClassifierStrategy concrete strategy for classifying lines 
   * @param reportBuilder concrete builder for generating the report
   * @param lineSequenceState 
   */
  public StandardTimesagLineProcessor(LineTypeClassifierStrategy linetypeClassifierStrategy, 
      ReportBuilder reportBuilder, LineSequenceState lineSequenceState) {
    this.builder = reportBuilder;
    this.lineTypeClassifierStrategy = linetypeClassifierStrategy;
    this.state = lineSequenceState;
    this.lastError = "No error";
  }
  
  @Override
  public void beginProcess() {
    builder.buildBegin();
  }

  @Override
  public LineType process(String line) {
    // Classify the line
    LineType lineType = lineTypeClassifierStrategy.classify(line);
    
    // Update our state machine that keeps track of the
    // sequencing of lines
    state = state.transitionOn(lineType);
    
    // if we have ended up in an illegal state due
    // to a sequencing error we can terminate already
    if ( state.getClass() == IllegalState.class ) {
      lastError = state.lastError();
      return LineType.INVALID_SEQUENCE;
    }
        
    // Parse the line into tokens for easy access
    String tokenArray[] = buildTokenArray(line);  
    
    // Based upon line type, invoke the builder
    switch ( lineType ) {
    case EMPTY_LINE:
      // No processing, just accept it
      break;
    case ASSIGNMENT_LINE:
      String variable = tokenArray[0];
      double value = Double.parseDouble(tokenArray[2]);
      
      builder.buildAssignment( variable, value );
      break;
      
    case WEEK_SPECIFICATION:
      int weekNo = Integer.parseInt(tokenArray[1]);
      int countWorkdays = Integer.parseInt(tokenArray[3]);
      int countUsedVacationdays = Integer.parseInt(tokenArray[5]);
      
      builder.buildWeekSpecification( weekNo, countWorkdays, 
                                      countUsedVacationdays);     
      break;
      
    case WEEKDAY_SPECIFICATION:
      String weekDay = tokenArray[0];
      String transportMode = tokenArray[1];
      
      builder.buildWeekDaySpecification(weekDay, transportMode);
      break;
      
    case WORK_SPECIFICATION:
      String category = tokenArray[0];
      String subCategory = tokenArray[1];
      double hours = Double.parseDouble(tokenArray[2]);
      
      builder.buildWorkSpecification(category, subCategory, hours);
      break;

    case INVALID_LINE:
      lastError = lineTypeClassifierStrategy.lastError();
      break;
      
    default:
      // No op
      break;
    }
    return lineType;
  }

  @Override
  public void endProcess() {
    builder.buildEnd();
  }
  
  @Override
  public String lastError() {
    return lastError;
  }

  @Override
  public String getReport() {
    return builder.getResult();
  }
  /** Split a line into an array of tokens,
   * whitespace is delimiter.
   * @param line the line to split
   * @return array of tokens in the line
   */
  private String[] buildTokenArray(String line) {
    String[] tokenList;
    StringTokenizer tokenizer = new StringTokenizer(line);
    tokenList = new String[ tokenizer.countTokens() ];
    int i = 0;
    while(tokenizer.hasMoreTokens()){
      tokenList[i] = tokenizer.nextToken();
      i++;
    }
    return tokenList;
  }
}
