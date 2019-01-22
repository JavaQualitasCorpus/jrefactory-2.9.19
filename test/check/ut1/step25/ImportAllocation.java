package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportAllocation {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String compute() {
		return (new NewClass()).getFieldValue() +
				(new UsedClass()).getFieldValue() +
				(new NearMiss()).getFieldValue();
	}
}
