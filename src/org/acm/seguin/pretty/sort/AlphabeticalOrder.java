/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;

import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTLiteral;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTAnnotation;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 *@author    Mike Atkinson
 */
class AlphabeticalOrder extends Ordering {
	/**
	 *  Description of the Method
	 *
	 *@param  one  Description of Parameter
	 *@param  two  Description of Parameter
	 *@return      Description of the Returned Value
	 */
	public int compare(Object one, Object two)
	{
		String nameOne = getName(one);
		String nameTwo = getName(two);

		return nameOne.compareTo(nameTwo);
	}


	/**
	 *  Gets the Index attribute of the AlphabeticalOrder object
	 *
	 *@param  object  Description of Parameter
	 *@return         The Index value
	 */
	protected int getIndex(Object object)
	{
		return 0;
	}


	/**
	 *  Gets the Name attribute of the AlphabeticalOrder object
	 *
	 *@param  obj  Description of Parameter
	 *@return      The Name value
	 */
	private String getName(Object object)
	{
		Object data = ((SimpleNode) object).jjtGetFirstChild();
		if (data instanceof ASTClassBodyDeclaration) {
			data = ((ASTClassBodyDeclaration) data).jjtGetFirstChild();
		}
		else if (data instanceof ASTInterfaceMemberDeclaration) {
			data = ((ASTInterfaceMemberDeclaration) data).jjtGetFirstChild();
		}

		//  Now that we have data, determine the type of data
		if (data instanceof ASTEnumDeclaration) {
			ASTEnumDeclaration field = (ASTEnumDeclaration) data;
         int childNo = field.skipAnnotations();
         Node child = field.jjtGetChild(childNo);
			ASTLiteral id = (ASTLiteral)child;
			return id.getName();
		}
		else if (data instanceof ASTFieldDeclaration) {
			ASTFieldDeclaration field = (ASTFieldDeclaration) data;
         int childNo = field.skipAnnotations();
			ASTVariableDeclarator declar = (ASTVariableDeclarator) field.jjtGetChild(childNo+1);
			return ((ASTVariableDeclaratorId) declar.jjtGetFirstChild()).getName();
		}
		else if (data instanceof ASTConstructorDeclaration) {
			return "";
		}
		else if (data instanceof ASTMethodDeclaration) {
			ASTMethodDeclaration method = (ASTMethodDeclaration) data;
         int childNo = method.skipAnnotationsAndTypeParameters();
			ASTMethodDeclarator decl = (ASTMethodDeclarator) method.jjtGetChild(childNo+1);
			return decl.getName();
		}
		else if (data instanceof ASTNestedInterfaceDeclaration) {
			ASTNestedInterfaceDeclaration nestedInterface = (ASTNestedInterfaceDeclaration) data;
			ASTUnmodifiedInterfaceDeclaration unmodified = (ASTUnmodifiedInterfaceDeclaration) nestedInterface.jjtGetFirstChild();
			return unmodified.getName();
		}
		else if (data instanceof ASTNestedClassDeclaration) {
			ASTNestedClassDeclaration nestedClass = (ASTNestedClassDeclaration) data;
			ASTUnmodifiedClassDeclaration unmodified = (ASTUnmodifiedClassDeclaration) nestedClass.jjtGetFirstChild();
			return unmodified.getName();
		}
		else {
			return "";
		}
	}
}
