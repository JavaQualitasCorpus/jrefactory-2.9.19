package testextractinterface;

/**
 *  This class extends another class but implements nothing
 *
 *@author     Grant
 *@created    December 6, 2000
 */
public class Employee extends java.util.Hashtable implements Person {

	private String m_firstName;
	private String m_lastName;
	private int m_salary;
	private String m_password;
	private String m_userName;


	/**
	 *  Constructor for the Employee object
	 */
	public Employee() {
	}


	/**
	 *  Sets the FirstName attribute of the Employee object
	 *
	 *@param  firstName  The new FirstName value
	 */
	public void setFirstName(String firstName) {
		m_firstName = firstName;
	}


	/**
	 *  Sets the LastName attribute of the Employee object
	 *
	 *@param  lastName  The new LastName value
	 */
	public void setLastName(String lastName) {
		m_lastName = lastName;
	}


	/**
	 *  Sets the Salary attribute of the Employee object
	 *
	 *@param  salary  The new Salary value
	 */
	public void setSalary(int salary) {
		m_salary = salary;
	}


	/**
	 *  Gets the FullName attribute of the Employee object
	 *
	 *@return    The FullName value
	 */
	public String getFullName() {
		return m_firstName + " " + m_lastName;
	}


	/**
	 *  Gets the Salary attribute of the Employee object
	 *
	 *@return    The Salary value
	 */
	public int getSalary() {
		return m_salary;
	}


	/**
	 *  Gets the Password attribute of the Employee object
	 *
	 *@return    The Password value
	 */
	protected String getPassword() {
		return m_password;
	}


	/**
	 *  Gets the UserName attribute of the Employee object
	 *
	 *@return    The UserName value
	 */
	String getUserName() {
		return m_userName;
	}
}
