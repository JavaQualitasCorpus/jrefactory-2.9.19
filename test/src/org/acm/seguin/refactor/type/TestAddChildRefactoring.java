package org.acm.seguin.refactor.type;

import java.io.File;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.io.FileCopy;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.refactor.RefactoringException;

/**
 *  Unit test for adding a child
 *
 *@author    Chris Seguin
 */
public class TestAddChildRefactoring extends DirSourceTestCase {
	/**
	 *  Constructor for the TestAddChildRefactoring object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestAddChildRefactoring(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test01() {
		AddChildRefactoring aap = new AddChildRefactoring();
		aap.setParentClass("abstracter", "NameConflict");
		aap.setChildName("Associate");

		boolean exceptionThrown = false;
		try {
			aap.run();
		}
		catch (RefactoringException re) {
			exceptionThrown = true;
		}

		assertTrue("Did not complain about the name conflicts", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException {
		AddChildRefactoring aap = new AddChildRefactoring();
		aap.setParentClass("abstracter", "NormalClass");
		aap.setChildName("ChildOneClass");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step20");
		File impDir = new File(root + "\\imp");

		FileCompare.assertEquals("NormalClass class in error",
				new File(checkDir, "NormalClass.java"),
				new File(destDir, "NormalClass.java"));

		FileCompare.assertEquals("ChildOneClass class in error",
				new File(checkDir, "ChildOneClass.java"),
				new File(destDir, "ChildOneClass.java"));

		FileCompare.assertEquals("TypeChecker class in error",
				new File(checkDir, "TypeChecker.java"),
				new File(impDir, "TypeChecker.java"));

		(new File(destDir, "ChildOneClass.java")).delete();
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\abstracter");
		File impDir = new File(root + "\\imp");

		(new FileCopy(
				new File(cleanDir, "NormalClass.java"),
				new File(destDir, "NormalClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "TypeChecker.java"),
				new File(impDir, "TypeChecker.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "NameConflict.java"),
				new File(destDir, "NameConflict.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		File destDir = new File(root + "\\abstracter");
		File impDir = new File(root + "\\imp");

		(new File(destDir, "NormalClass.java")).delete();
		(new File(impDir, "TypeChecker.java")).delete();
		(new File(destDir, "NameConflict.java")).delete();
	}
}
