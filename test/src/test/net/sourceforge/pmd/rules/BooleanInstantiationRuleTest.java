package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.XPathRule;

public class BooleanInstantiationRuleTest extends SimpleAggregatorTst {

    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        rule.addProperty(
            "xpath",
            "//AllocationExpression[not (ArrayDimsAndInits) and (ClassOrInterfaceType/Identifier/@Image='Boolean' or ClassOrInterfaceType/Identifier/@Image='java.lang.Boolean')]");
    }

    public void testAll() {
       runTests(new TestDescriptor[] {
           new TestDescriptor(TEST1, "simple failure case", 1, rule),
           new TestDescriptor(TEST2, "new java.lang.Boolean", 1, rule),
           new TestDescriptor(TEST3, "ok", 0, rule),
       });
    }

    private static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " Boolean b = new Boolean(\"true\");" + PMD.EOL +
    "}";

    private static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " Boolean b = new java.lang.Boolean(\"true\");" + PMD.EOL +
    "}";

    private static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " Boolean b = Boolean.TRUE;" + PMD.EOL +
    "}";

}
