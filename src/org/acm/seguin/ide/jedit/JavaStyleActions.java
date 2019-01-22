/*
    :tabSize=4:indentSize=4:noTabs=false:
    :folding=explicit:collapseFolds=1:
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more detaProjectTreeSelectionListenerils.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
  */
package org.acm.seguin.ide.jedit;

import java.io.File;
import java.io.IOException;
import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.GUIUtilities;

import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.io.VFSManager;
import org.gjt.sp.util.Log;



/**
 *  A collection of actions accessible through jEdit's Action mechanism, and other utility methods that may be
 *  interesting for interacting with the plugin.
 *
 * @author     <a href="mailto:JRefactoryPlugin@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @since      0.0.1
 * @created    23 July 2003
 * @version    $Id: JavaStyleActions.java,v 1.5 2003/12/02 23:39:37 mikeatkinson Exp $
 */
public final class JavaStyleActions {

   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    * @since        2.9.12
    */
   private static void openJRefactory(View view) {
      view.getDockableWindowManager().showDockableWindow("javastyle");
      Object pv = view.getDockableWindowManager().getDockable("javastyle");
      ((org.acm.seguin.ide.jedit.JRefactory)pv).setVisible("JRefactory");
   }


   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    * @since        2.9.12
    */
   private static void openCodingStandards(View view) {
      view.getDockableWindowManager().showDockableWindow("javastyle");
      Object pv = view.getDockableWindowManager().getDockable("javastyle");
      ((org.acm.seguin.ide.jedit.JRefactory)pv).setVisible("Coding standards");
   }



   /**
    * @param  view      the view; may be null, if there is no current view
    * @param  buffer    the buffer containing the java source code
    * @param  silently  if true, no error dialogs are shown
    * @since            v 1.0
    */
   public static void beautify(View view, Buffer buffer, boolean silently) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.instanceBeautify(view, buffer, silently);
   }


   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    * @since        v 1.0
    */
   public static void optionDialog(View view) {
      org.acm.seguin.ide.common.IDEPlugin.setPlugin(new JavaStylePlugin());
      new org.acm.seguin.ide.common.options.JSOptionDialog(view);
   }


   /**
    *  check current buffer
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void check(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
	  openCodingStandards(view);
      JavaStylePlugin.jsPlugin.instanceCheck(view, buffer, false);
   }


   /**
    *  check current directory (of the File System Browser)
    *
    * @param  view  Description of Parameter
    * @since        v 1.0
    */
   public static void checkDirectory(View view) {
      JavaStylePlugin.initJSPlugin();
	  openCodingStandards(view);
      JavaStylePlugin.jsPlugin.instanceCheckDirectory(view, false);
   }


   /**
    *  check all open buffers
    *
    * @param  view  Description of Parameter
    * @since        v 1.0
    */
   public static void checkAllOpenBuffers(View view) {
      JavaStylePlugin.initJSPlugin();
	  openCodingStandards(view);
      JavaStylePlugin.jsPlugin.instanceCheckAllOpenBuffers(view);
   }


   /**
    *  check directory recursively
    *
    * @param  view  Description of Parameter
    * @since        v 1.0
    */
   public static void checkDirectoryRecursively(View view) {
      JavaStylePlugin.initJSPlugin();
	  openCodingStandards(view);
      JavaStylePlugin.jsPlugin.instanceCheckDirectory(view, true);
   }


   /**
    *  clear error list
    *
    * @since    v 1.0
    */
   public static void clearErrorList(View view) {
      JavaStylePlugin.initJSPlugin();
	  openCodingStandards(view);
      JavaStylePlugin.jsPlugin.instanceClearErrorList();
   }


   /**
    * @param  view  Description of the Parameter
    * @since        v 1.0
    */
   public static void loadUML(View view) {
      JavaStylePlugin.initJSPlugin();
      openJRefactory(view);
      JavaStylePlugin.jsPlugin.loadUML(view, null);
   }


   /**
    * @param  view  Description of the Parameter
    * @param  file  Description of Parameter
    * @since        v 1.0
    */
   public static void loadUML(View view, File file) {
      try {
         JavaStylePlugin.initJSPlugin();
         openJRefactory(view);
         JavaStylePlugin.jsPlugin.loadUML(view, (file == null) ? null : file.getCanonicalPath());
      } catch (IOException e) {
      }
   }


   /**
    * @param  view  Description of the Parameter
    * @param  file  Description of Parameter
    * @since        v 1.0
    */
   public static void loadUML(View view, String file) {
      JavaStylePlugin.initJSPlugin();
      openJRefactory(view);
      JavaStylePlugin.jsPlugin.loadUML(view, file);
   }


   /**
    * @param  view  Description of the Parameter
    * @param  file  Description of Parameter
    * @since        v 1.0
    */
   public static void openUML(View view, File file) {
      try {
         JavaStylePlugin.initJSPlugin();
         openJRefactory(view);
         JavaStylePlugin.jsPlugin.openUML(view, file.getCanonicalPath());
      } catch (IOException e) {
      }
   }


   /**
    * @param  view  Description of the Parameter
    * @param  file  Description of Parameter
    * @since        v 1.0
    */
   public static void openUML(View view, String file) {
      JavaStylePlugin.initJSPlugin();
      openJRefactory(view);
      JavaStylePlugin.jsPlugin.openUML(view, file);
   }


   /**
    * @param  view  Description of the Parameter
    * @since        v 1.0
    */
   public static void openAllProjectFiles(View view) {
      JRefactory viewer = JRefactory.getViewer(view);
      openJRefactory(view);
   }


   /**
    * @param  view  Description of the Parameter
    * @since        v 1.0
    */
   public static void removeAllProjectFiles(View view) {
      JRefactory viewer = JRefactory.getViewer(view);
      openJRefactory(view);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void renameClass(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.renameClass(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void moveClassTo(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.moveClassTo(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  The feature to be added to the abstractParentClass attribute
    * @since          v 1.0
    */
   public static void addAbstractParentClass(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.addAbstractParentClass(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  The feature to be added to the childClass attribute
    * @since          v 1.0
    */
   public static void addChildClass(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.addChildClass(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void removeClass(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.removeClass(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void extractInterface(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.extractInterface(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void renameVariablesUsingHungarian(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.renameVariablesUsingHungarian(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void renameMethod(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.renameMethod(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void pushUpAbstractMethod(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.pushUpAbstractMethod(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void pushDownMethod(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.pushDownMethod(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void moveMethod(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.moveMethod(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void renameMethodParameters(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.renameMethodParameters(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void renameField(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.renameField(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void pushUpField(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.pushUpField(view, buffer);
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public static void pushDownField(View view, Buffer buffer) {
      JavaStylePlugin.initJSPlugin();
      JavaStylePlugin.jsPlugin.pushDownField(view, buffer);
   }

}

