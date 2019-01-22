package abstracter;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class NormalClass extends ParentClass {
	/**
	 *  Constructor for the NormalClass object
	 */
	public NormalClass() {
	}


	/**
	 *  Constructor for the NormalClass object
	 *
	 *@param  name  Description of Parameter
	 */
	public NormalClass(String name) {
		super(name);
	}


	/**
	 *  Constructor for the NormalClass object
	 *
	 *@param  name   Description of Parameter
	 *@param  index  Description of Parameter
	 */
	public NormalClass(String name, int index) {
		super(name);
		System.out.println("The name is:  " + name);
		System.out.println("The index is:  " + index);
	}


	/**
	 *  Gets the ParentName attribute of the NormalClass object
	 *
	 *@return    The ParentName value
	 */
	public String getParentName() {
		return "My parent";
	}
}
