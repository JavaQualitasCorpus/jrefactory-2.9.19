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
import java.io.*;
import java.util.Enumeration;

import net.sourceforge.jrefactory.ast.ASTTypeParameterList;
import net.sourceforge.jrefactory.ast.ASTTypeParameter;
import net.sourceforge.jrefactory.ast.ASTTypeArguments;
import net.sourceforge.jrefactory.ast.ASTReferenceTypeList;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTGenericNameList;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumElement;
import net.sourceforge.jrefactory.ast.ASTIdentifier;


/**
 *  This object visits an abstract syntax tree with the purpose of gathering
 *  summary information.
 *  @FIXME: add Enum, Generics, etc.
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    May 30, 1999
 */
public class SummaryLoadVisitor extends LineCountVisitor {
    private int anonCount = 1;


    /**
     *  Visits an enum declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTEnumDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int oldCode = state.getCode();

        state.setCode(SummaryLoaderState.LOAD_CLASSBODY);

        super.visit(node, data);

        state.setCode(oldCode);

        return data;
    }

    /**
     *  Visits a package declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTPackageDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        //  Get the name
        ASTName name = (ASTName) node.jjtGetFirstChild();

        //  Lookup the summary
        PackageSummary packageSummary = PackageSummary.getPackageSummary(name.getName());

        //  Create the file summary
        if (state.getCode() == SummaryLoaderState.INITIALIZE) {
            state.startSummary(new FileSummary(packageSummary, state.getFile()));
            state.setCode(SummaryLoaderState.LOAD_FILE);
        }

        return super.visit(node, data);
    }


    /**
     *  Visits an import statement
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTImportDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        //  Get the current file summary - add the import
        FileSummary current = (FileSummary) state.getCurrentSummary();
        ImportSummary importSummary = new ImportSummary(current, node);
        current.add(importSummary);

        int start = getLineCount() + 1;

        //  Done
        Object obj = super.visit(node, data);

        int end = getLineCount();
        importSummary.setStartLine(start);
        importSummary.setEndLine(end);

        return obj;
    }


    /**
     *  Visits a type declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTTypeDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        //  Get the current file summary
        FileSummary current = (FileSummary) state.getCurrentSummary();

        //  Create Type Summary
        TypeSummary next = new TypeSummary(current, node);
        current.add(next);

        //  Set the next type summary as the current summary
        state.startSummary(next);
        int oldCode = state.getCode();
        state.setCode(SummaryLoaderState.LOAD_TYPE);

        //  Traverse Children
        super.visit(node, data);

        //  Back to loading the file
        state.finishSummary();
        state.setCode(oldCode);

        int end = getLineCount();
        next.setStartLine(start);
        next.setEndLine(end);

        //  Done
        return data;
    }


    /**
     *  Visits a class declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTClassDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        //  Get the current type summary
        TypeSummary current = (TypeSummary) state.getCurrentSummary();

        //  Save the modifiers
        current.copyModifiers(node);

        //  Traverse the children
        return super.visit(node, data);
    }


    /**
     *  Visits a class declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTUnmodifiedClassDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int code = state.getCode();

        //  Get the current type summary
        Summary currentSummary = state.getCurrentSummary();
        TypeSummary current;

        if (currentSummary instanceof TypeSummary) {
            current = (TypeSummary) currentSummary;
        } else {
            //  Create Type Summary
            current = new TypeSummary(currentSummary, node);
            ((MethodSummary) currentSummary).addDependency(current);
            state.startSummary(current);
        }

        //  Remember the name
        current.setName(node.getName().intern());

        //  Iterate through the children
        //  Add the class body
        state.setCode(SummaryLoaderState.LOAD_TYPE);
        super.visit(node, data);

        state.setCode(code);

        //  This is a class
        current.setInterface(false);

        if (currentSummary instanceof TypeSummary) {
        } else {
            state.finishSummary();
        }

        return data;
    }


    /**
     *  Visit the items in the class body
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTClassBody node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int oldCode = state.getCode();

        state.setCode(SummaryLoaderState.LOAD_CLASSBODY);

        super.visit(node, data);

        state.setCode(oldCode);

        return data;
    }


    /**
     *  Visit a class that is nested in another class
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTNestedClassDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int code = state.getCode();

        //  Get the current type summary
        Summary current = state.getCurrentSummary();

        TypeSummary nested = new TypeSummary(current, node);

        //  Add it in
        if (current instanceof TypeSummary) {
            ((TypeSummary) current).add(nested);
        } else {
            ((MethodSummary) current).addDependency(nested);
        }

        //  Continue deeper
        state.startSummary(nested);
        state.setCode(SummaryLoaderState.LOAD_TYPE);

        //  Save the modifiers
        nested.copyModifiers(node);

        //  Traverse the children
        super.visit(node, data);

        int end = getLineCount();
        nested.setStartLine(start);
        nested.setEndLine(end);

        //  Finish the summary
        state.finishSummary();
        state.setCode(code);

        //  Return something
        return data;
    }


    /**
     *  Visit an interface declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTInterfaceDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        //  Get the current type summary
        TypeSummary current = (TypeSummary) state.getCurrentSummary();

        //  Save the modifiers
        current.copyModifiers(node);

        //  Traverse the children
        return super.visit(node, data);
    }


    /**
     *  Visit an interface that is nested in a class
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTNestedInterfaceDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int code = state.getCode();

        //  Get the current type summary
        Summary current = state.getCurrentSummary();

        //  Create the new nested type summary
        TypeSummary nested = new TypeSummary(current, node);

        //  Add it in
        if (current instanceof TypeSummary) {
            ((TypeSummary) current).add(nested);
        } else {
            ((MethodSummary) current).addDependency(nested);
        }

        //  Continue deeper
        state.startSummary(nested);
        state.setCode(SummaryLoaderState.LOAD_TYPE);

        //  Save the modifiers
        nested.copyModifiers(node);

        //  Traverse the children
        super.visit(node, data);

        int end = getLineCount();
        nested.setStartLine(start);
        nested.setEndLine(end);

        //  Finish the summary
        state.finishSummary();
        state.setCode(code);

        //  Return something
        return data;
    }


    /**
     *  Visit an interface
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTUnmodifiedInterfaceDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int code = state.getCode();

        //  Get the current type summary
        Summary currentSummary = state.getCurrentSummary();
        TypeSummary current;

        if (currentSummary instanceof TypeSummary) {
            current = (TypeSummary) currentSummary;
        } else {
            //  Create Type Summary
            current = new TypeSummary(currentSummary, node);
            ((MethodSummary) currentSummary).addDependency(current);
            state.startSummary(current);
        }

        //  Remember the name
        current.setName(node.getName().intern());

        //  Iterate through the children of the interface
        state.setCode(SummaryLoaderState.LOAD_TYPE);

        super.visit(node, data);

        state.setCode(code);

        //  This is an interface
        current.setInterface(true);

        if (currentSummary instanceof TypeSummary) {
        } else {
            state.finishSummary();
        }

        //  Done
        return data;
    }


    /**
     *  Visit the body of an interface
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTInterfaceBody node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int oldCode = state.getCode();

        state.setCode(SummaryLoaderState.LOAD_CLASSBODY);

        super.visit(node, data);

        state.setCode(oldCode);

        return data;
    }


    /**
     *  Visit a field declaration
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTFieldDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;
        int code = state.getCode();
        state.setCode(SummaryLoaderState.IGNORE);
        countFieldStart(node, data);
        
        state.setCode(code);

        //  Get the current type summary
        TypeSummary current = (TypeSummary) state.getCurrentSummary();

        //  Local Variables
        FieldSummary result = null;
        int last = node.jjtGetNumChildren();
        ASTType type = (ASTType)node.jjtGetFirstChild();

        //  Create a summary for each field
        for (int ndx = 1; ndx < last; ndx++) {
            Node next = node.jjtGetChild(ndx);
            result = new FieldSummary(current, type,
                    (ASTVariableDeclaratorId) next.jjtGetFirstChild());

            //  Count anything on the declarator
            state.setCode(SummaryLoaderState.IGNORE);
            next.jjtGetFirstChild().jjtAccept(this, data);
            state.setCode(code);

            //  Continue setting up the field summary
            result.copyModifiers(node);
            result.setStartLine(start);

            //  Load the initializer
            if (next.jjtGetNumChildren() > 1) {
                loadInitializer(current, state,
                        (SimpleNode) next.jjtGetChild(1),
                        node.isStatic());
            }

            //  Finish setting variables for the field summary
            countLines(node.getSpecial("comma." + (ndx - 1)));
            result.setEndLine(getLineCount());
            current.add(result);
        }
        countLines(node.getSpecial("semicolon"));
        if (result != null) {
            result.setEndLine(getLineCount());
        }

        return data;
    }


    /**
     *  Visits a method
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTMethodDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        countMethodHeader(node, data);

        int declStart = getLineCount();

        int code = state.getCode();
        

        if (code == SummaryLoaderState.LOAD_CLASSBODY) {
            int child = (node.jjtGetChild(1) instanceof ASTResultType) ? 2 : 1;
            ASTMethodDeclarator decl = (ASTMethodDeclarator) node.jjtGetChild(child);

            //  Get the current type summary
            TypeSummary current = (TypeSummary) state.getCurrentSummary();
            MethodSummary methodSummary = new MethodSummary(current);
            state.startSummary(methodSummary);
            current.add(methodSummary);

            //  Load the method summary
            //  Remember the modifiers
            methodSummary.copyModifiers(node);

            //  Load the method names
            methodSummary.setName(decl.getName());

            //  Load the return type
            loadMethodReturn(node, methodSummary, decl.getArrayCount());

            //  Load the parameters
            loadMethodParams(decl, state);

            //  Load the exceptions
            loadMethodExceptions(node, state, 2);

            //  Initialize the dependency list
            loadMethodBody(node, state);

            //  Done
            state.setCode(SummaryLoaderState.LOAD_CLASSBODY);
            state.finishSummary();

            int end = getLineCount();
            methodSummary.setStartLine(start);
            methodSummary.setDeclarationLine(declStart);
            methodSummary.setEndLine(end);

            return data;
        } else {
            int child = (node.jjtGetChild(1) instanceof ASTResultType) ? 2 : 1;
            ASTMethodDeclarator decl = (ASTMethodDeclarator) node.jjtGetChild(child);
            System.out.println("Encountered a method in state:  " + code);
            System.out.println("   name=" + decl.getName());
            return data;
        }
    }
/*
    public Object visit(ASTInstanceOfExpression node, Object data) {
        SummaryLoaderState state = (SummaryLoaderState) data;

        int childCount = node.jjtGetNumChildren();
        if (childCount==2) {            
            MethodSummary methodSummary = (MethodSummary) state.getCurrentSummary();
            ASTReferenceType reference = (ASTReferenceType)node.jjtGetChild(1);
            methodSummary.addDependency(new TypeDeclSummary(methodSummary, (ASTName)reference.jjtGetFirstChild()));
        }

        return super.visit(node, data);
    }
*/
    /**
     *  Visit a formal parameter
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTFormalParameter node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int code = state.getCode();

        if (code == SummaryLoaderState.LOAD_PARAMETERS) {
            //  Local Variables
            MethodSummary methodSummary = (MethodSummary) state.getCurrentSummary();

            //  For each variable create a summary
            methodSummary.add(new ParameterSummary(methodSummary, (ASTType) node.jjtGetFirstChild(),
                    (ASTVariableDeclaratorId) node.jjtGetChild(1)));

            //  Continue with the state
            return state;
        } else {
            //  Get the parent
            MethodSummary parent = (MethodSummary) state.getCurrentSummary();

            //  Add the dependency
            parent.addDependency(new ParameterSummary(parent, (ASTType) node.jjtGetFirstChild(),
                    (ASTVariableDeclaratorId) node.jjtGetChild(1)));

            //  Return something
            return data;
        }
    }


    /**
     *  Visit a constructor
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTConstructorDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int code = state.getCode();

        if (code == SummaryLoaderState.LOAD_CLASSBODY) {
            int start = getLineCount() + 1;

            countConstructorHeader(node);

            int declStart = getLineCount();

            //  Get the current type summary
            TypeSummary current = (TypeSummary) state.getCurrentSummary();
            MethodSummary methodSummary = new MethodSummary(current);
            current.add(methodSummary);
            state.startSummary(methodSummary);

            //  Load the constructor
            //  Remember the modifiers
            methodSummary.copyModifiers(node);

            //  Load the method names
            methodSummary.setName(node.getName());

            //  Load the parameters
            loadMethodParams(node, state);

            //  Load the exceptions
            loadMethodExceptions(node, state, 1);

            //  Initialize the dependency list
            methodSummary.beginBlock();
            int childNo = node.skipAnnotationsAndTypeParameters();
            state.setCode(SummaryLoaderState.LOAD_METHODBODY);
            int last = node.jjtGetNumChildren();
            for (int ndx = childNo+1; ndx < last; ndx++) {
                SimpleNode body = (SimpleNode) node.jjtGetChild(ndx);
                if (body instanceof ASTExplicitConstructorInvocation) {
                    body.jjtAccept(this, data);
                } else if (body instanceof ASTBlockStatement) {
                    body.jjtAccept(this, data);
                }
            }
            methodSummary.endBlock();

            //  Done
            state.setCode(SummaryLoaderState.LOAD_CLASSBODY);
            state.finishSummary();

            countLines(node.getSpecial("end"));
            int end = getLineCount();
            methodSummary.setStartLine(start);
            methodSummary.setDeclarationLine(declStart);
            methodSummary.setEndLine(end);

            return data;
        } else {
            System.out.println("Encountered a constructor in state:  " + code);
            System.out.println("   name=" + node.getName());
            return data;
        }
    }


    /**
     *  Visit an initializer
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTInitializer node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        int code = state.getCode();

        if (code == SummaryLoaderState.LOAD_CLASSBODY) {
            //  Get the current type summary
            TypeSummary current = (TypeSummary) state.getCurrentSummary();

            int last = node.jjtGetNumChildren();
            SimpleNode body = (SimpleNode) node.jjtGetChild(last - 1);
            if (body instanceof ASTBlock) {
                loadInitializer(current, state, body, node.isUsingStatic());
            }

            return data;
        } else {
            System.out.println("Encountered a initializer in state:  " + code);
            return data;
        }
    }


    /**
     *  Visit a type
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTType node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        if (state.getCurrentSummary() instanceof TypeSummary) {
           // FIXME??? this is an annotation???
           return data;
        }
        MethodSummary parent = (MethodSummary) state.getCurrentSummary();

        //  Add the dependency
        if (node.jjtGetFirstChild() instanceof ASTReferenceType) {
            // reference type dependencies are handled by their own visitor.
        } else if (node.jjtGetFirstChild() instanceof ASTPrimitiveType) {
            parent.addDependency(TypeDeclSummary.getTypeDeclSummary(parent, node));
        }

        //  Return the data
        return super.visit(node, data);
    }


    /**
     *  Visit a type
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTReferenceType node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        if (state.getCurrentSummary() instanceof MethodSummary) {
            MethodSummary parent = (MethodSummary) state.getCurrentSummary();
            parent.addDependency(TypeDeclSummary.getTypeDeclSummary(parent, node)); //  Add the dependency
        } else if (state.getCurrentSummary() instanceof TypeSummary) {
            TypeSummary parent = (TypeSummary) state.getCurrentSummary();
        } else {
        }

        
        return super.visit(node, data);   //  Return the data
    }


    /**
     *  Visit a return type
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTResultType node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        MethodSummary parent = (MethodSummary) state.getCurrentSummary();

        //  Add the dependency
        parent.addDependency(TypeDeclSummary.getTypeDeclSummary(parent, node));

        //  Return the data
        return data;
    }


    /**
     *  Visit a name
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTClassOrInterfaceType node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int code = state.getCode();

        //  Get the current type summary
        if (code == SummaryLoaderState.LOAD_TYPE) {
            TypeSummary current = (TypeSummary) state.getCurrentSummary();
            current.setParentClass(new TypeDeclSummary(current, node));
        }

        return super.visit(node, data);
    }


    /**
     *  Visit a name
     *  // FIXME? is this required?
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTName node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int code = state.getCode();

        //  Get the current type summary
        if (code == SummaryLoaderState.LOAD_TYPE) {
            TypeSummary current = (TypeSummary) state.getCurrentSummary();
            current.setParentClass(new TypeDeclSummary(current, node));
        }

        return super.visit(node, data);
    }


    /**
     *  Visit a list of names
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTNameList node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int code = state.getCode();

        //  Get the current type summary
        if (code == SummaryLoaderState.LOAD_TYPE) {
            TypeSummary current = (TypeSummary) state.getCurrentSummary();

            //  Local Variables
            int last = node.jjtGetNumChildren();

            //  For each interface it implements create a summary
            state.setCode(SummaryLoaderState.IGNORE);
            for (int ndx = 0; ndx < last; ndx++) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
                ASTName next = (ASTName) node.jjtGetChild(ndx);
                current.add(new TypeDeclSummary(current, next));
                next.jjtAccept(this, data);
            }
            state.setCode(code);

            return data;
        } else if (code == SummaryLoaderState.LOAD_EXCEPTIONS) {
            //  Local Variables
            int last = node.jjtGetNumChildren();
            MethodSummary methodSummary = (MethodSummary) state.getCurrentSummary();

            //  For each variable create a summary
            state.setCode(SummaryLoaderState.IGNORE);
            for (int ndx = 0; ndx < last; ndx++) {
                countLines(node.getSpecial("comma." + (ndx - 1)));
                ASTName next = (ASTName) node.jjtGetChild(ndx);
                methodSummary.add(new TypeDeclSummary(methodSummary, next));
                next.jjtAccept(this, data);
            }
            state.setCode(code);

            //  Return something
            return data;
        } else {
            return super.visit(node, data);
        }
    }


    /**
     *  Visit a list of names
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTGenericNameList node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int code = state.getCode();

        //  Get the current type summary
        if (code == SummaryLoaderState.LOAD_TYPE) {
            TypeSummary current = (TypeSummary) state.getCurrentSummary();

            //  Local Variables
            int last = node.jjtGetNumChildren();

            //  For each interface it implements create a summary
            state.setCode(SummaryLoaderState.IGNORE);
            for (int ndx = 0; ndx < last; ndx++) {
                Node child = node.jjtGetChild(ndx);
                if (child instanceof ASTClassOrInterfaceType) {
                    countLines(node.getSpecial("comma." + (ndx - 1)));
                    current.add(new TypeDeclSummary(current, (ASTClassOrInterfaceType) child));
                }
                child.jjtAccept(this, data);
            }
            state.setCode(code);

            return data;
        } else if (code == SummaryLoaderState.LOAD_EXCEPTIONS) {
            //  Local Variables
            int last = node.jjtGetNumChildren();
            MethodSummary methodSummary = (MethodSummary) state.getCurrentSummary();

            //  For each variable create a summary
            state.setCode(SummaryLoaderState.IGNORE);
            for (int ndx = 0; ndx < last; ndx++) {
                Node child = node.jjtGetChild(ndx);
                if (child instanceof ASTClassOrInterfaceType) {
                    countLines(node.getSpecial("comma." + (ndx - 1)));
                    methodSummary.add(new TypeDeclSummary(methodSummary, (ASTClassOrInterfaceType)child));
                }
                child.jjtAccept(this, data);
            }
            state.setCode(code);

            //  Return something
            return data;
        } else {
            return super.visit(node, data);
        }
    }


    /**
     *  Visit an expression
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTPrimaryExpression node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        //  Local Variables
        ASTName name = null;

        //  Check out the prefix
        ASTPrimaryPrefix prefix = (ASTPrimaryPrefix) node.jjtGetFirstChild();
        if (!prefix.hasAnyChildren()) {
            //  Count the lines
            countLines(node.getSpecial("this"));
            countLines(node.getSpecial("id"));

            //  It is entirely controlled by the name of the node
            String prefixName = prefix.getName();

            //  Create the name
            name = new ASTName();

            //  Check out the name
            if (prefixName.equals("this")) {
                name.addNamePart(prefixName);
            } else {
                // FIXME name could be of the form "super" ("." "super")*
                name.addNamePart("super");
                name.addNamePart(prefixName.substring(7, prefixName.length()));
            }
        } else if (prefix.jjtGetFirstChild() instanceof ASTName) {
            //  Count the items in the name
            int code = state.getCode();
            state.setCode(SummaryLoaderState.IGNORE);
            super.visit(prefix, data);
            state.setCode(code);

            //  Remember the name
            name = (ASTName) prefix.jjtGetFirstChild();
        } else {
            //  Our work is done here
            return super.visit(node, data);
        }

        if (state.getCurrentSummary() instanceof TypeSummary) {
           // FIXME??? this is an annotation???
           return data;
        }
        //  Get the parent
        MethodSummary parent = (MethodSummary) state.getCurrentSummary();

        //  Check out the suffix
        boolean isMessageSend = false;
        int suffixCount = node.jjtGetNumChildren();
        boolean sentLast = false;
        if (suffixCount > 1) {
            for (int ndx = 1; ndx < suffixCount; ndx++) {
                ASTPrimarySuffix suffix = (ASTPrimarySuffix) node.jjtGetChild(ndx);
                if (!suffix.hasAnyChildren()) {
                    //  Count the lines
                    countLines(node.getSpecial("dot"));
                    countLines(node.getSpecial("id"));

                    name = new ASTName();
                    name.addNamePart(suffix.getName());
                    sentLast = false;
                } else if (suffix.jjtGetFirstChild() instanceof ASTArguments) {
                    addAccess(parent, name, true);
                    sentLast = true;
                    super.visit((SimpleNode) suffix.jjtGetFirstChild(), data);
                } else if (suffix.jjtGetFirstChild() instanceof ASTReferenceTypeList) {
                    if (suffix.getName()!=null && !suffix.getName().equals("")) {
                        if (name==null) {
                            name = new ASTName();
                        }
                        name.addNamePart(suffix.getName());
                    }
                    super.visit((SimpleNode) suffix.jjtGetFirstChild(), data);
                } else if (!sentLast) {
                    addAccess(parent, name, false);
                    sentLast = true;

                    //  Count the lines
                    countLines(node.getSpecial("["));
                    countLines(node.getSpecial("]"));
                    countLines(node.getSpecial("dot"));
                    countLines(node.getSpecial("id"));

                    super.visit((SimpleNode) suffix.jjtGetFirstChild(), data);
                }
            }
        }

        //  Get the parent
        if (!sentLast) {
            addAccess(parent, name, false);
        }

        //  Return some value
        return data;
    }


    /**
     *  Visit an allocation
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTAllocationExpression node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;

        MethodSummary parent = (MethodSummary) state.getCurrentSummary();

        //  Add the dependency
        Node child = node.jjtGetFirstChild();
        TypeDeclSummary parentClass = null;
        if (child instanceof ASTClassOrInterfaceType) {
            parentClass = new TypeDeclSummary(parent, (ASTClassOrInterfaceType) child);
        } else if (child instanceof ASTPrimitiveType) {
            parentClass = new TypeDeclSummary(parent, (ASTPrimitiveType) child);
        }
        parent.addDependency(parentClass);

        //  Count the lines in the type and before
        countLines(node.getSpecial("id"));
        int code = state.getCode();
        state.setCode(SummaryLoaderState.IGNORE);
        child.jjtAccept(this, data);
        state.setCode(code);

        int last = node.jjtGetNumChildren();
        for (int ndx = 1; ndx < last; ndx++) {
            Node next = node.jjtGetChild(ndx);
            if (next instanceof ASTClassBody) {
                //  Create Type Summary
                TypeSummary typeSummary = new TypeSummary(parent, null);
                typeSummary.setName("Anonymous" + anonCount);
                anonCount++;
                typeSummary.setParentClass(parentClass);

                parent.addDependency(typeSummary);

                //  Set the next type summary as the current summary
                state.startSummary(typeSummary);
                int oldCode = state.getCode();
                state.setCode(SummaryLoaderState.LOAD_TYPE);

                //  Traverse Children
                next.jjtAccept(this, data);

                //  Back to loading the file
                state.finishSummary();
                state.setCode(oldCode);
            } else {
                ((SimpleNode) next).jjtAccept(this, data);
            }
        }

        int end = getLineCount();
        parentClass.setStartLine(start);
        parentClass.setEndLine(end);


        //  Return the data
        return data;
    }


    /**
     *  Visit a statement
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTStatement node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        Node child = node.jjtGetFirstChild();
        if (child instanceof ASTBlock) {
            //  Don't count blocks as statements
        } else {
            MethodSummary parent = (MethodSummary) state.getCurrentSummary();
            parent.incrStatementCount();
        }

        return super.visit(node, data);
    }


    /**
     *  Explicit constructor invocation gets one statement count
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     *@return       Description of the Returned Value
     */
    public Object visit(ASTExplicitConstructorInvocation node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        MethodSummary parent = (MethodSummary) state.getCurrentSummary();
        parent.incrStatementCount();

        return super.visit(node, data);
    }


