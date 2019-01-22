public class LoadingProblem {
	private int counter;
	private static int initial;

	public LoadingProblem() {
		counter = LoadingProblem.initial;
	}

	public int getCounter() {
		return counter;
	}

	static {
		initial = 52;
	}

	public native int getSqrt(float value);
}
