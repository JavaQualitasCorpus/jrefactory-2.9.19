/*
    ====================================================================
    The JRefactory License, Version 1.0
    Copyright (c) 2001 JRefactory.  All rights reserved.
    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in
    the documentation and/or other materials provided with the
    distribution.
    3. The end-user documentation included with the redistribution,
    if any, must include the following acknowledgment:
    "This product includes software developed by the
    JRefactory (http://www.sourceforge.org/projects/jrefactory)."
    Alternately, this acknowledgment may appear in the software itself,
    if and wherever such third-party acknowledgments normally appear.
    4. The names "JRefactory" must not be used to endorse or promote
    products derived from this software without prior written
    permission. For written permission, please contact seguin@acm.org.
    5. Products derived from this software may not be called "JRefactory",
    nor may "JRefactory" appear in their name, without prior written
    permission of Chris Seguin.
    THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
    WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
    ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
    SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
    LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
    USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
    ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
    OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
    OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
    SUCH DAMAGE.
    ====================================================================
    This software consists of voluntary contributions made by many
    individuals on behalf of JRefactory.  For more information on
    JRefactory, please see
    <http://www.sourceforge.org/projects/jrefactory>.
  */
package org.acm.seguin.ide.common;

import java.awt.Frame;
import javax.swing.Icon;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.IOException;

import org.acm.seguin.ide.common.options.PropertiesFile;
import net.sourceforge.jrefactory.ast.Node;

/**
 *  Description of the Interface
 *
 *@author    Mike Atkinson
 */
public interface IDEInterface {
   /**
    *  Debugging message urgency. Should be used for messages only useful when debugging a problem.
    *
    *@since    JRefactory 2.8.02
    */
   public final static int DEBUG = 1;


   /**
    *  Message urgency. Should be used for messages which give more detail than notices.
    *
    *@since    JRefactory 2.8.02
    */
   public final static int MESSAGE = 3;

   /**
    *  Notice urgency. Should be used for messages that directly affect the user.
    *
    *@since    JRefactory 2.8.02
    */
   public final static int NOTICE = 5;

   /**
    *  Warning urgency. Should be used for messages that warrant attention.
    *
    *@since    JRefactory 2.8.02
    */
   public final static int WARNING = 7;

   /**
    *  Error urgency. Should be used for messages that signal a failure.
    *
    *@since    JRefactory 2.8.02
    */
   public final static int ERROR = 9;


   /**
    *  Signals that this is to do with  coding standards checking.
    *
    *@since    JRefactory 2.9.17
    */
   public final static int CODING_STANDARDS = 1;

   /**
    *  Signals that this is to do with  cut and paste detection.
    *
    *@since    JRefactory 2.9.17
    */
   public final static int CUT_AND_PASTE_DETECTOR = 2;



   /**
    *  Gets the IDEProperty attribute of the IDEInterface object
    *
    *@param  prop  Description of Parameter
    *@return       The IDEProperty value
    */
   String getIDEProperty(String prop);


   /**
    *  Gets the IDEProperty attribute of the IDEInterface object
    *
    *@param  prop   Description of Parameter
    *@param  deflt  Description of Parameter
    *@return        The IDEProperty value
    */
   String getIDEProperty(String prop, String deflt);


   /**
    *  Gets the IDEProjects attribute of the IDEInterface object
    *
    *@param  parent  Description of Parameter
    *@return         The IDEProjects value
    */
   String[] getIDEProjects(Frame parent);


   /**
    *  Description of the Method
    *
    *@param  parent  Description of Parameter
    */
   void showWaitCursor(Frame parent);


   /**
    *  Description of the Method
    *
    *@param  parent  Description of Parameter
    */
   void hideWaitCursor(Frame parent);


   /**
    *  Description of the Method
    *
    *@param  urgency  Description of Parameter
    *@param  source   Description of Parameter
    *@param  message  Description of Parameter
    */
   void log(int urgency, Object source, Object message);


   /**
    *  Gets the Properties attribute of the IDEInterface object
    *
    *@param  type     Description of Parameter
    *@param  project  Description of Parameter
    *@return          The Properties value
    */
   PropertiesFile getProperties(String type, String project);


   /**
    *  Description of the Method
    *
    *@param  view      Description of Parameter
    *@param  fileName  The new Buffer value
    */
   void setBuffer(Frame view, Object fileName);


   /**
    *  Description of the Method
    *
    *@param  view   Description of Parameter
    *@param  start  The new Selection value
    *@param  end    The new Selection value
    */
   void setSelection(Frame view, Object buffer, int start, int end);


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@exception  IOException  Description of Exception
    */
   void cpdBuffer(Frame view, Object buffer) throws IOException;


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@exception  IOException  Description of Exception
    */
   void cpdAllOpenBuffers(Frame view) throws IOException;


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@param  recursive        Description of Parameter
    *@exception  IOException  Description of Exception
    */
   void cpdDir(Frame view, boolean recursive) throws IOException;


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@param  fileName         Description of Parameter
    *@return                  Description of the Returned Value
    *@exception  IOException  Description of Exception
    */
   Object openFile(Frame view, String fileName) throws IOException;



