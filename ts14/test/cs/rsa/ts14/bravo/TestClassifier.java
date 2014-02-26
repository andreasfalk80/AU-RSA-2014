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
package cs.rsa.ts14.bravo;

import org.junit.*; 

import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import static org.junit.Assert.*; 

/** JUnit tests. 
 * 
 * @author Bobby Nielsen
 *
 */
public class TestClassifier {
  
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
    classifier = new BravoLineTypeClassifierStrategy();
    sequenceState = new LineSequenceStateStub();
    // Configure the standard TS14 line processor
    processor = 
        new StandardTimesagLineProcessor( classifier, builder, sequenceState );
  }
  
  @Test
  public void shouldDenyInvalidWeekline1() {
    line = "Week 1 : 3 : ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline2() {
    line = "Week 1 : 3 : 0 0 ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline3() {
    line = "beek 1 : 3 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline4() {
    line = "Week 0 : 3 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline5() {
    line = "Week 54 : 3 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline6() {
    line = "Week 1 ; 3 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline7() {
    line = "Week 1 : -1 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline8() {
    line = "Week 1 : 6 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline9() {
    line = "Week 1 : 3 ; 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline10() {
    line = "Week 1 : 3 : -1";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWeekline11() {
    line = "Week 1 : 3 : 6 ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptValidWeekline() {
    line = "Week 1 : 3 : 0";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEK_SPECIFICATION, theLineType);
  }
  
  @Test
  public void shouldDenyCommentLine() {
    line = "! ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptCommentLine() {
    line = "# ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.COMMENT_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyEmptyLine() {
    line = "--- ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptEmptyLine() {
    line = "   ";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.EMPTY_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyAssignmentLine1() {
    line = "HoursOvertime  502.2";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyAssignmentLine2() {
    line = "HoursOvertime = = 502.2";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyAssignmentLine3() {
    line = "1 = 502.2";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyAssignmentLine4() {
    line = "HoursOvertime * 502.2";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyAssignmentLine5() {
    line = "HoursOvertime = a";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptAssignmentLine() {
    line = "HoursOvertime = 502.2";
    assertEquals(-999.9, builder.getValueOfLastAssignment(), 0.001);
    LineType theLineType = processor.process(line);
    assertEquals(LineType.ASSIGNMENT_LINE, theLineType);
    assertEquals(502.2, builder.getValueOfLastAssignment(), 0.001);
  }
 
  @Test
  public void shouldDenyInValidWeekdayline1() {
    line = "Fri "; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInValidWeekdayline2() {
    line = "Oth Ca 8.00-16.30"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInValidWeekdayline3() {
    line = "Fri Ot 8.00-16.30"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptValidWeekdayline1() {
    line = "Mon Bi 8.00-16.30"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAcceptValidWeekdayline2() {
    line = "Tue Ca *"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAcceptValidWeekdayline3() {
    line = "Wed Pu *"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAcceptValidWeekdayline4() {
    line = "Thu Tr *"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAcceptValidWeekdayline5() {
    line = "Fri No *"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAcceptValidWeekdayline6() {
    line = "Sat Ho *"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldAcceptValidWeekdayline7() {
    line = "Sun Bi"; 
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WEEKDAY_SPECIFICATION, theLineType);
    assertEquals( "No error", processor.lastError() );
  }
  
  @Test
  public void shouldDenyInvalidWorkline1() {
    line = "  censor    -";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWorkline2() {
    line = "censor    - 7";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }

  @Test
  public void shouldDenyInvalidWorkline3() {
    line = "  2ensor    - 7";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWorkline4() {
    line = "  censor    1 7";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptValidWorkline1() {
    line = "  censor    aau 7";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WORK_SPECIFICATION, theLineType);
  }

  @Test
  public void shouldDenyInvalidWorkline5() {
    line = "  censor    - -1";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWorkline6() {
    line = "  censor    - 24.5";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldDenyInvalidWorkline7() {
    line = "  censor    - 7.3";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.INVALID_LINE, theLineType);
  }
  
  @Test
  public void shouldAcceptValidWorkline2() {
    line = "  censor    - 7";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WORK_SPECIFICATION, theLineType);
  }

  @Test
  public void shouldAcceptValidWorkline3() {
    line = "  censor    - 7.5";
    LineType theLineType = processor.process(line);
    assertEquals(LineType.WORK_SPECIFICATION, theLineType);
  }
    
}
