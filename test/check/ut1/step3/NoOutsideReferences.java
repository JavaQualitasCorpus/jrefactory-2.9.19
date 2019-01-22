package ren;

/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 */
public class NoOutsideReferences {
	private String test;

	public NoOutsideReferences() {
		test = "Unit Test #3";
	}

	public String getTest() {
		return test;
	}

	public static NoOutsideReferences getNew() {
		return new NoOutsideReferences();
	}
}
