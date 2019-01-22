/*
 *  Author:  Mike Atkinson
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.ide.standalone;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.tree.TreeNode;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.umd.cs.findbugs.DetectorFactoryCollection;
import org.acm.seguin.ide.command.CommandLineSourceBrowser;
import org.acm.seguin.ide.command.ExitMenuSelection;
import org.acm.seguin.ide.common.ASTViewerPane;
import org.acm.seguin.ide.common.CPDDuplicateCodeViewer;
import org.acm.seguin.ide.common.CodingStandardsViewer;
import org.acm.seguin.ide.common.ExitOnCloseAdapter;
import org.acm.seguin.ide.common.IDEInterface;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.ide.common.PackageSelectorPanel;
import org.acm.seguin.ide.common.SourceBrowser;
import org.acm.seguin.ide.common.PackageSelectorPanel;
import org.acm.seguin.ide.common.Navigator;
import org.acm.seguin.ide.common.options.PropertiesFile;
import org.acm.seguin.io.AllFileFilter;

import org.acm.seguin.pmd.cpd.CPD;
import org.acm.seguin.pmd.cpd.FileFinder;
import org.acm.seguin.pmd.cpd.JavaLanguage;

import org.acm.seguin.JRefactoryVersion;
import org.acm.seguin.summary.*;
import org.acm.seguin.tools.RefactoryInstaller;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;
import org.acm.seguin.util.FileSettings;

import net.sourceforge.jrefactory.ast.Node;
import net.sourceforge.jrefactory.parser.JavaParser;

/**
 *  Draws a UML diagram for all the classes in a package
 *
 * @author    Mike Atkinson
 */
public class JRefactory extends JPanel implements IDEInterface {

   private JTabbedPane mainstage;
   private CPDDuplicateCodeViewer cpdViewer;
   private CodingStandardsViewer csViewer;
   private ASTViewerPane astv;
   private Navigation navigation;
   private Frame aView;
   private static PropertiesFile properties = null;
   private Properties ideProperties = new Properties();
   private JTextPane textPane;
   private JTabbedPane tabbedPane;
   private Map fileNametoTextPaneMap = new HashMap();
   private Map textPhoneToScrollPaneMap = new HashMap();


   /**  Description of the Field */
   public static String JAVASTYLE_DIR = "";
   /**  Description of the Field */
   public static File PRETTY_SETTINGS_FILE = null;
   
   private final static File userDir = new File(System.getProperty("user.dir"));
   private static Map propertiesMap = new HashMap();

   private static JFrame frame = null;

