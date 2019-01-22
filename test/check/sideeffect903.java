package direct;
import TestClass;

/**
 *  A test of static method invocation
 *
 *@author     Chris Seguin
 *@created    September 15, 1999
 */
public class StaticMethod {
	/**
	 *  Gets the StaticMethod attribute of the StaticMethod object
	 *
	 *@return    The StaticMethod value
	 */
	public Object getStaticMethod() {
		return TestClass.generalAccess();
	}
}
