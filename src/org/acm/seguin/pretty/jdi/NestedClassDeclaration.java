/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTNestedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;


/**
 *  Holds a nested class declaration
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class NestedClassDeclaration extends BaseJDI {
   private ASTNestedClassDeclaration clazz;

   /**
    *  Constructor for the NestedClassDeclaration JavaDoc creator.
    *
    * @param  clazz Create JavaDoc for this node.
    */
   public NestedClassDeclaration(ASTNestedClassDeclaration clazz) {
      super();
      this.clazz = clazz;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("class", clazz);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getString("class.tags"));
   }


   /**  Makes sure all the java doc components are present  */
   public void finish() {
      //  Description of the class
      jdi.require("", DescriptionPadder.find(bundle, "class.descr"));

      //  Require the other tags
      int childNo = clazz.skipAnnotations();
      Node child = clazz.jjtGetChild(childNo);
      RequiredTags.getTagger().addTags(bundle, "class", ((ASTUnmodifiedClassDeclaration)child).getName(), jdi);
   }



}

