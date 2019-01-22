package ren;

/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 */
public class SimpleNameConflict {
	private String test;

	public SimpleNameConflict() {
		test = "Unit Test #4";
	}

	public String getTest() {
		return test;
	}

	public static SimpleNameConflict factory() {
		return new SimpleNameConflict();
	}
}
