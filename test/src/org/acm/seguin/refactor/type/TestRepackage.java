package org.acm.seguin.refactor.type;

import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.refactor.RefactoringException;

/**
 *  Runs the unit tests for repackaging and renaming types 
 *
 *@author    Chris Seguin 
 */
public class TestRepackage extends DirSourceTestCase {
	private File checkDir;


	/**
	 *  Constructor for the TestRepackage object 
	 *
	 *@param  name  Description of Parameter 
	 */
	public TestRepackage(String name) {
		super(name);
	}


	/**
	 *  The first unit test 
	 *
	 *@exception  RefactoringException  Description of Exception 
	 */
	public void test01() throws RefactoringException {
		//  First Step
		MoveClass moveClass = new MoveClass();
		moveClass.setDirectory(root);
		moveClass.setDestinationPackage("test");
		moveClass.add("TestClass.java");
		moveClass.run();

		checkRelevantFiles("step1");

		// The second step
		moveClass = new MoveClass();
		moveClass.setDirectory(root + "\\test");
		moveClass.setDestinationPackage("experiment");
		moveClass.add("TestClass.java");
		moveClass.run();

		(new File(root + "\\test")).delete();

		checkRelevantFiles("step2");

		// The third step
		moveClass = new MoveClass();
		moveClass.setDirectory(root + "\\experiment");
		moveClass.setDestinationPackage("");
		moveClass.add("TestClass.java");
		moveClass.run();

		(new File(root + "\\experiment")).delete();

		checkRelevantFiles("step3");
		RenameClassRefactoring renameClass = new RenameClassRefactoring();
		renameClass.setDirectory(root + "\\ren");
		renameClass.setOldClassName("NoOutsideReferences");
		renameClass.setNewClassName("LocalizedObject");
		renameClass.run();

		// Copy the files
		checkRelevantFiles("step4");

		// The fifth step
		renameClass = new RenameClassRefactoring();
		renameClass.setDirectory(root + "\\ren");
		renameClass.setOldClassName("SimpleRename");
		renameClass.setNewClassName("TestState");
		renameClass.run();

		// Copy the files
		checkRelevantFiles("step5");

		// The sixth step
		renameClass = new RenameClassRefactoring();
		renameClass.setDirectory(root + "\\ren");
		renameClass.setOldClassName("SimpleNameConflict");
		renameClass.setNewClassName("AccessClassVar");
		renameClass.run();

		checkRelevantFiles("step6");

		// The seventh step
		renameClass = new RenameClassRefactoring();
		renameClass.setDirectory(root + "\\ren");
		renameClass.setOldClassName("ComplexNameConflict");
		renameClass.setNewClassName("Associate");
		renameClass.run();

		checkRelevantFiles("step7");

		(new File(root + "\\ren\\LocalizedObject.java")).delete();
		(new File(root + "\\ren\\TestState.java")).delete();
		(new File(root + "\\ren\\AccessClassVar.java")).delete();
		(new File(root + "\\ren\\Associate.java")).delete();

	}


	/**
	 *  A unit test for JUnit 
	 *
	 *@exception  RefactoringException  Description of Exception 
	 */
	public void test2() throws RefactoringException {
		File source = new File(clean);
		File orig = new File(root + "\\src");
		File dest = new File(root + "\\dest");
		checkDir = new File(check + "\\ut1\\step22");
		source.mkdirs();
		(new FileCopy(
				new File(source, "src_MovingClass.java"), 
				new File(orig, "MovingClass.java"), 
				false)).run();
		(new FileCopy(
				new File(source, "src_HoldingClass.java"), 
				new File(orig, "HoldingClass.java"), 
				false)).run();

		MoveClass moveClass = new MoveClass();
		moveClass.setDirectory(root + "\\src");
		moveClass.setDestinationPackage("dest");
		moveClass.add("MovingClass.java");
		moveClass.run();

		FileCompare.assertEquals("Comparing file MovingClass", 
				new File(checkDir, "MovingClass.java"), 
				new File(dest, "MovingClass.java"));

		(new File(dest, "MovingClass.java")).delete();
		(new File(orig, "HoldingClass.java")).delete();
	}