   /**
    *  Description of the Method
    *
    *@param  buffer  Description of Parameter
    *@param  begin   Description of Parameter
    *@return         The BeginLine value
    */
   int getLineStartOffset(Object buffer, int begin);


   /**
    *  Description of the Method
    *
    *@param  buffer  Description of Parameter
    *@param  end     Description of Parameter
    *@return         The LineEndOffset value
    */
   int getLineEndOffset(Object buffer, int end);



   /**
    *  Description of the Method
    *
    *@param  view   Description of Parameter
    *@param  start  Description of Parameter
    */
   void moveCaretPosition(Frame view, Object buffer, int start);


   /**
    *  Description of the Method
    *
    *@param  runnable  Description of Parameter
    */
   void runInAWTThread(Runnable runnable);


   /**
    *  Description of the Method
    *
    *@param  view  Description of Parameter
    *@return       The Text value
    */
   String getText(Frame view, Object buffer);


   /**
    *  Description of the Method
    *
    *@param  view  Description of Parameter
    */
   void checkBuffer(Frame view, Object buffer);


   /**
    *  Description of the Method
    *
    *@param  view  Description of Parameter
    */
   void checkAllOpenBuffers(Frame view);


   /**
    *  Description of the Method
    *
    *@param  view       Description of Parameter
    *@param  recursive  Description of Parameter
    */
   void checkDirectory(Frame view, boolean recursive);


   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  Description of Parameter
    */
   void goToBuffer(Frame view, Object buffer);


   /**
    *  Description of the Method
    *
    *@param  buffer  Description of Parameter
    *@return         The LineCount value
    */
   int getLineCount(Object buffer);



   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  Description of Parameter
    *@return         The ProjectName value
    */
   String getProjectName(Frame view, Object buffer);


   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  Description of Parameter
    */
   void saveProperties();

   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @return         The File path for this buffer
    */
   String getFilePathForBuffer(Object buffer);


   /**
    *  Load an icon from the IDE
    *
    * @param  name  The name of the icon.
    * @return       An icon (or null if the icon cannot be found).
    */
   Icon loadIcon(String name);


   /**
    *  Returns the frame that contains the editor. If this is not available or
    *  you want dialog boxes to be centered on the screen return null from this
    *  operation.
    *
    *@return    the frame
    */
   Frame getEditorFrame();


   /**
    * Get the current (atcive) buffer.
    *
    * @param  view  The frame containing the IDE.
    * @return       The active buffer or null if no active buffer.
    */
   Object getCurrentBuffer(Frame view);


   /**
    * Get the line number of the cursor within the current buffer.
    *
    * @param  view        The frame containing the IDE.
    * @param  buffer      Description of Parameter
    * @param  lineNumber  The line number of the cursor in the current buffer.
    */
   int getLineNumber(Frame view, Object buffer);


   /**
    * Get the line number of the cursor within the current buffer.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    * @return       The line number of the cursor in the current buffer, or -1 if no current buffer.
    */
   void setLineNumber(Frame view, Object buffer, int lineNumber);


   /**
    *  Does the buffer contain Java source code.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    *@return <code>true</code> if the buffer contains Java source code, <code>false</code> otherwise.
    */
   boolean bufferContainsJavaSource(Frame view, Object buffer);


   /**
    *  Sets the string in the IDE
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    *@param  value  The new file contained in a string
    */
   void setText(Frame view, Object buffer, String value);


   /**
    *  Gets the file that is being edited
    *
    *@return    The File value
    */
   File getFile(Frame view, Object buffer);


   /**
    * Indicates that a buffer has been parsed and that an Abstract Syntax Tree is available.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  The buffer (containing Java Source) that has been parsed.
    * @param  compilationUnit  The root node of the AST.
    */
   void bufferParsed(Frame view, Object buffer, Node compilationUnit);


   /**
    * Indicates that a buffer has been parsed and that a navigator tree of the source is available.
    *
    * @param  view    The frame containing the IDE.
    * @param  buffer  The buffer (containing Java Source) that has been parsed.
    * @param  node    The root node of the tree.
    */
   void bufferNavigatorTree(Frame view, Object buffer, TreeNode node);

   /**
    * Adds an annotation to an ide buffer.
    *
    * @param  view         The frame containing the IDE.
    * @param  buffer       The buffer (containing Java Source) that has been parsed.
    * @param  type         either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
    * @param  lineNo       The line number of the annotation.
    * @param  description  The annotation
    */
   void addAnnotation(Frame view, Object buffer, int type, int lineNo, String description);
   
   /**
    * Clears all annotation for an ide buffer.
    *
    * @param  view         The frame containing the IDE.
    * @param  buffer       The buffer (containing Java Source) that has been parsed.
    * @param  type         either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
    */
   void clearAnnotations(Frame view, Object buffer, int type);
}

