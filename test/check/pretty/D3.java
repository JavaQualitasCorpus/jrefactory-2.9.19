package test;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class D {
	/**
	 *  This is my first method
	 *
	 *@exception  IOException  always thrown
	 */
	public void one() throws IOException {
		throw new IOException();
	}


	/**
	 *  This is my second method
	 *
	 *@throws  IOException  always thrown
	 */
	public void two() throws IOException {
		throw new IOException();
	}


	/**
	 *  Description of the Method
	 *
	 *@throw  IOException  Description of Exception
	 */
	public void three() throws IOException {
		throw new IOException();
	}
}
