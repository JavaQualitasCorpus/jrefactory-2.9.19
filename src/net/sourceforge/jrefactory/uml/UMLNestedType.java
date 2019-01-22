package net.sourceforge.jrefactory.uml;

import javax.swing.*;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Description of the Class
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLNestedType.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLNestedType extends UMLLine {
   TypeSummary summary;


   /**
    *  Constructor for the UMLNestedType object
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @since            2.9.12
    */
   public UMLNestedType(TypeSummary summary, UMLSettings settings) {
      this.summary = summary;
      setFont(settings.getProtectionFont(false, summary));
      setIcon(settings.getIcon(summary));
      setText(summary.getName());
   }


   /**
    *  Gets the summary attribute of the UMLNestedType object
    *
    * @return    The summary value
    * @since     2.9.12
    */
   public TypeSummary getSummary() {
      return summary;
   }
}
