package net.sourceforge.jrefactory.uml;

import java.awt.Point;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeDeclSummary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.GetTypeSummary;



/**
 *  This class contains the UML component containing a Java field (instance variable).
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLField.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLField extends UMLLine implements HasSummary {
   FieldSummary summary;
   private boolean association;


   /**
    *  Constructor for the UMLField object
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @since            2.9.12
    */
   public UMLField(FieldSummary summary, UMLSettings settings) {
      this.summary = summary;
      setFont(settings.getProtectionFont(false, summary));
      setIcon(settings.getIcon(summary));
      setText(summary.getName());
   }


   /**
    *  Transform into an association
    *
    * @param  way  Description of Parameter
    * @since       2.9.12
    */
   public void setAssociation(boolean way) {
      association = way;
      if (association) {
         setText(summary.getName());
      } else {
         setText(summary.toString());
      }

      setSize(getPreferredSize());
   }


   /**
    *  Gets the summary attribute of the UMLField object
    *
    * @return    The summary value
    * @since     2.9.12
    */
   public FieldSummary getSummary() {
      return summary;
   }


   /**
    *  Gets the sourceSummary attribute of the UMLField object
    *
    * @return    The sourceSummary value
    * @since     2.9.12
    */
   public Summary getSourceSummary() {
      return summary;
   }


   /**
    *  Is this object represented as an association
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public boolean isAssociation() {
      return association;
   }


   /**
    *  Is this object represented as an association
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public boolean isConvertable() {
      TypeDeclSummary typeDecl = summary.getTypeDecl();
      if (typeDecl.isPrimitive()) {
         return false;
      }

      TypeSummary typeSummary = GetTypeSummary.query(typeDecl);
      return (typeSummary != null);
   }


   /**
    *  Description of the Method
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public TypeSummary getType() {
      TypeDeclSummary typeDecl = summary.getTypeDecl();
      return GetTypeSummary.query(typeDecl);
   }


   /**
    *  Description of the Method
    *
    * @param  x  Description of Parameter
    * @param  y  Description of Parameter
    * @since     2.9.12
    */
   public void shift(int x, int y) {
      Point pt = getLocation();
      setLocation(Math.max(0, x + pt.x), Math.max(0, y + pt.y));
      repaint();
   }

}
