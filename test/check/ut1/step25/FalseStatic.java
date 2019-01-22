package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class FalseStatic {
	private StringBuffer OldClass;


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String compute() {
		return OldClass.toString()
				+ UsedClass.classField
				+ NearMiss.classField;
	}
}
