package method;

public class ExtractMethodTestFour {
	public void action() {
		System.out.println("Before");
		KeyIterator key = new KeyIterator();

		while (key.isApplicable()) {
			key.apply();
		}
		key.debug();

		System.out.println("After");
	}


	public void process(KeyIterator key) {
		System.out.println("Before");

		while (key.isApplicable()) {
			key.apply();
		}
		key.debug();

		System.out.println("After");
	}


	private Iterator iter;
	public void apply(KeyIterator iter) {
		System.out.println("Before");

		while (iter.isApplicable()) {
			iter.apply();
		}
		iter.debug();

		System.out.println("After");
	}

	public void apply(LinkedList list) {
		System.out.println("Before");

		for (Iterator iter = list.iterator(); iter.hasNext(); ) {
			Object next = iter.next();
			System.out.println("String:  " + next.toString());
		}

		System.out.println("After");
	}
}

