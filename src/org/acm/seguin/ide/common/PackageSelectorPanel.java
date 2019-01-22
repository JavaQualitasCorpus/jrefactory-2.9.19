/*
 *  ====================================================================
 *  The JRefactory License, Version 1.0
 *
 *  Copyright (c) 2003 JRefactory.  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 *  3. The end-user documentation included with the redistribution,
 *  if any, must include the following acknowledgment:
 *  "This product includes software developed by the
 *  JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *  Alternately, this acknowledgment may appear in the software itself,
 *  if and wherever such third-party acknowledgments normally appear.
 *
 *  4. The names "JRefactory" must not be used to endorse or promote
 *  products derived from this software without prior written
 *  permission. For written permission, please contact seguin@acm.org.
 *
 *  5. Products derived from this software may not be called "JRefactory",
 *  nor may "JRefactory" appear in their name, without prior written
 *  permission of Chris Seguin.
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 *  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 *  ====================================================================
 *
 *  This software consists of voluntary contributions made by many
 *  individuals on behalf of JRefactory.  For more information on
 *  JRefactory, please see
 *  <http://www.sourceforge.org/projects/jrefactory>.
 */
package org.acm.seguin.ide.common;

import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.border.EmptyBorder;
import org.acm.seguin.ide.common.PackageSelectorArea;
import org.acm.seguin.io.Saveable;
import org.acm.seguin.io.AllFileFilter;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.SummaryTraversal;
import net.sourceforge.jrefactory.action.LoadPackageAction;
import net.sourceforge.jrefactory.uml.UMLPackage;
import net.sourceforge.jrefactory.uml.loader.Reloader;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;
import org.acm.seguin.ide.command.CommandLineMenu;
import org.acm.seguin.ide.command.ExitMenuSelection;
import org.acm.seguin.summary.load.SwingLoadStatus;

/**
 *  Creates a panel for the selection of packages to view.
 *
 * @author     Mike Atkinson
 * @created    June 26, 2003
 */
