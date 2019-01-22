package ren;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestState {
	private String test;


	/**
	 *  Constructor for the TestState object
	 */
	public TestState() {
		test = "Unit Test #4";
	}


	/**
	 *  Gets the Test attribute of the TestState object
	 *
	 *@return    The Test value
	 */
	public String getTest() {
		return test;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public static TestState factory() {
		return new TestState();
	}
}
