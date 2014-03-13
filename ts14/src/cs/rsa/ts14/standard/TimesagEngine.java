/*
 * Copyright 2014 group bravo, Aarhus University
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

import java.io.*;
import org.apache.commons.io.*;

import cs.rsa.ts14.framework.*;

/** A generic Timesage engine for processing timesag input
 * 
 * @author group bravo, Aarhus University
 */
public class TimesagEngine {
  public String getTimesagReport(File file, TimesagLineProcessor tlp) throws IOException {
    // Create an iterator for the lines in the file
    LineIterator it = FileUtils.lineIterator(file, "UTF-8");
    tlp.beginProcess();
    try {
      while (it.hasNext()) {
        // process each line
        String line = it.nextLine();
        LineType linetype = tlp.process( line );
        if ( linetype == LineType.INVALID_LINE ) {
          // todo throw exception ?
          break;
        }
      }
    } finally {
      LineIterator.closeQuietly(it);
    }
    tlp.endProcess();
    if(!tlp.lastError().equals("No error"))
    {
      return ("Error in input: " + tlp.lastError());
    }
    else
    {
      return (tlp.getReport());
    }
  }
}
        
