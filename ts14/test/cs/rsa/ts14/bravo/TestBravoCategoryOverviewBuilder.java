package cs.rsa.ts14.bravo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cs.rsa.ts14.framework.ReportBuilder;

public class TestBravoCategoryOverviewBuilder {

	private ReportBuilder builder;

	@Before
	public void setup() {
		builder = new BravoCategoryOverviewBuilder();
		builder.buildBegin();
	}

	@Test
	public void mainTestHappyPath() {
		builder.buildWorkSpecification("saip", "-", 5);
		builder.buildWorkSpecification("sa", "-", 8.5);
		builder.buildWorkSpecification("book2", "-", 4);
		builder.buildEnd();
		System.err.println(builder.getResult());
		assertEquals(
				"-- Time spent on classes and categories --\nteaching                  13.5 ( 77%)\n    saip     :     5.0\n    censor   :     0.0\n    sa       :     8.5\n    mtt      :     0.0\nresearch                   4.0 ( 23%)\n    es       :     0.0\n    book2    :     4.0\n    n4c      :     0.0\nmisc                       0.0 (  0%)\n    syg      :     0.0\nconsulent                  0.0 (  0%)\n    terna    :     0.0\nadm                        0.0 (  0%)\n    itevmd   :     0.0\n    adm      :     0.0\nTotal:                    17.5 (17.5)\n                          ===============\n",
				builder.getResult());

	}
	
	@Test
	public void CheckClassConsulentNotInSum() {
		builder.buildWorkSpecification("saip", "-", 5);
		builder.buildWorkSpecification("terna", "-", 8.5);
		builder.buildEnd();
		System.err.println(builder.getResult());
		assertEquals(
				"-- Time spent on classes and categories --\nteaching                   5.0 (100%)\n    saip     :     5.0\n    censor   :     0.0\n    sa       :     0.0\n    mtt      :     0.0\nresearch                   0.0 (  0%)\n    es       :     0.0\n    book2    :     0.0\n    n4c      :     0.0\nmisc                       0.0 (  0%)\n    syg      :     0.0\nconsulent                  8.5 (170%)\n    terna    :     8.5\nadm                        0.0 (  0%)\n    itevmd   :     0.0\n    adm      :     0.0\nTotal:                     5.0 (13.5)\n                          ===============\n",
				builder.getResult());

	}
}
