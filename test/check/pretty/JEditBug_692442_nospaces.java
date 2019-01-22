package pretty;




/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class JEditBug_692442 {

	/**
	 *  The main program for the JEditBug_692442 class
	 *
	 *@param  argv  The command line arguments
	 */
	public static void main(String[] argv) {
		switch(args[1]) {
			case 1:
				if(x < 15) {
					doSomething();
				}
				break;
			default:
				doSomethingElse();
				break;
		}
	}
}

