package imp;

import java.io.*;
import TestClass;

/**
 *  Relates to a Test Class object
 *
 *@author     Chris Seguin
 *@created    June 5, 1999
 */
public class AccessClassVariable {
	//  Instance Variables
	int value;


	/**
	 *  Description of the Method
	 */
	public void run() {
		value = TestClass.code;
	}
}
