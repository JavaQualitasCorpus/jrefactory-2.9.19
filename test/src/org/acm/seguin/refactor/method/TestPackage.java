/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import junit.framework.*;

/**
 *  Runs all the unit tests in this package
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

		suite.addTest(new TestSuite(TestPushUpMethod.class));
		suite.addTest(new TestSuite(TestPushDownMethod.class));
		suite.addTest(new TestSuite(TestPushUpAbstractMethod.class));
		suite.addTest(new TestSuite(TestMoveMethodRefactoring.class));
		suite.addTest(new TestSuite(TestExtractMethod.class));
		suite.addTest(new TestSuite(TestRenameParameterRefactoring.class));
		suite.addTest(new TestSuite(TestRenameMethodRefactoring.class));

		return suite;
	}
}
