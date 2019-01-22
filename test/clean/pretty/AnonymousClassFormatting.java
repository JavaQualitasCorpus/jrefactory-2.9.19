package other;

public class AnonymousClassFormatting {
	private Thread data = new Thread() {
		public void run() {
			System.out.println("Running thread one");
		}
	};
	private int value = 0;

	public Thread getData() {
		if (data == null) {
			data = new Thread() {
				public void run() { System.out.println("Running thread two"); }
			};
			value++;
		}

		return data;
	}

	public void reset() { data = null; }

	public Thread create() {
		return new Thread() {
			public void run() { System.out.println("Running thread three"); }
		};
		//  This is the end
	}

	public void test() {
		other.eval(new Thread() {
			public void run() {
				System.out.println("Running thread four");
			}
		}, 52);
	}
}
