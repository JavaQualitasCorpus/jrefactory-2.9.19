package method.param;

public class TestRenameParamSample {
	private int paramName;

	public TestRenameParamSample(int paramName) {
		this.paramName = paramName;
	}

	public boolean paramName() {
		System.out.println("This is a bad name for a method");
		return true;
	}

	public boolean useParam(String paramName) {
		System.out.println("Everything worked:  " + paramName.substring(0, 2));
		return true;
	}

	public double function(double paramName, double radius) {
		class NestedClass {
			private double paramName;

			public NestedClass(double init) {
				paramName = init;
			}

			public double square() {
				return paramName * paramName;
			}
		}

		NestedClass nc = new NestedClass(paramName);
		return radius * nc.square() + paramName;
	}
}
