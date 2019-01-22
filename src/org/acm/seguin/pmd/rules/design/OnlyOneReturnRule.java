package org.acm.seguin.pmd.rules.design;

import org.acm.seguin.pmd.AbstractRule;
import org.acm.seguin.pmd.RuleContext;
import net.sourceforge.jrefactory.ast.ASTInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTReturnStatement;
import net.sourceforge.jrefactory.ast.SimpleNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OnlyOneReturnRule extends AbstractRule {

    public Object visit(ASTInterfaceDeclaration node, Object data) {
        return data;
    }

    public Object visit(ASTMethodDeclaration node, Object data) {
        if (node.isAbstract()) {
            return data;
        }

        List returnNodes = new ArrayList();
        node.findChildrenOfType(ASTReturnStatement.class, returnNodes, false);
        if (returnNodes.size() > 1) {
            RuleContext ctx = (RuleContext) data;
            for (Iterator i = returnNodes.iterator(); i.hasNext();) {
                SimpleNode problem = (SimpleNode) i.next();
                // skip the last one, it's OK
                if (!i.hasNext()) {
                    continue;
                }
                ctx.getReport().addRuleViolation(createRuleViolation(ctx, problem.getBeginLine()));
            }
        }
        return data;
    }

}
