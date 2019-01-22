/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2001 JRefactory.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "JRefactory" must not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission. For written permission, please contact seguin@acm.org.
 *
 * 5. Products derived from this software may not be called "JRefactory",
 *    nor may "JRefactory" appear in their name, without prior written
 *    permission of Chris Seguin.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of JRefactory.  For more information on
 * JRefactory, please see
 * <http://www.sourceforge.org/projects/jrefactory>.
 */
package org.acm.seguin.summary;

import net.sourceforge.jrefactory.parser.JavaParserVisitor;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.parser.Token;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTInterfaceBody;
import net.sourceforge.jrefactory.ast.ASTTryStatement;
import net.sourceforge.jrefactory.ast.ASTSynchronizedStatement;
import net.sourceforge.jrefactory.ast.ASTThrowStatement;
import net.sourceforge.jrefactory.ast.ASTReturnStatement;
import net.sourceforge.jrefactory.ast.ASTForUpdate;
import net.sourceforge.jrefactory.ast.ASTStatementExpressionList;
import net.sourceforge.jrefactory.ast.ASTForInit;
import net.sourceforge.jrefactory.ast.ASTForStatement;
import net.sourceforge.jrefactory.ast.ASTDoStatement;
import net.sourceforge.jrefactory.ast.ASTWhileStatement;
import net.sourceforge.jrefactory.ast.ASTIfStatement;
import net.sourceforge.jrefactory.ast.ASTSwitchLabel;
import net.sourceforge.jrefactory.ast.ASTSwitchStatement;
import net.sourceforge.jrefactory.ast.ASTEmptyStatement;
import net.sourceforge.jrefactory.ast.ASTBlockStatement;
import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTStatement;
import net.sourceforge.jrefactory.ast.ASTAllocationExpression;
import net.sourceforge.jrefactory.ast.ASTArgumentList;
import net.sourceforge.jrefactory.ast.ASTArguments;
import net.sourceforge.jrefactory.ast.ASTNullLiteral;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTCastExpression;
import net.sourceforge.jrefactory.ast.ASTPreDecrementExpression;
import net.sourceforge.jrefactory.ast.ASTPreIncrementExpression;
import net.sourceforge.jrefactory.ast.ASTInstanceOfExpression;
import net.sourceforge.jrefactory.ast.ASTAndExpression;
import net.sourceforge.jrefactory.ast.ASTExclusiveOrExpression;
import net.sourceforge.jrefactory.ast.ASTInclusiveOrExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalAndExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalOrExpression;
import net.sourceforge.jrefactory.ast.ASTConditionalExpression;
import net.sourceforge.jrefactory.ast.ASTExpression;
import net.sourceforge.jrefactory.ast.ASTNameList;
import net.sourceforge.jrefactory.ast.ASTResultType;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTVariableInitializer;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassBody;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import net.sourceforge.jrefactory.ast.ASTArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTContinueStatement;
import net.sourceforge.jrefactory.ast.ASTBreakStatement;
import net.sourceforge.jrefactory.ast.ASTStatementExpression;
import net.sourceforge.jrefactory.ast.ASTLocalVariableDeclaration;
import net.sourceforge.jrefactory.ast.ASTBooleanLiteral;
import net.sourceforge.jrefactory.ast.ASTPrimarySuffix;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTPostfixExpression;
import net.sourceforge.jrefactory.ast.ASTUnaryExpressionNotPlusMinus;
import net.sourceforge.jrefactory.ast.ASTUnaryExpression;
import net.sourceforge.jrefactory.ast.ASTMultiplicativeExpression;
import net.sourceforge.jrefactory.ast.ASTAdditiveExpression;
import net.sourceforge.jrefactory.ast.ASTShiftExpression;
import net.sourceforge.jrefactory.ast.ASTRelationalExpression;
import net.sourceforge.jrefactory.ast.ASTEqualityExpression;
import net.sourceforge.jrefactory.ast.ASTAssignmentOperator;
import net.sourceforge.jrefactory.ast.ASTPrimitiveType;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.ast.ASTExplicitConstructorInvocation;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTImportDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTLiteral;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTLabeledStatement;
import net.sourceforge.jrefactory.ast.ASTArrayDimsAndInits;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.parser.JavaParserConstants;
import org.acm.seguin.util.FileSettings;
import org.acm.seguin.util.MissingSettingsException;
import net.sourceforge.jrefactory.ast.ASTAssertionStatement;

import net.sourceforge.jrefactory.ast.ASTTypeParameterList;
import net.sourceforge.jrefactory.ast.ASTTypeParameter;
import net.sourceforge.jrefactory.ast.ASTTypeArguments;
import net.sourceforge.jrefactory.ast.ASTReferenceTypeList;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.ASTActualTypeArgument;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTGenericNameList;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumElement;
import net.sourceforge.jrefactory.ast.ASTIdentifier;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTMemberValuePairs;
import net.sourceforge.jrefactory.ast.ASTMemberValuePair;
import net.sourceforge.jrefactory.ast.ASTMemberValue;
import net.sourceforge.jrefactory.ast.ASTMemberValueArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTAnnotationTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotationTypeMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotationMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstantDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTJSPBody;

import java.io.*;
import java.util.Enumeration;


/**
 *  This object counts the lines and labels them with specific data
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    March 4, 1999
 */
