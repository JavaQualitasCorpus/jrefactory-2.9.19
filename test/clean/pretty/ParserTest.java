package test.parser;

public class ParserTest extends ImaginaryParentClass {
	public ParserTest() {
		super();
	}

	public ParserTest(String initial) {
		this();

		System.out.println("Initial:  " + initial);
	}

	public abstract void abstractMethod(int value);;;

	public String concreteMethod(String name) {
		//  First comment
		final interface InternalInterface {
			public String getCode();
		}
		//  Second comment
		final class InternalClass implements InternalInterface {
			String code;

			public String getCode() { return code; }
		}

		InternalClass ic = new InternalClass();
		ic.code = name;

		//  Third comment
		ImaginaryParentClass.super.doIt();

		return ic.getCode();
	}
}
