package imp;

import TestClass;
import abstracter.*;

public class TypeChecker {
	public boolean check(Object data) {
		return data instanceof TestClass;
	}

	public NormalClass getNew() {
		return new NormalClass();
	}
}
