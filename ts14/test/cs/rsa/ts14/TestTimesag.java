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
package cs.rsa.ts14;

import org.junit.*; 

import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import static org.junit.Assert.*; 

/** JUnit learning tests. 
 * 
 * Use these test cases as a way to understand how the architecture 
 * of the TS14 system is (the use of the line type strategy and the report
 * builder).
 * 
 * Use these test cases as a demonstration of how to
 * build and test higher level abstractions (the TimesagLineProcessor)
 * without the lower level abstractions by using
 * test doubles.
 * 
 * Use these tests as an example of how to express
 * test cases in Java.
 * 
 * Use these tests as guide to writing your own test
 * cases in the mandatory project.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 *
 */
public class TestTimesag {
  
  private TimesagLineProcessor processor;
  private LineTypeClassifierStrategy classifier;
  private LineSequenceState sequenceState;
  
  // Note that I need access to temporary values
  // in the builder and therefore declare it
  // by its concrete type.
  private SpyWorkloadBuilder builder;
  
  private String line;

  @Before
  public void setup() {
    // As builder, we use a partial implementation
    //
    builder = new SpyWorkloadBuilder();
    classifier = new FaultyLineTypeClassifierStrategy();
    sequenceState = new LineSequenceStateStub();
    // Configure the standard TS14 line processor
    processor = 
        new StandardTimesagLineProcessor( classifier, builder, sequenceState );
  }
  
  @Test
  public void shouldAcceptEmptyLine() {
    line = "   ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.EMPTY_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptCommentLine() {
    line = "# Timesag system";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.COMMENT_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptAssignmentLine() {
    line = "HoursOvertime = 390.2";
    assertEquals(-999.9, builder.getValueOfLastAssignment(), 0.001);
    LineType theLineType = processor.process(line);
    assertEquals(LineType.ASSIGNMENT_LINE, theLineType);
    assertEquals(390.2, builder.getValueOfLastAssignment(), 0.001);
  }

  @Test
  public void shouldAcceptValidWeekline() {
    line = "Week 2 :  5 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEK_SPECIFICATION, theLineType);
  }
 
  @Test
  public void shouldAcceptValidWorkline() {
    line = "  n4c   -   2";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WORK_SPECIFICATION, theLineType);
  }

  @Test
  public void shouldAcceptValidWeekdayline() {
    line = "Fri    Bi        8.30-16.30"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAccumulate8HoursInWeek2() {
    processWeek2With8HoursWorkOnTaskN4C(); 
    
    assertEquals( 2, builder.getWeekData().weekNumber);
    assertEquals( 5, builder.getWeekData().workDays);
    assertEquals( 8.0, builder.getWeekData().hoursWorked, 0.001);
  }
  
  @Test
  public void shouldGenerateReportForWeek2() {
    processWeek2With8HoursWorkOnTaskN4C();
    processor.endProcess();
    String report = processor.getReport();
    //             Week   1 :   14.5 hours   (  2 Wdays of   7.3  d=501.9)
    assertEquals( "Week   2 :    8,0 hours   (  5 Wdays of   8,0  d=-29,4)", report );  
  }

  private void processWeek2With8HoursWorkOnTaskN4C() {
    line = "Week 2 :  5 : 0";
    processor.process(line);
    line = "Fri    Bi        8.30-16.30"; 
    processor.process(line);   
    line = "  n4c   -   2";
    processor.process(line);
    line = "  n4c   -   6";
    processor.process(line);
  }
  
  @Test
  public void shouldClassifyIntoClasses() {
    line = "  n4c   -   2";
    processor.process(line);
    assertEquals(ClassType.RESEARCH, builder.getCategoryOfLastWork());

    line = "  saip   -   2";
    processor.process(line);
    assertEquals(ClassType.TEACHING, builder.getCategoryOfLastWork());
    
    line = "  terna   -   2";
    processor.process(line);
    assertEquals(ClassType.CONSULENT, builder.getCategoryOfLastWork());
    
    line = "  syg   -   2";
    processor.process(line);
    assertEquals(ClassType.MISC, builder.getCategoryOfLastWork());

    line = "  itevmd   -   2";
    processor.process(line);
    assertEquals(ClassType.ADM, builder.getCategoryOfLastWork());
  }
  
}
