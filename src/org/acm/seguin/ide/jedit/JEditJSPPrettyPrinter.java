package org.acm.seguin.ide.jedit;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import errorlist.DefaultErrorSource;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.factory.BufferParserFactory;
import net.sourceforge.jrefactory.parser.ParseException;
import net.sourceforge.jrefactory.parser.Token;

import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.ide.common.options.PropertiesFile;
import org.acm.seguin.pretty.PrettyPrintFile;
import org.acm.seguin.project.Project;
import org.gjt.sp.jedit.*;
import org.gjt.sp.jedit.io.VFSManager;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.gjt.sp.util.Log;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import org.acm.seguin.util.FileSettings;
import net.sourceforge.jrefactory.factory.FileParserFactory;
import net.sourceforge.jrefactory.factory.ParserFactory;
import net.sourceforge.jrefactory.parser.JavaParser;


// note: JEditPrettyPrinter is _not_ derived from PrettyPrintFromIDE
// (like it should be), because then we cannot handle the parse
// exceptions ourselves.
/**
 *  Description of the Class
 *
 * @author     Mike Atkinson (Mike)
 * @since      1.0
 * @created    14 July 2003
 */
public class JEditJSPPrettyPrinter extends JEditPrettyPrinter {

   /**
    *  Constructor for the JEditPrettyPrinter object
    *
    * @param  jsPlugin  Description of Parameter
    * @param  view      Description of Parameter
    * @param  buffer    Description of Parameter
    * @since            v 1.0
    */
   public JEditJSPPrettyPrinter(JavaStylePlugin jsPlugin, View view, Buffer buffer) {
      super(jsPlugin,view,buffer);
   }


   /**
    *  Main processing method for the JEditPrettyPrinter object
    *
    * @since    v 1.0
    */
   public void run() {
      Log.log(Log.DEBUG, this, "formatting the buffer...");

      int caretPos = 0;
      JEditTextArea textarea = null;

      try {
         if (view != null) {
            view.showWaitCursor();
            textarea = view.getTextArea();
            caretPos = textarea.getCaretPosition();
         }

         setProjectData(view, buffer);
         setSettings();

         // set settings from jEdit
         // get text string
         String before = buffer.getText(0, buffer.getLength());
         // remember and remove all markers:
         Vector markers = (Vector)buffer.getMarkers().clone();

         StringBuffer scriptCode = new StringBuffer();
         String rest = before;
         int index1 = 0;
         int index2 = 0;
         index1 = rest.indexOf("<%",index2);
         if (index1>=0) {
            index2 = rest.indexOf("%>",index1);
            String x = rest.substring(0, index1+3);
            String y = rest.substring(index1+3, index2);
            char c = rest.charAt(index1+2);
            rest = rest.substring(index2);
            if (c=='!') {
               scriptCode.append(y);
            } else if (c=='=') {
               scriptCode.append(y);
            } else {
               scriptCode.append(y);
            }
         }
         System.out.println("scriptCode="+scriptCode);
         setInputString(scriptCode.toString());
         apply(null);

         String contentsX = getOutputBuffer();
         System.out.println("contentsX="+contentsX);


         StringBuffer after = new StringBuffer();
         rest = before;
         index1 = 0;
         index2 = 0;
         index1 = rest.indexOf("<%",index2);
         if (index1>=0) {
            index2 = rest.indexOf("%>",index1);
            String x = rest.substring(0, index1+3);
            after.append(x);
            String y = rest.substring(index1+3, index2);
            char c = rest.charAt(index1+2);
            rest = rest.substring(index2);
            System.out.println("\""+x+"\"");
            System.out.println("\""+y+"\"");
            if (c=='!') {
               after.append(y);
            } else if (c=='=') {
               after.append(y);
            } else {
               after.append(y);
            }
         }
         System.out.println("\""+rest+"\"");
         after.append(rest);
         
         
         String contents = after.toString();

         // get the new contents back
         if (exception != null) {
            exception.printStackTrace();
            // the JRefactory reformatting caused an exception, so handle it.
            if (exception instanceof ParseException) {
               Token currentToken = ((ParseException)exception).currentToken;
               JavaStylePlugin.getErrorSource().clear();
               JavaStylePlugin.getErrorSource().addError(DefaultErrorSource.ERROR,
                     buffer.getPath(),
                     Math.max(0, currentToken.next.beginLine - 1),
                     Math.max(0, currentToken.next.beginColumn - 1),
                     Math.max(0, currentToken.next.endColumn),
                     exception.getMessage());
            } else {
               exception.printStackTrace();
               // no idea what caused this exception
               Log.log(Log.ERROR, this, exception);
            }
            exception = null;

         } else if (contents == null || contents.length() == 0) {
            // we did not get an exception but JRefactory still did not produce valid contents
            GUIUtilities.error(view, "javastyle.error.other", null);
         } else if (!contents.equals(before)) {
            // The JRefactory reformatting caused a change in the text so perform the edit.
            try {
               buffer.beginCompoundEdit();
               if (markers.size() > 0) {
                  buffer.removeAllMarkers();
               }

               buffer.remove(0, buffer.getLength());
               buffer.insert(0, contents);

               // restore markers:
               Enumeration enumx = markers.elements();

               while (enumx.hasMoreElements()) {
                  Marker marker = (Marker)enumx.nextElement();

                  buffer.addMarker(marker.getShortcut(), marker.getPosition());
               }
               // restore caret position
               if (textarea != null) {
                  int bufLen = textarea.getBufferLength();

                  textarea.setCaretPosition(caretPos > bufLen ? bufLen : caretPos);
                  textarea.scrollToCaret(true);
               }
            } catch (Exception ex) {
               ex.printStackTrace();
               Log.log(Log.ERROR, this, ex);
            } finally {
               buffer.endCompoundEdit();
            }
         }

      } catch (Throwable ex) {
         ex.printStackTrace();
         Log.log(Log.ERROR, this, ex);
         GUIUtilities.error(view, "javastyle.error.parse", new Object[]{ex.toString()});
      } finally {
         if (view != null) {
            view.hideWaitCursor();
         }
      }
   }


