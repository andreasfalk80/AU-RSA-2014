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
	// matcher 1-53
	private static String weekNumbersPattern = "([1-9]|[1-4][0-9]|[5][0-3])";
	//matcher a-z ikke casesensitive
	private static String lettersPattern = "[a-zA-Z]";
	//matcher vilkårligt tal, hvor decimalpoint og et ciffer efter er valgfrit. Ingen grænse for antal cifre før decimalpoint
	private static String decimal_n_1_Pattern = "(\\d+|0)(\\.\\d+)?"; //n angiver antal før 1 antal efter decimalpoint 
	//matcher kort notation for ugedage 
	private static String weekdaysShortPattern = "(Mon|Tue|Wed|Thu|Fri|Sat|Sun)"; 
	//matcher ord der starter med et bogstav, og derefter kan indeholde hvad som helst 
	private static String wordsStartingWithLetterPattern = lettersPattern+"\\w*"; 
	//matcher  0-24 i intervaller på 0.5. mind 0.5 maks 24 Eksempler: , 0.5 , 1.5 , 5.0 , 5 , 23.5 , 24 , 24.0
	private static String everyHalfHourInADayPattern = "((0\\.5)|(([1-9]|1[0-9]|2[0-3])(\\.[05])?)|(24(\\.[0])?))"; 
	
	
	LineType lastSeen;
	ArrayList<LineMatch> lines;

	public BravoLineTypeClassifierStrategy() {
		lines = new ArrayList<LineMatch>();
		
		//match for Week specification line:  Week 1 :	3	:	0 
		lines.add(new LineMatch("^Week\\s+"+weekNumbersPattern+"\\s+:\\s+[0-5]\\s+:\\s+[0-5]$", LineType.WEEK_SPECIFICATION));
		
		//match for Assignment line:  HoursOvertime = 502.2 
		lines.add(new LineMatch("^"+lettersPattern+"+\\s+=\\s+"+decimal_n_1_Pattern+"$", LineType.ASSIGNMENT_LINE));
		
		//match for Weekday specification line:  Fri		Ca				8.00-16.30
		lines.add(new LineMatch("^"+weekdaysShortPattern+"\\s+(Bi|Ca|Pu|Tr|No|Ho).*$", LineType.WEEKDAY_SPECIFICATION));
		
		//match for Work specification line: censor	-		7.5
		lines.add(new LineMatch("^\\s+"+wordsStartingWithLetterPattern+"\\s+(-|"+wordsStartingWithLetterPattern+")\\s+"+everyHalfHourInADayPattern+"$", LineType.WORK_SPECIFICATION));
		
		//match for Comment: # dette er en kommentar
		lines.add(new LineMatch("^#.*$", LineType.COMMENT_LINE));
		
		//match for Empty line: "   "
		lines.add(new LineMatch("^\\s*$", LineType.EMPTY_LINE));
	}

	@Override
	public LineType classify(String line) {
		
		//System.out.printf("classifying %s\n", line);
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
			return "Error: last line was invalid";
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
