package cs.rsa.ts14.bravo;

import static org.junit.Assert.assertEquals;

import java.util.StringTokenizer;

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

/**
 * This method uses the builder to create output, and then returns the requested line from that output
 * @param linenumber the line from the output of the builder to return (indexed from 1)
 * @return Contents of the line from the builder output 	
 */
	private String happyPathLine(int linenumber){
		builder.buildWorkSpecification("saip", "-", 500);
		builder.buildWorkSpecification("sa", "-", 8.5);
		builder.buildWorkSpecification("book2", "-", 4);
		builder.buildWorkSpecification("book2", "-", 6);
		builder.buildEnd();
//		System.err.println(builder.getResult());
		return buildTokenArray(builder.getResult(),"\n")[linenumber-1];
	}

	@Test
	public void TestHappyPathLine1() {
		assertEquals("-- Time spent on classes and categories --",happyPathLine(1));
	}
	
	@Test
	public void TestHappyPathLine2() {
		assertEquals("teaching                 508.5 ( 98%)",happyPathLine(2));
		             
	}

	@Test
	public void TestHappyPathLine3() {
		assertEquals("    saip     :   500.0",happyPathLine(3));
	}

	@Test
	public void TestHappyPathLine4() {
		assertEquals("    censor   :     0.0",happyPathLine(4));
	}

	@Test  
	public void TestHappyPathLine5() {
		assertEquals("    sa       :     8.5",happyPathLine(5));
	}

	@Test
	public void TestHappyPathLine6() {
		assertEquals("    mtt      :     0.0",happyPathLine(6));
	}
	
	@Test
	public void TestHappyPathLine7() {
		assertEquals("research                  10.0 (  2%)",happyPathLine(7));
	}

	@Test
	public void TestHappyPathLine8() {
		assertEquals("    es       :     0.0",happyPathLine(8));
	}

	@Test
	public void TestHappyPathLine9() {
		assertEquals("    book2    :    10.0",happyPathLine(9));
	}

	@Test
	public void TestHappyPathLine10() {
		assertEquals("    n4c      :     0.0",happyPathLine(10));
	}

	@Test
	public void TestHappyPathLine11() {
		assertEquals("misc                       0.0 (  0%)",happyPathLine(11));
	}

	@Test
	public void TestHappyPathLine12() {
		assertEquals("    syg      :     0.0",happyPathLine(12));
	}

	@Test
	public void TestHappyPathLine13() {
		assertEquals("consulent                  0.0 (  0%)",happyPathLine(13));
	}

	@Test
	public void TestHappyPathLine14() {
		assertEquals("    terna    :     0.0",happyPathLine(14));
	}

	@Test
	public void TestHappyPathLine15() {
		assertEquals("adm                        0.0 (  0%)",happyPathLine(15));
	}

	@Test
	public void TestHappyPathLine16() {
		assertEquals("    itevmd   :     0.0",happyPathLine(16));
	}

	@Test
	public void TestHappyPathLine17() {
		assertEquals("    adm      :     0.0",happyPathLine(17));
	}

	@Test
	public void TestHappyPathLine18() {
		
		assertEquals("Total:                   518.5 (518.5)",happyPathLine(18));
	}

	@Test
	public void TestHappyPathLine19() {
		happyPathLine(19);
		assertEquals("                          ===============",happyPathLine(19));
	}

	
	@Test
	public void CheckClassConsulentNotInSum() {
		builder.buildWorkSpecification("saip", "-", 5);
		builder.buildWorkSpecification("terna", "-", 8.5);
		builder.buildEnd();
	//	System.err.println(builder.getResult());
		assertEquals(
				"-- Time spent on classes and categories --\nteaching                   5.0 (100%)\n    saip     :     5.0\n    censor   :     0.0\n    sa       :     0.0\n    mtt      :     0.0\nresearch                   0.0 (  0%)\n    es       :     0.0\n    book2    :     0.0\n    n4c      :     0.0\nmisc                       0.0 (  0%)\n    syg      :     0.0\nconsulent                  8.5 (170%)\n    terna    :     8.5\nadm                        0.0 (  0%)\n    itevmd   :     0.0\n    adm      :     0.0\nTotal:                     5.0 (13.5)\n                          ===============\n",
				builder.getResult());

	}
	
	//@Test   /* for lige at kunne se outputtet*/ 
	public void Test() {
		builder.buildWorkSpecification("test", "-", 5);
		//builder.buildWorkSpecification("terna", "-", 8.5);
		builder.buildEnd();
		System.err.println(builder.getResult());
	}
	
	
	 /** Split a line into an array of tokens,
	   * whitespace is delimiter.
	   * @param line the line to split
	   * @return array of tokens in the line
	   */
	  private String[] buildTokenArray(String line,String delim) {
	    String[] tokenList;
	    StringTokenizer tokenizer = new StringTokenizer(line,delim);
	    tokenList = new String[ tokenizer.countTokens() ];
	    int i = 0;
	    while(tokenizer.hasMoreTokens()){
	      tokenList[i] = tokenizer.nextToken();
	      i++;
	    }
	    return tokenList;
	  }
}
