package ref;

import other.NearMiss;
import ren.UsedClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportNotUse {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String compute() {
		return (new UsedClass()).getFieldValue() +
				(new NearMiss()).getFieldValue();
	}
}
