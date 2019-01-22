/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class LoadingProblem {
	private int counter;
	private static int initial;


	/**
	 *  Constructor for the LoadingProblem object
	 */
	public LoadingProblem() {
		counter = LoadingProblem.initial;
	}


	/**
	 *  Gets the Counter attribute of the LoadingProblem object
	 *
	 *@return    The Counter value
	 */
	public int getCounter() {
		return counter;
	}


	/**
	 *  Gets the Sqrt attribute of the LoadingProblem object
	 *
	 *@param  value  Description of Parameter
	 *@return        The Sqrt value
	 */
	public native int getSqrt(float value);

	static {
		initial = 52;
	}
}
