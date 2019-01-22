package test;

/**
 *  These comments are
 *  nicely formatted.  The reason for the
 *  unusual code is:
 *
 *  -  To test that the reformat=false works
 *  -  To check about single line comments
 *
 *@author     Chris Seguin
 *@created    October 31, 2000
 *   Happy Holloween!
 */
public class TestComments {
	/*<IMPORTANT>*/
	/**
	 *  Again we have some more strangely formatted
	 *  comments.  But then, some people like the
	 *  comments in their control.
	 *
	 *@return    Description of the Returned Value
	 */
	public double method() {
		double result;
		do {
			//  Single line comment to start
			int index = 52;
			result = 12;

			for (int ndx = 0; ndx < index; ndx++) {
				result *= 1.5;
				//  Increase the result
			}
			//  End for
		} while (result < 50);
		//  End While

//		This is an old outdated piece of code
//      That is commented here

		return result;
	}
	//  End method
	/*</IMPORTANT>*/


	/**
	 *  Here comes the next method
	 */
	public void method2() {
		double result = method();
		System.out.println("Here is the second method");
	}
	//  End method2
}
//  End TestComments
