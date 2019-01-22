/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;

import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ModifierHolder;

/**
 *  Sorts fields and method by whether they are marked final
 *
 *@author    Chris Seguin
 *@author    Mike Atkinson
 */
class FinalOrder extends Ordering {
	private boolean finalOnTop;

	public FinalOrder(boolean way) { finalOnTop = way; }

	/**
	 *  Gets the Index attribute of the FinalOrder object
	 *
	 *@param  object  Description of Parameter
	 *@return         The Index value
	 */
	protected int getIndex(Object object) {
		Object data = ((SimpleNode) object).jjtGetFirstChild();
		if (data instanceof ASTClassBodyDeclaration) {
			data = ((ASTClassBodyDeclaration) data).jjtGetFirstChild();
		}
		else if (data instanceof ASTInterfaceMemberDeclaration) {
			data = ((ASTInterfaceMemberDeclaration) data).jjtGetFirstChild();
		}

		int finalCode = 0;

		//  Now that we have data, determine the type of data
		if (data instanceof ASTEnumDeclaration) {
			finalCode = getFinalCode(((ASTEnumDeclaration) data).isFinal());
		}
		else if (data instanceof ASTFieldDeclaration) {
			finalCode = getFinalCode(((ASTFieldDeclaration) data).isFinal());
		}
		else if (data instanceof ASTConstructorDeclaration) {
			finalCode = getFinalCode(((ASTConstructorDeclaration) data).isFinal());
		}
		else if (data instanceof ASTMethodDeclaration) {
			finalCode = getFinalCode(((ASTMethodDeclaration) data).isFinal());
		}
		else if (data instanceof ASTNestedInterfaceDeclaration) {
			finalCode = getFinalCode(((ASTNestedInterfaceDeclaration) data).isFinal());
		}
		else if (data instanceof ASTNestedClassDeclaration) {
			finalCode = getFinalCode(((ASTNestedClassDeclaration) data).isFinal());
		}
		else {
			return 100;
		}

		if (finalOnTop) {
			return -finalCode;
		}
		else {
			return finalCode;
		}
	}


	/**
	 *  Gets the Protection attribute of the ProtectionOrder object
	 *
	 *@param  mods  Description of Parameter
	 *@return       The Protection value
	 */
	private int getFinalCode(boolean fin) {
		return (fin) ? 1 : 0;
	}

}
