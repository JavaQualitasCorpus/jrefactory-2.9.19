/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2003 JRefactory.  All rights reserved.
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
package net.sourceforge.jrefactory.uml;

import java.util.Iterator;
import javax.swing.*;
import net.sourceforge.jrefactory.action.AddChildClassAction;
import net.sourceforge.jrefactory.action.AddParentClassAction;
import net.sourceforge.jrefactory.action.BatchRenameAction;
import net.sourceforge.jrefactory.action.ExtractInterfaceAction;
import net.sourceforge.jrefactory.action.ExtractMethodAction;
import net.sourceforge.jrefactory.action.MoveClassAction;
import net.sourceforge.jrefactory.action.MoveMethodAction;
import net.sourceforge.jrefactory.action.PushDownFieldAction;
import net.sourceforge.jrefactory.action.PushDownMethodAction;
import net.sourceforge.jrefactory.action.PushUpAbstractMethodAction;
import net.sourceforge.jrefactory.action.PushUpFieldAction;
import net.sourceforge.jrefactory.action.PushUpMethodAction;
import net.sourceforge.jrefactory.action.RemoveClassAction;
import net.sourceforge.jrefactory.action.RenameClassAction;
import net.sourceforge.jrefactory.action.RenameFieldAction;
import net.sourceforge.jrefactory.action.RenameMethodAction;
import net.sourceforge.jrefactory.action.RenameParameterAction;
import net.sourceforge.jrefactory.action.ShowSourceAction;
import org.acm.seguin.summary.MethodSummary;
//import org.acm.seguin.ide.common.SourceBrowser;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.TypeSummary;



/**
 *  UMLPopupMenu
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLPopupMenu.java,v 1.2 2003/12/04 13:11:38 mikeatkinson Exp $
 */
public class UMLPopupMenu {
   private JPopupMenu popupMenu;
   private JComponent activeComponent;
   private UMLPackage current;


   /**
    *  Constructor for the UMLPopupMenu object
    *
    * @param  top   the package diagram
    * @param  init  the specific panel
    * @since        2.9.12
    */
   public UMLPopupMenu(UMLPackage top, JComponent init) {
      System.out.println("UMLPopupMenu(" + top + "," + init + ")");
      activeComponent = init;
      current = top;

      popupMenu = createPopupMenu();
      popupMenu.setInvoker(activeComponent);
   }


   /**
    *  Get the popup menu
    *
    * @return    the popup menu
    * @since     2.9.12
    */
   public JPopupMenu getMenu() {
      return popupMenu;
   }


   /**
    *  Add in the metrics
    *
    * @param  menu  Description of Parameter
    * @return       the metrics
    * @since        2.9.12
    */
   protected JMenuItem getMetricsMenu(JPopupMenu menu) {
      JMenu metrics = new JMenu("Metrics");
      JMenuItem item = new JMenuItem("Project Metrics");
      metrics.add(item);

      ProjectMetricsListener projectML = new ProjectMetricsListener(menu, item);
      item.addMouseListener(projectML);
      item.addActionListener(projectML);

      item = new JMenuItem("Package Metrics");
      metrics.add(item);
      PackageMetricsListener packageML = new PackageMetricsListener(current, menu, item);
      item.addMouseListener(packageML);
      item.addActionListener(packageML);

      item = new JMenuItem("Class Metrics");
      metrics.add(item);
      TypeMetricsListener tml = new TypeMetricsListener(activeComponent, menu, item);
      item.addMouseListener(tml);
      item.addActionListener(tml);

      if (activeComponent == null) {
         //  Do nothing
      } else if (activeComponent instanceof UMLMethod) {
         UMLMethod umlMethod = (UMLMethod)activeComponent;
         item = new JMenuItem("Method Metrics");
         metrics.add(item);
         MethodMetricsListener listener =
                  new MethodMetricsListener(umlMethod.getSummary(), menu, item);
         item.addMouseListener(listener);
         item.addActionListener(listener);
      }

      return metrics;  //  Return the value
   }


   /**
    *  Create the popup menu
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   protected JPopupMenu createPopupMenu() {
      JMenuItem item;
      JPopupMenu menu = new JPopupMenu("UML Diagram");

      if (activeComponent == null) {
         //  Do nothing
      } else if (activeComponent instanceof UMLField) {
         UMLField field = (UMLField)activeComponent;

         if (field.isAssociation()) {
            item = new JMenuItem("Convert to Attribute");
         } else {
            item = new JMenuItem("Convert to Association");
         }
         item.setEnabled(field.isConvertable());
         menu.add(item);
         item.addMouseListener(new PopupMenuListener(menu, item));
         item.addActionListener(new ConvertAdapter(current, field));
         menu.addSeparator();

      }

      addRefactorings(menu);
      menu.addSeparator();

      item = getMetricsMenu(menu);
      menu.add(item);

      // Add source link
      //if (SourceBrowser.get().canBrowseSource()) {
      menu.addSeparator();
      item = new JMenuItem("Show source");
      item.addActionListener(new ShowSourceAction((HasSummary)activeComponent, menu, item));
      menu.add(item);
      //}

      return menu;
   }


   /**
    *  Refactorings
    *
    * @param  menu  The feature to be added to the Refactorings attribute
    * @since        2.9.12
    */
   protected void addRefactorings(JPopupMenu menu) {
      addTypeRefactorings(menu);

      if (activeComponent == null) {
         //  Do nothing
      } else if (activeComponent instanceof UMLMethod) {
         addMethodRefactorings(menu);
      } else if (activeComponent instanceof UMLField) {
         addFieldRefactorings(menu);
      }
   }


