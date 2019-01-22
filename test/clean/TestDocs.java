public class TestDocs {
	private int value;

	public int getAttribute1() {
		return value;
	}

	public int transmit() {
		return 1;
	}

	public void setAttribute1(int value) {
		this.value = value;
	}

	class NestedData {
		private int value2;

		public int getAttribute2() {
			return value2;
		}

		public void setAttribute2(int value) {
			value2 = value;
		}
	}

	public boolean isAttributeEven() {
		return value % 2 == 0;
	}

	public static boolean isSingleton() {
		return false;
	}
}

