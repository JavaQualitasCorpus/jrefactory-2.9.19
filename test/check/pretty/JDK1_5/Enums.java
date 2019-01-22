package jdk1_5;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Enums {

	/**
	 *  Description of the Method
	 *
	 *@param  x  Description of Parameter
	 */
	public static void bar(Object[] x) {
		for (Object o : x) {
			System.out.println(o);
		}

		int[][] y;
		for (int[] z : y) {
			for (int i : z) {
				System.out.println(i);
			}
		}
	}


	/**
	 *  The main program for the Enums class
	 *
	 *@param  argv  The command line arguments
	 */
	public static void main(String[] argv) {
		CoinTest();
		bar(1, "a", 'z');
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	public class CoinTest {
		/**
		 *  Description of the Method
		 */
		public void CoinTest() {
			X:
			// fixed ":" after labels
			for (Iterator<Coin> i = Coin.VALUES.iterator(); i.hasNext(); ) {
				Coin c = i.next();
				System.out.println(c + ":   \t" + c.value() + "c \t" + color(c));
			}
		}


		/**
		 *  Description of the Method
		 *
		 *@param  c  Description of Parameter
		 *@return    Description of the Returned Value
		 */
		private static CoinColor color(Coin c) {
			if (c == null) {
				throw new NullPointerException();
			}

			switch (c) {
				case Coin.penny:
					return CoinColor.copper;
				case Coin.nickel:
					return CoinColor.nickel;
				case Coin.dime:
					return CoinColor.silver;
				case Coin.quarter:
					return CoinColor.silver;
			}

			throw new AssertionError("Unknown coin: " + c);
		}


		private enum CoinColor { copper, nickel, silver }
	}

	/**
	 *  Description of Enumeration
	 *
	 *@author    Chris Seguin
	 */
	public enum Quarks { up, down, top, bottom, charm, strange }

	/**
	 *  Description of Enumeration
	 *
	 *@author    Chris Seguin
	 */
	public enum Coin { penny, nickel, dime, quarter;


		/**
		 *  Constructor for the Coin object
		 *
		 *@param  value  Description of Parameter
		 */
		Coin(int value) {
			this.value = value;
		}


		private final int value;


		/**
		 *  Description of the Method
		 *
		 *@return    Description of the Returned Value
		 */
		public int value() {
			return value;
		}
	}

}

