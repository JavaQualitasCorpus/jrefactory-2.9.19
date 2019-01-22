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
package net.sourceforge.jrefactory.action;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import org.acm.seguin.ide.common.PackageSelectorPanel;
import org.acm.seguin.io.AllFileFilter;
import net.sourceforge.jrefactory.uml.loader.ReloaderSingleton;




/**
 *  Description of the Class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: LoadPackageAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class LoadPackageAction extends GenericAction {
   private File file;


   /**
    *  Constructor for the AddChildClassAction object
    *
    * @param  file  Description of Parameter
    * @since        2.9.12
    */
   public LoadPackageAction(String file) {
      super();
      this.file = (file==null) ? null : new File(file);
      initNames();
   }


   /**
    *  Responds to this item being selected
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void actionPerformed(ActionEvent evt) {
      if (file == null) {
         JFileChooser chooser = new JFileChooser();

         //  Add other file filters - All
         AllFileFilter allFilter = new AllFileFilter();
         chooser.addChoosableFileFilter(allFilter);

         //  Set it so that files and directories can be selected
         chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

         String rootDir = PackageSelectorPanel.getMainPanel(null).getRootDirectory();
         //  Set the directory to the current directory
         chooser.setCurrentDirectory(new File(rootDir));

         int returnVal = chooser.showOpenDialog(null);
         if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
         }
      }
      if (file != null && file.isDirectory()) {
         ReloaderSingleton.register(PackageSelectorPanel.getMainPanel(file.getAbsolutePath()));
         ReloaderSingleton.reload();
      }
      file = null;
   }


   /**
    *  Description of the Method
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Load");
      putValue(SHORT_DESCRIPTION, "Load package");
      putValue(LONG_DESCRIPTION, "Loads a package into the JRefactory browser window");
   }

}

