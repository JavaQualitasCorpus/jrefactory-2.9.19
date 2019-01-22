package method;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class ExtractMethodTestFour {

	private Iterator iter;


	/**
	 *  Description of the Method
	 */
	public void action() {
		System.out.println("Before");
		KeyIterator key = new KeyIterator();

		while (key.isApplicable()) {
			key.apply();
		}
		key.debug();

		System.out.println("After");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  key  Description of Parameter
	 */
	public void process(KeyIterator key) {
		System.out.println("Before");

		while (key.isApplicable()) {
			key.apply();
		}
		key.debug();

		System.out.println("After");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  iter  Description of Parameter
	 */
	public void apply(KeyIterator iter) {
		System.out.println("Before");
		extractedMethod(iter);
		iter.debug();

		System.out.println("After");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  list  Description of Parameter
	 */
	public void apply(LinkedList list) {
		System.out.println("Before");

		for (Iterator iter = list.iterator(); iter.hasNext(); ) {
			Object next = iter.next();
			System.out.println("String:  " + next.toString());
		}

		System.out.println("After");
	}


	/**
	 *  Description of the Method
	 *
	 *@param  iter  Description of Parameter
	 */
	private void extractedMethod(KeyIterator iter) {
		while (iter.isApplicable()) {
			iter.apply();
		}
	}
}