    /**
     *  Visit the local variables
     *
     *@param  node  the node we are visiting
     *@param  data  the state we are in
     *@return       nothing of interest
     */
    public Object visit(ASTLocalVariableDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        int start = getLineCount() + 1;
        countLocalVariable(node, data);
        int end = getLineCount();

        MethodSummary parent = (MethodSummary) state.getCurrentSummary();

        parent.incrStatementCount();

        //  Get the field summaries
        LocalVariableSummary[] result = LocalVariableSummary.createNew(parent, node);

        //  Add the dependencies into the method
        int last = result.length;
        for (int ndx = 0; ndx < last; ndx++) {
            parent.addDependency(result[ndx]);
            result[ndx].setStartLine(start);
            result[ndx].setEndLine(end);
        }

        //  Return some result
        return data;
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     */
    protected void forInit(ASTLocalVariableDeclaration node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            super.visit(node, data);
            return;
        }

        int start = getLineCount() + 1;
        countLocalVariable(node, data);
        int end = getLineCount();

        MethodSummary parent = (MethodSummary) state.getCurrentSummary();

        //  Get the field summaries
        LocalVariableSummary[] result = LocalVariableSummary.createNew(parent, node);

        //  Add the dependencies into the method
        int last = result.length;
        for (int ndx = 0; ndx < last; ndx++) {
            parent.addDependency(result[ndx]);
            result[ndx].setStartLine(start);
            result[ndx].setEndLine(end);
        }
    }


