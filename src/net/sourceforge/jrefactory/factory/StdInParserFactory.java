package net.sourceforge.jrefactory.factory;


import java.io.InputStreamReader;
import java.io.Reader;



/**
 *  Generates new parsers for standard input
 *
 * @author     Chris Seguin
 * @author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @since      v 1.0
 * @version    $Id: StdInParserFactory.java,v 1.2 2003/12/02 23:36:12 mikeatkinson Exp $
 */
public class StdInParserFactory extends ParserFactory {
   /**
    *  Constructor for a standard input ParserFactory
    *
    * @since    v 1.0
    */
   public StdInParserFactory() { }


   /**
    *  Return the input stream
    *
    * @return    the input stream
    * @since     v 1.0
    */
   protected Reader getReader() {
      return new InputStreamReader(System.in);
   }


   /**
    *  A method to return some key identifying the file that is being parsed
    *
    * @return    the identifier
    * @since     v 1.0
    */
   protected String getKey() {
      return "Standard Input";
   }
}

