package test.net.sourceforge.pmd.jaxen;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.pmd.jaxen.Attribute;
import org.acm.seguin.pmd.jaxen.AttributeAxisIterator;

public class AttributeAxisIteratorTest extends TestCase {

    public void testRemove() {
        SimpleNode n = new SimpleNode(0);
        n.testingOnly__setBeginColumn(1);
        n.testingOnly__setBeginLine(1);
        AttributeAxisIterator iter = new AttributeAxisIterator(n);
        try {
            iter.remove();
            fail("Should have thrown an exception!");
        } catch (UnsupportedOperationException e) {
            // cool
        }
    }

    public void testNext() {
        SimpleNode n = new SimpleNode(0);
        n.testingOnly__setBeginLine(1);
        n.testingOnly__setBeginColumn(2);
        //AttributeAxisIterator iter2 = new AttributeAxisIterator(n);
        //while (iter2.hasNext()) {
        //    Attribute a = (Attribute)iter2.next();
        //    System.out.println(a.getName());
        //}
        AttributeAxisIterator iter = new AttributeAxisIterator(n);
        Attribute a = (Attribute)iter.next();
        assertEquals("BeginLine", a.getName());
        assertEquals("1", a.getValue());
        a = (Attribute)iter.next();
        assertEquals("BeginColumn", a.getName());
        assertEquals("2", a.getValue());
        a = (Attribute)iter.next();
        assertEquals("EndLine", a.getName());
        assertEquals("0", a.getValue());
        a = (Attribute)iter.next();
        assertEquals("EndColumn", a.getName());
        a = (Attribute)iter.next();
        assertEquals("Image", a.getName());
        if (iter.hasNext()) {
           System.err.println("AttributeAxisIterator: iter.next()="+((Attribute)iter.next()).getName());
        }
        assertFalse(iter.hasNext());
    }
}
