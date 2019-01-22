package experiment;

/**
 *  Description of the Class
 *
 *@author     unknown
 *@created    April 7, 2000
 */
public class TryTest {
	/**
	 *  Main processing method for the TryTest object
	 */
	public void run() {
		InputStream input = null;
		try {
			StringBuffer buf = new StringBuffer("message");
			input = new FileInputStream("c:\\temp\\test.txt");
			input.write(buf.toString().getBytes());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException ioe) {
				System.out.println("Unable to close the file");
			}
		}
	}
}
