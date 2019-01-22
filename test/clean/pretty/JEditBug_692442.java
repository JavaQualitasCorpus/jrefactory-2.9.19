package pretty;

public class JEditBug_692442 {

	public static void main(String[] argv) {
		switch(args[1]) {
			case 1:
				if (x < 15) {
					doSomething();
				}
				break;
			default:
				doSomethingElse();
				break;
		}
	}
}

