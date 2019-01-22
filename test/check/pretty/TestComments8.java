package test;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestCStyleComments {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public int method() {
		/*
		 *  Here is a method that is included in the
		 *  source code to describe what is going on.
		 */
		int x = 0;

		/*
		 *  Here is a single line C style comment
		 */
		x++;

		/*
		 *  here is the first
		 *  comment     with funny spacing
		 */
		x *= 50;

		//  This is an empty
		/*
		 *
		 */
		/*
		 *  This comment has no stars to the right
		 */
		if (x > 10) {
			x--;
		}

		x = x % 30
		/*
		 *  + x * 2 (comment in the flow)
		 */
				+ 1;

		/*
		 *  here
		 *  is
		 *  a c style comment
		 *  with funny spacing
		 */
		return x;
	}
}
