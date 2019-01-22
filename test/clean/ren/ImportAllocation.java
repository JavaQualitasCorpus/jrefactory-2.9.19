package ref;

import other.NearMiss;
import ren.*;

public class ImportAllocation {
	public String compute() {
		return (new OldClass()).getFieldValue() +
		(new UsedClass()).getFieldValue() +
		(new NearMiss()).getFieldValue();
	}
}
