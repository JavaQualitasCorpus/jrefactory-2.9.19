package pretty;

public class JavadocStyleTest {
	/**  A simple field description */
	private boolean fieldOne;

	/**  A more complex field description hidden<br> */
	private boolean fieldTwo;

	/**  A very long line that contains a lot of text, but has no other reason to be broken into multiple lines */
	private boolean fieldThree;

	public void method() {
		/*  A simple method that could possibly fit on a single line */
		System.out.println("It works!");
	}

	public void method(int code) {
		/*  A simple method that should not fit on a single line */
		System.out.println("It works!");
	}
}
