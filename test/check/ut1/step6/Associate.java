package imp;

import java.io.*;
import ren.ComplexNameConflict;
import TestClass;
import ren.TestState;

/**
 *  Relates to a Test Class object
 *
 *@author    Chris Seguin
 */
public class Associate {
	//  Instance Variables
	TestClass value;
	TestState simple;
	ren.AccessClassVar conflict;
	ComplexNameConflict complex;


	/**
	 *  Sets the Simple attribute of the Associate object
	 */
	public void setSimple() {
		simple = new TestState();
	}


	/**
	 *  Sets the Conflict attribute of the Associate object
	 */
	public void setConflict() {
		conflict = ren.AccessClassVar.factory();
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
