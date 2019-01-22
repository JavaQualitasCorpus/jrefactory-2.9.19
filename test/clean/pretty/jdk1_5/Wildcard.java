package jdk1_5;

import java.util.*;
public class Wildcard {

    public interface MyCollection<E> {
        public boolean addAll(Collection<? extends E> c);
    }
    
    public static <T> void fill(List<? super T> list, T o) {
    }
    
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    }
    public static <T> void sort(T[] a, Comparator<? super T> c) {
    }
    public static <T> void fill(T[] a, T val) {
    }
    public static class MySet<T> extends AbstractSet<T> {
        public MySet(Comparator<? super T> c) {}
        public MySet(Collection<? extends T> c) {}
        public MySet(SortedSet<T> c) {}
    }
    
    public Wildcard() {
        // covariance
        Set<? extends Number> aReadOnlySet = new hashSet<Integer>();
        Iterator<? extends Number> i = aReadOnlySet.iterator();
        double sum = 0.0;
        while(i.hasNext()) { sum += i.next().doubleValue(); }
        
        // contravariance
        Set<? super Integer> aWriteOnlySet = new HashSet<Number>();
        aWriteOnlySet.add(new Integer(10));
        
        // bivariance
        Set<?> anUnkownSet = new HashSet<Number>();
        if (!anUnknownSet.isEmpty()) anUnknownSet.clear();
    }
}

