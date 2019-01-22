package direct;
import experiment.TestClass;

/**
 *  A test of allocation
 *
 *@author     Chris Seguin
 *@created    September 15, 1999
 */
public class AllocTest {
	/**
	 *  Gets the New attribute of the AllocTest object
	 *
	 *@return    The New value
	 */
	public Object getNew() {
		return new experiment.TestClass();
	}
}
