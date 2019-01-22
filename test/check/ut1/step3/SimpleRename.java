package ren;

/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 */
public class SimpleRename {
	private String test;

	public SimpleRename() {
		test = "Unit Test #4";
	}

	public String getTest() {
		return test;
	}

	public static SimpleRename factory() {
		return new SimpleRename();
	}
}
