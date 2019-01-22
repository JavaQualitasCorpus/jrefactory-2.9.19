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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import net.sourceforge.jrefactory.uml.PopupMenuListener;
import net.sourceforge.jrefactory.uml.UMLPackage;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;
import org.acm.seguin.awt.CenterDialog;
import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.VariableSummary;
import org.acm.seguin.summary.query.ChildClassSearcher;



/**
 *  Shares the commonality between actions that perform refactorings from JBuilder's IDE
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: RefactoringAction.java,v 1.2 2003/12/04 13:11:38 mikeatkinson Exp $
 */
abstract class RefactoringAction extends GenericAction {
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected PopupMenuListener listener = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected UMLPackage currentPackage = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected TypeSummary typeSummary = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected MethodSummary methodSummary = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected TypeSummary[] typeArray = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected FieldSummary fieldSummary = null;
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected ParameterSummary paramSummary = null;
   private SelectedFileSet selectedFileSet = null;


   /**
    *  Constructor for the RefactoringAction object
    *
    * @param  init  Description of Parameter
    * @since        2.9.12
    */
   public RefactoringAction(SelectedFileSet init) {
      super();
      System.out.println("RefactoringAction(SelectedFileSet)");
      selectedFileSet = init;
   }


   /**
    *  Sets the upMenu attribute of the RefactoringAction object
    *
    * @param  listener  The new upMenu value
    * @since            2.9.12
    */
   public void setPopupMenuListener(PopupMenuListener listener) {
      System.out.println("RefactoringAction.setPopupMenuListener()");
      this.listener = listener;
      if (listener != null && listener.getMenuItem() != null) {
         listener.getMenuItem().addMouseListener(listener);
      }
   }


   /**
    *  The action to be performed
    *
    * @param  evt  the triggering event
    * @since       2.9.12
    */
   public void actionPerformed(ActionEvent evt) {
      if (listener != null) {
         listener.actionPerformed(evt);
      } else {
         System.out.println("RefactoringAction.actionPerformed()");
         updateMetaData();
         System.out.println("RefactoringAction.actionPerformed() - 2");
         TypeSummary[] typeSummaryArray = getTypeSummaryArray();
         System.out.println("RefactoringAction.actionPerformed() - 3");
         activateListener(typeSummaryArray, evt);
         System.out.println("RefactoringAction.actionPerformed() - 4");

         CurrentSummary.get().reset();
         System.out.println("RefactoringAction.actionPerformed() - 5");
      }
   }


   /**
    *  Gets the AllJava attribute of the AddParentClassAction object
    *
    * @return    The AllJava value
    * @since     2.9.12
    */
   protected boolean isAllJava() {
      System.out.println("RefactoringAction.isAllJava()");
      if (selectedFileSet == null) {
         return true;
      } else {
         return selectedFileSet.isAllJava();
      }
   }


   /**
    *  Gets the SingleJavaFile attribute of the AddChildClassAction object
    *
    * @return    The SingleJavaFile value
    * @since     2.9.12
    */
   protected boolean isSingleJavaFile() {
      System.out.println("RefactoringAction.isSingleJavaFile()");
      if (selectedFileSet == null) {
         return true;
      } else {
         return selectedFileSet.isSingleJavaFile();
      }
   }


   /**
    *  The listener to activate with the specified types
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected abstract void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt);


   /**
    *  Reloads all the metadata before attempting to perform a refactoring.
    *
    * @since    2.9.12
    */
   protected void updateMetaData() {
      System.out.println("RefactoringAction.updateMetaData()");
      System.out.flush();
      CurrentSummary.get().updateMetaData();
   }


   protected boolean failsUnitTests(java.awt.Component parent, String action) {
		int iReturn = JOptionPane.showConfirmDialog(parent,
                                               action + " fails its unit tests, continuing\n" 
                                               + "may cause incorrect Java code to be generated\n",
                                               "do you want to continue?",
                                               JOptionPane.YES_NO_OPTION);
		return (iReturn == JOptionPane.YES_OPTION);
   }


