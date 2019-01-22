package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.SimpleNode;

public class MethodNameDeclaration extends AbstractNameDeclaration implements NameDeclaration {

    public MethodNameDeclaration(ASTMethodDeclarator node) {
        super(node);
    }

    public boolean equals(Object o) {
        MethodNameDeclaration otherMethodDecl = (MethodNameDeclaration) o;

        // compare method name
        if (!otherMethodDecl.node.getImage().equals(node.getImage())) {
            return false;
        }

        // compare parameter count - this catches the case where there are no params, too
        if (((ASTMethodDeclarator) (otherMethodDecl.node)).getParameterCount() != ((ASTMethodDeclarator) node).getParameterCount()) {
            return false;
        }

        // compare parameter types
        ASTFormalParameters myParams = (ASTFormalParameters) node.jjtGetFirstChild();
        ASTFormalParameters otherParams = (ASTFormalParameters) otherMethodDecl.node.jjtGetFirstChild();
        for (int i = 0; i < ((ASTMethodDeclarator) node).getParameterCount(); i++) {
            ASTFormalParameter myParam = (ASTFormalParameter) myParams.jjtGetChild(i);
            ASTFormalParameter otherParam = (ASTFormalParameter) otherParams.jjtGetChild(i);
            int myChildNo = myParam.skipAnnotations();
            int otherChildNo = otherParam.skipAnnotations();
            SimpleNode myTypeNode = (SimpleNode) myParam.jjtGetChild(myChildNo).jjtGetFirstChild();
            SimpleNode otherTypeNode = (SimpleNode) otherParam.jjtGetChild(otherChildNo).jjtGetFirstChild();

            // simple comparison of type images
            // this can be fooled by one method using "String"
            // and the other method using "java.lang.String"
            // once we get real types in here that should get fixed
            if (!myTypeNode.getImage().equals(otherTypeNode.getImage())) {
                return false;
            }

            // if type is ASTPrimitiveType and is an array, make sure the other one is also
        }
        return true;
    }

    public int hashCode() {
        return node.getImage().hashCode() + ((ASTMethodDeclarator) node).getParameterCount();
    }

    public String toString() {
        return "Method " + node.getImage() + ":" + node.getBeginLine();
    }
}
