package method;

public class MoveMethodSource2 {
	public int value;

	public void method1(MoveMethodDest2 dest) {
		System.out.println("Value:  " + value);
	}

	public void method2(MoveMethodDest2 dest) {
		System.out.println("No contact");
	}

	public void method3(String input) {
		System.out.println("Input:  " + input);
	}

	public void method4(int total) {
		value = total;
	}
}
