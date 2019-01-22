package org.acm.seguin.refactor.field;

import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.refactor.RefactoringException;

/**
 *  Unit tests for the pullup field refactoring.
 *
 *@author    Chris Seguin
 */
public class TestPushUpFieldRefactoring extends DirSourceTestCase {
	private File impDest;
	private File dest;


	/**
	 *  Constructor for the TestPullUpFieldRefactoring object
	 *
	 *@param  name  the name of the unit test to run
	 */
	public TestPushUpFieldRefactoring(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test1() {
		PushUpFieldRefactoring puff = new PushUpFieldRefactoring();
		puff.setClass("field.pullup", "Child");
		puff.setField("panel1");

		boolean exceptionThrown = false;
		try {
			puff.run();
		}
		catch (RefactoringException re) {
			exceptionThrown = true;
			assertEquals("Incorrect message", "Field named panel1 already exists in parent class", re.getMessage());
		}

		assertTrue("No exception thrown", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test2() {
		PushUpFieldRefactoring puff = new PushUpFieldRefactoring();
		puff.setClass("field.pullup", "Child");
		puff.setField("panel2");

		boolean exceptionThrown = false;
		try {
			puff.run();
		}
		catch (RefactoringException re) {
			exceptionThrown = true;
			assertEquals("Incorrect message", "Field named panel2 already exists in an ancestor class", re.getMessage());
		}

		assertTrue("No exception thrown", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test3() throws RefactoringException {
		PushUpFieldRefactoring puff = new PushUpFieldRefactoring();
		puff.setClass("field.pullup", "Child");
		puff.setField("panel3");

		puff.run();

		//  Check things out
		File check = new File(this.check + "\\ut2\\step1");

		FileCompare.assertEquals("Child is incorrect",
				new File(check, "Child.java"),
				new File(dest, "Child.java"));
		FileCompare.assertEquals("Child2 is incorrect",
				new File(check, "Child2.java"),
				new File(dest, "Child2.java"));
		FileCompare.assertEquals("Parent is incorrect",
				new File(check, "Parent.java"),
				new File(dest, "Parent.java"));
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test4() throws RefactoringException {
		PushUpFieldRefactoring puff = new PushUpFieldRefactoring();
		puff.setClass("field.pullup", "Child");
		puff.setField("panel4");

		puff.run();

		//  Check things out
		File check = new File(this.check + "\\ut2\\step2");

		FileCompare.assertEquals("Child is incorrect",
				new File(check, "Child.java"),
				new File(dest, "Child.java"));
		FileCompare.assertEquals("Child2 is incorrect",
				new File(check, "Child2.java"),
				new File(dest, "Child2.java"));
		FileCompare.assertEquals("Parent is incorrect",
				new File(check, "Parent.java"),
				new File(dest, "Parent.java"));
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test5() throws RefactoringException {
		PushUpFieldRefactoring puff = new PushUpFieldRefactoring();
		puff.setClass("field.pullup", "Child");
		puff.setField("panel5");

		puff.run();

		//  Check things out
		File check = new File(this.check + "\\ut2\\step3");

		FileCompare.assertEquals("Child is incorrect",
				new File(check, "Child.java"),
				new File(dest, "Child.java"));
		FileCompare.assertEquals("Child2 is incorrect",
				new File(check, "Child2.java"),
				new File(dest, "Child2.java"));
		FileCompare.assertEquals("Parent is incorrect",
				new File(check, "Parent.java"),
				new File(dest, "Parent.java"));
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test6() {
		PushUpFieldRefactoring puff = new PushUpFieldRefactoring();
		puff.setClass("field.pullup", "Child");
		puff.setField("panel7");

		boolean exceptionThrown = false;
		try {
			puff.run();
		}
		catch (RefactoringException re) {
			exceptionThrown = true;
			assertEquals("Incorrect message", "Field named panel7 does not exist in Child", re.getMessage());
		}

		assertTrue("No exception thrown", exceptionThrown);
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		File cleanDir = new File(this.clean);
		dest = new File(root + "\\field\\pullup");
		if (!dest.exists()) {
			dest.mkdirs();
		}

		(new FileCopy(
				new File(cleanDir, "field_pullup_Child.java"),
				new File(dest, "Child.java"),
				false)).run();
		(new FileCopy(
				new File(cleanDir, "field_pullup_Child2.java"),
				new File(dest, "Child2.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "field_pullup_Parent.java"),
				new File(dest, "Parent.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "field_pullup_Grandparent.java"),
				new File(dest, "Grandparent.java"),
				false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		(new File(dest, "Child.java")).delete();
		(new File(dest, "Child2.java")).delete();
		(new File(dest, "Parent.java")).delete();
		(new File(dest, "Grandparent.java")).delete();
	}
}