   /**
    *  Create a new <code>JRefactory</code>.
    *
    * @param  view  Description of Parameter
    */
   public JRefactory(Frame view) {
      super(new BorderLayout());
      aView = view;
      System.out.println("new JRefactory()");


      // plug into JRefactory some classes that adapt it to jEdit.
      org.acm.seguin.ide.common.ExitOnCloseAdapter.setExitOnWindowClose(true);

      // check whether JavaStyle is invoked for the first time
      boolean firstTime = !PRETTY_SETTINGS_FILE.exists();


      //  Make sure everything is installed properly
      (new RefactoryInstaller(true)).run();
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


      SourceBrowser.set(new CommandLineSourceBrowser());

      cpdViewer = new CPDDuplicateCodeViewer(aView);

      PackageSelectorPanel panel = PackageSelectorPanel.getMainPanel(null);
      JPanel jRefactoryPanel = (panel == null) ? new ReloadChooserPanel() : panel.getPanel();

      astv = new ASTViewerPane(aView);
      textPane = new JTextPane();
      textPane.setSelectionColor(java.awt.Color.BLUE.brighter().brighter());
      textPane.setSelectedTextColor(java.awt.Color.BLACK);
      textPane.setHighlighter(new javax.swing.text.DefaultHighlighter());

      MyScrollPane scrollPane = new MyScrollPane(textPane);
      textPhoneToScrollPaneMap.put(textPane, scrollPane);

      tabbedPane = new JTabbedPane();
      tabbedPane.add("none", scrollPane);

      navigation = new Navigation(aView, textPane);

      JRootPane findBugs = null;

      try {
         ClassLoader classLoader = this.getClass().getClassLoader();
         java.net.URL url = classLoader.getResource("org/acm/seguin/ide/standalone/JRefactory.class");
         //System.out.println("url="+url);
         java.util.List plugins = new java.util.ArrayList();
         if (url != null) {
            String urlStr = url.toString();
            if (urlStr.indexOf('!')>0) {
               String xStr = urlStr.substring("jar:file:/".length(), urlStr.indexOf('!'));
               //System.out.println("xStr="+xStr);
               xStr = replace(xStr, "%20", " ");
               //System.out.println("xStr="+xStr);
               File file = new File(xStr);
               //System.out.println("file="+file);
               if (file.exists()) {
                  System.out.println(" file exists");
                  plugins.add(file);
               }
            }
         }
         File findBugsDir = new File(JAVASTYLE_DIR,"findbugs");
         //System.out.println("findBugsDir="+findBugsDir);
         if (findBugsDir.exists() && findBugsDir.isDirectory()) {
            File[] files = findBugsDir.listFiles();
            for (int i=0; i<files.length; i++) {
               if (files[i].getName().endsWith(".jar")) {
                  plugins.add(files[i]);
               }
            }
         }
         
         File corePluginFile = new File(userDir, "coreplugin.jar");
         if (corePluginFile.exists()) {
            //System.out.println(" file " + corePluginFile + " exists");
            plugins.add(corePluginFile);
         }

         File[] pluginList = (File[])plugins.toArray(new File[plugins.size()]);
         
         //File[] pluginList = (corePluginFile.exists()) ? new File[]{corePluginFile} : new File[0];
         for (int i=0; i<pluginList.length; i++) {
            System.out.println("pluginList["+i+"]="+pluginList[i]);
         }

         DetectorFactoryCollection.setPluginList(pluginList);
         findBugs = org.acm.seguin.findbugs.FindBugsFrame.createFindBugsPanel(aView);
      } catch (Throwable e) {
         e.printStackTrace();
      }

      csViewer = new CodingStandardsViewer(aView);
      mainstage = new JTabbedPane(JTabbedPane.TOP);
      mainstage.addTab("JRefactory", jRefactoryPanel);
      mainstage.addTab("Cut & paste detector", cpdViewer);
      mainstage.addTab("Coding standards", csViewer);
      if (findBugs != null) {
         mainstage.addTab("Find Bugs", findBugs);
      }
      mainstage.addTab("Abstract Syntax Tree", astv);
      mainstage.addTab("navigator", navigation);
      add(mainstage, BorderLayout.CENTER);
      add(tabbedPane, BorderLayout.EAST);

      // print some version info
      String jversion = new JRefactoryVersion().toString();
      String fversion = properties.getString("version");

      log(1, this, "JRefactory version: " + jversion);
      log(1, this, "pretty settings file version: " + fversion);
      navigation.addBuffer(textPane);
   }

   
   private static String replace(String from, String oldStr, String newStr) {
      StringBuffer sb = new StringBuffer();
      int y = 0;
      int x = from.indexOf(oldStr);
      while (x>=0) {
         System.out.println("part="+from.substring(y,x));
         sb.append(from.substring(y,x)).append(newStr);
         y = x+oldStr.length();
         x = from.indexOf(oldStr,y);
      }
      System.out.println("lastpart="+from.substring(y));
      sb.append(from.substring(y));
      return sb.toString();
   }
   
   /**  Sets the DefaultValues attribute of the JavaStylePlugin object */
   private void setDefaultValues() {
      // these default settings need to be corrected:
      setProperty("end.line", "NL"); // jEdit requires this
      setProperty("space.before.javadoc", "true"); // default (false) looks odd
   }

   /**
    *  Sets the Property attribute of the JavaStylePlugin class
    *
    * @param  key    The new Property value
    * @param  value  The new Property value
    */
   public static void setProperty(String key, String value) {
      properties.setString(key, value);
   }


   /**
    *  Description of the Method
    *
    * @param  view      Description of Parameter
    * @param  fileName  The new Buffer value
    */
   public void setBuffer(Frame view, Object fileName) {
      System.out.println("setBuffer("+fileName+")");
      textPane = (JTextPane)fileNametoTextPaneMap.get(fileName);
      if (textPane!=null) {
         tabbedPane.setSelectedComponent( (JScrollPane)textPhoneToScrollPaneMap.get(textPane) );
      } else {
         // FIXME
      }
   }

