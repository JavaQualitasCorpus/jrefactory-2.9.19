package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportImplements {
	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class One implements NewClass {
		double d1;
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Two implements UsedClass {
		int i2;
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Three implements NearMiss {
		boolean b3;
	}
}
