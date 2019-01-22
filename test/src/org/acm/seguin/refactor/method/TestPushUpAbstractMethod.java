package org.acm.seguin.refactor.method;
import java.io.File;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.summary.query.GetMethodSummary;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.junit.DirSourceTestCase;

/**
 *  Functional tests for Push up method
 *
 *@author     unknown
 *@created    April 5, 2000
 */
public class TestPushUpAbstractMethod extends DirSourceTestCase {
	/**
	 *  Constructor for the TestPushUpAbstractMethod object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestPushUpAbstractMethod(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test01() throws RefactoringException {
		TypeSummary type = GetTypeSummary.query("method", "Child");
		MethodSummary method = GetMethodSummary.query(type, "getPanel");

		PushUpAbstractMethodRefactoring pum = new PushUpAbstractMethodRefactoring();
		pum.setMethod(method);

		pum.run();

		//  Check things out
		File check = new File(this.check + "\\ut3\\step6");
		File dest = new File(root + "\\method");

		FileCompare.assertEquals("Child is incorrect",
				new File(check, "Child.java"),
				new File(dest, "Child.java"));
		FileCompare.assertEquals("Parent is incorrect",
				new File(check, "Parent.java"),
				new File(dest, "Parent.java"));
	}


	/*
	 * A unit test for JUnit
	 *
	 * @exception  RefactoringException  Description of Exception
	 */
	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException {
		TypeSummary type = GetTypeSummary.query("method", "Child");
		MethodSummary method = GetMethodSummary.query(type, "init");

		PushUpAbstractMethodRefactoring pum = new PushUpAbstractMethodRefactoring();
		pum.setMethod(method);

		pum.run();

		//  Check things out
		File checkDir = new File(check + "\\ut3\\step7");
		File dest = new File(root + "\\method");

		FileCompare.assertEquals("Child is incorrect",
				new File(checkDir, "Child.java"),
				new File(dest, "Child.java"));
		FileCompare.assertEquals("Parent is incorrect",
				new File(checkDir, "Parent.java"),
				new File(dest, "Parent.java"));
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\method");
		destDir.mkdir();

		(new FileCopy(
				new File(cleanDir, "method_Parent.java"),
				new File(destDir, "Parent.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "method_Child.java"),
				new File(destDir, "Child.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		File destDir = new File(root + "\\method");
		(new File(destDir, "Parent.java")).delete();
		(new File(destDir, "Child.java")).delete();
	}
}
