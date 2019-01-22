package org.acm.seguin.refactor.method;
import java.io.File;
import org.acm.seguin.awt.Question;
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
public class TestPushUpMethod extends DirSourceTestCase {
	/**
	 *  Constructor for the TestPushUpMethod object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestPushUpMethod(String name) {
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

		PushUpMethodRefactoring pum = new PushUpMethodRefactoring();
		pum.setMethod(method);

		pum.run();

		//  Check things out
		File checkDir = new File(check + "\\ut3\\step1");
		File dest = new File(root + "\\method");

		FileCompare.assertEquals("Child is incorrect",
				new File(checkDir + "\\Child.java"),
				new File(dest, "Child.java"));
		FileCompare.assertEquals("Child2 is incorrect",
				new File(checkDir, "Child2.java"),
				new File(dest, "Child2.java"));
		FileCompare.assertEquals("Parent is incorrect",
				new File(checkDir, "Parent.java"),
				new File(dest, "Parent.java"));
	}


	/*
	 * A unit test for JUnit
	 *
	 * @exception  RefactoringException  Description of Exception
	 * public void test02() throws RefactoringException {
	 * TypeSummary type = GetTypeSummary.query("method", "Child");
	 * MethodSummary method = GetMethodSummary.query(type, "setSleepTime");
	 * PushUpMethodRefactoring pum = new PushUpMethodRefactoring();
	 * pum.setMethod(method);
	 * pum.run();
	 * Check things out
	 * File check = new File(this.check + "\\ut3\\step2");
	 * File dest = new File(root + "\\method");
	 * FileCompare.assertEquals("Child is incorrect",
	 * new File(check, "Child.java"),
	 * new File(dest, "Child.java"));
	 * FileCompare.assertEquals("Parent is incorrect",
	 * new File(check, "Parent.java"),
	 * new File(dest, "Parent.java"));
	 * }
	 */

	/**
	 *  A unit test for JUnit
	 */
	public void test03() {
		TypeSummary type = GetTypeSummary.query("method", "Child");
		MethodSummary method = GetMethodSummary.query(type, "stopOne");

		PushUpMethodRefactoring pum = new PushUpMethodRefactoring();
		pum.setMethod(method);

		boolean exceptionThrown = false;
		try {
			pum.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message", "A method with the same signature (name and parameter types) already exists in the Parent class", re.getMessage());
			exceptionThrown = true;
		}

		assertTrue("No exception thrown", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test04() {
		TypeSummary type = GetTypeSummary.query("method", "Child");
		MethodSummary method = GetMethodSummary.query(type, "stopTwo");

		PushUpMethodRefactoring pum = new PushUpMethodRefactoring();
		pum.setMethod(method);

		boolean exceptionThrown = false;
		try {
			pum.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message", "A method with the conflicting signature (name and parameter types) already exists in the Parent class", re.getMessage());
			exceptionThrown = true;
		}

		assertTrue("No exception thrown", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test05() {
		TypeSummary type = GetTypeSummary.query("method", "Child");
		MethodSummary method = GetMethodSummary.query(type, "init");

		PushUpMethodRefactoring pum = new PushUpMethodRefactoring();
		pum.setMethod(method);

		boolean exceptionThrown = false;
		try {
			pum.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message",
					"Method with a signature of init() : boolean found in child of Parent",
					re.getMessage());
			exceptionThrown = true;
		}

		assertTrue("No exception thrown", exceptionThrown);
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		Question.setAlwaysYes(true);
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\method");
		destDir.mkdir();

		(new FileCopy(
				new File(cleanDir, "method_Parent.java"),
				new File(destDir, "Parent.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "method_Child.java"),
				new File(destDir, "Child.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "method_Child2.java"),
				new File(destDir, "Child2.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		File destDir = new File(root + "\\method");
		(new File(destDir, "Parent.java")).delete();
		(new File(destDir, "Child.java")).delete();
		(new File(destDir, "Child2.java")).delete();
		Question.setAlwaysYes(false);
	}
}
