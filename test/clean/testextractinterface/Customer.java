package testextractinterface;

/**
 *  This class extends nothing, but implements something
 *
 *@author     Grant
 *@created    December 6, 2000
 */
public abstract class Customer implements java.io.Serializable
{
    private String m_firstName;
    private String m_lastName;
    private int m_creditLimit;
    private String m_password;
    private String m_userName;

    /**
     *  Constructor for the Customer object
     */
    public Customer()
    {
    }

    /**
     *  Sets the FirstName attribute of the Customer object
     *
     *@param  firstName  The new FirstName value
     */
    public void setFirstName(String firstName)
    {
        m_firstName = firstName;
    }

    /**
     *  Sets the LastName attribute of the Customer object
     *
     *@param  lastName  The new LastName value
     */
    public void setLastName(String lastName)
    {
        m_lastName = lastName;
    }

    /**
     *  Sets the CreditLimit attribute of the Customer object
     *
     *@param  creditLimit  The new CreditLimit value
     */
    public void setCreditLimit(int creditLimit)
    {
        m_creditLimit = creditLimit;
    }

    /**
     *  Gets the FullName attribute of the Customer object
     *
     *@return    The FullName value
     */
    public String getFullName()
    {
        return m_firstName + " " + m_lastName;
    }

    /**
     *  Gets the CreditLimit attribute of the Customer object
     *
     *@return    The CreditLimit value
     */
    public int getCreditLimit()
    {
        return m_creditLimit;
    }

    /**
     *  Gets the CustomerType attribute of the Customer object
     *
     *@return    The CustomerType value
     */
    public abstract String getCustomerType();

    /**
     *  Gets the Password attribute of the Customer object
     *
     *@return    The Password value
     */
    protected String getPassword()
    {
        return m_password;
    }

    /**
     *  Gets the UserName attribute of the Customer object
     *
     *@return    The UserName value
     */
    String getUserName()
    {
        return m_userName;
    }
}
