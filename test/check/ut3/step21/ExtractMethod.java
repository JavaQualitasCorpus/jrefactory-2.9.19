package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtractMethod {
	private String name;
	private double value;
	private int count;


	/**
	 *  Constructor for the ExtractMethod object
	 *
	 *@param  init   Description of Parameter
	 *@param  title  Description of Parameter
	 */
	public ExtractMethod(double init, String title) {
		double start = init;
		while (start < 1024) {
			extractedMethod(start, title);
		}

		name = title;
		value = start;
	}


	/**
	 *  Description of the Method
	 */
	public void longMethod1() {
		int last = 10;

		for (int ndx = 0; ndx < last; ndx++) {
			if (ndx % 2 == 0) {
				System.out.print("Even  ");
			}
			else {
				System.out.print("Odd  ");
			}
			System.out.println(ndx);
		}
	}
	{
		count = 0;
		while (value > 1) {
			value /= 2.0;
			count++;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  start  Description of Parameter
	 *@param  title  Description of Parameter
	 */
	private void extractedMethod(double start, String title) {
		start *= 2;
		System.out.println(" " + title + "  " + start);
	}
}
