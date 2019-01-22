package count;

import org.acm.seguin.io.*;
import java.util.Iterator;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ConstructorCounts extends FileCopy implements Serializable, Cloneable {
	/**
	 *  Description of the Method
	 *
	 */
	public ConstructorCounts() {
		StringBuffer buffer = new StringBuffer();
		int ndx = 0;

		do {
			ndx++;
			buffer.append(ndx);
			buffer.append(", ");
		} while (ndx < 50);

		//  Return the result
		System.out.println(buffer.toString());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  index  Description of Parameter
	 */
	protected ConstructorCounts(int index) {
		super(index);

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
	 *@param  value  Description of Parameter
	 */
	ConstructorCounts(String value) {
		super(value,
				value.length);
		System.out.println(value);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  input  Description of Parameter
	 *@param  extra  Description of Parameter
	 */
	ConstructorCounts(int input, double extra) {
		this("" + extra);
		switch (input) {
			case 0:
				System.out.println("It was zero");
				break;
			case 1:
				x += 1;
				break;
			case 2:
				break;
			default:
				helper();
				break;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  message1         Description of Parameter
	 *@param  message2         Description of Parameter
	 *@param  message3         Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	private ConstructorCounts(String message1, String message2, String message3)
			 throws IOException {
		this(message1 +
				message2 +
				message3);

		/*
		 *  Here is the big try/catch
		 */
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
	/*
	 *  This is the end of this mostly useless class
	 */
}

