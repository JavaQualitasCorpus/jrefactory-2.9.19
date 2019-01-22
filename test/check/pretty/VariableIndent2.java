package test;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class VariableIndent
{
	/**
	 *  Description of the Method
	 *
	 *@param  code  Description of Parameter
	 */
	public void method1(String code)
	{
	int myValue = 52;

		System.out.println("My value is:  " + code);

	String ask = "Do you want it formatted this way?";
		System.out.println(ask);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  code  Description of Parameter
	 */
	public void method2(String code)
	{
	int myValue = 52;
	int myCode = myValue * myCode;

	String ask = "Do you want it formatted this way?";
	String other = ask;

		System.out.println("My value is:  " + code);

		System.out.println(ask);
	}
}
