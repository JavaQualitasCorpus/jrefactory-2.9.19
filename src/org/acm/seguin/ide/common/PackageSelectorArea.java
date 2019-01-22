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
package org.acm.seguin.ide.common;

import java.awt.Dimension;
import java.awt.Component;
import java.io.IOException;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.*;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.util.FileSettings;
import net.sourceforge.jrefactory.uml.PackageSummaryListModel;
import net.sourceforge.jrefactory.uml.UMLPackage;


/**
 *  Just the package selector area
 *
 *@author     Chris Seguin
 *@created    October 18, 2001
 */
public class PackageSelectorArea extends JPanel {
    /**
     *  The list box of packages
     */
    protected JTree packages;
    private JScrollPane pane;
    public DefaultTreeModel model;
    public ANode rootNode;

    /** The instance of BugCellRenderer. */
    private static final NavigatorRenderer navigatorRenderer = new NavigatorRenderer();

    /**
     *  Constructor for the PackageSelectorArea object
     */
    public PackageSelectorArea() {
        //  Setup the UI
        setLayout(null);
        super.setSize(220, 300);

        //  Create the list
        packages = new JTree();
        packages.setCellRenderer(navigatorRenderer);

        rootNode = new RootNode("Modules", null);
        model = new DefaultTreeModel(rootNode);
        packages.setModel(model);
        

        pane = new JScrollPane(packages);
        pane.setBounds(10, 10, 200, 280);
        add(pane);
    }


    /**
     *  Gets the ScrollPane attribute of the PackageSelectorArea object
     *
     *@return    The ScrollPane value
     */
    public JScrollPane getScrollPane() {
        return pane;
    }


    /**
     *  Gets the Selection attribute of the PackageSelectorArea object
     *
     *@return    The Selection value
     */
    public PackageSummary getSelection() {
        TreePath selection = packages.getSelectionPath();
        return (PackageSummary) ((ANode)selection.getLastPathComponent()).getUserObject();
    }


    /**
     *  Loads the package into the listbox
     */
    public void loadPackages() {
        //PackageSummaryListModel model = new PackageSummaryListModel();

        //  Get the list of packages
        Iterator iter = PackageSummary.getAllPackages();
        if (iter == null) {
            return;
        }

        //  Add in the packages
        UMLPackage view = null;
        PackageSummary packageSummary = null;
        PackageListFilter filter = PackageListFilter.get();
        while (iter.hasNext()) {
            packageSummary = (PackageSummary) iter.next();
            if (filter.isIncluded(packageSummary)) {
                //model.add(packageSummary);
                String name = packageSummary.getName();
                addSummaryFirst(rootNode, name, packageSummary);
            }
        }

        //  Set the model
        packages.setModel(model);
        model.nodeStructureChanged(rootNode);
        model.reload();
        saveKnownModules();
    }


    public void saveKnownModules() {
       System.out.println("saveKnownModules()");
       StringBuffer buf = new StringBuffer();
       Enumeration nodes = rootNode.children();
       while (nodes.hasMoreElements()) {
          ANode node = (ANode)nodes.nextElement();
          if (node instanceof ModuleNode) {
             String name = node.getName();
             if (buf.length()>0) {
                buf.append(";");
             }
             buf.append(name);
          }
       }
       FileSettings bundle = FileSettings.getRefactorySettings("proj");
       bundle.setString("known.modules", buf.toString());
       bundle.save();
    }


    /**
     *  Load the summaries
     */
    public void loadSummaries() { }


