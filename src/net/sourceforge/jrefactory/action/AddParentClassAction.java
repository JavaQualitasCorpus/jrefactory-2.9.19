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
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.type.AddAbstractParent;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Description of the Class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: AddParentClassAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class AddParentClassAction extends RefactoringAction {
   /**
    *  Constructor for the AddParentClassAction object
    *
    * @param  init  Description of Parameter
    * @since        2.9.12
    */
   public AddParentClassAction(SelectedFileSet init) {
      super(init);
      initNames();
   }


   /**
    *  Constructor for the AddParentClassAction object
    *
    * @param  initPackage  The package containing the class (and parent class)
    * @param  initTypes    The class (+interfaces) to create a parent of.
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public AddParentClassAction(UMLPackage initPackage, TypeSummary[] initTypes, JPopupMenu initMenu, JMenuItem initItem) {
      this(null);
      currentPackage = initPackage;
      typeArray = initTypes;
      setPopupMenuListener(new AddParentClassListener(initMenu, initItem));
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
      AddParentClassListener apcl = new AddParentClassListener(null, null);
      apcl.actionPerformed(evt);
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Add Parent Class");
      putValue(SHORT_DESCRIPTION, "Add Parent Class");
      putValue(LONG_DESCRIPTION, "Allows the user to add an abstract parent class");
   }


   /**
    *  Creates a dialog box to prompt for the new parent name
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class AddAbstractParentDialog extends ClassNameDialog {

      /**
       *  Constructor for AddAbstractParentDialog
       *
       * @since        2.9.12
       */
      public AddAbstractParentDialog() {
         super(1);
      }


      /**
       *  Gets the typeSummaries attribute of the AddAbstractParentDialog object
       *
       * @return    The typeSummaries value
       * @since     2.9.12
       */
      public TypeSummary[] getTypeSummaries() {
         return typeArray;
      }


      /**
       *  Returns the window title
       *
       * @return    the title
       * @since     2.9.12
       */
      public String getWindowTitle() {
         return "Add an abstract parent";
      }


      /**
       *  Gets the label for the text
       *
       * @return    the text for the label
       * @since     2.9.12
       */
      public String getLabelText() {
         return "Parent class:";
      }


      /**
       *  Adds an abstract parent class to all specified classes.
       *
       * @return    the refactoring
       * @since     2.9.12
       */
      protected Refactoring createRefactoring() {
         //  Create system
         AddAbstractParent aap = RefactoringFactory.get().addParent();
         aap.setParentName(getClassName());

         //  Add the types
         for (int ndx = 0; ndx < typeArray.length; ndx++) {
            aap.addChildClass(typeArray[ndx]);
         }

         return aap;
      }
   }


   /**
    *  Adds an abstract parent listener
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class AddParentClassListener extends DialogViewListener {
      /**
       *  Constructor for the AddParentClassListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public AddParentClassListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  Creates an appropriate dialog to prompt the user for additional input
       *
       * @return    the dialog box
       * @since     2.9.12
       */
      protected JDialog createDialog() {
         return new AddAbstractParentDialog();
      }
   }
}

