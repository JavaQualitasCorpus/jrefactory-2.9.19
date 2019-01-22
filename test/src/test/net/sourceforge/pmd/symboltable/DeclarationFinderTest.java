package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTLocalVariableDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.pmd.symboltable.DeclarationFinder;
import org.acm.seguin.pmd.symboltable.LocalScope;
import org.acm.seguin.pmd.symboltable.NameOccurrence;

public class DeclarationFinderTest extends TestCase {

    public void testDeclarationsAreFound() {
        DeclarationFinder df = new DeclarationFinder();

        ASTVariableDeclaratorId node = new ASTVariableDeclaratorId(1);
        node.setImage("foo");
        node.testingOnly__setBeginLine(1);

        ASTVariableDeclarator parent = new ASTVariableDeclarator(2);
        parent.testingOnly__setBeginLine(1);
        //FIXME? MRA node.jjtSetParent(parent);  // should set up things with links in both directions
        parent.jjtAddFirstChild(node);

        ASTLocalVariableDeclaration gparent = new ASTLocalVariableDeclaration(3);
        gparent.testingOnly__setBeginLine(1);
        //FIXME? MRA parent.jjtSetParent(gparent);  // should set up things with links in both directions
        gparent.jjtAddFirstChild(parent);

        LocalScope scope = new LocalScope();
        node.setScope(scope);
        df.visit(node, null);

        SimpleNode simpleNode = new SimpleNode(4);
        simpleNode.testingOnly__setBeginLine(2);
        assertTrue(scope.contains(new NameOccurrence(simpleNode, "foo")));
    }

    public void test1() {
    }
}
