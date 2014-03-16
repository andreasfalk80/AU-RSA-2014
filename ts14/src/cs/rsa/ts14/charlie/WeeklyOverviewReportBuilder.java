package cs.rsa.ts14.charlie;

import java.util.Properties;

import cs.rsa.ts14.framework.ClassType;
import cs.rsa.ts14.standard.ClassMap;
import cs.rsa.ts14.framework.ReportBuilder;

public class WeeklyOverviewReportBuilder implements ReportBuilder {

	private class Week {
		public int workDays = 0;
		public double hours = 0.0;
		public boolean containsRegistration = false;		
	}
	
	private Week[] weeks;
	private int currentWeek = 0;
	private double overtime = 0.0;
	
	public void buildBegin() {
		currentWeek = 0;
		overtime = 0.0;
		weeks = new Week[53];
		for (int i= 0; i < weeks.length; i++) {
			weeks[i] = new Week();
		}

	}

	@Override
	public void buildWeekSpecification(int weekNo, int countWorkdays,
			int countUsedVacationdays) {
		currentWeek = weekNo-1;
		weeks[currentWeek].containsRegistration = true;
		weeks[currentWeek].workDays = countWorkdays;		
	}

	@Override
	public void buildWorkSpecification(String category, String subCategory,
			double hours) {
		ClassType type = ClassMap.mapCategoryToClass(category);
		if (!ClassType.CONSULENT.equals(type)) {
			weeks[currentWeek].hours += hours;
		}
	}

	@Override
	public void buildWeekDaySpecification(String weekDay, String transportMode) {
		//do Nothing
	}

	@Override
	public void buildAssignment(String variable, double value) {
		if (variable.equals("HoursOvertime")) {
			overtime = value;
		}
	}

	@Override
	public void buildEnd() {

	}

	@Override
	public String getResult() {
		StringBuffer output = new StringBuffer();
		output.append("=== Week Overview ===");
    output.append(System.getProperty("line.separator"));
		double delta = overtime;
		for (int i=0; i < weeks.length; i++) {
			if (weeks[i].containsRegistration) {
				double average = 0.0;
				if (weeks[i].workDays > 0) {
					average = (weeks[i].hours / weeks[i].workDays);
				}
				delta = delta + (weeks[i].hours - (37.0*(weeks[i].workDays/5.0)));
				output.append(String.format("Week %3d : %6.1f hours   ( %2d Wdays of %5.1f  d=%3.1f)", 
				        (i+1), weeks[i].hours, weeks[i].workDays, average, delta));
        output.append(System.getProperty("line.separator"));
			}
		}
		return output.toString();
	} 
	

}
