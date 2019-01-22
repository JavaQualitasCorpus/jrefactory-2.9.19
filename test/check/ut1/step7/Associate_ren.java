package ren;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Associate {
	private String test;
	private imp.Associate example;


	/**
	 *  Constructor for the Associate object
	 */
	public Associate() {
		test = "Unit Test #4";
		example = new imp.Associate();
	}


	/**
	 *  Gets the Test attribute of the Associate object
	 *
	 *@return    The Test value
	 */
	public String getTest() {
		return test;
	}


	/**
	 *  Gets the Example attribute of the Associate object
	 *
	 *@return    The Example value
	 */
	public imp.Associate getExample() {
		return example;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public static Associate factory() {
		return new Associate();
	}
}
