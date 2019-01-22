package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportClassAccess {
	/**
	 *  The main program for the ImportClassAccess class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		Class class1 = NewClass.class;
		Class class2 = UsedClass.class;
		Class class3 = NearMiss.class;
	}
}
