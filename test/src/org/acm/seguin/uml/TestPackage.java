/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.uml;

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

		suite.addTest(org.acm.seguin.uml.refactor.TestPackage.suite());

		return suite;
	}
}