public class LineCountVisitor implements JavaParserVisitor {
    //  Instance Variables
    private int lineCount;


    /**
     *  Constructor for the LineCountVisitor object
     */
    public LineCountVisitor() {
        lineCount = 1;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(SimpleNode node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTJSPBody node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }

    public Object visit(ASTTypeParameterList node, Object data) {
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx-1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        return data;
    }
    public Object visit(ASTTypeParameter node, Object data) {
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx == 1) {
                countLines(node.getSpecial("extends." + (ndx-1)));
            } if (ndx >1) {
                countLines(node.getSpecial("and." + (ndx-1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        return data;
    }
    public Object visit(ASTTypeArguments node, Object data) {
        countLines(node.getSpecial("<."));
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx-1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
        countLines(node.getSpecial(">."));
        return data;
    }
    public Object visit(ASTReferenceTypeList node, Object data) {
        countLines(node.getSpecial("<."));
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx-1)));
            }
            Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        countLines(node.getSpecial(">."));
        return data;
    }
    public Object visit(ASTReferenceType node, Object data) {
        node.childrenAccept(this, data);   //  Traverse the children
        //  Add the array
        int count = node.getArrayCount();
        for (int ndx = 0; ndx < count; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }
        return data;
    }
    public Object visit(ASTClassOrInterfaceType node, Object data) {
        node.childrenAccept(this, data);   //  Traverse the children
        //  Add the array
        int count = node.getNameSize();
        for (int ndx = 0; ndx < count; ndx++) {
            countLines(node.getSpecial("period." + ndx));
        }
        return data;
    }
    public Object visit(ASTActualTypeArgument node, Object data) {
        countLines(node.getSpecial("?"));
        countLines(node.getSpecial("extends"));
        countLines(node.getSpecial("super"));
        node.childrenAccept(this, data);   //  Traverse the children
        return data;
    }
    public Object visit(ASTTypeParameters node, Object data) {
        countLines(node.getSpecial("<"));
        node.childrenAccept(this, data);   //  Traverse the children
        countLines(node.getSpecial(">"));
        return data;
    }
    public Object visit(ASTGenericNameList node, Object data) {
        //  Traverse the children
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            Node child = node.jjtGetChild(ndx);
            if (ndx > 0 && child instanceof ASTClassOrInterfaceType) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            child.jjtAccept(this, data);
        }
        return data;//  Return the data
    }
    public Object visit(ASTEnumDeclaration node, Object data) {
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("@"));

        countLines(node.getSpecial("enum"));
        int childNo=node.skipAnnotations();
        Node child = node.jjtGetFirstChild();
        child.jjtAccept(this, data);
        child = node.jjtGetChild(++childNo);
        if (child instanceof ASTGenericNameList) {
            countLines(node.getSpecial("implements"));
            child.jjtAccept(this, data);
            child = node.jjtGetChild(++childNo);
        }
        
        countLines(node.getSpecial("begin"));
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = childNo; ndx < countChildren; ndx++) {
            if (node.jjtGetChild(ndx) instanceof ASTEnumElement) {
                if (ndx > childNo) {
                    countLines(node.getSpecial("comma." + (ndx - childNo)));
                }
            }
            child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        countLines(node.getSpecial("end"));
        
        return data;
    }
    public Object visit(ASTEnumElement node, Object data) {
        countLines(node.getSpecial("id"));
        node.childrenAccept(this, data);   //  Traverse the children
        return data;
    }
    public Object visit(ASTIdentifier node, Object data) {
        countLines(node.getSpecial("id"));
        return data;
    }
    public Object visit(ASTAnnotationTypeDeclaration node, Object data) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("strictfp"));
        countLines(node.getSpecial("@"));
        countLines(node.getSpecial("interface"));
        countLines(node.getSpecial("{"));
        node.childrenAccept(this, data);   //  Traverse the children
        countLines(node.getSpecial("}"));
        return data;
    }

    public Object visit(ASTAnnotationTypeMemberDeclaration node, Object data) {
        node.childrenAccept(this, data);   //  Traverse the children
        return data;
    }

    public Object visit(ASTAnnotationMethodDeclaration node, Object data) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("abstract"));
        Node type = node.jjtGetFirstChild();
        type.jjtAccept(this, data);
        Node id = node.jjtGetChild(1);
        id.jjtAccept(this, data);
        countLines(node.getSpecial("("));
        countLines(node.getSpecial(")"));
        if (node.jjtGetNumChildren()>2) {
           countLines(node.getSpecial("default"));
           Node next = node.jjtGetChild(2);
           next.jjtAccept(this, data);
        }
        countLines(node.getSpecial("semicolon"));
        return data;
    }

    public Object visit(ASTConstantDeclaration node, Object data) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("final"));
        Node type = node.jjtGetFirstChild();
        type.jjtAccept(this, data);
        Node var = node.jjtGetChild(1);
        var.jjtAccept(this, data);
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 2; ndx < lastIndex; ndx++) {
            Node next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
            if (ndx < lastIndex-1) {
               countLines(node.getSpecial("comma."+(ndx-2)));
            }
        }
        countLines(node.getSpecial("semicolon"));
        return data;
    }

    public Object visit(ASTAnnotation node, Object data) {
        Node id = node.jjtGetFirstChild();
        id.jjtAccept(this, data);
        countLines(node.getSpecial("begin"));
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 1; ndx < lastIndex; ndx++) {
            Node next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }
        countLines(node.getSpecial("end"));
        return data;
    }

    public Object visit(ASTMemberValuePairs node, Object data) {
       node.childrenAccept(this, data);   //  Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            Node next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
            if (ndx < lastIndex-1) {
               countLines(node.getSpecial("comma."+ndx));
            }
        }
        return data;
    }

    public Object visit(ASTMemberValuePair node, Object data) {
       Node id = node.jjtGetFirstChild();
       id.jjtAccept(this, data);
       countLines(node.getSpecial("="));
       Node value = node.jjtGetChild(1);
       id.jjtAccept(this, data);
       return data;
    }

    public Object visit(ASTMemberValue node, Object data) {
        countLines(node.getSpecial("@"));
        node.childrenAccept(this, data);   //  Traverse the children
        return data;
    }

    public Object visit(ASTMemberValueArrayInitializer node, Object data) {
       countLines(node.getSpecial("{"));
       node.childrenAccept(this, data);   //  Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            Node next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
            if (ndx < lastIndex-1) {
               countLines(node.getSpecial("comma."+ndx));
            }
        }
       countLines(node.getSpecial("}"));
       return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTCompilationUnit node, Object data) {
        //  Accept the children
        node.childrenAccept(this, data);

        //  Visit EOF special token
        countLines(node.getSpecial("EOF"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPackageDeclaration node, Object data) {
        //  Print any tokens
        countLines(node.getSpecial("package"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Print any final tokens
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTImportDeclaration node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("import"));
        countLines(node.getSpecial("static"));   // JDK1.5 static imports

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Print any final tokens
        if (node.isImportingPackage()) {
            countLines(node.getSpecial("period"));
            countLines(node.getSpecial("star"));
            countLines(node.getSpecial("semicolon"));
        } else {
            countLines(node.getSpecial("semicolon"));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTTypeDeclaration node, Object data) {
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            countLines(node.getSpecial("semicolon"));
        }
        return data;
    }


    /**
     *  Visit a class declaration
     *
     *@param  node  the class declaration
     *@param  data  the print data
     *@return       the result of visiting this node
     */
    public Object visit(ASTClassDeclaration node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("strictfp"));
        countLines(node.getSpecial("abstract"));

        //  Get the child
        SimpleNode child = (SimpleNode) node.jjtGetFirstChild();
        countLines(child.getSpecial("class"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTUnmodifiedClassDeclaration node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("id"));

        //  Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTClassOrInterfaceType) {
                countLines(node.getSpecial("extends"));
                next.jjtAccept(this, data);
            } else if (next instanceof ASTGenericNameList) {
                countLines(node.getSpecial("implements"));
                next.jjtAccept(this, data);
            } else {
                next.jjtAccept(this, data);
            }
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTClassBody node, Object data) {
        //  Print any tokens
        countLines(node.getSpecial("begin"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Print any tokens
        countLines(node.getSpecial("end"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTNestedClassDeclaration node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("strictfp"));

        //  Get the child
        SimpleNode child = (SimpleNode) node.jjtGetFirstChild();
        countLines(child.getSpecial("class"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTClassBodyDeclaration node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInterfaceDeclaration node, Object data) {

        //  Print any special tokens
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("strictfp"));
        countLines(node.getSpecial("@"));

        //  Get the child
        int childNo= node.skipAnnotations();
        Node child = node.jjtGetChild(childNo);
        countLines(((SimpleNode)child).getSpecial("interface"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTNestedInterfaceDeclaration node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("strictfp"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));

        //  Get the child
        SimpleNode child = (SimpleNode) node.jjtGetFirstChild();
        countLines(child.getSpecial("interface"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Finish this interface
        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTUnmodifiedInterfaceDeclaration node, Object data) {
        //  Local Variables
        boolean first = true;

        //  Print any tokens
        countLines(node.getSpecial("id"));

        //  Traverse the children
        int nextIndex = 0;
        Node next = node.jjtGetChild(nextIndex);
        if (next instanceof ASTGenericNameList) {
            countLines(node.getSpecial("extends"));
            next.jjtAccept(this, data);

            //  Get the next node
            nextIndex++;
            next = node.jjtGetChild(nextIndex);
        }

        //  Handle the interface body
        next.jjtAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInterfaceBody node, Object data) {
        countLines(node.getSpecial("begin"));   //  Begin the block
        node.childrenAccept(this, data);        //  Travers the children
        countLines(node.getSpecial("end"));     //  End the block
        return data;                            //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInterfaceMemberDeclaration node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTFieldDeclaration node, Object data) {
        //  Print any tokens
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("transient"));
        countLines(node.getSpecial("volatile"));
        countLines(node.getSpecial("strictfp"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("@"));

        //  Handle the first two children (which are required)
        int childNo = 0;
        Node next = node.jjtGetFirstChild();
        next.jjtAccept(this, data);
        while (next instanceof ASTAnnotation) {
           next = node.jjtGetChild(++childNo);
           next.jjtAccept(this, data);
        }
        next = node.jjtGetChild(++childNo);
        next.jjtAccept(this, data);

        //  Traverse the rest of the children
        for (int ndx = childNo+1; ndx < node.jjtGetNumChildren(); ndx++) {
            countLines(node.getSpecial("comma." + (ndx - childNo - 1)));
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }

        //  Finish the entry
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTVariableDeclarator node, Object data) {

        //  Handle the first child (which is required)
        Node next = node.jjtGetFirstChild();
        next.jjtAccept(this, data);

        //  Traverse the rest of the children
        if (node.jjtGetNumChildren() > 1) {
            countLines(node.getSpecial("equals"));
            next = node.jjtGetChild(1);
            next.jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTVariableDeclaratorId node, Object data) {

        //  Handle the first child (which is required)
        countLines(node.getSpecial("id"));
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTVariableInitializer node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArrayInitializer node, Object data) {

        //  Handle the first child (which is required)
        countLines(node.getSpecial("begin"));
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        if (node.isFinalComma()) {
            countLines(node.getSpecial("comma." + (last - 1)));
        }
        countLines(node.getSpecial("end"));

        //  Return the data
        return data;
    }


    /**
     *  Pretty prints the method declaration
     *
     *@param  node  the node in the parse tree
     *@param  data  the print data
     *@return       the print data
     */
    public Object visit(ASTMethodDeclaration node, Object data) {
        //  Print any tokens
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("native"));
        countLines(node.getSpecial("strictfp"));
        countLines(node.getSpecial("synchronized"));
        countLines(node.getSpecial("@"));
        int childNo=node.skipAnnotationsAndTypeParameters();
        Node child = node.jjtGetChild(childNo);
        countLines(getInitialToken((ASTResultType)child));

        //  Handle the first two/three children (which are required)
        childNo = 0;
        Node next = node.jjtGetFirstChild();
        next.jjtAccept(this, data);
        while (next instanceof ASTAnnotation) {
           next = node.jjtGetChild(++childNo);
           next.jjtAccept(this, data);
        }
        if (next instanceof ASTTypeParameters) {
           next = node.jjtGetChild(++childNo);
           next.jjtAccept(this, data);
        }

        next = node.jjtGetChild(++childNo);
        next.jjtAccept(this, data);

        //  Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        boolean foundBlock = false;
        for (int ndx = childNo+1; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            if (next instanceof ASTNameList) {
                countLines(node.getSpecial("throws"));
                next.jjtAccept(this, data);
            } else if (next instanceof ASTBlock) {
                foundBlock = true;
                next.jjtAccept(this, data);
            }
        }

        //  Finish if it is abstract
        if (!foundBlock) {
            countLines(node.getSpecial("semicolon"));
        }

        //  Note the end of the method
        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTMethodDeclarator node, Object data) {
        //  Handle the first child (which is required)
        countLines(node.getSpecial("id"));
        node.childrenAccept(this, data);

        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTFormalParameters node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("begin"));

        //  Traverse the children
        Node next;
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }

        //  Finish it
        countLines(node.getSpecial("end"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTFormalParameter node, Object data) {

        //  Print any tokens
        if (node.isUsingFinal()) {
            countLines(node.getSpecial("final"));
            countLines(node.getSpecial("@"));
        }

        //  Traverse the children
        int childNo = 0;
        Node next = node.jjtGetFirstChild();
        next.jjtAccept(this, data);
        while (next instanceof ASTAnnotation) {
           next = node.jjtGetChild(++childNo);
           next.jjtAccept(this, data);
        }
        next = node.jjtGetChild(childNo+1);
        next.jjtAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTConstructorDeclaration node, Object data) {

        //  Print any tokens
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("id"));
        countLines(node.getSpecial("@"));

        //  Handle the first child (which is required)
        int childNo = 0;
        Node child = node.jjtGetFirstChild();
        while (child instanceof ASTAnnotation) {
            child.jjtAccept(this, data);
            child = node.jjtGetChild(++childNo);
        }
        if (child instanceof ASTTypeParameters) {
            child.jjtAccept(this, data);
            child = node.jjtGetChild(++childNo);
        }
        child.jjtAccept(this, data);

        //  Get the last index
        int lastIndex = node.jjtGetNumChildren();
        int startAt = childNo+1;

        //  Handle the name list if it is present
        if (lastIndex > 1) {
            child = node.jjtGetChild(startAt);
            if (child instanceof ASTNameList) {
                countLines(node.getSpecial("throws"));
                child.jjtAccept(this, data);
                startAt++;
            }
        }

        //  Print the starting block
        countLines(node.getSpecial("begin"));

        //  Traverse the rest of the children
        boolean foundBlock = false;
        for (int ndx = startAt; ndx < lastIndex; ndx++) {
            child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }

        //  Print the end block
        countLines(node.getSpecial("end"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTExplicitConstructorInvocation node, Object data) {

        //  Print the name of the node
        int startWith = 0;
        if (node.jjtGetFirstChild() instanceof ASTPrimaryExpression) {
            node.jjtGetFirstChild().jjtAccept(this, data);
            startWith++;
            countLines(node.getSpecial("."));
        }
        countLines(node.getSpecial("explicit"));

        //  Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = startWith; ndx < last; ndx++) {
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }

        //  End of the line
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Formats the initializer
     *
     *@param  node  The initializer node
     *@param  data  The print data
     *@return       Nothing interesting
     */
    public Object visit(ASTInitializer node, Object data) {

        //  Print the name of the node
        if (node.isUsingStatic()) {
            countLines(node.getSpecial("static"));
        }

        node.childrenAccept(this, data);   //  Traverse the children
        return data;                       //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTType node, Object data) {
        node.childrenAccept(this, data); //  Traverse the children
        return data;                     //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPrimitiveType node, Object data) {
        countLines(node.getSpecial("primitive"));  //  Print the name of the node
        return data;                               //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTResultType node, Object data) {

        //  Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            countLines(node.getSpecial("primitive"));
        }

        return data;                  //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTName node, Object data) {

        //  Print the name of the node
        int size = node.getNameSize();
        for (int ndx = 0; ndx < size; ndx++) {
            if (ndx>0) {
                countLines(node.getSpecial("period" + ndx));
            }
            countLines(node.getSpecial("id" + ndx));
        }

        return data;        //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTNameList node, Object data) {

        //  Traverse the children
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTExpression node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTAssignmentOperator node, Object data) {

        //  Print the name of the node
        countLines(node.getSpecial("operator"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTConditionalExpression node, Object data) {

        //  Traverse the children
        int childCount = node.jjtGetNumChildren();
        if (childCount == 1) {
            node.jjtGetFirstChild().jjtAccept(this, data);
        } else {
            node.jjtGetFirstChild().jjtAccept(this, data);
            countLines(node.getSpecial("?"));
            node.jjtGetChild(1).jjtAccept(this, data);
            countLines(node.getSpecial(":"));
            node.jjtGetChild(2).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTConditionalOrExpression node, Object data) {
        return binaryExpression(node, "||", data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTConditionalAndExpression node, Object data) {
        return binaryExpression(node, "&&", data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInclusiveOrExpression node, Object data) {
        return binaryExpression(node, "|", data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTExclusiveOrExpression node, Object data) {
        return binaryExpression(node, "^", data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTAndExpression node, Object data) {
        return binaryExpression(node, "&", data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTEqualityExpression node, Object data) {
        return binaryExpression2(node, node.getNames(), data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInstanceOfExpression node, Object data) {
        return binaryExpression(node, "instanceof", data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTRelationalExpression node, Object data) {
        return binaryExpression2(node, node.getNames(), data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTShiftExpression node, Object data) {
        return binaryExpression2(node, node.getNames(), data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTAdditiveExpression node, Object data) {
        return binaryExpression2(node, node.getNames(), data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTMultiplicativeExpression node, Object data) {
        return binaryExpression2(node, node.getNames(), data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTUnaryExpression node, Object data) {

        //  Traverse the children
        Node child = node.jjtGetFirstChild();
        if (child instanceof ASTUnaryExpression) {
            countLines(node.getSpecial("operator"));
        }
        child.jjtAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPreIncrementExpression node, Object data) {

        //  Include the preincrement
        countLines(node.getSpecial("operator"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPreDecrementExpression node, Object data) {

        //  Include the preincrement
        countLines(node.getSpecial("operator"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTUnaryExpressionNotPlusMinus node, Object data) {

        //  Traverse the children
        Node child = node.jjtGetFirstChild();
        if (child instanceof ASTUnaryExpression) {
            countLines(node.getSpecial("operator"));
        }
        child.jjtAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPostfixExpression node, Object data) {

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Include the increment
        countLines(node.getSpecial("operator"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTCastExpression node, Object data) {

        //  Cast portion
        countLines(node.getSpecial("begin"));
        node.jjtGetFirstChild().jjtAccept(this, data);
        countLines(node.getSpecial("end"));

        //  Expression
        node.jjtGetChild(1).jjtAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPrimaryExpression node, Object data) {
        node.childrenAccept(this, data);
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPrimaryPrefix node, Object data) {

        //  Traverse the children
        if (node.jjtGetNumChildren() == 0) {
            int count = node.getCount();
            for (int i=0; i<count; i++) {
                countLines(node.getSpecial("this."+i));
            }
            countLines(node.getSpecial("this"));
            countLines(node.getSpecial("id"));
        } else {
            Node child = node.jjtGetFirstChild();
            if ((child instanceof ASTLiteral) ||
                    (child instanceof ASTName) ||
                    (child instanceof ASTAllocationExpression)) {
                child.jjtAccept(this, data);
            } else if (child instanceof ASTExpression) {
                countLines(node.getSpecial("begin"));
                child.jjtAccept(this, data);
                countLines(node.getSpecial("end"));
            } else if (child instanceof ASTResultType) {
                child.jjtAccept(this, data);
            } else {
                System.out.println("UNEXPECTED child for ASTPrimaryPrefix ="+child);
                child.jjtAccept(this, data);
            }
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTPrimarySuffix node, Object data) {

        //  Traverse the children
        if (node.jjtGetNumChildren() == 0) {
            countLines(node.getSpecial("dot"));
            countLines(node.getSpecial("id"));
        } else {
            Node child = node.jjtGetFirstChild();
            if (child instanceof ASTArguments) {
                child.jjtAccept(this, data);
            } else if (child instanceof ASTExpression) {
                countLines(node.getSpecial("["));
                child.jjtAccept(this, data);
                countLines(node.getSpecial("]"));
            } else if (child instanceof ASTAllocationExpression) {
                countLines(node.getSpecial("dot"));
                child.jjtAccept(this, data);
            } else if (child instanceof ASTReferenceTypeList) {
                countLines(node.getSpecial("dot"));
                countLines(node.getSpecial("id"));
                child.jjtAccept(this, data);
            }
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTLiteral node, Object data) {

        //  Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            countLines(node.getSpecial("id"));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTBooleanLiteral node, Object data) {
        countLines(node.getSpecial("id"));  //  Print the data
        return data;                        //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTNullLiteral node, Object data) {
        countLines(node.getSpecial("id"));  //  Print the data
        return data;                        //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArguments node, Object data) {
        countLines(node.getSpecial("begin"));      //  Start the parens
        node.childrenAccept(this, data);           //  Traverse the children
        countLines(node.getSpecial("end"));        //  Finish the parens
        return data;                               //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArgumentList node, Object data) {

        //  Traverse the children
        int count = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < count; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTAllocationExpression node, Object data) {
        //  Traverse the children
        countLines(node.getSpecial("id"));    //  Print the name of the node
        node.childrenAccept(this, data);      //  Traverse the children
        return data;                          //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArrayDimsAndInits node, Object data) {

        //  Traverse the children
        boolean foundInitializer = false;
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (node.jjtGetChild(ndx) instanceof ASTExpression) {
                countLines(node.getSpecial("[." + ndx));
                node.jjtGetChild(ndx).jjtAccept(this, data);
                countLines(node.getSpecial("]." + ndx));
            } else if (node.jjtGetChild(ndx) instanceof ASTArrayInitializer) {
                foundInitializer = true;
            }
        }
        int looping = node.getArrayCount();
        if (foundInitializer) {
            looping++;
        }

        for (int ndx = last; ndx < looping; ndx++) {
            countLines(node.getSpecial("[." + ndx));
            countLines(node.getSpecial("]." + ndx));
        }
        if (foundInitializer) {
            node.jjtGetChild(last - 1).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTStatement node, Object data) {

        //  Traverse the children
        node.childrenAccept(this, data);
        if (node.jjtGetFirstChild() instanceof ASTStatementExpression) {
            //  Finish off the statement expression
            countLines(node.getSpecial("semicolon"));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTLabeledStatement node, Object data) {

        //  Print the data
        countLines(node.getSpecial("id"));
        countLines(node.getSpecial("colon"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Visits a block in the parse tree
     *
     *@param  node  the block node
     *@param  data  the information that is used to traverse the tree
     *@return       data is returned
     */
    public Object visit(ASTBlock node, Object data) {
        //  Start the block
        countLines(node.getSpecial("begin"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Finish the block
        countLines(node.getSpecial("end"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTBlockStatement node, Object data) {

        //  Include the stuff before the class/interface declaration
        if (node.hasAnyChildren()) {
            SimpleNode child = (SimpleNode) node.jjtGetFirstChild();

            if (node.isFinal()) {
                countLines(node.getSpecial("final"));
            }
            if (child instanceof ASTUnmodifiedClassDeclaration) {
                countLines(child.getSpecial("class"));
            } else if (child instanceof ASTUnmodifiedInterfaceDeclaration) {
                countLines(child.getSpecial("interface"));
            }
        }
        countLines(node.getSpecial("semicolon"));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTLocalVariableDeclaration node, Object data) {
        //  Traverse the children
        int last = node.jjtGetNumChildren();

        //  Print the final token
        if (node.isUsingFinal()) {
            countLines(node.getSpecial("final"));
        }

        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx>1) {
                countLines(node.getSpecial("comma." + (ndx - 2)));
            }
            //  Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTEmptyStatement node, Object data) {

        //  Print the name of the node
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTStatementExpression node, Object data) {

        //  Traverse the children
        if (node.jjtGetFirstChild() instanceof ASTPrimaryExpression) {
            int last = node.jjtGetNumChildren();
            node.jjtGetFirstChild().jjtAccept(this, data);
            countLines(node.getSpecial("id"));
            for (int ndx = 1; ndx < last; ndx++) {
                node.jjtGetChild(ndx).jjtAccept(this, data);
            }
        } else {
            node.childrenAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Counts the number of lines associated with a switch statement
     *
     *@param  node  the switch node in the parse tree
     *@param  data  the data used to visit this parse tree
     *@return       the data
     */
    public Object visit(ASTSwitchStatement node, Object data) {

        //  Switch
        countLines(node.getSpecial("switch"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetFirstChild().jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));

        //  Start the block
        countLines(node.getSpecial("beginBlock"));

        //  Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = 1; ndx < last; ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTBlockStatement) {
                next.jjtAccept(this, data);
            } else {
                next.jjtAccept(this, data);
            }
        }

        //  End the block
        countLines(node.getSpecial("endBlock"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTSwitchLabel node, Object data) {

        //  Determine if the node has children
        if (node.hasAnyChildren()) {
            countLines(node.getSpecial("id"));
            node.childrenAccept(this, data);
            countLines(node.getSpecial("colon"));
        } else {
            countLines(node.getSpecial("id"));
            countLines(node.getSpecial("colon"));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTIfStatement node, Object data) {

        //  Determine if the node has children
        countLines(node.getSpecial("if"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetFirstChild().jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));

        //  Determine if the then contains a block
        boolean hasElse = (node.jjtGetNumChildren() == 3);
        if (node.jjtGetNumChildren() >= 2) {
            forceBlock(node.jjtGetChild(1), data);
        }

        //  Determine if the else part
        if (hasElse) {
            countLines(node.getSpecial("else"));
            //  Determine if the next item is a statement
            ASTStatement child = (ASTStatement) node.jjtGetChild(2);
            Node next = child.jjtGetFirstChild();
            if (next instanceof ASTIfStatement) {
                next.jjtAccept(this, data);
            } else {
                forceBlock(child, data);
            }
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTWhileStatement node, Object data) {

        //  Determine if the node has children
        countLines(node.getSpecial("while"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetFirstChild().jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));

        //  Process the block
        forceBlock(node.jjtGetChild(1), data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTDoStatement node, Object data) {

        //  Begin the do block
        countLines(node.getSpecial("do"));

        //  Process the block
        forceBlock(node.jjtGetFirstChild(), data);

        //  Process the while block
        countLines(node.getSpecial("while"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetChild(1).jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTForStatement node, Object data) {

        //  Start the for loop
        countLines(node.getSpecial("for"));
        countLines(node.getSpecial("beginExpr"));

        //  Traverse the children
        Node next = node.jjtGetFirstChild();
        int index = 1;
        if (next instanceof ASTLocalVariableDeclaration) {
            next.jjtAccept(this, data);
            countLines(node.getSpecial("loopover"));
            next = node.jjtGetChild(index);
            index++;
            next.jjtAccept(this, data);
        } else {
            if (next instanceof ASTForInit) {
                next.jjtAccept(this, data);
                next = node.jjtGetChild(index);
                index++;
            }
            countLines(node.getSpecial("init"));
            if (next instanceof ASTExpression) {
                next.jjtAccept(this, data);
                next = node.jjtGetChild(index);
                index++;
            }
            countLines(node.getSpecial("test"));
            if (next instanceof ASTForUpdate) {
                next.jjtAccept(this, data);
                next = node.jjtGetChild(index);
                index++;
            }
        }
        countLines(node.getSpecial("endExpr"));
        forceBlock(next, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTForInit node, Object data) {

        //  Traverse the children
        Node next = node.jjtGetFirstChild();
        if (next instanceof ASTLocalVariableDeclaration) {
            forInit((ASTLocalVariableDeclaration) next, data);
        } else {
            node.childrenAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTStatementExpressionList node, Object data) {

        //  Traverse the children
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTForUpdate node, Object data) {
        //  Traverse the children
        node.childrenAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTBreakStatement node, Object data) {

        //  Print the break statement
        countLines(node.getSpecial("break"));
        String name = node.getName();
        if (!((name == null) || (name.length() == 0))) {
            countLines(node.getSpecial("id"));
        }
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTContinueStatement node, Object data) {

        //  Print the continue statement
        countLines(node.getSpecial("continue"));
        String name = node.getName();
        if (!((name == null) || (name.length() == 0))) {
            countLines(node.getSpecial("id"));
        }
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTReturnStatement node, Object data) {

        //  Traverse the children
        if (node.hasAnyChildren()) {
            countLines(node.getSpecial("return"));
            node.childrenAccept(this, data);
            countLines(node.getSpecial("semicolon"));
        } else {
            countLines(node.getSpecial("return"));
            countLines(node.getSpecial("semicolon"));
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTThrowStatement node, Object data) {
        //  Traverse the children
        countLines(node.getSpecial("throw"));
        node.childrenAccept(this, data);
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTSynchronizedStatement node, Object data) {

        //  Traverse the children
        countLines(node.getSpecial("synchronized"));
        countLines(node.getSpecial("beginExpr"));
        node.jjtGetFirstChild().jjtAccept(this, data);
        countLines(node.getSpecial("endExpr"));
        node.jjtGetChild(1).jjtAccept(this, data);

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTTryStatement node, Object data) {
        //  Traverse the children
        countLines(node.getSpecial("try"));
        node.jjtGetFirstChild().jjtAccept(this, data);

        //  Now work with the pairs
        int last = node.jjtGetNumChildren();
        boolean paired = false;
        int catchCount = 0;
        for (int ndx = 1; ndx < last; ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTFormalParameter) {
                countLines(node.getSpecial("catch" + catchCount));
                countLines(node.getSpecial("beginExpr" + catchCount));
                next.jjtAccept(this, data);
                countLines(node.getSpecial("endExpr" + catchCount));
                paired = true;
                catchCount++;
            } else {
                if (!paired) {
                    countLines(node.getSpecial("finally"));
                }
                next.jjtAccept(this, data);
                paired = false;
            }
        }

        //  Return the data
        return data;
    }


    /**
     *  Visit the assertion node
     *
     *@param  node  the node
     *@param  data  the data needed to perform the visit
     *@return       the result of visiting the node
     */
    public Object visit(ASTAssertionStatement node, Object data)
    {
        //  Traverse the children
        countLines(node.getSpecial("assert"));
        node.jjtGetFirstChild().jjtAccept(this, data);
        if (node.jjtGetNumChildren() > 1) {
	        countLines(node.getSpecial("colon"));
	        node.jjtGetChild(1).jjtAccept(this, data);
        }
        countLines(node.getSpecial("semicolon"));

        //  Return the data
        return data;
    }

    /**
     *  Returns the current line count
     *
     *@return    the line count
     */
    protected int getLineCount() {
        return lineCount;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  name  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    protected Object binaryExpression(SimpleNode node, String name, Object data) {

        //  Traverse the children
        int childCount = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("operator." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node   Description of Parameter
     *@param  names  Description of Parameter
     *@param  data   Description of Parameter
     *@return        Description of the Returned Value
     */
    protected Object binaryExpression2(SimpleNode node, Enumeration names, Object data) {

        //  Traverse the children
        int childCount = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < childCount; ndx++) {
            if (ndx > 0) {
                countLines(node.getSpecial("operator." + (ndx - 1)));
            }
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }

        //  Return the data
        return data;
    }


    /**
     *  This code is based on the pretty printer, and was used to force the
     *  source code to have a block for an if statement.
     *
     *@param  node  The node that should be contained inside a block
     *@param  data  the print data
     */
    protected void forceBlock(Node node, Object data) {
        if ((node.jjtGetNumChildren() > 0) &&
                (node.jjtGetFirstChild() instanceof ASTBlock)) {
            //  We know we have a block
            ASTBlock child = (ASTBlock) node.jjtGetFirstChild();

            child.jjtAccept(this, data);
        } else {
            ((SimpleNode) node).childrenAccept(this, data);
        }
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     */
    protected void forInit(ASTLocalVariableDeclaration node, Object data) {

        //  Traverse the children
        int last = node.jjtGetNumChildren();
        Node typeNode = node.jjtGetFirstChild();
        typeNode.jjtAccept(this, data);

        for (int ndx = 1; ndx < last; ndx++) {
            if (ndx > 1) {
                //  Add a comma between entries
                countLines(node.getSpecial("comma." + (ndx - 1)));
            }

            //  Print the final token
            if (node.isUsingFinal()) {
                countLines(node.getSpecial("final"));
            }

            //  Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
    }


    /**
     *  Check the initial token, and removes it from the object.
     *
     *@param  top  the result type
     *@return      the initial token
     */
    protected Token getInitialToken(ASTResultType top) {
        //  Check to see if we need to go farther down
        if (top.hasAnyChildren()) {
            ASTType type = (ASTType) top.jjtGetFirstChild();
            if (type.jjtGetFirstChild() instanceof ASTPrimitiveType) {
                ASTPrimitiveType primitiveType = (ASTPrimitiveType) type.jjtGetFirstChild();
                Token tok = primitiveType.getSpecial("primitive");
                primitiveType.removeSpecial("primitive");
                return tok;
            }
            else if (type.jjtGetFirstChild() instanceof ASTReferenceType) {
                    ASTReferenceType reference = (ASTReferenceType) type.jjtGetFirstChild();
                    if (reference.jjtGetFirstChild() instanceof ASTPrimitiveType) {
                            ASTPrimitiveType primitiveType = (ASTPrimitiveType) reference.jjtGetFirstChild();
                            Token tok = primitiveType.getSpecial("primitive");
                            primitiveType.removeSpecial("primitive");
                            return tok;
                    } else {
                            ASTClassOrInterfaceType name = (ASTClassOrInterfaceType) reference.jjtGetFirstChild();
                            ASTIdentifier id = (ASTIdentifier) name.jjtGetFirstChild();
                            Token tok = id.getSpecial("id");
                            id.removeSpecial("id");
                            return tok;
                    }
            }
            else {
                ASTClassOrInterfaceType name = (ASTClassOrInterfaceType) type.jjtGetFirstChild();
                ASTIdentifier id = (ASTIdentifier) name.jjtGetFirstChild();
                Token tok = id.getSpecial("id");
                id.removeSpecial("id");
                return tok;
            }
        } else {
            //  No farther to go - return the token
            Token tok = top.getSpecial("primitive");
            top.removeSpecial("primitive");
            return tok;
        }
    }

    /**
     *  Check the initial token, and removes it from the object.
     *
     *@param  top  the result type
     *@return      the initial token
     */
    protected Token getInitialToken(ASTTypeParameters top) {
        Token tok = top.getSpecial("<");
        top.removeSpecial("<");
        return tok;
    }



    /**
     *  Counts the number of lines in the current token
     *
     *@param  token  the token
     */
    protected void countLines(Token token) {
        if (token == null) {
            return;
        }

        Token first = beginning(token);
        Token current = first;
        while (current != null) {
            switch (current.kind) {
                //case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    lineCount++;
                    break;
                case JavaParserConstants.SINGLE_LINE_COMMENT:
                case JavaParserConstants.FORMAL_COMMENT:
                case JavaParserConstants.MULTI_LINE_COMMENT:
                case JavaParserConstants.CATEGORY_COMMENT:
                    countNewlines(current.image);
                    break;
                default:
                    //System.out.println("Unknown code:  " + current.kind);
                    break;
            }
            current = current.next;
        }
    }


    /**
     *  Got to the beginning
     *
     *@param  tok  Description of Parameter
     *@return      Description of the Returned Value
     */
    private Token beginning(Token tok) {
        if (tok == null) {
            return null;
        }

        //  Find the first token
        Token current = tok;
        Token previous = tok.specialToken;
        while (previous != null) {
            current = previous;
            previous = current.specialToken;
        }

        //  Return the first
        return current;
    }


    /**
     *  Counts the lines in the token's string
     *
     *@param  value  the string to count
     */
    private void countNewlines(String value) {
        for (int ndx = 0; ndx < value.length(); ndx++) {
            if (value.charAt(ndx) == '\n') {
                lineCount++;
            }
        }
    }
}
