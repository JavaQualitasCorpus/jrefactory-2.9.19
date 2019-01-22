package test;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;


/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AddExtraSpaces {
	/**
	 *  Adds a feature to the SpaceAroundLocalVariables attribute of the
	 *  AddExtraSpaces object
	 */
	public void addSpaceAroundLocalVariables() {
		/*
		 *
		 */
		int code = 52;
		String message = "Hello world";

		System.out.println(message);

		double value = code * 0.8;

		System.out.println("Before:  " + value);
		for (int ndx = 0; ndx < code; ndx++) {
			value *= 2.0;
		}
		System.out.println("After:  " + value);
	}


	/**
	 */
	public void addSpaceAroundLocalVariables2() {
		int code = 52;
		String message = "Hello world";

		System.out.println(message);

		double value = code * 0.8;

		System.out.println(value);

		if (!singleTest) {
			System.out.println("Double line test");
			singleTest = !singleTest;
		}
		else {
			System.out.println("A single test");
		}

		if (!singleTest) {
			if (shouldNotBeRemoved) {
				System.out.println("Stuff1");
			}
			else {
				System.out.println("Other stuff");
			}
		}
		else {
			System.out.println("A single test");
		}

		for (int ndx = 0; ndx < 6; ndx++) {
			if (shouldBeRemoved) {
				System.out.println("Gone!");
			}
		}
	}
}
//  Here are some final comments
