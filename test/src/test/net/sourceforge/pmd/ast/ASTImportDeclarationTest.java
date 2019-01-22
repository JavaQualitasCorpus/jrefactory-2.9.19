package test.net.sourceforge.pmd.ast;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTImportDeclaration;
import net.sourceforge.jrefactory.ast.ASTName;

public class ASTImportDeclarationTest extends TestCase {

    public void testBasic() {
        ASTImportDeclaration i = new ASTImportDeclaration(1);
        assertTrue(!i.isImportOnDemand());
        i.setImportPackage(true);
        assertTrue(i.isImportOnDemand());
    }

    public void testGetImportedNameNode() {
        ASTImportDeclaration i = new ASTImportDeclaration(1);
        ASTName name = new ASTName(2);
        i.jjtAddChild(name, 0);
        assertEquals(name, i.getImportedNameNode());
    }
}
