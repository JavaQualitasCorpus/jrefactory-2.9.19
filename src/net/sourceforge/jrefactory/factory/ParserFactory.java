package net.sourceforge.jrefactory.factory;

import java.io.Reader;
import java.util.EmptyStackException;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.parser.JavaParser;
import net.sourceforge.jrefactory.parser.ParseException;


/**
 *  Generates new parsers for a java file
 *
 *@author     Chris Seguin
 *@author     <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 *@version    $Id: ParserFactory.java,v 1.3 2003/11/18 18:46:13 mikeatkinson Exp $ 
 *@created    June 6, 1999
 */
public abstract class ParserFactory {
	private SimpleNode root = null;

	private static JavaParser parser = null;


	/**
	 *  Return the AST
	 *
	 *@param  interactive         do we want to receive a response in the form of
	 *      a dialog box when a parse exception is encountered
	 *@return                     the simple node which represents the root
	 */
	public SimpleNode getAbstractSyntaxTree(boolean interactive, ExceptionPrinter printer) {
		//  Check to see if it is here yet
		if (root == null) {
			synchronized (ParserFactory.class) {
				//  Look it up
				JavaParser parser = getParser();
				if (parser == null) {
					return null;
				}

				//  Get the parse tree
				try {
					root = parse(parser);
				} catch (ParseException pe) {
					System.out.println("ParserFactory Version 0.1:  Encountered errors during parse:  " + getKey());
					printer.printException(pe, interactive);

					return null;
				} catch (EmptyStackException ese) {
					System.out.println("ParserFactory Version 0.1:  Encountered errors during parse:  " + getKey());
					printer.printException(ese, false);
					root = null;
				}
			}
		}

		return root;  //  Return the tree
	}


   protected SimpleNode parse(JavaParser parser) throws ParseException {
      return parser.CompilationUnit();
   }


	/**
	 *  Return the input stream
	 *
	 *@return    the input stream
	 */
	protected abstract Reader getReader();


	/**
	 *  Create the parser
	 *
	 *@return    the java parser
	 */
	protected JavaParser getParser() {
		Reader in = getReader();
		if (in == null) {
			return null;
		}

		//  Create the parser
		if (parser == null) {
			ParserFactory.parser = new JavaParser(in);
		}
		else {
			ParserFactory.parser.ReInit(in);
		}

		return ParserFactory.parser;
	}


	/**
	 *  A method to return some key identifying the file that is being parsed
	 *
	 *@return    the identifier
	 */
	protected abstract String getKey();
}
