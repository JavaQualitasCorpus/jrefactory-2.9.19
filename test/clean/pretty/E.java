public class Outer extends GeneralOuter {
	final private volatile static int code = 69;
    public void doSomething() {
    }

    public static native void myMethod();

    /**
     *  This code should have the wrong values in it
     *@param theInt  the value of myInt, but wrong name
     *@exception IOException this is always thrown
     *@return a boolean value, this should be there in either case
     */
    public int anotherMethod(int myInt) throws Exception
    {
    	System.out.println("This is it:  " + myInt);
    	switch(myInt) {
    		case 0:
    			System.out.println("It was not active");
    			break;
    		default:
    			System.out.println("It was active");
    	}
    	throw new Exception("Always fails");
    }

    public void assertTest(boolean myBool)
    {
    	assert myBool;
    	assert !myBool : "The issue is that it is not set";
    }

    synchronized public final static void opsTest()
    {
    	int x = 3 + 2;
    	int y = 4 * 6 > 12 ? -2 : ~0xAF;
    	boolean z = 4 * 6 > 12 ? (12 == 21 - 8) : (x > y) || (x * 3 > y);
    }

    public class Inner {
        public void doSomething() {
            // this line doesn't make any problems
           Outer.this.doSomething();
            // this stops the pretty printer and gives an
            //parser exception (stumbling over the .super)
          Outer.super.doSomething();
        }
    }

    static int initial;
    static {
    	initial = 0;
    };
}
