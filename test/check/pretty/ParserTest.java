package test.parser;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ParserTest extends ImaginaryParentClass {
	/**
	 *  Constructor for the ParserTest object
	 */
	public ParserTest() {
		super();
	}


	/**
	 *  Constructor for the ParserTest object
	 *
	 *@param  initial  Description of Parameter
	 */
	public ParserTest(String initial) {
		this();

		System.out.println("Initial:  " + initial);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  value  Description of Parameter
	 */
	public abstract void abstractMethod(int value);


	/**
	 *  Description of the Method
	 *
	 *@param  name  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public String concreteMethod(String name) {

		//  First comment

		final interface InternalInterface {
			/**
			 *  Gets the Code attribute of the InternalInterface object
			 *
			 *@return    The Code value
			 */
			public String getCode();
		}

		//  Second comment

		final class InternalClass implements InternalInterface {
			String code;


			/**
			 *  Gets the Code attribute of the InternalClass object
			 *
			 *@return    The Code value
			 */
			public String getCode() {
				return code;
			}
		}

		InternalClass ic = new InternalClass();
		ic.code = name;

		//  Third comment
		ImaginaryParentClass.super.doIt();

		return ic.getCode();
	}
}
