package ref;

import other.NearMiss;
import ren.*;

public class FalseStatic {
	private StringBuffer OldClass;

	public String compute() {
		return OldClass.toString()
				+ UsedClass.classField
				+ NearMiss.classField;
	}
}
