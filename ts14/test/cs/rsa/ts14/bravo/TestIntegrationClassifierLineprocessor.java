package cs.rsa.ts14.bravo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cs.rsa.ts14.doubles.LineSequenceStateStub;
import cs.rsa.ts14.doubles.ReportBuilderStub;
import cs.rsa.ts14.framework.TimesagLineProcessor;
import cs.rsa.ts14.standard.StandardTimesagLineProcessor;

public class TestIntegrationClassifierLineprocessor {
	TimesagLineProcessor tlp;

	@Before
	public void setUp() throws Exception {
		tlp = new StandardTimesagLineProcessor(new BravoLineTypeClassifierStrategy(), new ReportBuilderStub(), new LineSequenceStateStub());

	}

	@Test
	public void testInvalidLineExpectingError() {
		tlp.beginProcess();
		tlp.process("This is not a valid line");
		tlp.endProcess();
		assertEquals("Error: last line was invalid", tlp.lastError());

	}

	@Test
	public void testValidLineExpectingNoError() {
		tlp.beginProcess();
		tlp.process("Week 1 :	3	:	0");
		tlp.endProcess();
		assertEquals("No error", tlp.lastError());
	}

}
