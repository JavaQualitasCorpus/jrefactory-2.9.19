/*
 *  Author: Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTEnumDeclaration;
import net.sourceforge.jrefactory.ast.ASTLiteral;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;


/**
 *  Stores an <code>enum</code> declaration. Enumerations are new to JDK 1.5
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class EnumDeclaration extends BaseJDI {
   private ASTEnumDeclaration enumeration;

   /**
    *  Constructor for the EnumDeclaration JavaDoc creator.
    *
    * @param  enumeration Create JavaDoc for this node.
    */
   public EnumDeclaration(ASTEnumDeclaration enumeration) {
      super();
      this.enumeration = enumeration;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("field", enumeration);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getString("enum.tags"));
   }


   /**  Makes sure all the java doc components are present  */
   public void finish() {
      jdi.require("", DescriptionPadder.find(bundle, "enum.descr"));
      int childNo = enumeration.skipAnnotations();
      ASTLiteral litID = (ASTLiteral)enumeration.jjtGetChild(childNo+1);
      //  Require the other tags
      RequiredTags.getTagger().addTags(bundle, "enum", litID.getName(), jdi);
   }


}

