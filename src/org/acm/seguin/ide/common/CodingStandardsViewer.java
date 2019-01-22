package org.acm.seguin.ide.common;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.acm.seguin.ide.common.options.SelectedRules;
import net.sourceforge.jrefactory.action.GenericAction;
import net.sourceforge.jrefactory.action.RenameMethodAction;
import net.sourceforge.jrefactory.action.RenameFieldAction;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.PMDException;
import org.acm.seguin.pmd.Report;
import org.acm.seguin.pmd.RuleContext;
import org.acm.seguin.pmd.RuleSetNotFoundException;
import org.acm.seguin.pmd.RuleViolation;


/**
 *  A GUI Component to display Duplicate code.
 *
 * @author     Jiger Patel
 * @author     Mike Atkinson
 * @created    05 Apr 2003
 */

public class CodingStandardsViewer extends JPanel {
   Frame view;
   JTree tree;
   DefaultTreeModel treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Coding Standard Checking Results", true));
   private final static String CURRENT_BUFFER = "Current Buffer";


   /**
    *  Constructor for the CodingStandardsViewer object
    *
    * @param  aView  Description of Parameter
    */
   public CodingStandardsViewer(Frame aView) {
      this.view = aView;
      setLayout(new BorderLayout());
      JButton checkBuff = new JButton("Current");
      checkBuff.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               IDEPlugin.checkBuffer(view,null);
            }
         });
      JButton checkAllBuff = new JButton("All Buffers");
      checkAllBuff.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               IDEPlugin.checkAllOpenBuffers(view);
            }
         });
      JButton checkCurDir = new JButton("Dir");
      checkCurDir.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               IDEPlugin.checkDirectory(view, false);
            }
         });
      JButton checkDirRecurse = new JButton("Subdirs");
      checkDirRecurse.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               IDEPlugin.checkDirectory(view, true);
            }
         });
      JButton clearbutton = new JButton(IDEPlugin.loadIcon("Clear.png"));
      clearbutton.setBorderPainted(false);
		clearbutton.setToolTipText(IDEPlugin.getProperty("javastyle.pmd.clear.label"));
		clearbutton.addActionListener(
         new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               clearViolations();
               refreshTree();
            }
         });
      final JPanel top = new JPanel();
      top.setLayout(new BorderLayout());
      
      final JPanel buttons = new JPanel();
      buttons.setLayout(new java.awt.FlowLayout());
      buttons.add(checkBuff);
      buttons.add(checkAllBuff);
      buttons.add(checkCurDir);
      buttons.add(checkDirRecurse);
      buttons.add(clearbutton);
      top.add(new javax.swing.JLabel("check coding standards in:"), BorderLayout.NORTH);
      top.add(buttons, BorderLayout.CENTER);
      add(top, BorderLayout.NORTH);

      tree = new JTree(treeModel);
      tree.setCellRenderer(new CSRenderer());
      tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      tree.addTreeSelectionListener(
         new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent evt) {
               DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

               if (node != null) {
                  System.out.println("node="+node);
                  System.out.println("node.isLeaf()="+node.isLeaf());
                  System.out.println("node.getClass()="+node.getClass());
                  if (node.isLeaf() && node instanceof Refactor) {
                     System.out.println("trying to refactor");
                     try {
                        Refactor refactor = (Refactor)node;
                        (new RunGotoViolation(refactor, view)).run();
                        final Object buffer = IDEPlugin.openFile(view, refactor.getFilename());
                        IDEPlugin.setBuffer(view, buffer);
                        System.out.println("trying to refactor - before running refactoring action");
                        refactor.getAction().actionPerformed(new java.awt.event.ActionEvent(buffer, refactor.getLine(), "???"));
                        System.out.println("trying to refactor - after running refactoring action");
                     } catch (java.io.IOException e) {
                        e.printStackTrace();
                     }
                  } else if (node.isLeaf() && node instanceof Violation) {
                     Object obj = node.getUserObject();
                     Violation violation = (Violation)node;
                     if (obj == null) {
                        (new RunGotoViolation(violation, view)).run();
                     } else {
                        try {
                           (new RunGotoViolation(violation, view)).run();
                           final Object buffer = IDEPlugin.openFile(view, violation.getFilename());
                           IDEPlugin.setBuffer(view, buffer);
                           ((GenericAction)obj).actionPerformed(new java.awt.event.ActionEvent(buffer, violation.getLine(), "???"));
                        } catch (java.io.IOException e) {
                           e.printStackTrace();
                        }
                     }
                  }
               }
            }
         });

      add(new JScrollPane(tree), BorderLayout.CENTER);
   }


   public void setView(Frame view) {
      this.view = view;
   }
   

   /**
    *  Gets the root attribute of the CPDDuplicateCodeViewer object
    *
    * @return    The root value
    */
   public DefaultMutableTreeNode getRoot() {
      return (DefaultMutableTreeNode)treeModel.getRoot();
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         Description of the Returned Value
    */
   public RuleContext check(Frame view, Object buffer, boolean silent) {
      //System.err.println("check(view, " + buffer + ")");
      RuleContext ctx = new RuleContext();
      ctx.setReport(new Report());
      ctx.setSourceCodeFilename("");
      IDEPlugin.clearAnnotations(view, buffer, IDEInterface.CODING_STANDARDS);

      try {
         PMD pmd = new PMD();
         SelectedRules selectedRuleSets = new SelectedRules(IDEPlugin.getProjectName(view, buffer), view);
         pmd.processFile(new java.io.StringReader(IDEPlugin.getText(view, buffer)), selectedRuleSets.getSelectedRules(), ctx);
         String path = IDEPlugin.getFilePathForBuffer(buffer);
         if (ctx.getReport().isEmpty()) {
            if (!silent) {
               JOptionPane.showMessageDialog(this, "No problems found (1)", "JRefactory", JOptionPane.INFORMATION_MESSAGE);
            }
            removeViolations((path == null) ? "" : path);
         } else {
            Violations violations = (path != null && path.length() > 0) ? new Violations(path, path) : new Violations(path, "");

            for (Iterator i = ctx.getReport().iterator(); i.hasNext(); ) {
               RuleViolation ruleViolation = (RuleViolation)i.next();
               //System.out.println("ruleViolation=" + ruleViolation.getLine() + " : " + ruleViolation.getDescription());
               Violation violation = new Violation(path, ruleViolation.getLine(), ruleViolation.getDescription());

               violations.addViolation(violation);
               IDEPlugin.addAnnotation(view, buffer, IDEInterface.CODING_STANDARDS, ruleViolation.getLine(), ruleViolation.getDescription());
            }
            addViolations(violations);
         }
         refreshTree();
         expandAll();
      } catch (RuleSetNotFoundException rsne) {
         rsne.printStackTrace();
      } catch (PMDException pmde) {
         pmde.printStackTrace();
         JOptionPane.showMessageDialog(this, "Error while processing " + "<no path>");
      }
      return ctx;
   }


   /**
    *  Description of the Method
    *
    * @param  files   Description of Parameter
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         Description of the Returned Value
    */
   public List checkFiles(List files, Frame view, Object buffer) {
      PMD pmd = new PMD();
      SelectedRules selectedRuleSets = null;
      IDEPlugin.clearAnnotations(view, null, IDEInterface.CUT_AND_PASTE_DETECTOR);

      try {
         selectedRuleSets = new SelectedRules(IDEPlugin.getProjectName(view, buffer), view);
      } catch (RuleSetNotFoundException rsne) {
         // should never happen since rulesets are fetched via getRegisteredRuleSet, nonetheless:
         System.out.println("JavaStyle ERROR: Couldn't find a ruleset");
         rsne.printStackTrace();
         JOptionPane.showMessageDialog(view, "Unable to find rulesets, halting Coding Standards Check", "JRefactory", JOptionPane.ERROR_MESSAGE);
         return new ArrayList();
      }

      List contexts = new ArrayList();
      boolean foundProblems = false;

      for (Iterator i = files.iterator(); i.hasNext(); ) {
         File file = (File)i.next();

         RuleContext ctx = new RuleContext();
         contexts.add(ctx);
         ctx.setReport(new Report());
         ctx.setSourceCodeFilename(file.getAbsolutePath());
         try {
            pmd.processFile(new FileInputStream(file), selectedRuleSets.getSelectedRules(), ctx);
         } catch (FileNotFoundException fnfe) {
            // should never happen, but if it does, carry on to the next file
            System.out.println("JavaStyle ERROR: Unable to open file " + file.getAbsolutePath());
         } catch (PMDException pmde) {
            pmde.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error while processing " + file.getAbsolutePath());
         }

         String path = file.getAbsolutePath();
         if (ctx.getReport().size()>0) {
            Violations violations = new Violations(path, path);
            for (Iterator j = ctx.getReport().iterator(); j.hasNext(); ) {
               foundProblems = true;
               RuleViolation ruleViolation = (RuleViolation)j.next();
               //errorSource.addError(ErrorSource.WARNING, path, ruleViolation.getLine() - 1, 0, 0, ruleViolation.getDescription());
               Violation violation = new Violation(path, ruleViolation.getLine(), ruleViolation.getDescription());
               violations.addViolation(violation);
               IDEPlugin.addAnnotation(view, path, IDEInterface.CUT_AND_PASTE_DETECTOR, ruleViolation.getLine(), ruleViolation.getDescription());
            }
            addViolations(violations);
         } else {
            removeViolations(path);
         }
         refreshTree();
      }
      refreshTree();
      expandAll();
      if (!foundProblems) {
         JOptionPane.showMessageDialog(view, "No problems found", "Jrefactory", JOptionPane.INFORMATION_MESSAGE);
      }
      return contexts;
   }



   /**  Description of the Method */
   public void refreshTree() {
      treeModel.reload();
   }


   /**
    *  Description of the Method
    *
    * @param  violation  Description of Parameter
    */
   public void gotoViolation(final Violation violation) {
      if (violation != null) {
         IDEPlugin.runInAWTThread(new RunGotoViolation(violation, view));
      }
   }


   /**
    *  Adds a feature to the Duplicates attribute of the CPDDuplicateCodeViewer object
    *
    * @param  violations  The feature to be added to the Violations attribute
    */
   public void addViolations(Violations violations) {
      Enumeration children = getRoot().children();
      while (children.hasMoreElements()) {
         Violations v = (Violations)children.nextElement();
         if (v.sourcecode.equals(violations.sourcecode)) {
            v.removeAllChildren();
            int limit = violations.getChildCount();
            for (int i = 0; i < limit; i++) {
               Violation viol = (Violation)violations.getChildAt(0);
               v.addViolation(viol);
            }
            v.message = violations.message;
            v.sourcecode = violations.sourcecode;
            if (!v.message.equals(CURRENT_BUFFER)) {
               // remove the old current buffer violations if the current buffer has changed.
               removeCurrentBufferViolations();
            }
            return;
         }
      }
      if (!violations.message.equals(CURRENT_BUFFER)) {
         // remove the old current buffer violations if the current buffer has changed.
         removeCurrentBufferViolations();
      }
      getRoot().add(violations);
   }

   /**
    *  Adds a feature to the Duplicates attribute of the CPDDuplicateCodeViewer object
    *
    * @param  path  Description of Parameter
    */
   public void removeViolations(String path) {
      Enumeration children = getRoot().children();
      while (children.hasMoreElements()) {
         Violations v = (Violations)children.nextElement();
         if (v.sourcecode.equals(path)) {
            getRoot().remove(v);
            break;
         }
      }
      removeCurrentBufferViolations();
   }

   /**  Description of the Method */
   private void removeCurrentBufferViolations() {
      Enumeration children = getRoot().children();
      while (children.hasMoreElements()) {
         Violations v = (Violations)children.nextElement();
         if (v.message.equals(CURRENT_BUFFER)) {
            getRoot().remove(v);
            return;
         }
      }
   }

   /**  Description of the Method */
   public void expandAll() {
      int row = 0;

      while (row < tree.getRowCount()) {
         tree.expandRow(row);
         row++;
      }
   }


   /**  Description of the Method */
   public void collapseAll() {
      int row = tree.getRowCount() - 1;

      while (row >= 0) {
         tree.collapseRow(row);
         row--;
      }
   }


   /**  Description of the Method */
   public void clearViolations() {
      getRoot().removeAllChildren();
      IDEPlugin.clearAnnotations(view, null, IDEInterface.CODING_STANDARDS);
   }


   /**
    *  Description of the Class
    *
    * @author     Mike Atkinson
    * @created    September 13, 2003
    */
   public class Violations extends DefaultMutableTreeNode {
      String message, sourcecode;


      /**
       *  Constructor for the Duplicates object
       *
       * @param  message     Description of Parameter
       * @param  sourcecode  Description of Parameter
       */
      public Violations(String message, String sourcecode) {
         this.message = message;
         this.sourcecode = sourcecode;
      }


      /**
       *  Gets the sourceCode attribute of the Duplicates object
       *
       * @return    The sourceCode value
       */
      public String getSourceCode() {
         return sourcecode;
      }


      /**
       *  Adds a feature to the Duplicate attribute of the Duplicates object
       *
       * @param  violation  The feature to be added to the Violation attribute
       */
      public void addViolation(Violation violation) {
         add(violation);
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Return Value
       */
      public String toString() {
         return message;
      }
   }


   /**
    *  This holds the information about a coding standards violation in the tree view.
    *
    * @author     Mike Atkinson
    * @created    September 13, 2003
    */
   public class Violation extends DefaultMutableTreeNode {
      private String filename;
      private int line;
      private String description;


      /**
       *  Constructor for the Duplicate object
       *
       * @param  filename     Description of Parameter
       * @param  line         Description of Parameter
       * @param  description  Description of Parameter
       */
      public Violation(String filename, int line, String description) {
         this.filename = filename;
         this.line = line;
         this.description = description;
         if (description.startsWith("Avoid using short method names")) {
            //setUserObject(new RenameMethodAction());
            add(new Violation(filename, line, "go to line"));
            add(new Refactor(filename, line, new RenameMethodAction()));
         } else if (description.startsWith("Method name does not begin with a lower case character")) {
            //setUserObject(new RenameMethodAction());
            add(new Violation(filename, line, "go to line"));
            add(new Refactor(filename, line, new RenameMethodAction()));
         } else if (description.startsWith("Avoid variables with short names like")) {
            //setUserObject(new RenameFieldAction());
            add(new Violation(filename, line, "go to line"));
            add(new Refactor(filename, line, new RenameFieldAction()));
         } else if (description.startsWith("Avoid excessively long variable names like")) {
            //setUserObject(new RenameFieldAction());
            add(new Violation(filename, line, "go to line"));
            add(new Refactor(filename, line, new RenameFieldAction()));
         }
      }


      /**
       *  Gets the filename attribute of the Duplicate object
       *
       * @return    The filename value
       */
      public String getFilename() {
         return filename;
      }


      /**
       *  Gets the beginLine attribute of the Duplicate object
       *
       * @return    The beginLine value
       */
      public int getLine() {
         return line;
      }


      /**
       *  Gets the endLine attribute of the Duplicate object
       *
       * @return    The endLine value
       */
      public String getDescription() {
         return description;
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Return Value
       */
      public String toString() {
         return getDescription()+ " ("+filename + ":" + getLine() + ")";
      }
   }


   /**
    *  This holds the information about a refactoring action to correct a coding standards
    *  violation, within in the tree view.
    *
    * @author     Mike Atkinson
    * @created    May 06, 2004
    */
   public class Refactor extends Violation {
      private GenericAction action;
      
      public Refactor(String filename, int line, GenericAction action) {
         super(filename, line, action.getValue(GenericAction.NAME).toString());
         this.action = action;
      }

      /**
       *  Gets the endLine attribute of the Duplicate object
       *
       * @return    The endLine value
       */
      public GenericAction getAction() {
         return action;
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Return Value
       */
      public String toString() {
         return "<html><b>Refactor: "+getDescription()+"</b></html>";
      }
   }


   /**
    *  This class is used to jump to the position in the Java source where coding standards violation occurred
    *
    * @author    Mike Atkinson
    */
   private static class RunGotoViolation implements Runnable {
      private Violation violation;
      private Frame view;


      /**
       *  Constructor for the RunGotoViolation object
       *
       * @param  violation  Description of Parameter
       * @param  view       Description of Parameter
       */
      public RunGotoViolation(Violation violation, Frame view) {
         this.violation = violation;
         this.view = view;
      }


      /**  Main processing method for the RunGotoViolation object */
      public void run() {
         try {
            final Object buffer = IDEPlugin.openFile(view, violation.getFilename());
            IDEPlugin.setBuffer(view, buffer);
            int start = IDEPlugin.getLineStartOffset(buffer, violation.getLine() - 1);
            int end = IDEPlugin.getLineStartOffset(buffer, violation.getLine());
            IDEPlugin.setSelection(view, buffer, start, end);
            IDEPlugin.moveCaretPosition(view, buffer, end);
         } catch (Exception ex) {
            ex.printStackTrace();
            IDEPlugin.log(IDEInterface.ERROR, "CodingStandardsViewer", "can't open duplicate file! " + ex.getMessage());
         }
      }
   }


    /**
     * Custom cell renderer for the bug tree.
     * We use this to select the tree icons, and to set the
     * text color based on the bug priority.
     */
    private static class CSRenderer extends DefaultTreeCellRenderer {
        public CSRenderer() {
        }
       
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object obj = node.getUserObject();

            Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (!leaf || obj==null || !(obj instanceof GenericAction) ) {
               return c;
            }
            java.awt.Color color = c.getBackground();
            JPanel panel = new JPanel();
            panel.setBackground(color);
            panel.setLayout(new BorderLayout());
            panel.add(c, BorderLayout.NORTH);
            Object o = ((GenericAction)obj).getValue(GenericAction.NAME);
            JLabel c2 = new JLabel("      ");
            panel.add(c2, BorderLayout.WEST);
            JLabel c3 = new JLabel("Refactor: "+o);
            panel.add(c3, BorderLayout.CENTER);
            panel.validate();
            panel.setMinimumSize(panel.getMinimumSize());
            return panel;
        }
    }

}

