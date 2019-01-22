package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class UsesMethodTest {
	private int code;


	/**
	 *  Constructor for the UsesFieldTest object
	 */
	public UsesFieldTest() {
		height();
		code(2);
	}


	/**
	 *  Main processing method for the UsesMethodTest object
	 */
	public void run() {
		RenameMethodTest rmt = new RenameMethodTest();

		rmt.newCode();
		rmt.code(3);
		rmt.height();
		rmt.height(3);
		RenameMethodTest.height();
		RenameMethodTest.height(1);
		InheritMethodTest.height();
		InheritMethodTest.height(1);

		InheritMethodTest imt = new InheritMethodTest();
		imt.newCode();
		imt.super.newCode();
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
    private int height() {}


	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 *@return    Description of the Returned Value
	 */
    private int code(int x) {}
}
