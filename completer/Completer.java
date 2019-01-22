/*
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more detaProjectTreeSelectionListenerils.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
  */
package org.acm.seguin.completer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.tree.*;

import edu.umd.cs.findbugs.DetectorFactoryCollection;

import errorlist.DefaultErrorSource;
import errorlist.ErrorSource;
import org.acm.seguin.ide.command.CommandLineSourceBrowser;
import org.acm.seguin.ide.common.ASTViewerPane;
import org.acm.seguin.ide.common.CPDDuplicateCodeViewer;
import org.acm.seguin.ide.common.CodingStandardsViewer;
import org.acm.seguin.ide.common.IDEInterface;
import org.acm.seguin.ide.common.IDEPlugin;
import org.acm.seguin.ide.common.PackageSelectorPanel;
import org.acm.seguin.ide.common.SourceBrowser;
import org.acm.seguin.ide.jedit.Navigator;
import org.acm.seguin.ide.jedit.action.Action;
import org.acm.seguin.ide.jedit.action.HideAction;
import org.acm.seguin.ide.jedit.action.ReloadAction;
import org.acm.seguin.ide.jedit.action.ReloadAllAction;
import org.acm.seguin.ide.jedit.action.ShowAction;

import org.acm.seguin.ide.jedit.event.JRefactoryEvent;
import org.acm.seguin.ide.jedit.event.JRefactoryListener;
import org.acm.seguin.io.AllFileFilter;

import net.sourceforge.jrefactory.parser.JavaParser;
import net.sourceforge.jrefactory.parser.JavaParserConstants;
import net.sourceforge.jrefactory.parser.JavaParserVisitorAdapter;
import net.sourceforge.jrefactory.parser.ParseException;
import net.sourceforge.jrefactory.parser.TokenMgrError;
import net.sourceforge.jrefactory.ast.*;

// JRefactory imports

import org.acm.seguin.tools.RefactoryInstaller;
import org.acm.seguin.uml.loader.ReloaderSingleton;

import gnu.regexp.RE;
import gnu.regexp.REException;
import gnu.regexp.REMatch;

import org.acm.seguin.completer.info.*;
import org.acm.seguin.completer.popup.*;

import org.gjt.sp.jedit.*;
import org.gjt.sp.jedit.textarea.*;
import org.gjt.sp.jedit.buffer.*;

/**
 *  Main GUI for JRefactory
 *
 * @author     <a href="mailto:JRefactoryPlugin@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @created    23 July 2003
 * @version    $Id: JRefactory.java,v 1.4 2003/09/25 13:30:36 mikeatkinson Exp $
 * @since      0.0.1
 */
public final class Completer {
   
   static Navigator.NavigatorLogger logger = getLogger(Completer.class);
   public static final boolean DEBUG = true;

      Map mapBufferToParser = new HashMap();
      Timer timerPopup = null;
      private List buffers;
      JEditTextArea textArea = null;

      private final View view;
      private final Navigator navigator;
      private boolean popupOnDot;
      private int popupDelay;
      private Object popLock = new Object();
      private BasePopup popup = null;
      private Buffer buffer = null;

       // for convenience, we define these here to be set by the init method.
       int offset = -1;
       int startOffset = -1;
       int line = -1;
       int col = -1;
       char cursor = '\t';
       String strLine = null;
    
    void init() {
        textArea = view.getTextArea();
        buffer = view.getBuffer();
        offset = textArea.getCaretPosition();
        line = buffer.getLineOfOffset(offset);
        startOffset = buffer.getLineStartOffset(line);
        col = offset - startOffset;
        cursor = buffer.getText(offset, 1).charAt(0);
        strLine = buffer.getText(startOffset, col);
    }

    public static Navigator.NavigatorLogger getLogger(Class clazz) {
       return Navigator.getLogger(clazz);
    }
      /**
       *  Constructor for the Navigation object
       *
       * @param  view       Description of Parameter
       * @param  navigator  Description of Parameter
       */
      public Completer(View view, Navigator navigator) {
         System.out.println("new Navigation(" + view + ")");
         this.view = view;
         this.navigator = navigator;
         reconfigure();
      }


      public void reconfigure() {
         popupOnDot = true; //Config.DOT_POPUP.getAsBoolean();
         popupDelay = 2000; //Config.POPUP_DELAY_MS.getAsInt();
      }


      public boolean lockPopup(BasePopup argPopup) {
         synchronized (popLock) {
            boolean hasLock = false;
            if (popup == null) {
               popup = argPopup;
               hasLock = true;
            }
            return hasLock;
         }
      }
      
