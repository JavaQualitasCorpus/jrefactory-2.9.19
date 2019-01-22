package test;

public class JavadocCommentsTest {
	/**
	 *  This first method is short and simple
	 */
	public void methodOne() { System.out.println("One"); }
	/**
	 *  The second method is short and simple
	 *
	 *@param value the value to print
	 */
	public void methodTwo(int value) { System.out.println("Two:  " + value); }
	/**
	 *  The third method is short and simple
	 *
	 *@param value the value to print
	 *@return the message printed
	 */
	public String methodThree(int value) {
		String message = "Three:  " + value;
		System.out.println(message);
		return message;
	}
	/**
	 *  The fourth method is short and simple.  But it also needs to word wrap the words since they are much too long to fit on a single line.
	 *
	 *@param value the value to print
	 *@return the message printed
	 */
	public String methodFour(int value) {
		String message = "Four:  " + value;
		System.out.println(message);
		return message;
	}
	/**
	 *  The fifth method is short and simple.  But it also
	 *  <UL><LI>Uses codes that should break into multiple lines<LI>if they get that far</UL>
	 *
	 *
	 *@param value the value to print
	 *@return the message printed
	 */
	public String methodFive(int value) {
		String message = "Five:  " + value;
		System.out.println(message);
		return message;
	}

	/**
	 *  The sixth method is short and simple.  But it uses preformatted codes to describe how to use this method:<P>
	 *  <PRE><TT>
	 *     Call this method like:
	 *
	 *     String value = object.methodSix(32);
	 *     System.out.println("Value:  " + value);
	 *  </TT></PRE>
	 *
	 *
	 *@param value the value to print
	 *@return the message printed
	 */
	public String methodSix(int value) {
		String message = "Six:  " + value;
		System.out.println(message);
		return message;
	}
}
