package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
class Bug_761890 {

	/**
	 *  Constructor for the Bug_761890 object
	 */
	Bug_761890() {
		int a = 2;// comment
		a = 3;/* c style on-line */

		a = 4;
		/*
		 *  c style before-line a=5
		 */
		a = 5;
		/*
		 *  c style before-line 2 a=6
		 */
		a = 6;
	}
}
