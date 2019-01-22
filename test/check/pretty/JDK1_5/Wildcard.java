package jdk1_5;

import java.util.*;
/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class Wildcard {

	/**
	 *  Constructor for the Wildcard object
	 */
	public Wildcard() {
		// covariance
		Set<? extends Number> aReadOnlySet = new hashSet<Integer>();
		Iterator<? extends Number> i = aReadOnlySet.iterator();
		double sum = 0.0;
		while (i.hasNext()) {
			sum += i.next().doubleValue();
		}

		// contravariance
		Set<? super Integer> aWriteOnlySet = new HashSet<Number>();
		aWriteOnlySet.add(new Integer(10));

		// bivariance
		Set<?> anUnkownSet = new HashSet<Number>();
		if (!anUnknownSet.isEmpty()) {
			anUnknownSet.clear();
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  list  Description of Parameter
	 *@param  o     Description of Parameter
	 */
	public static <T> void fill(List<? super T> list, T o) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  dest  Description of Parameter
	 *@param  src   Description of Parameter
	 */
	public static <T> void copy(List<? super T> dest, List<? extends T> src) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  a  Description of Parameter
	 *@param  c  Description of Parameter
	 */
	public static <T> void sort(T[] a, Comparator<? super T> c) {
	}


	/**
	 *  Description of the Method
	 *
	 *@param  a    Description of Parameter
	 *@param  val  Description of Parameter
	 */
	public static <T> void fill(T[] a, T val) {
	}


	/**
	 *  Description of the Class
	 *
	 *@author    Chris Seguin
	 */
	public static class MySet<T> extends AbstractSet<T> {
		/**
		 *  Constructor for the MySet object
		 *
		 *@param  c  Description of Parameter
		 */
		public MySet(Comparator<? super T> c) {
		}


		/**
		 *  Constructor for the MySet object
		 *
		 *@param  c  Description of Parameter
		 */
		public MySet(Collection<? extends T> c) {
		}


		/**
		 *  Constructor for the MySet object
		 *
		 *@param  c  Description of Parameter
		 */
		public MySet(SortedSet<T> c) {
		}
	}


	/**
	 *  Description of the Interface
	 *
	 *@author    Chris Seguin
	 */
	public interface MyCollection<E>
}

