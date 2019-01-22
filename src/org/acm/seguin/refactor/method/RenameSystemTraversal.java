/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import org.acm.seguin.refactor.ComplexTransform;
import org.acm.seguin.summary.FieldAccessSummary;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.LocalVariableSummary;
import org.acm.seguin.summary.MessageSendSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TraversalVisitor;
import org.acm.seguin.summary.TypeDeclSummary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.VariableSummary;

/**
 *  All items that want to visit a summary tree should implement this
 *  interface.
 *
 *@author     Chris Seguin
 *@created    May 15, 1999
 */
public class RenameSystemTraversal extends TraversalVisitor {
	/**
	 *  Visit a file summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(FileSummary node, Object data) {
		if (node.getFile() == null) {
			return data;  // this file summary is from a stub (probably the JDK).
		}
      System.out.println("RenameSystemTraversal.visit(FileSummary "+node+", Object data)");

		//  Over the types
		Boolean result = Boolean.FALSE;
		Iterator iter = node.getTypes();
		if (iter != null) {
			while (iter.hasNext() && result.equals(Boolean.FALSE)) {
				TypeSummary next = (TypeSummary) iter.next();
				result = (Boolean) next.accept(this, data);
			}
		}

		if (result.booleanValue()) {
			//  Apply the refactoring to this file
			System.out.println("Updating:  " + node.getFile().getPath());
			RenameMethodData rfd = (RenameMethodData) data;
			transform(node, rfd.getOldMethod(), rfd.getNewName(), rfd.getComplexTransform());
		}

		//  Return some value
		return data;
	}


	/**
	 *  Visit a type summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(TypeSummary node, Object data) {
      System.out.println("RenameSystemTraversal.visit(TypeSummary "+node+", Object data)");
		RenameMethodData rfd = (RenameMethodData) data;
		if (node.equals(rfd.getTypeSummary())) {
            System.out.println("  - not found");
			return Boolean.FALSE;
		}

		//  Over the fields
		Iterator iter = node.getMethods();
		if (iter != null) {
			while (iter.hasNext()) {
				MethodSummary next = (MethodSummary) iter.next();
				Boolean result = (Boolean) next.accept(this, data);
				if (result.equals(Boolean.TRUE)) {
               System.out.println("  - found");
					return result;
				}
			}
		}

		//  Over the types
		iter = node.getTypes();
		if (iter != null) {
			while (iter.hasNext()) {
				TypeSummary next = (TypeSummary) iter.next();
				Boolean result = (Boolean) next.accept(this, data);
				if (result.equals(Boolean.TRUE)) {
            System.out.println("  - found");
					return result;
				}
			}
		}

            System.out.println("  - not found");
		//  Return some value
		return Boolean.FALSE;
	}


	/**
	 *  Visit a method summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(MethodSummary node, Object data) {
      System.out.println("RenameSystemTraversal.visit(MethodSummary "+node+", Object data)");
		//  Finally visit the dependencies
		Iterator iter = node.getDependencies();
		if (iter != null) {
			while (iter.hasNext()) {
				Summary next = (Summary) iter.next();
				Boolean result = (Boolean) next.accept(this, data);
				if (result.equals(Boolean.TRUE)) {
               System.out.println("  - found");
					return result;
				}
			}
		}

            System.out.println("  - not found");
		return Boolean.FALSE;
	}


	/**
	 *  Visit a field summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(FieldSummary node, Object data) {
		return Boolean.FALSE;
	}


	/**
	 *  Visit a parameter summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(ParameterSummary node, Object data) {
		return Boolean.FALSE;
	}


	/**
	 *  Visit a local variable summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(LocalVariableSummary node, Object data) {
		return Boolean.FALSE;
	}


	/**
	 *  Visit a variable summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(VariableSummary node, Object data) {
		return Boolean.FALSE;
	}


	/**
	 *  Visit a type declaration summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(TypeDeclSummary node, Object data) {
		return Boolean.FALSE;
	}


	/**
	 *  Visit a message send summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(MessageSendSummary node, Object data) {
      System.out.println("RenameSystemTraversal.visit(MessageSendSummary "+node+", Object data)");
		String message = node.toString();

		RenameMethodData rfd = (RenameMethodData) data;
      String oldName = rfd.getOldName();
		StringTokenizer tok = new StringTokenizer(message, ".");

		while (tok.hasMoreTokens()) {
			String next = tok.nextToken();
      int index = next.indexOf("(");
      if (index>0) {
         next = next.substring(0, index);
      }
         System.out.println("  next="+next+", oldName="+oldName);
			if (next.equals(oldName)) {
            System.out.println("  - found");
				return Boolean.TRUE;
			}
		}

      System.out.println("  - not found");
		return Boolean.FALSE;
	}


	/**
	 *  Visit a field access summary.
	 *
	 *@param  node  the summary that we are visiting
	 *@param  data  the data that was passed in
	 *@return       the result
	 */
	public Object visit(FieldAccessSummary node, Object data) {
      System.out.println("RenameSystemTraversal.visit(FieldAccessSummary "+node+", Object data)");
		String message = node.getName();

		RenameMethodData rfd = (RenameMethodData) data;
		StringTokenizer tok = new StringTokenizer(message, ".");

		while (tok.hasMoreTokens()) {
			String next = tok.nextToken();
			if (next.equals(rfd.getOldName())) {
            System.out.println("  - found");
				return Boolean.TRUE;
			}
		}

            System.out.println("  - not found");
		return Boolean.FALSE;
	}


	/**
	 *  Perform the rename transformation on this particular file
	 *
	 *@param  fileSummary  Description of Parameter
	 *@param  oldField     Description of Parameter
	 *@param  newName      Description of Parameter
	 *@param  transform    Description of Parameter
	 */
	private void transform(FileSummary fileSummary, MethodSummary oldMethod,
			String newName, ComplexTransform transform) {
      System.out.println("RenameSystemTraversal.transform()");
		transform.clear();

		RenameMethodTransform rft = new RenameMethodTransform(oldMethod, newName);
		transform.add(rft);
		transform.apply(fileSummary.getFile(), fileSummary.getFile());
	}
}
