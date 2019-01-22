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
package net.sourceforge.jrefactory.action;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import com.sun.image.codec.jpeg.*;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.io.ExtensionFileFilter;



/**
 *  Description of the Class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: SaveImageAction.java,v 1.2 2003/12/04 15:54:21 mikeatkinson Exp $
 */
public class SaveImageAction extends GenericAction {
   private JPanel diagram;
   private String imageType = "JPG";
   private String filename;
   private static File directory = null;


   /**
    *  Constructor for the SaveImageAction object
    *
    * @param  packageDiagram  Description of Parameter
    * @param  imageType       Description of Parameter
    * @since                  2.9.12
    */
   public SaveImageAction(JPanel packageDiagram, String imageType) {
      super();
      diagram = packageDiagram;
      this.imageType = imageType;
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      if ("JPG".equals(imageType)) {
         return (diagram instanceof UMLPackage);
      } else {
         try {
            new javax.media.jai.JAI();
            return (diagram instanceof UMLPackage);
         } catch (Throwable e) {
            return false;
         }
      }
   }


   /**
    *  Performs the action
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void actionPerformed(ActionEvent evt) {
      filename = getFilename();
      if (filename == null) {
         return;
      }
      run();
   }


   /**
    *  Main processing method for the Save object
    *
    * @since    2.9.12
    */
   public void run() {
      if ("JPG".equals(imageType)) {
         try {
            Dimension dim = diagram.getPreferredSize();
            BufferedImage doubleBuffer = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
            Graphics g = doubleBuffer.getGraphics();
            g.setColor(Color.gray);
            g.fillRect(0, 0, dim.width, dim.height);
            diagram.print(g);
            OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
            JPEGEncodeParam param = JPEGCodec.getDefaultJPEGEncodeParam(doubleBuffer);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out, param);
            encoder.encode(doubleBuffer);
            out.flush();
            out.close();
         } catch (IOException ioe) {
            ioe.printStackTrace();
         }
      } else {
         Dimension dim = diagram.getPreferredSize();
         BufferedImage doubleBuffer = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
         Graphics g = doubleBuffer.getGraphics();
         g.setColor(Color.gray);
         g.fillRect(0, 0, dim.width, dim.height);
         diagram.print(g);
         try {
           javax.media.jai.JAI.create("filestore", doubleBuffer, filename, imageType, null);
         } catch (Throwable e) {
         }
      }
   }


   /**
    *  Gets the Filename to save the file as
    *
    * @return    The Filename value
    * @since     2.9.12
    */
   private String getFilename() {
      JFileChooser chooser = new JFileChooser();

      //  Create the java file filter
      ExtensionFileFilter filter = new ExtensionFileFilter();
      filter.addExtension("." + imageType.toLowerCase());
      filter.setDescription(imageType + " Image Files (." + imageType.toLowerCase() + ")");
      chooser.setFileFilter(filter);

      //  Set it so that files only can be selected and it is a save box
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setDialogType(JFileChooser.SAVE_DIALOG);

      //  Set the directory to the current directory
      if (directory == null) {
         directory = new File(System.getProperty("user.dir"));
      }
      chooser.setCurrentDirectory(directory);

      //  Get the user's selection
      int returnVal = chooser.showSaveDialog(null);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
         File selectedFile = chooser.getSelectedFile();
         directory = selectedFile.getParentFile();
         return selectedFile.getAbsolutePath();
      }

      return null;
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, imageType);
      putValue(SHORT_DESCRIPTION, "Save Image");
      putValue(LONG_DESCRIPTION, "Save Image of the UML Package");
   }

}

