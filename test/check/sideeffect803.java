package direct;
import TestClass;

/**
 *  A test of static field access
 *
 *@author     Chris Seguin
 *@created    September 15, 1999
 */
public class StaticField {
	/**
	 *  Gets the StaticField attribute of the StaticField object
	 *
	 *@return    The StaticField value
	 */
	public Object getStaticField() {
		return TestClass.general;
	}
}
