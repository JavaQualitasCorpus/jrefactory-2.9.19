package ref;

import other.NearMiss;
import ren.*;

public class ImportCast {
	private Object one;
	private Object two;
	private Object three;

	public String compute() {
		return ((OldClass) one).getFieldValue() +
		((UsedClass) two).getFieldValue() +
		((NearMiss) three).getFieldValue();
	}
}
