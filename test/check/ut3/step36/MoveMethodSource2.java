package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class MoveMethodSource2 {
	/**
	 *  Description of the Field
	 */
	public int value;


	/**
	 *  Description of the Method
	 *
	 *@param  dest  Description of Parameter
	 */
	public void method1(MoveMethodDest2 dest) {
		System.out.println("Value:  " + value);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  input  Description of Parameter
	 */
	public void method3(String input) {
		System.out.println("Input:  " + input);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  total  Description of Parameter
	 */
	public void method4(int total) {
		value = total;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  dest  Description of Parameter
	 */
	public void method2(MoveMethodDest2 dest) {
		dest.method2();
	}
}
