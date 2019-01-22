package test.variables;

/**
 *  This is a variable formatting tester
 *
 *@author    Chris Seguin
 */
public class VariableFormattingTester {
	/**
	 *  Here is another value
	 */
	protected String anotherValue = "Some where over the rainbow";

	private  java.util.Date  date;
	private  double          age   = 31.3;
	/**
	 *  This is a constant
	 */
	public final static int CONSTANT = 52;


	/**
	 *  Description of the Method
	 *
	 *@param  key    Description of Parameter
	 *@param  delta  Description of Parameter
	 *@return        Description of the Returned Value
	 */
	public int firstMethod(int key, double delta) {
		       double  changed            = age + delta;
		final  double  MIDDLE_AGE_CUTOFF  = 60;

		if (changed > MIDDLE_AGE_CUTOFF) {
			return key;
		}
		else {
			return (int) Math.ceil(changed);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  value  Description of Parameter
	 */
	public void secondMethod(int value) {
		long    total         = value;
		String  finalMessage  = "This is now equal to:  ";
		for (int ndx = 0; ndx < CONTSTANT; ndx++) {
			long  temp  = total * 2;
			total = temp;
		}

		System.out.println(finalMessage + total);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  input  Description of Parameter
	 */
	public void thirdMethod(BufferedReader input) {
		int  countLines  = 0;
		try {
			while (input.readLine() != null) {
				countLines++;
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}
	}

}
