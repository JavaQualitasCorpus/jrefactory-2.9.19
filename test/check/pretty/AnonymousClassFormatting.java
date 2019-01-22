package other;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class AnonymousClassFormatting {
	private Thread data =
		new Thread() {
			public void run() {
				System.out.println("Running thread one");
			}
		};
	private int value = 0;


	/**
	 *  Gets the Data attribute of the AnonymousClassFormatting object
	 *
	 *@return    The Data value
	 */
	public Thread getData() {
		if (data == null) {
			data =
				new Thread() {
					public void run() {
						System.out.println("Running thread two");
					}
				};
			value++;
		}

		return data;
	}


	/**
	 *  Description of the Method
	 */
	public void reset() {
		data = null;
	}


	/**
	 *  Description of the Method
	 *
	 *@return    Description of the Returned Value
	 */
	public Thread create() {
		return
			new Thread() {
				public void run() {
					System.out.println("Running thread three");
				}
			};
		//  This is the end
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test() {
		other.eval(
					new Thread() {
						public void run() {
							System.out.println("Running thread four");
						}
					}, 52);
	}
}
