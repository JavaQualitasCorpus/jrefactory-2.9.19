/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.refactor.method;

import java.io.*;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.LocalVariableSummary;
import org.acm.seguin.summary.TypeDeclSummary;

/**
 *  Unit tests for the extract method refactoring
 *
 *@author    Chris Seguin
 */
public class TestExtractMethod extends DirSourceTestCase {
	/**
	 *  Constructor for the TestExtractMethod object
	 *
	 *@param  init  Description of Parameter
	 */
	public TestExtractMethod(String init)
	{
		super(init);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test01() throws IOException, RefactoringException
	{
		check("method_ExtractMethod.java", 8, 14, 20);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test02() throws IOException, RefactoringException
	{
		check("method_ExtractMethod.java", 21, 22, 21);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test03() throws IOException, RefactoringException
	{
		check("method_ExtractMethod.java", 34, 37, 22);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test04() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 16, 18, 23);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test05() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 17, 18, 24);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test06() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 17, 17, 25);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test07() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 22, 22, 26);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test08() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 34, 34, 27);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test09() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 32, 36, 28);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test10() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 32, 35, 29);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test11() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 33, 36, 30);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test12() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 36, 36, 31);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test13() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 30, 30, 32);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test14() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestTwo.java", 43, 45, 33);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test15() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestThree.java", 14, 15, 34);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test17() throws IOException, RefactoringException
	{
		check("ExtendedExtractMethodTester.java", 21, 26, 46);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test18() throws IOException, RefactoringException
	{
		check("ExtendedExtractMethodTester.java", 42, 42, 47);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test19() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFour.java", 8, 11, 38);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test20() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFour.java", 8, 10, 39);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test21() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFour.java", 20, 22, 50);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test22() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFour.java", 33, 35, 51);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test23() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFour.java", 45, 46, 52);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test24() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFive.java", 10, 17, 53);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test25() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestFive.java", 30, 33, 54);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test26() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestSix.java", 7, 8, 55);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test27() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestSix.java", 25, 26, 56);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test28() throws IOException, RefactoringException
	{
		check("method_ExtractMethodTestSeven.java", 8, 17, 57);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	public void test16() throws IOException, RefactoringException
	{
		TypeDeclSummary tds = new TypeDeclSummary(null, null, "String");
		LocalVariableSummary lvs = new LocalVariableSummary(null, tds, "keyword");
		check("ExtendedExtractMethodTester.java", 6, 12, 45, lvs);
	}


	/**
	 *  Description of the Method
	 *
	 *@param  filename                  Description of Parameter
	 *@param  startLine                 Description of Parameter
	 *@param  endLine                   Description of Parameter
	 *@param  code                      Description of Parameter
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	protected void check(String filename, int startLine, int endLine, int code) throws IOException, RefactoringException
	{
		check(filename, startLine, endLine, code, null);
	}


	/**
	 *  A unit test for JUnit
	 *
	 *@param  filename                  Description of Parameter
	 *@param  startLine                 Description of Parameter
	 *@param  endLine                   Description of Parameter
	 *@param  code                      Description of Parameter
	 *@param  returnType                Description of Parameter
	 *@exception  IOException           Description of Exception
	 *@exception  RefactoringException  Description of Exception
	 */
	protected void check(String filename, int startLine, int endLine, int code, Object returnType) throws IOException, RefactoringException
	{
		FileReader fileReader = new FileReader(clean + File.separator + filename);
		BufferedReader input = new BufferedReader(fileReader);

		StringBuffer fullFile = new StringBuffer();
		StringBuffer selection = new StringBuffer();

		String line = input.readLine();
		int count = 1;
		while (line != null) {
			fullFile.append(line);
			fullFile.append("\n");
			if ((count >= startLine) && (count <= endLine)) {
				selection.append(line);
				selection.append("\n");
			}
			line = input.readLine();
			count++;
		}

		input.close();

		ExtractMethodRefactoring em = new ExtractMethodRefactoring();
		em.setFullFile(fullFile.toString());
		em.setSelection(selection.toString());
		em.setMethodName("extractedMethod");
		if (returnType != null) {
			em.setReturnType(returnType);
		}

		em.run();

		String revised = em.getFullFile();

		File dest = new File(root + File.separator + "ExtractMethod.java");
		FileWriter fileWriter = new FileWriter(dest);
		PrintWriter output = new PrintWriter(fileWriter);
		output.print(revised);
		output.flush();
		output.close();
		fileWriter.close();

		File checkCode = new File(check + "/ut3/step" + code + "/ExtractMethod.java");
		FileCompare.assertEquals("ExtractMethod is incorrect",
				checkCode,
				dest);
	}
}