      public void unlockPopup(BasePopup argPopup) {
         synchronized (popLock) {
            if (popup == argPopup) {
               popup = null;
            }
         }
      }

    /**Complete the expression at the cursor.*/
    public void complete() {
        try {
            // forced request to open popup
            init();
            int parenOffset = -1;

            // search for last non-whitespace char
            char prevNonWS = '\t';
            char c = cursor;
            for (int i = offset - 1; i >= 0; i--) {
                c = buffer.getText(i, 1).charAt(0);
                if (!Character.isWhitespace(c)) {
                    prevNonWS = c;
                    parenOffset = i;
                    break;
                }
            }
            if (cursor != '(' && prevNonWS == '(') {
                openParenPopup(parenOffset);
            } else if (ParseUtils.isClassHelp(strLine)){
                completeClass();
            } else { //if (cursor != '.' && prevNonWS == '.') {
                completeMember();
            }
        } catch (NullPointerException e){
            logger.error("Error in complete()", e);
        }
    }


    public void signatureHelp() {
        // forced request to open popup
        init();
        int parenOffset = -1;

        // search for last non-whitespace char
        char prevNonWS = '\t';
        char c = cursor;
        for (int i = offset - 1; i >= 0; i--) {
            c = buffer.getText(i, 1).charAt(0);
            if (!Character.isWhitespace(c)) {
                prevNonWS = c;
                parenOffset = i;
                break;
            }
        }
        if (cursor != '(' && prevNonWS == '(') {
            openParenPopup(parenOffset);
        }
    }

    /**

     * Description of the Method

     */

    public void completeMember() {
        init();
        if (popupOnDot && cursor == '.') {
            // explicit dot was typed and we want popup
            openDotPopup(offset + 1, "");
        } else {
            // partial statement like "str.to"
            int dot = strLine.lastIndexOf(".");
            if (dot != -1) {
                openDotPopup(offset - (strLine.length() - dot) + 1, strLine.substring(dot + 1));
            }
        }
    }


    public void completeClass() {
        init();
        char prev = buffer.getText(offset - 1, 1).charAt(0);

        boolean isNewCase = ParseUtils.isNewClassStatement(strLine);
        if (Character.isWhitespace(prev)) {
            // CASE: keyword<space> (i.e. extends )
            openClassPopup(offset, "", isNewCase);
        } else if (ParseUtils.isCatchStatement(strLine)){
            // CASE: catch (
            int mark = strLine.lastIndexOf("(");
            if (mark != -1) {
                openClassPopup(offset - (strLine.length() - mark),
                               strLine.substring(mark + 1),
                               isNewCase);
            }
        } else {
            // CASE: keyword<space>PartialName (i.e. throws Foo[BarException])
            int mark = strLine.lastIndexOf(" ");
            if (mark != -1) {
                openClassPopup(offset - (strLine.length() - mark),
                               strLine.substring(mark + 1),
                               isNewCase);
            }
        }
    }


    void openClassPopup(int offset, String prefix, boolean isNewCase) {
        logger.msg("ClassPopup: prefix=(" + prefix + ") offset=(" + offset + ")");
        JavaParser docParser = (JavaParser) mapBufferToParser.get(buffer);
        ClassTable classTable = docParser.getClassTable();

        boolean allClasses = Config.USE_ALL_PROJECT_CLASSES.getAsBoolean();
        String strFilter = Config.IMPORT_CLASS_FILTER_OUT_RE.getAsString();
        Set setImports = getImportClassSet(docParser);
        Set setClasses = (allClasses ? classTable.getAllClassNames(strFilter) : setImports);
        logger.msg((allClasses ?
            "Using all classes except (" + strFilter + ")." :
            "Using imports only.") + " size", setClasses.size());
        Vector vecItems = new Vector();
        for (Iterator it = setClasses.iterator(); it.hasNext(); ) {
            vecItems.add(new ClassPopupItem(it.next().toString()));
        }
        CodePopup cp = new CodePopup(view, prefix, vecItems, null);

        // will auto import if config say so and will
        // display popup message of action.
        cp.addInsertListener(new ClassPopupItem.ImportInsertListener(
                             setImports,
                             Config.AUTO_IMPORT_CLASSES.getAsBoolean(),
                             !Config.SHOW_CONSTRUCTOR_HELP_AFTER_INSERT.getAsBoolean()
            ));

        if (Config.SHOW_CONSTRUCTOR_HELP_AFTER_INSERT.getAsBoolean() &&
            isNewCase) {
            cp.addInsertListener(new SigHelpInsertListener());
        }
        cp.show();
    }

