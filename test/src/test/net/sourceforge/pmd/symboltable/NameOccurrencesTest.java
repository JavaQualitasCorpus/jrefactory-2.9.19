package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTPrimarySuffix;
import org.acm.seguin.pmd.symboltable.NameOccurrence;
import org.acm.seguin.pmd.symboltable.NameOccurrences;
import net.sourceforge.jrefactory.parser.JavaParserTreeConstants;

public class NameOccurrencesTest extends TestCase {


    public void testNameLinkage() {
        ASTPrimaryExpression primary = new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);
        primary.testingOnly__setBeginLine(10);
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        prefix.setUsesThisModifier();
        prefix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(prefix, 0);
        ASTPrimarySuffix suffix = new ASTPrimarySuffix(JavaParserTreeConstants.JJTPRIMARYSUFFIX);
        suffix.setImage("x1");
        suffix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(suffix, 1);

        NameOccurrences occs = new NameOccurrences(primary);
        NameOccurrence thisOcc = (NameOccurrence) occs.iterator().next();
        NameOccurrence xOcc = (NameOccurrence) occs.getNames().get(1);
        assertEquals(thisOcc.getNameForWhichThisIsAQualifier(), xOcc);
    }

    // super
    public void testSuper() {
        ASTPrimaryExpression primary = new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);
        primary.testingOnly__setBeginLine(10);
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        prefix.setUsesSuperModifier();
        prefix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(prefix, 0);

        NameOccurrences occs = new NameOccurrences(primary);
        assertEquals("super", ((NameOccurrence) occs.getNames().get(0)).getImage());
    }

    // this
    public void testThis() {
        ASTPrimaryExpression primary = new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);
        primary.testingOnly__setBeginLine(10);
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        prefix.setUsesThisModifier();
        prefix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(prefix, 0);

        NameOccurrences occs = new NameOccurrences(primary);
        assertEquals("this", ((NameOccurrence) occs.getNames().get(0)).getImage());
    }

    // this.x
    public void testFieldWithThis() {
        ASTPrimaryExpression primary = new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);
        primary.testingOnly__setBeginLine(10);
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        prefix.setUsesThisModifier();
        prefix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(prefix, 0);
        ASTPrimarySuffix suffix = new ASTPrimarySuffix(JavaParserTreeConstants.JJTPRIMARYSUFFIX);
        suffix.setImage("x2");
        suffix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(suffix, 1);

        NameOccurrences occs = new NameOccurrences(primary);
        assertEquals("this", ((NameOccurrence) occs.getNames().get(0)).getImage());
        assertEquals("x2", ((NameOccurrence) occs.getNames().get(1)).getImage());
    }

    // x
    public void testField() {
        ASTPrimaryExpression primary = new ASTPrimaryExpression(JavaParserTreeConstants.JJTPRIMARYEXPRESSION);
        primary.testingOnly__setBeginLine(10);
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(JavaParserTreeConstants.JJTPRIMARYPREFIX);
        prefix.testingOnly__setBeginLine(10);
        primary.jjtAddChild(prefix, 0);
        ASTName name = new ASTName(JavaParserTreeConstants.JJTNAME);
        name.setImage("x3");
        name.testingOnly__setBeginLine(10);
        prefix.jjtAddChild(name, 0);

        NameOccurrences occs = new NameOccurrences(primary);
        assertEquals("x3", ((NameOccurrence) occs.getNames().get(0)).getImage());
    }


}
