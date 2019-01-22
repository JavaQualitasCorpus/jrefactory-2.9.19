package sample;

import java.io.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public strictfp class TestStrictFP
		 implements Serializable, TestStrictFPInterface {
	/**
	 *  Gets the Value attribute of the TestStrictFP object
	 *
	 *@return    The Value value
	 */
	public strictfp double getValue() {
		return 2.0;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	strictfp double increment() {
		return getValue() + 7.3;
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	strictfp class NestedClass implements NestedInterface {
		/**
		 *  Gets the Value attribute of the NestedClass object
		 *
		 *@return    The Value value
		 */
		public strictfp double getValue() {
			return 4.0;
		}
	}


	/**
	 *  Description of the Interface
	 *
	 *@author    Chris Seguin
	 */
	strictfp interface NestedInterface {
		/**
		 *  Gets the Value attribute of the NestedInterface object
		 *
		 *@return    The Value value
		 */
		public strictfp double getValue();
	}
}

/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
strictfp interface TestStrictFPInterface {


	/**
	 *  Gets the Value attribute of the TestStrictFPInterface object
	 *
	 *@return    The Value value
	 */
	public strictfp double getValue();
}

