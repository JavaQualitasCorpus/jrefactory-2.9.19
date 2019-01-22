package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.pmd.symboltable.ClassScope;
import org.acm.seguin.pmd.symboltable.NameOccurrence;
import org.acm.seguin.pmd.symboltable.VariableNameDeclaration;

public class ClassScopeTest extends TestCase {

    public void testContains() {
        ClassScope s = new ClassScope("Foo");
        ASTVariableDeclaratorId node = new ASTVariableDeclaratorId(1);
        node.setImage("bar");
        node.testingOnly__setBeginLine(1);
        s.addDeclaration(new VariableNameDeclaration(node));
        assertTrue(s.getVariableDeclarations(false).keySet().iterator().hasNext());
    }

    public void testCantContainsSuperToString() {
        ClassScope s = new ClassScope("Foo");
        //FIMXME? MRA SimpleNode node = new SimpleNode(1);
        //FIMXME? MRA node.setImage("super.toString");
        ASTName node = new ASTName(1);
        node.setImage("Foo.X");
        node.testingOnly__setBeginLine(1);
        assertTrue(!s.contains(new NameOccurrence(node, node.getImage())));
    }

    public void testContainsStaticVariablePrefixedWithClassName() {
        ClassScope s = new ClassScope("Foo");
        ASTVariableDeclaratorId node = new ASTVariableDeclaratorId(1);
        node.setImage("X");
        node.testingOnly__setBeginLine(1);
        s.addDeclaration(new VariableNameDeclaration(node));

        //FIMXME? MRA SimpleNode node2 = new SimpleNode(2);
        //FIMXME? MRA node2.setImage("Foo.X");
        ASTName node2 = new ASTName(2);
        node2.setImage("Foo.X");
        node2.testingOnly__setBeginLine(1);
        assertTrue(s.contains(new NameOccurrence(node2, node2.getImage())));
    }

    public void testClassName() {
        ClassScope s = new ClassScope("Foo");
        assertEquals("Foo", s.getClassName());
    }

    // FIXME - these will break when this goes from Anonymous$1 to Foo$1
    public void testAnonymousInnerClassName() {
        ClassScope s = new ClassScope();
        assertEquals("Anonymous$1", s.getClassName());
        s = new ClassScope();
        assertEquals("Anonymous$2", s.getClassName());
    }


}
