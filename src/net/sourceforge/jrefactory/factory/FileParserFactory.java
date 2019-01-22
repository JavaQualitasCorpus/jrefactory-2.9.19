/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package net.sourceforge.jrefactory.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;



/**
 *  Generates new parsers for a java file
 *
 * @author     Chris Seguin
 * @author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @since      v 1.0
 * @version    $Id: FileParserFactory.java,v 1.3 2003/12/02 23:36:12 mikeatkinson Exp $
 * @created    June 6, 1999
 */
public class FileParserFactory extends ParserFactory {
   private File input;


   /**
    *  Constructor for a file ParserFactory
    *
    * @param  file  the file that we want to create a parser for
    * @since        v 1.0
    */
   public FileParserFactory(File file) {
      input = file;
   }


   /**
    *  Return the input stream
    *
    * @return    the input stream
    * @since     v 1.0
    */
   protected Reader getReader() {
      try {
         return new FileReader(input);
      } catch (FileNotFoundException fnfe) {
         System.err.println("Unable to find the file specified by " + getKey());
         return null;
      } catch (IOException ioe) {
         System.err.println("Unable to create the file " + getKey());
         return null;
      }
   }


   /**
    *  A method to return some key identifying the file that is being parsed
    *
    * @return    the identifier
    * @since     v 1.0
    */
   protected String getKey() {
      return input.getAbsolutePath();
   }
}

