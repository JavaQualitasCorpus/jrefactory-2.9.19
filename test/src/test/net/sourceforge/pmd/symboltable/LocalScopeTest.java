package test.net.sourceforge.pmd.symboltable;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import org.acm.seguin.pmd.symboltable.LocalScope;
import org.acm.seguin.pmd.symboltable.NameOccurrence;
import org.acm.seguin.pmd.symboltable.VariableNameDeclaration;

public class LocalScopeTest extends TestCase {

    private class MyASTVariableDeclaratorId extends ASTVariableDeclaratorId {
        public MyASTVariableDeclaratorId(int x) {
            super(x);
        }

        public boolean isExceptionBlockParameter() {
            return true;
        }
    }

    public void testNameWithThisOrSuperIsNotFlaggedAsUnused() {
        LocalScope scope = new LocalScope();
        ASTName name = new ASTName(1);
        name.setImage("foo");
        name.testingOnly__setBeginLine(1);

        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(2);
        prefix.setUsesThisModifier();
        prefix.testingOnly__setBeginLine(1);

        name.jjtAddChild(prefix, 1);   //FIXME? MRA surely the ASTName should be added to the ASTPrimaryPrefix

        NameOccurrence occ = new NameOccurrence(name, "foo");
        scope.addVariableNameOccurrence(occ);
        assertTrue(!scope.getVariableDeclarations(false).keySet().iterator().hasNext());
    }

    public void testNameWithSuperIsNotFlaggedAsUnused() {
        LocalScope scope = new LocalScope();
        ASTName name = new ASTName(1);
        name.setImage("foo");
        name.testingOnly__setBeginLine(1);
        ASTPrimaryPrefix prefix = new ASTPrimaryPrefix(2);
        prefix.setUsesSuperModifier();
        prefix.testingOnly__setBeginLine(1);
        name.jjtAddChild(prefix, 1);
        NameOccurrence occ = new NameOccurrence(name, "foo");
        scope.addVariableNameOccurrence(occ);
        assertTrue(!scope.getVariableDeclarations(false).keySet().iterator().hasNext());
    }

    public void testExceptionParamNameIsDiscarded() {
        ASTVariableDeclaratorId node = new MyASTVariableDeclaratorId(1);
        VariableNameDeclaration decl = new VariableNameDeclaration(node);
        LocalScope scope = new LocalScope();
        scope.addDeclaration(decl);
        assertTrue(!scope.getVariableDeclarations(false).keySet().iterator().hasNext());
    }

}
