package method;

public class UsesMethodTest {
	private int height() {}
	private int code;
	private int code(int x) {}

	public UsesFieldTest() {
		height();
		code(2);
	}

	public void run() {
		RenameMethodTest rmt = new RenameMethodTest();

		rmt.code();
		rmt.code(3);
		rmt.height();
		rmt.height(3);
      RenameMethodTest.height();
      RenameMethodTest.height(1);
      InheritMethodTest.height();
      InheritMethodTest.height(1);

      InheritMethodTest imt = new InheritMethodTest();
		imt.code();
		imt.super.code();
	}
}
