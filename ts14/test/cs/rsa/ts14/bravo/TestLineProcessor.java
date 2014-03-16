package cs.rsa.ts14.bravo;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs.rsa.ts14.Golf.statemachine.AssignmentState;
import cs.rsa.ts14.Golf.statemachine.CommentState;
import cs.rsa.ts14.Golf.statemachine.EmptyState;
import cs.rsa.ts14.Golf.statemachine.IllegalState;
import cs.rsa.ts14.Golf.statemachine.InitialState;
import cs.rsa.ts14.Golf.statemachine.WeekDayState;
import cs.rsa.ts14.Golf.statemachine.WeekState;
import cs.rsa.ts14.Golf.statemachine.WorkState;
import cs.rsa.ts14.doubles.ReportBuilderStub;
import cs.rsa.ts14.framework.TimesagLineProcessor;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;

public class TestLineProcessor {

	private static String ASSIGNMENT_LINE = "HoursOvertime = 502.2";
	private static String COMMENT_LINE = "# ";
	private static String WEEK_SPECIFICATION = "Week 1 : 3 : 0";
	private static String WEEKDAY_SPECIFICATION = "Mon Bi 8.00-16.30";
	private static String WORK_SPECIFICATION = "  censor    aau 7";
	private static String EMPTY_LINE = "";
	
	TimesagLineProcessor tlp;

	@Before
	public void setup() {
		tlp = new StandardTimesagLineProcessor(
				new BravoLineTypeClassifierStrategy(),
				new ReportBuilderStub(), new InitialState());
		tlp.beginProcess();
	}

	@Test
	public void shouldHandleTypicalValidSequencePartially() {
		Assert.assertTrue(tlp.getState() instanceof InitialState);

		// Can transition to assignment
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof AssignmentState);

