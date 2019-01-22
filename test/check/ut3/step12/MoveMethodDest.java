package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class MoveMethodDest {
	private int code;


	/**
	 *  Sets the Code attribute of the MoveMethodDest object
	 *
	 *@param  value  The new Code value
	 */
	public void setCode(int value) {
		code = value;
	}


	/**
	 *  Sets the Title attribute of the MoveMethodDest object
	 *
	 *@param  title  The new Title value
	 */
	public void setTitle(String title) {
		System.out.println(title);
	}


	/**
	 *  Gets the Code attribute of the MoveMethodDest object
	 *
	 *@return    The Code value
	 */
	public int getCode() {
		return code;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  moveMethodSource  Description of Parameter
	 */
	public void methodThree(MoveMethodSource moveMethodSource) {
		int value = getCode();
		value += moveMethodSource.getIncrement();
		setCode(value);
	}
}
