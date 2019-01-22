package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtendedExtractMethodTester {
	/**
	 *  Description of the Method
	 *
	 *@param  value  Description of Parameter
	 */
	public void localVariableTest(int value) {
		System.out.println("Before what we are extracting.  " + value);
		String keyword = extractedMethod(value);
		System.out.println("After what we are extracting.  " + keyword);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  uppercase  Description of Parameter
	 *@return            Description of the Returned Value
	 */
	public static String staticMethodTest(String uppercase) {
		StringBuffer buffer = new StringBuffer();

		for (int ndx = 0; ndx < uppercase.length(); ndx++) {
			char ch = uppercase.charAt(ndx);
			if (ch < 32) {
				buffer.append("[" + ((int) ch) + "]");
			}
			else {
				buffer.append(Character.toUpperCase(ch));
			}
		}

		return buffer.toString();
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class Pair {
		private String first;
		private String second;


		/**
		 *  Sets the First attribute of the Pair object
		 *
		 *@param  value  The new First value
		 */
		public void setFirst(String value) {
			first = value;
		}


		/**
		 *  Sets the Second attribute of the Pair object
		 *
		 *@param  value  The new Second value
		 */
		public void setSecond(String value) {
			second = value;
		}


		/**
		 *  Description of the Method
		 *
		 *@return    Description of the Returned Value
		 */
		public int computeLength() {
			int firstValue = 0;
			firstValue =
					Integer.parseInt(first.substring(0, 4))
					;

			int secondValue = 0;
			secondValue = Integer.parseInt(second.substring(0, 4));

			return firstValue % secondValue;
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  value  Description of Parameter
	 *@return        Description of the Returned Value
	 */
	private String extractedMethod(int value) {
		String keyword = "data";
		if (value <= 32) {
			keyword = keyword + "." + value;
		}
		else {
			keyword = keyword + "[" + ((char) value) + "]";
		}
		return keyword;
	}
}
