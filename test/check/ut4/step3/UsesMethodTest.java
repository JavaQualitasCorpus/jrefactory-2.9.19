package method;

public class UsesMethodTest {
	private int height();
	private int code;
	private int code(int x);

	public UsesFieldTest() {
		height();
		code(2);
	}

	public void run() {
		RenameMethodTest rmt = new RenameMethodTest();

		rmt.code(3);
		rmt.width();
      RenameMethodTest.width();
      RenameMethodTest.height(1);
      InheritMethodTest.width();
      InheritMethodTest.height(1);

      InheritMethodTest imt = new InheritMethodTest();
		imt.code();
		imt.super.code();
	}
}
