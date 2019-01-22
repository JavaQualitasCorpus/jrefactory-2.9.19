package ren;

import imp.Associate;

/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 */
public class ComplexNameConflict {
	private String test;
	private Associate example;

	public ComplexNameConflict() {
		test = "Unit Test #4";
		example = new Associate();
	}

	public String getTest() {
		return test;
	}

	public Associate getExample() {
		return example;
	}

	public static ComplexNameConflict factory() {
		return new ComplexNameConflict();
	}
}
