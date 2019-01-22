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
 *  Unit test for extracting an interface
 *
 *@author     Grant Watson
 *@created    November 27, 2000
 */
public class TestExtractInterface extends DirSourceTestCase {
	/**
	 *  Constructor for the TestExtractInterface object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestExtractInterface(String name)
	{
		super(name);
	}


	/**
	 *  This test ensures that a new interface is extracted correctly from a
	 *  single class that extends nothing and implements nothing. All methods in
	 *  this class are public.
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test01() throws RefactoringException
	{
		ExtractInterfaceRefactoring eir = new ExtractInterfaceRefactoring();
		eir.addImplementingClass("testextractinterface", "Supplier1");
		eir.setInterfaceName("Supplier1Only");
		eir.run();
		File destDir = new File(root + "\\testextractinterface");
		File checkDir = new File(check + "\\testextractinterface");
		FileCompare.assertEquals("Extracted interface Supplier1Only in error",
				new File(checkDir, "Supplier1Only.java"),
				new File(destDir, "Supplier1Only.java"));
		FileCompare.assertEquals("Original class Supplier in error",
				new File(checkDir, "Supplier1.java"),
				new File(destDir, "Supplier1.java"));
	}


	/**
	 *  This test ensures that a new interface is extracted correctly from a
	 *  single class that extends something but implements nothing. All methods
	 *  in this class are public.
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException
	{
		ExtractInterfaceRefactoring eir = new ExtractInterfaceRefactoring();
		eir.addImplementingClass("testextractinterface", "Supplier2");
		eir.setInterfaceName("Supplier2Only");
		eir.run();
		File destDir = new File(root + "\\testextractinterface");
		File checkDir = new File(check + "\\testextractinterface");
		FileCompare.assertEquals("Extracted interface Supplier2Only in error",
				new File(checkDir, "Supplier2Only.java"),
				new File(destDir, "Supplier2Only.java"));
		FileCompare.assertEquals("Original class Supplier in error",
				new File(checkDir, "Supplier2.java"),
				new File(destDir, "Supplier2.java"));
	}


	/**
	 *  This test ensures that a new interface is extracted correctly from a
	 *  single class that extends nothing but implements something. All methods
	 *  in this class are public.
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test03() throws RefactoringException
	{
		ExtractInterfaceRefactoring eir = new ExtractInterfaceRefactoring();
		eir.addImplementingClass("testextractinterface", "Supplier3");
		eir.setInterfaceName("Supplier3Only");
		eir.run();
		File destDir = new File(root + "\\testextractinterface");
		File checkDir = new File(check + "\\testextractinterface");
		FileCompare.assertEquals("Extracted interface Supplier3Only in error",
				new File(checkDir, "Supplier3Only.java"),
				new File(destDir, "Supplier3Only.java"));
		FileCompare.assertEquals("Original class Supplier in error",
				new File(checkDir, "Supplier3.java"),
				new File(destDir, "Supplier3.java"));
	}


	/**
	 *  This test ensures that a new interface is extracted correctly from a
	 *  single class that extends something and implements something. All methods
	 *  in this class are public.
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test04() throws RefactoringException
	{
		ExtractInterfaceRefactoring eir = new ExtractInterfaceRefactoring();
		eir.addImplementingClass("testextractinterface", "Supplier4");
		eir.setInterfaceName("Supplier4Only");
		eir.run();
		File destDir = new File(root + "\\testextractinterface");
		File checkDir = new File(check + "\\testextractinterface");
		FileCompare.assertEquals("Extracted interface Supplier4Only in error",
				new File(checkDir, "Supplier4Only.java"),
				new File(destDir, "Supplier4Only.java"));
		FileCompare.assertEquals("Original class Supplier in error",
				new File(checkDir, "Supplier4.java"),
				new File(destDir, "Supplier4.java"));
	}


	/**
	 *  This test ensures that a new interface is extracted correctly when two
	 *  classes are specified.
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test05() throws RefactoringException
	{
		ExtractInterfaceRefactoring eir = new ExtractInterfaceRefactoring();
		eir.addImplementingClass("testextractinterface", "Employee");
		eir.addImplementingClass("testextractinterface", "Customer");
		eir.setInterfaceName("Person");
		eir.run();
		File destDir = new File(root + "\\testextractinterface");
		File checkDir = new File(check + "\\testextractinterface");
		FileCompare.assertEquals("Extracted interface Person in error",
				new File(checkDir, "Person.java"),
				new File(destDir, "Person.java"));
		FileCompare.assertEquals("Original file Employee in error",
				new File(checkDir, "Employee.java"),
				new File(destDir, "Employee.java"));
		FileCompare.assertEquals("Original file Customer in error",
				new File(checkDir, "Customer.java"),
				new File(destDir, "Customer.java"));

		(new File(destDir, "Person.java")).delete();
		(new File(destDir, "Customer.java")).delete();
		(new File(destDir, "Employee.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test06() throws RefactoringException
	{
		ExtractInterfaceRefactoring eir = new ExtractInterfaceRefactoring();
		eir.addImplementingClass("testextractinterface", "Employee");
		eir.addImplementingClass("testextractinterface", "Customer");
		eir.setInterfaceName("Person");
		eir.setPackageName("");
		eir.run();
		File destDir = new File(root + "\\testextractinterface");
		File checkDir = new File(check + "\\ut1\\step40");
		FileCompare.assertEquals("Extracted interface Person in error",
				new File(checkDir, "Person.java"),
				new File(root, "Person.java"));
		FileCompare.assertEquals("Original file Employee in error",
				new File(checkDir, "Employee.java"),
				new File(destDir, "Employee.java"));
		FileCompare.assertEquals("Original file Customer in error",
				new File(checkDir, "Customer.java"),
				new File(destDir, "Customer.java"));
		(new File(root, "Person.java")).delete();
		(new File(destDir, "Customer.java")).delete();
		(new File(destDir, "Employee.java")).delete();
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp()
	{
		File cleanDir = new File(clean + "\\testextractinterface");
		File destDir = new File(root + "\\testextractinterface");
		File impDir = new File(root + "\\imp");

		(new FileCopy(
				new File(cleanDir, "Employee.java"),
				new File(destDir, "Employee.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "Customer.java"),
				new File(destDir, "Customer.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "Supplier1.java"),
				new File(destDir, "Supplier1.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "Supplier2.java"),
				new File(destDir, "Supplier2.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "Supplier3.java"),
				new File(destDir, "Supplier3.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "Supplier4.java"),
				new File(destDir, "Supplier4.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  Cleanup the working directory
	 */
	protected void tearDown() { }


	/**
	 *  Description of the Method
	 *
	 *@param  args  Description of Parameter
	 */
	public static void main(String args[])
	{
		//junit.swingui.LoadingTestRunner runner = new junit.swingui.LoadingTestRunner();
		junit.swingui.TestRunner runner = new junit.swingui.TestRunner();
		runner.main(new String[0]);
	}
}
