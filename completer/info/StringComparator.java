//package codeaid.info;
package org.acm.seguin.completer.info;

// Collections API
import java.util.Comparator;


public class StringComparator implements Comparator, java.io.Serializable {
    public int compare(Object o1, Object o2) {
        //return ((String)o1).compareTo((String)o2);
        return o1.toString().compareTo(o2.toString());
    }


    public boolean equals(Object obj) {
        return obj instanceof StringComparator;
    }
}
