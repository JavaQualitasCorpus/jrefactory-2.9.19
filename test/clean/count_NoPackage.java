import java.io.*;
import java.util.LinkedList;

public class NoPackageCount {
	/**
	 *  An input stream
	 */
	public InputStream input = new FileInputStream("c:\\temp\\file1.java");
	/*<An output stream/>*/
	protected OutputStream output = null;
	/*
	 *  The last exception that was thrown
	 */
	IOException lastException = new IOException() {
		public String getMessage() {
			return "There have been no messages yet";
		}
	};



	//  A linked list of information
	private LinkedList list,
		secondDefinition = new LinkedList();
}
