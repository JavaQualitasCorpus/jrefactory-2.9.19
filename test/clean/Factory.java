package imp;

import TestClass;

/**
 *  Tests if we find stuff in allocation expressions
 *
 */
public class Factory {
	public Object createNew() {
		return new TestClass();
	}
}
