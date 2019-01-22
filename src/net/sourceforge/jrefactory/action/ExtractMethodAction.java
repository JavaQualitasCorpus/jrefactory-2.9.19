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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.acm.seguin.awt.CenterDialog;
import org.acm.seguin.awt.OrderableList;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.method.ExtractMethodRefactoring;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.VariableSummary;
import org.acm.seguin.summary.MethodSummary;
import net.sourceforge.jrefactory.uml.UMLPackage;



/**
 *  Performs the extract method action
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: ExtractMethodAction.java,v 1.2 2003/12/04 13:11:38 mikeatkinson Exp $
 */
public class ExtractMethodAction extends RefactoringAction {
   /**
    *  Constructor for the ExtractMethodAction object
    *
    * @since    2.9.12
    */
   public ExtractMethodAction() {
      super(null);
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
   public ExtractMethodAction(UMLPackage initPackage,
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
      setPopupMenuListener(new ExtractMethodListener(initMenu, initItem));
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      java.awt.Frame view = IDEPlugin.getEditorFrame();
      Object buffer = IDEPlugin.getCurrentBuffer(view);
      return IDEPlugin.bufferContainsJavaSource(view, buffer);
   }


   /**
    *  What to do when someone selects the extract method refactoring
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void actionPerformed(ActionEvent evt) {
      if (failsUnitTests(currentPackage, "Extract Method")) {
         try {
            (new GenericExtractMethod(evt.getSource())).show();
         } catch (RefactoringException re) {
            JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception", JOptionPane.ERROR_MESSAGE);
         }
         CurrentSummary.get().reset();
      }
   }


   /**
    *  The listener to activate with the specified types
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) { }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, ACCELERATOR).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Extract Method");
      putValue(SHORT_DESCRIPTION, "Extract Method (fails Unit tests)");
      putValue(LONG_DESCRIPTION, "Highlight the code to extract and select this menu option (this should not be used as it fails Unit tests)");
      putValue(ACCELERATOR, KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK | Event.SHIFT_MASK));
   }



   /**
    *  Listener for the move method menu item
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public class ExtractMethodListener extends DialogViewListener {
      /**
       *  Constructor for the ExtractMethodListener object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public ExtractMethodListener(JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
      }


      /**
       *  As this refactoring fails its unit tests, confirm that the user wants to continue.
       *
       * @param  evt  the action event
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         if (failsUnitTests(currentPackage, "Extract Method")) {
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
         try {
            return new MenuExtractMethod(null);
         } catch (RefactoringException e) {
            e.printStackTrace();
         }
         return null;
      }
   }


   /**
    *  JBuilder's method to extract a file
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    October 18, 2001
    */
   public class MenuExtractMethod extends ExtractMethodDialog {
      public MenuExtractMethod(JFrame frame) throws RefactoringException {
         super(frame);
      }


      /**
       *  Sets the StringInIDE attribute of the JBuilderExtractMethod object
       *
       * @param  value  The new StringInIDE value
       * @since         2.9.12
       */
      protected void setStringInIDE(String value) {
         
      }

      /**
       *  Gets the SelectionFromIDE attribute of the JBuilderExtractMethod object
       *
       * @return    The SelectionFromIDE value
       * @since     2.9.12
       */
      protected String getStringFromIDE() {
         return "";
      }

      /**
       *  Gets the SelectionFromIDE attribute of the JBuilderExtractMethod object
       *
       * @return    The SelectionFromIDE value
       * @since     2.9.12
       */
      protected String getSelectionFromIDE() {
         return "";
      }

   }

   /**
    *  JBuilder's method to extract a file
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    October 18, 2001
    */
   public class GenericExtractMethod extends ExtractMethodDialog {
      Object buffer;


      /**
       *  Constructor for the JBuilderExtractMethod object
       *
       * @param  buffer                    Description of Parameter
       * @exception  RefactoringException  Description of Exception
       * @since                            2.9.12
       */
      public GenericExtractMethod(Object buffer) throws RefactoringException {
         super((javax.swing.JFrame)IDEPlugin.getEditorFrame());
         this.buffer = buffer;
      }


      /**
       *  Sets the StringInIDE attribute of the JBuilderExtractMethod object
       *
       * @param  value  The new StringInIDE value
       * @since         2.9.12
       */
      protected void setStringInIDE(String value) {
         IDEPlugin.setText((java.awt.Frame)getOwner(), buffer, value);
      }


      /**
       *  Gets the SelectionFromIDE attribute of the JBuilderExtractMethod object
       *
       * @return    The SelectionFromIDE value
       * @since     2.9.12
       */
      protected String getSelectionFromIDE() {
         return IDEPlugin.getText((java.awt.Frame)getOwner(), buffer);  // FIXME: getSelectedText()
      }


      /**
       *  Gets the StringFromIDE attribute of the JBuilderExtractMethod object
       *
       * @return    The StringFromIDE value
       * @since     2.9.12
       */
      protected String getStringFromIDE() {
         return IDEPlugin.getText((java.awt.Frame)getOwner(), buffer);
      }
   }


   /**
    *  User interface to enter the name of the method that was just extracted
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   public abstract class ExtractMethodDialog extends RefactoringDialog {
      private JTextField newName;
      private ExtractMethodRefactoring emr;
      private OrderableList list;
      private JRadioButton privateButton;
      private JRadioButton packageButton;
      private JRadioButton protectedButton;
      private JRadioButton publicButton;
      private JList returnList;
      private JTextField returnTextField;
      private JLabel signatureLabel;
      private SignatureUpdateAdapter sua;
      private JLabel sizer;
      private Dimension originalSize;


      /**
       *  Constructor for the ExtractMethodDialog object
       *
       * @param  parent                    the parent frame
       * @exception  RefactoringException  problem in setting up the refactoring
       * @since                            2.9.12
       */
      public ExtractMethodDialog(JFrame parent) throws RefactoringException {
         super(parent);

         sua = new SignatureUpdateAdapter(this);

         init();

         //  Set the window size and layout
         setTitle(getWindowTitle());

         GridBagLayout gridbag = new GridBagLayout();
         GridBagConstraints gbc = new GridBagConstraints();
         getContentPane().setLayout(gridbag);
         setSize(310, 120);

         Insets zeroInsets = new Insets(0, 0, 0, 0);

         //  Add components
         int currentRow = 1;
         JLabel newNameLabel = new JLabel(getLabelText());
         gbc.gridx = 1;
         gbc.gridy = currentRow;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.insets = new Insets(0, 0, 0, 10);
         gridbag.setConstraints(newNameLabel, gbc);
         getContentPane().add(newNameLabel);

         newName = new JTextField();
         newName.setColumns(40);
         newName.getDocument().addDocumentListener(sua);
         newName.addFocusListener(sua);
         gbc.gridx = 2;
         gbc.gridy = currentRow;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.insets = zeroInsets;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gridbag.setConstraints(newName, gbc);
         getContentPane().add(newName);

         currentRow++;

         JLabel parameterLabel = new JLabel("Parameters:  ");
         gbc.gridx = 1;
         gbc.gridy = currentRow;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.insets = new Insets(10, 0, 0, 0);
         getContentPane().add(parameterLabel, gbc);

         currentRow++;

         VariableSummary[] vs = emr.getParameters();
         if (vs.length > 1) {
            list = new OrderableList(vs, new VariableListCellRenderer());
            list.addListDataListener(sua);
            gbc.gridx = 1;
            gbc.gridy = currentRow;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.insets = zeroInsets;
            gbc.fill = GridBagConstraints.CENTER;
            gridbag.setConstraints(list, gbc);
            getContentPane().add(list);
         } else {
            JLabel label;
            if (vs.length == 0) {
               label = new JLabel("There are no parameters required for this method");
            } else {
               label = new JLabel("There is only one parameter required for this method:  " + vs[0].getName());
            }

            list = null;
            gbc.gridx = 1;
            gbc.gridy = currentRow;
            gbc.gridwidth = 3;
            gbc.gridheight = 1;
            gbc.insets = zeroInsets;
            gbc.fill = GridBagConstraints.CENTER;
            gridbag.setConstraints(label, gbc);
            getContentPane().add(label);
         }

         currentRow++;

         JPanel panel = initRadioButtons();
         gbc.gridx = 1;
         gbc.gridy = currentRow;
         gbc.gridwidth = 3;
         gbc.gridheight = 1;
         gbc.insets = zeroInsets;
         gbc.fill = GridBagConstraints.CENTER;
         gridbag.setConstraints(panel, gbc);
         getContentPane().add(panel);

         currentRow++;

         JLabel returnNameLabel = new JLabel("Return:");
         gbc.gridx = 1;
         gbc.gridy = currentRow;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.insets = new Insets(10, 0, 10, 10);
         gridbag.setConstraints(returnNameLabel, gbc);
         getContentPane().add(returnNameLabel);

         gbc.gridx = 2;
         gbc.gridy = currentRow;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.insets = zeroInsets;
         gbc.insets = new Insets(10, 0, 10, 0);
         gbc.fill = GridBagConstraints.HORIZONTAL;

         if (emr.isStatement()) {
            returnList = new JList(emr.getReturnTypes());
            returnList.setSelectedIndex(0);
            gridbag.setConstraints(returnList, gbc);
            getContentPane().add(returnList);
            returnList.addListSelectionListener(sua);
            returnTextField = null;
         } else {
            returnTextField = new JTextField(emr.getReturnType().toString());
            gridbag.setConstraints(returnTextField, gbc);
            getContentPane().add(returnTextField);
            returnTextField.getDocument().addDocumentListener(sua);
            returnTextField.addFocusListener(sua);
            returnList = null;
         }

         currentRow++;

         JLabel signNameLabel = new JLabel("Signature:");
         gbc.gridx = 1;
         gbc.gridy = currentRow;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.insets = new Insets(10, 0, 10, 10);
         gridbag.setConstraints(signNameLabel, gbc);
         getContentPane().add(signNameLabel);

         signatureLabel = new JLabel("");
         gbc.gridx = 2;
         gbc.gridy = currentRow;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         gbc.insets = zeroInsets;
         gbc.insets = new Insets(10, 0, 10, 0);
         gbc.fill = GridBagConstraints.HORIZONTAL;
         gridbag.setConstraints(signatureLabel, gbc);
         getContentPane().add(signatureLabel);

         currentRow++;
         JButton okButton = new JButton("OK");
         gbc.gridx = 2;
         gbc.gridy = currentRow;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gbc.insets = zeroInsets;
         gbc.fill = GridBagConstraints.NONE;
         gridbag.setConstraints(okButton, gbc);
         okButton.addActionListener(this);
         getContentPane().add(okButton);

         JButton cancelButton = new JButton("Cancel");
         gbc.gridx = 3;
         gbc.gridy = currentRow;
         gbc.gridwidth = 1;
         gbc.gridheight = 1;
         gridbag.setConstraints(cancelButton, gbc);
         cancelButton.addActionListener(this);
         getContentPane().add(cancelButton);

         update();

         pack();

         sizer = new JLabel();
         originalSize = signatureLabel.getSize();

         CenterDialog.center(this, parent);
      }


      /**
       *  Performs the update to the signature line
       *
       * @since    2.9.12
       */
      public void update() {
         createRefactoring();

         String signature = emr.getSignature();

         if (sizer != null) {
            sizer.setText(signature);
            Dimension current = sizer.getPreferredSize();
            int length = signature.length();
            while (current.width > originalSize.width) {
               length--;
               signature = signature.substring(0, length) + "...";
               sizer.setText(signature);
               current = sizer.getPreferredSize();
            }
         }

         signatureLabel.setText(signature);
      }


      /**
       *  Sets the StringInIDE attribute of the ExtractMethodDialog object
       *
       * @param  value  The new StringInIDE value
       * @since         2.9.12
       */
      protected abstract void setStringInIDE(String value);


      /**
       *  Gets the StringFromIDE attribute of the ExtractMethodDialog object
       *
       * @return    The StringFromIDE value
       * @since     2.9.12
       */
      protected abstract String getStringFromIDE();


      /**
       *  Gets the SelectionFromIDE attribute of the ExtractMethodDialog object
       *
       * @return    The SelectionFromIDE value
       * @since     2.9.12
       */
      protected abstract String getSelectionFromIDE();


      /**
       *  Follows up the refactoring by updating the text in the current window
       *
       * @param  refactoring  Description of Parameter
       * @since               2.9.12
       */
      protected void followup(Refactoring refactoring) {
         ExtractMethodRefactoring emr = (ExtractMethodRefactoring)refactoring;
         setStringInIDE(emr.getFullFile());
      }


      /**
       *  Creates the refactoring and fills in the data
       *
       * @return    the extract method refactoring
       * @since     2.9.12
       */
      protected Refactoring createRefactoring() {
         emr.setMethodName(newName.getText());
         if (list == null) {
            //  Don't need to set the parameter order!
         } else {
            emr.setParameterOrder(list.getData());
         }

         int prot = ExtractMethodRefactoring.PRIVATE;
         if (packageButton.isSelected()) {
            prot = ExtractMethodRefactoring.PACKAGE;
         }
         if (protectedButton.isSelected()) {
            prot = ExtractMethodRefactoring.PROTECTED;
         }
         if (publicButton.isSelected()) {
            prot = ExtractMethodRefactoring.PUBLIC;
         }
         emr.setProtection(prot);

         if (returnTextField == null) {
            Object result = returnList.getSelectedValue();
            emr.setReturnType(result);
         } else {
            emr.setReturnType(returnTextField.getText());
         }

         return emr;
      }


      /**
       *  Gets the WindowTitle attribute of the ExtractMethodDialog object
       *
       * @return    The WindowTitle value
       * @since     2.9.12
       */
      private String getWindowTitle() {
         return "Extract Method Dialog";
      }


      /**
       *  Gets the LabelText attribute of the ExtractMethodDialog object
       *
       * @return    The LabelText value
       * @since     2.9.12
       */
      private String getLabelText() {
         return "Method name:";
      }


      /**
       *  Initialize the extract method refactoring
       *
       * @exception  RefactoringException  Description of Exception
       * @since                            2.9.12
       */
      private void init() throws RefactoringException {
         emr = RefactoringFactory.get().extractMethod();
         String full = getStringFromIDE();
         if (full == null) {
            dispose();
            throw new RefactoringException("Invalid file for extracting the source code");
         } else {
            emr.setFullFile(full);
         }

         String selection = getSelectionFromIDE();
         if (full == null) {
            JOptionPane.showMessageDialog(null,
                     "You must select a series of statements or an expression to extract.",
                     "Extract Method Error",
                     JOptionPane.ERROR_MESSAGE);
            dispose();
            throw new RefactoringException("Empty selection.");
         } else {
            emr.setSelection(selection);
         }

         emr.setMethodName("extractedMethod");
      }


      /**
       *  Creates a panel of radio buttons with protection levels
       *
       * @return    the panel
       * @since     2.9.12
       */
      private JPanel initRadioButtons() {
         JPanel panel = new JPanel();
         panel.setLayout(new FlowLayout());
         JLabel label = new JLabel("Protection:");
         panel.add(label);

         ButtonGroup group = new ButtonGroup();

         privateButton = new JRadioButton("private");
         privateButton.setSelected(true);
         panel.add(privateButton);
         group.add(privateButton);
         privateButton.addActionListener(sua);

         packageButton = new JRadioButton("package");
         panel.add(packageButton);
         group.add(packageButton);
         packageButton.addActionListener(sua);

         protectedButton = new JRadioButton("protected");
         panel.add(protectedButton);
         group.add(protectedButton);
         protectedButton.addActionListener(sua);

         publicButton = new JRadioButton("public");
         panel.add(publicButton);
         group.add(publicButton);
         publicButton.addActionListener(sua);

         panel.setBorder(BorderFactory.createEtchedBorder());

         return panel;
      }
   }


