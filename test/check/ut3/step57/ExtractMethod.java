package method;

import java.io.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtractMethodTestSeven {
	/**
	 *  Description of the Method
	 *
	 *@param  file  Description of Parameter
	 */
	public void method(File file) {
		System.out.println("This is before");
		extractedMethod(file);
		System.out.println("This is after");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  file  Description of Parameter
	 */
	private void extractedMethod(File file) {
		try {
			PrintWriter output = new PrintWriter(new FileWriter(file));

			output.println("Line #1");

			output.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
