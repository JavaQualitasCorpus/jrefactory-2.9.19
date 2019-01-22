package method;

import java.awt.Button;
import java.awt.Component;
import java.awt.Canvas;
import java.awt.Panel;

public class Child2 extends Parent {
	private boolean init() {
		return true;
	}

	public Component getPanel(Canvas canvas, Button button) {
		return new Panel();
	}
}
