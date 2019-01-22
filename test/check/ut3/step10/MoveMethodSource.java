package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class MoveMethodSource {
	/**
	 *  Description of the Field
	 */
	public int incr1;
	private int increment;
	private int incr2;


	/**
	 *  Constructor for the MoveMethodSource object
	 */
	public MoveMethodSource() {
		incr1 = 5;
		incr2 = 1;
		increment = 3;
	}


	/**
	 *  Gets the Increment attribute of the MoveMethodSource object
	 *
	 *@return    The Increment value
	 */
	public int getIncrement() {
		return increment;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodTwo(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += incr1;
		mmd.setCode(value);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodThree(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += increment;
		mmd.setCode(value);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodFour(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += incr2;
		mmd.setCode(value);
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String publicMethod() {
		return "hello";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodFive(MoveMethodDest mmd) {
		mmd.setTitle(publicMethod() + ":  " + mmd.getCode());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodSix(MoveMethodDest mmd) {
		mmd.setTitle(protectedMethod() + ":  " + mmd.getCode());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodSeven(MoveMethodDest mmd) {
		mmd.setTitle(packageMethod() + ":  " + mmd.getCode());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodEight(OtherMethodDest mmd) {
		mmd.setTitle(packageMethod() + ":  " + mmd.getCode());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodNine(MoveMethodDest mmd) {
		mmd.setTitle(privateMethod() + ":  " + mmd.getCode());
	}


	/**
	 *  Description of the Method
	 *
	 *@param  mmd  Description of Parameter
	 */
	public void methodOne(MoveMethodDest mmd) {
		mmd.methodOne();
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	protected String protectedMethod() {
		return "confidential";
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	String packageMethod() {
		return "secret";
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	private String privateMethod() {
		return "topSecret";
	}
}
