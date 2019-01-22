package test;

public class ClassInMethod {
	public void method() {
		class TempClass {
			int value;

			public void setValue(int k) { value = k; }

			public String toString() { return "" + value; }
		};

		TempClass tc = new TempClass();
		tc.setValue(5);
		System.out.println("XXX:  " + tc.toString());
	}
}
