package method;

import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ExtractMethodTestFive {
  void firewallNameField_focusLost(FocusEvent e) {
  	System.out.println("Before");
     SwingUtilities.invokeLater(
        new Runnable() {
          public void run() {
            actualFilename.setText( computeFile() );
            actualFilename.revalidate();
          }
        }
     );
     System.out.println("After");
    }
	private JTextField actualFilename;
	public String computeFile() {
		return "c:\\temp\\notes.txt";
	}

	class Inner {
		public void test() {
			Matcher _matcher;

			_matcher.init();
			// Gobble up any whitespace between attributes
			while( _matcher.match( new char[] { ' ', '\t' } ) ) {
			        _matcher.consume();
			}
			_matcher.done();
		}
	}
}
