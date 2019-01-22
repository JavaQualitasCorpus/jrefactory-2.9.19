package org.acm.seguin.refactor.field;
import java.io.File;
import java.util.Iterator;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.refactor.RefactoringException;

/**
 *  Test cases for the PushDownFieldRefactoring object
 *
 *@author    Chris Seguin
 */
public class TestPushDownFieldRefactoring extends DirSourceTestCase {
	/**
	 *  Constructor for the TestPushDownFieldRefactoring object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestPushDownFieldRefactoring(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test1() throws RefactoringException {
		PushDownFieldRefactoring pdfr = new PushDownFieldRefactoring();
		pdfr.setField("value1");
		pdfr.setClass("imp.inh", "BaseClass");
		pdfr.addChild("imp.inh", "ChildOneClass");

		pdfr.run();

		//  Check things out
		File check = new File(this.check + "\\ut2\\step4");
		File dest = new File(root + "\\imp\\inh");

		FileCompare.assertEquals("BaseClass is incorrect",
				new File(check, "BaseClass.java"),
				new File(dest, "BaseClass.java"));
		FileCompare.assertEquals("ChildOneClass is incorrect",
				new File(check, "ChildOneClass.java"),
				new File(dest, "ChildOneClass.java"));
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test2() throws RefactoringException {
		PushDownFieldRefactoring pdfr = new PushDownFieldRefactoring();
		pdfr.setField("value2");
		pdfr.setClass("imp.inh", "BaseClass");
		pdfr.addChild("imp.inh", "ChildOneClass");
		pdfr.addChild("imp.inh", "ChildTwoClass");

		pdfr.run();

		//  Check things out
		File check = new File(this.check + "\\ut2\\step5");
		File dest = new File(root + "\\imp\\inh");

		FileCompare.assertEquals("BaseClass is incorrect",
				new File(check, "BaseClass.java"),
				new File(dest, "BaseClass.java"));
		FileCompare.assertEquals("ChildOneClass is incorrect",
				new File(check, "ChildOneClass.java"),
				new File(dest, "ChildOneClass.java"));
		FileCompare.assertEquals("ChildTwoClass is incorrect",
				new File(check, "ChildTwoClass.java"),
				new File(dest, "ChildTwoClass.java"));
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test3() {
		PushDownFieldRefactoring pdfr = new PushDownFieldRefactoring();
		pdfr.setField("value2");
		pdfr.setClass("imp.inh", "BaseClass");
		pdfr.addChild("imp.inh", "ChildOneClass");
		pdfr.addChild("imp", "Associate");

		boolean exceptionThrown = false;
		try {
			pdfr.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message",
					"Trying to push a field from BaseClass to Associate and the destination is not a subclass of the source",
					re.getMessage());
			exceptionThrown = true;
		}

		assertTrue("No exception thrown", exceptionThrown);
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
