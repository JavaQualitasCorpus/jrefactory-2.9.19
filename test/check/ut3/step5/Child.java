package method;

import java.awt.Button;
import java.awt.Component;
import java.awt.Canvas;
import java.awt.Panel;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Child extends Parent {
	private long time;


	/**
	 *  Sets the SleepTime attribute of the Child object
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
	 *  Gets the Panel attribute of the Child object
	 *
	 *@param  canvas  Description of Parameter
	 *@param  button  Description of Parameter
	 *@return         The Panel value
	 */
	public Component getPanel(Canvas canvas, Button button) {
		return new Panel();
	}


	/**
	 *  Gets the Initialized attribute of the Child object
	 *
	 *@return    The Initialized value
	 */
	public boolean isInitialized() {
		return (time > 0);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  sample  Description of Parameter
	 */
	public void stopOne(Object sample) {
		//  Nothing here
	}


	/**
	 *  Description of the Method
	 *
	 *@param  crazy    Description of Parameter
	 *@param  strange  Description of Parameter
	 */
	public void stopTwo(Object crazy, Double strange) {
	}


	/**
	 *  Description of the Method
	 */
	protected void reset() {
		total = 0;
	}


	/**
	 *  Description of the Method
	 */
	private void init() {
		time = 1;
	}
}
