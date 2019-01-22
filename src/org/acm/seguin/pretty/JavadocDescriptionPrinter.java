/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import net.sourceforge.jrefactory.parser.Token;
import org.acm.seguin.util.SettingNotFoundException;
import org.acm.seguin.util.Settings;

/**
 *  Prints a description from a java doc comment with HTML tags formatted.
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @created    July 23, 1999
 */
public class JavadocDescriptionPrinter {
   /*<Instance Variables>*/
   private PrintData printData;
   private StringBuffer buffer;
   private int indent;
   private int mode;
   private boolean newline;
   private int owedLines;
   private boolean withinID;
   // are we within a "$Id: JavadocDescriptionPrinter.java,v 1.8 2004/05/05 20:56:29 mikeatkinson Exp $" sequence (if so don't do line breaks)
   private boolean lastLineWasCComment = false;
   /*</Instance Variables>*/

   /*<Class Variables>*/
   private static int NORMAL = 0;
   private static int PARA = 1;
   private static int LIST = 2;
   private static int END_LIST = 3;
   private static int TABLE = 4;
   private static int END_TAG = 5;
   private static int LINE_BREAK = 6;
   private static int PREFORMATTED = 7;
   /*</Class Variables>*/


   /*<Constructors>*/
   /**
    *  Constructor for the JavadocDescriptionPrinter object
    *
    * @param  data         Description of Parameter
    * @param  description  Description of Parameter
    */
   public JavadocDescriptionPrinter(PrintData data, String description) {
      this(data, description, data.getJavadocIndent());
   }


   /**
    *  Constructor for the JavadocDescriptionPrinter object
    *
    * @param  data         Description of Parameter
    * @param  description  Description of Parameter
    * @param  initIndent   Description of Parameter
    */
   public JavadocDescriptionPrinter(PrintData data, String description, int initIndent) {
      printData = data;
      buffer = new StringBuffer(description);
      indent = initIndent;
      newline = true;
      withinID = false;
      mode = NORMAL;
   }
   /*</Constructors>*/


   /*<Methods>*/
   /**  This is the main program.  */
   public void run() {
      if (printData.isReformatComments()) {
         int MIN = printData.getJavadocWordWrapMinimum();
         int MAX = printData.getJavadocWordWrapMaximum();

         JavadocTokenizer tok = new JavadocTokenizer(buffer.toString());
         mode = NORMAL;
         boolean first = true;
         while (tok.hasNext()) {
            Token nextToken = tok.next();
            first = printToken(nextToken, MIN, MAX, first);
         }
      } else {
         maintainCurrentFormat();
      }
   }


   /**  Indents the line and inserts the required "*"  */
   protected void indent() {
      if (printData.isCurrentSingle()) {
         return;
      }

      newline = true;
      printData.indent();
      if (!printData.isStarsAlignedWithSlash()) {
         printData.space();
      }
      printData.appendComment("*", PrintData.JAVADOC_COMMENT);

      if (printData.isReformatComments() && (mode != PREFORMATTED)) {
         for (int ndx = 0; ndx < indent; ndx++) {
            printData.space();
         }
      }
   }


