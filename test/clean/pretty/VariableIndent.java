package pretty;

public class VariableIndent {
	public static final int VERY_VERY_VERY_VERY_LONG_CONSTANT = 59;
	private String id = "simple";

	public VariableIndent() {
		String message = id + "2";
		int code = VERY_VERY_VERY_VERY_LONG_CONSTANT * 2;

		System.out.println("The message is:  " + message);
		System.out.println("The code is:     " + code);
	}
}
