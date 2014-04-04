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
 
import cs.rsa.ts14.Golf.statemachine.InitialState;
import cs.rsa.ts14.charlie.WeeklyOverviewReportBuilder;
import cs.rsa.ts14.delta.TransportReportBuilder;
import cs.rsa.ts14.framework.LineSequenceState;
import cs.rsa.ts14.framework.LineType;
import cs.rsa.ts14.framework.LineTypeClassifierStrategy;
import cs.rsa.ts14.framework.ReportBuilder;
import cs.rsa.ts14.framework.TimesagLineProcessor;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;
import cs.rsa.ts14dist.appserver.TS14Facade;
import cs.rsa.ts14dist.common.Constants;
 
/** Fake object - or rather semi fake, as some behavior 
 * is rather close to real behavior. 
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 * 
 */ 
public class BravoTS14Facade implements TS14Facade { 
 
  private LineTypeClassifierStrategy classifier; 
 
  public BravoTS14Facade() { 
    classifier = new BravoLineTypeClassifierStrategy(); 
  } 
 
  @Override 
  public LineType classify(String newLineToAdd) { 
    return classifier.classify(newLineToAdd); 
  } 
 
  @Override 
  public String generateReport(String[] splitData, String type) { 
	  // Configure the Timesag processor based upon report type string.
	    ReportBuilder builder;
	    if(type.equals(Constants.CATEGORYOVERVIEW_REPORT))
	    {
	      builder = new BravoCategoryOverviewBuilder(); // bravo
	    }
	    else if(type.equals(Constants.WEEKOVERVIEW_REPORT))
	    {
	      builder = new WeeklyOverviewReportBuilder(); // charlie
	    }
	    else
	    {
	      builder = new TransportReportBuilder(); // delta
	    }
    LineSequenceState sequenceState = new InitialState(); 
    // Configure the standard TS14 line processor 
    TimesagLineProcessor processor =  
        new StandardTimesagLineProcessor( classifier, builder, sequenceState ); 
     
    processor.beginProcess();
    for ( String line: splitData ) { 
      processor.process(line); 
    } 
    processor.endProcess();

    return processor.getReport(); 
  } 
 
} 
