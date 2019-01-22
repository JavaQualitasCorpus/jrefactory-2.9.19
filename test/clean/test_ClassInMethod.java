package test;

public class ClassInMethod {
	public void method() {
		interface Form {
			double getOne();
			double getTwo();
		}

		class Data implements Form {
			private double v1;
			private double v2;

			public Data(double init1, double init2) {
				v1 = init1; v2 = init2;
			}

			public double getOne() { return v1; }
			public double getTwo() { return v2; }
		}

		Data stuff = new Data(5, 2);
		System.out.println("Value:  " + stuff.getOne() + ", " + stuff.getTwo());
	}
}