   /**
    *  Certain tags require that we insert a new line after them.
    *
    * @param  forToken  the tag that we are considering
    * @return           true if we just printed a space or a newline
    */
   protected boolean startMode(Token forToken) {
      String token = forToken.image.toUpperCase();
      if (startsWith(token, "<PRE") || startsWith(token, "<CODE")) {
         mode = PREFORMATTED;
      } else if (startsWith(token, "</PRE") || startsWith(token, "</CODE")) {
         mode = NORMAL;
      } else if (startsWith(token, "<P")) {
         mode = PARA;
      } else if (startsWith(token, "<BR")) {
         mode = LINE_BREAK;
      } else if (startsWith(token, "<UL")) {
         mode = LIST;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "<OL")) {
         mode = LIST;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "<DL")) {
         mode = LIST;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "</UL")) {
         mode = END_LIST;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "</OL")) {
         mode = END_LIST;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "</DL")) {
         mode = END_LIST;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "<LI")) {
         indent();
         mode = END_TAG;
         return true;
      } else if (startsWith(token, "<DD")) {
         indent();
         mode = END_TAG;
         return true;
      } else if (startsWith(token, "<DT")) {
         indent();
         mode = END_TAG;
         return true;
      } else if (startsWith(token, "<TABLE")) {
         mode = TABLE;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "<TR")) {
         mode = TABLE;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "<TD")) {
         mode = TABLE;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "<TH")) {
         mode = TABLE;
         indent();
         indent += 2;
         return true;
      } else if (startsWith(token, "</TABLE")) {
         mode = TABLE;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "</TR")) {
         mode = TABLE;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "</TD")) {
         mode = TABLE;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "</TH")) {
         mode = TABLE;
         indent -= 2;
         indent();
         return true;
      } else if (startsWith(token, "</") && !newline) {
         mode = END_TAG;
      }

      return false;
   }


   /**
    *  Certain tags require that we insert a space after them.
    *
    * @param  token  the tag that we are considering
    * @return        true if we just printed a space or a newline
    * @since         JRefactory 2.7.00
    */
   protected boolean spaceRequired(Token token) {
      return spaceRequired(token.image.toUpperCase());
   }


   /**
    *  Detects the end of the tag marker
    *
    * @param  forToken  the token
    * @return           <code>true</code> if a newline has been issued.
    */
   protected boolean endMode(Token forToken) {
      String token = forToken.image;
      if (mode == END_TAG) {
         mode = NORMAL;
         printData.space();
         return true;
      }
      if (mode == PARA) {
         mode = NORMAL;
         indent();
         indent();
         return true;
      }
      if (mode == LINE_BREAK) {
         mode = NORMAL;
         indent();
         return true;
      }
      if (mode == LIST) {
         mode = NORMAL;
      }
      if (mode == END_LIST) {
         mode = NORMAL;
         indent();
         return true;
      }
      if (mode == TABLE) {
         mode = NORMAL;
         indent();
         return true;
      }

      return false;
   }


   /**
    *  Description of the Method
    *
    * @param  nextToken  Description of Parameter
    * @param  MIN        Description of Parameter
    * @param  MAX        Description of Parameter
    * @param  isFirst    Description of Parameter
    * @return            Description of the Returned Value
    */
   private boolean printToken(Token nextToken, int MIN, int MAX, boolean isFirst) {
      if (nextToken.kind == JavadocTokenizer.WORD) {
         newline = false;
         if (nextToken.image.equals("$Id:")) {
            withinID = true;
         }
         int length = nextToken.image.length();
         if (!withinID &&
               (printData.getLineLength() > MIN) &&
               (printData.getLineLength() + length > MAX) &&
               (mode != PREFORMATTED)) {
            indent();
            newline = true;
         }
         if (nextToken.image.charAt(0) == '<') {
            newline = startMode(nextToken);
         } else {
            newline = false;
         }
         if (nextToken.image.equals("$")) {
            withinID = false;
         }

         if ((newline && (indent == 0) && nextToken.image.startsWith("/"))) {
            printData.space();
         }
         if ((!newline && (indent == 0) && nextToken.image.startsWith("//"))) {
            indent();
            newline = true;
            printData.space();
            lastLineWasCComment = true;
         } else if (lastLineWasCComment) {
            indent();
            newline = true;
            lastLineWasCComment = false;
         }

         printData.appendComment(nextToken.image, PrintData.JAVADOC_COMMENT);

         if (spaceRequired(nextToken)) {
            printData.space();
         }

         if (nextToken.image.charAt(nextToken.image.length() - 1) == '>') {
            newline = endMode(nextToken) || newline;
         }
         return newline;
      } else {
         if (mode != PREFORMATTED) {
            if (!isFirst) {
               printData.space();
               return true;
            }
         } else if (nextToken.kind == JavadocTokenizer.SPACE) {
            printData.appendComment(nextToken.image, PrintData.JAVADOC_COMMENT);
         } else {
            indent();
         }

         return isFirst;
      }
   }


   /**  Maintains the current format  */
   private void maintainCurrentFormat() {
      JavadocTokenizer tok = new JavadocTokenizer(buffer.toString());
      owedLines = 0;

      Token last = null;
      Token current = tok.next();

      while (current.kind != JavadocTokenizer.WORD) {
         last = current;
         if (!tok.hasNext()) {
            return;
         }
         current = tok.next();
      }

      if ((last != null) && (last.kind != JavadocTokenizer.NEWLINE)) {
         mcfOutputToken(last, printData);
      }
      mcfOutputToken(current, printData);

      while (tok.hasNext()) {
         Token nextToken = tok.next();
         mcfOutputToken(nextToken, printData);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  nextToken  Description of Parameter
    * @param  printData  Description of Parameter
    */
   private void mcfOutputToken(Token nextToken, PrintData printData) {
      if (nextToken.kind == JavadocTokenizer.NEWLINE) {
         owedLines++;
      } else {
         while (owedLines > 0) {
            indent();
            owedLines--;
         }

         printData.appendComment(nextToken.image, PrintData.JAVADOC_COMMENT);
      }
   }


   /**
    *  Certain tags require that we insert a space after them.
    *
    * @param  token  the tag that we are considering (should be upper case).
    * @return        true if we just printed a space or a newline
    * @since         JRefactory 2.9.17
    */
   public static boolean spaceRequired(String token) {
      if (startsWithThenSpace(token, "<PRE")) {
         return true;
      } else if (startsWithThenSpace(token, "<P")) {
         return true;
      } else if (startsWithThenSpace(token, "<BR")) {
         return true;
      } else if (startsWithThenSpace(token, "<UL")) {
         return true;
      } else if (startsWithThenSpace(token, "<OL")) {
         return true;
      } else if (startsWithThenSpace(token, "<DL")) {
         return true;
      } else if (startsWithThenSpace(token, "<LI")) {
         return true;
      } else if (startsWithThenSpace(token, "<DD")) {
         return true;
      } else if (startsWithThenSpace(token, "<DT")) {
         return true;
      } else if (startsWithThenSpace(token, "<TABLE")) {
         return true;
      } else if (startsWithThenSpace(token, "<TR")) {
         return true;
      } else if (startsWithThenSpace(token, "<TD")) {
         return true;
      } else if (startsWithThenSpace(token, "<TH")) {
         return true;
      } else if (startsWithThenSpace(token, "//")) {
         return true;
      }

      return false;
   }


   /**
    *  Checks to see if this tag is the same as what we want and ignores case troubles
    *
    * @param  have  the token that we have
    * @param  want  the token that we are interested in
    * @return       <code>true</code> if what we have is the same as what we want
    */
   private static boolean startsWith(String have, String want) {
      return have.startsWith(want);
   }


   /**
    *  Checks to see if this tag is the same as what we want and ignores case troubles
    *
    * @param  have  the token that we have
    * @param  want  the token that we are interested in
    * @return       true if what we have is the same as what we want
    * @since        JRefactory 2.7.00
    */
   private static boolean startsWithThenSpace(String have, String want) {
      return have.startsWith(want) && have.length() == want.length();
   }
}

