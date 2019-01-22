package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTForStatement;
import net.sourceforge.jrefactory.ast.ASTIfStatement;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTSwitchStatement;
import net.sourceforge.jrefactory.ast.ASTTryStatement;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.parser.ChildrenVisitor;
import net.sourceforge.jrefactory.ast.SimpleNode;

import java.util.Stack;

/**
 * Serves as a sort of adaptor between the AST nodes and the symbol table scopes
 */
public class BasicScopeCreationVisitor extends ChildrenVisitor {

    private ScopeFactory sf;
    private Stack scopes = new Stack();

    public BasicScopeCreationVisitor(ScopeFactory sf) {
        this.sf = sf;
    }

    public Object visit(ASTCompilationUnit node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTUnmodifiedClassDeclaration node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTClassBodyDeclaration node, Object data) {
        if (node.isAnonymousInnerClass()) {
            sf.openScope(scopes, node);
            cont(node);
        } else {
            super.visit(node, data);
        }
        return data;
    }

    public Object visit(ASTUnmodifiedInterfaceDeclaration node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTBlock node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTConstructorDeclaration node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTMethodDeclaration node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTTryStatement node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTForStatement node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTIfStatement node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    public Object visit(ASTSwitchStatement node, Object data) {
        sf.openScope(scopes, node);
        cont(node);
        return data;
    }

    private void cont(SimpleNode node) {
        super.visit(node, null);
        scopes.pop();
    }
}
