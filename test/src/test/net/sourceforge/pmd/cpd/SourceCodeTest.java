package test.net.sourceforge.pmd.cpd;

import junit.framework.TestCase;
import org.acm.seguin.pmd.PMD;
import org.acm.seguin.pmd.cpd.JavaTokenizer;
import org.acm.seguin.pmd.cpd.SourceCode;
import org.acm.seguin.pmd.cpd.Tokens;

import java.io.StringReader;

public class SourceCodeTest extends TestCase {

    public void testSimple() throws Throwable {
        String code = MatchAlgorithmTest.getSampleCode();
        JavaTokenizer tokenizer = new JavaTokenizer();
        SourceCode sourceCode = new SourceCode("Foo.java");
        assertEquals("Foo.java", sourceCode.getFileName());
        tokenizer.tokenize(sourceCode, new Tokens(), new StringReader(code));

        assertEquals(MatchAlgorithmTest.LINE_1, sourceCode.getSlice(0,0));
        assertEquals(MatchAlgorithmTest.LINE_2, sourceCode.getSlice(1,1));
        assertEquals(MatchAlgorithmTest.LINE_1 + PMD.EOL + MatchAlgorithmTest.LINE_2, sourceCode.getSlice(0,1));
    }
}
