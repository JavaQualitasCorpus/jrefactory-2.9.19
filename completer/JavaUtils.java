package org.acm.seguin.completer;

/*
 *  Copyright (c) 2002, Beau Tateyama
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
//import anthelper.ui.ClassBrowserPanel;
//import anthelper.ui.ClassInfoDialog;
//import anthelper.ui.ClassSelectionDialog;
//import anthelper.utils.JEditLogger;
//import anthelper.utils.SrcInfoUtils;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import javax.swing.JOptionPane;
import org.gjt.sp.jedit.Macros;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.gui.DockableWindowManager;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.search.CurrentBufferSet;
import org.gjt.sp.jedit.search.SearchAndReplace;
import org.gjt.sp.jedit.search.SearchFileSet;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.gjt.sp.jedit.textarea.Selection;
import org.acm.seguin.ide.jedit.Navigator;

/**
 * Description of the Class
 *
 * @author    btateyama@yahoo.com
 * @created   September 12, 2002
 */
public class JavaUtils {
    final static Navigator.NavigatorLogger logger = Completer.getLogger(ClassPathSrcMgr.class);
    
    
    /**
     * View class info in a ClassInfoDialog
     *
     * @param argView  Description of the Parameter
     */
/*
     public static void viewClassInfo(View argView) {
        // try to select word first...if nothing found, use enclosing class name
        JEditTextArea textArea = argView.getTextArea();
        int iPos = textArea.getCaretPosition();
        textArea.selectWord();
        String strClassName = textArea.getSelectedText();
        textArea.setCaretPosition(iPos);
        
        if (strClassName == null || strClassName.trim().equals("")){
            strClassName = SrcInfoUtils.getClassName(argView);
        }
        //strClassName.
        String strClass = selectClass(argView, strClassName, false);
        if (strClass != null && !strClass.equals("")) {
            viewClassInfo(argView, strClass);
        }
    }
*/

    /**
     * View class info in a ClassInfoDialog
     *
     * @param argClassName  Description of the Parameter
     * @param argParent     Description of the Parameter
     */
/*
    public static void viewClassInfo(Frame argParent, String argClassName) {
        try {
            View view = (argParent instanceof View ? 
                         (View) argParent : 
                         jEdit.getActiveView());
            DockableWindowManager winMgr = view.getDockableWindowManager();
            if (!winMgr.isDockableWindowVisible(AntHelperPlugin.DOCKABLE_NAME)){
                winMgr.toggleDockableWindow(AntHelperPlugin.DOCKABLE_NAME);
            }
            
            ClassBrowserPanel panelCB = 
                (ClassBrowserPanel) winMgr.getDockableWindow(AntHelperPlugin.DOCKABLE_NAME);
            panelCB.setSelectedClass(argClassName);
            //ClassInfoDialog cid = new ClassInfoDialog(argParent, argClassName, true);
            //cid.show();
        } catch (Exception e) {
            logger.error("Could not view class info for (" + argClassName + ")", e);
        }
    }
*/
    /**
     * Select the full class name (or package) from the partial class name.
     * (i.e. java.util.Vector from Vector) Uses the java.class.path
     *
     * @param argView             Description of the Parameter
     * @param argIncludePackages  Description of the Parameter
     * @param argClassName        Description of the Parameter
     * @return                    Description of the Return Value
     */
    public static String selectClass(View argView, String argClassName, boolean argIncludePackages) {
        String strClass = null;
        Context context = ContextMgr.getContext(argView);
        if (context != null) {
            // get selected word
            JEditTextArea textArea = argView.getTextArea();
            String strClassName = argClassName == null ? extractClassNameFromTextArea(textArea) : argClassName;
            strClass = selectClass(argView, strClassName, context.getClassNameCache(), argIncludePackages);
        }
        return strClass;
    }


    /**
     * Select the full class name (or package) from the partial class name.
     * (i.e. java.util.Vector from Vector) Uses the java.class.path
     *
     * @param argParent           Description of the Parameter
     * @param argClassName        Description of the Parameter
     * @param argIncludePackages  Description of the Parameter
     * @return                    Description of the Return Value
     */
    public static String selectClass(Frame argParent,
                                     String argClassName,
                                     boolean argIncludePackages) {
        return selectClass(argParent,
            argClassName,
            System.getProperty("java.class.path"),
            argIncludePackages);
    }


