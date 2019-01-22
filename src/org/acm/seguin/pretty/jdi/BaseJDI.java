/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import java.text.DateFormat;
import java.util.Date;
import net.sourceforge.jrefactory.parser.JavaParser;
import net.sourceforge.jrefactory.parser.JavaParserVisitor;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.JavaDocComponent;
import org.acm.seguin.pretty.JavaDocable;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;
import org.acm.seguin.util.FileSettings;
import org.acm.seguin.util.MissingSettingsException;
import org.acm.seguin.pretty.JavaDocable;
import org.acm.seguin.pretty.JavaDocableImpl;

/**
 *  Holds a class declaration. Contains the list of modifiers for the class and the javadoc comments.
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
abstract class BaseJDI implements JavaDocable {
   protected JavaDocableImpl jdi;
   protected FileSettings bundle;
   
   
   /**
    *  Constructor for the ASTClassDeclaration node.
    *
    * @param  identifier  The id of this node (JJTCLASSDEClARATION).
    */
   public BaseJDI() {
      jdi = new JavaDocableImpl();
      bundle = FileSettings.getRefactoryPrettySettings();
   }


	/**
	 *  Allows you to add a java doc component
	 *
	 *@param  component  the component that can be added
	 */
	public void addJavaDocComponent(JavaDocComponent component) {
		jdi.addJavaDocComponent(component);
	}

}

