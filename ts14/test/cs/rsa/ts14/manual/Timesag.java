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

package cs.rsa.ts14.manual;

import java.io.*;
import org.apache.commons.io.*;

import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;

/** A command line processor that reads a timesag file and
 * outputs the required report.
 * 
 * An example of manual testing.
 * 
 * NOTE: This is a partial and unfinished implementation.
 * 
 * @author Henrik Baerbak Christensen, Aarhus University
 */
public class Timesag {
  public static void main(String[] args) throws IOException {
    // Validate arguments
    if ( args.length != 2 ) {
      System.err.println("Usage: Timesag [type] [timesag file]");
      System.err.println("  type must be one of (W, C, T).");
      System.err.println("  timesag file must obey the specified format.");
      System.exit(-1);
    }
    
    String reportTypeString = args[0];
    File file = new File(args[1]);

    // Configure the Timesag processor based upon
    // report type string.
    
    // TODO: introduce the proper builder and line type
    // classifier based upon the W,C,T parameter on the
    // command line
    ReportBuilder builder = new SpyWorkloadBuilder();
    LineSequenceState sequenceState = new LineSequenceStateStub();
    TimesagLineProcessor tlp = 
        new StandardTimesagLineProcessor( 
            new FaultyLineTypeClassifierStrategy(),
            builder, sequenceState );
    
    // Create an iterator for the lines in the file
    LineIterator it = FileUtils.lineIterator(file, "UTF-8");
    tlp.beginProcess();
    try {
      while (it.hasNext()) {
        // process each line
        String line = it.nextLine();
        LineType linetype = tlp.process( line );
        if ( linetype == LineType.INVALID_LINE ) {
          System.out.println("Error in file: " + tlp.lastError() );
          System.exit(-1);
        }
      }
    } finally {
      LineIterator.closeQuietly(it);
    }
    tlp.endProcess();
    // Aske the builder for the final report and print it.
    System.out.println(builder.getResult());
  }
}
        
