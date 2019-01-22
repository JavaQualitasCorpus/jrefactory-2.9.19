import java.io.*;
import test.TestClass;

/**
 *  Relates to a Test Class object
 *
 *@author     Chris Seguin
 *@created    June 20, 1999
 */
public class AssociateTwo {
	//  Instance Variables
	TestClass value;


	/**
	 *  Description of the Method
	 */
	public void run() {
		value = new TestClass();
		value.run();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  data   Description of Parameter
	 *@param  names  Description of Parameter
	 */
	public void inputArray(int[][] data, String[] names) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  name  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public int[] resultArray(String name) {
		return new int[5];
	}


	/**
	 *  Description of the Method
	 *
	 *@param  name  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public int resultArray2(String name)[][] {
		return new int[5][11];
	}


	/**
	 *  Description of the Method
	 *
	 *@param  name  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public int[] resultArray3(String name)[][] {
		return new int[5][6][3];
	}
}
