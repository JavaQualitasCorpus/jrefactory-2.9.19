package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportStaticFieldRef {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String compute() {
		return NewClass.classField
				+ UsedClass.classField
				+ NearMiss.classField;
	}
}
