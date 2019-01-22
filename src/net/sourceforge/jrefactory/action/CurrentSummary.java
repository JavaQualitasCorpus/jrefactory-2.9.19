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

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.ide.common.MultipleDirClassDiagramReloader;
import org.acm.seguin.summary.FieldSummary;
import org.acm.seguin.summary.FileSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.Summary;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Determines what the current summary is based on the information from the IDE.
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: CurrentSummary.java,v 1.1 2003/12/02 23:34:19 mikeatkinson Exp $
 */
public abstract class CurrentSummary extends Object implements DocumentListener {
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   public int lineNo = -1;
   /**
    *  Has this file changed since the last time this was invoked
    *
    * @since    2.9.12
    */
   protected boolean upToDate;
   private FileSummary fileSummary;
   private Summary summary;

   private static CurrentSummary singleton = null;


   /**
    *  Constructor for the CurrentSummary object
    *
    * @since    2.9.12
    */
   protected CurrentSummary() {
      summary = null;
      fileSummary = null;
      upToDate = false;
   }


   /**
    *  Gets the CurrentSummary attribute of the CurrentSummary object
    *
    * @return    The CurrentSummary value
    * @since     2.9.12
    */
   public Summary getCurrentSummary() {
      //System.out.println("CurrentSummary.getCurrentSummary()");
      //System.out.flush();
      if ((summary == null) || upToDate || !isSameFile() || !isInSameSummary()) {
         //System.out.println("  - lockAccess()");
         //System.out.flush();
         lockAccess();
      }

      //System.out.println("Summary is:  " + summary + " from " + getLineNumber());
      return summary;
   }


   /**
    *  Method that receives notification when the editor changes
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void changedUpdate(DocumentEvent evt) {
      upToDate = false;
   }


   /**
    *  Method that receives notification when the editor changes
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void insertUpdate(DocumentEvent evt) {
      upToDate = false;
   }


   /**
    *  Method that receives notification when the editor changes
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void removeUpdate(DocumentEvent evt) {
      upToDate = false;
   }


   /**
    *  Description of the Method
    *
    * @since    2.9.12
    */
   public void reset() {
      //System.out.println("CurrentSummary.reset()");
      upToDate = false;
   }


   /**
    *  Reloads all the metadata before attempting to perform a refactoring.
    *
    * @since    2.9.12
    */
   public void updateMetaData() {
      //System.out.println("CurrentSummary.updateMetaData()");
      //System.out.flush();
      MultipleDirClassDiagramReloader reloader = getMetadataReloader();

      reloader.setNecessary(true);
      reloader.reload();
      //System.out.println("CurrentSummary.updateMetaData() - end");
   }


   /**
    *  Gets the ActiveFile attribute of the CurrentSummary object
    *
    * @return    The ActiveFile value
    * @since     2.9.12
    */
   protected File getActiveFile() {
      java.awt.Frame view = IDEPlugin.getEditorFrame();
      Object buffer = IDEPlugin.getCurrentBuffer(view);
      //System.out.println("CurrentSummary.getActiveFile() -> " + IDEPlugin.getFile(view, buffer));
      return IDEPlugin.getFile(view, buffer);
   }


   /**
    *  Returns the initial line number
    *
    * @return    The LineNumber value
    * @since     2.9.12
    */
   protected int getLineNumber() {
      if (lineNo < 0) {
         java.awt.Frame view = IDEPlugin.getEditorFrame();
         Object buffer = IDEPlugin.getCurrentBuffer(view);
         int ln = IDEPlugin.getLineNumber(view, buffer);
         //System.out.println("CurrentSummary.getLineNumber() -> " + ln);
         return ln;
      }
      //System.out.println("CurrentSummary.getLineNumber() -> " + lineNo);
      return lineNo;
   }


   /**
    *  Gets the reloader
    *
    * @return    The MetadataReloader value
    * @since     2.9.12
    */
   protected abstract MultipleDirClassDiagramReloader getMetadataReloader();


   /**
    *  Register with the current document
    *
    * @since    2.9.12
    */
   protected abstract void registerWithCurrentDocument();


   /**
    *  Gets the InType attribute of the CurrentSummary object
    *
    * @param  fileSummary  Description of Parameter
    * @param  lineNumber   Description of Parameter
    * @return              The InType value
    * @since               2.9.12
    */
   private Summary getInType(FileSummary fileSummary, int lineNumber) {
      //System.out.println("CurrentSummary.getInType()");
      Iterator iter = fileSummary.getTypes();
      if (iter == null) {
         //System.out.println("  -> null");
         return null;
      }

      //System.out.println("Searching for:  " + lineNumber);
      while (iter.hasNext()) {
         TypeSummary next = (TypeSummary)iter.next();
         //System.out.println("Type:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
         if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
            Summary s = findSummaryInType(next, lineNumber);
            //System.out.println("  -> " + s);
            return s;
         }
      }

