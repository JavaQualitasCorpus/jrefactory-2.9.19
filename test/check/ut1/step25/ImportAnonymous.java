package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportAnonymous {
	/**
	 *  The main program for the ImportAnonymous class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		Object item =
			new NewClass() {
				public String getFieldValue() {
					return "temp";
				}
			};

		Object item2 =
			new UsedClass() {
				public String getFieldValue() {
					return "used";
				}
			};

		Object item3 =
			new NearMiss() {
				public String getFieldValue() {
					return "near";
				}
			};
	}
}