   /**
    *  Gets the TypeSummaryArray attribute of the JBuilderRefactoringAction object
    *
    * @return    The TypeSummaryArray value
    * @since     2.9.12
    */
   private TypeSummary[] getTypeSummaryArray() {
      System.out.println("RefactoringAction.getTypeSummaryArray()");
      if (selectedFileSet == null) {
         if (typeArray != null) {
            return typeArray;
         } else if (typeSummary != null) {
            return new TypeSummary[]{typeSummary};
         } else if (fieldSummary != null) {
            typeSummary = (TypeSummary)fieldSummary.getParent();
            return new TypeSummary[]{typeSummary};
         } else if (methodSummary != null) {
            typeSummary = (TypeSummary)methodSummary.getParent();
            return new TypeSummary[]{typeSummary};
         } else {
            return new TypeSummary[0];
         }
      } else {
         return selectedFileSet.getTypeSummaryArray();
      }
   }



   /**
    *  Names the variable based on hungarian notation
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    July 16, 2002
    */
   public class HungarianNamer {
      /**
       *  Sets the suggested name of this parameter
       *
       * @param  initVariable  The new defaultName value
       * @param  prefix        Description of the Parameter
       * @return               The defaultName value
       * @since                2.9.12
       */
      public String getDefaultName(VariableSummary initVariable, String prefix) {
         String name = initVariable.getName();
         if ((name.length() > 1) && (name.charAt(1) == '_')) {
            return name;
         }

         if (isAllCaps(name)) {
            return name;
         }

         StringBuffer buffer = new StringBuffer(prefix);
         String type = initVariable.getType();
         if (type.equals("String")) {
            buffer.append("sz");
         } else {
            useCapitalLettersFromType(type, buffer);
         }

         if (buffer.length() == 2) {
            buffer.append(type.charAt(0));
         } else if (buffer.length() == 3) {
            insureMinimumLettersInTypeCode(buffer, type);
         }

         int first = 0;
         if (name.charAt(0) == '_') {
            first++;
         }

         buffer.append(Character.toUpperCase(name.charAt(first)));
         if (name.length() > first + 1) {
            buffer.append(name.substring(first + 1));
         }

         return buffer.toString();
      }


      /**
       *  Gets the allCaps attribute of the HungarianNamer object
       *
       * @param  name  Description of the Parameter
       * @return       The allCaps value
       * @since        2.9.12
       */
      private boolean isAllCaps(String name) {
         for (int ndx = 0; ndx < name.length(); ndx++) {
            char ch = name.charAt(ndx);
            if (ch == '_') {
               //  OK
            } else if (Character.isUpperCase(ch)) {
               //  OK
            } else {
               return false;
            }
         }

         return true;
      }


      /**
       *  Determines if the character is a vowel (a, e, i, o, or u)
       *
       * @param  ch  Description of the Parameter
       * @return     Description of the Return Value
       * @since      2.9.12
       */
      private boolean isVowel(char ch) {
         ch = Character.toLowerCase(ch);
         return (ch == 'a') || (ch == 'e') || (ch == 'i') || (ch == 'o') || (ch == 'u');
      }


      /**
       *  Insures that we have the appropriate number of characters
       *
       * @param  buffer  Description of the Parameter
       * @param  type    Description of the Parameter
       * @since          2.9.12
       */
      private void insureMinimumLettersInTypeCode(StringBuffer buffer, String type) {
         buffer.append(type.charAt(1));
         int ndx = 2;
         char ch = type.charAt(ndx);
         while (isVowel(ch)) {
            buffer.append(ch);
            ndx++;
            ch = type.charAt(ndx);
         }
         buffer.append(ch);
      }


