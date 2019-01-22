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
			start *= 2;
			System.out.println(" " + title + "  " + start);
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
		extractedMethod();
	}


	/**
	 *  Description of the Method
	 */
	private void extractedMethod() {
		while (value > 1) {
			value /= 2.0;
			count++;
		}
	}
}
