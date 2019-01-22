package method;

public class ExtractMethod {
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

	public ExtractMethod(double init, String title) {
		double start = init;
		while (start < 1024) {
			start *= 2;
			System.out.println(" " + title + "  " + start);
		}

		name = title;
		value = start;
	}
	private String name;
	private double value;
	private int count;

	{
		count = 0;
		while (value > 1) {
			value /= 2.0;
			count++;
		}
	}
}