    class SigHelpInsertListener implements InsertListener {
        public void insertPerformed(PopupItem item,
                                    String prefix,
                                    String insertedText,
                                    View view) {
            if (item instanceof ClassPopupItem) {
                view.getTextArea().userInput('(');
            }
            signatureHelp();
        }
    }

    void openDotPopup() {
        openDotPopup(view.getTextArea().getCaretPosition(), "");
    }



    void openDotPopup(int offset, String prefix) {
        init();
        logger.msg("DotPopup: prefix=(" + prefix + ") offset=(" + offset + ")");  //"test".to
        String expr = ExpressionFinder.getExpression(buffer, offset);  // + 1);
        if (expr == null) {
            showMsg("Failed to find expression");
        } else {
            // remove the '.'
            expr = expr.substring(0, expr.length() - 1);
            JavaParser docParser = (JavaParser) mapBufferToParser.get(buffer);
            ClassTable classTable = docParser.getClassTable();
            String type = null;
            try {
                type = getTypeAtOffset(docParser, expr, offset);
            } catch (NullPointerException e) {
                showMsg("Parse error in buffer.");
                return;
            }
            logger.msg("expression={" + expr + "}, type={" + type + "}");
            boolean staticMembers = false;
            if (type.startsWith("*")) {
                staticMembers = true;
                type = type.substring(1);
            }

            Collection colMemberInfos = null;
            ClassInfo ciExpression = (ClassInfo) classTable.get(type);
            ClassInfo ciEnclosing = docParser.getEnclosingClass(line + 1, col);

            if (ciExpression != null) {
                logger.msg(ciExpression.getFullName() + " in " + ciEnclosing.getFullName());
                colMemberInfos = getMemberList(classTable,  ciExpression, ciEnclosing, staticMembers, null);
            }

            if (colMemberInfos == null) {
                showMsg("No members found.");
            } else {
                // create popup items
                Vector vecData = new Vector();
                for (Iterator it = colMemberInfos.iterator(); it.hasNext(); ) {
                    vecData.add(new MemberPopupItem((MemberInfo) it.next()));
                }
                CodePopup cp = new CodePopup( view, prefix, vecData, ciExpression.getFullName());
                if (Config.SHOW_SIG_HELP_AFTER_INSERT.getAsBoolean()) {
                    cp.addInsertListener(new SigHelpInsertListener());
                }
                cp.show();
            }
        }
    }

    void showMsg(String argMsg) {
        logger.msg(argMsg);
        SuicidalMsgPopup sp = new SuicidalMsgPopup(view, argMsg, 3000);
        sp.show();
    }


    String getTypeAtCursor(){
        String str = Test.FOO;
        init();
        String type = null;
        String word2 = buffer.getText(startOffset, offset - startOffset );

        // get word
        textArea.selectWord();

        String word = textArea.getSelectedText();

        textArea.setCaretPosition(offset);

        if (word != null && !word.trim().equals("")){
            JavaParser parser = (JavaParser)mapBufferToParser.get(buffer);
            type = getTypeAtOffset(parser, word, offset);
            if (type != null && !type.trim().equals("")){
                type = type.replace('$', '.');
                if (type.startsWith("*")){
                    type = type.substring(1);
                }
            }
            logger.msg("word", word);
            logger.msg("type", type);
            
            logger.msg("word2", word2);
            logger.msg("type2", getTypeAtOffset(parser, word2, offset)); 
        }
        return type;
    }


    void showTypeAtCursor(){
        String type = getTypeAtCursor();
        if (type != null && !type.trim().equals("")){
            PointPopup pp = new PointPopup(view, type);
            pp.show();
            /* OLD
            SuicidalMsgPopup sp = new SuicidalMsgPopup(
                _view, strType, 3000, Color.blue, Color.white);
            sp.show();
            */
        } else {
            showMsg("Type not known");
        }
    }


    String getTypeAtOffset(JavaParser parser,
                           String expression,
                           int offset) {
        int line = textArea.getLineOfOffset(offset);
        int col = offset - textArea.getLineStartOffset(line);
        return expressionParser.parseExpression(
            expression,
            new SourceLocation(parser.getPath(), line + 1, col),
            parser);
    }


    String getTypeFromImports(JavaParser parser, String name) {
        // the name should now be the unqualified class name
        // so we can just look it up classTable and cross reference
        // it with the imports to find the class!
        String type = "";
        String fullName = null;
        Set imports = getImportClassSet(parser);
        for (Iterator it = imports.iterator(); it.hasNext(); ) {
            fullName = (String) it.next();
            if (fullName.endsWith("." + name) || fullName.equals(name)) {
                type = fullName;
            }
        }
        return type;
    }