   /**
    *  Gets the Type attribute of the UMLPopupMenu object
    *
    * @return    The Type value
    * @since     2.9.12
    */
   private UMLType getType() {
      if (activeComponent instanceof UMLType) {
         return (UMLType)activeComponent;
      } else if (activeComponent instanceof UMLLine) {
         return ((UMLLine)activeComponent).getParentType();
      }
      return null;
   }


   /**
    *  Gets the Type attribute of the UMLPopupMenu object
    *
    * @return    The Type value
    * @since     2.9.12
    */
   private TypeSummary getTypeSummary() {
      UMLType umlType = getType();
      return (umlType == null) ? null : (TypeSummary)umlType.getSourceSummary();
   }


   /**
    *  Adds a feature to the FieldRefactorings attribute of the UMLPopupMenu object
    *
    * @param  menu  The feature to be added to the FieldRefactorings attribute
    * @since        2.9.12
    */
   private void addFieldRefactorings(JPopupMenu menu) {
      JMenu fieldRefactorings = new JMenu("Field Refactorings");
      menu.add(fieldRefactorings);

      JMenuItem item = new JMenuItem("Rename");
      item.setAction(new RenameFieldAction(current, ((UMLField)activeComponent).getSummary(), menu, item));
      item.setEnabled(true);
      fieldRefactorings.add(item);

      item = new JMenuItem("Push Up");
      item.setAction(new PushUpFieldAction(current, getTypeSummary(), ((UMLField)activeComponent).getSummary(), menu, item));
      item.setEnabled(true);
      fieldRefactorings.add(item);

      item = new JMenuItem("Push Down");
      item.setAction(new PushDownFieldAction(current, getTypeSummary(), ((UMLField)activeComponent).getSummary(), menu, item));
      item.setEnabled(true);
      fieldRefactorings.add(item);
   }


   /**
    *  Adds a feature to the MethodRefactorings attribute of the UMLPopupMenu object
    *
    * @param  menu  The feature to be added to the MethodRefactorings attribute
    * @since        2.9.12
    */
   private void addMethodRefactorings(JPopupMenu menu) {
      JMenu methodRefactorings = new JMenu("Method Refactorings");
      menu.add(methodRefactorings);

      MethodSummary methodSummary = ((UMLMethod)activeComponent).getSummary();
      JMenuItem item = new JMenuItem("Rename");
      item.setAction(new RenameMethodAction(current, getTypeSummary(), ((UMLMethod)activeComponent).getSummary(), menu, item));
      item.setEnabled(true);
      methodRefactorings.add(item);

      item = new JMenuItem("Push Up");
      item.setAction(new PushUpMethodAction(current, ((UMLMethod)activeComponent).getSummary(), menu, item));
      item.setEnabled(true);
      methodRefactorings.add(item);

      item = new JMenuItem("Push Up (Abstract)");
      item.setAction(new PushUpAbstractMethodAction(current, ((UMLMethod)activeComponent).getSummary(), menu, item));
      item.setEnabled(true);
      methodRefactorings.add(item);

      item = new JMenuItem("Push Down");
      item.setAction(new PushDownMethodAction(current, getTypeSummary(), methodSummary, menu, item));
      item.setEnabled(true);
      methodRefactorings.add(item);

      item = new JMenuItem("Move Method");
      item.setAction(new MoveMethodAction(current, getTypeSummary(), methodSummary, menu, item));
      item.setEnabled(methodSummary.getParameterCount() > 0);
      methodRefactorings.add(item);

      item = new JMenuItem("Extract Method");
      item.setAction(new ExtractMethodAction(current, getTypeSummary(), methodSummary, menu, item));
      methodRefactorings.add(item);

      if (methodSummary.getParameterCount() == 0) {
         item = new JMenuItem("Rename Parameters");
         item.setEnabled(false);
         methodRefactorings.add(item);
      } else {
         JMenu rename = new JMenu("Rename Parameter:");
         methodRefactorings.add(rename);

         Iterator iter = methodSummary.getParameters();
         while (iter.hasNext()) {
            ParameterSummary next = (ParameterSummary)iter.next();
            item = new JMenuItem(next.getName());
            item.setAction(new RenameParameterAction(current, next, menu, item));
            rename.add(item);
         }
      }
   }


   /**
    *  Adds a feature to the TypeRefactorings attribute of the UMLPopupMenu object
    *
    * @param  menu  The feature to be added to the TypeRefactorings attribute
    * @since        2.9.12
    */
   private void addTypeRefactorings(JPopupMenu menu) {
      JMenu typeRefactorings = new JMenu("Type Refactorings");
      menu.add(typeRefactorings);

      TypeSummary[] typeArray = SelectedSummaryList.list(current, getType());
      JMenuItem item = new JMenuItem("Rename Class");
      item.setAction(new RenameClassAction(current, getTypeSummary(), menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);

      //  Add in moving a class
      item = new JMenuItem("Move Class To");
      item.setAction(new MoveClassAction(typeArray, menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);

      //  Add in a parent class
      item = new JMenuItem("Add Abstract Parent Class");
      item.setAction(new AddParentClassAction(current, typeArray, menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);

      //  Add in a child class
      item = new JMenuItem("Add Child Class");
      item.setAction(new AddChildClassAction(current, getTypeSummary(), menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);

      //  Remove a child class
      item = new JMenuItem("Remove Class");
      item.setAction(new RemoveClassAction(current, getTypeSummary(), menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);

      //  Extract Interface
      item = new JMenuItem("Extract Interface");
      item.setAction(new ExtractInterfaceAction(current, typeArray, menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);

      //  Extract Interface
      item = new JMenuItem("Rename Variables Using Hungarian Notation");
      item.setAction(new BatchRenameAction(current, getTypeSummary(), menu, item));
      item.setEnabled(true);
      typeRefactorings.add(item);
   }
}

