package ref;

import other.NearMiss;
import ren.*;

public class ImportAnonymous {
	public static void main(String[] args) {
		Object item = new OldClass() {
			public String getFieldValue() {
				return "temp";
			}
		};

		Object item2 = new UsedClass() {
			public String getFieldValue() {
				return "used";
			}
		};

		Object item3 = new NearMiss() {
			public String getFieldValue() {
				return "near";
			}
		};
	}
}
