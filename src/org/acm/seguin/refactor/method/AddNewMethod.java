/*
 * Author:  Chris Seguin
 *
 * This software has been developed under the copyleft
 * rules of the GNU General Public License.  Please
 * consult the GNU General Public License for more
 * details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import java.util.Iterator;
import java.util.StringTokenizer;
import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTInterfaceBody;
import net.sourceforge.jrefactory.ast.ASTInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTNameList;
import net.sourceforge.jrefactory.ast.ASTPrimitiveType;
import net.sourceforge.jrefactory.ast.ASTResultType;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ModifierHolder;
import org.acm.seguin.refactor.TransformAST;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.TypeDeclSummary;

import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.parser.JavaParserTreeConstants;


/**
 *  A series of transformations taht adds a new method to a class.
 *
 *@author    Chris Seguin
 */
abstract class AddNewMethod extends TransformAST {
	/**
	 *  Description of the Field
	 */
	protected MethodSummary methodSummary;


	/**
	 *  Constructor for the AddNewMethod object
	 *
	 *@param  init  the method summary to add
	 */
	public AddNewMethod(MethodSummary init) {
		methodSummary = init;
	}


	/**
	 *  Update the syntax tree
	 *
	 *@param  root  the root of the syntax tree
	 */
	public void update(SimpleNode root) {
		//  Find the type declaration
		int last = root.jjtGetNumChildren();
		for (int ndx = 0; ndx < last; ndx++) {
			if (root.jjtGetChild(ndx) instanceof ASTTypeDeclaration) {
				drillIntoType((SimpleNode) root.jjtGetChild(ndx));
				return;
			}
		}
	}



	/**
	 *  Determines if the method is abstract
	 *
	 *@return    true if the method is abstract
	 */
	protected boolean isAbstract() {
		return methodSummary.isAbstract();
	}


	/**
	 *  Adds the return to the method declaration
	 *
	 *@param  methodDecl  The feature to be added to the Return attribute
	 *@param  index       The feature to be added to the Return attribute
	 */
	protected void addReturn(SimpleNode methodDecl, int index) {
		ASTResultType result = new ASTResultType(JavaParserTreeConstants.JJTRESULTTYPE);
		TypeDeclSummary returnType = methodSummary.getReturnType();
		if ((returnType != null) && !returnType.getType().equals("void")) {
			ASTType type = buildType(returnType);
			result.jjtAddChild(type, 0);
		}
		methodDecl.jjtAddChild(result, index);
	}


	/**
	 *  Creates the parameters
	 *
	 *@return    Description of the Returned Value
	 */
	protected ASTFormalParameters createParameters() {
		ASTFormalParameters params = new ASTFormalParameters(JavaParserTreeConstants.JJTFORMALPARAMETERS);
		Iterator iter = methodSummary.getParameters();
		if (iter != null) {
			int paramCount = 0;
			while (iter.hasNext()) {
				ParameterSummary paramSummary = (ParameterSummary) iter.next();
				ASTFormalParameter nextParam = new ASTFormalParameter(JavaParserTreeConstants.JJTFORMALPARAMETER);
				ASTType type = buildType(paramSummary.getTypeDecl());
				nextParam.jjtAddChild(type, 0);
				ASTVariableDeclaratorId paramDeclID = new ASTVariableDeclaratorId(JavaParserTreeConstants.JJTVARIABLEDECLARATORID);
				paramDeclID.setName(paramSummary.getName());
				nextParam.jjtAddChild(paramDeclID, 1);
				params.jjtAddChild(nextParam, paramCount);
				paramCount++;
			}
		}
		return params;
	}


	/**
	 *  Creates the exceptions
	 *
	 *@param  iter  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	protected ASTNameList createExceptions(Iterator iter) {
		ASTNameList list = new ASTNameList(JavaParserTreeConstants.JJTNAMELIST);
		int ndx = 0;
		while (iter.hasNext()) {
			TypeDeclSummary next = (TypeDeclSummary) iter.next();
			list.jjtAddChild(buildName(next), ndx);
			ndx++;
		}
		return list;
	}


	/**
	 *  Adds the exceptions to the node
	 *
	 *@param  methodDecl  The feature to be added to the Exceptions attribute
	 *@param  index       The feature to be added to the Exceptions attribute
	 *@return             Description of the Returned Value
	 */
	protected int addExceptions(SimpleNode methodDecl, int index) {
		Iterator iter = methodSummary.getExceptions();
		if (iter != null) {
			ASTNameList list = createExceptions(iter);
			methodDecl.jjtAddChild(list, index);
			index++;
		}
		return index;
	}


	/**
	 *  Adds the body of the method
	 *
	 *@param  methodDecl  The feature to be added to the Body attribute
	 *@param  index       The feature to be added to the Body attribute
	 */
	protected void addBody(SimpleNode methodDecl, int index) {
		if (!isAbstract()) {
			ASTBlock block = new ASTBlock(JavaParserTreeConstants.JJTBLOCK);
			methodDecl.jjtAddChild(block, index);
		}
	}


