package field;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class InheritFieldTest extends RenameFieldTest {
	/**
	 *  Description of the Field
	 */
	public String anOtherMember;
	private int code;


	/**
	 *  Constructor for the InheritFieldTest object
	 */
	public InheritFieldTest() {
		code = 52;
	}


	/**
	 *  Main processing method for the InheritFieldTest object
	 */
	public void run() {
		System.out.println("Code:  " + code);
		System.out.println("Height:  " + changed);
	}
}
