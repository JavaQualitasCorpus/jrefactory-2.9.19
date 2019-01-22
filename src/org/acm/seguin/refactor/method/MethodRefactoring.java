/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPrimitiveType;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.refactor.ComplexTransform;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.FileSummary;

/**
 *  This is a base class that is shared by a number of different
 *  method refactorings.
 *
 *@author    Chris Seguin
 */
abstract class MethodRefactoring extends Refactoring {
	/**
	 *  The name of the method
	 */
	protected String method;
   
   protected String[] params;
   
	/**
	 *  The type summary that contains the method
	 */
	protected TypeSummary typeSummary;


	/**
	 *  Constructor for the FieldRefactoring object
	 */
	public MethodRefactoring() {
		super();
	}


	/**
	 *  Sets the Class attribute of the MethodRefactoring object
	 *
	 *@param  packageName  the package name
	 *@param  className    the class name
	 */
	public void setClass(String packageName, String className) {
		setClass(GetTypeSummary.query(PackageSummary.getPackageSummary(packageName), className));
	}


	/**
	 *  Sets the Class attribute of the MethodRefactoring object
	 *
	 *@param  init  The new Class value
	 */
	public void setClass(TypeSummary init) {
		typeSummary = init;
	}


	/**
	 *  Sets the Method attribute of the MethodRefactoring object
	 *
	 *@param  methodName  The new Method value
	 */
	public void setMethod(String methodName) {
		method = methodName;
	}


	/**
	 *  Sets the types of the Parameters of the method of the MethodRefactoring object
	 *
	 *@param  params  The new Parameter types
	 */
	public void setParams(String[] params) {
		this.params = params;
	}


	/**
	 *  Determines if the specified type is in java.lang package
	 *
	 *@param  type  the type
	 *@return       true if it is in the package
	 */
	protected boolean isInJavaLang(ASTName type) {
		return (type.getNameSize() == 3) &&
				(type.getNamePart(0).equals("java")) &&
				(type.getNamePart(1).equals("lang"));
	}


	/**
	 *  Determines if the specified type is in java.lang package
	 *
	 *@param  type  the type
	 *@return       true if it is in the package
	 */
	protected boolean isInJavaLang(TypeSummary type) {
		return getPackage(type).getName().equals("java.lang");
	}


	/**
	 *  Gets the package summary for the specific object
	 *
	 *@param  current  the summary
	 *@return          the package summary
	 */
	protected PackageSummary getPackage(Summary current) {
		while (!(current instanceof PackageSummary)) {
			current = current.getParent();
		}
		return (PackageSummary) current;
	}


	/**
	 *  Gets the package summary for the specific object
	 *
	 *@param  current  the summary
	 *@return          the package summary
	 */
	protected FileSummary getFileSummary(Summary current) {
		while (!(current instanceof FileSummary)) {
			current = current.getParent();
		}
		return (FileSummary) current;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  source     Description of Parameter
	 *@param  transform  Description of Parameter
	 *@param  rft        Description of Parameter
	 */
	protected void removeMethod(TypeSummary source, ComplexTransform transform, RemoveMethodTransform rft) {
		transform.add(rft);
		FileSummary fileSummary = (FileSummary) source.getParent();
		transform.apply(fileSummary.getFile(), fileSummary.getFile());
	}
}
