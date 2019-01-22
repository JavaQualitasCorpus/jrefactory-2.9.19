package imp;

import java.io.*;
import experiment.TestClass;

/**
 *  Relates to a Test Class object
 *
 *@author    Chris Seguin
 */
public class AccessClassVar {
	//  Instance Variables
	int value;


	/**
	 *  Description of the Method
	 */
	public void run() {
		value = TestClass.code;
	}
}
