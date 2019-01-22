import junit.framework.*;
import org.acm.seguin.tools.RefactoryInstaller;

/**
 *  Unit tests for the entire system
 */
public class JUnitTests {
	public static TestSuite suite() {
		(new RefactoryInstaller(true)).run();

		TestSuite suite = new TestSuite();

		suite.addTest(org.TestPackage.suite());

		return suite;
	}
}
