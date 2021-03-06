
/*
 *Author:  Chris Seguin
 *
 *This software has been developed under the copyleft
 *rules of the GNU General Public License.  Please
 *consult the GNU General Public License for more
 *details about use and distribution of this software.
 */
//import java.io.File;
//import javax.swing.JFileChooser;
//import javax.swing.filechooser.FileFilter;
//import org.acm.seguin.awt.ExceptionPrinter;
//import org.acm.seguin.awt.GUIExceptionPrinter;
//import org.acm.seguin.awt.TextExceptionPrinter;
//import org.acm.seguin.io.AllFileFilter;
//import org.acm.seguin.io.DirectoryTreeTraversal;
//import org.acm.seguin.io.ExtensionFileFilter;
//import org.acm.seguin.io.FileCopy;
//import org.acm.seguin.pretty.PrettyPrintFile;
//import org.acm.seguin.tools.RefactoryInstaller;
//import org.acm.seguin.util.FileSettings;
//import org.acm.seguin.util.MissingSettingsException;


/**
 *  Traverses a directory structure and performs all refactorings on the files.
 *
 * @author   Chris Seguin
 * @created  May 12, 1999
 */
public class PrettyPrinter extends org.acm.seguin.tools.builder.PrettyPrinter {

    /**
     *  Creates a refactory
     *
     * @paraminit   Description of Parameter
     * @paramquiet  Description of Parameter
     * @paraminit   Description of Parameter
     * @paramquiet  Description of Parameter
     */
    public PrettyPrinter(String init, boolean quiet) {
        super(init, quiet);
    }

    /**
     *  The main program
     *
     * @paramargs  The command line arguments
     * @paramargs  The command line arguments
     * @paramargs  Description of Parameter
     */
    public static void main(String[] args) {
        org.acm.seguin.tools.builder.PrettyPrinter.main(args);
    }
//    {
//        try {
//            int lastOption = -1;
//            boolean quiet = false;
//
//            for (int ndx = 0; ndx < args.length; ndx++) {
//                if (args[ndx].equals("-quiet") || args[ndx].equals("-u")) {
//                    quiet = true;
//                    lastOption = ndx;
//                    ExceptionPrinter.register(new TextExceptionPrinter());
//                }
//                else if (args[ndx].equals("-?") || args[ndx].equalsIgnoreCase("-h") || args[ndx].equalsIgnoreCase("-help")) {
//                    printHelpMessage();
//                    return;
//                }
//                else if (args[ndx].equals("-config")) {
//                    String dir = args[ndx + 1];
//                    ndx++;
//                    FileSettings.setSettingsRoot(dir);
//                }
//            }
//
//            //  Make sure everything is installed properly
//            (new RefactoryInstaller(false)).run();
//
//            if (lastOption + 1 >= args.length) {
//                // no more arguments left
//                if (quiet) {
//                    prettyPrinter(quiet);
//                }
//                else {
//                    prettyPrinter(System.getProperty("user.dir"), quiet);
//                }
//            }
//            else {
//                // process remaining arguments as file / dir names
//                for (int ndx = lastOption + 1; ndx < args.length; ++ndx) {
//                    prettyPrinter(args[ndx], quiet);
//                }
//            }
//        }
//        catch (Throwable thrown) {
//            thrown.printStackTrace(System.out);
//            System.exit(1);
//        }
//
//        //  Exit
//        System.exit(ExceptionPrinter.getExceptionsPrinted());
//    }



//    public static void prettyPrinter(String filename, boolean quiet)
//    {
//        (new PrettyPrinter(filename, quiet)).run();
//    }



//    public static void prettyPrinter(boolean quiet)
//    {
//        JFileChooser chooser = new JFileChooser();
//
//        //  Create the java file filter
//        ExtensionFileFilter filter = new ExtensionFileFilter();
//        filter.addExtension(".java");
//        filter.setDescription("Java Source Files (.java)");
//        chooser.setFileFilter(filter);
//
//        //  Add other file filters - All
//        FileFilter allFilter = new AllFileFilter();
//        chooser.addChoosableFileFilter(allFilter);
//
//        //  Set it so that files and directories can be selected
//        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//
//        //  Set the directory to the current directory
//        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
//
//        //  Get the user's selection
//        int returnVal = chooser.showOpenDialog(null);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            (new PrettyPrinter(chooser.getSelectedFile().getAbsolutePath(), quiet)).run();
//        }
//    }



//    private static void printHelpMessage()
//    {
//        System.out.println("Syntax:  java PrettyPrinter file   //  means refactor this file");
//        System.out.println("   OR    java PrettyPrinter [-quiet|-u] dir   //  means refactor this directory");
//        System.out.println("   OR    java PrettyPrinter [-quiet|-u]   //  means refactor the current directory");
//        System.out.println("  the -quiet or the -u flag tells the pretty printer not to prompt the user");
//    }
}

