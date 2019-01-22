/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2001 JRefactory.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "JRefactory" must not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission. For written permission, please contact seguin@acm.org.
 *
 * 5. Products derived from this software may not be called "JRefactory",
 *    nor may "JRefactory" appear in their name, without prior written
 *    permission of Chris Seguin.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of JRefactory.  For more information on
 * JRefactory, please see
 * <http://www.sourceforge.org/projects/jrefactory>.
 */
package net.sourceforge.jrefactory.action;

import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.type.ExtractInterfaceRefactoring;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.GetPackageSummary;


/**
 *  Description of the Class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: ExtractInterfaceAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class ExtractInterfaceAction extends RefactoringAction {
   /**
    *  Constructor for the ExtractInterfaceAction object
    *
    * @param  init  Description of Parameter
    * @since        2.9.12
    */
   public ExtractInterfaceAction(SelectedFileSet init) {
      super(init);
      initNames();
   }


   /**
    *  Constructor for the ExtractInterfaceAction object
    *
    * @param  initPackage  Description of Parameter
    * @param  initTypes    Description of Parameter
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public ExtractInterfaceAction(UMLPackage initPackage, TypeSummary[] initTypes, JPopupMenu initMenu, JMenuItem initItem) {
      super(null);
      currentPackage = initPackage;
      typeArray = initTypes;
      setPopupMenuListener(new ExtractInterfaceListener(initMenu, initItem));
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      return isAllJava();
   }


   /**
    *  Description of the Method
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) {
      typeArray = typeSummaryArray;
      ExtractInterfaceListener eil = new ExtractInterfaceListener(null, null);
      eil.actionPerformed(evt);
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Extract Interface");
      putValue(SHORT_DESCRIPTION, "Extract Interface");
      putValue(LONG_DESCRIPTION, "Allows the user to extract an interface");
   }



   /**
    *  Description of the Class
    *
    * @author     Grant Watson
    * @since      2.9.12
    * @created    November 30, 2000
    */
   public class ExtractInterfaceListener extends DialogViewListener {
      /**
       *  Constructor for the ExtractInterfaceListener object
       *
       * @param  initMenu  Description of Parameter
       * @param  initItem  Description of Parameter
       * @since            2.9.12
       */
      public ExtractInterfaceListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  Creates an appropriate dialog to prompt the user for additional input
       *
       * @return    the dialog box
       * @since     2.9.12
       */
      protected JDialog createDialog() {
         return new ExtractInterfaceDialog();
      }
   }


   /**
    *  Extracts an interface from a class
    *
    * @author     Grant Watson
    * @since      2.9.12
    * @created    November 30, 2000
    */
   public class ExtractInterfaceDialog extends ClassNameDialog {
      private JComboBox packageNameBox;


      /**
       *  Constructor for ExtractInterfaceDialog
       *
       * @since        2.9.12
       */
      public ExtractInterfaceDialog() {
         super(2);

         PackageList packageList = new PackageList();
         packageNameBox = packageList.add(this);

         String name;
         if (currentPackage == null) {
            name = GetPackageSummary.query(typeArray[0]).getName();
         } else {
            name = currentPackage.getSummary().getName();
         }
         packageNameBox.setSelectedItem(name);

         pack();

         org.acm.seguin.awt.CenterDialog.center(this, currentPackage);
      }


      /**
       *  Returns the window title
       *
       * @return    the title
       * @since     2.9.12
       */
      public String getWindowTitle() {
         return "Extract Interface";
      }


      /**
       *  Gets the label for the text
       *
       * @return    the text for the label
       * @since     2.9.12
       */
      public String getLabelText() {
         return "Interface:";
      }


      /**
       *  Extracts an interface from all specified classes.
       *
       * @return    the refactoring
       * @since     2.9.12
       */
      protected Refactoring createRefactoring() {
         //  Create system
         ExtractInterfaceRefactoring eir = RefactoringFactory.get().extractInterface();

         String interfaceName = getClassName();
         if (interfaceName.indexOf(".") > 0) {
            eir.setInterfaceName(interfaceName);
         } else {
            String packageName = (String)packageNameBox.getSelectedItem();
            if (packageName.indexOf("<") == -1) {
               eir.setInterfaceName(packageName + "." + interfaceName);
            } else {
               eir.setInterfaceName(interfaceName);
            }
         }

         //  Add the types
         for (int ndx = 0; ndx < typeArray.length; ndx++) {
            eir.addImplementingClass(typeArray[ndx]);
         }

         return eir;
      }
   }
}

