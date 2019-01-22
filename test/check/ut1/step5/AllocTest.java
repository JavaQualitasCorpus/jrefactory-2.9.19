package direct;
import TestClass;
import ren.TestState;

/**
 *  A test of allocation
 *
 *@author    Chris Seguin
 */
public class AllocTest {
	/**
	 *  Gets the New attribute of the AllocTest object
	 *
	 *@return    The New value
	 */
	public Object getNew() {
		return new TestClass();
	}


	/**
	 *  Gets the SimpleRename attribute of the AllocTest object
	 *
	 *@return    The SimpleRename value
	 */
	public Object getSimpleRename() {
		return new ren.TestState();
	}


	/**
	 *  Gets the SimpleNameConflict attribute of the AllocTest object
	 *
	 *@return    The SimpleNameConflict value
	 */
	public Object getSimpleNameConflict() {
		return new ren.SimpleNameConflict();
	}


	/**
	 *  Gets the ComplexNameConflict attribute of the AllocTest object
	 *
	 *@return    The ComplexNameConflict value
	 */
	public Object getComplexNameConflict() {
		return new ren.ComplexNameConflict();
	}
}
