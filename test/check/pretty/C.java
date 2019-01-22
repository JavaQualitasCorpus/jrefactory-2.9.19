/**
 *  This is a class with some new formatting
 *  <DL>
 *    <DT> C
 *    <DD> is a class
 *    <DT> M
 *    <DD> is a method with lots of nested parens
 *  </DL>
 *  And that is all she wrote!
 *
 *@author    Chris Seguin
 */
public class C {
	/**
	 *@param  code  Description of Parameter
	 */
	public C(int code) {
		System.out.println("Code:  " + code);
	}


	/**
	 *  Here is an unconnected javadoc comment. It should appear only once.
	 */

	/**
	 *  Here is the real javadoc component
	 */
	public void M() {
		m((((((1))))));
	}
}