   /**
    *  Description of the Class
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class VariableListCellRenderer extends JLabel implements ListCellRenderer {
      /**
       *  Constructor for the VariableListCellRenderer object
       *
       * @since    2.9.12
       */
      public VariableListCellRenderer() {
         setOpaque(true);
      }


      /**
       *  Gets the listCellRendererComponent attribute of the VariableListCellRenderer object
       *
       * @param  list          Description of the Parameter
       * @param  value         Description of the Parameter
       * @param  index         Description of the Parameter
       * @param  isSelected    Description of the Parameter
       * @param  cellHasFocus  Description of the Parameter
       * @return               The listCellRendererComponent value
       * @since                2.9.12
       */
      public Component getListCellRendererComponent(
                                                    JList list,
                                                    Object value,
                                                    int index,
                                                    boolean isSelected,
                                                    boolean cellHasFocus) {
         if (value instanceof VariableSummary) {
            VariableSummary varSummary = (VariableSummary)value;
            setText(varSummary.getName() + " (" + varSummary.getType() + ")");
         } else {
            setText(value.toString());
         }
         setBackground(isSelected ? Color.red : Color.white);
         setForeground(isSelected ? Color.white : Color.black);
         return this;
      }
   }


   /**
    *  This adapter is resposible for keeping the signature in the dialog box relatively up to date.
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    September 12, 2001
    */
   class SignatureUpdateAdapter implements ActionListener, DocumentListener,
            FocusListener, ListDataListener, ListSelectionListener {
      private ExtractMethodDialog emd;


      /**
       *  Constructor for the SignatureUpdateAdapter object
       *
       * @param  init  the dialog box it is responsible for
       * @since        2.9.12
       */
      public SignatureUpdateAdapter(ExtractMethodDialog init) {
         emd = init;
      }


      /**
       *  Description of the Method
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void intervalAdded(ListDataEvent evt) {
         emd.update();
      }


      /**
       *  Description of the Method
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void intervalRemoved(ListDataEvent evt) {
         emd.update();
      }


      /**
       *  Description of the Method
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void contentsChanged(ListDataEvent evt) {
         emd.update();
      }


      /**
       *  Description of the Method
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         emd.update();
      }


      /**
       *  Description of the Method
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void focusGained(FocusEvent evt) { }


      /**
       *  Description of the Method
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void focusLost(FocusEvent evt) {
         emd.update();
      }


      /**
       *  Someone selected something in the list box
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void valueChanged(ListSelectionEvent evt) {
         emd.update();
      }


      /**
       *  Document listener event
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void insertUpdate(DocumentEvent evt) {
         emd.update();
      }


      /**
       *  Document listener event
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void removeUpdate(DocumentEvent evt) {
         emd.update();
      }


      /**
       *  Document listener event
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void changedUpdate(DocumentEvent evt) {
         emd.update();
      }
   }
}

