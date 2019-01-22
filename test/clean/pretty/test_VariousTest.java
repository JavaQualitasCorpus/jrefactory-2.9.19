package test;

/**
 *  Here is how you use a VariousTest object:<p>
 *
 *  <PRE>
 *     VariousTest vt = new VariousTest();
 *     if (vt.isNow()) {
 *         vt.method();
 *     }
 *  </PRE>
 */
public class VariousTest {
	/**
	 *  The constructor for <VariousTest>-objects
	 */
	public VariousTest() {
	}

	public void method() {
		if (true)
			System.out.println("No block around this please");
		else if (secondTest)
			System.out.println("No block here either");
		else if (thirdTest)
			System.out.println("Third test is here");
		else
			System.out.println("Finally to an if");

		if (true)
			local = 2;
		else
			local = 8;

		if (isTrue())
			return "Yep";
		else
			return "Nope";
	}

	public boolean isNow() {
		return true;
	}
}