    /**
     * Select the full class name (or package) from the partial class name.
     * (i.e. java.util.Vector from Vector) Uses the given ClassNameCache
     *
     * @param argParent           Description of the Parameter
     * @param argClassName        Description of the Parameter
     * @param argClassPath        Description of the Parameter
     * @param argIncludePackages  Description of the Parameter
     * @return                    Description of the Return Value
     */
    public static String selectClass(Frame argParent,
                                     String argClassName,
                                     String argClassPath,
                                     boolean argIncludePackages) {
        return selectClass(argParent,
            argClassName,
            new ClassNameCache(argClassPath),
            argIncludePackages);
    }


    /**
     * Select the full class name (or package) from the partial class name.
     * (i.e. java.util.Vector from Vector)
     *
     * @param argParent           Description of the Parameter
     * @param argClassName        Description of the Parameter
     * @param argCnc              Description of the Parameter
     * @param argIncludePackages  Description of the Parameter
     * @return                    Description of the Return Value
     */
    public static String selectClass(Frame argParent,
                                     String argClassName,
                                     ClassNameCache argCnc,
                                     boolean argIncludePackages) {

        String strSelectedVal = null;

        String strClassName = argClassName == null ? "" : argClassName;

        // first, optimistically, check to see if the class is found
        Set setClasses = argCnc.getClassListForClassName(strClassName);
        String strMsg = null;
        if (setClasses == null || setClasses.size() == 0) {
            if (strClassName.equals("")){
                // nothing inputted
                strMsg = "";
            }else if (strClassName.indexOf(".") != -1){
                // a full class name was sent
                strMsg = "";
            }else{
                // some kind of class fragment/name inputted
                strMsg = "No Classes found.";
            }
            setClasses = setClasses == null ? new TreeSet() : setClasses;
        } else if (setClasses.size() == 1) {
            strMsg = "1 class found.";
            strClassName = setClasses.iterator().next().toString();
        } else {
            strMsg = setClasses.size() + " classes found.";
        }

        // now ask for user input to verify the class
        // strClassName = JOptionPane.showInputDialog(argParent, "",
        // "Enter class to import\n" + strMsg, strClassName);
        strClassName = Macros.input(argParent,
            "Input class name:\n" + strMsg,
            strClassName);
        logger.msg("class", (String) strClassName);

        // check result
        if (strClassName == null || strClassName.equals("")) {
            logger.msg("No class input, exiting...");
        } else if (strClassName.endsWith(".*")) {
            logger.msg("Package import", strClassName);
            strSelectedVal = strClassName;
        } else if (strClassName.indexOf(".") > -1) {
            logger.msg("Full class name found", strClassName);
            strSelectedVal = strClassName;
        } else {
            // display dialog to select the class from a list
            setClasses = argCnc.getClassListForClassName(strClassName);
            setClasses = setClasses == null ? new TreeSet() : setClasses;
            logger.msg(setClasses.size() + " classes found.");

            // add package level imports, if desired, then class itself
            if (argIncludePackages) {
                addPackageImports(setClasses);
            }

            // display the 2nd dialog
            //FIXME: ClassSelectionDialog csd = new ClassSelectionDialog(argParent, strClassName, setClasses);
            //FIXME: csd.show();
            //FIXME: if (csd.getReturnVal() == null) {
            //FIXME:     logger.msg("Cancel import request of (" + strClassName + ")");
            //FIXME: } else {
            //FIXME:     strSelectedVal = (String) csd.getReturnVal();
            //FIXME: }
        }
        return strSelectedVal;
    }


    /**
     * Import a class.
     *
     * @param argView  Description of the Parameter
     */
    public static void importClass(View argView) {
        importClass(argView, null);
    }


    /**
     * Bring up import class dialogs to import a class.
     * the provided class name, argClassName, will be used
     * as an initial guess for the class name.  null may be used
     * when no guess is available.
     *
     * @param argView       Description of the Parameter
     * @param argClassName  Description of the Parameter
     */
    public static void importClass(View argView, String argClassName) {
        String strImport = selectClass(argView, argClassName, true);
        if (strImport != null && !strImport.equals("")) {
            doImport(argView, strImport);
        }
    }


    /**
     * Adds a feature to the PackageImports attribute of the JavaUtils class
     *
     * @param argClasses  The feature to be added to the PackageImports
     *      attribute
     */
    static void addPackageImports(Set argClasses) {
        String strVal = null;
        int iDot = -1;
        Set setPackageImports = new TreeSet();
        for (Iterator it = argClasses.iterator(); it.hasNext(); ) {
            strVal = it.next().toString();
            iDot = strVal.lastIndexOf(".");
            if (iDot != -1) {
                setPackageImports.add(strVal.substring(0, iDot + 1) + "*");
            }
        }
        argClasses.addAll(setPackageImports);
    }


