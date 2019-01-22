package ref;

import other.NearMiss;
import ren.*;

public class ImportNotUse {
	public String compute() {
		return (new UsedClass()).getFieldValue() +
		(new NearMiss()).getFieldValue();
	}
}
