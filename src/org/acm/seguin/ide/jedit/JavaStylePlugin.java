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

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.TreeNode;
import errorlist.DefaultErrorSource;

import errorlist.ErrorSource;
import net.sourceforge.jrefactory.action.*;

import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.parser.JavaParser;

import org.acm.seguin.JRefactoryVersion;
import org.acm.seguin.ide.common.CPDDuplicateCodeViewer;
import org.acm.seguin.ide.common.CodingStandardsViewer;
import org.acm.seguin.ide.common.IDEInterface;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.ide.common.options.PropertiesFile;
import org.acm.seguin.ide.common.options.SelectedRules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.PMDException;
import org.acm.seguin.pmd.Report;
import org.acm.seguin.pmd.RuleContext;
import org.acm.seguin.pmd.RuleSetNotFoundException;
import org.acm.seguin.pmd.RuleViolation;
import org.acm.seguin.pmd.cpd.CPD;
import org.acm.seguin.pmd.cpd.CPPLanguage;
import org.acm.seguin.pmd.cpd.FileFinder;
import org.acm.seguin.pmd.cpd.JavaLanguage;
import org.acm.seguin.pmd.cpd.Mark;
import org.acm.seguin.pmd.cpd.Match;
import org.acm.seguin.pmd.cpd.PHPLanguage;
import org.acm.seguin.tools.RefactoryInstaller;
import org.acm.seguin.util.FileSettings;

import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EBPlugin;
import org.gjt.sp.jedit.EditBus;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.browser.VFSBrowser;
import org.gjt.sp.jedit.io.VFS;
import org.gjt.sp.jedit.io.VFSManager;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.msg.*;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.gjt.sp.jedit.textarea.Selection;
import org.gjt.sp.util.Log;



/**
 *  A plugin for pretty printing the current jEdit buffer, using the PrettyPrinter of the JREFactory suite.
 *
 * @author     Mike Atkinson (<a href="mailto:javastyle@ladyshot.demon.co.uk"> Mike@ladyshot.demon.co.uk</a> )
 * @author     Dirk Moebius (<a href="mailto:dmoebius@gmx.net"> dmoebius@gmx.net </a> )
 * @since      1.0
 * @created    14 July 2003
 * @version    $Version: $
 */
public class JavaStylePlugin extends EBPlugin implements IDEInterface {
   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public final static String OPTION_RULES_PREFIX = "options.pmd.rules.";
   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public final static String OPTION_UI_DIRECTORY_POPUP = "pmd.ui.directorypopup";
   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public final static String DEFAULT_TILE_MINSIZE_PROPERTY = "pmd.cpd.defMinTileSize";
   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public final static String NAME = "javastyle";
   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public final static String JAVASTYLE_DIR = jEdit.getSettingsDirectory() + File.separator + "javastyle";

   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public final static File PRETTY_SETTINGS_FILE = new File(JAVASTYLE_DIR + File.separator + ".Refactory", "pretty.settings");

   /**
    *  Description of the Field
    *
    * @since    v 1.0
    */
   public static JavaStylePlugin jsPlugin = null;
   private static DefaultErrorSource errorSource = null;
   private static PropertiesFile properties = null;
   private static Properties ideProperties = new Properties();
   private static Map propertiesMap = new HashMap();

