package pretty;

public class SortTestData {
	public SortTestData() {
		code = 2;
	}
	public void setCode(int value) {
		code = value;
	}
	public int getCode() {
		return value;
	}
	public void run() {
		System.out.println("The code is " + code);
	}
	public static void main(String[] args) {
		SortTestData std = new SortTestData();
		std.setCode(69);
		std.run();
	}
	private int code;
	final int code2;
	protected int code3;
	public int code4;
}