		// Can transition to comment
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);
		
		// Accept empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);

		// Can transition to Week
		tlp.process(WEEK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekState);

		// On to Weekday
		tlp.process(WEEKDAY_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekDayState);

		// On to a work line
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WorkState);

	}

	@Test
	public void shouldNotTransitionFromWeekToAssignment() {
		// set the state to Week
		tlp.process(WEEK_SPECIFICATION);

		// Try transition into assignment
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);
		assertEquals("Illegal to have assignment after week line.",
				tlp.getState().lastError());
	}

	/*@Test
	public void shouldNotTransitionFromWeekDayToAssignment() {
		tlp.process(WEEK_SPECIFICATION);
		
		// set the state to Week
		tlp.process(WEEKDAY_SPECIFICATION);

		// Try transition into assignment
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);
		assertEquals("Illegal to have assignment after weekday line.",
				tlp.getState().lastError());
	}

	@Test
	public void shouldNotTransitionFromWorkToAssignment() {
		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		// set the state to Work
		tlp.process(WORK_SPECIFICATION);

		// Try transition into assignment
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);
		assertEquals("Illegal to have assignment after work line.",
				tlp.getState().lastError());
	}

	@Test
	public void shouldAllowOneEmptyLines() {
		// Accept 1st empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);

	}

	@Test
	public void shouldAllowTwoEmptyLines() {
		// Accept 1st empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);

		// Accept 2nd empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);

	}

	@Test
	public void shouldNotAllowThreeEmptyLines() {
		// Accept 1st empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);

		// Accept 2nd empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);

		// Reject 3rd empty line
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);
		assertEquals("Illegal to have three consecutive empty lines.",
				tlp.getState().lastError());
	}

	// Test for transitions from assignmentstate

	@Test
	public void shouldAllowWeekFromAssignment() {

		tlp.process(ASSIGNMENT_LINE);
		tlp.process(WEEK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekState);

	}

	@Test
	public void shouldAllowAssignmentFromAssignment() {

		tlp.process(ASSIGNMENT_LINE);
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof AssignmentState);
	}

	@Test
	public void shouldAllowEmptylineFromAssignment() {

		tlp.process(ASSIGNMENT_LINE);
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);
	}

	@Test
	public void shouldNotAllowWeekdayFromAssignment() {

		tlp.process(ASSIGNMENT_LINE);
		tlp.process(WEEKDAY_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);

	}

	@Test
	public void shouldNotAllowWorkFromAssignment() {

		tlp.process(ASSIGNMENT_LINE);
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);

	}

	// Test for transitions from weekstate

	@Test
	public void shouldAllowCommentFromWeek() {

		tlp.process(WEEK_SPECIFICATION);
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);
	}

	@Test
	public void shouldAllowEmptyFromWeek() {

		tlp.process(WEEK_SPECIFICATION);
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);
	}

	@Test
	public void shouldNotAllowWorkFromWeek() {

		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);

	}

	// Test for transitions from weekdaystate

	@Test
	public void shouldAllowCommentFromWeekday() {
		tlp.process(WEEK_SPECIFICATION);
		
		tlp.process(WEEKDAY_SPECIFICATION);
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);
	}

	@Test
	public void shouldAllowEmptyFromWeekday() {
		tlp.process(WEEK_SPECIFICATION);
		
		tlp.process(WEEKDAY_SPECIFICATION);
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);
	}

	// Test for transitions from workstate

	@Test
	public void shouldAllowCommentFromWork() {
		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		
		tlp.process(WORK_SPECIFICATION);
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);
	}

	@Test
	public void shouldAllowEmptyFromWork() {
		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		
		tlp.process(WORK_SPECIFICATION);
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);
	}

	@Test
	public void shouldAllowWeekFromWork() {
		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		
		tlp.process(WORK_SPECIFICATION);
		tlp.process(WEEK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekState);
	}

	@Test
	public void shouldAllowWeekdayFromWork() {
		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		
		tlp.process(WORK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekDayState);
	}

	@Test
	public void shouldAllowWorkFromWork() {
		tlp.process(WEEK_SPECIFICATION);
		tlp.process(WEEKDAY_SPECIFICATION);
		
		tlp.process(WORK_SPECIFICATION);
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WorkState);
	}

	// Test for transitions from commentstate

	@Test
	public void shouldAllowCommentFromComment() {

		tlp.process(COMMENT_LINE);
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);

	}

	@Test
	public void shouldAllowAssignmentFromComment() {

		tlp.process(COMMENT_LINE);
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof AssignmentState);
	}

	@Test
	public void shouldAllowEmptyFromComment() {

		tlp.process(COMMENT_LINE);
		tlp.process(EMPTY_LINE);
		Assert.assertTrue(tlp.getState() instanceof EmptyState);
	}

	@Test
	public void shouldAllowWeekdayFromComment() {

		tlp.process(COMMENT_LINE);
		tlp.process(WEEKDAY_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekDayState);
	}

	@Test
	public void shouldAllowWorkFromComment() {

		tlp.process(COMMENT_LINE);
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WorkState);
	}

	// Test for transitions from intialstate

	@Test
	public void shouldAllowCommentFromInitial() {

		setup();
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);
	}

	@Test
	public void shouldAllowWeekFromInitial() {

		setup();
		tlp.process(WEEK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekState);
	}

	@Test
	public void shouldNotAllowWeekdayFromInitial() {
		setup();
		tlp.process(WEEKDAY_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);

	}

	@Test
	public void shouldNotAllowWorkFromInitial() {
		setup();
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof IllegalState);
	}

	// Test for transitions from emptystate

	@Test
	public void shouldAllowAssignFromEmpty() {
		
		tlp.process(EMPTY_LINE);
		tlp.process(ASSIGNMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof AssignmentState);
	}

	@Test
	public void shouldAllowCommentFromEmpty() {

		tlp.process(EMPTY_LINE);
		tlp.process(EMPTY_LINE);
		tlp.process(COMMENT_LINE);
		Assert.assertTrue(tlp.getState() instanceof CommentState);
	}

	@Test
	public void shouldAllowWeekFromEmpty() {

		tlp.process(EMPTY_LINE);
		tlp.process(WEEK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekState);

	}

	@Test
	public void shouldAllowWeekDayFromEmpty() {

		tlp.process(EMPTY_LINE);
		tlp.process(WEEKDAY_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WeekDayState);

	}

	@Test
	public void shouldAllowWorkFromEmpty() {

		tlp.process(EMPTY_LINE);
		tlp.process(WORK_SPECIFICATION);
		Assert.assertTrue(tlp.getState() instanceof WorkState);

	}*/
}
