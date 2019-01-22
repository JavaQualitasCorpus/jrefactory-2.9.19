package net.sourceforge.jrefactory.action;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import net.sourceforge.jrefactory.uml.PopupMenuListener;
import net.sourceforge.jrefactory.uml.UMLPackage;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;
import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.refactor.Refactoring;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.refactor.RefactoringFactory;
import org.acm.seguin.refactor.field.RenameFieldRefactoring;
import org.acm.seguin.refactor.method.RenameParameterRefactoring;
import org.acm.seguin.summary.*;



/**
 *  Performs the hungarian name renaming to an entire class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: BatchRenameAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class BatchRenameAction extends RefactoringAction {
   /**
    *  Constructor for the BatchRenameAction object
    *
    * @since    2.9.12
    */
   public BatchRenameAction() {
      super(new EmptySelectedFileSet());
      initNames();
   }


   /**
    *  Constructor for the BatchRenameAction object
    *
    * @param  initPackage  Description of Parameter
    * @param  initType     Description of Parameter
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since               2.9.12
    */
   public BatchRenameAction(UMLPackage initPackage, TypeSummary initType, JPopupMenu initMenu, JMenuItem initItem) {
      this();
      currentPackage = initPackage;
      typeSummary = initType;
      setPopupMenuListener(new BatchRenameListener(initMenu, initItem));
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      return isSingleJavaFile();
   }


   /**
    *  Description of the Method
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) {
      typeSummary = typeSummaryArray[0];
      BatchRenameListener brl = new BatchRenameListener(null, null);
      brl.actionPerformed(null);
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Batch Rename");
      putValue(SHORT_DESCRIPTION, "Batch Rename");
      putValue(LONG_DESCRIPTION, "Batch Rename");
   }



   /**
    *  Performs the hungarian name renaming to an entire class
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    July 16, 2002
    */
   public class BatchRenameListener extends PopupMenuListener {

      /**
       *  Constructor for the BatchRename object
       *
       * @param  initMenu  The popup menu
       * @param  initItem  The current item
       * @since            2.9.12
       */
      public BatchRenameListener(JPopupMenu initMenu, JMenuItem initItem) {
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
         run(typeSummary);

         //  Update the GUIs
         ReloaderSingleton.reload();
      }


      /**
       *  Main processing method for the BatchRename object
       *
       * @param  typeSummary  Description of the Parameter
       * @since               2.9.12
       */
      public void run(TypeSummary typeSummary) {
         //  Over the fields
         Iterator iter = typeSummary.getFields();
         if (iter != null) {
            while (iter.hasNext()) {
               FieldSummary nextFieldSummary = (FieldSummary)iter.next();
               System.out.println("Renaming:  " + nextFieldSummary.getName());
               try {
                  renameField(typeSummary, nextFieldSummary);
               } catch (Exception exc) {
                  exc.printStackTrace();
               }
            }
         }

         //  Over the methods
         iter = typeSummary.getMethods();
         if (iter != null) {
            while (iter.hasNext()) {
               MethodSummary next = (MethodSummary)iter.next();
               visit(next);
            }
         }

         //  Over the types
         iter = typeSummary.getTypes();
         if (iter != null) {
            while (iter.hasNext()) {
               TypeSummary next = (TypeSummary)iter.next();
               run(next);
            }
         }
      }


      /**
       *  Renames a field to use hungarian notation
       *
       * @param  typeSummary   Description of the Parameter
       * @param  fieldSummary  Description of the Parameter
       * @since                2.9.12
       */
      private void renameField(TypeSummary typeSummary, FieldSummary fieldSummary) {
         HungarianNamer namer = new HungarianNamer();
         String newName = namer.getDefaultName(fieldSummary, "m_");

         if (newName.equals(fieldSummary.getName())) {
            return;
         }

         RenameFieldRefactoring rfr = RefactoringFactory.get().renameField();
         rfr.setClass(typeSummary);
         rfr.setField(fieldSummary.getName());
         rfr.setNewName(newName);
         runRefactoring(rfr);
      }


      /**
       *  Renames a parameter to have hungarian notation
       *
       * @param  method  Description of the Parameter
       * @param  param   Description of the Parameter
       * @since          2.9.12
       */
      private void renameParameter(MethodSummary method, ParameterSummary param) {
         HungarianNamer namer = new HungarianNamer();
         RenameParameterRefactoring rpr = RefactoringFactory.get().renameParameter();
         rpr.setMethodSummary(method);
         rpr.setParameterSummary(param);
         rpr.setNewName(namer.getDefaultName(param, "a_"));
         runRefactoring(rpr);
      }



      /**
       *  Update the code
       *
       * @param  refactoring  Description of the Parameter
       * @since               2.9.12
       */
      private void runRefactoring(Refactoring refactoring) {
         try {
            refactoring.run();
         } catch (RefactoringException re) {
            JOptionPane.showMessageDialog(null, re.getMessage(), "Refactoring Exception",
                     JOptionPane.ERROR_MESSAGE);
         } catch (Throwable thrown) {
            ExceptionPrinter.print(thrown, true);
         }
      }


      /**
       *  Description of the Method
       *
       * @param  methodSummary  Description of the Parameter
       * @since                 2.9.12
       */
      private void visit(MethodSummary methodSummary) {
         Iterator iter = methodSummary.getParameters();
         if (iter != null) {
            while (iter.hasNext()) {
               ParameterSummary nextParameterSummary = (ParameterSummary)iter.next();
               System.out.println("Renaming:  " + nextParameterSummary.getName());
               try {
                  renameParameter(methodSummary, nextParameterSummary);
               } catch (Exception exc) {
                  exc.printStackTrace();
               }
            }
         }
      }
   }

}

