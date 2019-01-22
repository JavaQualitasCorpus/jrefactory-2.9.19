/*
 *Author:  Chris Seguin
 *
 *This software has been developed under the copyleft
 *rules of the GNU General Public License.  Please
 *consult the GNU General Public License for more
 *details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import org.acm.seguin.awt.ExceptionPrinter;
import org.acm.seguin.awt.Question;
import org.acm.seguin.io.InplaceOutputStream;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import org.acm.seguin.util.FileSettings;
import net.sourceforge.jrefactory.factory.FileParserFactory;
import net.sourceforge.jrefactory.factory.ParserFactory;

import java.util.*;

/**
 *  Holds a refactoring. Default version just pretty prints the file.
 *
 * @author   Chris Seguin
 * @author   <a href="JRefactory@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @created  July 1, 1999
 * @version  $Id: PrettyPrintFile.java,v 1.12 2003/11/18 18:46:14 mikeatkinson Exp $
 */
public class PrettyPrintFile {
    private ParserFactory factory;
    private boolean ask;

    /**  Refactors java code. */
    public PrettyPrintFile() {
        ask = true;
    }

    /**
     *  Sets whether we should ask the user
     *
     * @param way  the way to set the variable
     */
    public void setAsk(boolean way) {
        ask = way;
    }

    /**
     *  Set the parser factory
     *
     * @param factory  Description of Parameter
     */
    public void setParserFactory(ParserFactory factory) {
        this.factory = factory;
    }

    /**
     *  Returns true if this refactoring is applicable
     *
     * @param inputFile  the input file
     * @return          true if this refactoring is applicable
     */
    public boolean isApplicable(File inputFile) {
        if (!inputFile.canWrite()) {
            return false;
        }

        boolean result = true;

        if (ask) {
            result = Question.isYes("Pretty Printer",
                    "Do you want to pretty print the file\n" + inputFile.getPath() + "?");
        }

        //  Create a factory if necessary
        if (result) {
            setParserFactory(new FileParserFactory(inputFile));
        }

        return result;
    }

    /**
     *  Return the factory that gets the abstract syntax trees
     *
     * @return  the parser factory
     */
    public ParserFactory getParserFactory() {
        return factory;
    }

    /**
     *  Apply the refactoring
     *
     * @param inputFile  the input file
     */
    public void apply(File inputFile) {
       SimpleNode root = factory.getAbstractSyntaxTree(true, ExceptionPrinter.getInstance());

       if (root!=null) {
          apply(inputFile, root);
          postApply(inputFile, root);
       }
    }

    /**
     *  Apply the refactoring
     *
     * @param inputFile  the input file
     * @param root       Description of Parameter
     */
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
            }
            else {
                printer.visit(root, data);
            }

            data.close();   //  Flush the output stream and close it
        }
    }

    /**
     *  Create the output stream
     *
     * @param file  the name of the file
     * @return     the output stream
     */
    protected Writer getWriter(File file) {
        Writer out = null;

        try {
            out = new OutputStreamWriter(new InplaceOutputStream(file));
        }
        catch (IOException ioe) {
            out = new OutputStreamWriter(System.out);
        }

        return out;   //  Return the output stream
    }

    /**
     *  Return the appropriate print data
     *
     * @param input  Description of Parameter
     * @return      the print data
     */
    protected PrintData getPrintData(File input) {
        JavadocTags.get().reload();

        //  Create the new stream
        return new PrintData(getWriter(input));
    }

    /**
     *  Apply the refactoring
     *
     * @param inputFile  the input file
     * @param root       Description of Parameter
     */
    protected void postApply(File inputFile, SimpleNode root) {
    }
}

