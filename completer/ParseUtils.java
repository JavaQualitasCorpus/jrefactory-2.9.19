package org.acm.seguin.completer;



//import anthelper.JavaUtils;
import org.acm.seguin.ide.jedit.Navigator;
//import org.acm.segiun.completer;
import gnu.regexp.RE;
import gnu.regexp.REException;
import gnu.regexp.REMatch;
import org.gjt.sp.jedit.textarea.JEditTextArea;


public class ParseUtils {
    final static Navigator.NavigatorLogger logger = Navigator.getLogger(ParseUtils.class);
    final static String RE_PAREN_METHOD_THIS = ".*[^\\w\\.\\$]+\\w+\\s*\\(";
    final static String RE_PAREN_NEW = "new\\s+\\w+\\s*\\(";
    final static String RE_PAREN_METHOD = "\\.\\s*\\w+\\s*\\(";
    final static String RE_CLASS_NEW = ".*[^\\w\\.\\$]+new\\s+\\w*";
    final static String RE_CLASS_CATCH = "\\s*[}]*\\s*catch\\s*\\(\\w*";
    final static String RE_CLASS_HELP;
    static {
        StringBuffer sb = new StringBuffer();
        sb.append("(.*\\s+(extends|implements|throws)\\s+.*)");
        sb.append("|");
        sb.append("(").append(RE_CLASS_NEW).append(")");
        sb.append("|");
        sb.append("(").append(RE_CLASS_CATCH).append(")");
        RE_CLASS_HELP = sb.toString();
    }

    /**
     * Gets the catchStatement attribute of the ParseUtils class
     *
     * @param argLine  Description of the Parameter
     * @return         The catchStatement value
     */
    public static boolean isCatchStatement(String argLine) {
        return isMatch(RE_CLASS_CATCH, argLine);
    }

    /**
     * Gets the newClassStatement attribute of the ParseUtils class
     *
     * @param argLine  Description of the Parameter
     * @return         The newClassStatement value
     */
    public static boolean isNewClassStatement(String argLine) {
        return isMatch(RE_CLASS_NEW, argLine);
    }

    /**
     * Gets the classHelp attribute of the ParseUtils class
     *
     * @param argLine  Description of the Parameter
     * @return         The classHelp value
     */
    public static boolean isClassHelp(String argLine) {
        return isMatch(RE_CLASS_HELP, argLine);
    }

    /**
     * Gets the codeLine attribute of the ParseUtils object
     *
     * @param argTextArea  Description of the Parameter
     * @param startLine    Description of the Parameter
     * @return             The codeLine value
     */
    public static boolean isCodeLine(JEditTextArea argTextArea, int startLine) {
        boolean bIsCodeLine = true;
        String text = argTextArea.getText();
        int begin = argTextArea.getLineStartOffset(startLine);
        int end = argTextArea.getCaretPosition();
        String line = text.substring(begin, end);

        // check for
        if (line.indexOf("//") > -1) {
            // single line comment
            bIsCodeLine = false;
        } else if (line.trim().startsWith("import ") ||
            line.trim().startsWith("package ")) {
            // import export
            bIsCodeLine = false;
        } else if (isInQuote(line)) {
            // double quoted fragement "foo.
            bIsCodeLine = false;
        } else {
            // multi line comment
            int pos = argTextArea.getCaretPosition();
            begin = text.lastIndexOf("/*", pos - 1);
            while (begin > 0 && text.charAt(begin - 1) == '/') {
                begin = text.lastIndexOf("/*", begin - 1);
            }
            end = text.lastIndexOf("*/", pos - 1);
            if (begin > -1 && end < begin) {
                bIsCodeLine = false;
            }
        }
        return bIsCodeLine;
    }

    static void testInQuote() {
        String[] strPTests = {
            "\"foo", "\"foobar'", "\""
            };

        String[] strNTests = {
            "\"foo\"", "\"foobar'\"", "\"'\""
            };
        for (int i = 0, l = strPTests.length; i < l; i++) {
            if (!isInQuote(strPTests[i])) {
                logger.msg("(+) ### FAIL on (" + strPTests[i] + ")");
            }else{
                logger.msg("(+) Pass (" + strPTests[i] + ")");
            }
        }
        for (int i = 0, l = strNTests.length; i < l; i++) {
            if (isInQuote(strNTests[i])) {
                logger.msg("(-) ### FAIL on (" + strNTests[i] + ")");
            }else{
                logger.msg("(-) Pass (" + strNTests[i] + ")");
            }
        }
    }

