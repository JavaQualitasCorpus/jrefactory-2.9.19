package org.acm.seguin.refactor.type;

import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.refactor.RefactoringException;

/**
 *  Unit tests for the remove empty class refactoring.
 *
 *@author    Chris Seguin
 */
public class TestRemoveEmptyClassRefactoring extends DirSourceTestCase {
	private File cleanDir;
	private File impDest;
	private File absDest;


	/**
	 *  Constructor for the TestRemoveAbstractParent object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestRemoveEmptyClassRefactoring(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test1() throws RefactoringException {
		//  Setup
		(new FileCopy(
				new File(cleanDir, "ChildRapOne.java"),
				new File(absDest, "ChildRapOne.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "RapTestOne.java"),
				new File(absDest, "RapTestOne.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "ParentRapOne.java"),
				new File(absDest, "ParentRapOne.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"),
				false)).run();

		(new SummaryTraversal(root)).run();
		RemoveEmptyClassRefactoring rap = new RemoveEmptyClassRefactoring();
		rap.setClass("abstracter", "RapTestOne");
		rap.run();

		//  Check things out
		File check = new File(this.check + "\\ut1\\step16");

		FileCompare.assertEquals("ChildRapOne is incorrect",
				new File(check, "ChildRapOne.java"),
				new File(absDest, "ChildRapOne.java"));
		FileCompare.assertEquals("ParentRapOne is incorrect",
				new File(check, "ParentRapOne.java"),
				new File(absDest, "ParentRapOne.java"));
		FileCompare.assertEquals("AccessWrap is incorrect",
				new File(check, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"));
		assertTrue("RapTestOne still exists", !(new File(absDest, "RapTestOne.java")).exists());

		//  Tear down
		(new File(absDest, "ChildRapOne.java")).delete();
		(new File(absDest, "ParentRapOne.java")).delete();
		(new File(impDest, "AccessWrap.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test2() throws RefactoringException {
		(new FileCopy(
				new File(cleanDir, "ChildRapTwo.java"),
				new File(absDest, "ChildRapTwo.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "RapTestTwo.java"),
				new File(absDest, "RapTestTwo.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"),
				false)).run();

		(new SummaryTraversal(root)).run();
		RemoveEmptyClassRefactoring rap = new RemoveEmptyClassRefactoring();
		rap.setClass("abstracter", "RapTestTwo");
		rap.run();

		//  Check things out
		File check = new File(this.check + "\\ut1\\step17");
		FileCompare.assertEquals("ChildRapTwo is incorrect",
				new File(check, "ChildRapTwo.java"),
				new File(absDest, "ChildRapTwo.java"));
		FileCompare.assertEquals("AccessWrap is incorrect",
				new File(check, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"));
		assertTrue("RapTestTwo still exists", !(new File(absDest, "RapTestTwo.java")).exists());

		//  Tear down
		(new File(absDest, "ChildRapTwo.java")).delete();
		(new File(impDest, "AccessWrap.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test3() throws RefactoringException {
		(new FileCopy(
				new File(cleanDir, "ChildRapThree.java"),
				new File(absDest, "ChildRapThree.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "RapTestThree.java"),
				new File(absDest, "RapTestThree.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "ParentRapOne.java"),
				new File(absDest, "ParentRapOne.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"),
				false)).run();

		(new SummaryTraversal(root)).run();
		RemoveEmptyClassRefactoring rap = new RemoveEmptyClassRefactoring();
		rap.setClass("abstracter", "RapTestThree");
		try {
			rap.run();
			fail("No refactoring exceptions were thrown");
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect refactoring exception",
					"The RapTestThree class has at least one method or field",
					re.getMessage());
		}

		//  Check things out
		File check = new File(this.check + "\\ut1\\step18");
		FileCompare.assertEquals("ChildRapThree is incorrect",
				new File(check, "ChildRapThree.java"),
				new File(absDest, "ChildRapThree.java"));
		FileCompare.assertEquals("RapTestThree is incorrect",
				new File(check, "RapTestThree.java"),
				new File(absDest, "RapTestThree.java"));
		FileCompare.assertEquals("ParentRapOne is incorrect",
				new File(check, "ParentRapOne.java"),
				new File(absDest, "ParentRapOne.java"));
		FileCompare.assertEquals("AccessWrap is incorrect",
				new File(check, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"));

		//  Tear down
		(new File(absDest, "ChildRapThree.java")).delete();
		(new File(absDest, "RapTestThree.java")).delete();
		(new File(absDest, "ParentRapOne.java")).delete();
		(new File(impDest, "AccessWrap.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test4() throws RefactoringException {
		(new FileCopy(
				new File(cleanDir, "ChildRapFour.java"),
				new File(absDest, "ChildRapFour.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "ChildRapFourImp.java"),
				new File(absDest, "ChildRapFourImp.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "RapTestFour.java"),
				new File(absDest, "RapTestFour.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "ParentRapFour.java"),
				new File(impDest, "ParentRapFour.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"),
				false)).run();

		File renDir = new File(root, "ren");
		(new FileCopy(
				new File(cleanDir, "OtherPackageRapFour.java"),
				new File(renDir, "OtherPackageRapFour.java"),
				false)).run();

		(new SummaryTraversal(root)).run();
		RemoveEmptyClassRefactoring rap = new RemoveEmptyClassRefactoring();
		rap.setClass("abstracter", "RapTestFour");
		rap.run();

		//  Check things out
		File check = new File(this.check + "\\ut1\\step19");

		FileCompare.assertEquals("ChildRapFour is incorrect",
				new File(check, "ChildRapFour.java"),
				new File(absDest, "ChildRapFour.java"));
		FileCompare.assertEquals("ParentRapFour is incorrect",
				new File(check, "ParentRapFour.java"),
				new File(impDest, "ParentRapFour.java"));
		FileCompare.assertEquals("AccessWrap is incorrect",
				new File(check, "AccessWrap.java"),
				new File(impDest, "AccessWrap.java"));
		FileCompare.assertEquals("OtherPackageRapFour is incorrect",
				new File(check, "OtherPackageRapFour.java"),
				new File(renDir, "OtherPackageRapFour.java"));
		FileCompare.assertEquals("ChildRapFourImp is incorrect",
				new File(check, "ChildRapFourImp.java"),
				new File(absDest, "ChildRapFourImp.java"));
		assertTrue("RapTestFour still exists", !(new File(absDest, "RapTestFour.java")).exists());

		//  Tear down
		(new File(absDest, "ChildRapFour.java")).delete();
		(new File(absDest, "ParentRapFour.java")).delete();
		(new File(impDest, "AccessWrap.java")).delete();
		(new File(renDir, "OtherPackageRapFour.java")).delete();
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		cleanDir = new File(this.clean);
		impDest = new File(root + "\\imp");
		absDest = new File(root + "\\abstracter");
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
	}
}