   /**
    *  Description of the Method
    *
    * @param  view   Description of Parameter
    * @param  start  The new Selection value
    * @param  end    The new Selection value
    */
   public void setSelection(Frame view, Object buffer, int start, int end) {
      System.out.println("setSelection(view, " + start + "," + end + ")");

      textPane = (JTextPane)buffer;
      tabbedPane.setSelectedComponent( (JScrollPane)textPhoneToScrollPaneMap.get(textPane));
      javax.swing.text.Caret caret = textPane.getCaret();

      caret.setDot(start);
      caret.moveDot(end);
      caret.setVisible(true);
      caret.setSelectionVisible(true);
   }


   /**  Gets the userSelection attribute of the JRefactory object */
   public void getUserSelection() {
      JFileChooser chooser = new JFileChooser();

      //  Add other file filters - All
      AllFileFilter allFilter = new AllFileFilter();

      chooser.addChoosableFileFilter(allFilter);

      //  Set it so that files and directories can be selected
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      //  Set the directory to the current directory
      chooser.setCurrentDirectory(userDir);

      int returnVal = chooser.showOpenDialog(null);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
         PackageSelectorPanel panel = PackageSelectorPanel.getMainPanel(chooser.getSelectedFile().getAbsolutePath());

         ReloaderSingleton.register(panel);
         mainstage.setComponentAt(0, panel.getPanel());
      } else {
         mainstage.setComponentAt(0, new ReloadChooserPanel());
      }
   }


   /**
    *  Gets the IDEProperty attribute of the IDEInterface object
    *
    * @param  prop  Description of Parameter
    * @return       The IDEProperty value
    */
   public String getIDEProperty(String prop) {
      System.out.println("getIDEProperty(" + prop + ")");
      return ideProperties.getProperty(prop);
   }


   /**
    *  Gets the IDEProperty attribute of the IDEInterface object
    *
    * @param  prop   Description of Parameter
    * @param  deflt  Description of Parameter
    * @return        The IDEProperty value
    */
   public String getIDEProperty(String prop, String deflt) {
      System.out.println("getIDEProperty(" + prop + "," + deflt + ")");
      return ideProperties.getProperty(prop, deflt);
   }


   /**
    *  Load an icon from the IDE
    *
    * @param  name  The name of the icon.
    * @return       An icon (or null if the icon cannot be found).
    */
   public Icon loadIcon(String name) {
      ClassLoader classLoader = this.getClass().getClassLoader();
      Icon icon = null;
      try {
         icon = new ImageIcon(classLoader.getResource(name));
      } catch (Exception e) {
      }
	   if (icon==null) {
         try {
            icon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/"+name));
         } catch (Exception e) {
         }
	   }
	   return icon;
   }


   /**
    * Get the line number of the cursor within the current buffer.
    *
    * @return       The ine number of the cursor in the current buffer, or -1 if no current buffer.
    */
   public int getLineNumber() {
      String text = textPane.getText();
      javax.swing.text.Caret caret = textPane.getCaret();
      int pos = caret.getDot();
      int i = 0;

      while (pos > 0 && i < text.length()) {
         if (text.charAt(i++) == '\n') {
            pos--;
         }
      }
      return i;
   }


   /**
    *  Gets the IDEProjects attribute of the IDEInterface object
    *
    * @param  parent  Description of Parameter
    * @return         The IDEProjects value
    */
   public String[] getIDEProjects(Frame parent) {
      System.out.println("getIDEProjects(" + parent + ")");
      return new String[]{"default"};
      //return new String[0];
   }

   /**
    *  Gets the Properties attribute of the IDEInterface object
    *
    * @param  type     Description of Parameter
    * @param  project  Description of Parameter
    * @return          The Properties value
    */
   public PropertiesFile getProperties(String type, String project) {
      System.out.println("getProperties(" + type + "," + project + ")");

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
    */
   public int getLineStartOffset(Object buffer, int begin) {
      System.out.println("getLineStartOffset(" + buffer + "," + begin + ")");

      textPane = (JTextPane)buffer;
      String text = textPane.getText();
      int i = 0;
      int pos = begin;

      while (pos > 0 && i < text.length()) {
         if (text.charAt(i++) == '\n') {
            pos--;
         }
      }
      //return i - begin;
      return i;
   }

   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @param  end     Description of Parameter
    * @return         The LineEndOffset value
    */
   public int getLineEndOffset(Object buffer, int end) {
      System.out.println("getLineEndOffset(" + buffer + "," + end + ")");

      textPane = (JTextPane)buffer;
      String text = textPane.getText();
      int i = 0;
      int pos = end;

      while (pos > 0 && i < text.length()) {
         if (text.charAt(i++) == '\n') {
            pos--;
         }
      }
      //return i - 1 - end;
      return i - 1;
   }

   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The Text value
    */
   public String getText(Frame view, Object buffer) {
      System.out.println("getText(view, " + buffer + ")");
      if (buffer!=null) {
         textPane = (JTextPane)buffer;
      }
      return textPane.getText();
   }

   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @return         The LineEndOffset value
    */
   public int getLineCount(Object buffer) {
      System.out.println("getLineCount(" + buffer + ")");

      textPane = (JTextPane)buffer;
      String text = textPane.getText();
      int lc = 1;
      int i = 0;

      while (i < text.length()) {
         if (text.charAt(i++) == '\n') {
            lc++;
         }
      }
      return lc;
   }


   /**
    *  Returns the frame that contains the editor. If this is not available or
    *  you want dialog boxes to be centered on the screen return null from this
    *  operation.
    *
    *@return    the frame
    */
   public Frame getEditorFrame() {
	  return null;
   }


   /**
    * Get the current (atcive) buffer.
    *
    * @param  view  The frame containing the IDE.
    * @return       The active buffer or null if no active buffer.
    */
   public Object getCurrentBuffer(Frame view) {
      return textPane;
   }



   /**
    * Get the line number of the cursor within the current buffer.
    *
    * @return       The ine number of the cursor in the current buffer, or -1 if no current buffer.
    */
   public int getLineNumber(Frame view, Object buffer) {
      System.out.println("getLineNumber(view," + buffer + ")");
      textPane = (JTextPane)buffer;
      JTextPane pane = (JTextPane)buffer;
      String text = pane.getText();
      javax.swing.text.Caret caret = pane.getCaret();
      int pos = caret.getDot();
      int lines = 0;

      for (int i=0; i<pos; i++) {
         if (text.charAt(i) == '\n') {
            lines++;
         }
      }
      return lines;
   }


   /**
     * Sets the line number
     *
     * @param  value  New 1-based line number
     */
   public void setLineNumber(Frame view, Object buffer, int lineNumber) {
      System.out.println("setLineNumber(view," + buffer + "," + lineNumber + ")");
      textPane = (JTextPane)buffer;
      JTextPane pane = (JTextPane)buffer;
      String text = pane.getText();
      javax.swing.text.Caret caret = pane.getCaret();
      int lines = 0;

      for (int i=0; i<text.length(); i++) {
         if (text.charAt(i) == '\n') {
            if (--lineNumber == 0) {
               caret.setDot(i);
               break;
            }
         }
      }
   }


   /**
    *  Does the buffer contain Java source code.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    *@return <code>true</code> if the buffer contains Java source code, <code>false</code> otherwise.
    */
   public boolean bufferContainsJavaSource(Frame view, Object buffer) {
      System.out.println("bufferContainsJavaSource(view," + buffer + ")");
      if (buffer==null) {
         return false;
      }
      for (Iterator i = fileNametoTextPaneMap.keySet().iterator(); i.hasNext(); ) {
         String key = (String)i.next();
         if (buffer == fileNametoTextPaneMap.get(key)) {
            return key.endsWith(".java");
         }
      }
      return false;
   }


   /**
    *  Gets the file that is being edited
    *
    *@return    The File value
    */
   public File getFile(Frame view, Object buffer) {
      System.out.println("getFile(view," + buffer + ")");
      if (buffer==null) {
         return null;
      }
      for (Iterator i = fileNametoTextPaneMap.keySet().iterator(); i.hasNext(); ) {
         String key = (String)i.next();
         if (buffer == fileNametoTextPaneMap.get(key)) {
            return new File(key);
         }
      }
	   return null;
   }


   /**
    * Indicates that a buffer has been parsed and that an Abstract Syntax Tree is available.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  The buffer (containing Java Source) that has been parsed.
    * @param  compilationUnit  The root node of the AST.
    */
	public void bufferParsed(Frame view, Object buffer, Node compilationUnit) {
      System.out.println("NOT IMPLEMENTED: bufferParsed()");
	}


   /**
    * Indicates that a buffer has been parsed and that a navigator tree of the source is available.
    *
    * @param  view    The frame containing the IDE.
    * @param  buffer  The buffer (containing Java Source) that has been parsed.
    * @param  node    The root node of the tree.
    */
	public void bufferNavigatorTree(Frame view, Object buffer, TreeNode node) {
      System.out.println("NOT IMPLEMENTED: bufferNavigatorTree()");
	}


   /**
    *  Sets the string in the IDE
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    *@param  value  The new file contained in a string
    */
   public void setText(Frame view, Object buffer, String value) {
      System.out.println("getFile(view," + buffer + ","+value+")");
      if (buffer!=null) {
         textPane = (JTextPane)buffer;
      }
	   textPane.setText(value);
      tabbedPane.setSelectedComponent( (JScrollPane)textPhoneToScrollPaneMap.get(textPane));
   }

   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @return         The ProjectName value
    */
   public String getProjectName(Frame view, Object buffer) {
      System.out.println("getProjectName(" + buffer + ")");
      return getProjectName(view);
   }

   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @return         The File path for this buffer
    */
   public String getFilePathForBuffer(Object buffer) {
      System.out.println("getFilePathForBuffer(" + buffer + ")");
      for (Iterator i = fileNametoTextPaneMap.keySet().iterator(); i.hasNext(); ) {
         String key = (String)i.next();
         if (buffer == fileNametoTextPaneMap.get(key)) {
            return key;
         }
      }
      return "";
   }

   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    */
   public void goToBuffer(Frame view, Object buffer) {
      System.out.println("goToBuffer(view" + ", " + buffer + ")");
      textPane = (JTextPane)buffer;
      tabbedPane.setSelectedComponent( (JScrollPane)textPhoneToScrollPaneMap.get(textPane));
   }

   /**
    *  Description of the Method
    *
    * @param  parent  Description of Parameter
    */
   public void showWaitCursor(Frame parent) {
      System.out.println("NOT IMPLEMENTED: showWaitCursor(" + parent + ")");
   }

   /**
    *  Description of the Method
    *
    * @param  parent  Description of Parameter
    */
   public void hideWaitCursor(Frame parent) {
      System.out.println("NOT IMPLEMENTED: hideWaitCursor(" + parent + ")");
   }

   /**
    *  Description of the Method
    *
    * @param  urgency  Description of Parameter
    * @param  source   Description of Parameter
    * @param  message  Description of Parameter
    */
   public void log(int urgency, Object source, Object message) {
      System.out.println("log(" + urgency + "," + source.getClass() + "," + message + ")");
   }

   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @exception  IOException  Description of Exception
    */
   public void cpdBuffer(Frame view, Object buffer) {
      System.out.println("NOT IMPLEMENTED: cpdBuffer(view,buffer)");
   }

   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @exception  IOException  Description of Exception
    */
   public void cpdAllOpenBuffers(Frame view) {
      System.out.println("NOT IMPLEMENTED: cpdAllOpenBuffers(view)");
   }

   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  recursive        Description of Parameter
    * @exception  IOException  Description of Exception
    */
   public void cpdDir(Frame view, boolean recursive) throws IOException {
      System.out.println("cpdDir(view)");

      JFileChooser chooser = new JFileChooser(getIDEProperty("pmd.cpd.lastDirectory"));

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
            JOptionPane.showMessageDialog(view, "Selection not a directory.", "JRefactory", JOptionPane.ERROR_MESSAGE);
            return;
         }
      } else {
         return;
      }
      // In case the user presses cancel or escape.

      getIDEProperty("pmd.cpd.lastDirectory", selectedFile.getCanonicalPath());

      int tilesize = 100;

      try {
         tilesize = Integer.parseInt(txttilesize.getText());
      } catch (NumberFormatException e) {
         //use the default.
         tilesize = 100;
      }

      CPD cpd = new CPD(tilesize, new JavaLanguage());

      if (recursive) {
         cpd.addRecursively(selectedFile.getCanonicalPath());
      } else {
         cpd.addAllInDirectory(selectedFile.getCanonicalPath());
      }

      cpd.go();
      if (cpdViewer != null) {
         cpdViewer.processDuplicates(cpd, view);
      }
   }

   /**
    *  Description of the Method
    *
    * @param  view             Description of Parameter
    * @param  fileName         Description of Parameter
    * @return                  Description of the Returned Value
    * @exception  IOException  Description of Exception
    */
   public Object openFile(Frame view, String fileName) throws IOException {
      System.out.println("openFile(view, " + fileName + ")");

      File file = new File(fileName);

      if (file.exists()) {
         try {
            //navigation.removeBuffer(textPane.getDocument());
            for (Iterator i = fileNametoTextPaneMap.keySet().iterator(); i.hasNext(); ) {
               String key = (String)i.next();
               if (key.equals(fileName)) {
                  textPane = (JTextPane)fileNametoTextPaneMap.get(key);
                  MyScrollPane scrollPane = (MyScrollPane)textPhoneToScrollPaneMap.get(textPane);
                  if (scrollPane==null) {
                     // ERROR
                     break;
                  }
                  tabbedPane.setSelectedComponent(scrollPane);
                  return textPane;
               }
            }
            if (fileNametoTextPaneMap.size()==0) {
               tabbedPane.removeTabAt(0);
            }
            textPane = new JTextPane();
            textPane.setSelectionColor(java.awt.Color.BLUE.brighter().brighter());
            textPane.setSelectedTextColor(java.awt.Color.BLACK);
            textPane.setHighlighter(new javax.swing.text.DefaultHighlighter());

            MyScrollPane scrollPane = new MyScrollPane(textPane);
            textPhoneToScrollPaneMap.put(textPane, scrollPane);
            fileNametoTextPaneMap.put(fileName, textPane);

            tabbedPane.add(getClassName(fileName), scrollPane);
            textPane.read(new java.io.FileReader(file), file);
            navigation.addBuffer(textPane);
         } catch (IOException e) {
            JOptionPane.showMessageDialog(textPane, e.getMessage(), "JRefactory", JOptionPane.INFORMATION_MESSAGE);
         }
      }
      return textPane;
   }


   private String getClassName(String fileName) {
      int x = fileName.lastIndexOf("\\");
      int y = fileName.lastIndexOf("/");
      if (y<x) {
         y = x;
      }
      if (y>=0) {
         return fileName.substring(y+1);
      }
      return fileName;
   }


   /**
    *  Description of the Method
    *
    * @param  view   Description of Parameter
    * @param  start  Description of Parameter
    */
   public void moveCaretPosition(Frame view, Object buffer, int start) {
      System.out.println("moveCaretPosition(view, " + start + ")");

      textPane = (JTextPane)buffer;
      tabbedPane.setSelectedComponent( (JScrollPane)textPhoneToScrollPaneMap.get(textPane));
      javax.swing.text.Caret caret = textPane.getCaret();

      caret.moveDot(start);
      caret.setVisible(true);
   }

   /**
    *  Description of the Method
    *
    * @param  runnable  Description of Parameter
    */
   public void runInAWTThread(Runnable runnable) {
      System.out.println("runInAWTThread(" + runnable + ")");
      SwingUtilities.invokeLater(runnable);
   }

   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    */
   public void checkBuffer(Frame view, Object buffer) {
      System.out.println("check(view, " + buffer + ")");
      if (buffer==null) {
         buffer = textPane;
      }
      csViewer.check(view, buffer, false);
   }

   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    */
   public void checkAllOpenBuffers(Frame view) {
      System.out.println("checkAllOpenBuffers(view)");
      for (Iterator i = fileNametoTextPaneMap.keySet().iterator(); i.hasNext(); ) {
         String key = (String)i.next();
         checkBuffer(view, fileNametoTextPaneMap.get(key));
      }
   }

   /**
    *  Description of the Method
    *
    * @param  view       Description of Parameter
    * @param  recursive  Description of Parameter
    */
   public void checkDirectory(Frame view, boolean recursive) {
      System.out.println("checkDirectory(view" + recursive + ")");

      JFileChooser chooser = new JFileChooser();

      //  Add other file filters - All
      AllFileFilter allFilter = new AllFileFilter();

      chooser.addChoosableFileFilter(allFilter);

      //  Set it so that files and directories can be selected
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      //  Set the directory to the current directory
      chooser.setCurrentDirectory(userDir);

      //  Get the user's selection
      int returnVal = chooser.showOpenDialog(null);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
         //selectionPanel(chooser.getSelectedFile().getAbsolutePath());
         process(findFiles(chooser.getSelectedFile().getAbsolutePath(), recursive), view);
      }
   }

   /**  Description of the Method */
   public void saveProperties() {
      for (java.util.Iterator i = propertiesMap.keySet().iterator(); i.hasNext(); ) {
         PropertiesFile projectProperties = (PropertiesFile)propertiesMap.get(i.next());

         projectProperties.save();
      }
   }

   /**
    *  Description of the Method
    *
    * @param  dir      Description of Parameter
    * @param  recurse  Description of Parameter
    * @return          Description of the Returned Value
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
    */
   private void process(final List files, final Frame view) {
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
    * @param  files  Description of Parameter
    * @param  view   Description of Parameter
    */
   private void processFiles(List files, Frame view) {
      List contexts = csViewer.checkFiles(files, view, null);
   }

   /**
    *  Sets the projectData attribute of the JEditPrettyPrinter object
    *
    * @param  view  Description of Parameter
    * @return       The projectName value
    */
   public static String getProjectName(Frame view) {
      return "";
   }

   /**
    *  The main program
    *
    * @param  args  the command line arguments
    */
   public static void main(String[] args) {
      try {
         System.setOut(new java.io.PrintStream(new java.io.FileOutputStream("out.txt")));
         System.setErr(new java.io.PrintStream(new java.io.FileOutputStream("err.txt")));
      } catch (java.io.FileNotFoundException e) {
         e.printStackTrace();
         System.exit(1);
      }

      Properties props = System.getProperties();

      props.list(System.out);

      JAVASTYLE_DIR = new File(props.getProperty("user.home") + File.separator + ".JRefactory" + File.separator + "javastyle").getAbsolutePath();
      PRETTY_SETTINGS_FILE = new File(JAVASTYLE_DIR + File.separator + ".Refactory", "pretty.settings");
      FileSettings.setSettingsRoot(JAVASTYLE_DIR);

      JavaParser.setTargetJDK("1.4.2");
      for (int ndx = 0; ndx < args.length; ndx++) {
         if (args[ndx].equals("-config")) {
            String dir = args[ndx + 1];

            ndx++;
            FileSettings.setSettingsRoot(dir);
         }
      }

      //if (args.length == 0) {
      //   elixir();
      //} else {
      //   selectionPanel(args[0]);
      //}
      frame = new JFrame();
      frame.setTitle("JRefactory");

      JRefactory refactory = new JRefactory(frame);

      IDEPlugin.setPlugin(refactory);
      frame.getContentPane().add(refactory);

      JMenuBar menuBar = new JMenuBar();

      frame.setJMenuBar(menuBar);

      JMenuItem options = new JMenuItem("Options");

      options.addActionListener(
               new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                     new org.acm.seguin.ide.common.options.JSOptionDialog(frame);
                  }
               });

      menuBar.add(options);

      frame.addWindowListener(new ExitMenuSelection());
      frame.pack();
      refactory.astv.initDividers();
      frame.show();

   }

   /**
    *  Creates the selection panel
    *
    * @param  directory  Description of Parameter
    */
   public static void selectionPanel(String directory) {
      PackageSelectorPanel panel = PackageSelectorPanel.getMainPanel(directory);

      ReloaderSingleton.register(panel);
   }

   /**  Insertion point for elixir */
   public static void elixir() {
      if (PackageSelectorPanel.getMainPanel(null) != null) {
         return;
      }

      JFileChooser chooser = new JFileChooser();

      //  Add other file filters - All
      AllFileFilter allFilter = new AllFileFilter();

      chooser.addChoosableFileFilter(allFilter);

      //  Set it so that files and directories can be selected
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      //  Set the directory to the current directory
      chooser.setCurrentDirectory(userDir);

      //  Get the user's selection
      int returnVal = chooser.showOpenDialog(null);

      if (returnVal == JFileChooser.APPROVE_OPTION) {
         selectionPanel(chooser.getSelectedFile().getAbsolutePath());
      }
   }

   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    */
   private class MyScrollPane extends JScrollPane {
      private Dimension size = new Dimension(600, 300);

      /**
       *  Constructor for the MyScrollPane object
       *
       * @param  component  Description of Parameter
       */
      public MyScrollPane(java.awt.Component component) {
         super(component);
      }

      /**
       *  Gets the MinimumSize attribute of the MyTextPane object
       *
       * @return    The MinimumSize value
       */
      public Dimension getMinimumSize() {
         return size;
      }

      /**
       *  Gets the PreferredSize attribute of the MyTextPane object
       *
       * @return    The PreferredSize value
       */
      public Dimension getPreferredSize() {
         return size;
      }
   }

   /**
    *  Description of the Class
    *
    * @author     <a href="mailto:JRefactoryPlugin@ladyshot.demon.co.uk">Mike Atkinson</a>
    * @created    23 July 2003
    * @version    $Id: JRefactory.java,v 1.17 2004/05/08 12:48:57 mikeatkinson Exp $
    * @since      0.1.0
    */
   private final class ReloadChooserPanel extends JPanel {
      /**  Constructor for the ReloadChooserPanel object */
      public ReloadChooserPanel() {
         //System.out.println("new ReloadChooserPanel()");
         JButton load = new JButton("load JRefactory UML viewer");

         load.addActionListener(
                  new ActionListener() {
                     public void actionPerformed(ActionEvent event) {
                        JRefactory.this.getUserSelection();
                     }
                  });
         add(load);
      }
   }

   
   private final class Navigation extends JScrollPane {
      private Frame view;
      private JTextPane textPane;
      private Document doc;
      private final Navigator navigator;
      public Navigation(Frame view, JTextPane textPane) {
        this.view = view;
        this.textPane = textPane;
        navigator = new Navigator(view);
        doc = textPane.getDocument();
        navigator.viewCreated(view);
        navigator.addBuffer(textPane);
        setViewportView(navigator);
        System.out.println("Navigation created");
      }
      
      public void addBuffer(final JTextPane textPane) {
         System.out.println("Navigation.addBuffer(doc)");
         navigator.addBuffer(textPane);
         textPane.getDocument().addDocumentListener(new MyDocumentListener(navigator));
         System.out.println("added listener");
         navigator.contentInserted(view, doc, 0, 16);
      }

      public void removeBuffer(final JTextPane textPane) {
         textPane.getDocument().removeDocumentListener(new MyDocumentListener(navigator));
         navigator.removeBuffer(textPane);
      }

      private class MyDocumentListener implements DocumentListener {
         private Navigator navigator;
         public MyDocumentListener(Navigator navigator) {
            this.navigator = navigator;
         }
         public void changedUpdate(DocumentEvent e) {
            System.out.println("changeUpdate("+e+")");
            navigator.contentInserted(view, e.getDocument(), e.getOffset(), e.getLength());
         }
         public void insertUpdate(DocumentEvent e) {
            System.out.println("insertUpdate("+e+")");
            navigator.contentInserted(view, e.getDocument(), e.getOffset(), e.getLength());
         }
         public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdate("+e+")");
            navigator.contentRemoved(view, e.getDocument(), e.getOffset(), e.getLength());
         }
        };
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
      System.out.println("NOT IMPLEMENTED: clearAnnotations()");
   }

   /**
    * Clears all annotation for an ide buffer.
    *
    * @param  view         The frame containing the IDE.
    * @param  buffer       The buffer (containing Java Source) that has been parsed.
    */
   public void clearAnnotations(Frame view, Object buffer, int type) {
      System.out.println("NOT IMPLEMENTED: clearAnnotations()");
   }
}

