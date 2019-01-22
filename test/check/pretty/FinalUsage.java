package test.prettyprinter;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class FinalUsage {
	final int CONSTANT = 23;


	/**
	 *  Constructor for the FinalUsage object
	 *
	 *@param  message  Description of Parameter
	 */
	public FinalUsage(final String message) {
		System.out.println(message);
	}


	/**
	 *  Main processing method for the FinalUsage object
	 */
	public void run() {
		final double key = 2.0;
		for (final Iterator iter = getList(key); ; ) {
			System.out.println("Next:  " + iter.next());
		}
	}


	/**
	 *  Gets the List attribute of the FinalUsage object
	 *
	 *@param  value  Description of Parameter
	 *@return        The List value
	 */
	protected final Iterator getList(final double value) {
		LinkedList list = new LinkedList();
		double current = 0.0;
		double stepSize = 1.0 / CONSTANT;
		while (current < value) {
			list.add(new Double(current));
			current += stepSize;
		}
		return list.iterator();
	}


	/**
	 *  Gets the FullList attribute of the FinalUsage object
	 *
	 *@return    The FullList value
	 */
	protected final Iterator getFullList() {
		LinkedList list = new LinkedList();
		double current = 0.0;
		for (final double stepSize = 1.0 / CONSTANT;
				current < 10.0; current += stepSize) {
			list.add(new Double(current));
		}
		return list.iterator();
	}
}
