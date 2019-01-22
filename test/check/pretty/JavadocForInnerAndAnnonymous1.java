package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class JavadocForInnerAndAnnonymous {
	/**
	 *  Constructor for the JavadocForInnerAndAnnonymous object
	 */
	public JavadocForInnerAndAnnonymous() {
	}


	/**
	 *  Description of the Method
	 */
	public void method() {


		class TempClass1 {
			/**
			 *  Converts to a String representation of the object.
			 *
			 *@return    A string representation of the object.
			 */
			public String toString() {
				return "A Temp Class 1";
			}
		}


		interface TempInterface1 {
			/**
			 *  Description of the Method
			 */
			public void temp();
		}


		class TempClass4 {
			/**
			 *  Converts to a String representation of the object.
			 *
			 *@return    A string representation of the object.
			 */
			public String toString() {
				return "A Temp Class 4";
			}
		}

		Object obj1 = new TempClass1();

		Object obj2 =
			new Object() {
				public String toString() {
					return "A Temp Class 2";
				}
			};

		System.out.println("Message:  " + obj1.toString() + ":" + obj2.toString());
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class TempClass3 {
		/**
		 *  Converts to a String representation of the object.
		 *
		 *@return    A string representation of the object.
		 */
		public String toString() {
			return "A Temp Class 3";
		}
	}


	/**
	 *  Description of the Interface
	 *
	 *@author    Chris Seguin
	 */
	interface TempInterface2 {
		/**
		 *  Description of the Method
		 */
		public void temp();
	}
}
