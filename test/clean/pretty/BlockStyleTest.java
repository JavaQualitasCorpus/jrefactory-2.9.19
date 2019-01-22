package pretty;

public class BlockStyleTest {
	private interface NestedInterface {
		public void method();
	}

	private class NestedClass implements NestedInterface
	{
		public void method() {
			if (true) { System.out.println("What..."); }
		}
	}

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
}
