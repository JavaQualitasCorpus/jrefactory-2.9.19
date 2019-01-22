package ref;

import other.NearMiss;
import ren.*;

public class ImportStaticMethodRef {
	public String compute() {
		return OldClass.getClassField() +
		UsedClass.getClassField() +
		NearMiss.getClassField();
	}
}
