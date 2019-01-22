/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 *@created    November 18, 1999
 */
public class TestDocs {
	private int value;


	/**
	 *  Sets the Attribute1 attribute of the TestDocs object
	 *
	 *@param  value  Description of Parameter
	 */
	public void setAttribute1(int value) {
		this.value = value
	}


	/**
	 *  Gets the Attribute1 attribute of the TestDocs object
	 *
	 *@return    Description of the Returned Value
	 */
	public int getAttribute1() {
		return value;
	}


	/**
	 *  Gets the AttributeEven attribute of the TestDocs object
	 *
	 *@return    Description of the Returned Value
	 */
	public boolean isAttributeEven() {
		return value % 2 == 0;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public int transmit() {
		return 1;
	}


	class NestedData {
		private int value2;


		/**
		 *  Sets the Attribute2 attribute of the NestedData object
		 *
		 *@param  value  Description of Parameter
		 */
		public void setAttribute2(int value) {
			value2 = value;
		}


		/**
		 *  Gets the Attribute2 attribute of the NestedData object
		 *
		 *@return    Description of the Returned Value
		 */
		public int getAttribute2() {
			return value2;
		}
	}
}
