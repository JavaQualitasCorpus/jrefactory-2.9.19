package method;

public class ExtractMethodTestTwo {
	private double start;
	private String text;

	public ExtractMethodTestTwo() {
		start = 5.0;
		text = "string";
	}

	public double methodTwo(double offset) {
		double init = 1.0;

		for (int ndx = 0; ndx < 10; ndx++) {
			double temp = start;
			temp = init * init;
			init += temp + offset;
		}

		if (
			init > 400
		)
			return init;
		else
			return 400;
	}

	public String methodOne (String input) {
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

	private void testMethod(Document original, Document result) throws IOException {
		StringBuffer originalSB = getDocAsStringBuffer(original);
		if (originalSB.length() != 50) {
		}
	}
}

