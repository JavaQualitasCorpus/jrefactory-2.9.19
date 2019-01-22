package method;

import java.awt.Button;
import java.awt.Component;
import java.awt.Canvas;
import java.awt.Panel;

public class Child extends Parent {
	private long time;

	public Component getPanel(Canvas canvas, Button button) {
		return new Panel();
	}

	public void setSleepTime(long time) {
		if (isInitialized())
			init();

		this.time = time;
		total = time + total;
	}

	public boolean isInitialized() {
		return (time > 0);
	}

	private void init() {
		time = 1;
	}

	public void stopOne(Object sample) {
		//  Nothing here
	}

	public void stopTwo(Object crazy, Double strange) {
	}
}
