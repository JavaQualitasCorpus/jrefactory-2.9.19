/*
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
package org.acm.seguin.ide.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Segment;
import javax.swing.tree.*;
import net.sourceforge.jrefactory.ast.*;
import net.sourceforge.jrefactory.parser.JavaParser;
import net.sourceforge.jrefactory.parser.JavaParserVisitorAdapter;
import net.sourceforge.jrefactory.parser.ParseException;
import net.sourceforge.jrefactory.parser.TokenMgrError;

import org.acm.seguin.util.FileSettings;



/**
 *  Main GUI for JRefactory
 *
 * @author     <a href="mailto:JRefactoryPlugin@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @since      0.0.1
 * @created    23 July 2003
 * @version    $Id: Navigator.java,v 1.5 2003/12/04 13:51:32 mikeatkinson Exp $
 */
public final class Navigator extends JPanel {
   private Frame view;
   private JTree tree;
   private DefaultTreeModel model;
   private final DefaultMutableTreeNode DEFAULT_NODE;
   private final static String NAME = "Navigator";
   private static Map viewToNavigation = new HashMap();
   private final static int DEFAULT_PARSER_PRIORITY = Thread.MIN_PRIORITY;

   private static AlphabeticalOrderComparator alphabeticalOrderComparator = new AlphabeticalOrderComparator();

   private static StandardOrderComparator standardOrderComparator = new StandardOrderComparator();

   /**
    *  The instance of BugCellRenderer.
    *
    * @since    2.9.12
    */
   private final static Navigator.NavigatorRenderer navigatorRenderer = new Navigator.NavigatorRenderer();


   /**
    *  Constructor for the Navigator object
    *
    * @param  aView  Description of Parameter
    * @since         2.9.12
    */
   public Navigator(Frame aView) {
      super(new BorderLayout());
      //System.out.println("new Navigator(" + view + ")");
      view = aView;
      viewCreated(view);
      //JavaStylePlugin.addNavigator(this);
      tree = new JTree();
      DEFAULT_NODE = new DefaultMutableTreeNode("classes");
      model = new DefaultTreeModel(DEFAULT_NODE);
      tree.setModel(model);
      tree.setCellRenderer(navigatorRenderer);
      tree.addTreeSelectionListener(
               new TreeSelectionListener() {
                  public void valueChanged(TreeSelectionEvent evt) {
                     DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

                     if (node != null) {
                        if (node.isLeaf() && node instanceof ANode) {
                           ANode aNode = (ANode)node;
                           //gotoViolation(violation);
                           (new RunGotoNode(aNode, view)).run();
                        } else if (node instanceof AConstructor || node instanceof AMethod || node instanceof AClass || node instanceof AnInterface) {
                           ANode aNode = (ANode)node;
                           //gotoViolation(violation);
                           (new RunGotoNode(aNode, view)).run();
                        }
                     }
                  }
               });
      add(tree);
   }


   /**
    *  Description of the Method
    *
    * @since    2.9.12
    */
   public void reconfigure() {
      // reset the properties like popup display, etc.
      //FIXME: for (Iterator it = viewToNavigation.keySet().iterator(); it.hasNext();){
      //FIXME:     Navigation nav = (Navigation) viewToNavigation.get(it.next());
      //FIXME:     nav.reconfigure();
      //FIXME: }
   }


   /**
    *  Description of the Method
    *
    * @param  aFile  Description of Parameter
    * @since         2.9.12
    */
   public void updateTree(DefaultTreeModel aFile) {
      ASourceFile root = (ASourceFile)aFile.getRoot();
      root.sort(standardOrderComparator);
      String filename = root.getName();
      Enumeration children = DEFAULT_NODE.children();
      //System.out.println("  filename="+filename);
      while (children.hasMoreElements()) {
         ASourceFile sourceFile = (ASourceFile)children.nextElement();
         //System.out.println("    child="+sourceFile.getName());
         if (filename.equals(sourceFile.getName())) {
            //TreePath path = new TreePath(sourceFile.getPath());
            List paths = new ArrayList();
            for (Enumeration x = sourceFile.depthFirstEnumeration(); x.hasMoreElements(); ) {
               TreeNode[] nds = ((ANode)x.nextElement()).getPath();
               paths.add(new PathData(nds, tree.isExpanded(new TreePath(nds))));
            }
            sourceFile.removeAllChildren();
            for (Enumeration newChildren = root.children(); newChildren.hasMoreElements(); ) {
               sourceFile.add((DefaultMutableTreeNode)newChildren.nextElement());
            }
            //System.out.println("updateTree(): structureChanged");
            int oldCount = paths.size();
            int newCount = 0;
            for (Enumeration np = sourceFile.depthFirstEnumeration(); np.hasMoreElements(); ) {
               np.nextElement();
               newCount++;
            }
            Enumeration np = sourceFile.depthFirstEnumeration();
            for (Iterator p = paths.iterator(); p.hasNext(); ) {
               PathData pathdata = (PathData)p.next();
               if (!np.hasMoreElements()) {
                  break;
               }
               TreeNode[] newPath = ((ANode)np.nextElement()).getPath();
               if (!comparePaths(pathdata.path, newPath)) {
                  //System.out.println("paths different");
                  //System.out.println("  newPath=");
                  //for (int i=0; i<newPath.length; i++) {
                  //   System.out.println("    "+newPath[i]);
                  //}
                  //System.out.println("  pd.path=");
                  //for (int i=0; i<pd.path.length; i++) {
                  //   System.out.println("    "+pd.path[i]);
                  //}
                  model.nodeStructureChanged(newPath[newPath.length - 1]);
                  model.nodeStructureChanged(newPath[newPath.length - 2]);
                  if (newPath.length >= 3) {
                     model.nodeStructureChanged(newPath[newPath.length - 3]);
                  }
                  if (oldCount > newCount) {
                     if (!p.hasNext()) {
                        break;
                     }
                     pathdata = (PathData)p.next();
                     //System.out.println("  new pd.path=");
                     //for (int i=0; i<pd.path.length; i++) {
                     //   System.out.println("    "+pd.path[i]);
                     //}
                  } else if (newCount > oldCount) {
                     if (!np.hasMoreElements()) {
                        break;
                     }
                     newPath = ((ANode)np.nextElement()).getPath();
                     //System.out.println("  new newPath=");
                     //for (int i=0; i<newPath.length; i++) {
                     //   System.out.println("    "+newPath[i]);
                     //}
                  }
               }
               if (pathdata.expanded) {
                  //System.out.println("expanded");
                  //for (int i=0; i<newPath.length; i++) {
                  //   System.out.println("  "+newPath[i]);
                  //}
                  tree.expandPath(new TreePath(newPath));
               }
            }

            return;
         }
      }
      DEFAULT_NODE.add(root);
      //TreeNode c = root.getChildAt(0);
      //System.out.println("updateTree(): node added");
      //System.out.println("  name="+((ANode)c).getName());
      model.nodesWereInserted(DEFAULT_NODE, new int[]{model.getIndexOfChild(DEFAULT_NODE, root)});
   }


