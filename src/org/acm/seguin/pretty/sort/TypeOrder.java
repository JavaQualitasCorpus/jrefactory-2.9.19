/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.sort;
import java.util.StringTokenizer;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTInitializer;
import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTAnnotation;

/**
 *  Orders the items in a class according to type.
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    July 4, 1999
 */
public class TypeOrder extends Ordering {
	//  Instance variables
	private Class[] order;
	private boolean usingMain;


	/**
	 *  Constructor for the TypeOrder object
	 *
	 *@param  ordering  A user specified string that describes the order
	 */
	public TypeOrder(String ordering)
	{
		//  Create instance variables
		usingMain = false;
		order = new Class[8];
		order[0] = ASTEnumDeclaration.class;
		order[1] = ASTFieldDeclaration.class;
		order[2] = ASTConstructorDeclaration.class;
		order[3] = ASTMethodDeclaration.class;
		order[4] = ASTNestedInterfaceDeclaration.class;
		order[5] = ASTNestedClassDeclaration.class;
		order[6] = ASTInitializer.class;
		order[7] = ASTInitializer.class;

		//  Local Variables
		int nextItem = 0;

		//  Break it up
		StringTokenizer tok = new StringTokenizer(ordering, ", \t");
		while (tok.hasMoreTokens() && (nextItem < 7)) {
			String next = tok.nextToken();
			String lowerCase = next.toLowerCase();
			if (lowerCase.startsWith("e")) {
				order[nextItem] = ASTEnumDeclaration.class;
			}
			else if (lowerCase.startsWith("f")) {
				order[nextItem] = ASTFieldDeclaration.class;
			}
			else if (lowerCase.startsWith("c")) {
				order[nextItem] = ASTConstructorDeclaration.class;
			}
			else if (lowerCase.startsWith("me")) {
				order[nextItem] = ASTMethodDeclaration.class;
			}
			else if (lowerCase.startsWith("ma")) {
				order[nextItem] = String.class;
				usingMain = true;
			}
			else if (lowerCase.startsWith("i")) {
				order[nextItem] = ASTInitializer.class;
			}
			else if (lowerCase.startsWith("n")) {
				if ((lowerCase.indexOf("i") >= 0)) {
					order[nextItem] = ASTNestedInterfaceDeclaration.class;
				}
				else {
					order[nextItem] = ASTNestedClassDeclaration.class;
				}
			}

			nextItem++;
		}
	}


	/**
	 *  Return the index of the item in the order array
	 *
	 *@param  object  the object we are checking
	 *@return         the objects index if it is found or 7 if it is not
	 */
	protected int getIndex(Object object)
	{
		Object data = ((SimpleNode) object).jjtGetFirstChild();
		if (data instanceof ASTClassBodyDeclaration) {
			data = ((ASTClassBodyDeclaration) data).jjtGetFirstChild();
		}
		else if (data instanceof ASTInterfaceMemberDeclaration) {
			data = ((ASTInterfaceMemberDeclaration) data).jjtGetFirstChild();
		}

		Class type = data.getClass();

		for (int ndx = 0; ndx < 7; ndx++) {
			if (isMatch(data, type, order[ndx])) {
				return ndx;
			}
		}

		return 8;
	}


	/**
	 *  Gets the Match attribute of the TypeOrder object
	 *
	 *@param  data     Description of Parameter
	 *@param  type     Description of Parameter
	 *@param  current  Description of Parameter
	 *@return          The Match value
	 */
	private boolean isMatch(Object data, Class type, Class current)
	{
		if (usingMain && (current.equals(String.class))) {
			if (type.equals(ASTMethodDeclaration.class)) {
            ASTMethodDeclaration declaration = (ASTMethodDeclaration) data;
            int childNo = declaration.skipAnnotationsAndTypeParameters();                         
            ASTMethodDeclarator declar = (ASTMethodDeclarator) (declaration.jjtGetChild(childNo+1));
            String name = declar.getName();
            return name.equals("main") && declaration.isStatic();
			}

			return false;
		}
		else {
			return type.equals(current);
		}
	}
}
