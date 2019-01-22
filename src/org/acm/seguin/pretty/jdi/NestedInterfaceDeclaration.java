/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTNestedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;



/**
 *  Holds a nested interface
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class NestedInterfaceDeclaration extends BaseJDI {
   private ASTNestedInterfaceDeclaration intf;

   /**
    *  Constructor for the NestedInterfaceDeclaration JavaDoc creator.
    *
    * @param  intf Create JavaDoc for this node.
    */
   public NestedInterfaceDeclaration(ASTNestedInterfaceDeclaration intf) {
      super();
      this.intf = intf;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("class", intf);
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
      jdi.require("", DescriptionPadder.find(bundle, "interface.descr"));

      //  Require the other tags
      ASTUnmodifiedInterfaceDeclaration child = (ASTUnmodifiedInterfaceDeclaration)intf.jjtGetFirstChild();
      RequiredTags.getTagger().addTags(bundle, "class", child.getName(), jdi);
   }

}

