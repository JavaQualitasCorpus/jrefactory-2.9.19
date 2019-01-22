package field;

public class UsesFieldTest {
	private String simple;
	private int code;

	public UsesFieldTest() {
		code = RenameFieldTest.CODE_OFF;
		simple = "high";
	}

	public void run() {
		RenameFieldTest rft = new RenameFieldTest();

		rft.setCode(RenameFieldTest.CODE_ON);
		rft.height = 32;

		code = RenameFieldTest.CODE_ON;
		simple = "turn it on!";
	}

}
