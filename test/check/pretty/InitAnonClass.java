/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class InitAnonClass {
	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Object method() {
		Object obj =
			new Object() {
				private int temp;


				public int getTemp() {
					return temp;
				}
				{
					temp = 5;
				}
			};

		return obj;
	}
}
