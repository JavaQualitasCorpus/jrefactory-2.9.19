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
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class IDEPlugin {
   private static IDEInterface ideInterface = null;


   /**
    *  Sets the Plugin attribute of the IDEPlugin class
    *
    *@param  ideIf  The new Plugin value
    */
   public static void setPlugin(IDEInterface ideIf) {
      ideInterface = ideIf;
   }


   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  The new Buffer value
    */
   public static void setBuffer(Frame view, Object buffer) {
      if (ideInterface != null) {
         ideInterface.setBuffer(view, buffer);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view   Description of Parameter
    *@param  start  The new Selection value
    *@param  end    The new Selection value
    */
   public static void setSelection(Frame view, Object buffer, int start, int end) {
      if (ideInterface != null) {
         ideInterface.setSelection(view, buffer, start, end);
      }
   }


   /**
    *  Gets the Property attribute of the IDEPlugin class
    *
    *@param  prop  Description of Parameter
    *@return       The Property value
    */
   public static String getProperty(String prop) {
      //System.out.println("ideInterface="+ideInterface);
      if (ideInterface == null) {
         return System.getProperty(prop);
      }
      return ideInterface.getIDEProperty(prop);
   }


   /**
    *  Gets the Property attribute of the IDEPlugin class
    *
    *@param  prop   Description of Parameter
    *@param  deflt  Description of Parameter
    *@return        The Property value
    */
   public static String getProperty(String prop, String deflt) {
      if (ideInterface == null) {
         return System.getProperty(prop, deflt);
      }
      return ideInterface.getIDEProperty(prop, deflt);
   }


   /**
    *  Gets the Projects attribute of the IDEPlugin class
    *
    *@param  parent  Description of Parameter
    *@return         The Projects value
    */
   public static String[] getProjects(Frame parent) {
      if (ideInterface == null) {
         return new String[]{"default"};
      }
      return ideInterface.getIDEProjects(parent);
   }


   /**
    *  Gets the Properties attribute of the IDEPlugin class
    *
    *@param  type     Description of Parameter
    *@param  project  Description of Parameter
    *@return          The Properties value
    */
   public static PropertiesFile getProperties(String type, String project) {
      return ideInterface.getProperties(type, project);
   }



   /**
    *  Description of the Method
    *
    *@param  buffer  Description of Parameter
    *@param  begin   Description of Parameter
    *@return         The BeginLine value
    */
   public static int getLineStartOffset(Object buffer, int begin) {
      if (ideInterface != null) {
         return ideInterface.getLineStartOffset(buffer, begin);
      }
      return 0;
   }


   /**
    *  Description of the Method
    *
    *@param  buffer  Description of Parameter
    *@param  end     Description of Parameter
    *@return         The LineEndOffset value
    */
   public static int getLineEndOffset(Object buffer, int end) {
      if (ideInterface != null) {
         return ideInterface.getLineEndOffset(buffer, end);
      }
      return 0;
   }


   /**
    *  Description of the Method
    *
    *@param  view  Description of Parameter
    *@return       The Text value
    */
   public static String getText(Frame view, Object buffer) {
      if (ideInterface != null) {
         return ideInterface.getText(view, buffer);
      }
      return "";
   }


   /**
    *  Description of the Method
    *
    *@param  buffer  Description of Parameter
    *@return         The LineCount value
    */
   public static int getLineCount(Object buffer) {
      if (ideInterface != null) {
         return ideInterface.getLineCount(buffer);
      }
      return 0;
   }


   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  Description of Parameter
    *@return         The ProjectName value
    */
   public static String getProjectName(Frame view, Object buffer) {
      if (ideInterface != null) {
         return ideInterface.getProjectName(view, buffer);
      }
      return null;
   }


   /**
    *  Description of the Method
    *
    *@param  parent  Description of Parameter
    */
   public static void showWaitCursor(Frame parent) {
      if (ideInterface != null) {
         ideInterface.showWaitCursor(parent);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  parent  Description of Parameter
    */
   public static void hideWaitCursor(Frame parent) {
      if (ideInterface != null) {
         ideInterface.hideWaitCursor(parent);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  urgency  Description of Parameter
    *@param  source   Description of Parameter
    *@param  message  Description of Parameter
    */
   public static void log(int urgency, Object source, Object message) {
      if (ideInterface != null) {
         ideInterface.log(urgency, source, message);
      } else {
         System.err.println(source + ": " + message);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@exception  IOException  Description of Exception
    */
   public static void cpdBuffer(Frame view, Object buffer) throws IOException {
      if (ideInterface != null) {
         ideInterface.cpdBuffer(view, buffer);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@exception  IOException  Description of Exception
    */
   public static void cpdAllOpenBuffers(Frame view) throws IOException {
      if (ideInterface != null) {
         ideInterface.cpdAllOpenBuffers(view);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@param  recursive        Description of Parameter
    *@exception  IOException  Description of Exception
    */
   public static void cpdDir(Frame view, boolean recursive) throws IOException {
      if (ideInterface != null) {
         ideInterface.cpdDir(view, recursive);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view             Description of Parameter
    *@param  fileName         Description of Parameter
    *@return                  Description of the Returned Value
    *@exception  IOException  Description of Exception
    */
   public static Object openFile(Frame view, String fileName) throws IOException {
      if (ideInterface != null) {
         return ideInterface.openFile(view, fileName);
      }
      return null;
   }


   /**
    *  Description of the Method
    *
    *@param  view   Description of Parameter
    *@param  start  Description of Parameter
    */
   public static void moveCaretPosition(Frame view, Object buffer, int start) {
      if (ideInterface != null) {
         ideInterface.moveCaretPosition(view, buffer, start);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  runnable  Description of Parameter
    */
   public static void runInAWTThread(Runnable runnable) {
      if (ideInterface != null) {
         ideInterface.runInAWTThread(runnable);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view  Description of Parameter
    */
   public static void checkBuffer(Frame view, Object buffer) {
      if (ideInterface != null) {
         ideInterface.checkBuffer(view, buffer);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view  Description of Parameter
    */
   public static void checkAllOpenBuffers(Frame view) {
      if (ideInterface != null) {
         ideInterface.checkAllOpenBuffers(view);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view       Description of Parameter
    *@param  recursive  Description of Parameter
    */
   public static void checkDirectory(Frame view, boolean recursive) {
      if (ideInterface != null) {
         ideInterface.checkDirectory(view, recursive);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  Description of Parameter
    */
   public static void goToBuffer(Frame view, Object buffer) {
      if (ideInterface != null) {
         ideInterface.goToBuffer(view, buffer);
      }
   }


   /**
    *  Description of the Method
    *
    *@param  view    Description of Parameter
    *@param  buffer  Description of Parameter
    */
   public static void saveProperties() {
      if (ideInterface != null) {
         ideInterface.saveProperties();
      }
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @return         The File path for this buffer
    */
   public static String getFilePathForBuffer(Object buffer) {
      if (ideInterface != null) {
         return ideInterface.getFilePathForBuffer(buffer);
      }
      return "";
   }


   /**
    *  Load an icon from the IDE
    *
    * @param  name  The name of the icon.
    * @return       An icon (or null if the icon cannot be found).
    */
   public static Icon loadIcon(String name) {
      if (ideInterface != null) {
         return ideInterface.loadIcon(name);
      }
      return null;
   }


   /**
    *  Returns the frame that contains the editor. If this is not available or
    *  you want dialog boxes to be centered on the screen return null from this
    *  operation.
    *
    *@return    the frame
    */
   public static Frame getEditorFrame() {
      if (ideInterface != null) {
         return ideInterface.getEditorFrame();
      }
      return null;
   }


   /**
    * Get the current (atcive) buffer.
    *
    * @param  view  The frame containing the IDE.
    * @return       The active buffer or null if no active buffer.
    */
    public static Object getCurrentBuffer(Frame view) {
      if (ideInterface != null) {
         return ideInterface.getCurrentBuffer(view);
      }
      return null;
    }


   /**
    * Get the line number of the cursor within the current buffer.
    *
    * @param  view  The frame containing the IDE.
    * @return       The ine number of the cursor in the current buffer, or -1 if no current buffer.
    */
    public static int getLineNumber(Frame view, Object buffer) {
      if (ideInterface != null) {
         return ideInterface.getLineNumber(view, buffer);
      }
      return -1;
    }


   /**
    * Get the line number of the cursor within the current buffer.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    * @return       The line number of the cursor in the current buffer, or -1 if no current buffer.
    */
   public static void setLineNumber(Frame view, Object buffer, int lineNumber) {
      if (lineNumber < 1) {
         throw new IllegalArgumentException("lineNumber must be 1 or greater: " + lineNumber);
      }
      if (ideInterface != null) {
         ideInterface.setLineNumber(view, buffer, lineNumber);
      }
   }


   /**
    *  Does the buffer contain Java source code.
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    *@return <code>true</code> if the buffer contains Java source code, <code>false</code> otherwise.
    */
   public static boolean bufferContainsJavaSource(Frame view, Object buffer) {
      if (ideInterface != null) {
         return ideInterface.bufferContainsJavaSource(view, buffer);
      }
      return false;
    }


   /**
    *  Sets the string in the IDE
    *
    * @param  view  The frame containing the IDE.
    * @param  buffer  Description of Parameter
    *@param  value  The new file contained in a string
    */
   public static void setText(Frame view, Object buffer, String value) {
      if (ideInterface != null) {
         ideInterface.setText(view, buffer, value);
      }
    }


   /**
    *  Gets the file that is being edited
    *
    *@return    The File value
    */
   public static File getFile(Frame view, Object buffer) {
      if (ideInterface != null) {
         return ideInterface.getFile(view, buffer);
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
   public static void bufferParsed(Frame view, Object buffer, Node compilationUnit) {
      if (ideInterface != null) {
         ideInterface.bufferParsed(view, buffer, compilationUnit);
      }
    }


   /**
    * Indicates that a buffer has been parsed and that a navigator tree of the source is available.
    *
    * @param  view    The frame containing the IDE.
    * @param  buffer  The buffer (containing Java Source) that has been parsed.
    * @param  node    The root node of the tree.
    */
   public static void bufferNavigatorTree(Frame view, Object buffer, TreeNode node) {
      if (ideInterface != null) {
         ideInterface.bufferNavigatorTree(view, buffer, node);
      }
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
   public static void addAnnotation(Frame view, Object buffer, int type, int lineNo, String description) {
      if (ideInterface != null) {
         ideInterface.addAnnotation(view, buffer, type, lineNo, description);
      }
   }

   /**
    * Clears all annotation for an ide buffer.
    *
    * @param  view         The frame containing the IDE.
    * @param  buffer       The buffer (containing Java Source) that has been parsed.
    * @param  type         either CODING_STANDARDS or CUT_AND_PASTE_DETECTION
    */
   public static void clearAnnotations(Frame view, Object buffer, int type) {
       if (ideInterface != null) {
         ideInterface.clearAnnotations(view, buffer, type);
      }      
   }
}

