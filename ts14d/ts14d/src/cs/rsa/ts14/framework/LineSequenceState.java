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

/** Define the State role of the State pattern for a state machine
 * that validate the sequence of lines in a timesag file.
 * 
 * An instance is required to remember the type of the last seen line 
 * and then validate that a transition to a new line type state is
 * valid.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 *
 */

public interface LineSequenceState {
  
  /** Transition to a new state based upon the type of line
   * seen.
   * 
   * @param lineType
   * @return an instance of LineSequenceState that represents
   * the state the time line processor has moved to.
   */
  LineSequenceState transitionOn(LineType lineType);

  /** In case of InvalidState then a description of the
   * error in sequencing. Otherwise just returns "OK".
   * @return the error
   */
  String lastError();
}
