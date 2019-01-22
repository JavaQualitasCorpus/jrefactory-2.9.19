package method.param;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestRenameParamSample {
	private int paramName;


	/**
	 *  Constructor for the TestRenameParamSample object
	 *
	 *@param  height  Description of Parameter
	 */
	public TestRenameParamSample(int height) {
		this.paramName = height;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean paramName() {
		System.out.println("This is a bad name for a method");
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  paramName  Description of Parameter
	 *@return            Description of the Returned Value
	 */
	public boolean useParam(String paramName) {
		System.out.println("Everything worked:  " + paramName.substring(0, 2));
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  paramName  Description of Parameter
	 *@param  radius     Description of Parameter
	 *@return            Description of the Returned Value
	 */
	public double function(double paramName, double radius) {


		class NestedClass {
			private double paramName;


			/**
			 *  Constructor for the NestedClass object
			 *
			 *@param  init  Description of Parameter
			 */
			public NestedClass(double init) {
				paramName = init;
			}


			/**
			 *  Description of the Method
			 *
			 *@return    Description of the Returned Value
			 */
			public double square() {
				return paramName * paramName;
			}
		}

		NestedClass nc = new NestedClass(paramName);
		return radius * nc.square() + paramName;
	}
}