	/**
	 *  The JUnit setup method 
	 */
	protected void setUp() {
		//  Setup
		(new FileCopy(
				new File(clean + "\\TestClass.java"), 
				new File(root + "\\TestClass.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\AssociateTwo.java"), 
				new File(root + "\\AssociateTwo.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\Nonassociate.java"), 
				new File(root + "\\Nonassociate.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\Associate.java"), 
				new File(root + "\\imp\\Associate.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\AccessClassVar.java"), 
				new File(root + "\\imp\\AccessClassVar.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\AssociateThree.java"), 
				new File(root + "\\imp\\AssociateThree.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\AssociateFour.java"), 
				new File(root + "\\imp\\AssociateFour.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\TypeChecker.java"), 
				new File(root + "\\imp\\TypeChecker.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\Factory.java"), 
				new File(root + "\\imp\\Factory.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\ClassAccess.java"), 
				new File(root + "\\imp\\ClassAccess.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\AllocTest.java"), 
				new File(root + "\\direct\\AllocTest.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\StaticField.java"), 
				new File(root + "\\direct\\StaticField.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\StaticMethod.java"), 
				new File(root + "\\direct\\StaticMethod.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\OtherType1.java"), 
				new File(root + "\\direct\\OtherType1.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\OtherType2.java"), 
				new File(root + "\\direct\\OtherType2.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\NoOutsideReferences.java"), 
				new File(root + "\\ren\\NoOutsideReferences.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\SimpleRename.java"), 
				new File(root + "\\ren\\SimpleRename.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\SimpleNameConflict.java"), 
				new File(root + "\\ren\\SimpleNameConflict.java"), 
				false)).run();
		(new FileCopy(
				new File(clean + "\\ComplexNameConflict.java"), 
				new File(root + "\\ren\\ComplexNameConflict.java"), 
				false)).run();
	}


	/**
	 *  The teardown method for JUnit 
	 */
	protected void tearDown() {
	}


	/**
	 *  Gets the FirstRename attribute of the TestRepackage object 
	 *
	 *@param  step  Description of Parameter 
	 *@return       The FirstRename value 
	 */
	private String getFirstRename(String step) {
		String index = step.substring(4);
		int indexValue = 0;
		try {
			indexValue = Integer.parseInt(index);
		}
		catch (NumberFormatException nfe) {
		}

		if (indexValue >= 4) {
			return "LocalizedObject";
		}
		else {
			return "NoOutsideReferences";
		}
	}


	/**
	 *  Gets the SecondRename attribute of the TestRepackage object 
	 *
	 *@param  step  Description of Parameter 
	 *@return       The SecondRename value 
	 */
	private String getSecondRename(String step) {
		String index = step.substring(4);
		int indexValue = 0;
		try {
			indexValue = Integer.parseInt(index);
		}
		catch (NumberFormatException nfe) {
		}

		if (indexValue >= 5) {
			return "TestState";
		}
		else {
			return "SimpleRename";
		}
	}


	/**
	 *  Gets the ThirdRename attribute of the TestRepackage object 
	 *
	 *@param  step  Description of Parameter 
	 *@param  dest  Description of Parameter 
	 *@return       The ThirdRename value 
	 */
	private String getThirdRename(String step, boolean dest) {
		String index = step.substring(4);
		int indexValue = 0;
		try {
			indexValue = Integer.parseInt(index);
		}
		catch (NumberFormatException nfe) {
		}

		if (indexValue >= 6) {
			if (dest) {
				return "AccessClassVar_ren";
			}
			return "AccessClassVar";
		}
		else {
			return "SimpleNameConflict";
		}
	}


	/**
	 *  Gets the FourthRename attribute of the TestRepackage object 
	 *
	 *@param  step  Description of Parameter 
	 *@param  dest  Description of Parameter 
	 *@return       The FourthRename value 
	 */
	private String getFourthRename(String step, boolean dest) {
		String index = step.substring(4);
		int indexValue = 0;
		try {
			indexValue = Integer.parseInt(index);
		}
		catch (NumberFormatException nfe) {
		}

		if (indexValue >= 7) {
			if (dest) {
				return "Associate_ren";
			}
			return "Associate";
		}
		else {
			return "ComplexNameConflict";
		}
	}


