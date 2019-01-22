package test.net.sourceforge.pmd.ant;

import junit.framework.TestCase;
import org.acm.seguin.pmd.ant.Formatter;
import org.acm.seguin.pmd.renderers.CSVRenderer;
import org.acm.seguin.pmd.renderers.HTMLRenderer;
import org.acm.seguin.pmd.renderers.TextRenderer;
import org.acm.seguin.pmd.renderers.XMLRenderer;
import org.apache.tools.ant.BuildException;

import java.io.File;

public class FormatterTest extends TestCase {

    
    
    
    public void testType() {
        Formatter f = new Formatter();
        f.setType("xml");
        assertTrue(f.getRenderer() instanceof XMLRenderer);
        f.setType("text");
        assertTrue(f.getRenderer() instanceof TextRenderer);
        f.setType("csv");
        assertTrue(f.getRenderer() instanceof CSVRenderer);
        f.setType("html");
        assertTrue(f.getRenderer() instanceof HTMLRenderer);
        try {
            f.setType("FAIL");
            assertTrue("Should have failed!", false);
            //FIXME? MRA    throw new RuntimeException("Should have failed!");
        } catch (Exception be) {
            // cool
        }
    }

    public void testNull() {
        Formatter f = new Formatter();
        assertTrue("Formatter toFile should start off null!", f.isToFileNull());
        f.setToFile(new File("foo"));
        assertFalse("Formatter toFile should not be null!", f.isToFileNull());
    }

}