public class PackageSelectorPanel extends PackageSelectorArea
       implements ActionListener, Saveable, Reloader, Runnable {

    protected JPanel buttons;
    protected JPanel panel;
    protected JFrame frame;
    protected SwingLoadStatus loadStatus;


    public static PackageSelectorPanel mainPanel;
    /**
     *  The root directory
     */
    protected String rootDir = null;

    protected HashMap viewList;

   /**
    *  Constructor for the PackageSelectorPanel object
    *
    * @param  root  The root directory
    */
   protected PackageSelectorPanel(String root) {
      super();
      setRootDirectory(root);  //  Setup the instance variables
      
      buttons = createButtons(this);
      loadStatus = new SwingLoadStatus();
      loadStatus.setVisible(false);
      panel = createMainPanel();
      MouseListener mouseListener = new MouseAdapter() {
         public void mousePressed(MouseEvent mevt) {
            maybeShowMenu(mevt);
         }
      
         public void mouseReleased(MouseEvent mevt) {
            maybeShowMenu(mevt);
         }
      
         public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
               //int index = packages.locationToIndex(e.getPoint());
               Point pt = e.getPoint();
               TreePath selection = packages.getPathForLocation(pt.x, pt.y);
               //System.out.println("Double clicked on Item " + index);
               if (selection != null) {
                  ANode node = (ANode)selection.getLastPathComponent();
                  Object userObject = node.getUserObject();
                  if (userObject instanceof NodeData) {
                     PackageSummary summary = ((NodeData)userObject).getPackageSummary();
                     if (summary != null) {
                        showSummary(summary);
                     }
                  } else {
                     System.out.println("userObject="+userObject);
                  }
               }
            }
         }
      };
      packages.addMouseListener(mouseListener);
      
      loadStatus.setLabel("waiting");
   }

   private void maybeShowMenu(MouseEvent mevt) {
      if (mevt.isPopupTrigger()) {
         Point pt = mevt.getPoint();
         TreePath selection = packages.getPathForLocation(pt.x, pt.y);
         if (selection != null) {
            ANode node = (ANode)selection.getLastPathComponent();
            JPopupMenu menu = new JPopupMenu("Module");
            JMenuItem item = new JMenuItem(new RemoveAction(node));
            item.setEnabled(node instanceof ModuleNode);
            menu.add(item);
            //menu.setLocation(pt.x, pt.y + 20);
            //menu.setVisible(true);
            menu.show(mevt.getComponent(), mevt.getX(), mevt.getY());
         }
      }
   }

   private class RemoveAction extends AbstractAction {
      ANode node;
      public RemoveAction(ANode node) {
         super("remove");
         this.node = node;
      }
      public void actionPerformed(ActionEvent evt) {
         System.out.println("evt="+evt);
         model.removeNodeFromParent(node);
         saveKnownModules();
      }
   }

   
    /**
     *  Set the root directory
     *
     *@param  root  the new root directory
     */
    public String getRootDirectory() {
       return rootDir;
    }

    /**
     *  Set the root directory
     *
     *@param  root  the new root directory
     */
    protected void setRootDirectory(String root) {
        if (root == null) {
            rootDir = System.getProperty("user.dir");
        } else {
            rootDir = root;
        }
    }

    
    
    /**
    *  Handle the button press events
    *
    * @param  evt  the event
    */
   public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();
        if (command.equals("Show")) {
            TreePath[] selection = packages.getSelectionPaths();
            for (int ndx = 0; ndx < selection.length; ndx++) {
                NodeData next = (NodeData) ((ANode)selection[ndx].getLastPathComponent()).getUserObject();
                showSummary(next.getPackageSummary());
            }
        }
        else if (command.equals("Hide")) {
            TreePath[] selection = packages.getSelectionPaths();
            for (int ndx = 0; ndx < selection.length; ndx++) {
                NodeData next = (NodeData) ((ANode)selection[ndx].getLastPathComponent()).getUserObject();
                hideSummary(next.getPackageSummary());
            }
        }
        else if (command.equals("Reload")) {
            ReloaderSingleton.reload();
        }
        else if (command.equals("Load")) {
            JFileChooser chooser = new JFileChooser();
      
            //  Add other file filters - All
            AllFileFilter allFilter = new AllFileFilter();
            chooser.addChoosableFileFilter(allFilter);
      
            //  Set it so that files and directories can be selected
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      
            //  Set the directory to the current directory
            chooser.setCurrentDirectory(new java.io.File(rootDir));
      
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
               rootDir = chooser.getSelectedFile().getAbsolutePath();
               ReloaderSingleton.register(this);
               ReloaderSingleton.reload();
            } else {
               // do nothing
            }
        }
   }


    /**
     *  Get the main panel.
     *
     *@return            The main panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     *  Get the main panel (as a window), setting it visible.
     *
     *@param  directory  Description of Parameter
     *@return            The MainPanel value
     */
    public static PackageSelectorPanel openMainFrame(String directory) {
        getMainFrame(directory);
        if (mainPanel!=null) { mainPanel.setVisible(true); }
        return mainPanel;
    }


   /**
    *  Get the package from the central store
    *
    * @param  summary  The package summary that we are looking for
    * @return          The UML package
    */
   protected UMLFrame getPackage(PackageSummary summary) {
      return (UMLFrame)viewList.get(summary);
   }



   /**
    *  Creates the frame
    *
    * @return    Description of the Returned Value
    */
   protected JFrame createFrame() {
        JFrame frame = new JFrame("Package Selector");

        frame.getContentPane().add(panel);

        CommandLineMenu clm = new CommandLineMenu();
        frame.setJMenuBar(clm.getMenuBar(this));
        frame.addWindowListener(new ExitMenuSelection());
        frame.setSize(350, 350);
        return frame;
   }


   /**
    *  Creates the content panel
    *
    * @return    Description of the Returned Value
    */
   protected JPanel createMainPanel() {
      JPanel panel = new JPanel();
      panel.setLayout(new BorderLayout());

      JScrollPane scrollPane = getScrollPane();
      //scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
      panel.add(scrollPane, BorderLayout.CENTER);
      panel.add(buttons, BorderLayout.NORTH);
      panel.add(loadStatus.getPanel(), BorderLayout.SOUTH);
      return panel;
   }


   /**
    *  Create the panel holding the buttons
    *
    * @param  listener  Description of Parameter
    * @return           Description of the Returned Value
    */
   protected JPanel createButtons(ActionListener listener) {
      JPanel panel = new JPanel();

      panel.add(new JButton(new LoadPackageAction(null)));

      JButton showButton = new JButton("Show");
      panel.add(showButton);
      showButton.addActionListener(listener);

      JButton hideButton = new JButton("Hide");
      panel.add(hideButton);
      hideButton.addActionListener(listener);

      JButton reloadButton = new JButton("Reload");
      panel.add(reloadButton);
      reloadButton.addActionListener(listener);
      
      return panel;
   }

    /**
     *  Creates a new view
     *
     *@param  packageSummary  The packages summary
     */
    private void createNewView(PackageSummary packageSummary) {
        UMLFrame frame = new UMLFrame(packageSummary);
        addPackage(packageSummary, frame);
    }

    /**
     *  Add package to central store
     *
     *@param  summary  the summary we are adding
     *@param  view     the associated view
     */
    protected void addPackage(PackageSummary summary, UMLFrame view) {
        viewList.put(summary, view);
    }



    /**
     *  Hide the summary
     *
     *@param  packageSummary  the summary to hide
     */
    private void hideSummary(PackageSummary packageSummary) {
        UMLFrame view = getPackage(packageSummary);
        view.setVisible(false);
    }


    /**
     *  Loads the packages into the packages structure and refreshes the UML diagrams
     */
    public void loadPackages() {
        new Thread(this).start();
    }
    
    public void run() {
        try {
            loadSummaries();
            super.loadPackages();
    
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    //  Reloads the screens
                    UMLPackage view = null;
                    PackageSummary packageSummary = null;
            
                    if (viewList == null) {
                        viewList = new HashMap();
                        return;
                    }
    
                    Iterator iter = viewList.keySet().iterator();
                    while (iter.hasNext()) {
                        packageSummary = (PackageSummary) iter.next();
                        view = getPackage(packageSummary).getUmlPackage();
                        view.reload();
                    }
                }
            });
        } catch (Exception e) {
        }
    }


    /**
     *  Load the summaries
     */
    public void loadSummaries() {
        //  Load the summaries
        SummaryLoaderThread loader = new SummaryLoaderThread(rootDir, loadStatus);
        try {
           loader.start();
           while (loader.waitForLoading()==0) {
              Thread.sleep(10);
           }
        } catch (InterruptedException e) {
        }
    }


    /**
     *  Main program for testing purposes
     *
     *@param  args  The command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Syntax:  java net.sourceforge.jrefactory.uml.PackageSelectorPanel <dir>");
            return;
        }

        PackageSelectorPanel panel = PackageSelectorPanel.openMainFrame(args[0]);
        ReloaderSingleton.register(panel);
    }


    /**
     *  Reloads the package information
     */
    public void reload() {
        loadPackages();
    }


    /**
     *  Saves the diagrams
     *
     *@exception  IOException  Description of Exception
     */
    public void save() throws IOException {
        Iterator iter = viewList.keySet().iterator();
        while (iter.hasNext()) {
            PackageSummary packageSummary = (PackageSummary) iter.next();
            UMLPackage view = getPackage(packageSummary).getUmlPackage();
            view.save();
        }
    }


    /**
     *  Shows the summary
     *
     *@param  packageSummary  the summary to show
     */
    private void showSummary(PackageSummary packageSummary) {
        UMLFrame view = getPackage(packageSummary);
        if ((view == null) && (packageSummary.getFileSummaries() != null)) {
            createNewView(packageSummary);
        }
        else if (packageSummary.getFileSummaries() == null) {
            //  Nothing to view
        }
        else {
            view.getUmlPackage().reload();
            view.setVisible(true);
        }
    }


   /**
    *  Get the main panel
    *
    * @param  directory  Description of Parameter
    * @return            The MainPanel value
    */
   public static PackageSelectorPanel getMainPanel(String directory) {
      if (mainPanel == null) {
         mainPanel = new PackageSelectorPanel(directory);
      } else if (directory!=null) {
         mainPanel.setRootDirectory(directory);
      }
      return mainPanel;
   }
   /**
    *  Get the main panel
    *
    * @param  directory  Description of Parameter
    * @return            The MainPanel value
    */
   public static JFrame getMainFrame(String directory) {
      getMainPanel(directory);
      if (mainPanel.frame == null) {
         mainPanel.frame = mainPanel.createFrame();
      }
      return mainPanel.frame;
   }
}

