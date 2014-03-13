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
import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import cs.rsa.ts14.standard.TimesagEngine;
import cs.rsa.ts14.Golf.statemachine.InitialState;
import cs.rsa.ts14.bravo.*;
import cs.rsa.ts14.charlie.*;
import cs.rsa.ts14.delta.*;

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
    
    File file = new File(args[1]);

    // Configure the Timesag processor based upon report type string.
    ReportBuilder builder;
    if(args[0].equals("C"))
    {
      builder = new BravoCategoryOverviewBuilder(); // bravo
    }
    else if(args[0].equals("W"))
    {
      builder = new WeeklyOverviewReportBuilder(); // charlie
    }
    else
    {
      builder = new TransportReportBuilder(); // delta
    }
	LineSequenceState sequenceState = new InitialState();
    TimesagLineProcessor tlp = 
        new StandardTimesagLineProcessor( 
            new BravoLineTypeClassifierStrategy(),
            builder, 
            sequenceState);
    
    TimesagEngine engine = new TimesagEngine();
    
    System.out.print(engine.getTimesagReport(file, tlp));
  }
}
        
