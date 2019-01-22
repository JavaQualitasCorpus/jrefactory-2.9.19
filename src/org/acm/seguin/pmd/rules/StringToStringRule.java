package org.acm.seguin.pmd.rules;

import org.acm.seguin.pmd.AbstractRule;
import org.acm.seguin.pmd.RuleContext;
import net.sourceforge.jrefactory.ast.ASTPrimitiveType;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.pmd.symboltable.Scope;
import org.acm.seguin.pmd.symboltable.NameOccurrence;
import org.acm.seguin.pmd.symboltable.VariableNameDeclaration;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StringToStringRule extends AbstractRule {

    public Object visit(ASTVariableDeclaratorId node, Object data) {
        SimpleNode nameNode = node.getTypeNameNode();
        if (nameNode instanceof ASTPrimitiveType || !nameNode.getImage().equals("String")) {
            return data;
        }
        // now we know we're at a variable declaration of type String
        Map decls = ((Scope)node.getScope()).getVariableDeclarations(true);
        for (Iterator i = decls.keySet().iterator(); i.hasNext();) {
            VariableNameDeclaration decl = (VariableNameDeclaration) i.next();
            if (!decl.getImage().equals(node.getImage())) {
                continue;
            }
            List usages = (List) decls.get(decl);
            for (Iterator j = usages.iterator(); j.hasNext();) {
                NameOccurrence occ = (NameOccurrence) j.next();
                if (occ.getNameForWhichThisIsAQualifier() != null && occ.getNameForWhichThisIsAQualifier().getImage().indexOf("toString") != -1) {
                    RuleContext ctx = (RuleContext) data;
                    ctx.getReport().addRuleViolation(createRuleViolation(ctx, occ.getBeginLine()));
                }
            }
        }
        return data;
    }
}
