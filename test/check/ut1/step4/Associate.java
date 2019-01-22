package imp;

import java.io.*;
import ren.SimpleRename;
import ren.SimpleNameConflict;
import ren.ComplexNameConflict;
import TestClass;

/**
 *  Relates to a Test Class object
 *
 *@author    Chris Seguin
 */
public class Associate {
	//  Instance Variables
	TestClass value;
	SimpleRename simple;
	SimpleNameConflict conflict;
	ComplexNameConflict complex;


	/**
	 *  Sets the Simple attribute of the Associate object
	 */
	public void setSimple() {
		simple = new SimpleRename();
	}


	/**
	 *  Sets the Conflict attribute of the Associate object
	 */
	public void setConflict() {
		conflict = SimpleNameConflict.factory();
	}


	/**
	 *  Sets the ComplexConflict attribute of the Associate object
	 */
	public void setComplexConflict() {
		complex = ComplexNameConflict.factory();
	}


	/**
	 *  Body of the method
	 */
	public void run() {
		value = new TestClass();
		value.run();
	}
}
