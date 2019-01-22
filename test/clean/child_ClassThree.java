package abstracter;

import javax.swing.JComponent;

public abstract class ClassThree extends ClassTwo implements InterfaceThree {
	public ClassThree(JComponent init) {
		comp = init;
	}
	private JComponent comp;

	public ClassThree(JComponent init, String name) {
		this(init);
		System.out.println("Creating " + name);
	}
}
