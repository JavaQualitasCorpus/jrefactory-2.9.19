package ren;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class LocalizedObject {
	private String test;


	/**
	 *  Constructor for the LocalizedObject object
	 */
	public LocalizedObject() {
		test = "Unit Test #3";
	}


	/**
	 *  Gets the Test attribute of the LocalizedObject object
	 *
	 *@return    The Test value
	 */
	public String getTest() {
		return test;
	}


	/**
	 *  Gets the New attribute of the LocalizedObject class
	 *
	 *@return    The New value
	 */
	public static LocalizedObject getNew() {
		return new LocalizedObject();
	}
}
