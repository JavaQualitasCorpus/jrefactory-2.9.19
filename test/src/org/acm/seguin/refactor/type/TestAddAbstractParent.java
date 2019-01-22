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
 *  Unit test for creating a class or adding a parent class
 *
 *@author    Chris Seguin
 */
public class TestAddAbstractParent extends DirSourceTestCase {
	/**
	 *  Constructor for the TestAddAbstractParent object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestAddAbstractParent(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test01() throws RefactoringException {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "NormalClass");
		aap.setParentName("ParentClass");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step10");
		FileCompare.assertEquals("Normal class in error",
				new File(checkDir, "NormalClass.java"),
				new File(destDir, "NormalClass.java"));
		FileCompare.assertEquals("Parent class in error",
				new File(checkDir, "ParentClass.java"),
				new File(destDir, "ParentClass.java"));

		(new File(destDir, "ParentClass.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "GrandParentClass");
		aap.setParentName("GreatGrandParentClass");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step11");
		FileCompare.assertEquals("Grand Parent class in error", new File(checkDir, "GrandParentClass.java"),
				new File(destDir, "GrandParentClass.java"));
		FileCompare.assertEquals("Great Grand Parent class in error", new File(checkDir, "GreatGrandParentClass.java"),
				new File(destDir, "GreatGrandParentClass.java"));

		(new File(destDir, "GreatGrandParentClass.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test03() {
		CreateClass aap = new CreateClass();
		aap.setPackageName("abstracter");
		String parent = "Parent";

		ASTName grandparent = new ASTName(0);
		grandparent.addNamePart("GrandParent");

		ASTUnmodifiedClassDeclaration ucd = aap.createClassBody(parent, grandparent);
		ASTCompilationUnit rootNode = new ASTCompilationUnit(0);
		rootNode.jjtAddChild(ucd, 0);
		aap.print(parent, rootNode);

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step12");
		FileCompare.assertEquals("Normal class in error", new File(checkDir, "AParent.java"),
				new File(destDir, "Parent.java"));

		(new File(destDir, "Parent.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test04() {
		String parent = "Parent";
		CreateClass aap = new CreateClass(null, parent, true);
		aap.setPackageName("abstracter");

		ASTName grandparent = new ASTName(0);
		grandparent.addNamePart("GrandParent");

		ASTClassDeclaration ucd = aap.createModifiedClass(grandparent);
		ASTCompilationUnit rootNode = new ASTCompilationUnit(0);
		rootNode.jjtAddChild(ucd, 0);
		aap.print(parent, rootNode);

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step12");
		FileCompare.assertEquals("Normal class in error", new File(checkDir, "BParent.java"),
				new File(destDir, "Parent.java"));

		(new File(destDir, "Parent.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test05() {
		String parent = "Parent";
		CreateClass aap = new CreateClass(null, parent, true);
		aap.setPackageName("abstracter");

		ASTName grandparent = new ASTName(0);
		grandparent.addNamePart("GrandParent");

		ASTTypeDeclaration ucd = aap.createTypeDeclaration(grandparent);
		ASTCompilationUnit rootNode = new ASTCompilationUnit(0);
		rootNode.jjtAddChild(ucd, 0);
		aap.print(parent, rootNode);

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step12");
		FileCompare.assertEquals("Normal class in error", new File(checkDir, "CParent.java"),
				new File(destDir, "Parent.java"));

		(new File(destDir, "Parent.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test06() {
		String parent = "Parent";

		ASTName grandparent = new ASTName(0);
		grandparent.addNamePart("GrandParent");

		ASTCompilationUnit rootNode = new ASTCompilationUnit(0);

		TypeSummary typeSummary = GetTypeSummary.query(
				PackageSummary.getPackageSummary("abstracter"),
				"NormalClass");
		CreateClass aap = new CreateClass(typeSummary, "Parent", true);
		boolean result = aap.addImportStatement(null, grandparent,
				rootNode, 0);
		assertTrue("Not incremented", result);
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test08() {
		String parent = "Parent";

		TypeSummary typeSummary = GetTypeSummary.query(
				PackageSummary.getPackageSummary("abstracter"),
				"NormalClass");
		CreateClass aap = new CreateClass(typeSummary, parent, true);
		aap.setPackageName("abstracter");

		ASTPackageDeclaration ucd = aap.createPackageDeclaration();
		ASTCompilationUnit rootNode = new ASTCompilationUnit(0);
		rootNode.jjtAddChild(ucd, 0);
		aap.print("Package", rootNode);

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step12");
		FileCompare.assertEquals("Normal class in error", new File(checkDir, "EParent.java"),
				new File(destDir, "Package.java"));

		(new File(destDir, "Package.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test09() {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "NameConflict");
		aap.setParentName("Associate");

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
	public void test10() throws RefactoringException {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "NameConflictClass");
		aap.setParentName("Associate");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step13");
		File impDir = new File(root + "\\imp");

		FileCompare.assertEquals("NameConflictClass class in error",
				new File(checkDir, "NameConflictClass.java"),
				new File(destDir, "NameConflictClass.java"));

		FileCompare.assertEquals("Associate class in error",
				new File(checkDir, "Associate.java"),
				new File(destDir, "Associate.java"));

		FileCompare.assertEquals("TypeChecker class in error",
				new File(checkDir, "TypeChecker.java"),
				new File(impDir, "TypeChecker.java"));

		(new File(destDir, "Associate.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test11() throws RefactoringException {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "ChildOne");
		aap.addChildClass("abstracter", "ChildTwo");
		aap.setParentName("StepParentClass");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step14");

		FileCompare.assertEquals("ChildOne class in error",
				new File(checkDir, "ChildOne.java"),
				new File(destDir, "ChildOne.java"));

		FileCompare.assertEquals("ChildTwo class in error",
				new File(checkDir, "ChildTwo.java"),
				new File(destDir, "ChildTwo.java"));

		FileCompare.assertEquals("StepParentClass class in error",
				new File(checkDir, "StepParentClass.java"),
				new File(destDir, "StepParentClass.java"));

		(new File(destDir, "StepParentClass.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test12() throws RefactoringException {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "ChildThree");
		aap.addChildClass("abstracter", "ChildFour");
		aap.setParentName("StepParentTwoClass");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step15");

		FileCompare.assertEquals("ChildThree class in error",
				new File(checkDir, "ChildThree.java"),
				new File(destDir, "ChildThree.java"));

		FileCompare.assertEquals("ChildFour class in error",
				new File(checkDir, "ChildFour.java"),
				new File(destDir, "ChildFour.java"));

		FileCompare.assertEquals("StepParentTwoClass class in error",
				new File(checkDir, "StepParentTwoClass.java"),
				new File(destDir, "StepParentTwoClass.java"));

		(new File(destDir, "StepParentTwoClass.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 */
	public void test13() {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "NameConflict");
		aap.addChildClass("imp", "Associate");
		aap.setParentName("ValidParentClass");

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
	 */
	public void test14() {
		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "NameConflict");
		aap.setParentName("NormalClass");

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
	public void test15() throws RefactoringException {
		String parent = "SampleChild";

		TypeSummary typeSummary = GetTypeSummary.query(
				PackageSummary.getPackageSummary("abstracter"),
				"NormalClass");
		CreateClass cc = new CreateClass(typeSummary, parent, false);
		cc.setAbstract(false);
		cc.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step12");

		FileCompare.assertEquals("Normal class in error", new File(checkDir, "AChild.java"),
				new File(destDir, "SampleChild.java"));

		(new File(destDir, "SampleChild.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test16() throws RefactoringException {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\abstracter");
		File impDir = new File(root + "\\imp");

		(new FileCopy(
				new File(cleanDir, "ExtraParentClass.java"),
				new File(impDir, "ExtraParentClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ExtraNormalClass.java"),
				new File(destDir, "ExtraNormalClass.java"), false)).run();

		(new SummaryTraversal(root)).run();

		AddAbstractParent aap = new AddAbstractParent();
		aap.addChildClass("abstracter", "ExtraNormalClass");
		aap.setParentName("ExtraLocalParent");

		aap.run();

		File checkDir = new File(check + "\\ut1\\step21");

		FileCompare.assertEquals("ExtraParentClass in error",
				new File(checkDir, "ExtraParentClass.java"),
				new File(impDir, "ExtraParentClass.java"));

		FileCompare.assertEquals("ExtraNormalClass in error",
				new File(checkDir, "ExtraNormalClass.java"),
				new File(destDir, "ExtraNormalClass.java"));

		FileCompare.assertEquals("ExtraLocalParent in error",
				new File(checkDir, "ExtraLocalParent.java"),
				new File(destDir, "ExtraLocalParent.java"));

		(new File(impDir, "ExtraParentClass.java")).delete();
		(new File(destDir, "ExtraNormalClass.java")).delete();
		(new File(destDir, "ExtraLocalParent.java")).delete();
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp() {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\abstracter");
		File impDir = new File(root + "\\imp");

		(new FileCopy(
				new File(cleanDir, "GrandParentClass.java"),
				new File(destDir, "GrandParentClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "NormalClass.java"),
				new File(destDir, "NormalClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "TypeChecker.java"),
				new File(impDir, "TypeChecker.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "NameConflict.java"),
				new File(destDir, "NameConflict.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "NameConflictClass.java"),
				new File(destDir, "NameConflictClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ChildOne.java"),
				new File(destDir, "ChildOne.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ChildTwo.java"),
				new File(destDir, "ChildTwo.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ChildThree.java"),
				new File(destDir, "ChildThree.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ChildFour.java"),
				new File(destDir, "ChildFour.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown() {
		File destDir = new File(root + "\\abstracter");
		File problemChild = new File(destDir, "Associate.java");
		if (problemChild.exists()) {
			problemChild.delete();
		}

		File impDir = new File(root + "\\imp");

		(new File(destDir, "GrandParentClass.java")).delete();
		(new File(destDir, "NormalClass.java")).delete();
		(new File(impDir, "TypeChecker.java")).delete();
		(new File(destDir, "NameConflict.java")).delete();
		(new File(destDir, "NameConflictClass.java")).delete();
		(new File(destDir, "ChildOne.java")).delete();
		(new File(destDir, "ChildTwo.java")).delete();
		(new File(destDir, "ChildThree.java")).delete();
		(new File(destDir, "ChildFour.java")).delete();
	}
}
