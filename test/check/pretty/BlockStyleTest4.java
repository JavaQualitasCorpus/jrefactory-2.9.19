package pretty;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class BlockStyleTest
{

	/**
	 *  Description of the Method
	 */
	public void method()
	{
		for (int ndx = 0; ndx < 50; ndx++)
			{
				String message = "";
				while (message.length() < ndx)
					{
						message = message + "X";
					}

				if (ndx % 2 == 0)
					{
						System.out.println("  " + ndx + ":" + message);
					}
				else
					{
						System.out.println("  " + ndx + "|" + message);
					}
			}
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	private class NestedClass implements NestedInterface
	{
		/**
		 *  Description of the Method
		 */
		public void method()
		{
			if (true)
				{
					System.out.println("What...");
				}
		}
	}


	/**
	 *  Description of the Interface
	 *
	 *@author    Chris Seguin
	 */
	private interface NestedInterface
	{
		/**
		 *  Description of the Method
		 */
		public void method();
	}
}
