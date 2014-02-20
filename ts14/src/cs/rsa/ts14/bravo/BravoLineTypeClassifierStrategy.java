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

import java.util.ArrayList;
import java.util.Iterator;

import cs.rsa.ts14.framework.LineType;
import cs.rsa.ts14.framework.LineTypeClassifierStrategy;

/**
 * 
 * @author RSA 2014 Group Bravo
 */

public class BravoLineTypeClassifierStrategy implements LineTypeClassifierStrategy {

	LineType lastSeen;
	ArrayList<LineMatch> lines;

	public BravoLineTypeClassifierStrategy() {
		lines = new ArrayList<LineMatch>();
		// Sjovt at når det kodes pattern, så er det match et tegn adgangen, så
		// her er der ikke tale om kode for at håndtere en range!!! giver flere
		// tests
		
		//match for Week specification line:  Week 1 :	3	:	0 
		lines.add(new LineMatch("Week\\s+([1-9]|[1-4][0-9]|[5][0-3])\\s+:\\s+[0-5]\\s+:\\s+[0-5]\\s*", LineType.WEEK_SPECIFICATION));
		
		//match for Assignment line:  HoursOvertime = 502.2 
		lines.add(new LineMatch("\\w+\\s+=\\s+\\d+(.\\d+)?", LineType.ASSIGNMENT_LINE));
		
		//match for Weekday specification line:  Fri		Ca				8.00-16.30
		lines.add(new LineMatch("(Mon|Tue|Wed|Thu|Fri|Sat|Sun)\\s+(Bi|Ca|Pu|Tr|No|Ho).*", LineType.WEEKDAY_SPECIFICATION));
		
		//match for Weekday Work specification line: censor	-		7.5
		lines.add(new LineMatch("\\s+\\D\\w*\\s+-\\s+[0-9](\\.[05])?", LineType.WORK_SPECIFICATION));
		
		//match for Comment: # dette er en kommentar
		lines.add(new LineMatch("#.*", LineType.COMMENT_LINE));
		
		//match for Empty line: "   "
		lines.add(new LineMatch("\\s*", LineType.EMPTY_LINE));
	}

	@Override
	public LineType classify(String line) {
		lastSeen = LineType.INVALID_LINE;
		for (Iterator<LineMatch> iterator = lines.iterator(); iterator.hasNext();) {
			LineMatch lm = iterator.next();
			if (line.matches(lm.pattern)) {
				lastSeen = lm.line;
				break;
			}
		}
		return lastSeen;
	}

	@Override
	public String lastError() {
		if (lastSeen == LineType.INVALID_LINE)
			return "Invalid weekday: Fre";
		return "No error";
	}

	private class LineMatch {
		private String pattern;
		private LineType line;

		private LineMatch(String pattern, LineType line) {
			this.pattern = pattern;
			this.line = line;
		}

	}

}
