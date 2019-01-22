/**
 *@todo    Start of the file
 */
package test;


import java.io.*;

/**
 *  Create a class where the todo tags stay around.<P>
 *
 *  This software also checks other aspects such as where single line comments
 *  remain separated.
 *
 *@author    Chris Seguin
 *@todo      Make sure that all the todo tags hang around
 */
public class TestComments3 {
	/**
	 *  My method
	 *
	 *@todo                 save this
	 *@throws  IOException  this is thrown if something goes wrong
	 */
	public void myMethod() throws IOException {
                                                  //  First line
		foo();

                                                  //  Second line - space before and after

		bar();

                                                  //  Third line - space before only
		foo();
                                                  //  Fourth line - space after only

		bar();
                                                  //  Fifth line - no space
		foo();

		foo();


		bar();

		foo();

		bar();

		foo();

	}//  The second method is about to begin


	/**
	 *  The second method starts here
	 */
	public void method2() {
		foo();
	}

}

