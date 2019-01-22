package method;

import java.awt.Button;
import java.util.Set;
import java.util.Vector;
import java.awt.Component;
import java.awt.Canvas;

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
	public abstract void setSleepTime(long time);


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Set get() {
		return new Vector();
	}


	/**
	 *  Gets the Panel attribute of the Parent object
	 *
	 *@param  canvas  Description of Parameter
	 *@param  button  Description of Parameter
	 *@return         The Panel value
	 */
	public abstract Component getPanel(Canvas canvas, Button button);


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
}
