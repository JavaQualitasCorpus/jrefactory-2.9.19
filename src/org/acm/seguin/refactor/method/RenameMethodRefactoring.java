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
import org.acm.seguin.refactor.ComplexTransform;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.FieldAccessSummary;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.MessageSendSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.FieldQuery;
import org.acm.seguin.summary.query.MethodQuery;
import org.acm.seguin.summary.PackageSummary;



/**
 *  Moves a method from one class to another. Generally used to move a method into a local variable or a parameter.
 *
 * @author    CMike Atkinson
 * @since     2.9.11
 */
public class RenameMethodRefactoring extends MethodRefactoring {
   private String newName;
   private MethodSummary oldMethod;


   /**
    *  Constructor for the MoveMethodRefactoring object
    *
    * @since    empty
    */
   public RenameMethodRefactoring() { 
      super();
   }


   /**
    *  Sets the Method attribute of the MoveMethodRefactoring object
    *
    * @param  value  The new Method value
    * @since         empty
    */
   public void setMethod(MethodSummary value) {
      oldMethod = value;
      Summary current = oldMethod;
      while (!(current instanceof TypeSummary)) {
         current = current.getParent();
      }
      typeSummary = (TypeSummary)current;
   }


   /**
    *  Sets the Destination attribute of the MoveMethodRefactoring object
    *
    * @param  newName  The new newMethodName value
    * @since           empty
    */
   public void setNewMethodName(String newName) {
      this.newName = newName;
   }


   /**
    *  Gets the description of the refactoring
    *
    * @return    the description
    * @since     empty
    */
   public String getDescription() {
      return "Renaming " + oldMethod.toString() + " to " + newName;
   }


   /**
    *  Gets the ID attribute of the MoveMethodRefactoring object
    *
    * @return    The ID value
    * @since     empty
    */
   public int getID() {
      return RENAME_METHOD;
   }


   /**
    *  Describes the preconditions that must be true for this refactoring to be applied
    *
    * @exception  RefactoringException  thrown if one or more of the preconditions is not satisfied. The text of the
    *      exception provides a hint of what went wrong.
    * @since                            empty
    */
   protected void preconditions() throws RefactoringException {
      System.out.println();
      System.out.println();
      System.out.println("preconditions() method="+method+"");
      getMethodSummary();
      Iterator iter = oldMethod.getDependencies();
      while ((iter != null) && (iter.hasNext())) {
         Summary next = (Summary)iter.next();
         //  Check to see if we have any private fields without the appropriate getters/setters
         if (next instanceof FieldAccessSummary) {
            FieldAccessSummary fas = (FieldAccessSummary)next;
            checkFieldAccess(fas);
         } else if (next instanceof MessageSendSummary) {
            MessageSendSummary mss = (MessageSendSummary)next;
            checkMessageSend(mss);
         }
      }
   }

   private void getMethodSummary() throws RefactoringException {
      if (oldMethod==null) {
         Iterator iter = typeSummary.getMethods();
         if (iter == null) {
            throw new RefactoringException(typeSummary.getName() + " has no methods associated with it, so it cannot be renamed");
         }

         boolean found = false;
         while (iter.hasNext()) {
            MethodSummary next = (MethodSummary) iter.next();
            System.out.println("  "+next);
            if (next.getName().equals(method) && checkParams(next)) {
               found = true;
               oldMethod = next;
            }
            if (next.getName().equals(newName) && checkParams(next)) {
               throw new RefactoringException("A method named " + newName + " already exists in class " + typeSummary.getName());
            }
         }

         if (!found) {
            throw new RefactoringException("No method named " + method + " is contained in " + typeSummary.getName());
         }
         
      }
   }

   protected boolean checkParams(MethodSummary summary) {
      System.out.println("summary="+summary);
      System.out.println("params.length="+params.length);
      System.out.println("summary.getParameterCount()="+summary.getParameterCount());
      return summary.getParameterCount()==params.length;
   }


   /**
    *  Performs the transform on the rest of the classes
    *
    * @since    empty
    */
   protected void transform() {
		if (oldMethod.getName().equals(newName)) {
			return;
      }

		FileSummary fileSummary = (FileSummary) getFileSummary(typeSummary);
		RenameMethodTransform rft = new RenameMethodTransform(oldMethod, newName);
		ComplexTransform transform = getComplexTransform();
		transform.add(rft);
		transform.apply(fileSummary.getFile(), fileSummary.getFile());

		if (oldMethod.isPrivate()) {
			//  We are done
         System.out.println("handle private");
		} else if (oldMethod.isPackage()) {
         System.out.println("handle package");
			RenameSystemTraversal rsv = new RenameSystemTraversal();
			rsv.visit(getPackage(), new RenameMethodData(oldMethod, newName, transform));
		} else {
         System.out.println("handle public or protected");
			RenameSystemTraversal rsv = new RenameSystemTraversal();
			rsv.visit(new RenameMethodData(oldMethod, newName, transform));
		}
   }


	/**
	 *  Gets the Package attribute of the RenameFieldRefactoring object
	 *
	 *@return    The Package value
	 */
	private PackageSummary getPackage() {
		Summary current = oldMethod;
		while (!(current instanceof PackageSummary)) {
			current = current.getParent();
		}

		return (PackageSummary) current;
	}


