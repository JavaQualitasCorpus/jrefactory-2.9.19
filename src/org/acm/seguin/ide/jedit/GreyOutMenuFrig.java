/*
    JavaStylePlugin.java - a Java pretty printer plugin for jEdit
    Copyright (c) 1999 Andreas "Mad" Schaefer (andreas.schaefer@madplanet.ch)
    Copyright (c) 2000,2001 Dirk Moebius (dmoebius@gmx.net)
    jEdit buffer options:
    :tabSize=4:indentSize=4:noTabs=false:maxLineLen=0:
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
  */
package org.acm.seguin.ide.jedit;


import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.textarea.JEditTextArea;


import java.awt.Component;
import java.awt.Container;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;
import net.sourceforge.jrefactory.action.CurrentSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.FieldSummary;

/**
 *  A plugin for pretty printing the current jEdit buffer, using the PrettyPrinter of the JREFactory suite.
 *
 * @author     Mike Atkinson (<a href="mailto:javastyle@ladyshot.demon.co.uk"> Mike@ladyshot.demon.co.uk</a> )
 * @author     Dirk Moebius (<a href="mailto:dmoebius@gmx.net"> dmoebius@gmx.net </a> )
 * @since      1.0
 * @created    14 July 2003
 * @version    $Version: $
 */
public class GreyOutMenuFrig {
   private static View view = null;
   private static Object javastyleMenuFound = null;

   private static MenuListener javastyleMenuListener = new JavastyleMenuListener();
   private static MenuListener javastyleMenuListener2 = new JavastyleMenuListener2();

