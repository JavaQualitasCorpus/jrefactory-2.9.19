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
import java.util.LinkedList;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.SummaryTraversal;
import org.acm.seguin.summary.TypeSummary;
import org.acm.seguin.summary.query.GetTypeSummary;

/**
 *  Additional unit test for adding a child
 *
 *@author    Chris Seguin
 */
public class TestAddChildTwo extends DirSourceTestCase {
	/**
	 *  Constructor for the TestAddChildTwo object
	 *
	 *@param  name  Description of Parameter
	 */
	public TestAddChildTwo(String name)
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
		AddChildRefactoring aap = new AddChildRefactoring();
		aap.setParentClass("abstracter", "ClassThree");
		aap.setChildName("ClassFour");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step23");

		FileCompare.assertEquals("ClassFour class in error",
				new File(checkDir, "ClassFour.java"),
				new File(destDir, "ClassFour.java"));

		(new File(destDir, "ClassFour.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws RefactoringException
	{
		TypeSummary type = GetTypeSummary.query("abstracter", "ClassThree");
		AbstractMethodFinder finder = new AbstractMethodFinder(type, true);
		finder.loadInterfaceMethods();
		LinkedList list = finder.getList();

		assertEquals("List length is correct", 6, list.size());

		finder.filter(type);
		assertEquals("List length is correct", 5, list.size());
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test03() throws RefactoringException
	{
		AddChildRefactoring aap = new AddChildRefactoring();
		aap.setParentClass("javax.swing.text", "AbstractDocument");
		aap.setChildName("XMLDocument");
		aap.setPackageName("abstracter");

		aap.run();

		File destDir = new File(root + "\\abstracter");
		File checkDir = new File(check + "\\ut1\\step24");

		FileCompare.assertEquals("XMLDocument class in error",
				new File(checkDir, "XMLDocument.java"),
				new File(destDir, "XMLDocument.java"));

		(new File(destDir, "XMLDocument.java")).delete();
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test04() throws RefactoringException
	{
		AddChildRefactoring aap = new AddChildRefactoring();
		aap.setParentClass("javax.swing.text", "AbstractDocument");
		aap.setChildName("XMLDocument");
		aap.setPackageName("");

		aap.run();

		File destDir = new File(root);
		File checkDir = new File(check + "\\ut1\\step26");

		FileCompare.assertEquals("XMLDocument class in error",
				new File(checkDir, "XMLDocument.java"),
				new File(destDir, "XMLDocument.java"));

		(new File(destDir, "XMLDocument.java")).delete();
	}


	/**
	 *  The JUnit setup method
	 */
	protected void setUp()
	{
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\abstracter");

		(new FileCopy(
				new File(cleanDir, "child_InterfaceOne.java"),
				new File(destDir, "InterfaceOne.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "child_InterfaceTwo.java"),
				new File(destDir, "InterfaceTwo.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "child_InterfaceThree.java"),
				new File(destDir, "InterfaceThree.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "child_ClassOne.java"),
				new File(destDir, "ClassOne.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "child_ClassTwo.java"),
				new File(destDir, "ClassTwo.java"), false)).run();

		(new FileCopy(
				new File(cleanDir, "child_ClassThree.java"),
				new File(destDir, "ClassThree.java"), false)).run();

		(new SummaryTraversal(root)).run();
	}


	/**
	 *  The teardown method for JUnit
	 */
	protected void tearDown()
	{
		File destDir = new File(root + "\\abstracter");

		(new File(destDir, "InterfaceOne.java")).delete();
		(new File(destDir, "InterfaceTwo.java")).delete();
		(new File(destDir, "InterfaceThree.java")).delete();
		(new File(destDir, "ClassOne.java")).delete();
		(new File(destDir, "ClassTwo.java")).delete();
		(new File(destDir, "ClassThree.java")).delete();
		(new File(destDir, "ClassFour.java")).delete();

	}
}
