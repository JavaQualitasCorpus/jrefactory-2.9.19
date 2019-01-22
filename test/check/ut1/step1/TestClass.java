package test;
import java.io.*;
import AssociateTwo;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestClass {
	//  Instance Variables
	private AssociateTwo two;

	//  Class Variables
	/**
	 *  Description of the Field
	 */
	public static final int code = 50;


	/**
	 *  Main processing method for the TestClass object
	 */
	public void run() {
		System.out.println("This class is a:  " +
				this.getClass().getName());
	}


	/**
	 *  The main program for the TestClass class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		(new TestClass()).run();
	}
}