    /**
     *  Visits a block in the parse tree. This code counts the block depth
     *  associated with a method. Deeply nested blocks in a method is a sign of
     *  poor design.
     *
     *@param  node  the block node
     *@param  data  the information that is used to traverse the tree
     *@return       data is returned
     */
    public Object visit(ASTBlock node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        //  Get the current file summary
        Summary current = state.getCurrentSummary();
        if (current instanceof MethodSummary) {
            ((MethodSummary) current).beginBlock();
        }

        Object result = super.visit(node, data);

        if (current instanceof MethodSummary) {
            ((MethodSummary) current).endBlock();
        }

        return result;
    }


    /**
     *  A switch statement counts as a block, even though it does not use the
     *  block parse token.
     *
     *@param  node  the switch node in the parse tree
     *@param  data  the data used to visit this parse tree
     *@return       the data
     */
    public Object visit(ASTSwitchStatement node, Object data) {
        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        if (state.getCode() == SummaryLoaderState.IGNORE) {
            return super.visit(node, data);
        }

        //  Get the current file summary
        Summary current = state.getCurrentSummary();
        if (current instanceof MethodSummary) {
            ((MethodSummary) current).beginBlock();
        }

        Object result = super.visit(node, data);

        if (current instanceof MethodSummary) {
            ((MethodSummary) current).endBlock();
        }

        return result;
    }


