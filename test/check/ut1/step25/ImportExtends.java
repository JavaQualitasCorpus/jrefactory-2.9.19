package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportExtends {
	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class One extends NewClass {
		double d1;
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Two extends UsedClass {
		int i2;
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Three extends NearMiss {
		boolean b3;
	}
}
