package org.acm.seguin.junit;

import junit.framework.TestCase;
import java.io.File;

/**
 *  The original unit tests had the name of the directory
 *  that I created to store the unit tests hard coded into
 *  them.  While I continue to hard code the names of the
 *  directories into a source file, it is now located in
 *  this single directory.
 *
 *@author    Chris Seguin
 */
public class DirSourceTestCase extends TestCase {
	/**
	 *  The name of the root directory for tests to occur
	 */
	protected String root = "./test/root";
	/**
	 *  The name of the unmodified source code
	 */
	protected String clean = "./test/clean";
	/**
	 *  The name of the directory that contains the "correct" source code
	 */
	protected String check = "./test/check";


	/**
	 *  Constructor for the TestCase object
	 *
	 *@param  name  Description of Parameter
	 */
	public DirSourceTestCase(String name) {
		super(name);
	}
}
