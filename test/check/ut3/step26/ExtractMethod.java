package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtractMethodTestTwo {
	private double start;
	private String text;


	/**
	 *  Constructor for the ExtractMethodTestTwo object
	 */
	public ExtractMethodTestTwo() {
		start = 5.0;
		text = "string";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  offset  Description of Parameter
	 *@return         Description of the Returned Value
	 */
	public double methodTwo(double offset) {
		double init = 1.0;

		for (int ndx = 0; ndx < 10; ndx++) {
			double temp = start;
			temp = init * init;
			init += temp + offset;
		}

		if (extractedMethod(init)
				) {
			return init;
		}
		else {
			return 400;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  input  Description of Parameter
	 *@return        Description of the Returned Value
	 */
	public String methodOne(String input) {
		StringBuffer buf = new StringBuffer();
		while (buf.length() < 50) {
			String msg = null;
			msg =
					text + " " + buf.length() + input
					;
			buf.append(msg);
		}

		return buf.toString();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@param  original         Description of Parameter
	 *@param  result           Description of Parameter
	 *@exception  IOException  Description of Exception
	 */
	private void testMethod(Document original, Document result) throws IOException {
		StringBuffer originalSB = getDocAsStringBuffer(original);
		if (originalSB.length() != 50) {
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  init  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	private boolean extractedMethod(double init) {
		return init > 400;
	}
}

