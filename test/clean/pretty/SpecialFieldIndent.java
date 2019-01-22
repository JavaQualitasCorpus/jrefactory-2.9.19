package test;

public class SpecialFieldIndent {
	public final static String key = "super";
	protected int value = 7;

	class NestedClass {
		protected final String data = "never mind";
		private int id = 15;
		private transient SpecialFieldIndent parent = null;
	}

	public void method() {
		final String var = "32";
		int veryVeryVeryLargeNumber = 2 * 800000;
		if (veryVeryVeryLargeNumber > 4) {
			int result = 15;
		}
	}
}
