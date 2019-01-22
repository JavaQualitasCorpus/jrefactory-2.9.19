package net.sourceforge.jrefactory.uml;

import org.acm.seguin.summary.*;



/**
 *  This contains static factory methods for creating the various UML diagram components.
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLFactory.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLFactory {

   /**
    *  Description of the Method
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @return           Description of the Returned Value
    * @since            2.9.12
    */
   public static UMLPackage createPackage(PackageSummary summary, UMLSettings settings) {
      return new UMLPackage(summary, settings);
   }


   /**
    *  Description of the Method
    *
    * @param  pack      Description of Parameter
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @return           Description of the Returned Value
    * @since            2.9.12
    */
   public static UMLType createType(UMLPackage pack, TypeSummary summary, UMLSettings settings) {
      return new UMLType(pack, summary, settings, false);
   }


   /**
    *  Description of the Method
    *
    * @param  pack      Description of Parameter
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @param  foreign   Description of Parameter
    * @return           Description of the Returned Value
    * @since            2.9.12
    */
   public static UMLType createType(UMLPackage pack, TypeSummary summary, UMLSettings settings, boolean foreign) {
      return new UMLType(pack, summary, settings, foreign);
   }


   /**
    *  Description of the Method
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @return           Description of the Returned Value
    * @since            2.9.12
    */
   public static UMLNestedType createNestedType(TypeSummary summary, UMLSettings settings) {
      return new UMLNestedType(summary, settings);
   }


   /**
    *  Description of the Method
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @return           Description of the Returned Value
    * @since            2.9.12
    */
   public static UMLMethod createMethod(MethodSummary summary, UMLSettings settings) {
      return new UMLMethod(summary, settings);
   }


   /**
    *  Description of the Method
    *
    * @param  summary   Description of Parameter
    * @param  settings  Description of Parameter
    * @return           Description of the Returned Value
    * @since            2.9.12
    */
   public static UMLField createField(FieldSummary summary, UMLSettings settings) {
      return new UMLField(summary, settings);
   }
}
