/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import net.sourceforge.jrefactory.parser.JavaParser;
import net.sourceforge.jrefactory.parser.JavaParserVisitor;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import org.acm.seguin.pretty.JavaDocComponent;
import org.acm.seguin.pretty.JavaDocable;
import org.acm.seguin.pretty.JavaDocableImpl;
import org.acm.seguin.pretty.PrintData;


/**
 *  Holds the package declaration at the beginning of the java file
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class PackageDeclaration implements JavaDocable {
   private JavaDocableImpl jdi = null;
   private ASTPackageDeclaration pack;


   /**
    *  Constructor for the PackageDeclaration JavaDoc creator.
    *
    * @param  pack Create JavaDoc for this node.
    */
   public PackageDeclaration(ASTPackageDeclaration pack) {
      this.pack = pack;
      jdi = new JavaDocableImpl();
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return false;
   }


   /**
    *  Allows you to add a java doc component
    *
    * @param  component  the component that can be added
    */
   public void addJavaDocComponent(JavaDocComponent component) {
      jdi.addJavaDocComponent(component);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, "since");
   }


   /**  Makes sure all the java doc components are present  */
   public void finish() { }

}