   /**
    *  Description of the Method
    *
    * @param  aFile  Description of Parameter
    * @since         2.9.12
    */
   public void removeNodeFromTree(DefaultTreeModel aFile) {
      ASourceFile root = (ASourceFile)aFile.getRoot();
      String filename = root.getName();
      Enumeration children = DEFAULT_NODE.children();
      while (children.hasMoreElements()) {
         ASourceFile sourcefile = (ASourceFile)children.nextElement();
         if (filename.equals(sourcefile.getName())) {
            model.removeNodeFromParent(sourcefile);
            return;
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    * @since        2.9.12
    */
   public void viewCreated(Frame view) {
      viewToNavigation.put(view, new Navigation(view, this));
   }


   /**
    *  Adds a feature to the buffer attribute of the Navigator object
    *
    * @param  buffer  The feature to be added to the buffer attribute
    * @since          2.9.12
    */
   public void addBuffer(Object buffer) {
      for (Iterator i = viewToNavigation.keySet().iterator(); i.hasNext(); ) {
         Navigation nav = (Navigation)viewToNavigation.get(i.next());
         nav.addBuffer(buffer);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @since          2.9.12
    */
   public void removeBuffer(Object buffer) {
      for (Iterator i = viewToNavigation.keySet().iterator(); i.hasNext(); ) {
         Navigation nav = (Navigation)viewToNavigation.get(i.next());
         nav.removeBuffer(buffer);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @param  offset  Description of Parameter
    * @param  length  Description of Parameter
    * @since          2.9.12
    */
   public void contentInserted(Frame view, Object buffer, int offset, int length) {
      for (Iterator i = viewToNavigation.keySet().iterator(); i.hasNext(); ) {
         Navigation nav = (Navigation)(viewToNavigation.get(i.next()));
         JavaParserThread jpt = (JavaParserThread)nav.mapBufferToParser.get(buffer);
         if (jpt != null) {
            jpt.contentInserted(offset, length);
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @param  offset  Description of Parameter
    * @param  length  Description of Parameter
    * @since          2.9.12
    */
   public void contentRemoved(Frame view, Object buffer, int offset, int length) {
      for (Iterator i = viewToNavigation.keySet().iterator(); i.hasNext(); ) {
         Navigation nav = (Navigation)(viewToNavigation.get(i.next()));
         JavaParserThread jpt = (JavaParserThread)nav.mapBufferToParser.get(buffer);
         if (jpt != null) {
            jpt.contentRemoved(offset, length);
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  view    Description of Parameter
    * @param  buffer  Description of Parameter
    * @since          2.9.12
    */
   public void transactionComplete(Frame view, Object buffer) {
      for (Iterator i = viewToNavigation.keySet().iterator(); i.hasNext(); ) {
         Navigation nav = (Navigation)(viewToNavigation.get(i.next()));
         JavaParserThread jpt = (JavaParserThread)nav.mapBufferToParser.get(buffer);
         if (jpt != null) {
            jpt.transactionComplete();
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  path1  Description of Parameter
    * @param  path2  Description of Parameter
    * @return        Description of the Returned Value
    * @since         2.9.12
    */
   private boolean comparePaths(TreeNode[] path1, TreeNode[] path2) {
      if (path1.length != path2.length) {
         return false;
      }
      for (int i = 0; i < path1.length; i++) {
         if (!path1[i].toString().equals(path2[i].toString())) {
            return false;
         }
      }
      return true;
   }


   /**
    *  Description of the Method
    *
    * @param  view  Description of Parameter
    * @since        2.9.12
    */
   public static void viewClosed(Frame view) {
      Navigation nav = (Navigation)(viewToNavigation.get(view));
      nav.stop();
      viewToNavigation.remove(view);
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private class PathData {
      TreeNode[] path;
      boolean expanded;


      /**
       *  Constructor for the PathData object
       *
       * @param  path      Description of Parameter
       * @param  expanded  Description of Parameter
       * @since            2.9.12
       */
      PathData(TreeNode[] path, boolean expanded) {
         this.path = path;
         this.expanded = expanded;
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class AlphabeticalOrderComparator implements java.util.Comparator {
      /**
       *  Description of the Method
       *
       * @param  lhs  Description of Parameter
       * @param  rhs  Description of Parameter
       * @return      Description of the Returned Value
       * @since       2.9.12
       */
      public int compare(Object lhs, Object rhs) {
         if (lhs instanceof AClass) {
            if (rhs instanceof AClass) {
               int value = ((ANode)lhs).getName().compareTo(((ANode)rhs).getName());
               //System.out.println("class lhs="+((ANode)lhs).getName()+", rhs="+((ANode)rhs).getName() +" => "+value);
               return value;
            } else {
               return 1;
            }
         } else if (rhs instanceof AClass) {
            return -1;
         }
         int value = ((ANode)lhs).getName().compareTo(((ANode)rhs).getName());
         //System.out.println("other lhs="+((ANode)lhs).getName()+", rhs="+((ANode)rhs).getName() +" => "+value);
         return value;
      }

   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class StandardOrderComparator implements java.util.Comparator {
      /**
       *  Description of the Method
       *
       * @param  lhs  Description of Parameter
       * @param  rhs  Description of Parameter
       * @return      Description of the Returned Value
       * @since       2.9.12
       */
      public int compare(Object lhs, Object rhs) {
         if (lhs instanceof AField) {
            if (rhs instanceof AField) {
               return ((AField)lhs).getName().compareTo(((AField)rhs).getName());
            }
            return -1;
         } else if (rhs instanceof AField) {
            return 1;
         }
         if (lhs instanceof AConstructor) {
            if (rhs instanceof AConstructor) {
               return ((AConstructor)lhs).getName().compareTo(((AConstructor)rhs).getName());
            }
            return -1;
         } else if (rhs instanceof AConstructor) {
            return 1;
         }
         if (lhs instanceof AMethod) {
            if (rhs instanceof AMethod) {
               return ((AMethod)lhs).getName().compareTo(((AMethod)rhs).getName());
            }
            return -1;
         } else if (rhs instanceof AMethod) {
            return 1;
         }
         if (lhs instanceof AnInterface) {
            if (rhs instanceof AnInterface) {
               return ((AnInterface)lhs).getName().compareTo(((AnInterface)rhs).getName());
            }
            return -1;
         } else if (rhs instanceof AnInterface) {
            return 1;
         }
         if (lhs instanceof AClass) {
            if (rhs instanceof AClass) {
               return ((AClass)lhs).getName().compareTo(((AClass)rhs).getName());
            }
            return -1;
         } else if (rhs instanceof AClass) {
            return 1;
         }
         return ((ANode)lhs).getName().compareTo(((ANode)rhs).getName());
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class Navigation {
      Map mapBufferToParser = new HashMap();
      Timer timerPopup = null;
      //private List buffers;
      private final Frame view;
      private final Navigator navigator;


      /**
       *  Constructor for the Navigation object
       *
       * @param  view       Description of Parameter
       * @param  navigator  Description of Parameter
       * @since             2.9.12
       */
      public Navigation(Frame view, Navigator navigator) {
         //System.out.println("new Navigation(" + view + ")");
         this.view = view;
         this.navigator = navigator;
      }


      /**
       *  Description of the Method
       *
       * @since    2.9.12
       */
      public void stop() {
         IDEPlugin.log(IDEInterface.MESSAGE, NAME, "Stopping DotComplete for view " + view.getName());
         // stop all parsers (kind of round about b/c otherwise we get ConcurrentModEx)
         IDEPlugin.log(IDEInterface.MESSAGE, NAME, "buffers " + mapBufferToParser.keySet().size());
         Object[] buffers = new Object[mapBufferToParser.keySet().size()];
         int buf = 0;
         for (Iterator it = mapBufferToParser.keySet().iterator(); it.hasNext(); buf++) {
            buffers[buf] = (Object)it.next();
         }
         for (int i = 0, l = buffers.length; i < l; i++) {
            removeBuffer(buffers[i]);
         }

         // unlock and popups and dispose them
         IDEPlugin.log(IDEInterface.MESSAGE, NAME, "Done");
      }


      /**
       *  Adds a feature to the Buffer attribute of the Navigation object
       *
       * @param  buffer  The feature to be added to the Buffer attribute
       * @since          2.9.12
       */
      synchronized void addBuffer(Object buffer) {
         try {
            if (IDEPlugin.getFilePathForBuffer(buffer).trim().toLowerCase().endsWith(".java")) {
               if (!mapBufferToParser.containsKey(buffer)) {
                  // first, get the class path source
                  JavaParserThread jpt = new JavaParserThread(view, navigator);
                  jpt.init(buffer, IDEPlugin.getFilePathForBuffer(buffer), new java.io.StringReader(IDEPlugin.getText(view, buffer)));
                  mapBufferToParser.put(buffer, jpt);
                  jpt.start();
               }
            }
         } catch (Exception e) {
            IDEPlugin.log(IDEInterface.MESSAGE, NAME, e.getMessage());
         }
      }


      /**
       *  Description of the Method
       *
       * @param  buffer  Description of Parameter
       * @since          2.9.12
       */
      synchronized void removeBuffer(Object buffer) {
         IDEPlugin.log(IDEInterface.MESSAGE, NAME, "removing buffer!");
         JavaParserThread jpt = (JavaParserThread)mapBufferToParser.get(buffer);
         if (jpt != null) {
            jpt.stopSoon();
            try {
               IDEPlugin.log(IDEInterface.DEBUG, NAME, "Waiting 5 seconds for thread to stop " + IDEPlugin.getFilePathForBuffer(buffer));
               jpt.join(5 * 1000);
               IDEPlugin.log(IDEInterface.DEBUG, NAME, "Joined successfully! " + IDEPlugin.getFilePathForBuffer(buffer));
            } catch (InterruptedException e) {
               IDEPlugin.log(IDEInterface.ERROR, "Error waiting for Parse thread to exit", e);
            }
         }
         mapBufferToParser.remove(buffer);
         PTVData data = new PTVData(IDEPlugin.getFilePathForBuffer(buffer));
         navigator.removeNodeFromTree(data.model);
      }
   }



   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private final static class JavaParserThread extends Thread {
      JavaParser parser;
      private Object buffer = null;
      private String path = null;
      private Reader reader = null;
      private boolean docChange;
      private boolean dirty;
      private boolean firstPass;
      private boolean generateSyntaxErrorInfo = false;
      private boolean generateSemanticErrorInfo = false;
      private Navigator navigator;
      private Frame view;

      private boolean bRun = true;  // adding for safer stopping of parse thread
      private Timer docTimer = null;
      private FileSettings settings;



      /**
       *  Constructor for the JavaParserThread object
       *
       * @param  view       Description of Parameter
       * @param  navigator  Description of Parameter
       * @since             2.9.12
       */
      public JavaParserThread(Frame view, Navigator navigator) {
         this.view = view;
         this.navigator = navigator;
         setName("JavaParser");
         setPriority(DEFAULT_PARSER_PRIORITY);
         parser = new JavaParser((Reader)null);
         settings = FileSettings.getRefactoryPrettySettings();
         docChange = false;
         dirty = false;
         firstPass = true;
      }


      /**
       *  Sets the DocChange attribute of the JavaParserThread object
       *
       * @param  docChange  The new DocChange value
       * @since             2.9.12
       */
      public void setDocChange(boolean docChange) {
         this.docChange = docChange;
      }


      /**
       *  Description of the Method
       *
       * @param  reader  Description of Parameter
       * @param  path    Description of Parameter
       * @since          2.9.12
       */
      public void init(Reader reader, String path) {
         this.reader = reader;
         this.path = path;
      }


      /**
       *  Description of the Method
       *
       * @param  buffer  Description of Parameter
       * @param  path    Description of Parameter
       * @param  reader  Description of Parameter
       * @since          2.9.12
       */
      public void init(Object buffer, String path, Reader reader) {
         init(reader, path);
         this.buffer = buffer;
         docChange = true;
      }


      /**
       *  Main processing method for the JavaParserThread object
       *
       * @since    2.9.12
       */
      public void run() {
         if (reader == null) {
            throw new IllegalStateException("Must call init() before run().");
         }

         if (buffer != null) {
            do {
               // sleep until a change is made to the document
               while (!docChange) {
                  try {
                     synchronized (this) {
                        wait();
                     }
                  } catch (InterruptedException iex) {
                     docChange = false;
                     bRun = false;
                     return;
                  }
               }
               docChange = false;

               // only do the parsing if the
               boolean enableNavigator = true;
               try {
                  enableNavigator = settings.getBoolean("navigator.enable");
               } catch (Exception snfe) {
                  // Default is sufficient
               }
               if (firstPass || enableNavigator) {
                  firstPass = false;
                  //System.out.println("buffer="+buffer);
                  String text = IDEPlugin.getText(view, buffer);
                  //String name = IDEPlugin.getFilePathForBuffer(buffer);
                  reader = new java.io.StringReader(text);
                  IDEPlugin.log(IDEInterface.MESSAGE, NAME, "PARSE @" + new java.util.Date().toString() + " - " + path);
                  try {
                     oneParsePass();
                  } catch (NullPointerException e) {
                     e.printStackTrace();
                     IDEPlugin.log(IDEInterface.ERROR, "Navigator: NPE, Error parsing " + path, e);
                  } catch (Throwable t) {
                     IDEPlugin.log(IDEInterface.ERROR, "Navigator: Error parsing " + path, t);
                  }
               }
            } while (bRun);
            //if (generateSyntaxErrorInfo || generateSemanticErrorInfo) {
            //   errorTable.replace(getPath(), new ErrorTable());
            //}
         } else {
            oneParsePass();
         }
      }


      /**
       *  Called when text is inserted into the buffer. Reparses the buffer when more than 16 characters are inserted or
       *  ; or }
       *
       * @param  offset  Description of Parameter
       * @param  length  Description of Parameter
       * @since          2.9.12
       */
      public void contentInserted(int offset, int length) {
         IDEPlugin.log(IDEInterface.DEBUG, this, "insertUpdate: " + length + "]");
         if (length >= 16) {
            docChange = true;
            //System.out.println("contentInserted(" + offset + "," + length + ") more than 16 chars so notify");
            synchronized (this) {
               notify();
            }
         } else {
            String text = IDEPlugin.getText(view, buffer);
            char[] array = text.substring(offset, offset + length).toCharArray();

            for (int i = 0; i < length; i++) {
               //System.out.println("array["+i+"]='"+array[i]+"'");
               if (array[i] == ';' || array[i] == '}') {
                  docChange = true;
                  //System.out.println("contentInserted(" + offset + "," + length + ") array[" + i + "]=" + array[i] + " so notify");
                  synchronized (this) {
                     notify();
                  }
                  break;
               }
            }
         }

         if (!docChange) {
            //System.out.println("docChange==false");
            if (docTimer == null) {
               //System.out.println("starting timer");
               docTimer = new Timer(5000, new DocRunner(this));
               docTimer.start();
            } else {
               //System.out.println("restarting timer");
               docTimer.restart();
            }
         }
      }


      /**
       *  Description of the Method
       *
       * @param  offset  Description of Parameter
       * @param  length  Description of Parameter
       * @since          2.9.12
       */
      public void contentRemoved(int offset, int length) {
         // Called when text is removed from the buffer.
         // Reparses the buffer when more than 2 characters are deleted
         IDEPlugin.log(IDEInterface.DEBUG, this, "removeUpdate: " + length + "]");
         if (length >= 16) {
            docChange = true;
            //System.out.println("contentRemoved(" + offset + "," + length + ") more than 16 chars so notify");
            synchronized (this) {
               notify();
            }
         } else {
            if (docTimer == null) {
               //System.out.println("starting timer");
               docTimer = new Timer(5000, new DocRunner(this));
               docTimer.start();
            } else {
               //System.out.println("restarting timer");
               docTimer.restart();
            }
         }
      }


      /**
       *  Called after an undo or compound edit has finished.
       *
       * @since    2.9.12
       */
      public void transactionComplete() {
         //System.out.println("transactionComplete()  so notify");
         synchronized (this) {
            notify();
         }
      }


      /**
       *  Avoids using Thread.stop()
       *
       * @since    2.9.12
       */
      public synchronized void stopSoon() {
         if (buffer != null) {
            docChange = true;
            bRun = false;
         }
         notify();
      }


      /**
       *  Description of the Method
       *
       * @since    2.9.12
       */
      private void oneParsePass() {
         parser.ReInit(reader);

         preParse();
         try {

            ASTCompilationUnit compilationUnit = parser.CompilationUnit();
            ParseTreeVisitor visitor = new ParseTreeVisitor();
            PTVData data = new PTVData(path);
            visitor.visit(compilationUnit, data);
            navigator.updateTree(data.model);
            IDEPlugin.bufferParsed(view, buffer, compilationUnit);
            IDEPlugin.bufferNavigatorTree(view, buffer, (TreeNode)data.model.getRoot());
         } catch (ParseException pex) {
            //if (generateSyntaxErrorInfo) {
            //    error(pex.currentToken.next, "Syntax error.");
            //}
            //pex.printStackTrace(System.err);
         } catch (TokenMgrError tme) {
            //tme.printStackTrace(System.err);
         }
         postParse();
      }


      /**
       *  Description of the Method
       *
       * @since    2.9.12
       */
      private void preParse() { }


      /**
       *  Description of the Method
       *
       * @since    2.9.12
       */
      private synchronized void postParse() { }


      /**
       *  Description of the Class
       *
       * @author    Mike Atkinson
       * @since     2.9.12
       */
      private class DocRunner implements ActionListener {
         JavaParserThread runner;


         /**
          *  Constructor for the DocRunner object
          *
          * @param  jpt  Description of Parameter
          * @since       2.9.12
          */
         public DocRunner(JavaParserThread jpt) {
            runner = jpt;
         }


         /**
          *  Description of the Method
          *
          * @param  event  Description of Parameter
          * @since         2.9.12
          */
         public void actionPerformed(ActionEvent event) {
            docTimer.stop();
            docTimer = null;
            //System.out.println("stopped timer - notify");
            docChange = true;
            synchronized (runner) {
               runner.notify();
            }
         }
      }

   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class ParseTreeVisitor extends JavaParserVisitorAdapter {
      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTInterfaceDeclaration node, Object data) {
         String className = node.getUnmodifedInterfaceDeclaration().getImage();
         PTVData ptv = (PTVData)data;
         ptv.push(new AClass(className, node));
         super.visit(node, data);
         ptv.pop();
         return ptv;
      }


      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTNestedInterfaceDeclaration node, Object data) {
         String className = node.getUnmodifedInterfaceDeclaration().getImage();
         PTVData ptv = (PTVData)data;
         ptv.push(new AClass(className, node));
         super.visit(node, data);
         ptv.pop();
         return ptv;
      }


      /**
       *  Outer class declaration
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTClassDeclaration node, Object data) {
         String className = ((ASTUnmodifiedClassDeclaration)node.jjtGetFirstChild()).getImage();
         PTVData ptv = (PTVData)data;
         ptv.push(new AClass(className, node));
         super.visit(node, data);
         ptv.pop();
         return ptv;
      }


      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTNestedClassDeclaration node, Object data) {
         String className = ((ASTUnmodifiedClassDeclaration)node.jjtGetFirstChild()).getImage();
         PTVData ptv = (PTVData)data;
         ptv.push(new AClass(className, node));
         super.visit(node, data);
         ptv.pop();
         return ptv;
      }


      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTClassBody node, Object data) {
         if (node.jjtGetParent() instanceof ASTUnmodifiedClassDeclaration) {
            return super.visit(node, data);
         }
         PTVData ptv = (PTVData)data;
         ptv.push(new AClass("<anonymous>", node));
         super.visit(node, data);
         ptv.pop();
         return ptv;
      }


      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTMethodDeclaration node, Object data) {
         for (int n = 0; n < node.jjtGetNumChildren(); n++) {
            SimpleNode child = (SimpleNode)node.jjtGetChild(n);
            if (child instanceof ASTMethodDeclarator) {
               String methodName = child.getImage();
               PTVData ptv = (PTVData)data;
               ptv.push(new AMethod(methodName, node));
               super.visit(node, data);
               ptv.pop();
               return ptv;
            }
         }
         super.visit(node, data);
         return data;
      }


      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTConstructorDeclaration node, Object data) {
         String constructorName = node.getName();
         PTVData ptv = (PTVData)data;
         ptv.push(new AConstructor(constructorName, node));
         super.visit(node, data);
         ptv.pop();
         return ptv;
      }


      /**
       *  Description of the Method
       *
       * @param  node  Description of Parameter
       * @param  data  Description of Parameter
       * @return       Description of the Returned Value
       * @since        2.9.12
       */
      public Object visit(ASTFieldDeclaration node, Object data) {
         List fields = node.findChildrenOfType(ASTVariableDeclaratorId.class);
         if (fields.size() > 0) {
            String fieldName = ((ASTVariableDeclaratorId)fields.get(0)).getName();
            PTVData ptv = (PTVData)data;
            ptv.push(new AField(fieldName, node));
            super.visit(node, data);
            ptv.pop();
         } else {
            super.visit(node, data);
         }
         return data;
      }

   }



   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class PTVData {
      /**
       *  Description of the Field
       *
       * @since    2.9.12
       */
      public DefaultTreeModel model;
      /**
       *  Description of the Field
       *
       * @since    2.9.12
       */
      public DefaultMutableTreeNode currentNode;


      /**
       *  Constructor for the PTVData object
       *
       * @param  name  Description of Parameter
       * @since        2.9.12
       */
      public PTVData(String name) {
         currentNode = new ASourceFile(name, null);
         model = new DefaultTreeModel(currentNode);
      }


      /**
       *  Description of the Method
       *
       * @param  child  Description of Parameter
       * @since         2.9.12
       */
      public void push(DefaultMutableTreeNode child) {
         currentNode.add(child);
         currentNode = child;
      }


      /**
       *  Description of the Method
       *
       * @since    2.9.12
       */
      public void pop() {
         currentNode = (DefaultMutableTreeNode)currentNode.getParent();
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class NodeData extends ModifierAdapter {
      private int beginLine;


      /**
       *  Constructor for the NodeData object
       *
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public NodeData(SimpleNode node) {
         beginLine = (node == null) ? 0 : node.getBeginLine();
         if (node instanceof AccessNode) {
            setModifiers(((AccessNode)node).getModifiers());
         }
      }


      /**
       *  Gets the beginLine attribute of the NodeData object
       *
       * @return    The beginLine value
       * @since     2.9.12
       */
      public int getBeginLine() {
         return beginLine;
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class ANode extends DefaultMutableTreeNode {
      String name;


      /**
       *  Constructor for the ANode object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public ANode(String name, SimpleNode node) {
         this.name = name;
         setUserObject(new NodeData(node));
      }


      /**
       *  Sets the name attribute of the ANode object
       *
       * @param  name  The new name value
       * @since        2.9.12
       */
      public void setName(String name) {
         this.name = name;
      }


      /**
       *  Gets the Line attribute of the ANode object
       *
       * @return    The Line value
       * @since     2.9.12
       */
      public int getLine() {
         return ((NodeData)getUserObject()).getBeginLine();
      }


      /**
       *  Gets the Name attribute of the ANode object
       *
       * @return    The Name value
       * @since     2.9.12
       */
      public String getName() {
         return name;
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Returned Value
       * @since     2.9.12
       */
      public String toString() {
         return name;
      }


      /**
       *  Description of the Method
       *
       * @param  comparator  Description of Parameter
       * @since              2.9.12
       */
      public void sort(java.util.Comparator comparator) {
         if (children != null) {
            List sortedChildren = new ArrayList(children);
            removeAllChildren();
            java.util.Collections.sort(sortedChildren, comparator);
            for (Iterator i = sortedChildren.iterator(); i.hasNext(); ) {
               add((ANode)i.next());
            }
         }
         for (Enumeration i = children(); i.hasMoreElements(); ) {
            ANode child = (ANode)i.nextElement();
            if (!child.isLeaf()) {
               child.sort(comparator);
            }
         }
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class ASourceFile extends ANode {
      private String filename = "";


      /**
       *  Constructor for the ASourceFile object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public ASourceFile(String name, SimpleNode node) {
         super(name, node);
         int index = name.lastIndexOf("\\");
         if (index < 0) {
            index = name.lastIndexOf("/");
         }
         StringBuffer sb = new StringBuffer(name.substring(index+1));
         for (int i=sb.length(); i<30; i++) {
            sb.append(" ");
         }
         sb.append(" (" + name.substring(0, index+1) + ")");
         setName(sb.toString());
         filename = name;
      }


      /**
       *  Gets the fileName attribute of the ASourceFile object
       *
       * @return    The fileName value
       * @since     2.9.12
       */
      public String getFileName() {
         return filename;
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class AnInterface extends ANode {
      /**
       *  Constructor for the AClass object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public AnInterface(String name, SimpleNode node) {
         super(name, node);
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class AClass extends ANode {
      /**
       *  Constructor for the AClass object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public AClass(String name, SimpleNode node) {
         super(name, node);
      }

   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class AMethod extends ANode {
      /**
       *  Constructor for the AMethod object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public AMethod(String name, SimpleNode node) {
         super(name, node);
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class AField extends ANode {
      /**
       *  Constructor for the AMethod object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public AField(String name, SimpleNode node) {
         super(name, node);
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    * @since     2.9.12
    */
   private static class AConstructor extends ANode {
      /**
       *  Constructor for the AConstructor object
       *
       * @param  name  Description of Parameter
       * @param  node  Description of Parameter
       * @since        2.9.12
       */
      public AConstructor(String name, SimpleNode node) {
         super(name, node);
      }
   }


   /**
    *  Description of the Class
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class RunGotoNode implements Runnable {
      private ANode node;
      private Frame view;


      /**
       *  Constructor for the RunGotoViolation object
       *
       * @param  node  Description of Parameter
       * @param  view  Description of Parameter
       * @since        2.9.12
       */
      public RunGotoNode(ANode node, Frame view) {
         this.node = node;
         this.view = view;
      }


      /**
       *  Main processing method for the RunGotoViolation object
       *
       * @since    2.9.12
       */
      public void run() {
         try {
            String filename = getFilename(node);
            if (filename != null) {
               final Object buffer = IDEPlugin.openFile(view, filename);
               IDEPlugin.setBuffer(view, buffer);
               int start = IDEPlugin.getLineStartOffset(buffer, node.getLine() - 1);
               int end = IDEPlugin.getLineStartOffset(buffer, node.getLine());
               if (start < 0) {
                  start = 0;
               }
               IDEPlugin.setSelection(view, buffer, start, end);
               IDEPlugin.moveCaretPosition(view, buffer, end);
            }
         } catch (Exception ex) {
            ex.printStackTrace();
            IDEPlugin.log(IDEInterface.ERROR, "Navigator", "can't open duplicate file! " + ex.getMessage());
         }
      }


      /**
       *  Gets the Filename attribute of the RunGotoNode object
       *
       * @param  node  Description of Parameter
       * @return       The Filename value
       * @since        2.9.12
       */
      private String getFilename(ANode node) {
         TreeNode thisNode = node;
         while (thisNode != null) {
            if (thisNode instanceof ASourceFile) {
               return ((ASourceFile)thisNode).getFileName();
            }
            thisNode = thisNode.getParent();
         }
         return null;
      }
   }


   /**
    *  Custom cell renderer for the bug tree. We use this to select the tree icons, and to set the text color based on
    *  the bug priority.
    *
    * @author    Mike Atkinson
    * @since     2.9.12
    */
   private static class NavigatorRenderer extends DefaultTreeCellRenderer {
      //private ImageIcon packageIcon;
      private ImageIcon classPublicIcon;
      private ImageIcon classPrivateIcon;
      private ImageIcon classPackageIcon;
      private ImageIcon classProtectedIcon;
      private ImageIcon interfacePublicIcon;
      private ImageIcon interfacePrivateIcon;
      private ImageIcon interfacePackageIcon;
      private ImageIcon interfaceProtectedIcon;
      private ImageIcon constructorPublicIcon;
      private ImageIcon constructorPrivateIcon;
      private ImageIcon constructorPackageIcon;
      private ImageIcon constructorProtectedIcon;
      private ImageIcon methodPublicIcon;
      private ImageIcon methodPrivateIcon;
      private ImageIcon methodPackageIcon;
      private ImageIcon methodProtectedIcon;
      private ImageIcon methodStPublicIcon;
      private ImageIcon methodStPrivateIcon;
      private ImageIcon methodStPackageIcon;
      private ImageIcon methodStProtectedIcon;
      private ImageIcon fieldPublicIcon;
      private ImageIcon fieldPrivateIcon;
      private ImageIcon fieldPackageIcon;
      private ImageIcon fieldProtectedIcon;
      private ImageIcon fieldStPublicIcon;
      private ImageIcon fieldStPrivateIcon;
      private ImageIcon fieldStPackageIcon;
      private ImageIcon fieldStProtectedIcon;
      private ImageIcon sourceFileIcon;
      private Object value;


      /**
       *  Constructor for the NavigatorRenderer object
       *
       * @since    2.9.12
       */
      public NavigatorRenderer() {
         ClassLoader classLoader = this.getClass().getClassLoader();
         classPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classPublic.gif"));
         classPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classPrivate.gif"));
         classPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classPackage.gif"));
         classProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/classProtected.gif"));
         interfacePublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfacePublic.gif"));
         interfacePrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfacePrivate.gif"));
         interfacePackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfacePackage.gif"));
         interfaceProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/interfaceProtected.gif"));
         constructorPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/constructorPublic.gif"));
         methodPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodPublic.gif"));
         methodPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodPrivate.gif"));
         methodPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodPackage.gif"));
         methodProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodProtected.gif"));
         methodStPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStPublic.gif"));
         methodStPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStPrivate.gif"));
         methodStPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStPackage.gif"));
         methodStProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/methodStProtected.gif"));
         fieldPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variablePublic.gif"));
         fieldPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variablePrivate.gif"));
         fieldPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variablePackage.gif"));
         fieldProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableProtected.gif"));
         fieldStPublicIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStPublic.gif"));
         fieldStPrivateIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStPrivate.gif"));
         fieldStPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStPackage.gif"));
         fieldStProtectedIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/variableStProtected.gif"));
         sourceFileIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/sourcefile.gif"));
      }


      /**
       *  Gets the treeCellRendererComponent attribute of the NavigatorRenderer object
       *
       * @param  tree      Description of Parameter
       * @param  value     Description of Parameter
       * @param  sel       Description of Parameter
       * @param  expanded  Description of Parameter
       * @param  leaf      Description of Parameter
       * @param  row       Description of Parameter
       * @param  hasFocus  Description of Parameter
       * @return           The treeCellRendererComponent value
       * @since            2.9.12
       */
      public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
                                                    boolean expanded, boolean leaf, int row, boolean hasFocus) {
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
         Object obj = node.getUserObject();

         this.value = obj;

         super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

         // Set the icon, depending on what kind of node it is
         if (value instanceof AnInterface) {
            if (obj instanceof ModifierHolder) {
               ModifierHolder astnode = (ModifierHolder)obj;
               if (astnode.isPublic()) {
                  setIcon(interfacePublicIcon);
               } else if (astnode.isPrivate()) {
                  setIcon(interfacePrivateIcon);
               } else if (astnode.isProtected()) {
                  setIcon(interfaceProtectedIcon);
               } else {
                  setIcon(interfacePackageIcon);
               }
            } else {
               setIcon(interfacePublicIcon);
            }
         } else if (value instanceof AClass) {
            if (obj instanceof ModifierHolder) {
               ModifierHolder astnode = (ModifierHolder)obj;
               if (astnode.isPublic()) {
                  setIcon(classPublicIcon);
               } else if (astnode.isPrivate()) {
                  setIcon(classPrivateIcon);
               } else if (astnode.isProtected()) {
                  setIcon(classProtectedIcon);
               } else {
                  setIcon(classPackageIcon);
               }
            } else {
               setIcon(classPublicIcon);
            }
         } else if (value instanceof AConstructor) {
            ModifierHolder astnode = (ModifierHolder)obj;
            if (astnode.isPublic()) {
               setIcon(constructorPublicIcon);
            } else if (astnode.isPrivate()) {
               setIcon(constructorPrivateIcon);
            } else if (astnode.isProtected()) {
               setIcon(constructorProtectedIcon);
            } else {
               setIcon(constructorPackageIcon);
            }
         } else if (value instanceof AMethod) {
            ModifierHolder astnode = (ModifierHolder)obj;
            boolean isStatic = astnode.isStatic();
            if (astnode.isPublic()) {
               setIcon((isStatic) ? methodStPublicIcon : methodPublicIcon);
            } else if (astnode.isPrivate()) {
               setIcon((isStatic) ? methodStPrivateIcon : methodPrivateIcon);
            } else if (astnode.isProtected()) {
               setIcon((isStatic) ? methodStProtectedIcon : methodProtectedIcon);
            } else {
               setIcon((isStatic) ? methodStPackageIcon : methodPackageIcon);
            }
         } else if (value instanceof AField) {
            ModifierHolder astnode = (ModifierHolder)obj;
            boolean isStatic = astnode.isStatic();
            if (astnode.isPublic()) {
               setIcon((isStatic) ? fieldStPublicIcon : fieldPublicIcon);
            } else if (astnode.isPrivate()) {
               setIcon((isStatic) ? fieldStPrivateIcon : fieldPrivateIcon);
            } else if (astnode.isProtected()) {
               setIcon((isStatic) ? fieldStProtectedIcon : fieldProtectedIcon);
            } else {
               setIcon((isStatic) ? fieldStPackageIcon : fieldPackageIcon);
            }
         } else if (value instanceof ASourceFile) {
            setIcon(sourceFileIcon);
         } else {
            setIcon(null);
         }

         return this;
      }


      /**
       *  Gets the textNonSelectionColor attribute of the NavigatorRenderer object
       *
       * @return    The textNonSelectionColor value
       * @since     2.9.12
       */
      public Color getTextNonSelectionColor() {
         return getCellTextColor();
      }


      /**
       *  Gets the cellTextColor attribute of the NavigatorRenderer object
       *
       * @return    The cellTextColor value
       * @since     2.9.12
       */
      private Color getCellTextColor() {
         // Based on the priority, color-code the bug instance.
         Color color = Color.BLACK;
         return color;
      }
   }
}

