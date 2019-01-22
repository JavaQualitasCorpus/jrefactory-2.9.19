package test.prettyprinter;

import java.util.Iterator;
import java.util.LinkedList;

public class FinalUsage {
	final int CONSTANT = 23;

	public FinalUsage(final String message) {
		System.out.println(message);
	}

	public void run() {
		final double key = 2.0;
		for (final Iterator iter = getList(key); ;) {
			System.out.println("Next:  " + iter.next());
		}
	}

	protected final Iterator getList(final double value) {
		LinkedList list = new LinkedList();
		double current = 0.0;
		double stepSize = 1.0 / CONSTANT;
		while (current < value) {
			list.add (new Double(current));
			current += stepSize;
		}
		return list.iterator();
	}


	protected final Iterator getFullList() {
		LinkedList list = new LinkedList();
		double current = 0.0;
		for (final double stepSize = 1.0 / CONSTANT;
			current < 10.0; current += stepSize) {
			list.add (new Double(current));
		}
		return list.iterator();
	}
}