   /**
    *  Description of the Method
    *
    * @since    v 1.0
    */
   public static void checkMenus() {
      view = jEdit.getActiveView();
      if (view == null) {
         return;
      }
      javax.swing.JMenuBar menuBar = view.getJMenuBar();
      javax.swing.MenuElement[] menus = menuBar.getSubElements();
      for (int i = 0; i < menus.length; i++) {
         if (menus[i] instanceof JMenu) {
            String label = ((JMenu)menus[i]).getLabel();
            //System.out.println("menu[" + i + "] = \"" + label + "\"");
            if ("Plugins".equals(label)) {
				if (javastyleMenuFound != menus[i]) {
				   addMenuListener( (JMenu)menus[i], javastyleMenuListener);
				   javastyleMenuFound = menus[i];
				}
                break;
            }
         }
      }

   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson MRA
    * @since     v 1.0
    */
   private static class JavastyleMenuListener implements MenuListener {
      /**
       *  Description of the Method
       *
       * @param  e  Description of Parameter
       * @since     v 1.0
       */
      public void menuCanceled(MenuEvent e) {
         //System.out.println("JavaStyleMenuListener: menuCanceled("+e+")");
      }


      /**
       *  Description of the Method
       *
       * @param  e  Description of Parameter
       * @since     v 1.0
       */
      public void menuDeselected(MenuEvent e) {
         //System.out.println("JavaStyleMenuListener: menuDeselected("+e+")");
      }


      /**
       *  Description of the Method
       *
       * @param  e  Description of Parameter
       * @since     v 1.0
       */
      public void menuSelected(MenuEvent e) {
         //System.out.println("JavaStyleMenuListener: menuSelected(" + e + ")");
         JMenuItem source = (JMenuItem)e.getSource();
		 if (source instanceof JMenu) {
			int count = ((JMenu)source).getItemCount();
		    //System.out.println("  itemCount="+count);
			for (int i=0; i<count; i++) {
				Component item = ((JMenu)source).getMenuComponent(i);
			    if (item !=null && item instanceof JMenu) {
					if ("JavaStyle".equals(((JMenu)item).getLabel())) {
				       addMenuListener( (JMenu)item, javastyleMenuListener2);
					} else {
				       addMenuListener( (JMenu)item, javastyleMenuListener);
					}
			    }
			}
		 }
      }
   }

   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson MRA
    * @since     v 1.0
    */
   private static class JavastyleMenuListener2 implements MenuListener {
      /**
       *  Description of the Method
       *
       * @param  e  Description of Parameter
       * @since     v 1.0
       */
      public void menuCanceled(MenuEvent e) {
         //System.out.println("JavaStyleMenuListener2: menuCanceled("+e+")");
      }


      /**
       *  Description of the Method
       *
       * @param  e  Description of Parameter
       * @since     v 1.0
       */
      public void menuDeselected(MenuEvent e) {
         //System.out.println("JavaStyleMenuListener2: menuDeselected("+e+")");
      }


      /**
       *  Description of the Method
       *
       * @param  e  Description of Parameter
       * @since     v 1.0
       */
      public void menuSelected(MenuEvent e) {
         //System.out.println("JavaStyleMenuListener2: menuSelected("+e+")");
         JMenuItem source = (JMenuItem)e.getSource();
		 if (source instanceof JMenu) {
		    int count = ((JMenu)source).getItemCount();
		    //System.out.println("  itemCount="+count);
			for (int i=0; i<count; i++) {
				Component item = ((JMenu)source).getMenuComponent(i);
			    if (item !=null && item instanceof JMenuItem) {
					String label = ((JMenuItem)item).getLabel();
				    if ("Reformat Buffer".equals(label)) {
					   //System.out.println(" grey out reformat buffer?");
					   Buffer buffer = view.getBuffer();
					   item.setEnabled(buffer.getName().endsWith(".java"));
				    } else if ("Check current buffer".equals(label)) {
					   //System.out.println(" grey out reformat buffer?");
					   Buffer buffer = view.getBuffer();
					   item.setEnabled(buffer.getName().endsWith(".java"));
				    } else if ("In Current File".equals(label)) {
					   //System.out.println(" grey out reformat buffer?");
					   Buffer buffer = view.getBuffer();
					   item.setEnabled(buffer.getName().endsWith(".java"));
					} else if ("Refactor".equals(label)) {
					   //System.out.println(" grey out method refactorings?");
					   try {
					      CurrentSummary cs = CurrentSummary.get();
						  JEditTextArea textArea = view.getTextArea();
					      Object summary = cs.getCurrentSummary();
						  item.setEnabled(summary != null);
					      addMenuListener( (JMenu)item, javastyleMenuListener2);
					   } catch (Exception ex) {
						   item.setEnabled(false);
					   }

					} else if ("Method refactorings".equals(label)) {
					   //System.out.println(" grey out method refactorings?");
					   try {
					      CurrentSummary cs = CurrentSummary.get();
						  JEditTextArea textArea = view.getTextArea();
					      Object summary = cs.getCurrentSummary();
						  if (summary != null && textArea != null) {
							 //System.out.println("JavaStylePlugin.getLineNumber() -> " + textArea.getCaretLine());
							 cs.lineNo = textArea.getCaretLine();
							 item.setEnabled(summary != null && summary instanceof MethodSummary);
						  } else {
  						     item.setEnabled(false);
						  }
					   } catch (Exception ex) {
						   item.setEnabled(false);
					   }

					} else if ("Field refactorings".equals(label)) {
					   //System.out.println(" grey out field refactorings?");
					   try {
					      CurrentSummary cs = CurrentSummary.get();
						  JEditTextArea textArea = view.getTextArea();
					      Object summary = cs.getCurrentSummary();
						  if (summary != null && textArea != null) {
							 //System.out.println("JavaStylePlugin.getLineNumber() -> " + textArea.getCaretLine());
							 cs.lineNo = textArea.getCaretLine();
							 item.setEnabled(summary != null && summary instanceof FieldSummary);
						  } else {
  						     item.setEnabled(false);
						  }
					   } catch (Exception ex) {
						   item.setEnabled(false);
					   }
					} else if (item !=null && item instanceof JMenu) {
					   addMenuListener( (JMenu)item, javastyleMenuListener2);
					}
				}
			}
		 }
	  }
   }
   
   public static void addMenuListener(JMenu item, MenuListener menuListener) {
	   MenuListener[] listeners = item.getMenuListeners();
	   if (listeners==null) {
		   item.addMenuListener(menuListener);
	   } else {
		   for (int i=0; i<listeners.length; i++) {
			   if (listeners[i] == menuListener) {
				   return;
			   }
			   item.addMenuListener(menuListener);
		   }
	   }
   }
}

