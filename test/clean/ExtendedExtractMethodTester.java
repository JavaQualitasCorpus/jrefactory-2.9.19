package method;

public class ExtendedExtractMethodTester {
	public void localVariableTest(int value) {
		System.out.println("Before what we are extracting.  " + value);
		String keyword = "data";
		if (value <= 32) {
			keyword = keyword + "." + value;
		}
		else {
			keyword = keyword + "[" + ((char) value) + "]";
		}
		System.out.println("After what we are extracting.  " + keyword);
	}

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

	private class Pair {
		private String first;
		private String second;

		public void setFirst(String value) { first = value; }
		public void setSecond(String value) { second = value; }

		public int computeLength() {
			int firstValue = 0;
			firstValue =
				Integer.parseInt(first.substring(0,4))
				;

			int secondValue = 0;
			secondValue = Integer.parseInt(second.substring(0, 4));

			return firstValue % secondValue;
		}
	}
}
