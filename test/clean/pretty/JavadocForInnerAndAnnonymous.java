package pretty;

public class JavadocForInnerAndAnnonymous {
	public JavadocForInnerAndAnnonymous() {
	}

	public void method() {
		class TempClass1 {
			public String toString() { return "A Temp Class 1"; }
		}

		interface TempInterface1 {
			public void temp();
		}

		/**  Just a little javadoc that has been inserted */
		class TempClass4 {
			public String toString() { return "A Temp Class 4"; }
		}

		Object obj1 = new TempClass1();

		Object obj2 = new Object() {
				public String toString() { return "A Temp Class 2"; }
		};

		System.out.println("Message:  " + obj1.toString() + ":" + obj2.toString());
	}

		class TempClass3 {
			public String toString() { return "A Temp Class 3"; }
		}

		interface TempInterface2 {
			public void temp();
		}
}
