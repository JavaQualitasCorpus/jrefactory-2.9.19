package field;

public class InheritFieldTest extends RenameFieldTest {
	private int code;
   public String anOtherMember;

	public InheritFieldTest() {
		code = 52;
	}

	public void run() {
		System.out.println("Code:  " + code);
		System.out.println("Height:  " + height);
	}
}
