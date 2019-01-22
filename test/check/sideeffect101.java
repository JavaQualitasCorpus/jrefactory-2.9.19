package imp;

import java.io.*;
import test.TestClass;

/**
 *  Relates to a Test Class object
 *
 *@author     Chris Seguin
 *@created    June 5, 1999
 */
public class Associate {
	//  Instance Variables
	TestClass value;


	/**
	 *  Description of the Method
	 */
	public void run() {
		value = new TestClass();
		value.run();
	}
}
