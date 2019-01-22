package net.sourceforge.jrefactory.factory;

import java.io.Reader;



/**
 *  Generates new parsers for a java file
 *
 * @author     Chris Seguin
 * @author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @since      v 1.0
 * @version    $Id: InputStreamParserFactory.java,v 1.2 2003/12/02 23:36:12 mikeatkinson Exp $
 * @created    June 6, 1999
 */
public class InputStreamParserFactory extends ParserFactory {
   private Reader reader;
   private String key;


   /**
    *  Constructor for a file ParserFactory
    *
    * @param  reader       Description of Parameter
    * @param  initKey      Description of Parameter
    * @since               v 1.0
    */
   public InputStreamParserFactory(Reader reader, String initKey) {
      this.reader = reader;
      key = initKey;
   }


   /**
    *  Return the input stream
    *
    * @return    the input stream
    * @since     v 1.0
    */
   protected Reader getReader() {
      return reader;
   }


   /**
    *  A method to return some key identifying the file that is being parsed
    *
    * @return    the identifier
    * @since     v 1.0
    */
   protected String getKey() {
      return key;
   }
}

