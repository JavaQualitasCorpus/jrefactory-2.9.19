package org.acm.seguin.pretty.line;

import junit.framework.*;

public class TestPackage {
public static TestSuite suite() {
TestSuite suite = new TestSuite();
suite.addTest(new TestSuite(LineNumberingTest.class));
return suite;
}
}
