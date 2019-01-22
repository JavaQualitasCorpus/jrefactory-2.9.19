package direct;
import experiment.TestClass;

/**
 *  A test of static method invocation
 *
 *@author    Chris Seguin
 */
public class StaticMethod {
	/**
	 *  Gets the StaticMethod attribute of the StaticMethod object
	 *
	 *@return    The StaticMethod value
	 */
	public Object getStaticMethod() {
		return experiment.TestClass.main();
	}
}
