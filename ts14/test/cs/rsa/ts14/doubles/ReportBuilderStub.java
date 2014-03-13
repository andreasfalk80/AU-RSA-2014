package cs.rsa.ts14.doubles;

import cs.rsa.ts14.framework.*;

/** Stub implementation of ReportBuilder that accepts
 * all buildcommands, but doesn't do anything with it
 * 
 * getResult returns the String "Result"
 *
 */
public class ReportBuilderStub implements ReportBuilder {

	@Override
	public void buildBegin() {
		
	}

	@Override
	public void buildWeekSpecification(int weekNo, int countWorkdays, int countUsedVacationdays) {
		
	}

	@Override
	public void buildWorkSpecification(String category, String subCategory, double hours) {
		
	}

	@Override
	public void buildWeekDaySpecification(String weekDay, String transportMode) {
		
	}

	@Override
	public void buildAssignment(String variable, double value) {
		
	}

	@Override
	public void buildEnd() {
		
	}

	@Override
	public String getResult() {
		return "Result";
	}
}
