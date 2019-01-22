package test.net.sourceforge.pmd.ast;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTTryStatement;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;

public class ASTVariableDeclaratorIdTest extends TestCase {

    public void testIsExceptionBlockParameter() {
        ASTTryStatement tryNode = new ASTTryStatement(1);
        ASTBlock block = new ASTBlock(2);

        ASTVariableDeclaratorId v = new ASTVariableDeclaratorId(3);
        v.jjtSetParent(block);
        block.jjtSetParent(tryNode);

        assertTrue(v.isExceptionBlockParameter());
    }
}