    private void addSummaryFirst(ANode toNode, String name, PackageSummary summary) {
       //System.out.println("summary.isTopLevel()="+summary.isTopLevel());
       //System.out.println("summary.getDirectory()="+summary.getDirectory());
       String file = null;
       try {
          file = summary.getDirectory().getCanonicalPath();
       } catch (Exception e) {
       }
       if (file == null) {
          file = "JDK";
       } else if (file.length()>name.length()) {
          file = file.substring(0,file.length()-name.length());
          if (file.endsWith("\\")) {
             file = file.substring(0, file.length()-1);
          } else if (file.endsWith("/")) {
             file = file.substring(0, file.length()-1);
          }
       }
       ANode childNode = null;
       for (Enumeration children = toNode.children(); children.hasMoreElements(); ) {
          ANode child = (ANode)children.nextElement();
          if (child.getName().equals(file)) {
             childNode = child;
          }
       }
       if (childNode==null) {
          childNode = new ModuleNode(file, null);
          toNode.add(childNode);
          //System.out.println("created node "+file);
          model.reload();
       }
       addSummary(childNode, name, summary);
    }
    private void addSummary(ANode toNode, String name, PackageSummary summary) {
       //System.out.println("toNode="+toNode+", name="+name+", summary="+summary.getName());
       if (name.indexOf(".")>0) {
          ANode childNode = null;
          String x = name.substring(0, name.indexOf("."));
          for (Enumeration children = toNode.children(); children.hasMoreElements(); ) {
             ANode child = (ANode)children.nextElement();
             if (child.getName().equals(x)) {
                childNode = child;
             }
          }
          if (childNode==null) {
             childNode = new PackageNode(x, null);
             toNode.add(childNode);
             //System.out.println("created node "+x);
          }
          addSummary(childNode, name.substring(name.indexOf(".")+1), summary);
       } else {
          ANode childNode = null;
          for (Enumeration children = toNode.children(); children.hasMoreElements(); ) {
             ANode child = (ANode)children.nextElement();
             if (child.getName().equals(name)) {
                childNode = child;
             }
          }
          if (childNode==null) {
             //System.out.println("Adding child node "+name);
             toNode.add(new PackageNode(name, summary));
          } else {
             //System.out.println("Found child node "+name);
             //System.out.println("  childNode.getUserObject()="+childNode.getUserObject());{
             NodeData data = (NodeData)childNode.getUserObject();
             if (data.summary==null && summary!=null) {
                   data.summary=summary;
             }
          }
       }
    }


   protected static class NodeData {
      PackageSummary summary;
      public NodeData(PackageSummary summary) {
         this.summary = summary;
      }
      public int getBeginLine() {
         return 0;
      }
      public PackageSummary getPackageSummary() {
         return summary;
      }
   }

   /**
    *  Description of the Class
    *
    * @author    Chris Seguin
    */
   protected static class ANode extends DefaultMutableTreeNode {
      String name;


      /**
       *  Constructor for the ANode object
       *
       * @param  name  Description of Parameter
       * @param  summary  Description of Parameter
       */
      public ANode(String name, PackageSummary summary) {
         this.name = name;
         setUserObject(new NodeData(summary));
      }


      /**
       *  Gets the Line attribute of the ANode object
       *
       * @return    The Line value
       */
      public int getLine() {
         return ((NodeData)getUserObject()).getBeginLine();
      }


      /**
       *  Gets the Name attribute of the ANode object
       *
       * @return    The Name value
       */
      public String getName() {
         return name;
      }


      /**
       *  Description of the Method
       *
       * @return    Description of the Returned Value
       */
      public String toString() {
         return name;
      }
      
      public void sort(java.util.Comparator comparator) {
         if (children!=null) {
            List sortedChildren = new ArrayList(children);
            removeAllChildren();
            java.util.Collections.sort(sortedChildren, comparator);
            for (Iterator i= sortedChildren.iterator(); i.hasNext(); ) {
               add((ANode)i.next());
            }
         }
         Enumeration i = children();
         while (i.hasMoreElements()) {
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
    */
   protected static class RootNode extends ANode {
      /**
       *  Constructor for the ASourceFile object
       *
       * @param  name  Description of Parameter
       * @param  summary  Description of Parameter
       */
      public RootNode(String name, PackageSummary summary) {
         super(name, summary);
      }
   }

   protected static class PackageNode extends ANode {
      public PackageNode(String name, PackageSummary summary) {
         super(name, summary);
      }
   }

   protected static class ModuleNode extends ANode {
      public ModuleNode(String name, PackageSummary summary) {
         super(name, summary);
      }
   }

    private static class NavigatorRenderer extends DefaultTreeCellRenderer {
        private ImageIcon packageIcon;
        private ImageIcon emptyPackageIcon;
        private ImageIcon modulesIcon;
        private Object value;
        
        public NavigatorRenderer() {
            ClassLoader classLoader = this.getClass().getClassLoader();
            packageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/package.gif"));
            emptyPackageIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/emptyPackage.gif"));
            modulesIcon = new ImageIcon(classLoader.getResource("org/acm/seguin/ide/common/icons/modules.gif"));
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
        boolean expanded, boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object obj = node.getUserObject();
            
            this.value = obj;
            
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            
            // Set the icon, depending on what kind of node it is
            if (node.isRoot()) {
                setIcon(modulesIcon);
            } else if (leaf) {
                setIcon(packageIcon);
            } else if (node instanceof ModuleNode) {
                setIcon(modulesIcon);
            } else if (obj instanceof NodeData) {
                if ( ((NodeData)obj).getPackageSummary() != null) {
                   setIcon(packageIcon);
                } else {
                   setIcon(emptyPackageIcon);
                }
            } else {
                setIcon(emptyPackageIcon);
            }
            return this;
        }
    }
}