    /**
     * Gets the className attribute of the JavaUtils class
     *
     * @param arg  Description of the Parameter
     * @return     The className value
     */
    static boolean isClassName(String arg) {
        if (arg == null || arg.equals(".")) {
            return false;
        }
        boolean bRes = true;
        char c;
        for (int i = 0; i < arg.length(); i++) {
            c = arg.charAt(i);
            if((0 == i && !Character.isJavaIdentifierStart(c)) ||
							 !Character.isJavaIdentifierPart(c)) {
                bRes = false;
                break;
            }
        }
        return bRes;
    }


    /**
     * Description of the Method
     *
     * @param arg  Description of the Parameter
     * @return     Description of the Return Value
     */
    static String filterTag(String arg) {
        if (arg == null || arg.trim().equals("") || !isClassName(arg.trim())) {
            return "";
        } else {
            return arg.trim();
        }
    }


    /**
     * Description of the Method
     *
     * @param argTextArea  Description of the Parameter
     * @return             Description of the Return Value
     */
    static String extractClassNameFromTextArea(JEditTextArea argTextArea) {
        // get the selected word

        int iOldPos = argTextArea.getCaretPosition();
        // get selection, if any exists
        String strClass = argTextArea.getSelectedText();
        if (strClass == null) {
            // no selected text so select word
            argTextArea.selectWord();
            strClass = argTextArea.getSelectedText();
        }
        argTextArea.setCaretPosition(iOldPos);
        return filterTag(strClass);
    }


    /**
     * Gets the simpleName attribute of the JavaUtils object
     *
     * @param className  Description of the Parameter
     * @return           The simpleName value
     */
    String getSimpleName(String className) {
        int i = className.lastIndexOf('$');
        int j = className.lastIndexOf('.');
        
        return className.substring(Math.max(i, j) + 1);
    }


    /**
     * Description of the Method
     *
     * @param argView           Description of the Parameter
     * @param argClassToImport  Description of the Parameter
     */
    public static void doImport(View argView, String argClassToImport) {
        JEditTextArea textArea = argView.getTextArea();
        argView.getBuffer().addMarker('i', textArea.getCaretPosition());
        
        textArea.setCaretPosition(0);
        
        SearchAndReplaceOriginalValuesHolder orgValuesHolder =
          new SearchAndReplaceOriginalValuesHolder();

        SearchAndReplace.setAutoWrapAround(true);
        SearchAndReplace.setReverseSearch(false);
        SearchAndReplace.setIgnoreCase(false);
        SearchAndReplace.setRegexp(true);
        SearchAndReplace.setSearchString("^package .*;$");
        SearchAndReplace.setSearchFileSet(new CurrentBufferSet());

        String strResult = "import " + argClassToImport + ";";
        boolean bPackageFound = SearchAndReplace.find(argView);
        if (bPackageFound) {
            // if package is found...
            textArea.goToStartOfWhiteSpace(false);
            textArea.goToNextLine(false);
            //textArea.setSelectedText("\n");// + strResult);
        } else {
            // move to import of it exists, else will be at top anyway;
            textArea.setCaretPosition(0);
        }

        SearchAndReplace.setSearchString("^import .*;$");
        if (!SearchAndReplace.find(argView) && bPackageFound) {
            // if no import found
            strResult = "\n" + strResult;
        }
        textArea.goToStartOfWhiteSpace(false);
        textArea.setSelectedText(strResult + "\n");
        textArea.goToMarker('i', false);
        textArea.addMarker();
        
        orgValuesHolder.restore();
    }


    /**
     * A unit test for JUnit
     */
    static void test() {
        ClassNameCache cnc = new ClassNameCache(System.getProperty("java.class.path"));
        logger.msg("selected.class", selectClass(null, "jEdit", cnc, true));
        logger.msg("selected.class", selectClass(null, "Context", cnc, true));
        logger.msg("selected.class", selectClass(null, "Foobar", cnc, true));
    }

    public static class JavaFileFilter implements FilenameFilter{
		public boolean accept(File argDir, String argName){
			return 
            argName.toLowerCase().endsWith(".java") || 
            (new File(argDir, argName)).isDirectory();
		}
	}

    public static void listAllJavaFiles(File argDir, List argList){
        listAllFiles(argDir, new JavaFileFilter(), argList);
    }
    
