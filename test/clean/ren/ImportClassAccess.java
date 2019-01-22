package ref;

import other.NearMiss;
import ren.*;

public class ImportClassAccess {
	public static void main(String[] args) {
		Class class1 = OldClass.class;
		Class class2 = UsedClass.class;
		Class class3 = NearMiss.class;
	}
}
