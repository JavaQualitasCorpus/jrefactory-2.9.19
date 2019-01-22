package field;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class RenameFieldTest {
	/**
	 *  Description of the Field
	 */
	public double changed;
	private String simple;
	private int code;
	private UsesFieldTest childObject;
	/**
	 *  Description of the Field
	 */
	public static final int CODE_ON = 0;
	/**
	 *  Description of the Field
	 */
	public static final int CODE_OFF = 1;


	/**
	 *  Constructor for the RenameFieldTest object
	 */
	public RenameFieldTest() {
		simple = "ample";
		code = 52;
		changed = 321.12;
	}


	/**
	 *  Constructor for the RenameFieldTest object
	 *
	 *@param  code  Description of Parameter
	 */
	public RenameFieldTest(int code) {
		simple = "ample";
		this.code = code;
		changed = 321.12;
	}


	/**
	 *  Sets the Code attribute of the RenameFieldTest object
	 *
	 *@param  code  The new Code value
	 */
	public void setCode(int code) {
		this.code = code;
	}


	/**
	 *  Sets the CodeAlt attribute of the RenameFieldTest object
	 *
	 *@param  changed  The new CodeAlt value
	 */
	public void setCodeAlt(int changed) {
		code = changed;
	}


	/**
	 *  Gets the Simple attribute of the RenameFieldTest object
	 *
	 *@return    The Simple value
	 */
	public String getSimple() {
		return simple;
	}


	/**
	 *  Gets the Code attribute of the RenameFieldTest object
	 *
	 *@return    The Code value
	 */
	public int getCode() {
		return this.code;
	}


	/**
	 *  Gets the Volume attribute of the RenameFieldTest object
	 *
	 *@return    The Volume value
	 */
	public double getVolume() {
		return changed * this.changed * changed;
	}


	/**
	 *  Description of the Method
	 */
	public void debugPrint() {
		System.out.println("String:  " + simple.toString() + "  " + simple());
	}


	/**
	 *  Compares this to the parameter.
	 *
	 *@param  other  the reference object with which to compare.
	 *@return        <tt>true</tt> if this object is the same as the obj
	 *      argument; <tt>false</tt> otherwise.
	 */
	public boolean equals(Object other) {
		if (other instanceof RenameFieldTest) {
			RenameFieldTest rft = (RenameFieldTest) other;
			return rft.code == this.code;
		}

		return false;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  anObject  Description of Parameter
	 */
	void usesField(UsesFieldTest anObject) {
		anObject.simple = "";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  objectToDownCast  Description of Parameter
	 */
	void upCasts(RenameFieldTest objectToDownCast) {
		((InheritFieldTest) objectToDownCast).anOtherMember = "";
		((InheritFieldTest) objectToDownCast).simple = "";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  objectToDownCast  Description of Parameter
	 */
	void downCasts(InheritFieldTest objectToDownCast) {
		((RenameFieldTest) objectToDownCast).simple = "";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  linkedObject  Description of Parameter
	 */
	void linkedField(RenameFieldTest linkedObject) {
		linkedObject.childObject.simple = "";
	}

}
