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
	 * This method uses the builder to create output for a specific testcase called happyPath 
	 *  	
	 */
		private void buildHappyPath(){
			builder.buildWorkSpecification("saip", "-", 500);
			builder.buildWorkSpecification("sa", "-", 8.5);
			builder.buildWorkSpecification("book2", "-", 4);
			builder.buildWorkSpecification("book2", "-", 6);
			builder.buildEnd();
//			System.err.println(builder.getResult());
		}
	
	@Test
	public void TestHappyPathLine1() {
		buildHappyPath();
		assertEquals("-- Time spent on classes and categories --",getBuilderResultLine(1));
	}
	
	@Test
	public void TestHappyPathLine2() {
		buildHappyPath();
		assertEquals("teaching                 508.5 ( 98%)",getBuilderResultLine(2));
		             
	}

	@Test
	public void TestHappyPathLine3() {
		buildHappyPath();
		assertEquals("    saip     :   500.0",getBuilderResultLine(3));
	}

	@Test
	public void TestHappyPathLine4() {
		buildHappyPath();
		assertEquals("    censor   :     0.0",getBuilderResultLine(4));
	}

	@Test  
	public void TestHappyPathLine5() {
		buildHappyPath();
		assertEquals("    sa       :     8.5",getBuilderResultLine(5));
	}

	@Test
	public void TestHappyPathLine6() {
		buildHappyPath();
		assertEquals("    mtt      :     0.0",getBuilderResultLine(6));
	}
	
	@Test
	public void TestHappyPathLine7() {
		buildHappyPath();
		assertEquals("research                  10.0 (  2%)",getBuilderResultLine(7));
	}

	@Test
	public void TestHappyPathLine8() {
		buildHappyPath();
		assertEquals("    es       :     0.0",getBuilderResultLine(8));
	}

	@Test
	public void TestHappyPathLine9() {
		buildHappyPath();
		assertEquals("    book2    :    10.0",getBuilderResultLine(9));
	}

	@Test
	public void TestHappyPathLine10() {
		buildHappyPath();
		assertEquals("    n4c      :     0.0",getBuilderResultLine(10));
	}

	@Test
	public void TestHappyPathLine11() {
		buildHappyPath();
		assertEquals("misc                       0.0 (  0%)",getBuilderResultLine(11));
	}

	@Test
	public void TestHappyPathLine12() {
		buildHappyPath();
		assertEquals("    syg      :     0.0",getBuilderResultLine(12));
	}

	@Test
	public void TestHappyPathLine13() {
		buildHappyPath();
		assertEquals("consulent                  0.0 (  0%)",getBuilderResultLine(13));
	}

	@Test
	public void TestHappyPathLine14() {
		buildHappyPath();
		assertEquals("    terna    :     0.0",getBuilderResultLine(14));
	}

	@Test
	public void TestHappyPathLine15() {
		buildHappyPath();
		assertEquals("adm                        0.0 (  0%)",getBuilderResultLine(15));
	}

	@Test
	public void TestHappyPathLine16() {
		buildHappyPath();
		assertEquals("    itevmd   :     0.0",getBuilderResultLine(16));
	}

	@Test
	public void TestHappyPathLine17() {
		buildHappyPath();
		assertEquals("    adm      :     0.0",getBuilderResultLine(17));
	}

	@Test
	public void TestHappyPathLine18() {
		buildHappyPath();
		assertEquals("Total:                   518.5 (518.5)",getBuilderResultLine(18));
	}

	@Test
	public void TestHappyPathLine19() {
		buildHappyPath();
		assertEquals("                          ===============",getBuilderResultLine(19));
	}

	
	@Test
	public void TestConsulentNotInSum() {
		builder.buildWorkSpecification("saip", "-", 5);
		builder.buildWorkSpecification("terna", "-", 8.5);
		builder.buildEnd();
	//	System.err.println(builder.getResult());
		assertEquals("Total:                     5.0 (13.5)",getBuilderResultLine(18)); 
	}

	
	@Test
	public void TestInvalidCategory() {
		builder.buildWorkSpecification("invalid", "-", 1);
		builder.buildEnd();
//		System.err.println(builder.getResult());
		assertEquals("Unknown category found: \"invalid\". The record was ignored.",getBuilderResultLine(20));

	}
	
		@Test
	public void TestNegativeHourValue() {
		builder.buildWorkSpecification("saip", "-", -5);
		builder.buildEnd();
		//System.err.println(builder.getResult());
		assertEquals("Illegal value for hours found: \"-5.0\". The record was ignored.",getBuilderResultLine(20)); 

	}


		@Test
	public void TestAllCategories() {
		builder.buildWorkSpecification("saip", "-", 1);
		builder.buildWorkSpecification("censor", "-", 2);
		builder.buildWorkSpecification("sa", "-", 3);
		builder.buildWorkSpecification("mtt", "-", 4);
		builder.buildWorkSpecification("es", "-", 5);
		builder.buildWorkSpecification("book2", "-", 6);
		builder.buildWorkSpecification("n4c", "-", 7);
		builder.buildWorkSpecification("syg", "-", 8);
		builder.buildWorkSpecification("terna", "-", 9);
		builder.buildWorkSpecification("itevmd", "-", 10);
		builder.buildWorkSpecification("adm", "-", 11);
		builder.buildEnd();
	//	System.err.println(builder.getResult());
		assertEquals("    saip     :     1.0",getBuilderResultLine(3)); 
		assertEquals("    censor   :     2.0",getBuilderResultLine(4));
		assertEquals("    sa       :     3.0",getBuilderResultLine(5));
		assertEquals("    mtt      :     4.0",getBuilderResultLine(6));
		assertEquals("    es       :     5.0",getBuilderResultLine(8));
		assertEquals("    book2    :     6.0",getBuilderResultLine(9));
		assertEquals("    n4c      :     7.0",getBuilderResultLine(10));
		assertEquals("    syg      :     8.0",getBuilderResultLine(12));
		assertEquals("    terna    :     9.0",getBuilderResultLine(14));
		assertEquals("    itevmd   :    10.0",getBuilderResultLine(16));
		assertEquals("    adm      :    11.0",getBuilderResultLine(17));
	}

		@Test
	public void TestNoWorkSpecifications() {
		builder.buildEnd();
		assertEquals("teaching                   0.0 (  0%)",getBuilderResultLine(2));
		assertEquals("    saip     :     0.0",getBuilderResultLine(3)); 
		assertEquals("    censor   :     0.0",getBuilderResultLine(4));
		assertEquals("    sa       :     0.0",getBuilderResultLine(5));
		assertEquals("    mtt      :     0.0",getBuilderResultLine(6));
		assertEquals("research                   0.0 (  0%)",getBuilderResultLine(7));
		assertEquals("    es       :     0.0",getBuilderResultLine(8));
		assertEquals("    book2    :     0.0",getBuilderResultLine(9));
		assertEquals("    n4c      :     0.0",getBuilderResultLine(10));
		assertEquals("misc                       0.0 (  0%)",getBuilderResultLine(11));
		assertEquals("    syg      :     0.0",getBuilderResultLine(12));
		assertEquals("consulent                  0.0 (  0%)",getBuilderResultLine(13));
		assertEquals("    terna    :     0.0",getBuilderResultLine(14));
		assertEquals("adm                        0.0 (  0%)",getBuilderResultLine(15));
		assertEquals("    itevmd   :     0.0",getBuilderResultLine(16));
		assertEquals("    adm      :     0.0",getBuilderResultLine(17));
		assertEquals("Total:                     0.0 (0.0)",getBuilderResultLine(18));

	}
		
		
		
	 /** Split a line into an array of tokens,
	   * @param line the line to split
	   * @param delim is the delimiter
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
	  
	  /**
	   * 
	   * @param linenumber
	   * @return the content of the specified line from the builder
	   */
		private String getBuilderResultLine(int linenumber){
			return buildTokenArray(builder.getResult(),"\n")[linenumber-1];
		}
	
}