    /**
     *  Adds an access to the method
     *
     *@param  parent         the parent
     *@param  name           the name
     *@param  isMessageSend  is this a message send
     */
    protected void addAccess(MethodSummary parent, ASTName name, boolean isMessageSend) {
        //  Record the access
        if (isMessageSend) {
            //  Add the dependency
            parent.addDependency(new MessageSendSummary(parent, (ASTName) name));
        } else {
            //  Add the dependency
            parent.addDependency(new FieldAccessSummary(parent, (ASTName) name));
        }
    }


    /**
     *  Description of the Method
     *
     *@param  current   Description of Parameter
     *@param  state     Description of Parameter
     *@param  body      Description of Parameter
     *@param  isStatic  Description of Parameter
     */
    void loadInitializer(TypeSummary current, SummaryLoaderState state, SimpleNode body, boolean isStatic) {
        MethodSummary methodSummary = current.getInitializer(isStatic);
        state.startSummary(methodSummary);

        //  Load the method summary's dependency list
        int oldCode = state.getCode();
        state.setCode(SummaryLoaderState.LOAD_METHODBODY);
        body.jjtAccept(this, state);

        //  Done
        state.setCode(oldCode);
        state.finishSummary();
    }


    /**
     *  Counts the lines associated with the field declaration and the
     *  associated type
     *
     *@param  node  the field declaration
     *@param  data  the data for the visitor
     */
    private void countFieldStart(ASTFieldDeclaration node, Object data) {
        //  Print any tokens
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("transient"));
        countLines(node.getSpecial("volatile"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));

