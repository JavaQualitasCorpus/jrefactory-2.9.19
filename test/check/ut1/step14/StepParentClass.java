package abstracter;
/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
abstract class StepParentClass extends NormalClass {
	/**
	 *  Constructor for the StepParentClass object
	 */
	public StepParentClass() {
		super();
	}


	/**
	 *  Constructor for the StepParentClass object
	 *
	 *@param  name  Description of Parameter
	 */
	public StepParentClass(String name) {
		super(name);
	}


	/**
	 *  Constructor for the StepParentClass object
	 *
	 *@param  name   Description of Parameter
	 *@param  index  Description of Parameter
	 */
	public StepParentClass(String name, int index) {
		super(name, index);
	}
}
