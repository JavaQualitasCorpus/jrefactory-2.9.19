/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AssertTest {
	/**
	 *  Constructor for the AssertTest object
	 */
	public AssertTest() {
		int x = 1;
		assert x > 0;
		assert (x > 0);
		assert x > 0 : "test";
		assert (x > 0) : "test";
	}
}