      //System.out.println("  -> null");
      return null;
   }


   /**
    *  Gets the InField attribute of the CurrentSummary object
    *
    * @param  typeSummary  Description of Parameter
    * @param  lineNumber   Description of Parameter
    * @return              The InField value
    * @since               2.9.12
    */
   private Summary isInField(TypeSummary typeSummary, int lineNumber) {
      //System.out.println("CurrentSummary.isInField()");
      Iterator iter = typeSummary.getFields();
      if (iter == null) {
         //System.out.println("  -> null");
         return null;
      }

      while (iter.hasNext()) {
         FieldSummary next = (FieldSummary)iter.next();
         //System.out.println("Field:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
         if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
            return next;
         }
      }

      //System.out.println("  -> null");
      return null;
   }


   /**
    *  Gets the InMethod attribute of the CurrentSummary object
    *
    * @param  typeSummary  Description of Parameter
    * @param  lineNumber   Description of Parameter
    * @return              The InMethod value
    * @since               2.9.12
    */
   private Summary isInMethod(TypeSummary typeSummary, int lineNumber) {
      //System.out.println("CurrentSummary.isInMethod()");
      Iterator iter = typeSummary.getMethods();
      if (iter == null) {
         //System.out.println("  -> null");
         return null;
      }

      while (iter.hasNext()) {
         MethodSummary next = (MethodSummary)iter.next();

         //System.out.println("Method:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
         if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
            return next;
         }
      }

      //System.out.println("  -> null");
      return null;
   }


   /**
    *  Gets the InNestedClass attribute of the CurrentSummary object
    *
    * @param  typeSummary  Description of Parameter
    * @param  lineNumber   Description of Parameter
    * @return              The InNestedClass value
    * @since               2.9.12
    */
   private Summary isInNestedClass(TypeSummary typeSummary, int lineNumber) {
      //System.out.println("CurrentSummary.isInNestedClass()");
      Iterator iter = typeSummary.getTypes();
      if (iter == null) {
         //System.out.println("  -> null");
         return null;
      }

      while (iter.hasNext()) {
         TypeSummary next = (TypeSummary)iter.next();
         //System.out.println("Type:  " + next.toString() + " " + next.getStartLine() + ", " + next.getEndLine());
         if ((next.getStartLine() <= lineNumber) && (next.getEndLine() >= lineNumber)) {
            return findSummaryInType(next, lineNumber);
         }
      }

      //System.out.println("  -> null");
      return null;
   }


   /**
    *  Gets the InSameSummary attribute of the CurrentSummary object
    *
    * @return    The InSameSummary value
    * @since     2.9.12
    */
   private boolean isInSameSummary() {
      int lineNumber = getLineNumber();
      return ((summary.getStartLine() <= lineNumber) && (summary.getEndLine() >= lineNumber));
   }


   /**
    *  Gets the SameNode attribute of the CurrentSummary object
    *
    * @return    The SameNode value
    * @since     2.9.12
    */
   private boolean isSameFile() {
      if (fileSummary == null) {
         return false;
      }

      boolean result = (fileSummary.getFile() == getActiveFile());
      //System.out.println("Node is the same:  " + result);
      return result;
   }


   /**
    *  Description of the Method
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   private Summary find() {
      //System.out.println("CurrentSummary.find()");
      try {
         registerWithCurrentDocument();

         int lineNumber = getLineNumber();
         if (lineNumber == -1) {
            //System.out.println("Unable to get the line number:  " + lineNumber);
            return null;
         }

         if (!upToDate || (fileSummary == null)) {
            fileSummary = reloadNode();
         }

         if (fileSummary == null) {
            //System.out.println("Unable to load a file summary");
            return null;
         }

         Summary summary = getInType(fileSummary, lineNumber);
         if (summary != null) {
            //System.out.println("Found a summary:  " + summary);
            return summary;
         }

         //System.out.println("Just able to return the file summary");
         return fileSummary;
      } catch (IOException ioe) {
         return null;
      }
   }


   /**
    *  Description of the Method
    *
    * @param  next        Description of Parameter
    * @param  lineNumber  Description of Parameter
    * @return             Description of the Returned Value
    * @since              2.9.12
    */
   private Summary findSummaryInType(TypeSummary next, int lineNumber) {
      //System.out.println("CurrentSummary.findSummaryInType()");
      Summary result = isInMethod(next, lineNumber);
      if (result != null) {
         //System.out.println("Found a method:  " + summary);
         return result;
      }

      result = isInField(next, lineNumber);
      if (result != null) {
         //System.out.println("Found a field:  " + summary);
         return result;
      }

      result = isInNestedClass(next, lineNumber);
      if (result != null) {
         //System.out.println("Found a class:  " + summary);
         return result;
      }

      return next;
   }


   /**
    *  Only does one find at a time
    *
    * @since    2.9.12
    */
   private synchronized void lockAccess() {
      if ((summary == null) || !upToDate || !isSameFile() || !isInSameSummary()) {
         //System.out.println("About to find the summary");
         summary = find();
         upToDate = true;
         //System.out.println("Done");
      }
      //System.out.println("Finished lock access");
   }


   /**
    *  Description of the Method
    *
    * @return                  Description of the Returned Value
    * @exception  IOException  Description of Exception
    * @since                   2.9.12
    */
   private FileSummary reloadNode() throws IOException {
      //System.out.println("CurrentSummary.reloadNode()");
      java.awt.Frame view = IDEPlugin.getEditorFrame();
      Object buffer = IDEPlugin.getCurrentBuffer(view);
      if (IDEPlugin.bufferContainsJavaSource(view, buffer)) {
         String contents = IDEPlugin.getText(view, buffer);
         StringReader reader = new StringReader(contents);

         //System.out.println("CurrentSummary.reloadNode() -> FileSummary");
         return FileSummary.reloadFromBuffer(IDEPlugin.getFile(view, buffer), reader);
      }

      //System.out.println("CurrentSummary.reloadNode() -> null");
      return null;
   }


   /**
    *  Method to get the singleton object
    *
    * @return    the current summary
    * @since     2.9.12
    */
   public static CurrentSummary get() {
      //System.out.println("CurrentSummary.get()");
      return singleton;
   }


   /**
    *  Register the current summary
    *
    * @param  value  Description of Parameter
    * @since         2.9.12
    */
   public static void register(CurrentSummary value) {
      singleton = value;
   }
}

