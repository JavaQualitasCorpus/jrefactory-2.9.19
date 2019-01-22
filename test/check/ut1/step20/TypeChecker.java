package imp;

import TestClass;
import abstracter.NormalClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TypeChecker {

	/**
	 *  Gets the New attribute of the TypeChecker object
	 *
	 *@return    The New value
	 */
	public NormalClass getNew() {
		return new NormalClass();
	}


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
