/*
 * Author:  Chris Seguin
 *
 * This software has been developed under the copyleft
 * rules of the GNU General Public License.  Please
 * consult the GNU General Public License for more
 * details about use and distribution of this software.
 */
package org.acm.seguin.uml.refactor;
import java.io.File;
import java.util.Iterator;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.junit.DirSourceTestCase;

/**
 *  Test cases for the TypeCheckbox object
 *
 *@author    Chris Seguin
 */
public class TestTypeCheckbox extends DirSourceTestCase {
	/**
	 *  Constructor for the TestTypeCheckbox object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestTypeCheckbox(String name) {
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

		Iterator iter = baseClass.getTypes();
		if (iter == null) {
			fail("Should have a class called SampleClass in it");
		}

		TypeSummary sampleClass = (TypeSummary) iter.next();

		TypeCheckbox tcb = new TypeCheckbox(sampleClass);
		assertEquals("Values not the same!",
				"imp.inh.BaseClass.SampleClass",
				tcb.getFullName());
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

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		File destDir = new File(root + "\\imp\\inh");
		(new File(destDir, "BaseClass.java")).delete();
	}
}