	/**
	 *  Gets the TestClassDir attribute of the TestRepackage object 
	 *
	 *@param  step  Description of Parameter 
	 *@return       The TestClassDir value 
	 */
	private String getTestClassDir(String step) {
		if (step.equals("step1")) {
			return "\\test";
		}
		else if (step.equals("step2")) {
			return "\\experiment";
		}

		return "";
	}


	/**
	 *  Description of the Method 
	 *
	 *@param  step  Description of Parameter 
	 */
	private void checkRelevantFiles(String step) {
		String check = this.check + "\\ut1\\" + step;

		//  COPY Files
		FileCompare.assertEquals("Comparing file TestClass in " + step, 
				new File(check + "\\TestClass.java"), 
				new File(root + getTestClassDir(step) + "\\TestClass.java"));
		FileCompare.assertEquals("Comparing file Associate in " + step, 
				new File(check + "\\Associate.java"), 
				new File(root + "\\imp\\Associate.java"));
		FileCompare.assertEquals("Comparing file AssociateTwo in " + step, 
				new File(check + "\\AssociateTwo.java"), 
				new File(root + "\\AssociateTwo.java"));
		FileCompare.assertEquals("Comparing file AccessClassVar in " + step, 
				new File(check + "\\AccessClassVar.java"), 
				new File(root + "\\imp\\AccessClassVar.java"));
		FileCompare.assertEquals("Comparing file TypeChecker in " + step, 
				new File(check + "\\TypeChecker.java"), 
				new File(root + "\\imp\\TypeChecker.java"));
		FileCompare.assertEquals("Comparing file ClassAccess in " + step, 
				new File(check + "\\ClassAccess.java"), 
				new File(root + "\\imp\\ClassAccess.java"));
		FileCompare.assertEquals("Comparing file Factory in " + step, 
				new File(check + "\\Factory.java"), 
				new File(root + "\\imp\\Factory.java"));
		FileCompare.assertEquals("Comparing file AllocTest in " + step, 
				new File(check + "\\AllocTest.java"), 
				new File(root + "\\direct\\AllocTest.java"));
		FileCompare.assertEquals("Comparing file StaticField in " + step, 
				new File(check + "\\StaticField.java"), 
				new File(root + "\\direct\\StaticField.java"));
		FileCompare.assertEquals("Comparing file StaticMethod in " + step, 
				new File(check + "\\StaticMethod.java"), 
				new File(root + "\\direct\\StaticMethod.java"));
		FileCompare.assertEquals("Comparing file OtherType1 in " + step, 
				new File(check + "\\OtherType1.java"), 
				new File(root + "\\direct\\OtherType1.java"));
		FileCompare.assertEquals("Comparing file OtherType2 in " + step, 
				new File(check + "\\OtherType2.java"), 
				new File(root + "\\direct\\OtherType2.java"));
		FileCompare.assertEquals("Comparing file " + getFirstRename(step) + " in " + step, 
				new File(check + "\\" + getFirstRename(step) + ".java"), 
				new File(root + "\\ren\\" + getFirstRename(step) + ".java"));
		FileCompare.assertEquals("Comparing file " + getSecondRename(step) + " in " + step, 
				new File(check + "\\" + getSecondRename(step) + ".java"), 
				new File(root + "\\ren\\" + getSecondRename(step) + ".java"));
		FileCompare.assertEquals("Comparing file " + getThirdRename(step, false) + " in " + step, 
				new File(check + "\\" + getThirdRename(step, true) + ".java"), 
				new File(root + "\\ren\\" + getThirdRename(step, false) + ".java"));
		FileCompare.assertEquals("Comparing file " + getFourthRename(step, false) + " in " + step, 
				new File(check + "\\" + getFourthRename(step, true) + ".java"), 
				new File(root + "\\ren\\" + getFourthRename(step, false) + ".java"));
	}
}
