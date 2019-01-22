/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.MethodAnalyzer;
import org.acm.seguin.pretty.ai.RequiredTags;


/**
 *  Holds a method declaration in a class
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class MethodDeclaration extends BaseJDI {
   ASTMethodDeclaration method;

   /**
    *  Constructor for the MethodDeclaration JavaDoc creator.
    *
    * @param  method Create JavaDoc for this node.
    */
   public MethodDeclaration(ASTMethodDeclaration method) {
      super();
      this.method = method;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("method", method);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getString("method.tags"));
   }


   /**
    *  Makes sure all the java doc components are present. For methods and constructors we need to do more work -
    *  checking parameters, return types, and exceptions.
    */
   public void finish() {
      finish("");
   }


   /**
    *  Makes sure all the java doc components are present. For methods and constructors we need to do more work -
    *  checking parameters, return types, and exceptions.
    *
    * @param  className  Description of Parameter
    */
   public void finish(String className) {
      MethodAnalyzer mai = new MethodAnalyzer(method, jdi);
      mai.finish(className);

      //  Require the other tags
      //  skip possible attributes
      int childNo = method.skipAnnotationsAndTypeParameters();
      ASTMethodDeclarator mthd = (ASTMethodDeclarator)method.jjtGetChild(childNo + 1);
      RequiredTags.getTagger().addTags(bundle, "method", mthd.getName(), jdi);
   }

}

