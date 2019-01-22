package ref;

import other.NearMiss;
import ren.*;

public class ImportStaticFieldRef {
	public String compute() {
		return OldClass.classField
				+ UsedClass.classField
				+ NearMiss.classField;
	}
}
