package org.acm.seguin.pretty.line;

import junit.framework.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestPackage {
	/**
	 *  A suite of unit tests for JUnit
	 *
	 *@return    The test suite
	 */
	public static TestSuite suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new TestSuite(LineNumberingTest.class));
		return suite;
	}
}
