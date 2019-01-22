package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.parser.ChildrenVisitor;

public class DeclarationFinder extends ChildrenVisitor {

    public Object visit(ASTVariableDeclaratorId node, Object data) {
        ((Scope)node.getScope()).addDeclaration(new VariableNameDeclaration(node));
        return super.visit(node, data);
    }

    public Object visit(ASTMethodDeclarator node, Object data) {
        ((Scope)node.getScope()).getEnclosingClassScope().addDeclaration(new MethodNameDeclaration(node));
        return super.visit(node, data);
    }
}
