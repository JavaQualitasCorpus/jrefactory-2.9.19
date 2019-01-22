package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
class Bug_465568 {

	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Inner1 {
		/**
		 *  Constructor for the Inner1 object
		 */
		Inner1() {
		}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Inner2 extends Inner1 {
		/**
		 *  Constructor for the Inner2 object
		 *
		 *@param  a  Description of Parameter
		 */
		Inner2(Bug_465568 a) {
			a.super();
		}
	}
}