	/**
	 *  Description of the Method
	 *
	 *@param  methodDecl  Description of Parameter
	 *@return             Description of the Returned Value
	 */
/*
    protected ASTMethodDeclaration updateMethod(SimpleNode methodDecl) {
		ASTMethodDeclaration decl = (ASTMethodDeclaration) methodDecl.jjtGetFirstChild();
		for (int i=0; i<decl.jjtGetNumChildren(); i++) {
         if (decl.jjtGetChild(i) instanceof ASTMethodDeclarator) {
            ((ASTMethodDeclarator)decl.jjtGetChild(i)).setName(newName);
            break;
         }
      }
		return decl;
	}
*/

   /**
    *  Removes the method from the source
    *
    * @param  source     the source type
    * @param  transform  the transform
    * @return            Description of the Returned Value
    * @since             empty
    */
/*
   protected SimpleNode removeMethod(TypeSummary source, ComplexTransform transform) {
      RemoveMethodTransform rft = new RemoveMethodTransform(oldMethod);
      transform.add(rft);

      InvokeMovedMethodTransform immt = new InvokeMovedMethodTransform(oldMethod, typeSummary);
      transform.add(immt);

      FileSummary fileSummary = (FileSummary)source.getParent();
      transform.apply(fileSummary.getFile(), fileSummary.getFile());

      return rft.getMethodDeclaration();
   }
*/


	/**
	 *  Adds the method to the destination class
	 *
	 *@param  transform   The feature to be added to the MethodToDest attribute
	 *@param  rft         The feature to be added to the MethodToDest attribute
	 *@param  methodDecl  The feature to be added to the MethodToDest attribute
	 *@param  dest        The feature to be added to the MethodToDest attribute
	 */
/*
   protected void addMethodToDest(ComplexTransform transform,
                                  RemoveMethodTransform rft,
                                  SimpleNode methodDecl,
                                  TypeSummary dest) {
		transform.clear();
		transform.add(rft);
		AddMethodTransform aft = new AddMethodTransform(methodDecl);
		transform.add(aft);

		AddMethodTypeVisitor visitor = new AddMethodTypeVisitor();
		oldMethod.accept(visitor, transform);

		//  Add appropriate import statements - to be determined later
		FileSummary parentFileSummary = (FileSummary) dest.getParent();
		transform.apply(parentFileSummary.getFile(), parentFileSummary.getFile());
	}
*/


   /**
    *  Gets the name of the getter for the field
    *
    * @param  summary  the field summary
    * @return          the getter
    * @since           empty
    */
   private String getFieldGetter(FieldSummary summary) {
      String typeName = summary.getType();
      String prefix = "get";
      if (typeName.equalsIgnoreCase("boolean")) {
         prefix = "is";
      }

      String name = summary.getName();

      return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
   }


   /**
    *  Gets the name of the setter for the field
    *
    * @param  summary  the field summary
    * @return          the setter
    * @since           empty
    */
   private String getFieldSetter(FieldSummary summary) {
      String prefix = "set";
      String name = summary.getName();
      return prefix + name.substring(0, 1).toUpperCase() + name.substring(1);
   }


   /**
    *  Checks if we can properly transform the field access
    *
    * @param  fas                       Description of Parameter
    * @exception  RefactoringException  Description of Exception
    * @since                            empty
    */
   private void checkFieldAccess(FieldAccessSummary fas) throws RefactoringException {
      if ((fas.getPackageName() == null) &&
               ((fas.getObjectName() == null) || fas.getObjectName().equals("this"))) {
         //  Now we have to find the field
         FieldSummary field = FieldQuery.find(typeSummary, fas.getFieldName());
         if (field != null) {
            if (field.isPrivate()) {
               checkForMethod(fas, field);
            }
         }
      }
   }


   /**
    *  For a private field, check that we have the correct setters or getters (as appropriate)
    *
    * @param  fas                       Description of Parameter
    * @param  field                     Description of Parameter
    * @exception  RefactoringException  Description of Exception
    * @since                            empty
    */
   private void checkForMethod(FieldAccessSummary fas, FieldSummary field) throws RefactoringException {
      String methodName;
      if (fas.isAssignment()) {
         methodName = getFieldSetter(field);
      } else {
         methodName = getFieldGetter(field);
      }
      MethodSummary method = MethodQuery.find(typeSummary, methodName);
      if (method == null) {
         throw new RefactoringException("Unable to find the appropriate method (" +
                  methodName + ") for private field access in " + typeSummary.getName());
      }
   }


   /**
    *  Updates the node fore move method
    *
    * @param  node  Description of Parameter
    * @since        empty
    */
/*
   private void update(SimpleNode node) {
      MoveMethodVisitor mmv = new MoveMethodVisitor(typeSummary, oldMethod, typeSummary);
      node.jjtAccept(mmv, null);
   }
*/


   /**
    *  Description of the Method
    *
    * @param  mss                       Description of Parameter
    * @exception  RefactoringException  Description of Exception
    * @since                            empty
    */
   private void checkMessageSend(MessageSendSummary mss) throws RefactoringException {
      if ((mss.getPackageName() == null) &&
               ((mss.getObjectName() == null) || mss.getObjectName().equals("this"))) {
         MethodSummary method = MethodQuery.find(typeSummary, mss.getMessageName());
         if (method == null) {
            throw new RefactoringException("Unable to find the method (" +
                     mss.getMessageName() + ") in " + typeSummary.getName());
         }
      }
   }

}

