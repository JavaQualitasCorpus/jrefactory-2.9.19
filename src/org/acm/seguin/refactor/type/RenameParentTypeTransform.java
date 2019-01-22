package org.acm.seguin.refactor.type;

import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTImportDeclaration;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.factory.NameFactory;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.refactor.TransformAST;

/**
 *  This object revises the import statements in the tree. 
 *
 *@author     Chris Seguin 
 *@created    October 23, 1999 
 */
public class RenameParentTypeTransform extends TransformAST {
	private String newName = null;
	private String oldName = null;
	private TypeSummary summary = null;


	/**
	 *  Sets the OldName attribute of the RenameParentTypeTransform object 
	 *
	 *@param  name  The new OldName value 
	 */
	public void setOldName(String name) {
		oldName = name;
	}


	/**
	 *  Sets the NewName attribute of the RenameParentTypeTransform object 
	 *
	 *@param  name  The new NewName value 
	 */
	public void setNewName(String name) {
		newName = name;
	}


	/**
	 *  Sets the TypeSummary attribute of the RenameParentTypeTransform object 
	 *
	 *@param  value  The new TypeSummary value 
	 */
	public void setTypeSummary(TypeSummary value) {
		summary = value;
	}


	/**
	 *  Update the syntax tree 
	 *
	 *@param  root  the root of the syntax tree 
	 */
	public void update(SimpleNode root) {
		root.jjtAccept(new RenameParentVisitor(), 
				new RenameTypeData(oldName, newName, summary));
	}
}
