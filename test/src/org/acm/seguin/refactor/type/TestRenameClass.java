/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.type;

import java.io.File;
import java.io.IOException;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import net.sourceforge.jrefactory.ast.ASTClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import net.sourceforge.jrefactory.ast.ASTTypeDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.GetTypeSummary;

/**
 *  Unit test for performing the rename class refactoring
 *
 *@author    Chris Seguin
 */
public class TestRenameClass extends DirSourceTestCase {
	/**
	 *  Constructor for the TestRenameClass object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestRenameClass(String name)
	{
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException  Description of Exception
	 */
	public void test01() throws IOException
	{
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\ren");
		File impDir = new File(root + "\\imp");

		(new FileCopy(
				new File(cleanDir, "ConflictClass.java"),
				new File(destDir, "ConflictClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "BaseClass.java"),
				new File(destDir, "BaseClass.java"), false)).run();

		(new SummaryTraversal(root)).run();
		RenameClassRefactoring rcf = new RenameClassRefactoring();
		rcf.setDirectory(destDir.getCanonicalPath());
		rcf.setOldClassName("BaseClass");
		rcf.setNewClassName("ConflictClass");

		boolean exceptionThrown = false;
		try {
			rcf.run();
		}
		catch (RefactoringException re) {
			exceptionThrown = true;
		}

		(new File(destDir, "BaseClass.java")).delete();
		(new File(destDir, "ConflictClass.java")).delete();

		assertTrue("Did not complain about the name conflicts", exceptionThrown);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 *@exception  IOException           Description of Exception
	 */
	public void test02() throws RefactoringException, IOException
	{
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\ren");
		File checkDir = new File(check + "\\ut1\\step8");

		(new FileCopy(
				new File(cleanDir, "ChildExtensionClass.java"),
				new File(destDir, "ChildExtensionClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "BaseClass.java"),
				new File(destDir, "BaseClass.java"), false)).run();

		(new SummaryTraversal(root)).run();
		RenameClassRefactoring rcf = new RenameClassRefactoring();
		rcf.setDirectory(destDir.getCanonicalPath());
		rcf.setOldClassName("BaseClass");
		rcf.setNewClassName("RenamedClass");

		rcf.run();

		FileCompare.assertEquals("RenamedClass class in error",
				new File(checkDir, "RenamedClass.java"),
				new File(destDir, "RenamedClass.java"));

		FileCompare.assertEquals("ChildExtensionClass class in error",
				new File(checkDir, "ChildExtensionClass.java"),
				new File(destDir, "ChildExtensionClass.java"));

		(new File(destDir, "RenamedClass.java")).delete();
		(new File(destDir, "ChildExtensionClass.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 *@exception  IOException           Description of Exception
	 */
	public void test03() throws RefactoringException, IOException
	{
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\ren");
		File impDir = new File(root + "\\imp");
		File conflictDir = new File(root + "\\conflict");
		File checkDir = new File(check + "\\ut1\\step9");

		(new FileCopy(
				new File(cleanDir, "ChildExtensionClass.java"),
				new File(destDir, "ChildExtensionClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "BaseClass.java"),
				new File(destDir, "BaseClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "conflict_ConflictClass.java"),
				new File(conflictDir, "ConflictClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ImporterClass.java"),
				new File(impDir, "ImporterClass.java"), false)).run();

		(new SummaryTraversal(root)).run();
		RenameClassRefactoring rcf = new RenameClassRefactoring();
		rcf.setDirectory(destDir.getCanonicalPath());
		rcf.setOldClassName("BaseClass");
		rcf.setNewClassName("RenamedClass");

		rcf.run();

		FileCompare.assertEquals("RenamedClass class in error",
				new File(checkDir, "RenamedClass.java"),
				new File(destDir, "RenamedClass.java"));

		FileCompare.assertEquals("ChildExtensionClass class in error",
				new File(checkDir, "ChildExtensionClass.java"),
				new File(destDir, "ChildExtensionClass.java"));

		FileCompare.assertEquals("ConflictClass class in error",
				new File(checkDir, "ConflictClass.java"),
				new File(conflictDir, "ConflictClass.java"));

		FileCompare.assertEquals("ImporterClass class in error",
				new File(checkDir, "ImporterClass.java"),
				new File(impDir, "ImporterClass.java"));

		(new File(destDir, "RenamedClass.java")).delete();
		(new File(destDir, "ChildExtensionClass.java")).delete();
		(new File(conflictDir, "ConflictClass.java")).delete();
		(new File(impDir, "ImporterClass.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 *@exception  IOException           Description of Exception
	 */
	public void test04() throws RefactoringException, IOException
	{
		File cleanDir = new File(clean + "\\ren");
		File renDir = new File(root + "\\ren");
		File refDir = new File(root + "\\ref");
		refDir.mkdirs();
		File otherDir = new File(root + "\\other");
		otherDir.mkdirs();
		File checkDir = new File(check + "\\ut1\\step25");

		(new File(renDir, "NewClass.java")).delete();
		(new FileCopy(
				new File(cleanDir, "OldClass.java"),
				new File(renDir, "OldClass.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "UsedClass.java"),
				new File(renDir, "UsedClass.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "NearMiss.java"),
				new File(renDir, "NearMiss.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "other_NearMiss.java"),
				new File(otherDir, "NearMiss.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "ImportStaticFieldRef.java"),
				new File(refDir, "ImportStaticFieldRef.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportStaticMethodRef.java"),
				new File(refDir, "ImportStaticMethodRef.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportAllocation.java"),
				new File(refDir, "ImportAllocation.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportCast.java"),
				new File(refDir, "ImportCast.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportLocalVar.java"),
				new File(refDir, "ImportLocalVar.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportParameter.java"),
				new File(refDir, "ImportParameter.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportField.java"),
				new File(refDir, "ImportField.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportReturn.java"),
				new File(refDir, "ImportReturn.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportException.java"),
				new File(refDir, "ImportException.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportExtends.java"),
				new File(refDir, "ImportExtends.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportImplements.java"),
				new File(refDir, "ImportImplements.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportConstructor.java"),
				new File(refDir, "ImportConstructor.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportNotUse.java"),
				new File(refDir, "ImportNotUse.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "FalseStatic.java"),
				new File(refDir, "FalseStatic.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportAnonymous.java"),
				new File(refDir, "ImportAnonymous.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportAnonymousField.java"),
				new File(refDir, "ImportAnonymousField.java"), false)).run();
		(new FileCopy(
				new File(cleanDir, "ImportClassAccess.java"),
				new File(refDir, "ImportClassAccess.java"), false)).run();

		(new SummaryTraversal(root)).run();
		RenameClassRefactoring rcf = new RenameClassRefactoring();
		rcf.setDirectory(renDir.getCanonicalPath());
		rcf.setOldClassName("OldClass");
		rcf.setNewClassName("NewClass");

		rcf.run();

		FileCompare.assertEquals("NewClass class in error",
				new File(checkDir, "NewClass.java"),
				new File(renDir, "NewClass.java"));
		FileCompare.assertEquals("ImportStaticFieldRef class in error",
				new File(checkDir, "ImportStaticFieldRef.java"),
				new File(refDir, "ImportStaticFieldRef.java"));
		FileCompare.assertEquals("ImportStaticMethodRef class in error",
				new File(checkDir, "ImportStaticMethodRef.java"),
				new File(refDir, "ImportStaticMethodRef.java"));
		FileCompare.assertEquals("ImportAllocation class in error",
				new File(checkDir, "ImportAllocation.java"),
				new File(refDir, "ImportAllocation.java"));
		FileCompare.assertEquals("ImportCast class in error",
				new File(checkDir, "ImportCast.java"),
				new File(refDir, "ImportCast.java"));
		FileCompare.assertEquals("ImportLocalVar class in error",
				new File(checkDir, "ImportLocalVar.java"),
				new File(refDir, "ImportLocalVar.java"));
		FileCompare.assertEquals("ImportParameter class in error",
				new File(checkDir, "ImportParameter.java"),
				new File(refDir, "ImportParameter.java"));
		FileCompare.assertEquals("ImportField class in error",
				new File(checkDir, "ImportField.java"),
				new File(refDir, "ImportField.java"));
		FileCompare.assertEquals("ImportReturn class in error",
				new File(checkDir, "ImportReturn.java"),
				new File(refDir, "ImportReturn.java"));
		FileCompare.assertEquals("ImportException class in error",
				new File(checkDir, "ImportException.java"),
				new File(refDir, "ImportException.java"));
		FileCompare.assertEquals("ImportExtends class in error",
				new File(checkDir, "ImportExtends.java"),
				new File(refDir, "ImportExtends.java"));
		FileCompare.assertEquals("ImportImplements class in error",
				new File(checkDir, "ImportImplements.java"),
				new File(refDir, "ImportImplements.java"));
		FileCompare.assertEquals("ImportConstructor class in error",
				new File(checkDir, "ImportConstructor.java"),
				new File(refDir, "ImportConstructor.java"));
		FileCompare.assertEquals("ImportNotUse class in error",
				new File(checkDir, "ImportNotUse.java"),
				new File(refDir, "ImportNotUse.java"));
		FileCompare.assertEquals("FalseStatic class in error",
				new File(checkDir, "FalseStatic.java"),
				new File(refDir, "FalseStatic.java"));
		FileCompare.assertEquals("ImportAnonymous class in error",
				new File(checkDir, "ImportAnonymous.java"),
				new File(refDir, "ImportAnonymous.java"));
		FileCompare.assertEquals("ImportAnonymousField class in error",
				new File(checkDir, "ImportAnonymousField.java"),
				new File(refDir, "ImportAnonymousField.java"));
		FileCompare.assertEquals("ImportClassAccess class in error",
				new File(checkDir, "ImportClassAccess.java"),
				new File(refDir, "ImportClassAccess.java"));

		(new File(renDir, "NewClass.java")).delete();
		(new File(renDir, "UsedClass.java")).delete();
		(new File(renDir, "NearMiss.java")).delete();

		(new File(otherDir, "NearMiss.java")).delete();

		(new File(refDir, "ImportStaticFieldRef.java")).delete();
		(new File(refDir, "ImportStaticMethodRef.java")).delete();
		(new File(refDir, "ImportAllocation.java")).delete();
		(new File(refDir, "ImportCast.java")).delete();
		(new File(refDir, "ImportLocalVar.java")).delete();
		(new File(refDir, "ImportParameter.java")).delete();
		(new File(refDir, "ImportField.java")).delete();
		(new File(refDir, "ImportReturn.java")).delete();
		(new File(refDir, "ImportException.java")).delete();
		(new File(refDir, "ImportExtends.java")).delete();
		(new File(refDir, "ImportImplements.java")).delete();
		(new File(refDir, "ImportConstructor.java")).delete();
		(new File(refDir, "ImportNotUse.java")).delete();
		(new File(refDir, "FalseStatic.java")).delete();
		(new File(refDir, "ImportAnonymous.java")).delete();
		(new File(refDir, "ImportAnonymousField.java")).delete();
		(new File(refDir, "ImportClassAccess.java")).delete();
	}
}
