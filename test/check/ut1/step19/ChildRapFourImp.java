package abstracter;

import imp.ParentRapFour;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ChildRapFourImp extends ParentRapFour {
	/**
	 *  Gets the ClassName attribute of the ChildRapFourImp object
	 *
	 *@return    The ClassName value
	 */
	public String getClassName() {
		return "ChildRapFourImp";
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public ParentRapFour createGrandparent() {
		return new ParentRapFour();
	}
}
