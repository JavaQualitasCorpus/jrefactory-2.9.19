/**
 *  Description of the Interface
 *
 *@author    Chris Seguin
 */
public interface Person {
	/**
	 *  Sets the FirstName attribute of the Person object
	 *
	 *@param  firstName  The new FirstName value
	 */
	public void setFirstName(String firstName);


	/**
	 *  Sets the LastName attribute of the Person object
	 *
	 *@param  lastName  The new LastName value
	 */
	public void setLastName(String lastName);


	/**
	 *  Gets the FullName attribute of the Person object
	 *
	 *@return    The FullName value
	 */
	public String getFullName();
}
