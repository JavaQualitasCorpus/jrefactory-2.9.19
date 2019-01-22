package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import org.acm.seguin.pmd.PMD;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.parser.JavaParser;
import org.acm.seguin.pmd.symboltable.SymbolFacade;
import net.sourceforge.jrefactory.parser.ParseException;

import java.io.StringReader;

public class AcceptanceTest extends TestCase {

    public void testClashingSymbols() {
        try {
            JavaParser parser = new JavaParser(new StringReader(TEST1));
            ASTCompilationUnit c = parser.CompilationUnit();
            SymbolFacade stb = new SymbolFacade();
            stb.initializeWith(c);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void testInitializer() {
        try {
            JavaParser parser = new JavaParser(new StringReader(TEST2));
            ASTCompilationUnit c = parser.CompilationUnit();
            ASTInitializer a = (ASTInitializer)(c.findChildrenOfType(ASTInitializer.class)).get(0);
            assertFalse(a.isUsingStatic());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void testStaticInitializer() {
        try {
            JavaParser parser = new JavaParser(new StringReader(TEST3));
            ASTCompilationUnit c = parser.CompilationUnit();
            ASTInitializer a = (ASTInitializer)(c.findChildrenOfType(ASTInitializer.class)).get(0);
            assertTrue(a.isUsingStatic());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static final String TEST1 =
    "import java.io.*;" + PMD.EOL +
    "public class Foo  {" + PMD.EOL +
    " void buz( ) {" + PMD.EOL +
    "  Object o = new Serializable() { int x; };" + PMD.EOL +
    "  Object o1 = new Serializable() { int x; };" + PMD.EOL +
    " }" + PMD.EOL  +
    "}" + PMD.EOL;

    private static final String TEST2 =
    "public class Foo  {" + PMD.EOL +
    " {} " + PMD.EOL +
    "}" + PMD.EOL;

    private static final String TEST3 =
    "public class Foo  {" + PMD.EOL +
    " static {} " + PMD.EOL +
    "}" + PMD.EOL;

}
