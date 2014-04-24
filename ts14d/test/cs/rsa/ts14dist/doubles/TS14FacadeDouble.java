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
 
package cs.rsa.ts14dist.doubles; 
 
import cs.rsa.ts14.doubles.*;
import cs.rsa.ts14.framework.*; 
import cs.rsa.ts14.standard.StandardTimesagLineProcessor; 
import cs.rsa.ts14dist.appserver.TS14Facade; 
 
/** Fake object - or rather semi fake, as some behavior 
 * is rather close to real behavior. 
 *  
 * @author Henrik Baerbak Christensen, Aarhus University 
 * 
 */ 
public class TS14FacadeDouble implements TS14Facade { 
 
  private LineTypeClassifierStrategy classifier; 
 
  public TS14FacadeDouble() { 
    classifier = new FaultyLineTypeClassifierStrategy(); 
  } 
 
  @Override 
  public LineType classify(String newLineToAdd) { 
    return classifier.classify(newLineToAdd); 
  } 
 
  @Override 
  public String generateReport(String[] splitData, String type) { 
    ReportBuilder builder = new StubBuilder(type); 
    LineSequenceState sequenceState = new LineSequenceStateStub(); 
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
