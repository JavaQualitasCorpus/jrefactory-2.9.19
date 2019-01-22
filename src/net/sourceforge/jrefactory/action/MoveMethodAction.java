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

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.awt.CenterDialog;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.method.MoveMethodRefactoring;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Pushes a method into the parent class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: MoveMethodAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class MoveMethodAction extends RefactoringAction {
   /**
    *  Constructor for the MoveMethodAction object
    *
    * @since    2.9.12
    */
   public MoveMethodAction() {
      super(new EmptySelectedFileSet());
      initNames();
   }


   /**
    *  Constructor for the MoveMethodAction object
    *
    * @param  initPackage  Description of Parameter
    * @param  initType     Description of Parameter
    * @param  method       Description of Parameter
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public MoveMethodAction(UMLPackage initPackage,
                           TypeSummary initType,
                           MethodSummary method,
                           JPopupMenu initMenu,
                           JMenuItem initItem) {
      super(null);
      currentPackage = initPackage;
      typeSummary = initType;
      methodSummary = method;
      if (typeSummary == null) {
         typeSummary = (TypeSummary)methodSummary.getParent();
      }
      setPopupMenuListener(new MoveMethodListener(initMenu, initItem));
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
      methodSummary = (MethodSummary)CurrentSummary.get().getCurrentSummary();
      MoveMethodListener listener = new MoveMethodListener(null, null);
      listener.actionPerformed(null);
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Move Method");
      putValue(SHORT_DESCRIPTION, "Move Method");
      putValue(LONG_DESCRIPTION, "Move a method into another class");
   }


   /**
    *  Listener for the move method menu item
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class MoveMethodListener extends DialogViewListener {
      /**
       *  Constructor for the MoveMethodListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public MoveMethodListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  Creates an appropriate dialog to prompt the user for additional input
       *
       * @return    the dialog box
       * @since     2.9.12
       */
      protected JDialog createDialog() {
         return new MoveMethodDialog();
      }
   }


   /**
    *  This dialog box selects which parameter the method is being moved into.
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class MoveMethodDialog extends RefactoringDialog {
      /**
       *  parameter panel
       *
       * @since    2.9.12
       */
      private ParameterPanel params;


      /**
       *  Constructor for the MoveMethodDialog object
       *
       * @since        2.9.12
       */
      public MoveMethodDialog() {
         super();

         getContentPane().setLayout(new BorderLayout());

         params = new ParameterPanel();
         getContentPane().add(params, BorderLayout.NORTH);

         JButton okButton = new JButton("OK");
         getContentPane().add(okButton, BorderLayout.WEST);
         okButton.addActionListener(this);

         JButton cancelButton = new JButton("Cancel");
         getContentPane().add(cancelButton, BorderLayout.EAST);
         cancelButton.addActionListener(this);

         setTitle("Move method " + methodSummary.toString() + "  to:");

         pack();

         CenterDialog.center(this, currentPackage);
      }


      /**
       *  Creates a refactoring to be performed
       *
       * @return    the refactoring
       * @since     2.9.12
       */
      protected Refactoring createRefactoring() {
         MoveMethodRefactoring moveMethod = RefactoringFactory.get().moveMethod();
         moveMethod.setMethod(methodSummary);
         moveMethod.setDestination(params.get());

         return moveMethod;
      }
   }



   /**
    *  Panel of radio buttons containing the parameters
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class ParameterPanel extends JPanel {
      private LinkedList children;


      /**
       *  Constructor for the ParameterPanel object
       *
       * @since        2.9.12
       */
      public ParameterPanel() {
         children = new LinkedList();
         ButtonGroup buttonGroup = new ButtonGroup();

         Iterator iter = methodSummary.getParameters();
         int count = 0;
         while (iter.hasNext()) {
            ParameterSummary next = (ParameterSummary)iter.next();

            ParameterRadioButton tcb = new ParameterRadioButton(next);
            children.add(tcb);
            buttonGroup.add(tcb);
            tcb.setSelected(count == 0);
            count++;
         }

         int columns = count / 10 + 1;
         setLayout(new GridLayout(count / columns + 1, columns));

         iter = children.iterator();
         while (iter.hasNext()) {
            add((JComponent)iter.next());
         }
      }


      /**
       *  Gets the selected parameter
       *
       * @return    The selected parameter
       * @since     2.9.12
       */
      public ParameterSummary get() {
         Iterator iter = children.iterator();
         while (iter.hasNext()) {
            ParameterRadioButton prb = (ParameterRadioButton)iter.next();
            if (prb.isSelected()) {
               return prb.getParameterSummary();
            }
         }

         return null;
      }
   }


   /**
    *  This radio button holds a parameter and sets the name of the radio button automatically. You can ask for the
    *  parameter summary.
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class ParameterRadioButton extends JRadioButton {
      private ParameterSummary summary;


      /**
       *  Constructor for the ParameterRadioButton object
       *
       * @param  init  the parameter summary
       * @since        2.9.12
       */
      public ParameterRadioButton(ParameterSummary init) {
         summary = init;
         setText(summary.getName() + " (" + summary.getType() + ")");
      }


      /**
       *  Gets the ParameterSummary
       *
       * @return    The ParameterSummary
       * @since     2.9.12
       */
      public ParameterSummary getParameterSummary() {
         return summary;
      }
   }

}

