package imp;
import TestClass;

/**
 *  Tests if we find stuff in allocation expressions
 *
 *@author    Chris Seguin
 */
public class Factory {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Object createNew() {
		return new TestClass();
	}
}
