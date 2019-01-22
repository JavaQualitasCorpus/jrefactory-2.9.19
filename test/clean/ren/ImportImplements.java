package ref;

import other.NearMiss;
import ren.*;

public class ImportImplements {
	class One implements OldClass {
		double d1;
	}

	class Two implements UsedClass {
		int i2;
	}

	class Three implements NearMiss {
		boolean b3;
	}
}
