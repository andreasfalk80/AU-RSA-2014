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
package cs.rsa.ts14.statemachine;

import cs.rsa.ts14.framework.*;

/** Define a ConcreteState for the LineSequenceState
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 *
 */

public class AssignmentState implements LineSequenceState {

  @Override
  public String lastError() {
    return "OK";
  }

  @Override
  public LineSequenceState transitionOn(LineType lineType) {
    LineSequenceState state = null;
    switch( lineType ) {
    case WEEK_SPECIFICATION:
      state = new WeekState();
      break;
    case COMMENT_LINE:
      state = new CommentState();
      break;
    case ASSIGNMENT_LINE:
      break;
    case EMPTY_LINE:
      break;
    case INVALID_LINE:
      break;
    case INVALID_SEQUENCE:
      break;
    case WEEKDAY_SPECIFICATION:
      break;
    case WORK_SPECIFICATION:
      break;
    default:
      break;
    }
    return state;
  }

}
