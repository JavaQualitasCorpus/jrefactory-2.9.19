package org.acm.seguin.pmd.rules;

import org.acm.seguin.pmd.AbstractRule;
import net.sourceforge.jrefactory.ast.ASTBlockStatement;
import net.sourceforge.jrefactory.ast.ASTForStatement;
import net.sourceforge.jrefactory.ast.Node;

public class StringConcatenationRule extends AbstractRule {

    public Object visit(ASTForStatement node, Object data) {
        Node forLoopStmt = null;
        for (int i = 0; i < 4; i++) {
            forLoopStmt = node.jjtGetChild(i);
            if (forLoopStmt instanceof ASTBlockStatement) {
                break;
            }
        }
        if (forLoopStmt == null) {
            return data;
        }


        return data;
    }
}
