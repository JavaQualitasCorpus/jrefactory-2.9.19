package method;

import java.io.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtractMethodTestThree {
	/**
	 *  Constructor for the ExtractMethodTestThree object
	 */
	public ExtractMethodTestThree() {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  input            Description of Parameter
	 *@param  output           Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	public void transcribe(InputStream input, OutputStream output) throws IOException {
		int bufferSize = 1024 * 8;
		byte[] buffer = new byte[bufferSize];
		int bytesRead = input.read(buffer);
		while (bytesRead >= 0) {
			extractedMethod(output, buffer, bytesRead, input);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  output     Description of Parameter
	 *@param  buffer     Description of Parameter
	 *@param  bytesRead  Description of Parameter
	 *@param  input      Description of Parameter
	 */
	private void extractedMethod(OutputStream output, byte[] buffer, int bytesRead, InputStream input) {
		output.write(buffer, 0, bytesRead);
		bytesRead = input.read(buffer);
	}
}

