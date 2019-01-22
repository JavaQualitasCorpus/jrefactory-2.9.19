package field;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class UsesFieldTest {
	private String simple;
	private int code;


	/**
	 *  Constructor for the UsesFieldTest object
	 */
	public UsesFieldTest() {
		code = RenameFieldTest.CODE_OFF;
		simple = "high";
	}


	/**
	 *  Main processing method for the UsesFieldTest object
	 */
	public void run() {
		RenameFieldTest rft = new RenameFieldTest();

		rft.setCode(RenameFieldTest.changed);
		rft.height = 32;

		code = RenameFieldTest.changed;
		simple = "turn it on!";
	}

}
