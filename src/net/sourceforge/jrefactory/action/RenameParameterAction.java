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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.awt.CenterDialog;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.method.RenameParameterRefactoring;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Rename a parameter of a method
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: RenameParameterAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class RenameParameterAction extends RefactoringAction {
   /**
    *  Constructor for the RenameParameterAction object
    *
    * @since    2.9.12
    */
   public RenameParameterAction() {
      super(new EmptySelectedFileSet());
      initNames();
   }


   /**
    *  Constructor for the RenameParameterAction object
    *
    * @param  initPackage  Description of Parameter
    * @param  initParam    Description of Parameter
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public RenameParameterAction(UMLPackage initPackage, ParameterSummary initParam, JPopupMenu initMenu, JMenuItem initItem) {
      super(null);
      currentPackage = initPackage;
      paramSummary = initParam;
      setPopupMenuListener(new RenameParameterListener(initMenu, initItem));
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
      return (summary != null) && (summary instanceof MethodSummary) && (((MethodSummary)summary).getParameterCount() > 0);
   }


   /**
    *  Description of the Method
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) {
      methodSummary = (MethodSummary)CurrentSummary.get().getCurrentSummary();
      RenameParameterListener rpl = new RenameParameterListener(null, null);
      rpl.actionPerformed(null);
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Rename Parameter");
      putValue(SHORT_DESCRIPTION, "Rename Parameter");
      putValue(LONG_DESCRIPTION, "Rename a parameter of the method");
   }


   /**
    *  Menu listener that invokes the refactoring dialog for renaming a parameter
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class RenameParameterListener extends DialogViewListener {
      /**
       *  Constructor for the RenameParameterListener object
       *
       * @param  initMenu  Description of Parameter
       * @param  initItem  Description of Parameter
       * @since            2.9.12
       */
      public RenameParameterListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Returned Value
       * @since     2.9.12
       */
      protected JDialog createDialog() {
         return (paramSummary == null) ? new RenameParameterDialog() : new RenameParameterDialog(paramSummary);
      }
   }


   /**
    *  Dialog box that gets input for renaming the parameter
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class RenameParameterDialog extends ClassNameDialog {
      private ParameterSummary param;
      private JComboBox parameterSelection;


      /**
       *  Constructor for the RenameParameterDialog object
       *
       * @param  initParam  Description of Parameter
       * @since             2.9.12
       */
      public RenameParameterDialog(ParameterSummary initParam) {
         super(0);
         param = initParam;
         methodSummary = (MethodSummary)param.getParent();
         if (methodSummary == null) {
            System.out.println("No method specified");
         }

         setTitle(getWindowTitle());
         pack();
         setDefaultName(initParam);
         CenterDialog.center(this, currentPackage);
      }


      /**
       *  Constructor for the RenameParameterDialog object
       *
       * @since        2.9.12
       */
      public RenameParameterDialog() {
         super(1);

         param = null;
         if (methodSummary == null) {
            System.out.println("No method specified");
         }

         GridBagConstraints gbc = new GridBagConstraints();

         JLabel newNameLabel = new JLabel("Parameter:  ");
         gbc.gridx = 1;
         gbc.gridy = 0;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         GridBagLayout gridbag = (GridBagLayout)getContentPane().getLayout();
         gridbag.setConstraints(newNameLabel, gbc);
         getContentPane().add(newNameLabel);

         parameterSelection = new JComboBox();
         Iterator iter = methodSummary.getParameters();
         while (iter.hasNext()) {
            parameterSelection.addItem(iter.next());
         }
         parameterSelection.setEditable(false);

         gbc.gridx = 2;
         gbc.gridy = 0;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gridbag.setConstraints(parameterSelection, gbc);
         getContentPane().add(parameterSelection);

         setTitle(getWindowTitle());

         pack();

         CenterDialog.center(this, currentPackage);
      }


      /**
       *  Gets the LabelText attribute of the RenameParameterDialog object
       *
       * @return    The LabelText value
       * @since     2.9.12
       */
      public String getLabelText() {
         return "New parameter name:";
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Returned Value
       * @since     2.9.12
       */
      protected Refactoring createRefactoring() {
         RenameParameterRefactoring rpr = RefactoringFactory.get().renameParameter();
         rpr.setMethodSummary(methodSummary);
         if (param == null) {
            Object selection = parameterSelection.getSelectedItem();
            rpr.setParameterSummary((ParameterSummary)selection);
         } else {
            rpr.setParameterSummary(param);
         }
         rpr.setNewName(getClassName());
         return rpr;
      }


      /**
       *  Sets the suggested name of this parameter
       *
       * @param  initVariable  The new defaultName value
       * @since                2.9.12
       */
      private void setDefaultName(ParameterSummary initVariable) {
         try {
            HungarianNamer namer = new HungarianNamer();
            setClassName(namer.getDefaultName(initVariable, "a_"));
         } catch (Exception exc) {
            exc.printStackTrace();
            setClassName(initVariable.getName());
         }
      }


      /**
       *  Gets the WindowTitle attribute of the RenameParameterDialog object
       *
       * @return    The WindowTitle value
       * @since     2.9.12
       */
      private String getWindowTitle() {
         return (param == null) ? "Renaming a parameter" : "Renaming the parameter " + param.getName() + " in " + methodSummary.getName();
      }
   }
}

