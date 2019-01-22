/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.CharArrayWriter;
import java.io.Writer;
import java.io.BufferedWriter;
import net.sourceforge.jrefactory.factory.BufferParserFactory;

/**
 *  Pretty prints the string
 *
 *@author    Chris Seguin
 */
public abstract class PrettyPrintString extends PrettyPrintFile {
	//  Instance Variables
	private CharArrayWriter outputStream;
   private Writer outputWriter;


	/**
	 *  Create an PrettyPrintString object
	 */
	public PrettyPrintString()
	{
		outputStream = new CharArrayWriter();
      outputWriter = new BufferedWriter(outputStream);
	}


	/**
	 *  Sets the input string to be pretty printed
	 *
	 *@param  input  the input buffer
	 */
	protected void setInputString(String input)
	{
		if (input == null) {
			return;
		}

		setParserFactory(new BufferParserFactory(input));
	}


	/**
	 *  Get the output buffer
	 *
	 *@return    a string containing the results
	 */
	protected String getOutputBuffer()
	{
		return new String(outputStream.toCharArray());
	}


	/**
	 *  Create the output stream
	 *
	 *@param  file  the name of the file
	 *@return       the output stream
	 */
	protected Writer getWriter(File file)
	{
		return outputWriter;
	}


	/**
	 *  Reset the output buffer
	 */
	protected void resetOutputBuffer()
	{
		outputStream.reset();
	}
}
