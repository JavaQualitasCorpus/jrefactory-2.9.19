package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.rules.AvoidDuplicateLiteralsRule;

public class AvoidDuplicateLiteralsRuleTest extends RuleTst {

    public static final String TEST1 =
    "public class Foo {" + PMD.EOL +
    " private void bar() {" + PMD.EOL +
    "    buz(\"Howdy\");" + PMD.EOL +
    "    buz(\"Howdy\");" + PMD.EOL +
    "    buz(\"Howdy\");" + PMD.EOL +
    "    buz(\"Howdy\");" + PMD.EOL +
    " }" + PMD.EOL +
    " private void buz(String x) {}" + PMD.EOL +
    "}";

    public static final String TEST2 =
    "public class Foo {" + PMD.EOL +
    " private void bar() {" + PMD.EOL +
    "    buz(2);" + PMD.EOL +
    " }" + PMD.EOL +
    " private void buz(int x) {}" + PMD.EOL +
    "}";

    public static final String TEST3 =
    "public class Foo {" + PMD.EOL +
    " private static final String FOO = \"foo\";" + PMD.EOL +
    "}";

    public static final String TEST4 =   //MRA
    "public class Foo {" + PMD.EOL +   //MRA
    " private static final String[] FOO = {\"foo\",\"foo\",\"foo\",\"foo\"};" + PMD.EOL +   //MRA
    "}";   //MRA

    private AvoidDuplicateLiteralsRule rule;

    public void setUp() {
        rule = new AvoidDuplicateLiteralsRule();
        rule.setMessage("avoid ''{0}'' and ''{1}''");
        rule.addProperty("threshold", "2");
    }

    public void testTwoLiteralStringArgs() throws Throwable {
        runTestFromString(TEST1, 1, rule);
    }
    public void testLiteralIntArg() throws Throwable {
        runTestFromString(TEST2, 0, rule);
    }
    public void testLiteralFieldDecl() throws Throwable {
        runTestFromString(TEST3, 0, rule);
    }
    public void testLiteralList() throws Throwable {   //MRA
        runTestFromString(TEST4, 0, rule);   //MRA
    }   //MRA
}
