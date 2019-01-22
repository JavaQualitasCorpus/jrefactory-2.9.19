package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.pmd.symboltable.AbstractScope;
import org.acm.seguin.pmd.symboltable.ClassScope;
import org.acm.seguin.pmd.symboltable.NameDeclaration;
import org.acm.seguin.pmd.symboltable.NameOccurrence;
import org.acm.seguin.pmd.symboltable.Scope;
import org.acm.seguin.pmd.symboltable.VariableNameDeclaration;

import java.util.Iterator;

public class AbstractScopeTest extends TestCase {

    // A helper class to stub out AbstractScope's abstract stuff
    private class MyScope extends AbstractScope {
        protected NameDeclaration findVariableHere(NameOccurrence occ) {
            for (Iterator i = variableNames.keySet().iterator(); i.hasNext();) {
                NameDeclaration decl = (NameDeclaration) i.next();
                if (decl.getImage().equals(occ.getImage())) {
                    return decl;
                }
            }
            return null;
        }
    }

    // Another helper class to test the search for a class scope behavior
    private class IsEnclosingClassScope extends ClassScope {

        public IsEnclosingClassScope(String name) {
            super(name);
        }

        protected NameDeclaration findVariableHere(NameOccurrence occ) {
            return null;
        }

        public ClassScope getEnclosingClassScope() {
            return this;
        }
    }

    public void testAccessors() {
        Scope scope = new MyScope();
        MyScope parent = new MyScope();
        scope.setParent(parent);
        assertEquals(parent, scope.getParent());

        assertTrue(!scope.getVariableDeclarations(false).keySet().iterator().hasNext());
        assertTrue(scope.getVariableDeclarations(true).isEmpty());
    }

    public void testEnclClassScopeGetsDelegatedRight() {
        Scope scope = new MyScope();
        Scope isEncl = new IsEnclosingClassScope("Foo");
        scope.setParent(isEncl);
        assertEquals(isEncl, scope.getEnclosingClassScope());
    }

    public void testAdd() {
        Scope scope = new MyScope();
        ASTVariableDeclaratorId node = new ASTVariableDeclaratorId(1);
        node.setImage("foo");
        node.testingOnly__setBeginLine(1);
        VariableNameDeclaration decl = new VariableNameDeclaration(node);
        scope.addDeclaration(decl);
        assertTrue(scope.contains(new NameOccurrence(new SimpleNode(1), "foo")));
    }
}