        //  Handle the first two children (which are required)
        Node next = node.jjtGetFirstChild();
        next.jjtAccept(this, data);
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     *@param  data  Description of Parameter
     */
    private void countMethodHeader(ASTMethodDeclaration node, Object data) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("static"));
        countLines(node.getSpecial("abstract"));
        countLines(node.getSpecial("final"));
        countLines(node.getSpecial("native"));
        countLines(node.getSpecial("synchronized"));
        int childNo = 0;
        Node child = node.jjtGetFirstChild();
        if (child instanceof ASTTypeParameters) {
            countLines(getInitialToken((ASTTypeParameters)child));
            child = node.jjtGetChild(++childNo);
        }
        countLines(getInitialToken((ASTResultType)child));
        countLines(node.getSpecial("throws"));
        countLines(node.getSpecial("semicolon"));
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of Parameter
     */
    private void countConstructorHeader(ASTConstructorDeclaration node) {
        countLines(node.getSpecial("public"));
        countLines(node.getSpecial("protected"));
        countLines(node.getSpecial("private"));
        countLines(node.getSpecial("id"));
        countLines(node.getSpecial("throws"));
        countLines(node.getSpecial("begin"));
        countLines(node.getSpecial("@"));
    }


    /**
     *  Description of the Method
     *
     *@param  decl   Description of Parameter
     *@param  state  Description of Parameter
     */
    private void loadMethodParams(SimpleNode decl, SummaryLoaderState state) {
        Node child = decl.jjtGetFirstChild();
        if (child instanceof ASTTypeParameters) {
            child = decl.jjtGetChild(1);
        }
        if (!(child instanceof ASTFormalParameters)) {
            System.out.println("ERROR!  Not formal parameters");
            return;
        }
        state.setCode(SummaryLoaderState.LOAD_PARAMETERS);
        child.jjtAccept(this, state);
    }


    /**
     *  Description of the Method
     *
     *@param  node           Description of Parameter
     *@param  methodSummary  Description of Parameter
     *@param  count          Description of Parameter
     */
    private void loadMethodReturn(ASTMethodDeclaration node, MethodSummary methodSummary, int count) {
        int childNo = node.skipAnnotationsAndTypeParameters();
        TypeDeclSummary returnType = TypeDeclSummary.getTypeDeclSummary(methodSummary, (ASTResultType) node.jjtGetChild(childNo));
        returnType.setArrayCount(returnType.getArrayCount() + count);
        methodSummary.setReturnType(returnType);
    }


    /**
     *  Description of the Method
     *
     *@param  node   Description of Parameter
     *@param  state  Description of Parameter
     *@param  index  Description of Parameter
     */
    private void loadMethodExceptions(SimpleNode node, SummaryLoaderState state, int index) {
        if (node.jjtGetNumChildren() > index) {
            Node child = node.jjtGetChild(index);
            if (child instanceof ASTNameList) {
                state.setCode(SummaryLoaderState.LOAD_EXCEPTIONS);
                child.jjtAccept(this, state);
            }
        }
    }


    /**
     *  Description of the Method
     *
     *@param  node   Description of Parameter
     *@param  state  Description of Parameter
     */
    private void loadMethodBody(ASTMethodDeclaration node, SummaryLoaderState state) {
        state.setCode(SummaryLoaderState.LOAD_METHODBODY);
        int last = node.jjtGetNumChildren();
        SimpleNode body = (SimpleNode) node.jjtGetChild(last - 1);
        if (body instanceof ASTBlock) {
            body.jjtAccept(this, state);
        }
    }


    /**
     *  Description of the Method
     *
     *@param  node  Description of the Parameter
     *@param  data  Description of the Parameter
     */
    private void countLocalVariable(ASTLocalVariableDeclaration node, Object data) {
        //  Traverse the children
        int last = node.jjtGetNumChildren();

        //  Print the final token
        if (node.isUsingFinal()) {
            countLines(node.getSpecial("final"));
        }

        //  Convert the data into the correct form
        SummaryLoaderState state = (SummaryLoaderState) data;

        //  The first child is special - it is the type
        int code = state.getCode();
        state.setCode(SummaryLoaderState.IGNORE);
        node.jjtGetFirstChild().jjtAccept(this, data);
        state.setCode(code);

        //  Traverse the rest of the children
        for (int ndx = 1; ndx < last; ndx++) {
            countLines(node.getSpecial("comma." + (ndx - 1)));
            //  Visit the child
            node.jjtGetChild(ndx).jjtAccept(this, data);
        }
    }
}
