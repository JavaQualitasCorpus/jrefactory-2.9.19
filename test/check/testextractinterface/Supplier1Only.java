package testextractinterface;
/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface Supplier1Only {
	/**
	 *  Sets the FirstName attribute of the Supplier1Only object
	 *
	 *@param  firstName  The new FirstName value
	 */
	public void setFirstName(String firstName);


	/**
	 *  Sets the LastName attribute of the Supplier1Only object
	 *
	 *@param  lastName  The new LastName value
	 */
	public void setLastName(String lastName);


	/**
	 *  Sets the SupplierType attribute of the Supplier1Only object
	 *
	 *@param  type  The new SupplierType value
	 */
	public void setSupplierType(String type);


	/**
	 *  Gets the SupplierType attribute of the Supplier1Only object
	 *
	 *@return    The SupplierType value
	 */
	public String getSupplierType();


	/**
	 *  Gets the FullName attribute of the Supplier1Only object
	 *
	 *@return    The FullName value
	 */
	public String getFullName();
}
