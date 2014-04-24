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

import cs.rsa.ts14.framework.ClassType;

/** Define a mapping between categories to their
 * respective classes. Ideally a configurable mapping
 * but fixed in this exercise.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 *
 */
public class ClassMap {

  public static ClassType mapCategoryToClass(String category) {
    switch( category ) {
    case "saip":
    case "censor":
    case "sa":
    case "mtt":
      return ClassType.TEACHING;

    case "es":
    case "book2":
    case "n4c":
      return ClassType.RESEARCH;

    case "syg":
      return ClassType.MISC;
    
    case "terna":
      return ClassType.CONSULENT;
      
    case "itevmd":
    case "adm":
      return ClassType.ADM;
      
    default:
        return null;
    }
  }

}

/*
-- Time spent on classes and categories --
teaching                  47.0 ( 68%)
    saip     :     2.0
    censor   :     0.5
    sa       :    41.5
    mtt      :     3.0
research                   9.5 ( 14%)
    es       :     4.5
    book2    :     1.0
    n4c      :     4.0
misc                       4.5 (  6%)
    syg      :     4.5
consulent                  1.5 (  2%)
    terna    :     1.5
adm                        8.5 ( 12%)
    itevmd   :     2.5
    adm      :     6.0
*/