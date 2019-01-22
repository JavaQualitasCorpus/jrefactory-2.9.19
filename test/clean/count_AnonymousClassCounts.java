package count;

import java.io.*;
import java.util.LinkedList;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AnonymousClassCounts {
	private InputStream data =
		new FileInputStream() {
			/**
			 *  Description of the Method
			 *
			 *@return    Description of the Returned Value
			 */
			public int available() {
				return 1;
			}
		}
	;


	/**
	 *  Gets the Results attribute of the AnonymousClassCounts object
	 *
	 *@return    The Results value
	 */
	public Exception getResults() {
		Object obj =
			new IOException() {
				/**
				 *  Gets the Message attribute of the AnonymousClassCounts object
				 *
				 *@return    The Message value
				 */
				public String getMessage() {
					return "right place";
				}
			}
		;

		return
			new Exception() {
				/**
				 *  Gets the Message attribute of the AnonymousClassCounts object
				 *
				 *@return    The Message value
				 */
				public String getMessage() {
					return "Result";
				}
			}
		;
	}
}
