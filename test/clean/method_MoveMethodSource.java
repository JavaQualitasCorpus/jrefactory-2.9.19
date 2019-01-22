package method;

public class MoveMethodSource  {
	public int incr1;
	private int increment;
	private int incr2;

	public MoveMethodSource() {
		incr1 = 5;
		incr2 = 1;
		increment = 3;
	}
	public int getIncrement() {
		return increment;
	}
	public void methodOne(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += 2;
		mmd.setCode(value);
	}

	public void methodTwo(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += incr1;
		mmd.setCode(value);
	}

	public void methodThree(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += increment;
		mmd.setCode(value);
	}

	public void methodFour(MoveMethodDest mmd) {
		int value = mmd.getCode();
		value += incr2;
		mmd.setCode(value);
	}

	public String publicMethod() {
		return "hello";
	}

	public void methodFive(MoveMethodDest mmd) {
		mmd.setTitle(publicMethod() + ":  " + mmd.getCode());
	}

	protected String protectedMethod() {
		return "confidential";
	}

	public void methodSix(MoveMethodDest mmd) {
		mmd.setTitle(protectedMethod() + ":  " + mmd.getCode());
	}

	String packageMethod() {
		return "secret";
	}

	public void methodSeven(MoveMethodDest mmd) {
		mmd.setTitle(packageMethod() + ":  " + mmd.getCode());
	}

	public void methodEight(OtherMethodDest mmd) {
		mmd.setTitle(packageMethod() + ":  " + mmd.getCode());
	}

	private String privateMethod() {
		return "topSecret";
	}

	public void methodNine(MoveMethodDest mmd) {
		mmd.setTitle(privateMethod() + ":  " + mmd.getCode());
	}
}
