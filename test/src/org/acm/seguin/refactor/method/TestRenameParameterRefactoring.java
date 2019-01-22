/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.MethodSummary;
import org.acm.seguin.summary.PackageSummary;
import org.acm.seguin.summary.ParameterSummary;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.GetTypeSummary;
import org.acm.seguin.summary.query.MethodQuery;
import org.acm.seguin.summary.query.ParameterQuery;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class TestRenameParameterRefactoring extends DirSourceTestCase {
	/**
	 *  Constructor for the TestRenameParameterRefactoring object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestRenameParameterRefactoring(String name)
	{
		super(name);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test01() throws RefactoringException
	{
		RenameParameterRefactoring rpr = new RenameParameterRefactoring();
		PackageSummary packageSummary = PackageSummary.getPackageSummary("method.param");
		TypeSummary typeSummary = GetTypeSummary.query(packageSummary, "TestRenameParamSample");
		MethodSummary methodSummary = MethodQuery.find(typeSummary, "function");
		ParameterSummary paramSummary = ParameterQuery.query(methodSummary, "paramName");
		rpr.setMethodSummary(methodSummary);
		rpr.setParameterSummary(paramSummary);
		rpr.setNewName("height");

		rpr.run();

		//  Check things out
		File check = new File(this.check + "\\ut3\\step40");
		File dest = new File(root + "\\method\\param");

		FileCompare.assertEquals("TestRenameParamSample is incorrect",
				new File(check, "TestRenameParamSample.java"),
				new File(dest, "TestRenameParamSample.java"));
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException
	{
		RenameParameterRefactoring rpr = new RenameParameterRefactoring();
		PackageSummary packageSummary = PackageSummary.getPackageSummary("method.param");
		TypeSummary typeSummary = GetTypeSummary.query(packageSummary, "TestRenameParamSample");
		MethodSummary methodSummary = MethodQuery.find(typeSummary, "TestRenameParamSample");
		ParameterSummary paramSummary = ParameterQuery.query(methodSummary, "paramName");
		rpr.setMethodSummary(methodSummary);
		rpr.setParameterSummary(paramSummary);
		rpr.setNewName("height");

		rpr.run();

		//  Check things out
		File check = new File(this.check + "\\ut3\\step41");
		File dest = new File(root + "\\method\\param");

		FileCompare.assertEquals("TestRenameParamSample is incorrect",
				new File(check, "TestRenameParamSample.java"),
				new File(dest, "TestRenameParamSample.java"));
	}


	public void test03() throws RefactoringException
	{
		RenameParameterRefactoring rpr = new RenameParameterRefactoring();
		PackageSummary packageSummary = PackageSummary.getPackageSummary("method.param");
		TypeSummary typeSummary = GetTypeSummary.query(packageSummary, "TestRenameParamSample");
		MethodSummary methodSummary = MethodQuery.find(typeSummary, "useParam");
		ParameterSummary paramSummary = ParameterQuery.query(methodSummary, "paramName");
		rpr.setMethodSummary(methodSummary);
		rpr.setParameterSummary(paramSummary);
		rpr.setNewName("height");

		rpr.run();

		//  Check things out
		File check = new File(this.check + "\\ut3\\step42");
		File dest = new File(root + "\\method\\param");

		FileCompare.assertEquals("TestRenameParamSample is incorrect",
				new File(check, "TestRenameParamSample.java"),
				new File(dest, "TestRenameParamSample.java"));
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp()
	{
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\method\\param");

		(new FileCopy(
				new File(cleanDir, "method_param_TestRenameParamSample.java"),
				new File(destDir, "TestRenameParamSample.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown()
	{
		File destDir = new File(root + "\\method\\param");
		(new File(destDir, "TestRenameParamSample.java")).delete();
	}
}
