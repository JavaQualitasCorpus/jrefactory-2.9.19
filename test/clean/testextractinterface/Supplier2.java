package testextractinterface;

/**
 *  This class extends something and implements nothing
 *
 *@author     Grant
 *@created    December 6, 2000
 */
public class Supplier2 extends java.util.Hashtable
{
    private String m_firstName;
    private String m_lastName;
    private String m_type;
    private String m_password;

    /**
     *  Constructor for the Supplier object
     */
    public Supplier2()
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
     *  Sets the SupplierType attribute of the Supplier object
     *
     *@param  type  The new SupplierType value
     */
    public void setSupplierType(String type)
    {
        m_type = type;
    }

    /**
     *  Gets the SupplierType attribute of the Supplier object
     *
     *@return    The SupplierType value
     */
    public String getSupplierType()
    {
        return m_type;
    }

    /**
     *  Gets the FullName attribute of the Supplier object
     *
     *@return    The FullName value
     */
    public String getFullName()
    {
        return m_firstName + " " + m_lastName;
    }

}
