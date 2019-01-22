package direct;
import experiment.TestClass;

/**
 *  A test of instanceof
 *
 *@author    Chris Seguin
 */
public class OtherType1 {
	/**
	 *  Gets the It attribute of the OtherType1 object
	 *
	 *@param  input  Description of Parameter
	 *@return        The It value
	 */
	public boolean isIt(Object input) {
		return input instanceof experiment.TestClass;
	}
}
