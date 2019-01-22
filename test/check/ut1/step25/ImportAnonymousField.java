package ref;

import other.NearMiss;
import ren.UsedClass;
import ren.NewClass;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ImportAnonymousField {
	private Object item =
		new NewClass() {
			public String getFieldValue() {
				return "temp";
			}
		};

	private Object item2 =
		new UsedClass() {
			public String getFieldValue() {
				return "used";
			}
		};

	private Object item3 =
		new NearMiss() {
			public String getFieldValue() {
				return "near";
			}
		};
}
