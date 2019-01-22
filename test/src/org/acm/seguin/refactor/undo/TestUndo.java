/*
 * Author:  Chris Seguin
 *
 * This software has been developed under the copyleft
 * rules of the GNU General Public License.  Please
 * consult the GNU General Public License for more
 * details about use and distribution of this software.
 */
package org.acm.seguin.refactor.undo;
import java.io.File;
import java.io.IOException;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.refactor.RefactoringFactory;
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
import org.acm.seguin.refactor.type.RenameClassRefactoring;

/**
 *  JUnit tests for Undo operation
 *
 *@author    Chris Seguin
 */
public class TestUndo extends DirSourceTestCase {
	/**
	 *  Constructor for the TestUndo object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestUndo(String name) {
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 *@exception  IOException           Description of Exception
	 */
	public void test02() throws RefactoringException, IOException {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\ren");
		File checkDir = new File(check + "\\ut1\\step30");

		(new FileCopy(
				new File(cleanDir, "ChildExtensionClass.java"),
				new File(destDir, "ChildExtensionClass.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "BaseClass.java"),
				new File(destDir, "BaseClass.java"), false)).run();

		(new SummaryTraversal(root)).run();
		RenameClassRefactoring rcf = RefactoringFactory.get().renameClass();
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

		UndoStack.get().undo();

		FileCompare.assertEquals("BaseClass class in error",
				new File(checkDir, "BaseClass.java"),
				new File(destDir, "BaseClass.java"));

		FileCompare.assertEquals("ChildExtensionClass class in error",
				new File(checkDir, "undone_ChildExtensionClass.java"),
				new File(destDir, "ChildExtensionClass.java"));

		(new File(destDir, "BaseClass.java")).delete();
		(new File(destDir, "ChildExtensionClass.java")).delete();
	}
}