      /**
       *  Selects the capital letters from the type
       *
       * @param  type    Description of the Parameter
       * @param  buffer  Description of the Parameter
       * @since          2.9.12
       */
      private void useCapitalLettersFromType(String type, StringBuffer buffer) {
         for (int ndx = 0; ndx < type.length(); ndx++) {
            char ch = type.charAt(ndx);
            if (Character.isUpperCase(ch)) {
               buffer.append(Character.toLowerCase(ch));
            }
         }
      }
   }



   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   abstract class NoInputRefactoringListener extends PopupMenuListener {

      /**
       *  Constructor for the NoInputRefactoringListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public NoInputRefactoringListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  A menu item has been selected, display the dialog box
       *
       * @param  evt  the action event
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         super.actionPerformed(evt);
         runRefactoring();
      }


      /**
       *  Creates a refactoring to be performed
       *
       * @return    the refactoring
       * @since     2.9.12
       */
      protected abstract Refactoring createRefactoring();


      /**
       *  Do any necessary updates to the summaries after the refactoring is complete
       *
       * @since    2.9.12
       */
      protected void updateSummaries() { }


      /**
       *  Adds an abstract parent class to all specified classes.
       *
       * @since    2.9.12
       */
      private void runRefactoring() {
         Refactoring refactoring = createRefactoring();

         //  Update the code
         try {
            refactoring.run();
         } catch (RefactoringException re) {
            JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception",
                     JOptionPane.ERROR_MESSAGE);
         }

         updateSummaries();

         //  Update the GUIs
         ReloaderSingleton.reload();
      }
   }



   /**
    *  Creates a listener that will view a dialog box and do whatever the dialog box says.
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   abstract class DialogViewListener extends PopupMenuListener {
      /**
       *  Constructor for the DialogViewListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public DialogViewListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  A menu item has been selected, display the dialog box
       *
       * @param  evt  the action event
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         super.actionPerformed(evt);
         createDialog().show();
      }


      /**
       *  Creates an appropriate dialog to prompt the user for additional input
       *
       * @return    the dialog box
       * @since     2.9.12
       */
      protected abstract JDialog createDialog();
   }


   /**
    *  Prompts the user for a class name. The class name can then be used to rename a class, add an abstract parent, or
    *  add a child.
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   abstract class ClassNameDialog extends RefactoringDialog {
      private JTextField newName;


      /**
       *  Constructor for ClassNameDialog
       *
       * @param  startRow  Description of Parameter
       * @since            2.9.12
       */
      public ClassNameDialog(int startRow) {
         super();
         GridBagLayout gridbag = new GridBagLayout();
         GridBagConstraints gbc = new GridBagConstraints();
         getContentPane().setLayout(gridbag);
         setSize(310, 120);

         //  Add components
         JLabel newNameLabel = new JLabel(getLabelText());
         gbc.gridx = 1;
         gbc.gridy = startRow;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gridbag.setConstraints(newNameLabel, gbc);
         getContentPane().add(newNameLabel);

         newName = new JTextField();
         newName.setColumns(25);
         gbc.gridx = 2;
         gbc.gridy = startRow;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gridbag.setConstraints(newName, gbc);
         getContentPane().add(newName);

         JButton okButton = new JButton("OK");
         gbc.gridx = 2;
         gbc.gridy = startRow + 1;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.fill = GridBagConstraints.NONE;
         gridbag.setConstraints(okButton, gbc);
         okButton.addActionListener(this);
         getContentPane().add(okButton);

         JButton cancelButton = new JButton("Cancel");
         gbc.gridx = 3;
         gbc.gridy = startRow + 1;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gridbag.setConstraints(cancelButton, gbc);
         cancelButton.addActionListener(this);
         getContentPane().add(cancelButton);

         pack();

         CenterDialog.center(this, currentPackage);
      }


      /**
       *  Gets the label for the text
       *
       * @return    the text for the label
       * @since     2.9.12
       */
      public abstract String getLabelText();


      /**
       *  Sets the className attribute of the ClassNameDialog object
       *
       * @param  name  The new className value
       * @since        2.9.12
       */
      protected void setClassName(String name) {
         newName.setText(name);
         newName.setSelectionStart(0);
         newName.setSelectionStart(name.length());
      }


      /**
       *  Gets the ClassName attribute of the ClassNameDialog object Gets the ClassName attribute of the ClassNameDialog
       *  object
       *
       * @return    The ClassName value
       * @since     2.9.12
       */
      protected String getClassName() {
         return newName.getText();
      }
   }


   /**
    *  Dialog box that runs a refactoring
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   abstract class RefactoringDialog extends JDialog implements ActionListener {
      /**
       *  Constructor for the RefactoringDialog object
       *
       * @since        2.9.12
       */
      public RefactoringDialog() {
         super(IDEPlugin.getEditorFrame());
      }


      /**
       *  Constructor for the RefactoringDialog object
       *
       * @param  frame  Description of the Parameter
       * @since         2.9.12
       */
      public RefactoringDialog(JFrame frame) {
         super(frame);
      }


      /**
       *  Respond to a button press
       *
       * @param  evt  The action event
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         if (evt.getActionCommand().equals("OK")) {
            dispose();
            runRefactoring();
         } else if (evt.getActionCommand().equals("Cancel")) {
            dispose();
         }

         if (currentPackage != null) {
            currentPackage.repaint();
         }
      }


      /**
       *  Returns the current UML package
       *
       * @return    the package
       * @since     2.9.12
       */
      protected UMLPackage getUMLPackage() {
         return currentPackage;
      }


      /**
       *  Creates a refactoring to be performed
       *
       * @return    the refactoring
       * @since     2.9.12
       */
      protected abstract Refactoring createRefactoring();


      /**
       *  Do any necessary updates to the summaries after the refactoring is complete
       *
       * @since    2.9.12
       */
      protected void updateSummaries() { }


      /**
       *  Follows up the refactoring by updating the class diagrams
       *
       * @param  refactoring  Description of the Parameter
       * @since               2.9.12
       */
      protected void followup(Refactoring refactoring) {
         updateSummaries();

         //  Update the GUIs
         ReloaderSingleton.reload();
      }


      /**
       *  Adds an abstract parent class to all specified classes.
       *
       * @since    2.9.12
       */
      private void runRefactoring() {
         Refactoring refactoring = createRefactoring();

         //  Update the code
         try {
            refactoring.run();
         } catch (RefactoringException re) {
            JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception",
                     JOptionPane.ERROR_MESSAGE);
         } catch (Throwable thrown) {
            ExceptionPrinter.print(thrown, true);
         }

         followup(refactoring);
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   class PackageList {
      /**
       *  Adds a label and the combo box to the designated dialog
       *
       * @param  dialog  the dialog window
       * @return         the combo box that was added
       * @since          2.9.12
       */
      public JComboBox add(JDialog dialog) {
         GridBagConstraints gbc = new GridBagConstraints();

         JLabel packageLabel = new JLabel("Package:");
         gbc.gridx = 1;
         gbc.gridy = 1;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.gridwidth = 1;
         dialog.getContentPane().add(packageLabel, gbc);

         JComboBox packageName = new JComboBox();
         packageName.setEditable(true);
         gbc.gridx = 2;
         gbc.gridy = 1;
         gbc.gridheight = 1;
         gbc.gridwidth = 2;
         dialog.getContentPane().add(packageName, gbc);

         addPackages(packageName);

         JPanel blank = new JPanel();
         gbc.gridx = 1;
         gbc.gridy = 4;
         gbc.gridwidth = 3;
         Dimension blankDim = new Dimension(50, packageName.getPreferredSize().height * 5);
         blank.setMinimumSize(blankDim);
         blank.setPreferredSize(blankDim);
         dialog.getContentPane().add(blank, gbc);

         return packageName;
      }


      /**
       *  Fills in the combo box with the names of the packages
       *
       * @param  comboBox  the combo box to fill in
       * @since            2.9.12
       */
      private void addPackages(JComboBox comboBox) {
         //  Add the package names
         Iterator iter = PackageSummary.getAllPackages();
         TreeSet set = new TreeSet();
         while (iter.hasNext()) {
            PackageSummary next = (PackageSummary)iter.next();
            set.add(next.toString());
         }

         iter = set.iterator();
         while (iter.hasNext()) {
            comboBox.addItem(iter.next().toString());
         }
      }
   }



   /**
    *  Holds a type summary in addition to being a checkbox
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class TypeCheckbox extends JCheckBox {
      private TypeSummary type;


      /**
       *  Constructor for the TypeCheckbox object
       *
       * @param  init  Description of Parameter
       * @since        2.9.12
       */
      public TypeCheckbox(TypeSummary init) {
         super(" ");

         type = init;

         setText(getFullName());
         setSize(getPreferredSize());
         setSelected(true);
      }


      /**
       *  Gets the TypeSummary attribute of the TypeCheckbox object
       *
       * @return    The TypeSummary value
       * @since     2.9.12
       */
      public TypeSummary getTypeSummary() {
         return type;
      }


      /**
       *  Gets the FullName attribute of the TypeCheckbox object
       *
       * @return    The FullName value
       * @since     2.9.12
       */
      String getFullName() {
         StringBuffer buf = new StringBuffer(type.getName());
         Summary current = type.getParent();

         while (current != null) {
            if (current instanceof TypeSummary) {
               buf.insert(0, ".");
               buf.insert(0, ((TypeSummary)current).getName());
            } else if (current instanceof PackageSummary) {
               String temp = ((PackageSummary)current).getName();

               if ((temp != null) && (temp.length() > 0)) {
                  buf.insert(0, ".");
                  buf.insert(0, temp);
               }
            }
            current = current.getParent();
         }

         return buf.toString();
      }
   }



   /**
    *  Basic dialog box taht lists all the children classes
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   abstract class ChildrenCheckboxDialog extends RefactoringDialog {
      /**
       *  List of type checkboxes
       *
       * @since    2.9.12
       */
      protected ChildClassCheckboxPanel children;
      /**
       *  The parent type summary
       *
       * @since    2.9.12
       */
      protected TypeSummary parentType;


      /**
       *  Constructor for the ChildrenCheckboxDialog object
       *
       * @param  initType  the parent type
       * @since            2.9.12
       */
      public ChildrenCheckboxDialog(TypeSummary initType) {
         super();

         parentType = initType;

         getContentPane().setLayout(new BorderLayout());

         children = new ChildClassCheckboxPanel(parentType);
         getContentPane().add(children, BorderLayout.NORTH);

         JButton okButton = new JButton("OK");
         getContentPane().add(okButton, BorderLayout.WEST);
         okButton.addActionListener(this);

         JButton cancelButton = new JButton("Cancel");
         getContentPane().add(cancelButton, BorderLayout.EAST);
         cancelButton.addActionListener(this);

         pack();

         CenterDialog.center(this, currentPackage);
      }
   }



   /**
    *  Panel of checkboxes with all of the children classes listed
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class ChildClassCheckboxPanel extends JPanel {
      private TypeSummary parentType;
      private LinkedList childrenCheckboxes;


      /**
       *  Constructor for the ChildClassCheckboxPanel object
       *
       * @param  initType  The type
       * @since            2.9.12
       */
      public ChildClassCheckboxPanel(TypeSummary initType) {
         parentType = initType;

         childrenCheckboxes = new LinkedList();

         Iterator iter = ChildClassSearcher.query(parentType);
         int count = 0;
         while (iter.hasNext()) {
            TypeSummary next = (TypeSummary)iter.next();

            TypeCheckbox tcb = new TypeCheckbox(next);
            childrenCheckboxes.add(tcb);
            count++;
         }

         int columns = count / 10 + 1;
         setLayout(new GridLayout(count / columns + 1, columns));

         iter = childrenCheckboxes.iterator();
         while (iter.hasNext()) {
            add((TypeCheckbox)iter.next());
         }
      }


      /**
       *  Gets the Checkboxes associated with this child class
       *
       * @return    The list of type checkboxes
       * @since     2.9.12
       */
      public Iterator getCheckboxes() {
         return childrenCheckboxes.iterator();
      }
   }

}