    ClassInfo getEnclosingClassAtOffset(JavaParser parser, int offset) {
        int line = textArea.getLineOfOffset(offset);
        int col = offset - textArea.getLineStartOffset(line);
        return parser.getEnclosingClass(line + 1, col);
    }


    void openParenPopup(int parenOffset) {
        init();
        String name = null;
        String type = null;
        int lookBack = 100;
        int start = Math.max(0, offset - lookBack);
        name = buffer.getText(start, offset - start + 1);
        if (DEBUG){
            logger.msg("100 chars back", name);
        }
        if (name != null) {
            JavaParser docParser = (JavaParser) mapBufferToParser.get(buffer);
            ClassTable classTable = docParser.getClassTable();
            REMatch match = ParseUtils.getLastMatch(ParseUtils.RE_PAREN_NEW, name);
            if (ParseUtils.isValidMatch(match, start, parenOffset)) {
                // found possible constructor [i.e. new String( ]
                name = match.toString();
                name = name.substring(3, name.length() - 1).trim();
                logger.msg("cons, name", name);
                type = getTypeFromImports(docParser, name);
                //logger.msg("type", type);
                ClassInfo ci = classTable.get(type);
                if (ci != null) {
                    SignaturePopup.showConstructorPopup(view, ci);
                } else {
                    showMsg("Could not find construtor class info (" + name + ")");
                }
                return;
            }
            match = ParseUtils.getLastMatch(ParseUtils.RE_PAREN_METHOD, name);
            if (ParseUtils.isValidMatch(match, start, parenOffset)) {
                // found possible constructor: new ClassName(
                name = match.toString();
                name = name.substring(1, name.length() - 1).trim();
                int adjustedOffset = start + match.getStartIndex() + 1;
                String expr = ExpressionFinder.getExpression(buffer, adjustedOffset);
                if (expr != null) {
                    expr = expr.substring(0, expr.length() - 1);
                    // remove the '.'
                    try {
                        type = getTypeAtOffset(docParser, expr, adjustedOffset);
                    } catch (NullPointerException e) {
                        showMsg("Parse error in buffer.");
                        return;
                    }
                    //type = getTypeAtOffset(docParser, expr, iAdjustedOffset);
                    boolean staticOnly = false;
                    if (type.startsWith("*")) {
                        staticOnly = true;
                        type = type.substring(1);
                    }
                    ClassInfo ci = classTable.get(type);
                    if (ci != null) {
                        // method help
                        ClassInfo enclosingClass = getEnclosingClassAtOffset(docParser, adjustedOffset);
                        Collection members = getMemberList(classTable, ci, enclosingClass,
                            staticOnly, name);
                        SignaturePopup.showMethodPopup(view, members, ci.getFullName());
                    } else {
                        showMsg("Could not find class info for (" + name + ")");
                    }
                }
                return;
            }
            match = ParseUtils.getLastMatch(ParseUtils.RE_PAREN_METHOD_THIS, name);
            if (ParseUtils.isValidMatch(match, start, parenOffset)) {
                // found possible method call to local class [eg, this]
                //} else if ( isClassHelpCase(strLine)) {
                //} else if (isClassHelpCase(strLine)) {
                // rip off trailing open paren
                name = name.substring(0, name.lastIndexOf("(")).trim();
                for (int i = name.length() - 1; i >= 0; i--) {
                    if (!Character.isJavaIdentifierPart(name.charAt(i))) {
                        name = name.substring(i + 1);
                        break;
                    }
                }
                logger.msg("local method", name);
                ClassInfo ci = getEnclosingClassAtOffset(docParser, parenOffset);
                // return both static and non-static methods
                Collection members = getMemberList(classTable, ci, ci, false, name);
                members.addAll(getMemberList(classTable, ci, ci, true, name));
                SignaturePopup.showMethodPopup(view, members, ci.getFullName());
                return;
            }
        }
    }


    private static Collection getMemberList(ClassTable argClassTable,
                                            ClassInfo ci, ClassInfo fci,
                                            boolean staticMembers, String methodName) {
        Set setMembers = new TreeSet();
        try {
            findMemberList(argClassTable, ci, fci, staticMembers, setMembers, methodName);
        } catch (Exception e) {
            logger.error("Error finding members", e);
        }
        return setMembers;
    }


