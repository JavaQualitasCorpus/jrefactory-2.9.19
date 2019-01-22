import java.io.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class MyOtherObject {

	/**
	 *  Constructor for the MyOtherObject object
	 *
	 *@param  aDouble         Description of Parameter
	 *@param  aRWPN_incident  Description of Parameter
	 */
	public MyOtherObject(double aDouble, MyObject aRWPN_incident) {
		System.out.println("Something before");
		this.accidentType = aRWPN_incident.fSourceType;
		this.theNumber = aRWPN_incident.fNumber;
		System.out.println("Something after");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  value  Description of Parameter
	 */
	public void exceptionProblemReport(int value) {
		String message = "Hello";

		try {
			PrintWriter output = new PrintWriter(new FileWriter("c:\\temp\\temp.txt"));

			output.println("Hello there!");
			output.println("  Message:  " + message);
			output.println("  Code:     " + value);

			output.close();
		}
		catch (IOException ioe) {
			extractedMethod(ioe);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  ioe  Description of Parameter
	 */
	private void extractedMethod(IOException ioe) {
		System.out.println("Error:  " + ioe.getMessage());
		ioe.printStackTrace();
	}
}

