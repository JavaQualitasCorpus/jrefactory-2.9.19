package test.net.sourceforge.pmd.rules;

import junit.framework.TestCase;
import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.Report;
import org.acm.seguin.pmd.Rule;
import org.acm.seguin.pmd.RuleContext;
import org.acm.seguin.pmd.RuleSet;
import org.acm.seguin.pmd.util.ResourceLoader;

import java.io.StringReader;

public class RuleTst extends TestCase {

    private static final String TEST_FILE_DIR = "test-data/";

    public void runTestFromString(String code, int expectedResults, Rule rule) throws Throwable {
        Report rpt = processUsingStringReader(code, rule);
        //System.out.println(rpt);
        assertEquals(expectedResults, rpt.size());
    }

    public void runTestFromString(String code, Rule rule, Report report) throws Throwable {
        PMD p = new PMD();
        RuleContext ctx = new RuleContext();
        ctx.setReport(report);
        ctx.setSourceCodeFilename("n/a");
        RuleSet rules = new RuleSet();
        rules.addRule(rule);
        p.processFile(new StringReader(code), rules, ctx);
    }

    public void runTestFromFile(String filename, int expectedResults, Rule rule) throws Throwable {
        Report rpt = runTestFromFile(filename, rule);
        System.out.println(rpt);
        assertEquals(expectedResults, rpt.size());
    }

    public Report runTestFromFile(String fileName, Rule rule) throws Throwable {
        Report report = new Report();
        runTestFromPartialFileName(fileName, rule, report);
        return report;
    }

    public void runTestFromFullFilename(String fullFileName, Rule rule, Report report) throws Throwable {
        PMD p = new PMD();
        RuleContext ctx = new RuleContext();
        ctx.setReport(report);
        ctx.setSourceCodeFilename(fullFileName);
        RuleSet rules = new RuleSet();
        rules.addRule(rule);
        p.processFile(ResourceLoader.loadResourceAsStream(fullFileName), rules, ctx);
    }

    //////////////////////
    private void runTestFromPartialFileName(String fileName, Rule rule, Report report) throws Throwable {
        runTestFromFullFilename(TEST_FILE_DIR + fileName, rule, report);
    }

    private Report processUsingStringReader(String code, Rule rule) throws Throwable {
        Report report = new Report();
        runTestFromString(code, rule, report);
        return report;
    }
}
