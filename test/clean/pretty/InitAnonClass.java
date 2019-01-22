public class InitAnonClass {
	public Object method() {
		Object obj = new Object() {
			private int temp;
			{
				temp = 5;
			}

			public int getTemp() {
				return temp;
			}
		};

		return obj;
	}
}
