package method;

import java.awt.Button;
import java.util.Set;
import java.util.Vector;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public abstract class Parent {
	/**
	 *  Description of the Field
	 */
	protected long total = 0;
	/**
	 *  Description of the Field
	 */
	protected long time;
	private Button next;
	private int count;


	/**
	 *  Description of the Method
	 */
	public void set() {
		count++;
		reset();
	}


	/**
	 *  Sets the SleepTime attribute of the Parent object
	 *
	 *@param  time  The new SleepTime value
	 */
	public void setSleepTime(long time) {
		if (isInitialized()) {
			init();
		}

		this.time = time;
		total = time + total;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Set get() {
		return new Vector();
	}


	/**
	 *  Gets the Initialized attribute of the Parent object
	 *
	 *@return    The Initialized value
	 */
	public abstract boolean isInitialized();


	/**
	 *  Description of the Method
	 *
	 *@param  obj  Description of Parameter
	 */
	public void stopOne(Object obj) {
		//  Nothing here
	}


	/**
	 *  Description of the Method
	 *
	 *@param  obj  Description of Parameter
	 *@param  d    Description of Parameter
	 */
	public void stopTwo(Double obj, Object d) {
		//  Nothing here
	}


	/**
	 *  Description of the Method
	 */
	private void reset() {
		total = 0;
	}


	/**
	 *  Description of the Method
	 */
	private abstract void init();
}
