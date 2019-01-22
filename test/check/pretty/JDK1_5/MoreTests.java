/**
 *  Description of the Class
 *
 *@author     Mike Atkinson (Mike)
 *@created    29 June 2003
 */
public class MoreTests<K, V> {
	/**
	 *  Description of the Field
	 */
	public final Object[] args;
	/**
	 *  Description of the Field
	 */
	public final String key;


	/**
	 *  Constructor for the MoreTests object
	 *
	 *@param  key   Description of Parameter
	 *@param  args  Description of Parameter
	 */
	public MoreTests(String key, Object[] args) {
		MoreTests.this.super();
		this.key = key;
		this.args = args;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  s  Description of Parameter
	 */
	public MoreTests(Set s) {
		super((Set<Map.Entry<K, V>>) (Set) s);
	}


	/**
	 *  Adds a feature to the All attribute of the MoreTests object
	 *
	 *@param  c  The feature to be added to the All attribute
	 *@return    Description of the Returned Value
	 */
	public boolean addAll(Collection<? extends E> c) {
		SortedSet<Map.Entry<E, Object>> set = (SortedSet) c;
		SortedSet<Map.Entry> set2 = (SortedSet<Map.Entry>) c;
		SortedSet<Map.Entry<E, Object>> set3 = (SortedSet<Map.Entry<E, Object>>) c;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  key    Description of Parameter
	 *@param  value  Description of Parameter
	 */
	public void put(K key, V value) {
	}


	/**
	 *  Description of the Method
	 *
	 *@return    The map entries
	 */
	public abstract Set<? extends Entry<K, V>> entrySet();


	/**
	 *  Description of the Method
	 *
	 *@param  a    Description of Parameter
	 *@param  val  Description of Parameter
	 */
	public static <T> void fill(T[] a, T val) {
		Arrays(a, 0, a.length, val);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  list  Description of Parameter
	 *@param  key   Description of Parameter
	 *@return       Index into list of found item.
	 */
	public abstract static <T extends Comparable<? super T>> int indexedBinarySearch(List<? extends T> list, T key);


	/**
	 *  The main program for the Test_for_reference class
	 *
	 *@param  args  The command line arguments
	 */
	public static void main(String[] args) {
		Iterator<Map.Entry<K, V>> i = m.entrySet().iterator();
		for (Iterator i = m.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			put(e.getKey(), e.getValue());
		}
		for (Iterator<Map.Entry<K, V>> i = m.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			put(e.getKey(), e.getValue());
		}
		for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i.hasNext(); ) {
			Map.Entry<? extends K, ? extends V> e = i.next();
			put(e.getKey(), e.getValue());
		}
		make.App(make.QualIdent(v), List.<Tree>make(tree));
	}
}

