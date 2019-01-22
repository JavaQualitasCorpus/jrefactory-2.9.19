package pretty;

/**
 *  The first two produce the same result after "Remove {} when they surround
 *  only one statement" <p>
 *
 *  The fixing the bug means that the third part inserts a redundant { }, but
 *  this is acceptable in such a rare case as it does not lead to indentation
 *  errors.
 *
 *@author    Chris Seguin
 */
class Bug_605626 {

	/**
	 *  The main program for the Bug_605626 class
	 *
	 *@param  argv  The command line arguments
	 */
	public static void main(String[] argv) {
		boolean value = true;
		String[] test = new String[]{"rttr", "dfsgdf"};
		if (true) {
			if (value)
				for (int i = 0; i < test.length; i++)
					if (true)
						System.out.println("Hello");
					else
						System.out.println("Goodbye");


		}
		if (true) {
			if (value)
				for (int i = 0; i < test.length; i++) {
					if (true)
						System.out.println("Hi");

				}
			else
				System.out.println("Bye");

		}
		if (true) {
			if (value)
				for (int i = 0; i < test.length; i++) {
					if (true)
						System.out.println("Howdee");

				}

		}
		else
			System.out.println("Bye Bye");

	}
}