    /**
     * Gets the inQuote attribute of the ParseUtils class
     *
     * @param argLine  Description of the Parameter
     * @return         The inQuote value
     */
    public static boolean isInQuote(String argLine) {
        boolean bResult = false;
        boolean bInQuote = false;
        int iDQ = 0;
        for (int i=0, l = argLine.length(); i < l; i++){
            switch(argLine.charAt(i)){
                case '"':
                    // it's a quote
                    if (bInQuote){
                        // already found one quote so subtract
                        iDQ++;
                    }else{
                        iDQ--;
                    }
                    bInQuote = !bInQuote;
                    break;
                case '\\':
                    // skip next char, escape seq
                    i++;
                    break;
            }
        }
        return iDQ != 0;
    }

    /**
     * Gets the inComment attribute of the DotCompleter class
     *
     * @param startLine    Description of the Parameter
     * @param argTextArea  Description of the Parameter
     * @return             The inComment value
     */
    public static boolean isInComment(JEditTextArea argTextArea, int startLine) {
        boolean bInComment = false;
        String text = argTextArea.getText();
        int begin = argTextArea.getLineStartOffset(startLine);
        int end = argTextArea.getCaretPosition();
        String line = text.substring(begin, end);
        if (line.indexOf("//") > -1) {
            bInComment = true;
        } else {
            int pos = argTextArea.getCaretPosition();
            begin = text.lastIndexOf("/*", pos - 1);
            while (begin > 0 && text.charAt(begin - 1) == '/') {
                begin = text.lastIndexOf("/*", begin - 1);
            }
            end = text.lastIndexOf("*/", pos - 1);
            if (begin > -1 && end < begin) {
                bInComment = true;
            }
        }
        return bInComment;
    }

    static REMatch getLastMatch(String argRE, String argText) {
        REMatch match = null;
        try {
            RE re = new RE(argRE);
            REMatch[] matches = re.getAllMatches(argText);
            if (matches != null && matches.length > 0) {
                match = matches[matches.length - 1];
            }
        } catch (REException e) {
            logger.error("Error creating re: " + argRE, e);
        }
        return match;
    }

    static boolean isValidMatch(REMatch match,
                                int start,
                                int argParenOffset) {
        if (Completer.DEBUG) {
            logger.msg("string=(" + match.toString() +
                "), start=(" + match.getStartIndex() +
                "), end=(" + match.getEndIndex() + ")");
        }
        return (
            match != null &&
            start + match.getEndIndex() - 1 == argParenOffset);
    }

    static boolean isMatch(String argRE, String argText) {
        boolean bIsMatch = false;
        try {
            RE re = new RE(argRE);
            bIsMatch = re.isMatch(argText);
        } catch (REException e) {
            logger.error("Error creating regexp", e);
        }
        return bIsMatch;
    }

    static void testREClassHelp() {
        String[] strPTests = new String[]{
            " implements ", " extends ", " throws ", " new ",
            "(new ", ")new ",
            "+new ", "-new ", "&new ", "/new ", "|new ", "=new ", "<new ",
            "!new ", ">new ", " new Str", " new String",
            "catch(", "} catch(", "  }catch (", "  }catch (NullPo"
            };
        String[] strNTests = new String[]{
            "Aimplements ", "Aextends ", "Athrows ", " new",
            "$new ", "Anew ", "anew ", "9new ", "_new", ".new ",
            " new String("
            };
        testRE(RE_CLASS_HELP, strPTests, strNTests);
    }

    static void testREParenThisHelp() {
        String[] strPTests = new String[]{
            "(toString(", "( toString(", ") toString(", " toString(",
            "} else if (isClassHelpCase(",
            "if (isValidMatch("
            };
        String[] strNTests = new String[]{
            "$toString("
            };
        testRE(RE_PAREN_METHOD_THIS, strPTests, strNTests);
    }

    static void testRE(String argRE, String strPTests[], String strNTests[]) {
        try {
            logger.msg("start");
            RE re = new RE(argRE);
            for (int i = 0, l = strPTests.length; i < l; i++) {
                if (!re.isMatch(strPTests[i])) {
                    logger.error("+Error testing phrase", strPTests[i]);
                }
            }
            for (int i = 0, l = strNTests.length; i < l; i++) {
                if (re.isMatch(strNTests[i])) {
                    logger.error("-Error testing phrase", strNTests[i]);
                }
            }

            logger.msg("done");
        } catch (Exception e) {
            logger.error("Error testing RE for Class Help", e);
        }
    }

    /**
     * The main program for the ParseUtils class
     *
     * @param args  The command line arguments
     */
    public static void main(String[] args) {
        try {
            testInQuote();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}


