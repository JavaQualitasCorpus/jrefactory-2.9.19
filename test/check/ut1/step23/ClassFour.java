package abstracter;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Date;
/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
class ClassFour extends ClassThree {
	/**
	 *  Constructor for the ClassFour object
	 *
	 *@param  init  Description of Parameter
	 */
	public ClassFour(JComponent init) {
		super(init);
	}


	/**
	 *  Constructor for the ClassFour object
	 *
	 *@param  init  Description of Parameter
	 *@param  name  Description of Parameter
	 */
	public ClassFour(JComponent init, String name) {
		super(init, name);
	}


	/**
	 *  Gets the RootComponent attribute of the ClassFour object
	 *
	 *@return    The RootComponent value
	 */
	public JComponent getRootComponent() {
	}


	/**
	 *  Gets the List attribute of the ClassFour object
	 *
	 *@return    The List value
	 */
	public Iterator getList() {
	}


	/**
	 *  Gets the Calendar attribute of the ClassFour object
	 *
	 *@param  time  Description of Parameter
	 *@return       The Calendar value
	 */
	public Calendar getCalendar(Date time) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	public void actionPerformed(ActionEvent e) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  list  Description of Parameter
	 */
	public void createList(LinkedList list) {
	}
}
