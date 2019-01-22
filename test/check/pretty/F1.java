package test;

import java.io.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class FTest
					extends Object
						implements Serializable {
	/**
	 *  Here is a method with an @ejb:artificial artificial tag and an
	 *
	 *@since    this is what we are trying to test
	 *@real     tag in the middle of an extended comment. Part of this will be
	 *      broken out<p>
	 *
	 *
	 */
	public void firstMethod() {

		System.out.println("Stuff here");
		System.out.println("More stuff here");
	}


	/**
	 *  Description of the Method
	 */
	public void secondMethod() {

		System.out.println("Second method");
		System.out.println("Additional data here");
	}


	/**
	 *  Description of the Method
	 */
	public void thirdMethod() {
		System.out.println("Simple simon");
	}


	/**
	 *  Description of the Method
	 */
	public void fourthMethod() {

		System.out.println("More than one");
	}
}