    /**
     * Add all files in the given directory (and sub-directories) to argFileList
     * The filter can be used to narrow the results.
     * @param argDir       Description of the Parameter
     * @param argFilter    Description of the Parameter
     * @param argFileList  Description of the Parameter
     */
    public static void listAllFiles(File argDir, FilenameFilter argFilter, List argFileList) {
        if (argDir == null){
            return;
        }
        File[] files = null;
        if (argFilter == null) {
            files = argDir.listFiles();
        } else {
            files = argDir.listFiles(argFilter);
        }
        for (int i = 0; i < files.length; i++) {
            //System.out.println("file[i]=" + files[i]);
            if (files[i].isDirectory()) {
                listAllFiles(files[i], argFilter, argFileList);
            } else {
                argFileList.add(files[i]);
            }
        }
    }


    /**
     * Description of the Method
     *
     * @param selectedText  Description of the Parameter
     * @return              Description of the Return Value
     */
    static String sort(String selectedText) {

        StringTokenizer tokens = new StringTokenizer(selectedText, "\n");
        List lines = new ArrayList();
        while (tokens.hasMoreTokens()) {
            lines.add(tokens.nextToken());
        }
        Collections.sort(lines);
        // insert your favorite comparator here...
        StringBuffer result = new StringBuffer();
        for (Iterator it = lines.iterator(); it.hasNext(); ) {
            result.append(it.next()).append('\n');
        }
        result.append('\n');
        return result.toString();
    }


    /**
     * Sort import statements. Based on macro by Richard Wan,
     * rwan@palmtreebusiness.com
     *
     * @param argView  Description of the Parameter
     */
    public static void sortImports(View argView) {

        JEditTextArea textArea = argView.getTextArea();
        int iOldPos = textArea.getCaretPosition();

        SearchAndReplaceOriginalValuesHolder orgValuesHolder =
          new SearchAndReplaceOriginalValuesHolder();

        textArea.setCaretPosition(0);
        SearchAndReplace.setSearchString("import .+;");
        SearchAndReplace.setAutoWrapAround(false);
        SearchAndReplace.setReverseSearch(false);
        SearchAndReplace.setIgnoreCase(false);
        SearchAndReplace.setRegexp(true);
        SearchAndReplace.setSearchFileSet(new CurrentBufferSet());
        SearchAndReplace.find(argView);
        textArea.goToStartOfLine(false);
        int startPos = textArea.getCaretPosition();

        SearchAndReplace.setSearchString("(/\\*\\*)|(public class)");
        SearchAndReplace.setAutoWrapAround(false);
        SearchAndReplace.setReverseSearch(false);
        SearchAndReplace.setIgnoreCase(false);
        SearchAndReplace.setRegexp(true);
        SearchAndReplace.setSearchFileSet(new CurrentBufferSet());
        SearchAndReplace.find(argView);
        textArea.goToStartOfLine(false);
        int endPos = textArea.getCaretPosition();

        textArea.setSelection(new Selection.Range(startPos, endPos));
        String oldImports = textArea.getSelectedText();
        String newImports = sort(oldImports);
        textArea.setSelectedText(newImports);
        textArea.setCaretPosition(iOldPos);
        
        orgValuesHolder.restore();
    }

    private static class SearchAndReplaceOriginalValuesHolder
    {
      boolean orgAutoWrapAround  = SearchAndReplace.getAutoWrapAround();
      boolean orgReverseSearch  = SearchAndReplace.getReverseSearch();
      boolean orgIgnoreCase = SearchAndReplace.getIgnoreCase();
      boolean orgRegexp = SearchAndReplace.getRegexp();
      String orgSearchString = SearchAndReplace.getSearchString();
      SearchFileSet orgSearchFileSet = SearchAndReplace.getSearchFileSet();
      
      public void restore()
      {
        SearchAndReplace.setAutoWrapAround(orgAutoWrapAround);
        SearchAndReplace.setReverseSearch(orgReverseSearch);
        SearchAndReplace.setIgnoreCase(orgIgnoreCase);
        SearchAndReplace.setRegexp(orgRegexp);
        SearchAndReplace.setSearchString(orgSearchString);
        SearchAndReplace.setSearchFileSet(orgSearchFileSet);
      }
    }

    /**
     * The main program for the JavaUtils class
     *
     * @param args  The command line arguments
     */
    //public static void main(String[] args) {
    //    try {
    //        viewClassInfo(null, "java.lang.String");
    //    } catch (Throwable t) {
    //        t.printStackTrace();
    //    }
    //}

}

