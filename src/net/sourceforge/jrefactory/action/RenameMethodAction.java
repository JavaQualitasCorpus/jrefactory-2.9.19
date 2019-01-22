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
import org.acm.seguin.refactor.method.RenameMethodRefactoring;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Pushes a method into the parent class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: RenameMethodAction.java,v 1.3 2004/04/30 17:11:45 mikeatkinson Exp $
 */
public class RenameMethodAction extends RefactoringAction {
   /**
    *  Constructor for the RenameMethodAction object
    *
    * @since    2.9.12
    */
   public RenameMethodAction() {
      super(new EmptySelectedFileSet());
      initNames();
   }


   /**
    *  Constructor for the RenameMethodAction object
    *
    * @param  initPackage  Description of Parameter
    * @param  initType     Description of Parameter
    * @param  method       Description of Parameter
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public RenameMethodAction(UMLPackage initPackage,
                             TypeSummary initType,
                             MethodSummary method,
                             JPopupMenu initMenu,
                             JMenuItem initItem) {
      this();
      currentPackage = initPackage;
      typeSummary = initType;
      methodSummary = method;
      if (typeSummary == null) {
         typeSummary = (TypeSummary)methodSummary.getParent();
      }
      setPopupMenuListener(new RenameMethodListener(initMenu, initItem));
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      Summary summary = CurrentSummary.get().getCurrentSummary();
      return (summary != null) && (summary instanceof MethodSummary);
   }


   /**
    *  Description of the Method
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) {
      System.err.println("RenameMethodListener: activateListener("+typeSummaryArray+","+evt+")");
      CurrentSummary.get().lineNo = evt.getID();
      methodSummary = (MethodSummary)CurrentSummary.get().getCurrentSummary();
      RenameMethodListener listener = new RenameMethodListener(null, null);
      listener.actionPerformed(null);
      CurrentSummary.get().lineNo = -1;
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Rename Method");
      putValue(SHORT_DESCRIPTION, "Rename Method (fails Unit tests)");
      putValue(LONG_DESCRIPTION, "Rename a method but keep it in the same the class (this should not be used as it fails Unit tests)");
   }


   /**
    *  Listener for the move method menu item
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class RenameMethodListener extends DialogViewListener {
      /**
       *  Constructor for the MoveMethodListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public RenameMethodListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  As this refactoring fails its unit tests, confirm that the user wants to continue.
       *
       * @param  evt  the action event
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         System.err.println("RenameMethodListener: actionPerformed("+evt+")");
         if (failsUnitTests(currentPackage, "Rename Method")) {
            super.actionPerformed(evt);
         }
      }


      /**
       *  Creates an appropriate dialog to prompt the user for additional input
       *
       * @return    the dialog box
       * @since     2.9.12
       */
      protected JDialog createDialog() {
         return new RenameMethodDialog();
      }
   }


   /**
    *  This dialog box selects which parameter the method is being moved into.
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class RenameMethodDialog extends ClassNameDialog {
      /**
       *  Constructor for the MoveMethodDialog object
       *
       * @since        2.9.12
       */
      public RenameMethodDialog() {
         super(1);
         setTitle(getWindowTitle());
         setDefaultName(methodSummary);
      }


      /**
       *  Gets the label for the text
       *
       * @return    the text for the label
       * @since     2.9.12
       */
      public String getLabelText() {
         return "New Name:";
      }


      /**
       *  Creates a refactoring to be performed
       *
       * @return    the refactoring
       * @since     2.9.12
       */
      protected Refactoring createRefactoring() {
         RenameMethodRefactoring renameMethod = RefactoringFactory.get().renameMethod();
         renameMethod.setMethod(methodSummary);
         renameMethod.setNewMethodName(getClassName());

         return renameMethod;
      }


      /**
       *  Rename the type summary that has been influenced
       *
       * @since    empty
       */
      protected void updateSummaries() {
         methodSummary.setName(getClassName());
      }


      /**
       *  Sets the suggested name of this parameter
       *
       * @param  method  The new defaultName value
       * @since          empty
       */
      private void setDefaultName(MethodSummary method) {
         try {
            setClassName(method.getName());
         } catch (Exception exc) {
            exc.printStackTrace();
         }
      }


      /**
       *  Returns the window title
       *
       * @return    the title
       * @since     2.9.12
       */
      private String getWindowTitle() {
         return (methodSummary == null) ? "Rename method" : "Rename method: " + methodSummary.getName();
      }
   }
}

