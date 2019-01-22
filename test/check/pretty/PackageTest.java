/*
 *
 * Copyright 1999
 * Dade Behring, Inc
 *
 * This code was developed
 * for the DBNet project.
 * All rights reserved.
 *
 */
package com.dbnet.common.view.report;

import test.framework.*;

/**
 *  Tests the objects in com.dbnet.admin.contents
 *
 *@author    Chris Seguin
 *@date      February 3, 1999
 */
public class PackageTest {
	/**
	 *  Runs all the tests
	 *
	 *@return    the suite
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTest(new TestPageInfo("testInitial"));
		suite.addTest(new TestPageInfo("testNull"));
		suite.addTest(new TestPageInfo("testChange"));

		return suite;
	}


	/**
	 *  Run the test suite to the command line
	 *
	 *@param  args  Description of Parameter
	 */
	public static void main(String args[]) {
		test.textui.TestRunner.run(suite());
	}
}
