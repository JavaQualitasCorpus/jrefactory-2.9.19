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
import net.sourceforge.jrefactory.ast.ASTInterfaceMemberDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTAnnotation;


/**
 *  Sort the objects so that beans end up together
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    April 3, 2002
 */
class BeanTogetherOrder extends Ordering
{
    /**
     *  Description of the Method
     *
     *@param  one  Description of Parameter
     *@param  two  Description of Parameter
     *@return      Description of the Returned Value
     */
    public int compare(Object one, Object two)
    {
        String nameOne = isBean(one);
        String nameTwo = isBean(two);

        if ((nameOne == null) && (nameTwo == null))
        	return 0;

        if (nameOne == null)
        	return -1;

        if (nameTwo == null)
        	return 1;

		String propertyOne = getPropertyName(nameOne);
		String propertyTwo = getPropertyName(nameTwo);

		int compare = propertyOne.compareTo(propertyTwo);
		if (compare != 0)
			return compare;

		int prefixCodeOne = getPrefixCode(nameOne);
		int prefixCodeTwo = getPrefixCode(nameTwo);

		if (prefixCodeOne < prefixCodeTwo)
			return -1;
		if (prefixCodeOne > prefixCodeTwo)
			return 1;

		return 0;
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
     *@param  object  Description of the Parameter
     *@return         The Name value of the method or null if it is not possibly
     *      a bean
     */
    private String isBean(Object object)
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
            return null;
        }
        else if (data instanceof ASTFieldDeclaration) {
            return null;
        }
        else if (data instanceof ASTConstructorDeclaration) {
            return null;
        }
        else if (data instanceof ASTMethodDeclaration) {
            ASTMethodDeclaration method = (ASTMethodDeclaration) data;
            String name;
            int childNo = method.skipAnnotationsAndTypeParameters();
            ASTMethodDeclarator decl = (ASTMethodDeclarator) method.jjtGetChild(childNo+1);
            name = decl.getName();

            if (name.length() > 3) {
                if ((name.startsWith("get") || name.startsWith("set")) &&
                        (Character.isUpperCase(name.charAt(3)))) {
                    return name;
                }
            }

            if (name.startsWith("is") && Character.isUpperCase(name.charAt(2))) {
                return name;
            }
            else {
                return null;
            }
        }
        else if (data instanceof ASTNestedInterfaceDeclaration) {
            return null;
        }
        else if (data instanceof ASTNestedClassDeclaration) {
            return null;
        }
        else {
            return null;
        }
    }

    /**
     *  Get the prefix
     */
    private String getPrefix(String value)
    {
    	if (value.startsWith("is"))
    		return "is";
    	else
    		return value.substring(0, 3);
    }

    /**
     *  Get the property name
     */
    private String getPropertyName(String value)
    {
    	if (value.startsWith("is"))
    		return value.substring(2);
    	else
    		return value.substring(3);
    }

    private int getPrefixCode(String value)
    {
    	String prefix = getPrefix(value);
    	if (prefix.equals("is"))
    		return 3;

    	if (prefix.equals("set"))
    		return 2;

    	if (prefix.equals("get"))
    		return 1;

    	return 5;
    }
}
