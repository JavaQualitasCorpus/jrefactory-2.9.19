/*
 * Author:  Chris Seguin
 *
 * This software has been developed under the copyleft
 * rules of the GNU General Public License.  Please
 * consult the GNU General Public License for more
 * details about use and distribution of this software.
 */
package org.acm.seguin.summary.query;
import java.io.File;
import java.util.Iterator;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.junit.DirSourceTestCase;

/**
 *  Test cases for the ChildClassSearcher object
 *
 *@author    Chris Seguin
 */
public class TestChildClassSearcher extends DirSourceTestCase {
	/**
	 *  Constructor for the TestChildClassSearcher object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestChildClassSearcher(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test1() {
		PackageSummary packageSummary = PackageSummary.getPackageSummary("imp.inh");
		TypeSummary baseClass = GetTypeSummary.query(
				packageSummary,
				"BaseClass");

		Iterator iter = ChildClassSearcher.query(baseClass);
		if (iter == null) {
			fail("Should have two classes");
		}

		int count = 0;
		while (iter.hasNext()) {
			count++;
			iter.next();
		}

		assertEquals("Wrong number of child classes",
				2,
				count);
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\imp\\inh");

		(new FileCopy(
				new File(cleanDir, "imp_inh_BaseClass.java"),
				new File(destDir, "BaseClass.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "imp_inh_ChildOneClass.java"),
				new File(destDir, "ChildOneClass.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "imp_inh_ChildTwoClass.java"),
				new File(destDir, "ChildTwoClass.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		File destDir = new File(root + "\\imp\\inh");
		(new File(destDir, "BaseClass.java")).delete();
		(new File(destDir, "ChildOneClass.java")).delete();
		(new File(destDir, "ChildTwoClass.java")).delete();
	}
}
