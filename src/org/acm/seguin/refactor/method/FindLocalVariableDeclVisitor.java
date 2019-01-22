/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import net.sourceforge.jrefactory.parser.ChildrenVisitor;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTBlockStatement;
import net.sourceforge.jrefactory.ast.ASTLocalVariableDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import org.acm.seguin.summary.VariableSummary;

/**
 *  Finds the local variable declaration
 *
 *@author    Chris Seguin
 */
class FindLocalVariableDeclVisitor extends ChildrenVisitor
{
	private boolean found = false;


	/**
	 *  Gets the Found attribute of the FindLocalVariableDeclVisitor object
	 *
	 *@return    The Found value
	 */
	public boolean isFound()
	{
		return found;
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
		Node child = node.jjtGetFirstChild();
		if ((child instanceof ASTUnmodifiedClassDeclaration) ||
				(child instanceof ASTUnmodifiedInterfaceDeclaration))
		{
			return Boolean.FALSE;
		}

		return super.visit(node, data);
	}


	/**
	 *  Determines if it is used here
	 *
	 *@param  node  Description of Parameter
	 *@param  data  Description of Parameter
	 *@return       Description of the Returned Value
	 */
	public Object visit(ASTLocalVariableDeclaration node, Object data)
	{
		VariableSummary var = (VariableSummary) data;

		for (int ndx = 1; ndx < node.jjtGetNumChildren(); ndx++)
		{
			ASTVariableDeclarator next = (ASTVariableDeclarator) node.jjtGetChild(ndx);
			ASTVariableDeclaratorId id = (ASTVariableDeclaratorId) next.jjtGetFirstChild();
			if (id.getName().equals(var.getName()))
			{
				found = true;
				return Boolean.TRUE;
			}
		}

		return super.visit(node, data);
	}
}
