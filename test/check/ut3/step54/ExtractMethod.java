package method;

import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtractMethodTestFive {
	private JTextField actualFilename;


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public String computeFile() {
		return "c:\\temp\\notes.txt";
	}


	/**
	 *  Description of the Method
	 *
	 *@param  e  Description of Parameter
	 */
	void firewallNameField_focusLost(FocusEvent e) {
		System.out.println("Before");
		SwingUtilities.invokeLater(
					new Runnable() {
						public void run() {
							actualFilename.setText(computeFile());
							actualFilename.revalidate();
						}
					}
				);
		System.out.println("After");
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	class Inner {
		/**
		 *  A unit test for JUnit
		 */
		public void test() {
			Matcher _matcher;

			_matcher.init();
			extractedMethod();
			_matcher.done();
		}


		/**
		 *  Description of the Method
		 */
		private void extractedMethod() {
			// Gobble up any whitespace between attributes
			while (_matcher.match(new char[]{' ', '\t'})) {
				_matcher.consume();
			}
		}
	}
}
