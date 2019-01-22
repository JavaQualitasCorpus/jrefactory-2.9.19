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
package org.acm.seguin.tools.stub;
import org.acm.seguin.pretty.PrintData;
import net.sourceforge.jrefactory.parser.JavaParserVisitor;
import net.sourceforge.jrefactory.ast.Node;
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
import net.sourceforge.jrefactory.ast.ASTAssertionStatement;
import java.io.*;
import java.util.Enumeration;

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

import org.acm.seguin.pretty.jdi.*;


/**
 *  This object simply reflects all the processing back to the individual nodes.
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    October 13, 1999
 *@date       March 4, 1999
 */
public class StubPrintVisitor implements JavaParserVisitor {
    /**
     *  Constructor for the StubPrintVisitor object
     */
    public StubPrintVisitor() { }


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
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTTypeParameter node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTTypeArguments node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTReferenceTypeList node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTReferenceType node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        //  Add the array
        int count = node.getArrayCount();
        for (int ndx = 0; ndx < count; ndx++) {
            printData.appendText("[");
            printData.appendText("]");
        }
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTClassOrInterfaceType node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print the name of the node
        printData.appendText(node.getName());

        //  Return the data
        return data;
    }

    public Object visit(ASTActualTypeArgument node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTTypeParameters node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTGenericNameList node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        //  Traverse the children
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
            }
            Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        return data;  //  Return the data
    }


    public Object visit(ASTEnumDeclaration node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTEnumElement node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }
    public Object visit(ASTIdentifier node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTAnnotationTypeDeclaration node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTAnnotationTypeMemberDeclaration node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTAnnotationMethodDeclaration node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTConstantDeclaration node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTAnnotation node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTMemberValuePairs node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTMemberValuePair node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTMemberValue node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }

    public Object visit(ASTMemberValueArrayInitializer node, Object data) {
        PrintData printData = (PrintData) data; //  Get the data
        node.childrenAccept(this, data);        //  Accept the children
        printData.flush();                      //  Flush the buffer
        return data;                            //  Return the data
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTCompilationUnit node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Accept the children
        node.childrenAccept(this, data);

        //  Flush the buffer
        printData.flush();

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.appendKeyword("package");
        printData.space();

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Print any final tokens
        printData.appendText(";");
        printData.newline();

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.appendKeyword("import ");

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Print any final tokens
        if (node.isImportingPackage()) {
            printData.appendText(".");
            printData.appendText("*");
            printData.appendText(";");
            printData.newline();
        } else {
            printData.appendText(";");
            printData.newline();
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
            //  Get the data
            PrintData printData = (PrintData) data;
            printData.appendText(";");
            printData.newline();
        }
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTClassDeclaration node, Object data) {
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;

        //  Get the child
        //SimpleNode child = (SimpleNode) node.jjtGetFirstChild();
        int childNo = node.skipAnnotations();
        Node child = node.jjtGetChild(childNo);

        //  Indent and insert the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));

        //  Traverse the children
        child.jjtAccept(this, data);

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.appendKeyword("class ");
        printData.appendText(node.getName());

        //  Traverse the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTClassOrInterfaceType) {
                printData.appendKeyword(" extends ");
                next.jjtAccept(this, data);
            } else if (next instanceof ASTNameList) {
                printData.appendKeyword(" implements ");
                next.jjtAccept(this, data);
            } else if (next instanceof ASTClassBody) {
                next.jjtAccept(this, data);
            } else {
                // TypeParameters - don't do anything
                //next.jjtAccept(this, data);
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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.beginBlock(false, false);

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Print any tokens
        printData.endBlock(); //false, false);

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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.beginClass();

        //  Get the child
        SimpleNode child = (SimpleNode) node.jjtGetFirstChild();

        //  Indent and include the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));

        //  Traverse the children
        node.childrenAccept(this, data);
        printData.endClass();

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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;

        //  Get the child
        int childNo = node.skipAnnotations();
        Node child = node.jjtGetChild(childNo);

        //  Indent and add the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));

        //  Traverse the children
        child.jjtAccept(this, data);
        printData.flush();                      //  Flush the buffer

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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;
        NestedInterfaceDeclaration intfJDI = new NestedInterfaceDeclaration(node);

        //  Print any tokens
        printData.beginInterface();

        //  Get the child
        SimpleNode child = (SimpleNode) node.jjtGetFirstChild();

        //  Force the Javadoc to be included
        if (intfJDI.isRequired()) {
            intfJDI.finish();
            //node.printJavaDocComponents(printData);
        }

        //  Indent and include the modifiers
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));

        //  Traverse the children
        node.childrenAccept(this, data);

        //  Finish this interface
        printData.endInterface();

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.appendKeyword("interface ");
        printData.appendText(node.getName());

        //  Traverse the children
        for (int ndx = 0; ndx < node.jjtGetNumChildren(); ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTGenericNameList) {
                printData.appendKeyword(" extends ");
                next.jjtAccept(this, data);
            } else if (next instanceof ASTInterfaceBody) {
                next.jjtAccept(this, data);
            } else {
                // TypeParameters - don't do anything
                //next.jjtAccept(this, data);
            }
        }
        printData.flush();                      //  Flush the buffer

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Begin the block
        printData.beginBlock(false, false);

        //  Traverse the children
        node.childrenAccept(this, data);

        //  End the block
        printData.endBlock(); //false, false);

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
    public Object visit(ASTInterfaceMemberDeclaration node, Object data) {
        for (int ndx = 0; ndx < node.jjtGetNumChildren(); ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTMethodDeclaration) {
               ((ASTMethodDeclaration)next).setPublic();
               next.jjtAccept(this, data);
            }
        }
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;
        FieldDeclaration fieldJDI = new FieldDeclaration(node);

        //  Print any tokens
        printData.beginField();
        if (fieldJDI.isRequired()) {
            fieldJDI.finish();
            //node.printJavaDocComponents(printData);
        }
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));

        //  Handle the first two children (which are required)
        int childNo = node.skipAnnotations();
        Node next = node.jjtGetChild(childNo);
        next.jjtAccept(this, data);
        printData.space();
        next = node.jjtGetChild(childNo+1);
        next.jjtAccept(this, data);

        //  Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = childNo+2; ndx < lastIndex; ndx++) {
            printData.appendText(", ");
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }

        //  Finish the entry
        printData.appendText(";");
        printData.newline();
        printData.endField();

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Handle the first child (which is required)
        Node next = node.jjtGetFirstChild();
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
    public Object visit(ASTVariableDeclaratorId node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Handle the first child (which is required)
        printData.appendText(node.getName());
        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            printData.appendText("[");
            printData.appendText("]");
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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Handle the first child (which is required)
        printData.appendText("{");
        int last = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < last; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
            }
            Node child = node.jjtGetChild(ndx);
            child.jjtAccept(this, data);
        }
        if (node.isFinalComma()) {
            printData.appendText(",");
        }
        printData.appendText("}");

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
    public Object visit(ASTMethodDeclaration node, Object data) {
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        //printData.beginMethod(); //no need for lines between methods
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));

        //  Handle the first two/three children (which are required)
        int childNo = node.skipAnnotations();
        Node next = node.jjtGetChild(childNo);
        next.jjtAccept(this, data);
        printData.space();
        if (next instanceof ASTTypeParameters) {
            next = node.jjtGetChild(++childNo);
            next.jjtAccept(this, data);
            printData.space();
        }
        next = node.jjtGetChild(++childNo);
        next.jjtAccept(this, data);

        //  Traverse the rest of the children
        int lastIndex = node.jjtGetNumChildren();
        boolean foundBlock = false;
        for (int ndx = childNo+1; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            if (next instanceof ASTNameList) {
                printData.appendKeyword(" throws ");
                next.jjtAccept(this, data);
            } else if (next instanceof ASTBlock) {
                foundBlock = true;
                printData.appendText("{ ");
                next.jjtAccept(this, data);
            }
        }

        //  Finish if it is abstract
        if (foundBlock) {
            printData.appendText("}");
        } else {
            printData.appendText(";");
            printData.newline();
        }

        //  Note the end of the method
        printData.endMethod();

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Handle the first child (which is required)
        printData.appendText(node.getName());
        node.childrenAccept(this, data);

        int last = node.getArrayCount();
        for (int ndx = 0; ndx < last; ndx++) {
            printData.appendText("[");
            printData.appendText("]");
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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.beginExpression(node.jjtGetNumChildren() > 0);

        //  Traverse the children
        Node next;
        int lastIndex = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < lastIndex; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
            }
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }

        //  Finish it
        printData.endExpression(node.jjtGetNumChildren() > 0);

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
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        if (node.isUsingFinal()) {
            printData.appendKeyword("final ");
        }

        //  Traverse the children
        int childNo = node.skipAnnotations();
        Node next = node.jjtGetChild(childNo);
        next.jjtAccept(this, data);
        printData.space();
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
        if (!(node.isPublic() || node.isProtected())) {
            return data;
        }

        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print any tokens
        printData.beginMethod();
        printData.indent();
        printData.appendKeyword(node.getModifiersString(PrintData.STANDARD_ORDER));
        printData.appendText(node.getName());

        //  Handle the first child (which is required)
        int childNo = node.skipAnnotationsAndTypeParameters();
        Node next = node.jjtGetChild(childNo);
        next.jjtAccept(this, data);

        //  Get the last index
        int lastIndex = node.jjtGetNumChildren();
        int startAt = childNo+1;

        //  Handle the name list if it is present
        if (lastIndex > childNo+1) {
            next = node.jjtGetChild(startAt);
            if (next instanceof ASTNameList) {
                printData.space();
                printData.appendKeyword("throws");
                printData.space();
                next.jjtAccept(this, data);
                startAt++;
            }
        }

        //  Print the starting block
        printData.beginBlock(false, false);

        //  Traverse the rest of the children
        boolean foundBlock = false;
        for (int ndx = startAt; ndx < lastIndex; ndx++) {
            next = node.jjtGetChild(ndx);
            next.jjtAccept(this, data);
        }

        //  Print the end block
        printData.endBlock(false,false);
        printData.endMethod();

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
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInitializer node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTType node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

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
    public Object visit(ASTPrimitiveType node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print the name of the node
        printData.appendKeyword(node.getName());

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
    public Object visit(ASTResultType node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Traverse the children
        if (node.hasAnyChildren()) {
            node.childrenAccept(this, data);
        } else {
            printData.appendKeyword("void");
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
    public Object visit(ASTName node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Print the name of the node
        printData.appendText(node.getName());

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
    public Object visit(ASTNameList node, Object data) {
        //  Get the data
        PrintData printData = (PrintData) data;

        //  Traverse the children
        int countChildren = node.jjtGetNumChildren();
        for (int ndx = 0; ndx < countChildren; ndx++) {
            if (ndx > 0) {
                printData.appendText(", ");
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
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTConditionalAndExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInclusiveOrExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTExclusiveOrExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTAndExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTEqualityExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTInstanceOfExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTRelationalExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTShiftExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTAdditiveExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTMultiplicativeExpression node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTUnaryExpression node, Object data) {
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
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTNullLiteral node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArguments node, Object data) {
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArgumentList node, Object data) {
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
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTArrayDimsAndInits node, Object data) {
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
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTBlock node, Object data) {
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
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTSwitchStatement node, Object data) {
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
        return data;
    }
}
