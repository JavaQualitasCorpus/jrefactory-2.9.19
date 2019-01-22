/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

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

		suite.addTest(new TestSuite(TestPrettyPrinter.class));
		suite.addTest(new TestSuite(TestPrettyPrinterJDK1_5.class));
		suite.addTest(new TestSuite(TestJavadocTokenizer.class));
		suite.addTest(org.acm.seguin.pretty.line.TestPackage.suite());
		suite.addTest(org.acm.seguin.pretty.ai.TestPackage.suite());
		//suite.addTest(TestPrettyPrinter.suite());
                //suite.addTest(TestPrettyPrinterJDK1_5.suite());

		return suite;
	}
}
