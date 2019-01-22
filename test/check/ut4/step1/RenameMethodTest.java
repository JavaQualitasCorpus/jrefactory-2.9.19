package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class RenameMethodTest {

	/**
	 *  Constructor for the RenameMethodTest object
	 */
	public RenameMethodTest() {
	}


	/**
	 *  Constructor for the RenameMethodTest object
	 *
	 *@param  x  Description of Parameter
	 */
	public RenameMethodTest(int x) {
		newCode();
		code(x);
		height();
		height(x);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 */
	public void withinMethod(int x) {
		newCode();
		code(x);
		height();
		height(x);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public int newCode() {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 *@return    Description of the Returned Value
	 */
	public int code(int x) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 */
	protected void protectedMethod(int x) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 */
	void packageProtectedMethod(int x) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 */
	private void privateMethod(int x) {
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public static int height() {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 */
	public static void height(int x) {
	}

}
