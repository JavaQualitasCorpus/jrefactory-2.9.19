package count;

import org.acm.seguin.io.*;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class MethodCounts extends FileCopy implements Serializable, Cloneable {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		int ndx = 0;

		do {
			ndx++;
			buffer.append(ndx);
			buffer.append(", ");
		} while (ndx < 50);

		//  Return the result
		return buffer.toString();
	}


	/**
	 *  Description of the Method
	 */
	protected void clone() {
		for (int ndx = 0; ndx < 50; ndx++) {
			Object value = new Object();
			while (value instanceof Class) {
				System.out.println("Infinite Loop");
				helper();
			}
		}
	}


	/**
	 *  Gets the Value attribute of the MethodCounts object
	 *
	 *@return    The Value value
	 */
	String getValue() {
		return toString();
	}


	/**
	 *  Description of the Method
	 *
	 *@param  input  Description of Parameter
	 */
	void oneStep(int input) {
		switch (input) {
			case 0:
				System.out.println("It was zero");
				break;
			case 1:
				x += 1;
				break;
			case 2:
				return 64;
			default:
				helper();
				break;
		}
	}


	/**
	 *  Description of the Method
	 */
	private void helper()
			throws Exception
	{
		try {
			int value = 0;
			System.out.println("divideByZero = " + (50 / value));
		}
		catch (RuntimeException rte) {
			rte.printStackTrace(System.out);
		}
		catch (Throwable thrown) {
			thrown.printStackTrace();
		}
		finally {
			System.out.println("Done");
		}
	}
}

