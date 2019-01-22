package method;

import java.awt.Button;
import java.util.Set;
import java.util.Vector;

public abstract class Parent {
	private Button next;
	protected long total = 0;
	private int count;

	public void stopOne(Object obj) {
		//  Nothing here
	}

	public void stopTwo(Double obj, Object d) {
		//  Nothing here
	}

	public Set get() {
		return new Vector();
	}

	public void set() {
		count++;
		reset();
	}

	private void reset() {
		total = 0;
	}

	public abstract void setSleepTime(long time);
}
