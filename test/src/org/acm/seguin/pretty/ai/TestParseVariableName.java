/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.ai;

import junit.framework.TestCase;

/**
 *  Unit tests for the ParseVariableName object
 *
 *@author    Chris Seguin
 */
public class TestParseVariableName extends TestCase {
	/**
	 *  Constructor for the TestParseVariableName object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestParseVariableName(String name)
	{
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test01()
	{
		ParseVariableName pvn = new ParseVariableName();

		assertEquals("First attempt", "parse variable name", pvn.parse("ParseVariableName"));
		assertEquals("Second attempt", "parse variable name", pvn.parse("parseVariableName"));
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test02()
	{
		ParseVariableName pvn = new ParseVariableName();

		assertEquals("First attempt", "find URL", pvn.parse("findURL"));
		assertEquals("Second attempt", "get URL path", pvn.parse("GetURLPath"));
	}
}

