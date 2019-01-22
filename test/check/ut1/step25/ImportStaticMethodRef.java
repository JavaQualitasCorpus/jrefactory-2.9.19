package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportStaticMethodRef {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String compute() {
		return NewClass.getClassField() +
				UsedClass.getClassField() +
				NearMiss.getClassField();
	}
}
