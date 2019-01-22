package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class FieldInitOrderTest {
	private String privateFieldInit = "Chris";
	int packageIntInit = 66;
	/**
	 *  Description of the Field
	 */
	protected String protectedFieldInit = privateFieldInit + " Seguin";
	/**
	 *  Description of the Field
	 */
	public String publicFieldInit = protectedFieldInit + " wrote this code";


	/**
	 *  Constructor for the FieldInitOrderTest object
	 */
	public FieldInitOrderTest() {
	}


	/**
	 *  Description of the Method
	 */
	public void method() {
		System.out.println("Message:  " + publicFieldInit);
	}


	/**
	 *  Description of the Field
	 */
	public String publicField;
	/**
	 *  Description of the Field
	 */
	protected String protectedField;
	int packageInt;
	private String privateField;
}
