package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class JavadocStyleTest {
	/**  A simple field description */
	private boolean fieldOne;

	/**
	 *  A more complex field description hidden<br>
	 *
	 */
	private boolean fieldTwo;

	/**
	 *  A very long line that contains a lot of text, but has no other reason to
	 *  be broken into multiple lines
	 */
	private boolean fieldThree;


	/**  Description of the Method */
	public void method() {
		/*
		 *  A simple method that could possibly fit on a single line
		 */
		System.out.println("It works!");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  code  Description of Parameter
	 */
	public void method(int code) {
		/*
		 *  A simple method that should not fit on a single line
		 */
		System.out.println("It works!");
	}
}
