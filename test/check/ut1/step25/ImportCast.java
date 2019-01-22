package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportCast {
	private Object one;
	private Object two;
	private Object three;


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String compute() {
		return ((NewClass) one).getFieldValue() +
				((UsedClass) two).getFieldValue() +
				((NearMiss) three).getFieldValue();
	}
}
