package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
class Bug_758816 {

	/**
	 *  Constructor for the Bug_758816 object
	 */
	Bug_758816() {
		boolean negative = true;
		Long result = new Long(10);
		result = negative ? new Long((long) -result.longValue()) : result;
	}
}
