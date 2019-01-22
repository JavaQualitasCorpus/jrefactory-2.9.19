package jdk1_5;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Generics<A extends B, C extends P & Q> extends D<E> implements F<E>, G<E> {

	/**
	 *  Description of the Field
	 */
	public X<A, C> a;
	/**
	 *  Description of the Field
	 */
	protected Y<A, X<A, C>> b;
	Y<A, X<A, C>> c = new Y<A, X<A, C>>();
	Y<A, X<A, C>>[] d = new Y<A, X<A, C>>[2];


	/**
	 *  Description of the Method
	 */
	static void foo() {
		String[] array;
		for (String s : array) {
			System.out.println(s);
		}
	}

}

