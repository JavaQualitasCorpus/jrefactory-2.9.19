package ref;

import other.NearMiss;
import ren.*;

public class ImportAnonymousField {
private		Object item = new OldClass() {
			public String getFieldValue() {
				return "temp";
			}
		};

private		Object item2 = new UsedClass() {
			public String getFieldValue() {
				return "used";
			}
		};

private		Object item3 = new NearMiss() {
			public String getFieldValue() {
				return "near";
			}
		};
}