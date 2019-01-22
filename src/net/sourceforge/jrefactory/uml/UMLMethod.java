package net.sourceforge.jrefactory.uml;

import javax.swing.*;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.Summary;



/**
 *  Description of the Class
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLMethod.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLMethod extends UMLLine implements HasSummary {
   MethodSummary summary;


   /**
    *  Constructor for the UMLMethod object
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @since            2.9.12
    */
   public UMLMethod(MethodSummary summary, UMLSettings settings) {
      this.summary = summary;
      setFont(settings.getProtectionFont(false, summary));
      setIcon(settings.getIcon(summary));
      setText(summary.toString());
   }


   /**
    *  Gets the summary attribute of the UMLMethod object
    *
    * @return    The summary value
    * @since     2.9.12
    */
   public MethodSummary getSummary() {
      return summary;
   }


   /**
    *  Gets the sourceSummary attribute of the UMLMethod object
    *
    * @return    The sourceSummary value
    * @since     2.9.12
    */
   public Summary getSourceSummary() {
      return summary;
   }
}