    private static void findMemberList(ClassTable argClassTable,
                                       ClassInfo ci, ClassInfo fci,
                                       boolean staticMembers, Collection argMembersCol,
                                       String methodName) {

        if (methodName == null) {
            for (Iterator i = ci.getClasses().iterator(); i.hasNext(); ) {
                MemberInfo mi = (MemberInfo) argClassTable.get(ci.getFullName() + '$' + i.next().toString());
                //ci.getFullName() + '$' + (String) i.next());
                if (mi != null &&
                    argClassTable.isAccessAllowed(fci, mi, staticMembers, false)) {
                    argMembersCol.add(mi);
                }
            }
            for (Iterator i = ci.getFields().iterator(); i.hasNext(); ) {
                MemberInfo mi = (MemberInfo) i.next();
                //logger.msg("methodName=null,fields", mi.getName());
                if (argClassTable.isAccessAllowed(fci, mi, staticMembers, false)) {
                    argMembersCol.add(mi);
                }
            }
        }

        for (Iterator i = ci.getMethods().iterator(); i.hasNext(); ) {
            MemberInfo mi = (MemberInfo) i.next();
            //logger.msg("methodName=null,methods-" + ci.getFullName() , mi.getName());
            if (methodName == null || methodName.equals(mi.getName())) {
                if (argClassTable.isAccessAllowed(fci, mi, staticMembers, false)) {
                    argMembersCol.add(mi);
                }
            }
        }

        String[] ifs = ci.getInterfaces();
        String sc = ci.getSuperclass();
        for (int i = 0; i < ifs.length; i++) {
            ci = argClassTable.get(ifs[i]);
            if (ci != null) {
                findMemberList(argClassTable, ci, fci, staticMembers, argMembersCol, methodName);
            }
        }
        // resist getting java.lang.Object methods for arrays...only length
        if (sc != null && !ci.getFullName().endsWith("[]") ) {
            ci = argClassTable.get(sc);
            if (ci != null) {
                findMemberList(argClassTable, ci, fci, staticMembers, argMembersCol, methodName);
            }
        }
    }


    static Set getImportClassSet(JavaParser argParser) {
        TreeSet set = new TreeSet();
        String strImports[] = argParser.getImports();
        ClassTable classTable = argParser.getClassTable();
        List listClasses;
        String strImport;
        String strPkg;

        for (int i = 0, l = strImports.length; i < l; i++) {
            strImport = strImports[i];
            if (strImport.endsWith(".*")) {
                listClasses = classTable.getAllClassesInPackage(strImport);
                for (Iterator it = listClasses.iterator(); it.hasNext(); ) {
                    set.add(it.next());
                }
            } else {
                set.add(strImport);
            }
        }
        return set;
    }



         /**
          *  Description of the Method
          *
          * @param  buffer     Description of the Parameter
          * @param  startLine  Description of the Parameter
          * @param  offset     Description of the Parameter
          * @param  numLines   Description of the Parameter
          * @param  length     Description of the Parameter
          */
         public void contentInserted(Buffer buffer,
                                     int startLine, int offset,
                                     int numLines, int length) {
            if (length != 1) {
               return;
            }
            try {
               char c = buffer.getText(offset, 1).charAt(0);
               if (jEdit.getActiveView() == view &&
                    popupOnDot && c == '.') {
                    JEditTextArea textArea = view.getTextArea();
                    //if (!isInComment(textArea(), startLine)) {
                    if (ParseUtils.isCodeLine(textArea, startLine)){
                        //if (!isInComment(jEdit.getActiveView().getTextArea(), startLine)) {
                        if (timerPopup == null) {
                            // This delay is to keep the popup from
                            // opening when a macro is executing (many fast
                            // keystrokes.  50 should be a minimum.
                            // User may set higher.
                            timerPopup = new Timer(popupDelay, new PopupOpener());
                            timerPopup.start();
                        } else {
                            timerPopup.restart();
                        }
                    } else if (timerPopup != null) {
                        if (timerPopup.isRunning()) {
                            timerPopup.stop();
                        }
                        timerPopup = null;
                    }
                }
            } catch (Exception ex) {
               logger.warning("Error responding to Content Insert: [" +
                     "buffer=" + buffer.getName() +
                     ",line=" + startLine + "]", ex);
            }
         }


    private class PopupOpener implements Runnable, ActionListener {
        /**
         * Description of the Method
         *
         * @param event  Description of the Parameter
         */
        public void actionPerformed(ActionEvent event) {
            run();
        }

        /**  Main processing method for the PopupOpener object  */
        public void run() {
            if (timerPopup != null && timerPopup.isRunning()) {
                timerPopup.stop();
            }
            timerPopup = null;
            openDotPopup();
        }
    }
   }


