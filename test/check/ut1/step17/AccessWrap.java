package imp;

import abstracter.RapTestOne;
import abstracter.ChildRapOne;
import abstracter.ChildRapTwo;
import abstracter.RapTestThree;
import abstracter.ChildRapThree;
import abstracter.RapTestFour;
import abstracter.ChildRapFour;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AccessWrap {
	/**
	 *  Gets the InstanceOne attribute of the AccessWrap object
	 *
	 *@return    The InstanceOne value
	 */
	public RapTestOne getInstanceOne() {
		return new ChildRapOne();
	}


	/**
	 *  Gets the InstanceTwo attribute of the AccessWrap object
	 *
	 *@return    The InstanceTwo value
	 */
	public Object getInstanceTwo() {
		return new ChildRapTwo();
	}


	/**
	 *  Gets the InstanceThree attribute of the AccessWrap object
	 *
	 *@return    The InstanceThree value
	 */
	public RapTestThree getInstanceThree() {
		return new ChildRapThree();
	}


	/**
	 *  Gets the InstanceFour attribute of the AccessWrap object
	 *
	 *@return    The InstanceFour value
	 */
	public RapTestFour getInstanceFour() {
		return new ChildRapFour();
	}
}
