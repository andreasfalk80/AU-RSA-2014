package cs.rsa.ts14.bravo;

import cs.rsa.ts14.charlie.*;
import cs.rsa.ts14.delta.*;

import org.junit.*;
import org.mockito.InOrder;

import java.util.Properties;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.*;
import org.apache.commons.io.*;

import cs.rsa.ts14.framework.*;
import cs.rsa.ts14.standard.*;
import cs.rsa.ts14.bravo.*;
import cs.rsa.ts14.Golf.statemachine.*;

public class TopDownIntegrationTestWithMocks {
  @Before
  public void setup() {
  }

  @Test
  public void topDownIntegrationTestStage1() {
    
    // Instead of defining stubs and spies, we ask
    // the Mockito library to genereate mock objects
    TimesagLineProcessor tlpMock = mock(TimesagLineProcessor.class);
    when(tlpMock.lastError()).thenReturn("No error");
    when(tlpMock.getReport()).thenReturn("tlpMock dummy report");
    
    File file = new File("resource/timesag.txt");

    String resultString = new String("");
    
    try {
      TimesagEngine timesagEngineUnderTest = new TimesagEngine();
      resultString = timesagEngineUnderTest.getTimesagReport(file, tlpMock);
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
  
    verify(tlpMock).beginProcess();
    verify(tlpMock).process("# Timesag system for NN 2013");
    verify(tlpMock).process("HoursOvertime = 502.2");
    verify(tlpMock).process("Week 1 :	3	:	0");
    verify(tlpMock).process("Wed    		Ca				8.00");
    verify(tlpMock).process("	sa		exam		3	forb question");
    verify(tlpMock).endProcess();
    verify(tlpMock).lastError();
    verify(tlpMock).getReport();
    assertEquals(resultString, "tlpMock dummy report");
  }

  @Test
  public void topDownIntegrationTestStage1Error() {
    
    // Instead of defining stubs and spies, we ask
    // the Mockito library to genereate mock objects
    TimesagLineProcessor tlpMock = mock(TimesagLineProcessor.class);
    when(tlpMock.lastError()).thenReturn("Integration test error");
    when(tlpMock.getReport()).thenReturn("tlpMock dummy report");
    
    File file = new File("resource/timesag.txt");

    String resultString = new String("");
    
    try {
      TimesagEngine timesagEngineUnderTest = new TimesagEngine();
      resultString = timesagEngineUnderTest.getTimesagReport(file, tlpMock);
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    assertEquals(resultString, "Error in input: Integration test error");
  }

  @Test
  public void topDownIntegrationTestStage2() {
      
    // Instead of defining stubs and spies, we ask
    // the Mockito library to genereate mock objects
    ReportBuilder builderMock = mock(ReportBuilder.class);
    when(builderMock.getResult()).thenReturn("builderMock dummy report");
    
    File file = new File("resource/timesag.txt");

    String resultString = new String("");

    TimesagLineProcessor tlp = 
        new StandardTimesagLineProcessor( 
            new BravoLineTypeClassifierStrategy(),
            builderMock, 
            new InitialState());
    
    try {
      TimesagEngine engine = new TimesagEngine();
      resultString = engine.getTimesagReport(file, tlp);
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    verify(builderMock).buildBegin();
    verify(builderMock).buildWeekSpecification(1, 3, 0);
    verify(builderMock).buildWorkSpecification("sa", "hotciv", 1.0);
    verify(builderMock).buildWeekDaySpecification("Fri", "Ho");
    verify(builderMock).buildAssignment("Year", 2013.0);
    verify(builderMock).buildEnd();
    verify(builderMock).getResult();
    assertEquals(resultString, "builderMock dummy report");
  }

  @Test
  public void topDownIntegrationTestStage3CharlieWeeklyOverview() {
    String testOracleString = new String(
"=== Week Overview ==="+System.getProperty("line.separator")+
"Week   1 :   22.0 hours   (  3 Wdays of   7.3  d=502.0)"+System.getProperty("line.separator")+
"Week   2 :   40.5 hours   (  5 Wdays of   8.1  d=505.5)"+System.getProperty("line.separator")+
"Week   3 :    7.0 hours   (  5 Wdays of   1.4  d=475.5)"+System.getProperty("line.separator"));

    String testResultString = new String("");

    File file = new File("resource/timesag.txt");
    ReportBuilder builder = new WeeklyOverviewReportBuilder(); // charlie
    TimesagLineProcessor tlp = 
        new StandardTimesagLineProcessor( 
            new BravoLineTypeClassifierStrategy(),
            builder, 
            new InitialState());
    
    try {
      TimesagEngine engine = new TimesagEngine();
      testResultString = engine.getTimesagReport(file, tlp);
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    assertEquals(testOracleString, testResultString);
  }

  @Test
  public void topDownIntegrationTestStage3DeltaTransportOverview() {
    String testOracleString = new String(
"-------------------------------------------------------------------------"+System.getProperty("line.separator")+
"| Car:     7     Bicycle:    0     Public:     0    Traveling:    0     |"+System.getProperty("line.separator")+
"| Home:    1    NonWDays:    2     Unknown:    0                        |"+System.getProperty("line.separator")+
"-------------------------------------------------------------------------"+System.getProperty("line.separator"));

    String testResultString = new String("");
    
    File file = new File("resource/timesag.txt");
    ReportBuilder builder = new TransportReportBuilder(); //delta
    TimesagLineProcessor tlp = 
        new StandardTimesagLineProcessor( 
            new BravoLineTypeClassifierStrategy(),
            builder, 
            new InitialState());
    
    try {
      TimesagEngine engine = new TimesagEngine();
      testResultString = engine.getTimesagReport(file, tlp);
    } catch (IOException e) {
      System.err.println("Caught IOException: " + e.getMessage());
    }
    assertEquals(testOracleString, testResultString);
  }
}
