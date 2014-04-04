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

import static org.junit.Assert.*;

import org.junit.*;

import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.statemachine.*;

/** Initial test of the state machine implementation for
 * validating the sequencing of lines.
 * 
 * This is only a partial test and only a partial 
 * solution. It is provided to give an idea of how
 * to use the State pattern for programming the 
 * validation.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 *
 */
public class TestStatemachine {
  
  private LineSequenceState sequenceState;

  @Before
  public void setup() {
    sequenceState = new InitialState();
  }
  
  @Test
  public void shouldHandleTypicalValidSequencePartially() {
    assertEquals( InitialState.class, sequenceState.getClass() );

    // Can transition to assignment
    sequenceState = sequenceState.transitionOn(LineType.ASSIGNMENT_LINE);
    assertEquals( AssignmentState.class, sequenceState.getClass() );

    // Can transition to comment
    sequenceState = sequenceState.transitionOn(LineType.COMMENT_LINE);
    assertEquals( CommentState.class, sequenceState.getClass() );
    
    // Can transition to Week
    sequenceState = sequenceState.transitionOn(LineType.WEEK_SPECIFICATION);
    assertEquals( WeekState.class, sequenceState.getClass() );

    // On to Weekday
    sequenceState = sequenceState.transitionOn(LineType.WEEKDAY_SPECIFICATION);
    assertEquals( WeekDayState.class, sequenceState.getClass() );
    
    // On to a work line
    sequenceState = sequenceState.transitionOn(LineType.WORK_SPECIFICATION);
    assertEquals( WorkState.class, sequenceState.getClass() );
    
  }
  
  @Test
  public void shouldNotTransitionFromWeekToAssignment() {
    // set the state to Week
    sequenceState = new WeekState();
    
    // Try transition into assignment
    sequenceState = sequenceState.transitionOn(LineType.ASSIGNMENT_LINE);
    assertEquals( IllegalState.class, sequenceState.getClass() );
    assertEquals( "Illegal to have assignment after week line.", sequenceState.lastError() );
  }
  
  @Test
  public void shouldNotTransitionFromWeekDayToAssignment() {
    // set the state to Week
    sequenceState = new WeekDayState();
    
    // Try transition into assignment
    sequenceState = sequenceState.transitionOn(LineType.ASSIGNMENT_LINE);
    assertEquals( IllegalState.class, sequenceState.getClass() );
    assertEquals( "Illegal to have assignment after weekday line.", sequenceState.lastError() );
  }

  @Test
  public void shouldNotTransitionFromWorkToAssignment() {
    // set the state to Week
    sequenceState = new WorkState();
    
    // Try transition into assignment
    sequenceState = sequenceState.transitionOn(LineType.ASSIGNMENT_LINE);
    assertEquals( IllegalState.class, sequenceState.getClass() );
    assertEquals( "Illegal to have assignment after work line.", sequenceState.lastError() );
  }
  
  @Test
  public void shouldNotAllowThreeEmptyLines() {
    // Accept 1st empty line
    sequenceState = sequenceState.transitionOn(LineType.EMPTY_LINE);
    assertEquals( EmptyState.class, sequenceState.getClass() );
    
    // Accept 2nd empty line
    sequenceState = sequenceState.transitionOn(LineType.EMPTY_LINE);
    assertEquals( EmptyState.class, sequenceState.getClass() );

    // Reject 3rd empty line
    sequenceState = sequenceState.transitionOn(LineType.EMPTY_LINE);
    assertEquals( IllegalState.class, sequenceState.getClass() );
    assertEquals( "Illegal to have three consecutive empty lines.", sequenceState.lastError() );
  }

}
