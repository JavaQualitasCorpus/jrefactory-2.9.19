package test;
import java.io.*;
import AssociateTwo;

/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 *@created    June 5, 1999
 */
public class TestClass {
	//  Instance Variables
	private AssociateTwo two;

	//  Class Variables
	public final static int code = 50;


	/**
	 *  Description of the Method
	 */
	public void run() {
		System.out.println("This class is a:  " +
				this.getClass().getName());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  args  Description of Parameter
	 */
	public static void main(String[] args) {
		(new TestClass()).run();
	}
}
