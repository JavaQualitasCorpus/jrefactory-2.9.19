package count;

import java.io.*;
import java.util.LinkedList;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TypeCount
		 extends Object
		 implements Serializable,
		Cloneable {

	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class LocalType
			 extends LinkedList {
		private double total;


		/**
		 *  Description of the Method
		 */
		public abstract void average();
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	interface Checksum extends Serializable
			, Cloneable {
		/**
		 *  Description of the Method
		 *
		 *@return    Description of the Returned Value
		 */
		public String compute();
	}
}
