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
import net.sourceforge.jrefactory.parser.ChildrenVisitor;
import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.ast.ASTArguments;
import net.sourceforge.jrefactory.ast.ASTArgumentList;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTPrimarySuffix;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import java.util.Iterator;
import org.acm.seguin.awt.Question;
import org.acm.seguin.summary.TraversalVisitor;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.TypeDeclSummary;
import org.acm.seguin.summary.VariableSummary;
import org.acm.seguin.refactor.ComplexTransform;
import org.acm.seguin.summary.query.Ancestor;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.summary.query.ImportsType;
import org.acm.seguin.summary.query.LookupVariable;


/**
 *  Removes the method from all subclasses of a particular class.
 *
 *@author    Chris Seguin
 */
public class RenameMethodVisitor extends ChildrenVisitor {
	/**
	 *  Visit a package declaration
	 *
	 *@param  node  the class body node
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTPackageDeclaration node, Object data) {
		RenameMethodData rfd = (RenameMethodData) data;

		ASTName name = (ASTName) node.jjtGetFirstChild();
		PackageSummary packageSummary = PackageSummary.getPackageSummary(name.getName());
		rfd.setCurrentSummary(packageSummary);

		return super.visit(node, data);
	}


	/**
	 *  Visit a class declaration
	 *
	 *@param  node  the class body node
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTUnmodifiedClassDeclaration node, Object data) {
		RenameMethodData rfd = (RenameMethodData) data;
		Summary current = rfd.getCurrentSummary();

		if (current == null) {
			rfd.setCurrentSummary(GetTypeSummary.query("", node.getName()));
		} else if (current instanceof PackageSummary) {
			rfd.setCurrentSummary(GetTypeSummary.query((PackageSummary) current, node.getName()));
		} else if (current instanceof TypeSummary) {
			rfd.setCurrentSummary(GetTypeSummary.query((TypeSummary) current, node.getName()));
		} else if (current instanceof MethodSummary) {
			rfd.setCurrentSummary(GetTypeSummary.query((MethodSummary) current, node.getName()));
		}

		Object result = super.visit(node, data);

		rfd.setCurrentSummary(current);
		return result;
	}


	/**
	 *  Visit a class declaration
	 *
	 *@param  node  the class body node
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTUnmodifiedInterfaceDeclaration node, Object data) {
		RenameMethodData rfd = (RenameMethodData) data;
		Summary current = rfd.getCurrentSummary();

		if (current == null) {
			rfd.setCurrentSummary(GetTypeSummary.query("", node.getName()));
		} else if (current instanceof PackageSummary) {
			rfd.setCurrentSummary(GetTypeSummary.query((PackageSummary) current, node.getName()));
		} else if (current instanceof TypeSummary) {
			rfd.setCurrentSummary(GetTypeSummary.query((TypeSummary) current, node.getName()));
		} else if (current instanceof MethodSummary) {
			rfd.setCurrentSummary(GetTypeSummary.query((MethodSummary) current, node.getName()));
		}

		Object result = super.visit(node, data);

		rfd.setCurrentSummary(current);
		return result;
	}


	/**
	 *  Visit a field declaration
	 *
	 *@param  node  the class body node
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTMethodDeclaration node, Object data) {
		RenameMethodData rfd = (RenameMethodData) data;

		if (rfd.getCurrentSummary() == rfd.getTypeSummary()) {
         int childNo = node.skipAnnotations();
			for (int ndx = childNo+1; ndx < node.jjtGetNumChildren(); ndx++) {
            Node child = node.jjtGetChild(ndx);
            if (child instanceof ASTMethodDeclarator) {
               ASTMethodDeclarator method = (ASTMethodDeclarator)child;
               if (method.getName().equals(rfd.getOldName())) {
                  // FIXME check parameters
                  ASTFormalParameters params = (ASTFormalParameters)method.jjtGetFirstChild();
                  int oldCount = rfd.getOldMethod().getParameterCount();
                  int paramCount = params.jjtGetNumChildren();
                  System.out.println("visit(ASTMethodDeclaration): oldCount="+oldCount+", paramCount="+paramCount);
                  if (oldCount == paramCount) {
                     method.setName(rfd.getNewName());
                  }
               }
            }
			}
		}

		return super.visit(node, data);
	}


	/**
	 *  Visit a primary expression
	 *
	 *@param  node  the class body node
	 *@param  data  the data for the visitor
	 *@return       the field if it is found
	 */
	public Object visit(ASTPrimaryExpression node, Object data) {
		RenameMethodData rfd = (RenameMethodData) data;
		ASTPrimaryPrefix prefix = (ASTPrimaryPrefix) node.jjtGetFirstChild();
		if ("this".equals(prefix.getName())) {
			processThisExpression(rfd, node, prefix);
		} else if ((prefix.jjtGetNumChildren() >= 1) && (prefix.jjtGetFirstChild() instanceof ASTName)) {
			processNameExpression(rfd, node, prefix);
		}

		return super.visit(node, data);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  name                Description of Parameter
	 *@param  oldName             Description of Parameter
	 *@param  current             Description of Parameter
	 *@param  hasSuffixArguments  Description of Parameter
	 *@param  changingType        Description of Parameter
	 *@return                     Description of the Returned Value
	 */
	private int shouldChangePart(ASTName name, String oldName,
			Summary current, boolean hasSuffixArguments, TypeSummary changingType)
	{
		int last = name.getNameSize() - 1;
		if (hasSuffixArguments) {
			last--;
		}

		int forwardTo = -1;
		for (int ndx = last; ndx >= 0; ndx--) {
			if (name.getNamePart(ndx).equals(oldName)) {
				forwardTo = ndx;
			}
		}

		if (forwardTo == -1) {
			return -1;
		}

		VariableSummary varSummary = LookupVariable.query((MethodSummary) current, name.getNamePart(0));
		if (varSummary == null) {
			return -1;
		}
		TypeSummary currentType = GetTypeSummary.query(varSummary.getTypeDecl());

		for (int ndx = 1; ndx < forwardTo; ndx++) {
			varSummary = LookupVariable.queryFieldSummary(currentType, name.getNamePart(ndx));
			if (varSummary == null) {
				return -1;
			}
			currentType = GetTypeSummary.query(varSummary.getTypeDecl());
		}

		if (currentType == changingType) {
			return forwardTo;
		}

		return -1;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rfd     Description of Parameter
	 *@param  node    Description of Parameter
	 *@param  prefix  Description of Parameter
	 */
	private void processThisExpression(RenameMethodData rfd, ASTPrimaryExpression node, ASTPrimaryPrefix prefix)
	{
		if (rfd.isAllowedToChangeThis() && (node.jjtGetNumChildren() >= 2)) {
			ASTPrimarySuffix suffix = (ASTPrimarySuffix) node.jjtGetChild(1);
			if (rfd.getOldName().equals(suffix.getName())) {
				boolean change = true;

				if (node.jjtGetNumChildren() >= 3) {
					ASTPrimarySuffix next = (ASTPrimarySuffix) node.jjtGetChild(2);
					if ((next.jjtGetFirstChild() != null) && (next.jjtGetFirstChild() instanceof ASTArguments)) {
						change = false;
					}
				}

				if (change) {
					suffix.setName(rfd.getNewName());
				}
			}
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  rfd     Description of Parameter
	 *@param  node    Description of Parameter
	 *@param  prefix  Description of Parameter
	 */
	private void processNameExpression(RenameMethodData rfd, ASTPrimaryExpression node, ASTPrimaryPrefix prefix) {
		ASTName name = (ASTName) prefix.jjtGetFirstChild();
      System.out.println("processNameExpression() "+name);

      //MethodSummary oldMethod = rfd.getOldMethod();
		if (!rfd.isThisRequired()) {
			ASTArguments arguments = null;

			if (node.jjtGetNumChildren() >= 2) {
				ASTPrimarySuffix next = (ASTPrimarySuffix) node.jjtGetChild(1);
				if ((next.jjtGetFirstChild() != null) && (next.jjtGetFirstChild() instanceof ASTArguments)) {
					arguments = (ASTArguments)next.jjtGetFirstChild();
				}
			}

         System.out.println("arguments="+arguments);
			if (arguments != null) {
            System.out.println("name.getNameSize()="+name.getNameSize()+", rfd.isAllowedToChangeFirst()="+rfd.isAllowedToChangeFirst());
            if ((name.getNameSize()==1) && rfd.isAllowedToChangeFirst() && (name.getNamePart(0).equals(rfd.getOldName()))) {
               if (checkParameters(rfd, arguments)) {
                  name.setNamePart(0, rfd.getNewName());
                  if (rfd.isMustInsertThis()) {
                     name.insertNamePart(0, "this");
                  }
                  System.out.println("[1] old MethodSummary.name="+rfd.getOldMethod().getName()+", name.getName()="+name.getName());
                  //rfd.getOldMethod().setName(name.getName());
                  //System.out.println("[1] new MethodSummary.name="+rfd.getOldMethod().getName());
               }
				} else if (name.getNamePart(name.getNameSize()-1).equals(rfd.getOldName())) {
               if (checkParameters(rfd, arguments)) {
                  int last = name.getNameSize() - 1;
                  if (name.getNamePart(last).equals(rfd.getOldName())) {
                     name.setNamePart(last, rfd.getNewName());
                  }
                  System.out.println("[2] old MethodSummary.name="+rfd.getOldMethod().getName()+", name.getName()="+name.getName());
                  //rfd.getOldMethod().setName(name.getName());
                  //System.out.println("[2] new MethodSummary.name="+rfd.getOldMethod().getName());
               }
				}
			}
		}

		if (rfd.getOldMethod().isStatic()) {
			String nameString = name.getName();
         System.out.println("oldMethod is static nameString="+nameString+", rfd.getFullName()="+rfd.getFullName()+", rfd.getImportedName()="+rfd.getImportedName());
			if (nameString.startsWith(rfd.getFullName())) {
				replaceNamePart(name, rfd.getFullName(), rfd.getNewName());
            System.out.println("[3] old MethodSummary.name="+rfd.getOldMethod().getName());
            //rfd.getOldMethod().setName(rfd.getFullName());
            //System.out.println("[3] new MethodSummary.name="+rfd.getOldMethod().getName());
			} else if (nameString.startsWith(rfd.getImportedName()) && ImportsType.query(rfd.getCurrentSummary(), rfd.getTypeSummary())) {
				replaceNamePart(name, rfd.getImportedName(), rfd.getNewName());
            System.out.println("[4] old MethodSummary.name="+rfd.getOldMethod().getName());
            //rfd.getOldMethod().setName(rfd.getImportedName());
            //System.out.println("[4] new MethodSummary.name="+rfd.getOldMethod().getName());
			}
		}
	}


   private boolean checkParameters(RenameMethodData rfd, ASTArguments arguments) {
      int oldCount = rfd.getOldMethod().getParameterCount();
      if (arguments.jjtGetNumChildren()==0) {
         return oldCount==0;
      }
      ASTArgumentList argList = (ASTArgumentList)arguments.jjtGetFirstChild();
      int argCount = argList.jjtGetNumChildren();
      System.out.println("checkParameters(): oldCount="+oldCount+", argCount="+argCount);
      if (oldCount != argCount) {
         return false;
      }
      // FIXME check the parameter types
      return true;
   }


	/**
	 *  Description of the Method
	 *
	 *@param  name     Description of Parameter
	 *@param  form     Description of Parameter
	 *@param  newName  Description of Parameter
	 */
	private void replaceNamePart(ASTName name, String form, String newName) {
		StringTokenizer tok = new StringTokenizer(form, ".");
		int count = -1;
		String finalPart = null;
		while (tok.hasMoreTokens()) {
			finalPart = tok.nextToken();
			count++;
		}

		if (name.getNamePart(count).equals(finalPart)) {
			name.setNamePart(count, newName);
		}
	}
}
