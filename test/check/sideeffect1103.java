package direct;
import TestClass;

/**
 *  A test of class casting
 *
 *@author     Chris Seguin
 *@created    September 15, 1999
 */
public class OtherType2 {
	/**
	 *  Gets the It attribute of the OtherType2 object
	 *
	 *@param  input  Description of Parameter
	 *@return        The It value
	 */
	public boolean isIt(Object input) {
		return ((TestClass) input).isIt();
	}
}
