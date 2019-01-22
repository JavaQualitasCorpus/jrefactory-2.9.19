package ren;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AccessClassVar {
	private String test;


	/**
	 *  Constructor for the AccessClassVar object
	 */
	public AccessClassVar() {
		test = "Unit Test #4";
	}


	/**
	 *  Gets the Test attribute of the AccessClassVar object
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
	public static AccessClassVar factory() {
		return new AccessClassVar();
	}
}
