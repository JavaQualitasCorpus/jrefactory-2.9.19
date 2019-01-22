package org.acm.seguin.pmd.rules;

import org.acm.seguin.pmd.AbstractRule;
import org.acm.seguin.pmd.Rule;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;

public class SymbolTableTestRule extends AbstractRule implements Rule {

    public Object visit(ASTVariableDeclaratorId node, Object data) {
        SimpleNode n = node.getTypeNameNode();
        return super.visit(node, data);
    }

}

