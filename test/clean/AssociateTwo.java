import java.io.*;
import TestClass;

/**
 *  Relates to a Test Class object
 *
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

	public void inputArray(int[][] data, String[] names) {
	}

	public int[] resultArray(String name) {
		return new int[5];
	}

	public int resultArray2(String name)[][] {
		return new int[5][11];
	}

	public int[] resultArray3(String name)[][] {
		return new int[5][6][3];
	}
}