   private static List navigators = new ArrayList();



   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  The new Selection value
    * @param  start   The new Selection value
    * @param  end     The new Selection value
    * @since          v 1.0
    */
   public void setSelection(Frame view, Object buffer, int start, int end) {
      ((View)view).getTextArea().setSelection(new Selection.Range(start, end - 1));
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  The new Buffer value
    * @since          v 1.0
    */
   public void setBuffer(Frame view, Object buffer) {
      ((View)view).setBuffer((Buffer)buffer);
   }


   /**
    *  Sets the string in the IDE
    *
    * @param  view    The frame containing the IDE.
    * @param  buffer  Description of Parameter
    * @param  value   The new file contained in a string
    * @since          v 1.0
    */
   public void setText(Frame view, Object buffer, String value) {
      Buffer buf = (Buffer)buffer;
      if (buf != null && value != null && buf.isEditable()) {
         buf.writeLock();
         buf.beginCompoundEdit();
         buf.remove(0, buf.getLength());
         buf.insert(0, value);
         buf.endCompoundEdit();
         buf.writeUnlock();
      }
   }


   /**
    *  Sets the line number
    *
    * @param  view        The new lineNumber value
    * @param  buffer      The new lineNumber value
    * @param  lineNumber  The new lineNumber value
    * @since              2.9.12
    */
   public void setLineNumber(Frame view, Object buffer, int lineNumber) {
      JEditTextArea textArea = ((View)view).getTextArea();
      try {
         textArea.setCaretPosition(textArea.getLineStartOffset(lineNumber - 1));
      } catch (Exception e) {
         textArea.getToolkit().beep();
      }
   }


   /**
    *  Gets the IDEProperty attribute of the JavaStylePlugin object
    *
    * @param  prop  Description of Parameter
    * @return       The IDEProperty value
    * @since        v 1.0
    */
   public String getIDEProperty(String prop) {
      String value = null;
      try {
         value = jEdit.getProperty(prop);
         if (value == null || value.equals("")) {
            value = ideProperties.getProperty(prop);
         }
      } catch (Exception e) {
         Log.log(Log.ERROR, JavaStylePlugin.class, "can't find IDE property " + prop);
         value = ideProperties.getProperty(prop);
      }
      return (value == null) ? "<none>" : value;
   }


   /**
    *  Gets the IDEProperty attribute of the JavaStylePlugin object
    *
    * @param  prop   Description of Parameter
    * @param  deflt  Description of Parameter
    * @return        The IDEProperty value
    * @since         v 1.0
    */
   public String getIDEProperty(String prop, String deflt) {
      if (prop == null) {
         Log.log(Log.ERROR, JavaStylePlugin.class, "IDE property == null");
         return null;
      }
      String value = null;
      try {
         value = jEdit.getProperty(prop);
         if (value == null || value.equals("")) {
            value = ideProperties.getProperty(prop);
         }
      } catch (Exception e) {
         Log.log(Log.ERROR, JavaStylePlugin.class, "can't find IDE property " + prop);
         value = ideProperties.getProperty(prop);
      }
      return (value == null) ? deflt : value;
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @return         The File path for this buffer
    * @since          v 1.0
    */
   public String getFilePathForBuffer(Object buffer) {
      return ((Buffer)buffer).getPath();
   }


   /**
    *  Description of the Method
    *
    * @param  parent  Description of Parameter
    * @return         The IDEProjects value
    * @since          v 1.0
    */
   public String[] getIDEProjects(java.awt.Frame parent) {
      try {
         Class clazz = Class.forName("projectviewer.ProjectViewer");
         //projectviewer.ProjectViewer viewer = projectviewer.ProjectViewer.getViewer((View) parent);
         projectviewer.ProjectManager manager = projectviewer.ProjectManager.getInstance();
         List projs = new ArrayList();

         projs.add("default");
         for (Iterator i = manager.getProjects(); i.hasNext(); ) {
            projectviewer.vpt.VPTProject proj = (projectviewer.vpt.VPTProject)i.next();
            String name = proj.getName();

            projs.add(name);
         }
         return (String[])projs.toArray(new String[projs.size()]);
      } catch (ClassNotFoundException e) {
      }
      return new String[0];
   }


   /**
    *  Gets the CPDDuplicateCodeViewer attribute of the JavaStylePlugin object
    *
    * @param  view  Description of Parameter
    * @return       The CPDDuplicateCodeViewer value
    * @since        v 1.0
    */
   public CPDDuplicateCodeViewer getCPDDuplicateCodeViewer(View view) {
      view.getDockableWindowManager().showDockableWindow("javastyle");

      JRefactory refactory = (JRefactory)view.getDockableWindowManager().getDockableWindow("javastyle");

      return refactory.getCPDDuplicateCodeViewer();
   }


   /**
    *  Gets the Properties attribute of the JavaStylePlugin class
    *
    * @param  type     Description of Parameter
    * @param  project  Description of Parameter
    * @return          The Properties value
    * @since           v 1.0
    */
   public PropertiesFile getProperties(String type, String project) {
      //System.out.println("getProperties(" + type+","+project + ")");
      String key = ("default".equals(project)) ? type + "::null" : type + "::" + project;
      PropertiesFile projectProperties = (PropertiesFile)propertiesMap.get(key);

      //System.out.println("  key="+key+" ->projectProperties="+projectProperties);
      if (projectProperties == null) {
         //System.out.println("  getting Properties(FileSettings.getSettings("+project+", \"Refactory\", "+type+")");
         projectProperties = new PropertiesFile(org.acm.seguin.util.FileSettings.getSettings(project, "Refactory", type));
         propertiesMap.put(key, projectProperties);
      }
      return projectProperties;
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @param  begin   Description of Parameter
    * @return         The BeginLine value
    * @since          v 1.0
    */
   public int getLineStartOffset(Object buffer, int begin) {
      return ((Buffer)buffer).getLineStartOffset(begin);
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @param  end     Description of Parameter
    * @return         The LineEndOffset value
    * @since          v 1.0
    */
   public int getLineEndOffset(Object buffer, int end) {
      return ((Buffer)buffer).getLineEndOffset(end);
   }



   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The Text value
    * @since          v 1.0
    */
   public String getText(Frame view, Object buffer) {
      Buffer buffer2 = (buffer == null) ? ((View)view).getBuffer() : (Buffer)buffer;
      System.out.println("getText(" + buffer + ")");
      return buffer2.getText(0, buffer2.getLength());
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @return         The LineCount value
    * @since          v 1.0
    */
   public int getLineCount(Object buffer) {
      return ((Buffer)buffer).getLineCount();
   }



   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The ProjectName value
    * @since          v 1.0
    */
   public String getProjectName(Frame view, Object buffer) {
      return getProjectName((View)view, (Buffer)buffer);
   }


   /**
    *  Returns the frame that contains the editor. If this is not available or you want dialog boxes to be centered on
    *  the screen return null from this operation.
    *
    * @return    the frame
    * @since     v 1.0
    */
   public Frame getEditorFrame() {
      return jEdit.getActiveView();
   }


   /**
    *  Get the current (atcive) buffer.
    *
    * @param  view  The frame containing the IDE.
    * @return       The active buffer or null if no active buffer.
    * @since        v 1.0
    */
   public Object getCurrentBuffer(Frame view) {
      return (view == null) ? null : ((View)view).getBuffer();
   }


   /**
    *  Get the line number of the cursor within the current buffer.
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The ine number of the cursor in the current buffer, or -1 if no current buffer.
    * @since          v 1.0
    */
   public int getLineNumber(Frame view, Object buffer) {
      if (view != null) {
         JEditTextArea textArea = ((View)view).getTextArea();
         if (textArea != null) {
            System.out.println("JavaStylePlugin.getLineNumber() -> " + textArea.getCaretLine());
            return textArea.getCaretLine();
         }
      }
      return -1;
   }


   /**
    *  Gets the file that is being edited
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The File value
    * @since          v 1.0
    */
   public File getFile(Frame view, Object buffer) {
      return (buffer == null) ? null : new File(((Buffer)buffer).getPath());
   }


   /**
    *  Indicates that a buffer has been parsed and that an Abstract Syntax Tree is available.
    *
    * @param  view             The frame containing the IDE.
    * @param  buffer           The buffer (containing Java Source) that has been parsed.
    * @param  compilationUnit  The root node of the AST.
    * @since                   2.9.12
    */
   public void bufferParsed(Frame view, Object buffer, Node compilationUnit) {
      EditBus.send(new JavaAST(this, (Buffer)buffer, compilationUnit));
   }


   /**
    *  Indicates that a buffer has been parsed and that a navigator tree of the source is available.
    *
    * @param  view    The frame containing the IDE.
    * @param  buffer  The buffer (containing Java Source) that has been parsed.
    * @param  node    The root node of the tree.
    * @since          2.9.12
    */
   public void bufferNavigatorTree(Frame view, Object buffer, TreeNode node) {
      EditBus.send(new JavaTree(this, (Buffer)buffer, node));
   }


   /**
    *  Load an icon from the IDE
    *
    * @param  name  The name of the icon.
    * @return       An icon (or null if the icon cannot be found).
    * @since        v 1.0
    */
   public Icon loadIcon(String name) {
      Icon icon = GUIUtilities.loadIcon(name);
      if (icon == null) {
         ClassLoader classLoader = this.getClass().getClassLoader();
         icon = new ImageIcon(classLoader.getResource(name));
      }
      System.out.println("icon=" + icon);
      return icon;
   }


   /**
    *  Does the buffer contain Java source code.
    *
    * @param  view    The frame containing the IDE.
    * @param  buffer  Description of Parameter
    * @return         <code>true</code> if the buffer contains Java source code, <code>false</code> otherwise.
    * @since          v 1.0
    */
   public boolean bufferContainsJavaSource(Frame view, Object buffer) {
      return (buffer == null) ? false : ((Buffer)buffer).getName().endsWith(".java");
   }


   /**
    *  Description of the Method
    *
    * @param  message  Description of Parameter
    * @since           v 1.0
    */
   public void handleMessage(EBMessage message) {
      GreyOutMenuFrig.checkMenus();
      if (message instanceof ViewUpdate
               || message instanceof BufferUpdate
               || message instanceof EditPaneUpdate) {
         synchronized (navigators) {
            for (Iterator i = navigators.iterator(); i.hasNext(); ) {
               Navigator nav = (Navigator)i.next();
               nav.handleMessage(message);
            }
         }
      }

      if (message instanceof BufferUpdate) {
         BufferUpdate update = (BufferUpdate)message;

         String name = update.getBuffer().getName();
         if (update.getWhat() == BufferUpdate.SAVING) {
            if (name.endsWith(".java")) {
               String project = getProjectName(update.getView(), update.getBuffer());
               PropertiesFile props = getProperties("pretty", project);
               if (props.getBoolean("formatOnSave", false)) {
                  JavaStyleActions.beautify(update.getView(), update.getBuffer(), true);
               } else if (props.getBoolean("checkOnSave", false)) {
                  instanceCheck(update.getView(), update.getBuffer(), true);
               } else {
                  JavaStylePlugin.getErrorSource().clear();
               }
            } else if (name.endsWith(".jsp")) {
               String project = getProjectName(update.getView(), update.getBuffer());
               PropertiesFile props = getProperties("pretty", project);
               if (props.getBoolean("formatOnSave", false)) {
                  JavaStyleActions.beautify(update.getView(), update.getBuffer(), true);
               }
            }
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @since    v 1.0
    */
   public void start() {
      if (jEdit.getSettingsDirectory() == null) {
         // no settings directory. jEdit was invoked with the
         // 'nosettings' option. The action will not be added,
         // meaning it will be disabled in the Plugins menu.
         Log.log(Log.DEBUG, this, "javastyle action disabled because "
                  + "settings dir not found!");
         return;
      }

      // check whether JavaStyle is invoked for the first time
      boolean firstTime = !PRETTY_SETTINGS_FILE.exists();

      // point PrettyPrinter to the location of the settings file
      FileSettings.setSettingsRoot(JAVASTYLE_DIR);
      // install the setting files (update new properties if already there)
      (new RefactoryInstaller(false)).run();

      properties = getProperties("pretty", null);
      try {
         ideProperties.load(getClass().getResourceAsStream("/ui/JavaStyle.props"));
      } catch (java.io.IOException e) {
         e.printStackTrace();
      }

      // if JavaStyle is invoked for the first time, we need to
      // correct some default values:
      if (firstTime) {
         setDefaultValues();
      }

      org.acm.seguin.ide.common.IDEPlugin.setPlugin(this);
      //JavaParser.setTargetJDK("1.4.0");

      jsPlugin = this;

      errorSource = new DefaultErrorSource(NAME);
      ErrorSource.registerErrorSource(errorSource);

      // print some version info
      String jversion = new JRefactoryVersion().toString();
      String fversion = properties.getString("version");

      Log.log(Log.NOTICE, this, "JRefactory version: " + jversion);
      Log.log(Log.NOTICE, this, "pretty settings file version: " + fversion);
      GreyOutMenuFrig.checkMenus();
   }


   /**
    *  Description of the Method
    *
    * @param  parent  Description of Parameter
    * @since          v 1.0
    */
   public void showWaitCursor(java.awt.Frame parent) {
      ((View)parent).showWaitCursor();
   }


   /**
    *  Description of the Method
    *
    * @param  parent  Description of Parameter
    * @since          v 1.0
    */
   public void hideWaitCursor(java.awt.Frame parent) {
      ((View)parent).hideWaitCursor();
   }


   /**
    *  Description of the Method
    *
    * @param  urgency  Description of Parameter
    * @param  source   Description of Parameter
    * @param  message  Description of Parameter
    * @since           v 1.0
    */
   public void log(int urgency, Object source, Object message) {
      Log.log(urgency, source, message);
   }


   /**
    *  check current buffer
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @param  silent  Description of Parameter
    * @since          v 1.0
    */
   public void instanceCheck(View view, Buffer buffer, boolean silent) {
      errorSource.clear();
      String name = buffer.getName();
      //System.out.println("buffer.getName()=" + name);
      if (name != null && name.endsWith(".java")) {
         JRefactory refactory = (JRefactory)view.getDockableWindowManager().getDockableWindow("javastyle");
         RuleContext ctx = null;
         if (refactory == null) {
            ctx = checkBuffer(view, buffer);
         } else {
            CodingStandardsViewer csViewer = refactory.getCodingStandardsViewer();
            ctx = csViewer.check(view, buffer, true);
         }
         if (!silent && ctx.getReport().isEmpty()) {
            JOptionPane.showMessageDialog(jEdit.getFirstView(), "No problems found", NAME, JOptionPane.INFORMATION_MESSAGE);
         } else {
            String path = buffer.getPath();
            for (Iterator i = ctx.getReport().iterator(); i.hasNext(); ) {
               RuleViolation ruleViolation = (RuleViolation)i.next();
               errorSource.addError(ErrorSource.WARNING, path, ruleViolation.getLine() - 1, 0, 0, ruleViolation.getDescription());
            }
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view       Description of Parameter
    * @param  recursive  Description of Parameter
    * @since             v 1.0
    */
   public void instanceCheckDirectory(View view, boolean recursive) {
      process(getFileList(view, recursive), view);
   }


   /**
    *  check all open buffers
    *
    * @param  view  Description of Parameter
    * @since        v 1.0
    * @paramview    Description of Parameter
    * @paramview    Description of Parameter
    */
   public void instanceCheckAllOpenBuffers(View view) {
      // I'm putting the files in a Set to work around some
      // odd behavior in jEdit - the buffer.getNext()
      // seems to iterate over the files twice.
      Set fileSet = new HashSet();
      Buffer buffer = jEdit.getFirstBuffer();

      while (buffer != null) {
         if (buffer.getName().endsWith(".java")) {
            fileSet.add(buffer.getFile());
         }
         buffer = buffer.getNext();
      }

      List files = new ArrayList();

      files.addAll(fileSet);
      process(files, view);
   }



   /**
    *  clear error list
    *
    * @since    v 1.0
    */
   public void instanceClearErrorList() {
      errorSource.clear();
   }


   /**
    *  Description of the Method
    *
    * @param  view   Description of Parameter
    * @param  entry  Description of Parameter
    * @since         v 1.0
    */
   public void checkFile(View view, VFS.DirectoryEntry entry[]) {
      if (view != null && entry != null) {
         List files = new ArrayList();

         for (int i = 0; i < entry.length; i++) {
            if (entry[i].type == VFS.DirectoryEntry.FILE) {
               files.add(new File(entry[i].path));
            }
         }

         //System.out.println("See final files to process " + files);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  buffer           Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public void cpdBuffer(Frame view, Object buffer) throws IOException {
      JavaStylePlugin.cpdCurrentFile((View)view);
   }


   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public void cpdAllOpenBuffers(Frame view) throws IOException {
      JavaStylePlugin.cpdAllOpenBuffers((View)view, true);
   }


   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  recursive        Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public void cpdDir(Frame view, boolean recursive) throws IOException {
      JavaStylePlugin.cpdDir((View)view, recursive);
   }



   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  fileName         Description of Parameter
    * @return                  Description of the Returned Value
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public Object openFile(Frame view, String fileName) throws IOException {
      if (fileName == null || fileName.equals("")) {
         fileName = ((View)view).getBuffer().getPath();
      }
      return jEdit.openFile((View)view, fileName);
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @param  start   Description of Parameter
    * @since          v 1.0
    */
   public void moveCaretPosition(Frame view, Object buffer, int start) {
      ((View)view).getTextArea().moveCaretPosition(start);
   }


   /**
    *  Description of the Method
    *
    * @param  runnable  Description of Parameter
    * @since            v 1.0
    */
   public void runInAWTThread(Runnable runnable) {
      VFSManager.runInAWTThread(runnable);
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void checkBuffer(Frame view, Object buffer) {
      //org.acm.seguin.ide.jedit.JavaStyleActions.check((View)view, ((View)view).getBuffer());
      instanceCheck((View)view, ((View)view).getBuffer(), false);
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void check(View view, Buffer buffer) {
      //org.acm.seguin.ide.jedit.JavaStyleActions.check((View) view, ((View) view).getBuffer());
      instanceCheck(view, buffer, false);
   }


   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    * @since        v 1.0
    */
   public void checkAllOpenBuffers(Frame view) {
      //org.acm.seguin.ide.jedit.JavaStyleActions.checkAllOpenBuffers((View)view);
      instanceCheckAllOpenBuffers((View)view);
   }


   /**
    *  Description of the Method
    *
    * @param  view       Description of Parameter
    * @param  recursive  Description of Parameter
    * @since             v 1.0
    */
   public void checkDirectory(Frame view, boolean recursive) {
      //org.acm.seguin.ide.jedit.JavaStyleActions.checkDirectory((View)view);
      instanceCheckDirectory((View)view, recursive);
   }



   /**
    * @param  view      the view; may be null, if there is no current view
    * @param  buffer    the buffer containing the java source code
    * @param  silently  if true, no error dialogs are shown
    * @since            v 1.0
    */
   public void instanceBeautify(View view, Buffer buffer, boolean silently) {
      try {
         // does the current buffer contains Java source code?
         String bufferFilename = buffer.getName().toLowerCase();
         String bufferMode = buffer.getMode().getName();
         if (bufferFilename.endsWith(".jsp") || bufferMode.equals("jsp")) {
            setUpForBeautify(view, buffer, silently);
            // run the format routine synchronously on the AWT thread:
            VFSManager.runInAWTThread(new JEditJSPPrettyPrinter(JavaStylePlugin.jsPlugin, view, buffer));
         } else if (bufferFilename.endsWith(".java") || bufferMode.equals("java")) {
            setUpForBeautify(view, buffer, silently);
            // run the format routine synchronously on the AWT thread:
            VFSManager.runInAWTThread(new JEditPrettyPrinter(JavaStylePlugin.jsPlugin, view, buffer));
         } else {
            if (!silently) {
               GUIUtilities.error(view, "javastyle.error.noJavaBuffer", null);
            }
            return;
         }
      } catch (Throwable e) {
         e.printStackTrace();
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void goToBuffer(Frame view, Object buffer) {
      ((View)view).goToBuffer((Buffer)buffer);
   }


   /**
    *  write new settings
    *
    * @since    v 1.0
    */
   public void saveProperties() {
      try {
         jEdit.propertiesChanged();
         jEdit.saveSettings();
         properties.save(PRETTY_SETTINGS_FILE);
      } catch (IOException ioex) {
         Log.log(Log.ERROR, JavaStylePlugin.class,
                  "Error saving file "
                  + PRETTY_SETTINGS_FILE + ": " + ioex);
      }

      for (Iterator i = propertiesMap.keySet().iterator(); i.hasNext(); ) {
         PropertiesFile projectProperties = (PropertiesFile)propertiesMap.get(i.next());
         projectProperties.save();
      }
   }


   /**
    * @param  view      Description of the Parameter
    * @param  fileName  Description of Parameter
    * @since            v 2.9.12
    */
   public void loadUML(View view, String fileName) {
      JRefactory viewer = JRefactory.getViewer(view);
      (new LoadPackageAction(fileName)).actionPerformed(new ActionEvent(viewer, -1, "load"));
   }


   /**
    * @param  view      Description of the Parameter
    * @param  fileName  Description of Parameter
    * @since            v 2.9.12
    */
   public void openUML(View view, String fileName) {
      try {
         Buffer buffer = null;
         if (fileName != null) {
            buffer = (Buffer)openFile(view, fileName);
         }
         if (buffer == null) {
            buffer = view.getBuffer();
         }
         if (buffer != null && buffer.getPath().endsWith(".java")) {
            JRefactory viewer = JRefactory.getViewer(view);
            (new LoadPackageAction(buffer.getPath())).actionPerformed(new ActionEvent(viewer, -1, "load"));
         }
      } catch (IOException e) {
      }
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void renameClass(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.renameClass(view," + buffer + ")");
      System.out.flush();
      RenameClassAction renameClassAction = new RenameClassAction(new JEditSelectedFileSet(view, buffer));
      renameClassAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "renameClass"));
      System.out.println("JavaStylePlugin.renameClass(view," + buffer + ") - end");
      System.out.flush();
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void moveClassTo(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.moveClassTo(view," + buffer + ")");
      MoveClassAction moveClassAction = new MoveClassAction(new JEditSelectedFileSet(view, buffer));
      moveClassAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "moveClass"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  The feature to be added to the abstractParentClass attribute
    * @since          v 1.0
    */
   public void addAbstractParentClass(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.addAbstractParentClass(view," + buffer + ")");
      AddParentClassAction addAbstractParentClassAction = new AddParentClassAction(new JEditSelectedFileSet(view, buffer));
      addAbstractParentClassAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "addAbstractParentClass"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void removeClass(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.removeClass(view," + buffer + ")");
      RemoveClassAction removeClassAction = new RemoveClassAction(new JEditSelectedFileSet(view, buffer));
      removeClassAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "removeClass"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  The feature to be added to the childClass attribute
    * @since          v 1.0
    */
   public void addChildClass(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.addChildClass(view," + buffer + ")");
      AddChildClassAction addChildClassAction = new AddChildClassAction(new JEditSelectedFileSet(view, buffer));
      addChildClassAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "addChildClass"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void extractInterface(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.extractInterface(view," + buffer + ")");
      ExtractInterfaceAction extractInterfaceAction = new ExtractInterfaceAction(new JEditSelectedFileSet(view, buffer));
      extractInterfaceAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "extractInterface"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void renameVariablesUsingHungarian(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.renameVariablesUsingHungarian(view," + buffer + ")");
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void pushUpMethod(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.pushUpMethod(view," + buffer + ")");
      PushUpMethodAction pushUpMethodAction = new PushUpMethodAction();
      pushUpMethodAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "pushUpMethod"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void pushUpAbstractMethod(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.pushUpAbstractMethod(view," + buffer + ")");
      PushUpAbstractMethodAction pushUpAbstractMethodAction = new PushUpAbstractMethodAction();
      pushUpAbstractMethodAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "pushUpAbstractMethod"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void renameMethod(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.renameMethod(view," + buffer + ")");
      RenameMethodAction renameMethodAction = new RenameMethodAction();
      renameMethodAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "renameMethod"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void pushDownMethod(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.pushDownMethod(view," + buffer + ")");
      PushDownMethodAction pushDownMethodAction = new PushDownMethodAction();
      pushDownMethodAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "pushDownMethod"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void moveMethod(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.moveMethod(view," + buffer + ")");
      MoveMethodAction moveMethodAction = new MoveMethodAction();
      moveMethodAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "moveMethod"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void renameMethodParameters(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.renameMethodParameters(view," + buffer + ")");
      RenameParameterAction renameMethodParametersAction = new RenameParameterAction();
      renameMethodParametersAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "renameMethodParametersAction"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void renameField(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.renameField(view," + buffer + ")");
      RenameFieldAction renameFieldAction = new RenameFieldAction();
      renameFieldAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "renameField"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void pushUpField(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.pushUpField(view," + buffer + ")");
      PushUpFieldAction pushUpFieldAction = new PushUpFieldAction();
      pushUpFieldAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "pushUpField"));
   }


   /**
    * @param  view    Description of the Parameter
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void pushDownField(View view, Buffer buffer) {
      System.out.println("JavaStylePlugin.pushDownField(view," + buffer + ")");
      PushDownFieldAction pushDownFieldAction = new PushDownFieldAction();
      pushDownFieldAction.actionPerformed(new java.awt.event.ActionEvent(buffer, -1, "pushDownField"));
   }


   /**
    *  Sets the upForBeautify attribute of the JavaStylePlugin object
    *
    * @param  view      The new upForBeautify value
    * @param  buffer    The new upForBeautify value
    * @param  silently  The new upForBeautify value
    * @since            v 1.0
    */
   private void setUpForBeautify(View view, Buffer buffer, boolean silently) {
      int charStreamType = net.sourceforge.jrefactory.io.CharStream.UNICODE;
      try {
         FileSettings bundle = FileSettings.getRefactoryPrettySettings();
         charStreamType = bundle.getInteger("char.stream.type");
         JavaParser.setTargetJDK(bundle.getString("jdk"));
      } catch (Exception e) {
         JavaParser.setTargetJDK("1.4.2");
      }
      net.sourceforge.jrefactory.io.CharStream.setCharStreamType(charStreamType);
      // is the current buffer editable?
      if (buffer.isReadOnly()) {
         Log.log(Log.DEBUG, JavaStylePlugin.class, "the buffer is read-only: " + buffer);
         if (!silently) {
            GUIUtilities.error(view, "javastyle.error.isNotEditable", null);
         }
         return;
      }
   }


   /**
    *  Sets the DefaultValues attribute of the JavaStylePlugin object
    *
    * @since    v 1.0
    */
   private void setDefaultValues() {
      // these default settings need to be corrected:
      setProperty("end.line", "NL");
      // jEdit requires this
      setProperty("space.before.javadoc", "true");
      // default (false) looks odd

   }


   /**
    *  Gets the FileList attribute of the JavaStylePlugin object
    *
    * @param  view       Description of Parameter
    * @param  recursive  Description of Parameter
    * @return            The FileList value
    * @since             v 1.0
    */
   private List getFileList(View view, boolean recursive) {
      try {
         String file = getBrowserDirectory(view);
         if (file != null) {
            jEdit.setProperty("pmd.cpd.lastDirectory", file);
            return findFiles(file, recursive);
         } else {
            JFileChooser chooser = new JFileChooser(jEdit.getProperty("pmd.cpd.lastDirectory"));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
               File dir = chooser.getSelectedFile();
               if (dir != null) {
                  if (!dir.exists() || !dir.isDirectory()) {
                     JOptionPane.showMessageDialog(jEdit.getFirstView(), dir + " is not a valid directory name", NAME, JOptionPane.ERROR_MESSAGE);
                     return new ArrayList();
                  }
                  jEdit.setProperty("pmd.cpd.lastDirectory", dir.getCanonicalPath());
                  return findFiles(dir.getCanonicalPath(), recursive);
               }
            } else {
               return new ArrayList();
            }
         }
      } catch (java.io.IOException e) {
         e.printStackTrace();
      }
      return new ArrayList();
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         Description of the Returned Value
    * @since          v 1.0
    */
   private RuleContext checkBuffer(View view, Buffer buffer) {
      //System.out.println("checkBuffer(view, " + buffer + ")");
      RuleContext ctx = new RuleContext();
      ctx.setReport(new Report());
      ctx.setSourceCodeFilename("");

      try {
         PMD pmd = new PMD();
         SelectedRules selectedRuleSets = new SelectedRules(getProjectName(view, buffer), view);
         pmd.processFile(new java.io.StringReader(getText(view, buffer)), selectedRuleSets.getSelectedRules(), ctx);
      } catch (RuleSetNotFoundException rsne) {
         rsne.printStackTrace();
      } catch (PMDException pmde) {
         pmde.printStackTrace();
         JOptionPane.showMessageDialog(view, "Error while processing " + "<no path>");
      }
      return ctx;
   }


   /**
    *  Description of the Method
    *
    * @param  files  Description of Parameter
    * @param  view   Description of Parameter
    * @since         v 1.0
    */
   private void process(final List files, final View view) {
      new Thread(
               new Runnable() {
                  public void run() {
                     processFiles(files, view);
                  }
               }).start();
   }


   /**
    *  Description of the Method
    *
    * @param  dir      Description of Parameter
    * @param  recurse  Description of Parameter
    * @return          Description of the Returned Value
    * @since           v 1.0
    */
   private List findFiles(String dir, boolean recurse) {
      FileFinder finder = new FileFinder();
      return finder.findFilesFrom(dir, new JavaLanguage.JavaFileOrDirectoryFilter(), recurse);
   }


   /**
    *  Description of the Method
    *
    * @param  files  Description of Parameter
    * @param  view   Description of Parameter
    * @since         v 1.0
    */
   private void processFiles(List files, View view) {
      errorSource.clear();
      JRefactory refactory = (JRefactory)view.getDockableWindowManager().getDockableWindow("javastyle");
      CodingStandardsViewer csViewer = refactory.getCodingStandardsViewer();
      boolean foundProblems = false;
      List contexts = csViewer.checkFiles(files, view, view.getBuffer());
      for (Iterator i = contexts.iterator(); i.hasNext(); ) {
         RuleContext ctx = (RuleContext)i.next();
         for (Iterator j = ctx.getReport().iterator(); j.hasNext(); ) {
            foundProblems = true;
            RuleViolation ruleViolation = (RuleViolation)j.next();
            errorSource.addError(ErrorSource.WARNING, ctx.getSourceCodeFilename(), ruleViolation.getLine() - 1, 0, 0, ruleViolation.getDescription());
         }
      }
      if (!foundProblems) {
         JOptionPane.showMessageDialog(jEdit.getFirstView(), "No problems found", NAME, JOptionPane.INFORMATION_MESSAGE);
         errorSource.clear();
      }
   }


   /**
    *  Description of the Method
    *
    * @param  cpd   Description of Parameter
    * @param  view  Description of Parameter
    * @since        v 1.0
    */
   private void processDuplicates(CPD cpd, View view) {
      CPDDuplicateCodeViewer cpdViewer = getCPDDuplicateCodeViewer(view);
      if (cpdViewer != null) {
         cpdViewer.processDuplicates(cpd, view);
      }
      /*
      cpdViewer.clearDuplicates();
      for (Iterator i = cpd.getMatches(); i.hasNext(); ) {
         Match match = (Match)i.next();
         CPDDuplicateCodeViewer.Duplicates duplicates = cpdViewer.new Duplicates(match.getLineCount() + " duplicate lines", match.getSourceCodeSlice());
         for (Iterator occurrences = match.iterator(); occurrences.hasNext(); ) {
            Mark mark = (Mark)occurrences.next();
            int lastLine = mark.getBeginLine() + match.getLineCount();
            CPDDuplicateCodeViewer.Duplicate duplicate = cpdViewer.new Duplicate(mark.getTokenSrcID(), mark.getBeginLine(), lastLine);
            duplicates.addDuplicate(duplicate);
         }
         cpdViewer.addDuplicates(duplicates);
      }
      cpdViewer.refreshTree();
      cpdViewer.expandAll();
*/
   }


   /**
    *  Sets the Property attribute of the JavaStylePlugin class
    *
    * @param  key    The new Property value
    * @param  value  The new Property value
    * @since         v 1.0
    */
   public static void setProperty(String key, String value) {
      properties.setString(key, value);
   }


   /**
    *  Gets the ErrorSource attribute of the JavaStylePlugin class
    *
    * @return    The ErrorSource value
    * @since     v 1.0
    */
   public static DefaultErrorSource getErrorSource() {
      return errorSource;
   }


   /**
    *  Sets the projectData attribute of the JEditPrettyPrinter object
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The projectName value
    * @since          v 1.0
    */
   public static String getProjectName(View view, Buffer buffer) {
      try {
         String path = buffer.getPath();
         Class clazz = Class.forName("projectviewer.ProjectViewer");
         //projectviewer.ProjectViewer viewer = projectviewer.ProjectViewer.getViewer(view);
         projectviewer.ProjectManager manager = projectviewer.ProjectManager.getInstance();

         for (Iterator i = manager.getProjects(); i.hasNext(); ) {
            projectviewer.vpt.VPTProject proj = (projectviewer.vpt.VPTProject)i.next();
            if (proj.isProjectFile(path)) {
               return proj.getName();
            }
         }
      } catch (ClassNotFoundException e) {
      }
      return "";
   }


   /**
    *  Adds a feature to the navigator attribute of the JavaStylePlugin class
    *
    * @param  nav  The feature to be added to the navigator attribute
    * @since       v 1.0
    */
   public static void addNavigator(Navigator nav) {
      synchronized (navigators) {
         navigators.add(nav);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  nav  Description of Parameter
    * @since       v 1.0
    */
   public static void removeNavigator(Navigator nav) {
      synchronized (navigators) {
         navigators.remove(nav);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  key  Description of Parameter
    * @since       v 1.0
    */
   public static void deleteProperty(String key) {
      properties.deleteKey(key);
   }


   /**
    *  Description of the Method
    *
    * @since    v 1.0
    */
   public static void initJSPlugin() {
      if (jsPlugin == null) {
         jsPlugin = new JavaStylePlugin();
         jsPlugin.start();
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public static void cpdCurrentFile(View view) throws IOException {
      initJSPlugin();
      jsPlugin.errorSource.clear();

      CPD cpd = null;
      String modeName = view.getBuffer().getMode().getName();

      if (modeName.equals("java")) {
         cpd = new CPD(jEdit.getIntegerProperty(DEFAULT_TILE_MINSIZE_PROPERTY, 100), new JavaLanguage());
      } else if (modeName.equals("php")) {
         cpd = new CPD(jEdit.getIntegerProperty(DEFAULT_TILE_MINSIZE_PROPERTY, 100), new PHPLanguage());
      } else if (modeName.equals("c") || modeName.equals("c++")) {
         cpd = new CPD(jEdit.getIntegerProperty(DEFAULT_TILE_MINSIZE_PROPERTY, 100), new CPPLanguage());
      } else {
         JOptionPane.showMessageDialog(view, "Copy/Paste detection can only be performed on Java,C/C++,PHP code.", "Copy/Paste Detector", JOptionPane.INFORMATION_MESSAGE);
         return;
      }

      cpd.add(new File(view.getBuffer().getPath()));
      cpd.go();
      jsPlugin.processDuplicates(cpd, view);
   }


   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  all              Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public static void cpdAllOpenBuffers(View view, boolean all) throws IOException {
      initJSPlugin();
      jsPlugin.errorSource.clear();

      boolean found = false;
      CPD cpd = cpd = new CPD(jEdit.getIntegerProperty(DEFAULT_TILE_MINSIZE_PROPERTY, 100), new JavaLanguage());

      Buffer buffer = jEdit.getFirstBuffer();

      while (buffer != null) {
         String modeName = buffer.getMode().getName();

         if (modeName.equals("java")) {
            cpd.add(new File(buffer.getPath()));
            found = true;
         }

         buffer = buffer.getNext();
      }
      if (found) {
         cpd.go();
         jsPlugin.processDuplicates(cpd, view);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  recursive        Description of Parameter
    * @exception  IOException  Description of Exception
    * @since                   v 1.0
    */
   public static void cpdDir(View view, boolean recursive) throws IOException {
      String file = getBrowserDirectory(view);
      JFileChooser chooser = new JFileChooser((file == null) ? jEdit.getProperty("pmd.cpd.lastDirectory") : file);

      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      JPanel pnlAccessory = new JPanel();

      pnlAccessory.add(new JLabel("Minimum Tile size :"));

      JTextField txttilesize = new JTextField("100");

      pnlAccessory.add(txttilesize);
      chooser.setAccessory(pnlAccessory);

      int returnVal = chooser.showOpenDialog(view);
      File selectedFile = null;

      if (returnVal == JFileChooser.APPROVE_OPTION) {
         selectedFile = chooser.getSelectedFile();
         if (!selectedFile.isDirectory()) {
            JOptionPane.showMessageDialog(view, "Selection not a directory.", NAME, JOptionPane.ERROR_MESSAGE);
            return;
         }
      } else {
         return;
      }
      // In case the user presses cancel or escape.

      jEdit.setProperty("pmd.cpd.lastDirectory", selectedFile.getCanonicalPath());
      initJSPlugin();
      jsPlugin.errorSource.clear();

      int tilesize = 100;

      try {
         tilesize = Integer.parseInt(txttilesize.getText());
      } catch (NumberFormatException e) {
         //use the default.
         tilesize = jEdit.getIntegerProperty(DEFAULT_TILE_MINSIZE_PROPERTY, 100);
      }

      CPD cpd = new CPD(tilesize, new JavaLanguage());

      if (recursive) {
         cpd.addRecursively(selectedFile.getCanonicalPath());
      } else {
         cpd.addAllInDirectory(selectedFile.getCanonicalPath());
      }

      cpd.go();
      jsPlugin.processDuplicates(cpd, view);
   }


   /**
    *  Description of the Method
    *
    * @param  view     Description of Parameter
    * @param  browser  Description of Parameter
    * @since           v 1.0
    */
   public static void checkFile(View view, VFSBrowser browser) {
      initJSPlugin();
      jsPlugin.checkFile(view, browser.getSelectedFiles());
   }


   /**
    *  Description of the Method
    *
    * @param  view       Description of Parameter
    * @param  browser    Description of Parameter
    * @param  recursive  Description of Parameter
    * @since             v 1.0
    */
   public static void checkDirectory(View view, VFSBrowser browser, boolean recursive) {
      VFS.DirectoryEntry entry[] = browser.getSelectedFiles();

      if (entry == null || entry.length == 0 || entry[0].type != VFS.DirectoryEntry.DIRECTORY) {
         JOptionPane.showMessageDialog(view, "Selection must be a directory", NAME, JOptionPane.ERROR_MESSAGE);
         return;
      }
      initJSPlugin();
      jsPlugin.process(jsPlugin.findFiles(entry[0].path, recursive), view);
   }


   /**
    *  Gets the BrowserDirectory attribute of the JavaStylePlugin object
    *
    * @param  view  Description of Parameter
    * @return       The BrowserDirectory value
    * @since        v 1.0
    */
   private static String getBrowserDirectory(View view) {
      VFSBrowser browser = (VFSBrowser)view.getDockableWindowManager().getDockable("vfs.browser");
      if (jEdit.getBooleanProperty(OPTION_UI_DIRECTORY_POPUP) || browser == null) {
         return null;
      }
      return browser.getDirectory();
   }



   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   public final class JavaAST extends EBMessage {
      private Node node;
      private Buffer buffer;


      /**
       *  Constructor for the JavaAST object
       *
       * @param  component  Description of Parameter
       * @param  buffer     Description of Parameter
       * @param  node       Description of Parameter
       * @since             2.9.12
       */
      public JavaAST(EBComponent component, Buffer buffer, Node node) {
         super(component);
         this.buffer = buffer;
         this.node = node;
      }


      /**
       *  Gets the rootNode attribute of the JavaAST object
       *
       * @return    The rootNode value
       * @since     2.9.12
       */
      public Node getRootNode() {
         return node;
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   public final class JavaTree extends EBMessage {
      private TreeNode node;
      private Buffer buffer;


      /**
       *  Constructor for the JavaTree object
       *
       * @param  component  Description of Parameter
       * @param  buffer     Description of Parameter
       * @param  node       Description of Parameter
       * @since             2.9.12
       */
      public JavaTree(EBComponent component, Buffer buffer, TreeNode node) {
         super(component);
         this.buffer = buffer;
         this.node = node;
      }


      /**
       *  Gets the rootNode attribute of the JavaTree object
       *
       * @return    The rootNode value
       * @since     2.9.12
       */
      public TreeNode getRootNode() {
         return node;
      }
   }


   /**
    * Adds an annotation to an ide buffer.
    *
    * @param  view         The frame containing the IDE.
    * @param  buffer       The buffer (containing Java Source) that has been parsed.
    * @param  type         either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
    * @param  lineNo       The line number of the annotation.
    * @param  description  The annotation
    */
   public void addAnnotation(Frame view, Object buffer, int type, int lineNo, String description) {
      //Log.log(Log.NOTICE, this, "NOT IMPLEMENTED: addAnnotation()");
   }

   /**
    * Clears all annotation for an ide buffer.
    *
    * @param  view         The frame containing the IDE.
    * @param  buffer       The buffer (containing Java Source) that has been parsed.
    * @param  type         either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
    */
   public void clearAnnotations(Frame view, Object buffer, int type) {
      //Log.log(Log.NOTICE, this, "NOT IMPLEMENTED: clearAnnotations()");
   }
}

