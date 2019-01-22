/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTArguments;
import net.sourceforge.jrefactory.ast.ASTBlockStatement;
import net.sourceforge.jrefactory.ast.ASTLocalVariableDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTPrimarySuffix;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.summary.MethodSummary;

/**
 *  Visitor that seeks out and update the tree
 *
 *@author    Chris Seguin
 */
class RenameParameterVisitor extends IdentifyMethodVisitor {
	/**
	 *  Constructor for the RenameParameterVisitor object
	 *
	 *@param  init  Description of Parameter
	 */
	public RenameParameterVisitor(MethodSummary init)
	{
		super(init);
	}


	/**
	 *  Search and find the correct method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTMethodDeclaration node, Object data)
	{
		RenameParameterTransform rpt = (RenameParameterTransform) data;
		if (isFound(node)) {
			rpt.setRightTree(true);

			//  Update the javadoc

			//  Continue traversal
			super.visit(node, data);

			rpt.setRightTree(false);
			return null;
		}

		return super.visit(node, data);
	}


	/**
	 *  Search and find the correct method
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTConstructorDeclaration node, Object data)
	{
		RenameParameterTransform rpt = (RenameParameterTransform) data;
		if (isFound(node)) {
			rpt.setRightTree(true);

			//  Update the javadoc

			//  Continue traversal
			super.visit(node, data);

			rpt.setRightTree(false);
			return null;
		}

		return super.visit(node, data);
	}


	/**
	 *  Updates the parameter name
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTVariableDeclaratorId node, Object data)
	{
		RenameParameterTransform rpt = (RenameParameterTransform) data;
		if (rpt.isRightTree()) {
			if (node.getName().equals(rpt.getParameter().getName())) {
				node.setName(rpt.getNewName());
			}
		}

		return null;
	}


	/**
	 *  Visits a block node. Stops traversing the tree if we come to a new class.
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTBlockStatement node, Object data)
	{
		RenameParameterTransform rpt = (RenameParameterTransform) data;
		if (rpt.isRightTree()) {
			Node child = node.jjtGetFirstChild();
			if ((child instanceof ASTUnmodifiedClassDeclaration) ||
					(child instanceof ASTUnmodifiedInterfaceDeclaration)) {
				return null;
			}
		}

		return super.visit(node, data);
	}


	/**
	 *  Updates where that parameter is used
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTPrimaryExpression node, Object data)
	{
		RenameParameterTransform rpt = (RenameParameterTransform) data;
		if (rpt.isRightTree()) {
			ASTPrimaryPrefix prefix = (ASTPrimaryPrefix) node.jjtGetFirstChild();
			ASTPrimarySuffix suffix = null;
			if (node.jjtGetNumChildren() > 1) {
				suffix = (ASTPrimarySuffix) node.jjtGetChild(1);
			}

			if ((prefix.jjtGetNumChildren() > 0) && (prefix.jjtGetFirstChild() instanceof ASTName) &&
					((suffix == null) || (suffix.jjtGetNumChildren() == 0) || !isMethodCall(prefix, suffix))) {
				ASTName name = (ASTName) prefix.jjtGetFirstChild();
				if (name.getNamePart(0).equals(rpt.getParameter().getName())) {
					name.setNamePart(0, rpt.getNewName());
				}
			}
		}

		return super.visit(node, data);
	}

	private boolean isMethodCall(ASTPrimaryPrefix prefix, ASTPrimarySuffix suffix)
	{
		if (!(suffix.jjtGetFirstChild() instanceof ASTArguments))
			return false;

		ASTName name = (ASTName) prefix.jjtGetFirstChild();
		return (name.getNameSize() == 1);
	}
}
