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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import net.sourceforge.jrefactory.uml.HasSummary;
import net.sourceforge.jrefactory.uml.PopupMenuListener;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Description of the Class
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: ShowSourceAction.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public class ShowSourceAction extends RefactoringAction {
   /**
    *  Constructor for the ShowSourceAction object
    *
    * @param  init  Description of Parameter
    * @since        2.9.12
    */
   public ShowSourceAction(SelectedFileSet init) {
      super(init);
      initNames();
   }


   /**
    *  Constructor for the ShowSourceAction object
    *
    * @param  initType     The class (or interface) to show the Java source for.
    * @param  initMenu     if not null then the menu that contained the action.
    * @param  initItem     if not null then the menuitem that contained the action.
    * @since            2.9.12
    */
   public ShowSourceAction(HasSummary initType, JPopupMenu initMenu, JMenuItem initItem) {
      this(null);
      setPopupMenuListener(new ShowSourceListener(initType.getSourceSummary(), initMenu, initItem));
      initNames();
   }


   /**
    *  Is this action enabled?
    *
    * @return    If <code>true</code> then enable this action's menu item or button.
    * @since     2.9.12
    */
   public boolean isEnabled() {
      return isSingleJavaFile();
   }


   /**
    *  Description of the Method
    *
    * @param  typeSummaryArray  Description of Parameter
    * @param  evt               Description of Parameter
    * @since                    2.9.12
    */
   protected void activateListener(TypeSummary[] typeSummaryArray, ActionEvent evt) {
      ShowSourceListener accl = new ShowSourceListener(typeSummaryArray[0], null, null);
      accl.actionPerformed(evt);
   }


   /**
    *  Initialise the Action values (NAME, SHORT_DESCRIPTION, LONG_DESCRIPTION).
    *
    * @since    2.9.12
    */
   private void initNames() {
      putValue(NAME, "Show source");
      putValue(SHORT_DESCRIPTION, "Show source");
      putValue(LONG_DESCRIPTION, "Shows the Java source in a browser window");
   }


   /**
    *  Generic adapter for browsing source code
    *
    * @author     Chris Seguin
    * @since      2.9.12
    * @created    October 18, 2001
    */
   class ShowSourceListener extends PopupMenuListener {
      private Summary summary;


      /**
       * @param  summary   Description of Parameter
       * @param  initMenu  Description of Parameter
       * @param  initItem  Description of Parameter
       * @since            2.9.12
       */
      public ShowSourceListener(Summary summary, JPopupMenu initMenu, JMenuItem initItem) {
         super(initMenu, initItem);
         this.summary = summary;
      }


      /**
       *  Responds to this item being selected
       *
       * @param  evt  Description of Parameter
       * @since       2.9.12
       */
      public void actionPerformed(ActionEvent evt) {
         File file = findFile();
         int line = getLine();
         if (file != null) {
            try {
               System.out.println("gotoSource(" + file.getCanonicalPath() + ", " + line + ")");
               java.awt.Frame view = IDEPlugin.getEditorFrame();
               Object buffer = IDEPlugin.openFile(view, file.getCanonicalPath());
               int start = IDEPlugin.getLineStartOffset(buffer, line - 1);
               System.out.println("  start=" + start);
               IDEPlugin.moveCaretPosition(view, buffer, start);
            } catch (java.io.IOException e) {
               e.printStackTrace();
            }
         }
      }


      /**
       *  Get the line number of the start of the current activeComponent.
       *
       * @return    The line number.
       * @since     2.9.12
       */
      protected int getLine() {
         return summary.getDeclarationLine();
      }


      /**
       *  Look up the chain of Summary parents to find the File the activeComponent is sourced in.
       *
       * @return    The File.
       * @since     2.9.12
       */
      protected File findFile() {
         while (!(summary instanceof FileSummary)) {
            summary = summary.getParent();
         }
         FileSummary fileSummary = (FileSummary)summary;
         return fileSummary.getFile();
      }
   }
}

