package org.acm.seguin.junit;

import junit.framework.AssertionFailedError;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.acm.seguin.io.FileCopy;

/**
 *  Unit test tool that compares two files and throws an
 *  exception if they are different.
 *
 *@author    Chris Seguin
 */
public class FileCompare {
	private static int count = 0;


	/**
	 *  Determines if they are equal
	 *
	 *@param  message  the message to be printed if they are different
	 *@param  one      the first file
	 *@param  two      the second file
	 */
	public static void assertEquals(String message, File one, File two) {
		assertEquals(message, one, two, true);
	}


	/**
	 *  Determines if they are equal
	 *
	 *@param  message  the message to be printed if they are different
	 *@param  one      the first file
	 *@param  two      the second file
	 *@param  copy     should the file be copied
	 */
	public static void assertEquals(String message, File one, File two, boolean copy) {
		try {
			if (one.length() == two.length()) {
				boolean same = true;
				FileInputStream oneInput = new FileInputStream(one);
				FileInputStream twoInput = new FileInputStream(two);

				for (long index = one.length(); same && (index > 0); index--) {
					same = (oneInput.read() == twoInput.read());
				}

				oneInput.close();
				twoInput.close();

				if (same) {
					return;
				}
                        }
		}
		catch (IOException ioe) {
			System.out.println("FileCompare.assertEquals  Problem comparing files");
			ioe.printStackTrace(System.out);
		}

		File destFile = new File("c:\\temp\\file" + count + ".java");
		if (copy) {
			count++;
			(new FileCopy(
					two,
					destFile, false)).run();
		}

		String formatted = "";
		if (message != null) {
			formatted = message + " ";
		}
		throw new AssertionFailedError(formatted +
				"expected same, copied to " + destFile.getPath() + " (len="+two.length() +
				") compared against " + one.getPath()+" (len="+one.length()+")");
	}
}
