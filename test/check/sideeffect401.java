package imp;
import test.TestClass;

/**
 *  Tests if we find stuff in instanceOf expressions
 *
 *@author     Chris Seguin
 *@created    September 7, 1999
 */
public class TypeChecker {
	/**
	 *  Description of the Method
	 *
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public boolean check(Object data) {
		return data instanceof TestClass;
	}
}
