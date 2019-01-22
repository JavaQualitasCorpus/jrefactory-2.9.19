/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Outer extends GeneralOuter {

	static int initial;
	private final static volatile int code = 69;


	/**
	 *  Description of the Method
	 */
	public void doSomething() {
	}


	/**
	 *  This code should have the wrong values in it
	 *
	 *@param  myInt            Description of Parameter
	 *@return                  a boolean value, this should be there in either
	 *      case
	 *@exception  Exception    Description of Exception
	 */
	public int anotherMethod(int myInt) throws Exception {
		System.out.println("This is it:  " + myInt);
		switch (myInt) {
			case 0:
				System.out.println("It was not active");
				break;
			default:
				System.out.println("It was active");
		}
		throw new Exception("Always fails");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  myBool  Description of Parameter
	 */
	public void assertTest(boolean myBool) {
		assert myBool;
		assert !myBool : "The issue is that it is not set";
	}


	/**
	 *  Description of the Method
	 */
	public native static void myMethod();


	/**
	 *  Description of the Method
	 */
	public final static synchronized void opsTest() {
		int x = 3 + 2;
		int y = 4 * 6 > 12 ? -2 : ~0xAF;
		boolean z = 4 * 6 > 12 ? (12 == 21 - 8) : (x > y) || (x * 3 > y);
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	public class Inner {
		/**
		 *  Description of the Method
		 */
		public void doSomething() {
			// this line doesn't make any problems
			Outer.this.doSomething();
			// this stops the pretty printer and gives an
			//parser exception (stumbling over the .super)
			Outer.super.doSomething();
		}
	}
	static {
		initial = 0;
	}
}
