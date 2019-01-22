package org.acm.seguin.summary;

import org.acm.seguin.io.FileCopy;
import java.util.Iterator;
import java.io.File;
import org.acm.seguin.junit.DirSourceTestCase;

/**
 *  Unit tests to check that we get all the counts correct
 *
 *@author    Chris Seguin
 */
public class TestCounters extends DirSourceTestCase {
	private File destDir;


	/**
	 *  Constructor for the TestCounters object
	 *
	 *@param  name  The name of the test method to invoke
	 */
	public TestCounters(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test01() {
		FileSummary fileSummary = FileSummary.getFileSummary(new File(destDir, "NoPackage.java"));

		Iterator iter = fileSummary.getImports();
		ImportSummary one = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #1", 1, one.getStartLine());
		assertEquals("Ended at the wrong place - import #1", 1, one.getEndLine());

		ImportSummary two = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #2", 2, two.getStartLine());
		assertEquals("Ended at the wrong place - import #2", 2, two.getEndLine());

		TypeSummary typeSummary = (TypeSummary) (fileSummary.getTypes().next());

		iter = typeSummary.getFields();
		int[][] results = new int[][]{{5, 8}, {9, 10}, {11, 18}, {19, 23}, {19, 24}};
		for (int ndx = 0; ndx < 5; ndx++) {
			FieldSummary next = (FieldSummary) iter.next();
			assertEquals("Started at the wrong place - field " + ndx, results[ndx][0], next.getStartLine());
			assertEquals("Ended at the wrong place - field " + ndx, results[ndx][1], next.getEndLine());
		}

		assertEquals("Started at the wrong place - type", 3, typeSummary.getStartLine());
		assertEquals("Ended at the wrong place - type", 25, typeSummary.getEndLine());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test02() {
		FileSummary fileSummary = FileSummary.getFileSummary(new File(destDir, "MethodCounts.java"));

		Iterator iter = fileSummary.getImports();
		ImportSummary one = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #1", 2, one.getStartLine());
		assertEquals("Ended at the wrong place - import #1", 3, one.getEndLine());

		ImportSummary two = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #2", 4, two.getStartLine());
		assertEquals("Ended at the wrong place - import #2", 4, two.getEndLine());

		TypeSummary typeSummary = (TypeSummary) (fileSummary.getTypes().next());

		iter = typeSummary.getMethods();
		int[][] results = new int[][]{{12, 29, 7, 2}, {30, 43, 5, 3}, {44, 53, 1, 1}, {54, 75, 8, 2}, {76, 97, 6, 2}};
		for (int ndx = 0; ndx < 5; ndx++) {
			MethodSummary next = (MethodSummary) iter.next();
			assertEquals("Started at the wrong place - method " + ndx, results[ndx][0], next.getStartLine());
			assertEquals("Ended at the wrong place - method " + ndx, results[ndx][1], next.getEndLine());
			assertEquals("Wrong statement count - method " + ndx, results[ndx][2], next.getStatementCount());
			assertEquals("Wrong block depth - method " + ndx, results[ndx][3], next.getMaxBlockDepth());
		}

		assertEquals("Started at the wrong place - type", 5, typeSummary.getStartLine());
		assertEquals("Ended at the wrong place - type", 98, typeSummary.getEndLine());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test03() {
		FileSummary fileSummary = FileSummary.getFileSummary(
				new File(destDir, "ConstructorCounts.java"));

		Iterator iter = fileSummary.getImports();
		ImportSummary one = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #1", 2, one.getStartLine());
		assertEquals("Ended at the wrong place - import #1", 3, one.getEndLine());

		ImportSummary two = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #2", 4, two.getStartLine());
		assertEquals("Ended at the wrong place - import #2", 4, two.getEndLine());

		TypeSummary typeSummary = (TypeSummary) (fileSummary.getTypes().next());

		iter = typeSummary.getMethods();
		int[][] results = new int[][]{
			{12,  28, 7, 2, 0, 16},
			{29,  46, 6, 3, 1, 36},
			{47,  58, 2, 1, 1, 54},
			{59,  82, 9, 2, 2, 67},
			{83, 115, 7, 2, 3, 94}};
		for (int ndx = 0; ndx < 5; ndx++) {
			MethodSummary next = (MethodSummary) iter.next();
			assertEquals("Started at the wrong place - constructor " + ndx, results[ndx][0], next.getStartLine());
			assertEquals("Ended at the wrong place - constructor " + ndx, results[ndx][1], next.getEndLine());
			assertEquals("Wrong statement count - constructor " + ndx, results[ndx][2], next.getStatementCount());
			assertEquals("Wrong block depth - constructor " + ndx, results[ndx][3], next.getMaxBlockDepth());
			assertEquals("Wrong parameter count - constructor " + ndx, results[ndx][4], next.getParameterCount());
			assertEquals("Wrong declaration line - constructor " + ndx, results[ndx][5], next.getDeclarationLine());
		}

		assertEquals("Started at the wrong place - type", 5, typeSummary.getStartLine());
		assertEquals("Ended at the wrong place - type", 119, typeSummary.getEndLine());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test04() {
		FileSummary fileSummary = FileSummary.getFileSummary(new File(destDir, "TypeCount.java"));

		Iterator iter = fileSummary.getImports();
		ImportSummary one = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #1", 2, one.getStartLine());
		assertEquals("Ended at the wrong place - import #1", 3, one.getEndLine());

		ImportSummary two = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #2", 4, two.getStartLine());
		assertEquals("Ended at the wrong place - import #2", 4, two.getEndLine());

		TypeSummary typeSummary = (TypeSummary) (fileSummary.getTypes().next());

		iter = typeSummary.getTypes();
		TypeSummary nestedClass = (TypeSummary) iter.next();
		TypeSummary nestedInterface = (TypeSummary) iter.next();

		assertEquals("Started at the wrong place - nested class", 15, nestedClass.getStartLine());
		assertEquals("Ended at the wrong place - nested class", 30, nestedClass.getEndLine());
		FieldSummary field = (FieldSummary) nestedClass.getFields().next();
		MethodSummary abstractMethod = (MethodSummary) nestedClass.getMethods().next();
		assertEquals("Started at the wrong place - field", 23, field.getStartLine());
		assertEquals("Ended at the wrong place - field", 23, field.getEndLine());
		assertEquals("Started at the wrong place - abstract method", 24, abstractMethod.getStartLine());
		assertEquals("Ended at the wrong place - abstract method", 29, abstractMethod.getEndLine());

		assertEquals("Started at the wrong place - nested interface", 31, nestedInterface.getStartLine());
		MethodSummary interfaceMethod = (MethodSummary) nestedInterface.getMethods().next();
		assertEquals("Started at the wrong place - interface method", 40, interfaceMethod.getStartLine());
		assertEquals("Ended at the wrong place - interface method", 45, interfaceMethod.getEndLine());
		assertEquals("Ended at the wrong place - nested interface", 46, nestedInterface.getEndLine());

		assertEquals("Started at the wrong place - type", 5, typeSummary.getStartLine());
		assertEquals("Ended at the wrong place - type", 47, typeSummary.getEndLine());
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test05() {
		FileSummary fileSummary = FileSummary.getFileSummary(new File(destDir, "AnonymousClassCounts.java"));

		Iterator iter = fileSummary.getImports();
		ImportSummary one = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #1", 2, one.getStartLine());
		assertEquals("Ended at the wrong place - import #1", 3, one.getEndLine());

		ImportSummary two = (ImportSummary) iter.next();
		assertEquals("Started at the wrong place - import #2", 4, two.getStartLine());
		assertEquals("Ended at the wrong place - import #2", 4, two.getEndLine());

		TypeSummary typeSummary = (TypeSummary) (fileSummary.getTypes().next());
		assertEquals("Started at the wrong place - type", 5, typeSummary.getStartLine());

		FieldSummary field = (FieldSummary) typeSummary.getFields().next();
		assertEquals("Started at the wrong place - field", 12, field.getStartLine());
		assertEquals("Ended at the wrong place - field", 23, field.getEndLine());

		iter = typeSummary.getMethods();
		MethodSummary method = (MethodSummary) iter.next();
		while (!method.getName().equals("getResults")) {
			method = (MethodSummary) iter.next();
		}
		assertEquals("Started at the wrong place - method", 24, method.getStartLine());
		assertEquals("Ended at the wrong place - method", 57, method.getEndLine());

		assertEquals("Ended at the wrong place - type", 58, typeSummary.getEndLine());
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		File cleanDir = new File(clean);
		destDir = new File(root + "\\count");
		destDir.mkdirs();

		(new FileCopy(
				new File(cleanDir, "count_NoPackage.java"),
				new File(destDir, "NoPackage.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "count_MethodCounts.java"),
				new File(destDir, "MethodCounts.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "count_ConstructorCounts.java"),
				new File(destDir, "ConstructorCounts.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "count_TypeCount.java"),
				new File(destDir, "TypeCount.java"),
				false)).run();

		(new FileCopy(
				new File(cleanDir, "count_AnonymousClassCounts.java"),
				new File(destDir, "AnonymousClassCounts.java"),
				false)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		(new File(destDir, "NoPackage.java")).delete();
		(new File(destDir, "MethodCounts.java")).delete();
		(new File(destDir, "ConstructorCounts.java")).delete();
		(new File(destDir, "TypeCount.java")).delete();
		(new File(destDir, "AnonymousClassCounts.java")).delete();
	}
}
