package org.acm.seguin.refactor.method;

import java.io.File;
import java.util.Iterator;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.*;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.summary.query.GetMethodSummary;

/**
 *  Unit tests for moving a method from one class to another
 *
 *@author    Chris Seguin
 */
public class TestMoveMethodRefactoring extends DirSourceTestCase {
	/**
	 *  Constructor for the TestMoveMethodRefactoring object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestMoveMethodRefactoring(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test01() throws RefactoringException {
		System.out.println("Test 01");
		test("methodOne", 1);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException {
		System.out.println("Test 02");
		test("methodTwo", 2);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test03() throws RefactoringException {
		System.out.println("Test 03");
		test("methodThree", 3);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test04() throws RefactoringException {
		System.out.println("Test 04");
		test("methodFour",
				"Unable to find the appropriate method (getIncr2) for private field access in MoveMethodSource");
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test05() throws RefactoringException {
		System.out.println("Test 05");
		test("methodFive", 5);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test06() throws RefactoringException {
		System.out.println("Test 06");
		test("methodSix", 6);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test07() throws RefactoringException {
		System.out.println("Test 07");
		test("methodSeven", 7);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test08() throws RefactoringException {
		System.out.println("Test 08");
		test("methodEight", "Moving a method (packageMethod) from MoveMethodSource to a different package that requires package access is illegal.");
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test09() throws RefactoringException {
		System.out.println("Test 09");
		test("methodNine", "Moving a method (privateMethod) from MoveMethodSource that requires private access is illegal");
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test10() throws RefactoringException {
		System.out.println("Test 10");
		testFile2("method1", "step35");
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test11() throws RefactoringException {
		System.out.println("Test 11");
		testFile2("method2", "step36");
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test12() {
		System.out.println("Test 12");
		TypeSummary type = GetTypeSummary.query("method", "MoveMethodSource2");
		MethodSummary method = GetMethodSummary.query(type, "method3");
		Iterator iter = method.getParameters();
		ParameterSummary param = (ParameterSummary) iter.next();

		MoveMethodRefactoring mmr = new MoveMethodRefactoring();
		mmr.setMethod(method);
		mmr.setDestination(param);

		boolean exceptionThrown = false;
		try {
			mmr.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message", "The source code for String is not modifiable", re.getMessage());
			exceptionThrown = true;
		}
		assertTrue("No exception", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test13() {
		System.out.println("Test 13");
		TypeSummary type = GetTypeSummary.query("method", "MoveMethodSource2");
		MethodSummary method = GetMethodSummary.query(type, "method4");
		Iterator iter = method.getParameters();
		ParameterSummary param = (ParameterSummary) iter.next();

		MoveMethodRefactoring mmr = new MoveMethodRefactoring();
		mmr.setMethod(method);
		mmr.setDestination(param);

		boolean exceptionThrown = false;
		try {
			mmr.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message", "The parameter total is a primitive", re.getMessage());
			exceptionThrown = true;
		}
		assertTrue("No exception", exceptionThrown);
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		(new FileCopy(
				new File(clean + File.separator + "method_MoveMethodSource.java"),
				new File(root + File.separator + "method\\MoveMethodSource.java"),
				false)).run();
		(new FileCopy(
				new File(clean + File.separator + "method_MoveMethodDest.java"),
				new File(root + File.separator + "method\\MoveMethodDest.java"),
				false)).run();
		(new FileCopy(
				new File(clean + File.separator + "other_OtherMethodDest.java"),
				new File(root + File.separator + "other\\OtherMethodDest.java"),
				false)).run();
		(new FileCopy(
				new File(clean + File.separator + "method_MoveMethodSource2.java"),
				new File(root + File.separator + "method\\MoveMethodSource2.java"),
				false)).run();
		(new FileCopy(
				new File(clean + File.separator + "method_MoveMethodDest2.java"),
				new File(root + File.separator + "method\\MoveMethodDest2.java"),
				false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		(new File(root + "method\\MoveMethodSource.java")).delete();
		(new File(root + "method\\MoveMethodDest.java")).delete();
		(new File(root + "other\\OtherMethodDest.java")).delete();
		(new File(root + "method\\MoveMethodSource2.java")).delete();
		(new File(root + "method\\MoveMethodDest2.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@param  methodName                Description of Parameter
	 *@param  testID                    Description of Parameter
	 *@exception  RefactoringException  Description of Exception
	 */
	private void testFile2(String methodName, String testID) throws RefactoringException {
		TypeSummary type = GetTypeSummary.query("method", "MoveMethodSource2");
		MethodSummary method = GetMethodSummary.query(type, methodName);
		Iterator iter = method.getParameters();
		ParameterSummary param = (ParameterSummary) iter.next();

		MoveMethodRefactoring mmr = new MoveMethodRefactoring();
		mmr.setMethod(method);
		mmr.setDestination(param);

		mmr.run();

		//  Check things out
		File checkDir = new File(check + "\\ut3\\" + testID);
		File destDir = new File(root + "\\method");

		FileCompare.assertEquals("MoveMethodSource2 is incorrect",
				new File(checkDir, "MoveMethodSource2.java"),
				new File(destDir, "MoveMethodSource2.java"));
		FileCompare.assertEquals("MoveMethodDest2 is incorrect",
				new File(checkDir, "MoveMethodDest2.java"),
				new File(destDir, "MoveMethodDest2.java"));
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@param  methodName                Description of Parameter
	 *@param  testCase                  Description of Parameter
	 *@exception  RefactoringException  Description of Exception
	 */
	private void test(String methodName, int testCase) throws RefactoringException {
		TypeSummary type = GetTypeSummary.query("method", "MoveMethodSource");
		MethodSummary method = GetMethodSummary.query(type, methodName);
		Iterator iter = method.getParameters();
		ParameterSummary param = (ParameterSummary) iter.next();

		MoveMethodRefactoring mmr = new MoveMethodRefactoring();
		mmr.setMethod(method);
		mmr.setDestination(param);

		mmr.run();

		//  Check things out
		File checkDir = new File(check + "\\ut3\\step" + (9 + testCase));
		File destDir = new File(root + "\\method");

		FileCompare.assertEquals("MoveMethodSource is incorrect",
				new File(checkDir, "MoveMethodSource.java"),
				new File(destDir, "MoveMethodSource.java"));
		FileCompare.assertEquals("MoveMethodDest is incorrect",
				new File(checkDir, "MoveMethodDest.java"),
				new File(destDir, "MoveMethodDest.java"));
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@param  methodName                Description of Parameter
	 *@param  message                   Description of Parameter
	 *@exception  RefactoringException  Description of Exception
	 */
	private void test(String methodName, String message) throws RefactoringException {
		TypeSummary type = GetTypeSummary.query("method", "MoveMethodSource");
		MethodSummary method = GetMethodSummary.query(type, methodName);
		Iterator iter = method.getParameters();
		ParameterSummary param = (ParameterSummary) iter.next();

		MoveMethodRefactoring mmr = new MoveMethodRefactoring();
		mmr.setMethod(method);
		mmr.setDestination(param);

		try {
			mmr.run();
		}
		catch (RefactoringException re) {
			assertEquals("Incorrect message", message, re.getMessage());
			return;
		}
		fail("Expected a refactoring exception with a message of " + message);
	}
}
