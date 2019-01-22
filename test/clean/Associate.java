package imp;

import java.io.*;
import TestClass;
import ren.SimpleRename;
import ren.SimpleNameConflict;
import ren.ComplexNameConflict;

/**
 *  Relates to a Test Class object
 *
 */
public class Associate {
	//  Instance Variables
	TestClass value;
	SimpleRename simple;
	SimpleNameConflict conflict;
	ComplexNameConflict complex;


	/**
	 *  Body of the method
	 */
	public void run() {
		value = new TestClass();
		value.run();
	}

	public void setSimple() {
		simple = new SimpleRename();
	}

	public void setConflict() {
		conflict = SimpleNameConflict.factory();
	}

	public void setComplexConflict() {
		complex = ComplexNameConflict.factory();
	}
}
