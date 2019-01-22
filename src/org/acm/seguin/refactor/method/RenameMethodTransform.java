/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import net.sourceforge.jrefactory.ast.SimpleNode;
import org.acm.seguin.refactor.TransformAST;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.SummaryLoadVisitor;
import org.acm.seguin.summary.SummaryLoaderState;


/**
 *  A transform that renames a specific method
 *
 * @author    Mike Atkinson
 * @since     2.9.11
 */
public class RenameMethodTransform extends TransformAST {
	private MethodSummary oldMethod;
	private String newName;


	/**
	 *  Constructor for the RemoveFieldTransform object
	 *
	 *@param  oldName  Description of Parameter
	 *@param  newName  Description of Parameter
	 */
	public RenameMethodTransform(MethodSummary oldMethod, String newName) {
		this.oldMethod = oldMethod;
		this.newName = newName;
	}


	/**
	 *  Updates the root
	 *
	 *@param  root  the root node
	 */
	public void update(SimpleNode root) {
      System.out.println("RenameMethodTransform.update()");
		RenameMethodVisitor rfv = new RenameMethodVisitor();
		rfv.visit(root, new RenameMethodData(oldMethod, newName));
      /*
      Summary parent = oldMethod.getParent();
      while ( !(parent instanceof FileSummary) ) {
         parent = parent.getParent();
      }
		SummaryLoaderState state = new SummaryLoaderState();
      state.setFile( ((FileSummary)parent).getFile() );
		root.jjtAccept(new SummaryLoadVisitor(), state);
      */
	}
}
