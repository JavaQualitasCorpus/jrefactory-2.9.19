package pretty;




/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class JEditBug_570143 {

	/**
	 *  Description of the Method
	 */
	void toto() {
		if (a) {
		}
		else
		// a comment
		if (b) {
			toto();
		}

		if (c) {
		}
		else if (d) {
			toto();
		}
	}
}

