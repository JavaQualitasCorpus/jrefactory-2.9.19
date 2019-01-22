package ref;

import other.NearMiss;
import ren.*;

public class ImportExtends {
	class One extends OldClass {
		double d1;
	}

	class Two extends UsedClass {
		int i2;
	}

	class Three extends NearMiss {
		boolean b3;
	}
}
