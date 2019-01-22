/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class CopyConstructor {
	private int x;


	/**
	 *  Copy constructor for the CopyConstructor object. Creates a copy of the
	 *  CopyConstructor object parameter.
	 *
	 *@param  cc  Object to copy.
	 */
	public CopyConstructor(CopyConstructor cc) {
		this.x = cc.x;
	}
}

