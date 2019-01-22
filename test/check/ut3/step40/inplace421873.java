/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package method.param;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestRenameParamSample
{
	private int paramName;


	/**
	 *  Constructor for the TestRenameParamSample object
	 *
	 *@param  paramName  Description of Parameter
	 */
	public TestRenameParamSample(int paramName)
	{
		this.paramName = paramName;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean paramName()
	{
		System.out.println("This is a bad name for a method");
		return true;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  paramName  Description of Parameter
	 *@param  radius     Description of Parameter
	 *@return            Description of the Returned Value
	 */
	public double function(double paramName, double radius)
	{
class NestedClass
		{


			private double paramName;


			/**
			 *  Constructor for the NestedClass object
			 *
			 *@param  init  Description of Parameter
			 */
			public NestedClass(double init)
			{
				paramName = init;
			}


			/**
			 *  Description of the Method
			 *
			 *@return    Description of the Returned Value
			 */
			public double square()
			{
				return paramName * paramName;
			}
		}

		NestedClass nc = new NestedClass(paramName);
		return radius * nc.square() + paramName;
	}
}
