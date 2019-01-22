package method;

public class InheritMethodTest extends RenameMethodTest {
	public int code() {}
	public int height() {}

	public InheritMethodTest() {
		code(52);
      height();
      RenameMethodTest.height(42);
	}

	public void run() {
		System.out.println("Code:  " + code());
		System.out.println("Code:  " + code(1));
		System.out.println("Height:  " + height());
		System.out.println("Height:  " + RenameMethodTest.width());
	}
}