   private String prettyPrintJSPDeclaration(String before) {
      //setInputString(before);
      //apply(null);
      //// JRefactory reformat (may cause exception to be set).
      //return getOutputBuffer();
      System.out.println("before="+before);
      return before;
   }


   private String prettyPrintJSPExpression(String before) {
      //setInputString(before);
      //apply(null);
      //// JRefactory reformat (may cause exception to be set).
      //return getOutputBuffer();
      System.out.println("before="+before);
      return before;
   }


   private String prettyPrintJSP(String before) {
      //setInputString(before);
      //apply(null);
      //// JRefactory reformat (may cause exception to be set).
      //return getOutputBuffer();
      System.out.println("before="+before);
      return before;
   }

   /**
    *  Sets the InputString attribute of the JEditPrettyPrinter object
    *
    * @param  input  The new InputString value
    * @since         v 1.0
    */
   protected void setInputString(String input) {
      if (input == null) {
         return;
      }
      setParserFactory(new ClassBodyParserFactory(input));
   }


    /**
     *  Apply the refactoring
     *
     * @param inputFile  the input file
     * @param root       Description of Parameter
     */
/*
     public void apply(File inputFile, SimpleNode root) {
        if (root != null) {
            FileSettings pretty = FileSettings.getRefactoryPrettySettings();

            pretty.setReloadNow(true);

            //  Create the visitor
            PrettyPrintVisitor printer = new PrettyPrintVisitor();

            //  Create the appropriate print data
            PrintData data = getPrintData(inputFile);

            if (root instanceof ASTCompilationUnit) {
                printer.visit((ASTCompilationUnit) root, data);
            } else {
                printer.visit(root, data);
            }

            data.close();   //  Flush the output stream and close it
        }
    }
*/


   private static class ClassBodyParserFactory extends BufferParserFactory {
      public ClassBodyParserFactory(String buffer) {
         super(buffer);
      }

      protected SimpleNode parse(JavaParser parser) throws ParseException {
         return parser.JSPBody();
      }
   }
}

