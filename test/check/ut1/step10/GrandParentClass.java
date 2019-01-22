package abstracter;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class GrandParentClass extends GreatGrandParentClass {
	/**
	 *  Constructor for the GrandParentClass object
	 */
	public GrandParentClass() {
		System.out.println("Creating nameless object");
	}


	/**
	 *  Constructor for the GrandParentClass object
	 *
	 *@param  name  Description of Parameter
	 */
	public GrandParentClass(String name) {
		System.out.println("Creating:  " + name);
	}


	/**
	 *  Gets the ParentName attribute of the GrandParentClass object
	 *
	 *@return    The ParentName value
	 */
	public String getParentName() {
		return "My great grandparent";
	}
}