	/**
	 *  Drills down into the class definition to add the method
	 *
	 *@param  node  the type syntax tree node that is being modified
	 */
	private void drillIntoType(SimpleNode node) {
		if (node.jjtGetFirstChild() instanceof ASTClassDeclaration) {
			ASTClassDeclaration classDecl = (ASTClassDeclaration) node.jjtGetFirstChild();
			if (isAbstract()) {
				classDecl.setAbstract(true);
			}
			SimpleNode unmodified = (SimpleNode) classDecl.jjtGetFirstChild();
			SimpleNode classBody = (SimpleNode) unmodified.jjtGetChild(unmodified.jjtGetNumChildren() - 1);
			ASTClassBodyDeclaration decl = new ASTClassBodyDeclaration(JavaParserTreeConstants.JJTCLASSBODYDECLARATION);
			decl.jjtAddChild(build(true), 0);
			classBody.jjtAddChild(decl, classBody.jjtGetNumChildren());
		}
		else if (node.jjtGetFirstChild() instanceof ASTInterfaceDeclaration) {
			ASTInterfaceDeclaration classDecl = (ASTInterfaceDeclaration) node.jjtGetFirstChild();
         int childNo= classDecl.skipAnnotations();
         SimpleNode unmodified = (SimpleNode)classDecl.jjtGetChild(childNo);
			SimpleNode classBody = (SimpleNode) unmodified.jjtGetChild(unmodified.jjtGetNumChildren() - 1);
			classBody.jjtAddChild(build(false), classBody.jjtGetNumChildren());
		}
	}


	/**
	 *  Builds the method to be adding
	 *
	 *@param  addBody  Description of Parameter
	 *@return          a syntax tree branch containing the new method
	 */
	private ASTMethodDeclaration build(boolean addBody) {
		ASTMethodDeclaration methodDecl = new ASTMethodDeclaration(JavaParserTreeConstants.JJTMETHODDECLARATION);

		//  Set the modifiers
		copyModifiers(methodSummary,methodDecl);

		//  Set return type
		addReturn(methodDecl, 0);

		//  Set the declaration
		ASTMethodDeclarator declarator = new ASTMethodDeclarator(JavaParserTreeConstants.JJTMETHODDECLARATOR);
		declarator.setName(methodSummary.getName());
		ASTFormalParameters params = createParameters();
		declarator.jjtAddChild(params, 0);
		methodDecl.jjtAddChild(declarator, 1);

		int index = 2;

		index = addExceptions(methodDecl, index);

		if (addBody) {
			addBody(methodDecl, index);
		}

		return methodDecl;
	}




	/**
	 *  Builds the type from the type declaration summary
	 *
	 *@param  summary  the summary
	 *@return          the AST type node
	 */
	private ASTType buildType(TypeDeclSummary summary) {
		ASTType type = new ASTType(JavaParserTreeConstants.JJTTYPE);
		if (summary.getArrayCount()==0 && summary.isPrimitive()) {
			type.jjtAddChild(buildPrimitive(summary), 0);
		} else {
         type.jjtAddChild(buildReferenceType(summary), 0);
		}
		return type;
	}


	/**
	 *  Builds the name of the type from the type decl summary
	 *
	 *@param  summary  the summary
	 *@return          the name node
	 */
	private ASTName buildName(TypeDeclSummary summary) {
		ASTName name = new ASTName();
		name.fromString(summary.getLongName());
		return name;
	}
        
        
	/**
	 *  Builds the name of the type from the type decl summary
	 *
	 *@param  summary  the summary
	 *@return          the name node
	 */
	private ASTClassOrInterfaceType buildClassName(TypeDeclSummary summary) {
		ASTClassOrInterfaceType name = new ASTClassOrInterfaceType(JavaParserTreeConstants.JJTCLASSORINTERFACETYPE);
		name.fromString(summary.getLongName());
		return name;
	}
        
        
	/**
	 *  Builds the name of the type from the type decl summary
	 *
	 *@param  summary  the summary
	 *@return          the name node
	 */
	private ASTPrimitiveType buildPrimitive(TypeDeclSummary summary) {
		ASTPrimitiveType primitive = new ASTPrimitiveType(JavaParserTreeConstants.JJTPRIMITIVETYPE);
		primitive.setName(summary.getLongName());
		return primitive;
	}
        
        
	/**
	 *  Builds the name of the type from the type decl summary
	 *
	 *@param  summary  the summary
	 *@return          the name node
	 */
	private ASTReferenceType buildReferenceType(TypeDeclSummary summary) {
                ASTReferenceType reference = new ASTReferenceType(JavaParserTreeConstants.JJTREFERENCETYPE);
		if (summary.isPrimitive()) {
			reference.jjtAddChild(buildPrimitive(summary), 0);
		} else {
                        reference.jjtAddChild(buildClassName(summary), 0);
		}
                reference.setArrayCount(summary.getArrayCount());
		return reference;
	}
}
