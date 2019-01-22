package direct;
import experiment.TestClass;

/**
 *  A test of class casting
 *
 *@author    Chris Seguin
 */
public class OtherType2 {
	/**
	 *  Gets the It attribute of the OtherType2 object
	 *
	 *@param  input  Description of Parameter
	 *@return        The It value
	 */
	public boolean isIt(Object input) {
		return ((experiment.TestClass) input).isIt();
	}
}
