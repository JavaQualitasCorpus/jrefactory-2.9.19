/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclarator;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;


/**
 *  Holds a field declaration. The two components that this structure store are the modifiers to the field and any
 *  javadoc comments associated with the field.
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class FieldDeclaration extends BaseJDI {
   private ASTFieldDeclaration field;


   /**
    *  Constructor for the FieldDeclaration JavaDoc creator.
    *
    * @param  field Create JavaDoc for this node.
    */
   public FieldDeclaration(ASTFieldDeclaration field) {
      super();
      this.field = field;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("field", field);
   }


   /**
    *  Return true if the javadoc comments were printed
    *
    * @return    The javadocPrinted value
    */
   public boolean isJavadocPrinted() {
      return jdi.isPrinted();
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getString("field.tags"));
   }


   /**  Makes sure all the java doc components are present  */
   public void finish() {
      jdi.require("", DescriptionPadder.find(bundle, "field.descr"));

      //  Require the other tags
      int childNo = field.skipAnnotations();
      ASTVariableDeclarator decl = (ASTVariableDeclarator)field.jjtGetChild(childNo+1);
      ASTVariableDeclaratorId varID = (ASTVariableDeclaratorId)decl.jjtGetFirstChild();
      RequiredTags.getTagger().addTags(bundle, "field", varID.getName(), jdi);
   }


}

