package net.sourceforge.jrefactory.uml;

import java.awt.*;
import javax.swing.*;
import net.sourceforge.jrefactory.ast.ModifierHolder;
import org.acm.seguin.summary.*;



/**
 *  Description of the Class
 *
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLSettings.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLSettings {

   private boolean asLine = false;

   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected static Color[] protectionColors = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected static Font defaultFont = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected static Font staticFont = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected static Font abstractFont = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected static Font titleFont = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected static Font abstractTitleFont = null;
   public static ImageIcon packageIcon;
   public static ImageIcon emptyPackageIcon;
   public static ImageIcon classPublicIcon;
   private static ImageIcon classPrivateIcon;
   private static ImageIcon classPackageIcon;
   private static ImageIcon classProtectedIcon;
   public static ImageIcon interfacePublicIcon;
   private static ImageIcon interfacePrivateIcon;
   private static ImageIcon interfacePackageIcon;
   private static ImageIcon interfaceProtectedIcon;
   private static ImageIcon constructorPublicIcon;
   private static ImageIcon constructorPrivateIcon;
   private static ImageIcon constructorPackageIcon;
   private static ImageIcon constructorProtectedIcon;
   private static ImageIcon methodPublicIcon;
   private static ImageIcon methodPrivateIcon;
   private static ImageIcon methodPackageIcon;
   private static ImageIcon methodProtectedIcon;
   private static ImageIcon methodStPublicIcon;
   private static ImageIcon methodStPrivateIcon;
   private static ImageIcon methodStPackageIcon;
   private static ImageIcon methodStProtectedIcon;
   private static ImageIcon fieldPublicIcon;
   private static ImageIcon fieldPrivateIcon;
   private static ImageIcon fieldPackageIcon;
   private static ImageIcon fieldProtectedIcon;
   private static ImageIcon fieldStPublicIcon;
   private static ImageIcon fieldStPrivateIcon;
   private static ImageIcon fieldStPackageIcon;
   private static ImageIcon fieldStProtectedIcon;
   private static ImageIcon sourceFileIcon;
   private static Object value;
   private static Color[] typeBGColours;

   private static boolean initialised = false;


   /**
    *  Constructor for the UMLSettings object
    *
    * @since    2.9.12
    */
   public UMLSettings() { }


   /**
    *  Copy constructor for the UMLSettings object. Creates a copy of the UMLSettings object parameter
    *
    * @param  copyFrom  Object to copy.
    * @since            2.9.12
    */
   public UMLSettings(UMLSettings copyFrom) { }


   /**
    *  Sets the asLine attribute of the UMLSettings object
    *
    * @param  asLine  The new asLine value
    * @since          2.9.12
    */
   public void setAsLine(boolean asLine) {
      this.asLine = asLine;
   }


   /**
    *  Gets the asLine attribute of the UMLSettings object
    *
    * @return    The asLine value
    * @since     2.9.12
    */
   public boolean getAsLine() {
      return asLine;
   }



   /**
    *  Get the font appropriate for the level of protection
    *
    * @param  title      is this a title
    * @param  modifiers  the modifiers
    * @return            Description of the Returned Value
    * @since             2.9.12
    */
   public static Font getProtectionFont(boolean title, ModifierHolder modifiers) {
      initData();

      if (modifiers == null) {
         return defaultFont;
      }

      if (modifiers.isAbstract()) {
         if (title) {
            return abstractTitleFont;
         } else {
            return abstractFont;
         }
      } else if (modifiers.isStatic()) {
         return staticFont;
      } else if (title) {
         return titleFont;
      } else {
         return defaultFont;
      }
   }


   /**
    *  Get the color associated with a level of protection
    *
    * @param  level  the level that we need to know
    * @return        the color
    * @since         2.9.12
    */
   public static Color getProtectionColor(int level) {
      if (protectionColors == null) {
         //  Initialize
         protectionColors = new Color[5];

         protectionColors[0] = Color.green;
         protectionColors[1] = Color.blue;
         protectionColors[2] = Color.yellow;
         protectionColors[3] = Color.orange;
         protectionColors[4] = Color.red;
      }

      return protectionColors[level];
   }


   /**
    *  Gets the icon attribute of the UMLSettings class
    *
    * @param  summary  Description of Parameter
    * @return          The icon value
    * @since           2.9.12
    */
   public static ImageIcon getIcon(FieldSummary summary) {
      if (summary.isStatic()) {
         if (summary.isPublic()) {
            return fieldStPublicIcon;
         } else if (summary.isPrivate()) {
            return fieldStPrivateIcon;
         } else if (summary.isProtected()) {
            return fieldStProtectedIcon;
         } else {
            return fieldStPackageIcon;
         }
      } else {
         if (summary.isPublic()) {
            return fieldPublicIcon;
         } else if (summary.isPrivate()) {
            return fieldPrivateIcon;
         } else if (summary.isProtected()) {
            return fieldProtectedIcon;
         } else {
            return fieldPackageIcon;
         }
      }
   }


   /**
    *  Gets the icon attribute of the UMLSettings class
    *
    * @param  summary  Description of Parameter
    * @return          The icon value
    * @since           2.9.12
    */
   public static ImageIcon getIcon(MethodSummary summary) {
      if (summary.isInitializer()) {
         return constructorPublicIcon;
      }
      if (summary.isStatic()) {
         if (summary.isPublic()) {
            return methodStPublicIcon;
         } else if (summary.isPrivate()) {
            return methodStPrivateIcon;
         } else if (summary.isProtected()) {
            return methodStProtectedIcon;
         } else {
            return methodStPackageIcon;
         }
      } else {
         if (summary.isPublic()) {
            return methodPublicIcon;
         } else if (summary.isPrivate()) {
            return methodPrivateIcon;
         } else if (summary.isProtected()) {
            return methodProtectedIcon;
         } else {
            return methodPackageIcon;
         }
      }
   }


   /**
    *  Gets the icon attribute of the UMLSettings class
    *
    * @param  summary  Description of Parameter
    * @return          The icon value
    * @since           2.9.12
    */
   public static ImageIcon getIcon(TypeSummary summary) {
      if (summary.isInterface()) {
         if (summary.isPublic()) {
            return interfacePublicIcon;
         } else if (summary.isPrivate()) {
            return interfacePrivateIcon;
         } else if (summary.isProtected()) {
            return interfaceProtectedIcon;
         } else {
            return interfacePackageIcon;
         }
      } else {
         if (summary.isPublic()) {
            return classPublicIcon;
         } else if (summary.isPrivate()) {
            return classPrivateIcon;
         } else if (summary.isProtected()) {
            return classProtectedIcon;
         } else {
            return classPackageIcon;
         }
      }
   }


   /**
    *  Return the background color
    *
    * @param  state  Description of Parameter
    * @return        the background color
    * @since         2.9.12
    */
   public static Color getBackgroundColor(int state) {
      return typeBGColours[state];
   }


   /**
    *  Initialize the fonts
    *
    * @since    2.9.12
    */
   public static void initData() {
      if (staticFont != null) {
         return;
      }
      typeBGColours = new Color[]{Color.white,
               new Color(250, 255, 220),
               new Color(200, 200, 255),
               new Color(220, 255, 220)};
      defaultFont = new Font("Serif", Font.PLAIN, 12);
      staticFont = new Font("Serif", Font.BOLD, 12);
      abstractFont = new Font("Serif", Font.ITALIC, 12);
      titleFont = new Font("Serif", Font.PLAIN, 16);
      abstractTitleFont = new Font("Serif", Font.ITALIC, 16);
      ClassLoader classLoader = UMLSettings.class.getClassLoader();
      packageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/package.gif"));
      emptyPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/emptyPackage.gif"));
      classPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classPublic.gif"));
      classPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classPrivate.gif"));
      classPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classPackage.gif"));
      classProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classProtected.gif"));
      interfacePublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfacePublic.gif"));
      interfacePrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfacePrivate.gif"));
      interfacePackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfacePackage.gif"));
      interfaceProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfaceProtected.gif"));
      constructorPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/constructorPublic.gif"));
      methodPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodPublic.gif"));
      methodPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodPrivate.gif"));
      methodPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodPackage.gif"));
      methodProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodProtected.gif"));
      methodStPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStPublic.gif"));
      methodStPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStPrivate.gif"));
      methodStPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStPackage.gif"));
      methodStProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStProtected.gif"));
      fieldPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variablePublic.gif"));
      fieldPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variablePrivate.gif"));
      fieldPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variablePackage.gif"));
      fieldProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableProtected.gif"));
      fieldStPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStPublic.gif"));
      fieldStPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStPrivate.gif"));
      fieldStPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStPackage.gif"));
      fieldStProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStProtected.gif"));
      sourceFileIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/sourcefile.gif"));
   }
}
