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

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import net.sourceforge.jrefactory.parser.JavaParser;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.pretty.PrettyPrintFromIDE;
import org.acm.seguin.util.FileSettings;



/**
 *  Pretty printer action button
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: PrettyPrinterAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class PrettyPrinterAction extends GenericAction {
   /**
    *  Constructor for the PrettyPrinterAction object
    *
    * @since    2.9.11
    */
   public PrettyPrinterAction() {
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.11
    */
   public boolean isEnabled() {
      if (!enabled) {
         return false;
      }

      java.awt.Frame view = IDEPlugin.getEditorFrame();
      Object buffer = IDEPlugin.getCurrentBuffer(view);
      return IDEPlugin.bufferContainsJavaSource(view, buffer);
   }


   /**
    *  The pretty printer action
    *
    * @param  evt  the action that occurred
    * @since       2.9.11
    */
   public void actionPerformed(ActionEvent evt) {
      FileSettings bundle = FileSettings.getRefactoryPrettySettings();
      JavaParser.setTargetJDK(bundle.getString("jdk"));
      GenericPrettyPrinter jbpp = new GenericPrettyPrinter();
      jbpp.prettyPrintCurrentWindow();
      CurrentSummary.get().reset();
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION, ACCELERATOR).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Pretty Printer");
      putValue(SHORT_DESCRIPTION, "Pretty Printer");
      putValue(LONG_DESCRIPTION, "Reindents java source file\n" +
               "Adds intelligent javadoc comments");
      putValue(ACCELERATOR, KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK | Event.SHIFT_MASK));
   }


   /**
    *  Software that reformats the java source code
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    October 18, 2001
    */
   class GenericPrettyPrinter extends PrettyPrintFromIDE {

      /**
       *  Sets the line number
       *
       * @param  value  The new LineNumber value
       * @since         2.9.12
       */
      protected void setLineNumber(int value) {
         java.awt.Frame view = IDEPlugin.getEditorFrame();
         Object buffer = IDEPlugin.getCurrentBuffer(view);
         IDEPlugin.setLineNumber(view, buffer, value);
      }


      /**
       *  Sets the string in the IDE
       *
       * @param  value  The new file contained in a string
       * @since         2.9.12
       */
      protected void setStringInIDE(String value) {
         java.awt.Frame view = IDEPlugin.getEditorFrame();
         Object buffer = IDEPlugin.getCurrentBuffer(view);
         IDEPlugin.setText(view, buffer, value);
      }


      /**
       *  Returns the initial line number
       *
       * @return    The LineNumber value
       * @since     2.9.12
       */
      protected int getLineNumber() {
         java.awt.Frame view = IDEPlugin.getEditorFrame();
         Object buffer = IDEPlugin.getCurrentBuffer(view);
         return IDEPlugin.getLineNumber(view, buffer);
      }


      /**
       *  Gets the initial string from the IDE
       *
       * @return    The file in string format
       * @since     2.9.12
       */
      protected String getStringFromIDE() {
         java.awt.Frame view = IDEPlugin.getEditorFrame();
         Object buffer = IDEPlugin.getCurrentBuffer(view);
         return IDEPlugin.getText(view, buffer);
      }
   }
}

