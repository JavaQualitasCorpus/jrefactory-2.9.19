/*
 *Author:  Chris Seguin
 *
 *This software has been developed under the copyleft
 *rules of the GNU General Public License.  Please
 *consult the GNU General Public License for more
 *details about use and distribution of this software.
 */
package org.acm.seguin.pretty;


import net.sourceforge.jrefactory.parser.Token;


/**
 *  Parses a javadoc comment
 *
 * @author   Chris Seguin
 * @author   Mike Atkinson
 * @created  June 10, 2002
 */
public class JavadocTokenizer extends Tokenizer {
   private Token lookahead = null;
   
    /**
     *  Constructor for the JavadocTokenizer object
     *
     * @paraminit  Description of Parameter
     */
    public JavadocTokenizer(String init) {
        super(init);
    }

    /**
     *  Description of the Method
     *
     * @return  Description of the Returned Value
     */
    public Token next() {
       if (lookahead != null) {
          Token t = lookahead;
          lookahead = null;
          return t;
       }
       Token token = super.next();
       if (token.image.equals("//")) {
          StringBuffer buf = new StringBuffer(token.image);
          while (true) {
             Token nextToken = super.next();
             if (nextToken.kind == NEWLINE) {
                lookahead = nextToken;
                Token t = new Token();
                t.kind = WORD;
                t.image = buf.toString();
                return t;
             } else {
                buf.append(nextToken.image);
             }
          }
       }
       return token;
    }
}


