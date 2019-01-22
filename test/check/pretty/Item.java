package test;

import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Item extends Object {
	/**
	 *  Description of the Field
	 */
	protected boolean shortValue;
	Vector save;
	private String name;
	/**
	 *  Description of the Field
	 */
	public static final int EXAMPLE = 53;


	/**
	 *  Constructor for the Item object
	 */
	public Item() {
		name = "name";

		save = new Vector();
		shortValue = true;
	}


	/**
	 *  Sets the Short attribute of the Item object
	 *
	 *@param  value  The new Short value
	 */
	public void setShort(boolean value) {
		shortValue = value;
	}


	/**
	 *  Sets the Name attribute of the Item object
	 *
	 *@param  value  The new Name value
	 */
	public void setName(String value) {
		name = value;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  key  Description of Parameter
	 */
	public void set(String key) {
		if (save.contains(key)) {
			name = name + "::" + key;
		}
	}


	/**
	 *  Gets the Short attribute of the Item object
	 *
	 *@return    The Short value
	 */
	public boolean isShort() {
		return shortValue;
	}


	/**
	 *  Gets the Name attribute of the Item object
	 *
	 *@return    The Name value
	 */
	public String getName() {
		return name;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  name  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public String get(String name) {
		return name + "::";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  value  Description of Parameter
	 *@return        Description of the Returned Value
	 */
	public boolean is(String value) {
		return name.equals(value);
	}


	/**
	 *  Adds a feature to the Key attribute of the Item object
	 *
	 *@param  key  The feature to be added to the Key attribute
	 */
	public void addKey(String key) {
		save.addElement(key);
	}


	/**
	 *  Main processing method for the Item object
	 */
	public void run() {
		System.out.println("Name:  " + name);
		for (int ndx = 0; ndx < save.size(); ndx++) {
			System.out.println("..." +
					save.elementAt(ndx).toString());
		}
	}


	/**
	 *  The main program for the Item class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		System.out.println("This is the main program");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  stupid  Description of Parameter
	 */
	public static void main(int stupid) {
		System.out.println("This is not the main program");
	}
}
