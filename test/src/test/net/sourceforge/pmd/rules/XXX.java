package test.net.sourceforge.pmd.rules;

import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.AbstractRule;
import org.acm.seguin.pmd.Report;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTImportDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTPrimaryExpression;
import net.sourceforge.jrefactory.ast.ASTPrimaryPrefix;
import net.sourceforge.jrefactory.ast.ASTStatement;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pmd.jaxen.DocumentNavigator;
import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.UnsupportedAxisException;
import test.net.sourceforge.pmd.rules.EmptyCatchBlockRuleTest;
import test.net.sourceforge.pmd.rules.RuleTst;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.rules.XPathRule;

import java.util.Iterator;
import java.util.List;

/**
 * @author daniels
 *
 * Cannot Implement:
 * <LI> Cyclomatic Complexity Rule - don't understand it
 * <LI> Import From Same Package Rule - cannot check for sub packages
 * <LI> StringToString - may be possible, but a better grammar would help.
 * <LI> UnnecessaryConversionTemporaryRule - don't understand it
 * <LI> UnusedFormalParameter - may be possible, but a better grammar would help. 
 * <LI> UnusedImportsRule - may be possible, but a better grammar would help.
 * <LI> UnusedLocalVariableFieldRule - may be possible, but a better grammar would help.
 * <LI> UnusedPrivateFieldRule - may be possible, but a better grammar would help.
 * <LI> UnusedPrivateMethodRule - may be possible, but a better grammar would help.
 * <HR> 
 *
 * Partial Implementation
 * <LI> DuplicateImportsRuleTest - cannot detect specific vs. general imports  
 * 
 * <HR>
 *  
 * Differing Implementation
 * <LI> AvoidDuplicateLiteralsRule - marks all duplicate nodes
 *
 */
public class XXX extends RuleTst {

    private static final String TEST1 =
    "public class AssignmentInOperand1 {" + PMD.EOL +
    " public void bar() {" + PMD.EOL +
    "  if (x==1) {" + PMD.EOL +
    "  }" + PMD.EOL +
    " }" + PMD.EOL +
    "}";



    private Rule rule;

    public void setUp() {
        rule = new XPathRule();
        //rule.addProperty("xpath", "//*[name()='WhileStatement' or name()='IfStatement'][Expression//AssignmentOperator]");
        rule.addProperty("xpath", "//*[name()='Block']//*[name()='IfStatement']");
    }

    public void testSimple() throws Throwable {
        runTestFromString(TEST1, 1, rule);
    }

}
