package org.acm.seguin.test;

import java.io.*;

/**
 *  This class is a test of what happens to an already acceptable file
 *
 *@author     Chris Seguin
 *@created    January 1, 2000
 */
public class Correct extends Object {
	/**
	 *  This method throws an exception or returns a string based on the boolean
	 *  input
	 *
	 *@param  value            the boolean value that makes the choice
	 *@return                  a string "OK"
	 *@exception  IOException  thrown if value was false
	 */
	public String makeChoice(boolean value) throws IOException {
		if (value) {
			return "OK";
		}
		else {
			throw new IOException("Value was not OK");
		}
	}
}
