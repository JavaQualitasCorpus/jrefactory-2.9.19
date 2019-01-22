/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;


/**
 *  Holds an interface declaration. Essentially this is the method declaration inside an interface.
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class InterfaceDeclaration extends BaseJDI {
   ASTInterfaceDeclaration intf;


   /**
    *  Constructor for the InterfaceDeclaration JavaDoc creator.
    *
    * @param  intf Create JavaDoc for this node.
    */
   public InterfaceDeclaration(ASTInterfaceDeclaration intf) {
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


   /**
    *  Makes sure all the java doc components are present. For classes and interfaces, this means a date and an author.
    */
   public void finish() {
      jdi.require("", DescriptionPadder.find(bundle, "interface.descr"));

      //  Require the other tags
      int childNo = intf.skipAnnotations();
      Node child = intf.jjtGetChild(childNo);
      RequiredTags.getTagger().addTags(bundle, "class", ((ASTUnmodifiedInterfaceDeclaration)child).getName(), jdi);
   }

}

