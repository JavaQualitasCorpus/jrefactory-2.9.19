package abstracter;

import javax.swing.JComponent;
import java.awt.event.ActionListener;

public interface InterfaceThree extends ActionListener, InterfaceTwo {
	public JComponent getRootComponent();
}
