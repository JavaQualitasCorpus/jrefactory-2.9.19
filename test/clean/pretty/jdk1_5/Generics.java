package jdk1_5;

public class Generics<A extends B, C extends P & Q> extends D<E> implements F<E>, G<E> {

    public X<A, C> a;
    protected Y<A, X<A, C>> b;
    Y<A, X<A, C>> c = new Y<A, X<A, C>>();
    Y<A, X<A, C>>[] d = new Y<A, X<A, C>>[2];

    static void foo() {
        String[] array;
        for (String s : array) {
            System.out.println(s);
        }
    }

}

