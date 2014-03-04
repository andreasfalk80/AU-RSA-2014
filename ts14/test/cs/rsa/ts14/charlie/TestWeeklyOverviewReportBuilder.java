package cs.rsa.ts14.charlie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestWeeklyOverviewReportBuilder {

	 private WeeklyOverviewReportBuilder builder;

	 @Before
	  public void setup() {
		  builder = new WeeklyOverviewReportBuilder();
	  }
	  
	  @Test
	  public void testFormatting() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 0, 0); 
		  builder.buildEnd();
		  String result = builder.getResult();
		  assertTrue(result.startsWith("=== Week Overview ==="));
		  String expectedLine = String.format("Week %3d : %6.1f hours   ( %2d Wdays of %5.1f  d=%3.1f)", 
			        1,0.0,0,0.0,0.0);
		  assertTrue(result.contains(expectedLine));
	  }
	  
	  @Test
	  public void testExpectedWeekLines() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(4, 0, 0); 
		  builder.buildWeekSpecification(6, 0, 0); 
		  builder.buildEnd();  
		  String result = builder.getResult();
		  String expectedWeek4 =    "Week   4";
		  String notExpectedWeek5 = "Week   5";
		  String expectedWeek6 =    "Week   6";		
		  assertTrue(result.contains(expectedWeek4));
		  assertFalse(result.contains(notExpectedWeek5));
		  assertTrue(result.contains(expectedWeek6));		  
	  }
	  
	  @Test
	  public void testExpectedHourSum() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 1, 0); 
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 2.5);
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedWork = String.format("%6.1f hours",7.5);
		  assertTrue(result.contains(expectedWork));
	  }
	  
	  @Test
	  public void testExpectedHourSumExcludesConsulting() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 1, 0); 
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("terna", "work", 2.5);
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedWork = String.format("%6.1f hours",5.0);
		  assertTrue(result.contains(expectedWork));
	  }
	  
	  @Test
	  public void testExpectedWeekdays() {	
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 3, 0); 
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedWdays = "3 Wdays";
		  assertTrue(result.contains(expectedWdays));
	  }
	  
	  @Test
	  public void testExpectedAverage() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 3, 0); 
		  builder.buildWorkSpecification("work", "work", 3.0);
		  builder.buildWorkSpecification("work", "work", 4.5);
		  builder.buildWorkSpecification("work", "work", 5.5);
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedAverage = String.format("of %5.1f", 4.3);
		  assertTrue(result.contains(expectedAverage));
	  }
	  
	  @Test
	  public void testExpectedDelta() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 3, 0); 
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedDelta = String.format("d=%3.1f", -7.2);
		  assertTrue(result.contains(expectedDelta));
	  }
	  
	  @Test
	  public void testExpectedDeltaWithHoursOvertime() {
		  builder.buildBegin();
		  builder.buildAssignment("HoursOvertime", 10.0);
		  builder.buildWeekSpecification(1, 3, 0); 
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedDelta = String.format("d=%3.1f", 2.8);
		  assertTrue(result.contains(expectedDelta));
	  }
	  
	  @Test
	  public void testExpectedDeltaAddedFromPreviousWeek() {
		  builder.buildBegin();
		  builder.buildWeekSpecification(1, 3, 0); 
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWeekSpecification(2, 3, 0); 
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildWorkSpecification("work", "work", 5.0);
		  builder.buildEnd();
		  String result = builder.getResult();
		  String expectedDelta1 = String.format("d=%3.1f", -7.2);
		  String expectedDelta2 = String.format("d=%3.1f", -14.4);
		  assertTrue(result.contains(expectedDelta1));
		  assertTrue(result.contains(expectedDelta2));
		  assertTrue(result.indexOf(expectedDelta1) < result.indexOf(expectedDelta2));
	  }
}
