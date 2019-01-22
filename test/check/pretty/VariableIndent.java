package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class VariableIndent {
	private              String  id                                 = "simple";
	/**
	 *  Description of the Field
	 */
	public final static  int     VERY_VERY_VERY_VERY_LONG_CONSTANT  = 59;


	/**
	 *  Constructor for the VariableIndent object
	 */
	public VariableIndent() {
		String  message  = id + "2";
		int     code     = VERY_VERY_VERY_VERY_LONG_CONSTANT * 2;

		System.out.println("The message is:  " + message);
		System.out.println("The code is:     " + code);
	}
}
