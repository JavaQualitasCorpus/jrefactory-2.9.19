/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import org.acm.seguin.tools.builder.PrettyPrinter;
import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.pretty.sort.MultipleOrdering;
import org.acm.seguin.tools.RefactoryInstaller;
import junit.framework.*;


/**
 *  Unit tests for the pretty printer
 *
 *@author     Chris Seguin
 *@author     Mike Atkinson
 *@created    February 22, 2002
 */
public class TestPrettyPrinter extends DirSourceTestCase
{
    private File checkDir;
    private File cleanDir;
    private File destDir;


    /**
     *  Constructor for the TestPrettyPrinter object
     *
     *@param  name  Description of Parameter
     */
    public TestPrettyPrinter(String name)
    {
        super(name);
    }


    /**
     *  A unit test for JUnit
     */
    public void test01()
    {
        File dest = new File(destDir, "TestCStyleComments.java");
        (new FileCopy(
                new File(cleanDir, "test_TestCStyleComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCStyleComments.java in error",
                new File(checkDir, "TestComments7.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test02()
    {
        File dest = new File(destDir, "TestCStyleComments.java");
        (new FileCopy(
                new File(cleanDir, "test_TestCStyleComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_ALIGN_STAR);
        tppf.setCStyleIndent(2);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCStyleComments.java in error",
                new File(checkDir, "TestComments8.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test03()
    {
        File dest = new File(destDir, "TestCStyleComments.java");
        (new FileCopy(
                new File(cleanDir, "test_TestCStyleComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_ALIGN_BLANK);
        tppf.setCStyleIndent(2);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCStyleComments.java in error",
                new File(checkDir, "TestComments9.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test04()
    {
        File dest = new File(destDir, "TestCStyleComments.java");
        (new FileCopy(
                new File(cleanDir, "test_TestCStyleComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_ALIGN_STAR);
        tppf.setCStyleIndent(4);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCStyleComments.java in error",
                new File(checkDir, "TestComments10.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test05()
    {
        File dest = new File(destDir, "TestCStyleComments.java");
        (new FileCopy(
                new File(cleanDir, "test_TestCStyleComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_MAINTAIN_STAR);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCStyleComments.java in error",
                new File(checkDir, "TestComments11.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test06()
    {
        File dest = new File(destDir, "VariousTest.java");
        (new FileCopy(
                new File(cleanDir, "test_VariousTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(false);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.apply(dest);

        FileCompare.assertEquals("VariousTest.java in error",
                new File(checkDir, "VariousTest1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test07()
    {
        File dest = new File(destDir, "VariousTest.java");
        (new FileCopy(
                new File(cleanDir, "test_VariousTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(false);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.apply(dest);

        FileCompare.assertEquals("VariousTest.java in error",
                new File(checkDir, "VariousTest2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test08()
    {
        File dest = new File(destDir, "FinalUsage.java");
        (new FileCopy(
                new File(cleanDir, "FinalUsage.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("FinalUsage.java in error",
                new File(checkDir, "FinalUsage.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test09()
    {
        File dest = new File(destDir, "TestCastPrettyPrinter.java");
        (new FileCopy(
                new File(cleanDir, "TestCastPrettyPrinter.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCastSpace(true);
        tppf.setExpressionSpace(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCastPrettyPrinter.java in error",
                new File(checkDir, "TestCastPrettyPrinter1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test10()
    {
        File dest = new File(destDir, "TestCastPrettyPrinter.java");
        (new FileCopy(
                new File(cleanDir, "TestCastPrettyPrinter.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCastSpace(false);
        tppf.setExpressionSpace(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCastPrettyPrinter.java in error",
                new File(checkDir, "TestCastPrettyPrinter2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test11()
    {
        File dest = new File(destDir, "JavadocForInnerAndAnnonymous.java");
        (new FileCopy(
                new File(cleanDir, "JavadocForInnerAndAnnonymous.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDocumentNestedClasses(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JavadocForInnerAndAnnonymous.java in error",
                new File(checkDir, "JavadocForInnerAndAnnonymous1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test12()
    {
        File dest = new File(destDir, "JavadocForInnerAndAnnonymous.java");
        (new FileCopy(
                new File(cleanDir, "JavadocForInnerAndAnnonymous.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDocumentNestedClasses(false);
        tppf.apply(dest);

        FileCompare.assertEquals("JavadocForInnerAndAnnonymous.java in error",
                new File(checkDir, "JavadocForInnerAndAnnonymous2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test13()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(setter,getter,other)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00000.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test14()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(getter,setter,other)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00010.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test15()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(getter,other,setter)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00020.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test16()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(other,getter,setter)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00030.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test17()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(other,setter,getter)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00040.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test18()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(setter,other,getter)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00050.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test19()
    {
        String[] orderStrings = {
                "Type(Field,Main,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(setter,getter,other)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData00001.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test20()
    {
        File dest = new File(destDir, "VariableIndent.java");
        (new FileCopy(
                new File(cleanDir, "VariableIndent.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDynamicFieldSpacing(PrintData.DFS_ALWAYS);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableIndent.java in error",
                new File(checkDir, "VariableIndent.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test21()
    {
        File dest = new File(destDir, "VariableFormatTwo.java");
        (new FileCopy(
                new File(cleanDir, "VariableFormatTwo.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDynamicFieldSpacing(PrintData.DFS_ALWAYS);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableFormatTwo.java in error",
                new File(checkDir, "VariableFormatTwo.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test22()
    {
        String[] orderStrings = {
                "FieldInitializers()",
                "Type(Constructor,Method,NestedClass,NestedInterface,Field,Initializer)",
                "Class(Instance,Static)",
                "Protection(public)",
                "Method(setter,getter,other)"
                };

        File dest = new File(destDir, "FieldInitOrderTest.java");
        (new FileCopy(
                new File(cleanDir, "FieldInitOrderTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("FieldInitOrderTest.java in error",
                new File(checkDir, "FieldInitOrderTest.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test23()
    {
        File dest = new File(destDir, "BlockStyleTest.java");
        (new FileCopy(
                new File(cleanDir, "BlockStyleTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.apply(dest);

        FileCompare.assertEquals("BlockStyleTest.java in error",
                new File(checkDir, "BlockStyleTest1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test24()
    {
        File dest = new File(destDir, "BlockStyleTest.java");
        (new FileCopy(
                new File(cleanDir, "BlockStyleTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.apply(dest);

        FileCompare.assertEquals("BlockStyleTest.java in error",
                new File(checkDir, "BlockStyleTest2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test25()
    {
        File dest = new File(destDir, "BlockStyleTest.java");
        (new FileCopy(
                new File(cleanDir, "BlockStyleTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.apply(dest);

        FileCompare.assertEquals("BlockStyleTest.java in error",
                new File(checkDir, "BlockStyleTest3.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test26()
    {
        File dest = new File(destDir, "BlockStyleTest.java");
        (new FileCopy(
                new File(cleanDir, "BlockStyleTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.apply(dest);

        FileCompare.assertEquals("BlockStyleTest.java in error",
                new File(checkDir, "BlockStyleTest4.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test27()
    {
        File dest = new File(destDir, "JavadocStyleTest.java");
        (new FileCopy(
                new File(cleanDir, "JavadocStyleTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineJavadocs(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JavadocStyleTest.java in error",
                new File(checkDir, "JavadocStyleTest.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test28()
    {
        File dest = new File(destDir, "VariableIndent.java");
        (new FileCopy(
                new File(cleanDir, "pretty_VariableIndent.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setVariablesAlignWithBlock(true);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableIndent.java in error",
                new File(checkDir, "VariableIndent2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test29()
    {
        File dest = new File(destDir, "VariousTest.java");
        (new FileCopy(
                new File(cleanDir, "test_VariousTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(false);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setElseOnNewLine(false);
        tppf.apply(dest);

        FileCompare.assertEquals("VariousTest.java in error",
                new File(checkDir, "VariousTest3.java"),
                dest);
    }


    /**
     *  Unit test for pretty printer
     */
    public void test30()
    {
        File dest = new File(destDir, "VariousTest.java");
        (new FileCopy(
                new File(cleanDir, "test_VariousTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(true);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setElseOnNewLine(false);
        tppf.apply(dest);

        FileCompare.assertEquals("VariousTest.java in error",
                new File(checkDir, "VariousTest4.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test31()
    {
        File dest = new File(destDir, "SwitchClass.java");
        (new FileCopy(
                new File(cleanDir, "SwitchClass.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCaseIndent(0);
        tppf.apply(dest);

        FileCompare.assertEquals("SwitchClass.java in error",
                new File(checkDir, "SwitchClass1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test32()
    {
        File dest = new File(destDir, "SwitchClass.java");
        (new FileCopy(
                new File(cleanDir, "SwitchClass.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCaseIndent(2);
        tppf.apply(dest);

        FileCompare.assertEquals("SwitchClass.java in error",
                new File(checkDir, "SwitchClass2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test33()
    {
        String[] orderStrings = {
                "Alphabetical()"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData_a.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test34()
    {
        String[] orderStrings = {
                "Final(top)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData_ft.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test35()
    {
        String[] orderStrings = {
                "Final(bottom)"
                };

        File dest = new File(destDir, "SortTestData.java");
        (new FileCopy(
                new File(cleanDir, "SortTestData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("SortTestData.java in error",
                new File(checkDir, "SortTestData_fb.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test36()
    {
        String[] orderStrings = {};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(0);
        tppf.setSortTop(true);
        tppf.setEmptyBlockOnSingleLine(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData01.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test37()
    {
        String[] orderStrings = {"java", "javax"};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(0);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData02.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test38()
    {
        String[] orderStrings = {"java", "javax", "org.w3c.dom"};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(0);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData03.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test39()
    {
        String[] orderStrings = {"java", "javax", "org.w3c.dom"};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(1);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData04.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test40()
    {
        String[] orderStrings = {"java", "javax", "org.w3c.dom"};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(2);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData05.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test41()
    {
        String[] orderStrings = {"java", "javax", "org.w3c.dom"};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(3);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData06.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test42()
    {
        String[] orderStrings = {};

        File dest = new File(destDir, "SortImportData.java");
        (new FileCopy(
                new File(cleanDir, "SortImportData.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(3);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("SortImportData.java in error",
                new File(checkDir, "SortImportData07.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test43()
    {
        File dest = new File(destDir, "ParserTest.java");
        (new FileCopy(
                new File(cleanDir, "ParserTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("ParserTest.java in error",
                new File(checkDir, "ParserTest.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test44()
    {
        File dest = new File(destDir, "AddExtraSpaces.java");
        (new FileCopy(
                new File(cleanDir, "AddExtraSpaces.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.apply(dest);

        FileCompare.assertEquals("AddExtraSpaces.java in error",
                new File(checkDir, "AddExtraSpaces.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test45()
    {
        File dest = new File(destDir, "AddExtraSpaces.java");
        (new FileCopy(
                new File(cleanDir, "AddExtraSpaces.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setRemoveExcessBlocks(true);
        tppf.setForceBlock(false);
        tppf.setSortTop(true);
        tppf.apply(dest);

        FileCompare.assertEquals("AddExtraSpaces.java in error",
                new File(checkDir, "AddExtraSpaces2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test46()
    {
        File dest = new File(destDir, "C.java");
        (new FileCopy(
                new File(cleanDir, "C.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("C.java in error",
                new File(checkDir, "C.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test47()
    {
        File dest = new File(destDir, "TestArrayInitializerIndent.java");
        (new FileCopy(
                new File(cleanDir, "TestArrayInitializerIndent.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setEmptyBlockOnSingleLine(true);
        tppf.setIndentInInitializer(true);
        tppf.setBangSpace(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestArrayInitializerIndent.java in error",
                new File(checkDir, "TestArrayInitializerIndent.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test48()
    {
        File dest = new File(destDir, "D.java");
        (new FileCopy(
                new File(cleanDir, "D.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.setExceptionTag("exception");
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("D.java in error",
                new File(checkDir, "D1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test49()
    {
        File dest = new File(destDir, "D.java");
        (new FileCopy(
                new File(cleanDir, "D.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.setExceptionTag("throws");
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("D.java in error",
                new File(checkDir, "D2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test50()
    {
        File dest = new File(destDir, "D.java");
        (new FileCopy(
                new File(cleanDir, "D.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.setExceptionTag("throw");
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("D.java in error",
                new File(checkDir, "D3.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test51()
    {
        File dest = new File(destDir, "E.java");
        (new FileCopy(
                new File(cleanDir, "E.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setModifierOrder(PrintData.ALPHABETICAL_ORDER);
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("E.java in error",
                new File(checkDir, "E1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test52()
    {
        File dest = new File(destDir, "category.java");
        (new FileCopy(
                new File(cleanDir, "category.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("category.java in error",
                new File(checkDir, "category.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test53()
    {
        File dest = new File(destDir, "Item.java");
        (new FileCopy(
                new File(cleanDir, "Item.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("Item.java in error",
                new File(checkDir, "Item.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test54()
    {
        File dest = new File(destDir, "LineNumberingTest.java");
        (new FileCopy(
                new File(cleanDir, "LineNumberingTest.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("LineNumberingTest.java in error",
                new File(checkDir, "LineNumberingTest.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test55()
    {
        File dest = new File(destDir, "TestPackage.java");
        (new FileCopy(
                new File(cleanDir, "TestPackage.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("TestPackage.java in error",
                new File(checkDir, "TestPackage.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test56()
    {
        File dest = new File(destDir, "Correct.java");
        (new FileCopy(
                new File(cleanDir, "Correct.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("Correct.java in error",
                new File(checkDir, "Correct.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test57()
    {
        File dest = new File(destDir, "VenkatsError.java");
        (new FileCopy(
                new File(cleanDir, "VenkatsError.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("VenkatsError.java in error",
                new File(checkDir, "VenkatsError.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test58()
    {
        File dest = new File(destDir, "LoadingProblem.java");
        (new FileCopy(
                new File(cleanDir, "LoadingProblem.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("LoadingProblem.java in error",
                new File(checkDir, "LoadingProblem.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test59()
    {
        File dest = new File(destDir, "TestStrictFP.java");
        (new FileCopy(
                new File(cleanDir, "TestStrictFP.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("TestStrictFP.java in error",
                new File(checkDir, "TestStrictFP.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test60()
    {
        File dest = new File(destDir, "AnonymousClassFormatting.java");
        (new FileCopy(
                new File(cleanDir, "AnonymousClassFormatting.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("AnonymousClassFormatting.java in error",
                new File(checkDir, "AnonymousClassFormatting.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test61()
    {
        File dest = new File(destDir, "DirkTest.java");
        (new FileCopy(
                new File(cleanDir, "DirkTest.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("DirkTest.java in error",
                new File(checkDir, "DirkTest.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test62()
    {
        File dest = new File(destDir, "InitAnonClass.java");
        (new FileCopy(
                new File(cleanDir, "InitAnonClass.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("InitAnonClass.java in error",
                new File(checkDir, "InitAnonClass.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test63()
    {
        File dest = new File(destDir, "SwitchClass.java");
        (new FileCopy(
                new File(cleanDir, "SwitchClass.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("SwitchClass.java in error",
                new File(checkDir, "SwitchClass.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test64()
    {
        File dest = new File(destDir, "SingleLineComments.java");
        (new FileCopy(
                new File(cleanDir, "SingleLineComments.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("SingleLineComments.java in error",
                new File(checkDir, "SingleLineComments.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test65()
    {
        File dest = new File(destDir, "DoWhileTest.java");
        (new FileCopy(
                new File(cleanDir, "DoWhileTest.java"),
                dest, false)).run();

        String arg = dest.getPath();
        PrettyPrinter.prettyPrinter(arg, false);

        FileCompare.assertEquals("DoWhileTest.java in error",
                new File(checkDir, "DoWhileTest.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test66()
    {
        File dest = new File(destDir, "TestComments.java");
        (new FileCopy(
                new File(cleanDir, "TestComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(false);
        tppf.setSingleLineCommentOwnline(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestComments.java in error",
                new File(checkDir, "TestComments1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test67()
    {
        File dest = new File(destDir, "TestComments2.java");
        (new FileCopy(
                new File(cleanDir, "TestComments2.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.setSingleLineCommentOwnline(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestComments2.java in error",
                new File(checkDir, "TestComments2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test68()
    {
        File dest = new File(destDir, "TestComments3.java");
        (new FileCopy(
                new File(cleanDir, "TestComments3.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(false);
        tppf.setKeepAll(true);
        tppf.setSingleLineCommentOwnline(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestComments3.java in error",
                new File(checkDir, "TestComments3.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test69()
    {
        File dest = new File(destDir, "TestComments3.java");
        (new FileCopy(
                new File(cleanDir, "TestComments3.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(true);
        tppf.setKeepAll(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("TestComments4.java in error",
                new File(checkDir, "TestComments4.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test70()
    {
        File dest = new File(destDir, "TestComments3.java");
        (new FileCopy(
                new File(cleanDir, "TestComments3.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(true);
        tppf.setKeepAll(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setSingleLineCommentOwnlineCode(false);
        tppf.setSingleLineCommentSharedIncremental(false);
        tppf.setAbsoluteSpace(50);
        tppf.apply(dest);

        FileCompare.assertEquals("TestComments5.java in error",
                new File(checkDir, "TestComments5.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test71()
    {
        File dest = new File(destDir, "TestComments3.java");
        (new FileCopy(
                new File(cleanDir, "TestComments3.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(true);
        tppf.setKeepAll(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setSingleLineCommentOwnlineCode(false);
        tppf.setSingleLineCommentSharedIncremental(true);
        tppf.setAbsoluteSpace(50);
        tppf.apply(dest);

        FileCompare.assertEquals("TestComments6.java in error",
                new File(checkDir, "TestComments6.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test72()
    {
        File dest = new File(destDir, "TestCStyleComments.java");
        (new FileCopy(
                new File(cleanDir, "TestCStyleComments.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.setKeepAll(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setSingleLineCommentOwnlineCode(false);
        tppf.setSingleLineCommentSharedIncremental(true);
        tppf.setAbsoluteSpace(50);
        tppf.apply(dest);

        FileCompare.assertEquals("TestCStyleComments.java in error",
                new File(checkDir, "TestCStyleComments.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test73()
    {
        File dest = new File(destDir, "TestEscapedCharacters.java");
        (new FileCopy(
                new File(cleanDir, "TestEscapedCharacters.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setReformatComments(false);
        tppf.setKeepAll(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setSingleLineCommentOwnlineCode(false);
        tppf.setSingleLineCommentSharedIncremental(true);
        tppf.setAbsoluteSpace(50);
        tppf.apply(dest);

        FileCompare.assertEquals("TestEscapedCharacters.java in error",
                new File(checkDir, "TestEscapedCharacters.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test74()
    {
        File dest = new File(destDir, "VariableFormattingTester.java");
        (new FileCopy(
                new File(cleanDir, "VariableFormattingTester.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDynamicFieldSpacing(PrintData.DFS_ALWAYS);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableFormattingTester.java in error",
                new File(checkDir, "VariableFormattingTester1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test75()
    {
        File dest = new File(destDir, "VariableFormattingTester.java");
        (new FileCopy(
                new File(cleanDir, "VariableFormattingTester.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDynamicFieldSpacing(PrintData.DFS_NOT_WITH_JAVADOC);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableFormattingTester.java in error",
                new File(checkDir, "VariableFormattingTester2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test76()
    {
        File dest = new File(destDir, "VariableFormattingTester.java");
        (new FileCopy(
                new File(cleanDir, "VariableFormattingTester.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDynamicFieldSpacing(PrintData.DFS_NEVER);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableFormattingTester.java in error",
                new File(checkDir, "VariableFormattingTester3.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test77()
    {
        File dest = new File(destDir, "VariableFormattingTester.java");
        (new FileCopy(
                new File(cleanDir, "VariableFormattingTester.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setDynamicFieldSpacing(PrintData.DFS_ALIGN_EQUALS);
        tppf.apply(dest);

        FileCompare.assertEquals("VariableFormattingTester.java in error",
                new File(checkDir, "VariableFormattingTester4.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test78()
    {
        File dest = new File(destDir, "E.java");
        (new FileCopy(
                new File(cleanDir, "E.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setKeepErroneousJavadocTags(true);
        tppf.setSpaceInsideCast(true);
        tppf.setSpaceAfterMethod(true);
        tppf.setSpaceAroundOperators(false);
        tppf.setModifierOrder(PrintData.STANDARD_ORDER);
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("E.java in error",
                new File(checkDir, "E2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test79()
    {
        File dest = new File(destDir, "F.java");
        (new FileCopy(
                new File(cleanDir, "F.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setLineBeforeClassBody(true);
        tppf.setLineBeforeExtends(true);
        tppf.setLineBeforeImplements(true);
        tppf.setLineBeforeMultistatementMethodBody(true);
        tppf.setExtendsIndentation(5);
        tppf.setImplementsIndentation(6);
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("F.java in error",
                new File(checkDir, "F1.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test80()
    {
        File dest = new File(destDir, "F.java");
        (new FileCopy(
                new File(cleanDir, "F.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("F.java in error",
                new File(checkDir, "F2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test81()
    {
        File dest = new File(destDir, "TestArrayInitializerIndent.java");
        (new FileCopy(
                new File(cleanDir, "TestArrayInitializerIndent.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setEmptyBlockOnSingleLine(true);
        tppf.setIndentInInitializer(true);
        tppf.setArrayInitializerIndented(false);
        tppf.setAlignStarsWithSlash(true);
        tppf.setBangSpace(true);
        tppf.apply(dest);

        FileCompare.assertEquals("TestArrayInitializerIndent.java in error",
                new File(checkDir, "TestArrayInitializerIndent2.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test82()
    {
        File dest = new File(destDir, "JEditBug_692442_spaces.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_692442.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_692442_spaces.java in error",
                new File(checkDir, "JEditBug_692442_spaces.java"),
                dest);

        dest = new File(destDir, "JEditBug_692442_nospaces.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_692442.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(false);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_692442_nospaces.java in error",
                new File(checkDir, "JEditBug_692442_nospaces.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test83()
    {
        File dest = new File(destDir, "JEditBug_728002.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_728002.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_728002.java in error",
                new File(checkDir, "JEditBug_728002.java"),
                dest);
    }
    /**
     *  This test gives a parse error, as it should. It used to cause an
     *  infinite loop rather than an error, which was fixed by adding a
     *  LOOKAHEAD(2) to the ArgumentList() definition in java1_5.jjt
     *  JavaCC definition.
     *@since JRefactory 2.7.01
     */
/* 
    public void test84()
    {
        File dest = new File(destDir, "JEditBug_549394.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_549394.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_549394.java in error",
                new File(checkDir, "JEditBug_549394.java"),
                dest);
    }
*/

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test85()
    {
        File dest = new File(destDir, "JEditBug_551481.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_551481.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_551481.java in error",
                new File(checkDir, "JEditBug_551481.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test86()
    {
        File dest = new File(destDir, "JEditBug_570143.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_570143.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(true);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_570143.java in error",
                new File(checkDir, "JEditBug_570143.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test87()
    {
        File dest = new File(destDir, "Labels.java");
        (new FileCopy(
                new File(cleanDir, "Labels.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setInsertSpaceLocalVariables(true);
        tppf.setLinesAfterPackage(2);
        tppf.setMaintainNewlinesAroundImports(false);
        tppf.setSortTop(true);
        tppf.setLinesBeforeClass(2);
        tppf.setSpaceAfterKeyword(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Labels.java in error",
                new File(checkDir, "Labels.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test88()
    {
        String[] orderStrings = {
                "Type(Field,Constructor,Method,NestedClass,NestedInterface,Initializer)",
                "Class(Instance,Static)",
                "Protection(private,public)",
                "Method(other,setter,getter)"
                };

        File dest = new File(destDir, "Bug_703771.java");
        (new FileCopy(
                new File(cleanDir, "Bug_703771.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setMultipleOrdering(new MultipleOrdering(orderStrings));
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_703771.java in error",
                new File(checkDir, "Bug_703771.java"),
                dest);
    }
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test89()
    {

        File dest = new File(destDir, "Bug_693745.java");
        (new FileCopy(
                new File(cleanDir, "Bug_693745.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setIndentInInitializer(false);
        tppf.setBangSpace(true);
        tppf.setIndentBracesInitializer(false);   // testing these settings
        tppf.setArrayInitializerIndented(false);  // testing these settings
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_693745.java in error",
                new File(checkDir, "Bug_693745.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test90()
    {
        String[] orderStrings = {"java", "javax", "org.w3c", "org.apache", "com.sun"};

        File dest = new File(destDir, "Bug_693738.java");
        (new FileCopy(
                new File(cleanDir, "Bug_693738.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setImportSortImportant(orderStrings);
        tppf.setImportSortNeighbourhood(1);
        tppf.setSortTop(true);
        tppf.setEmptyBlockOnSingleLine(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_693738.java in error",
                new File(checkDir, "Bug_693738.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test91()
    {

        File dest = new File(destDir, "Bug_679496.java");
        (new FileCopy(
                new File(cleanDir, "Bug_679496.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_679496.java in error",
                new File(checkDir, "Bug_679496.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test92()
    {

        File dest = new File(destDir, "Bug_605626.java");
        (new FileCopy(
                new File(cleanDir, "Bug_605626.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setRemoveExcessBlocks(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_605626.java in error",
                new File(checkDir, "Bug_605626.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test93()
    {

        File dest = new File(destDir, "Bug_641473.java");
        (new FileCopy(
                new File(cleanDir, "Bug_641473.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_641473.java in error",
                new File(checkDir, "Bug_641473.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test94()
    {

        File dest = new File(destDir, "Bug_616419.java");
        (new FileCopy(
                new File(cleanDir, "Bug_616419.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setJavadocWordWrapMinimum(30);
        tppf.setJavadocWordWrapMaximum(50);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_616419.java in error",
                new File(checkDir, "Bug_616419.java"),
                dest);
    }

    /**
     *  Unit test for pretty printer
     *@since JRefactory 2.7.01
     */
    public void test95()
    {
        File dest = new File(destDir, "Bug_583264_c.java");
        (new FileCopy(
                new File(cleanDir, "Bug_583264.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(true);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_C);
        tppf.setElseOnNewLine(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_583264.java in error",
                new File(checkDir, "Bug_583264_c.java"),
                dest);
                
        dest = new File(destDir, "Bug_583264_emacs.java");
        (new FileCopy(
                new File(cleanDir, "Bug_583264.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(true);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_EMACS);
        tppf.setElseOnNewLine(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_583264.java in error",
                new File(checkDir, "Bug_583264_emacs.java"),
                dest);
                
        dest = new File(destDir, "Bug_583264_pascal.java");
        (new FileCopy(
                new File(cleanDir, "Bug_583264.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setForceBlock(true);
        tppf.setCodeBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setMethodBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setClassBlockStyle(PrintData.BLOCK_STYLE_PASCAL);
        tppf.setElseOnNewLine(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_583264.java in error",
                new File(checkDir, "Bug_583264_pascal.java"),
                dest);
                
    }
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.01
     */
    public void test96()
    {

        File dest = new File(destDir, "Bug_574070.java");
        (new FileCopy(
                new File(cleanDir, "Bug_574070.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_574070.java in error",
                new File(checkDir, "Bug_574070.java"),
                dest);
    }
   
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.02
     */
    public void test97()
    {

        File dest = new File(destDir, "Bug_758816.java");
        (new FileCopy(
                new File(cleanDir, "Bug_758816.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_758816.java in error",
                new File(checkDir, "Bug_758816.java"),
                dest);
    }
   
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.02
     */
    public void test98()
    {

        File dest = new File(destDir, "RFE_651809.java");
        (new FileCopy(
                new File(cleanDir, "RFE_651809.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_651809.java in error",
                new File(checkDir, "RFE_651809.java"),
                dest);
    }
    

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.03
     */
    public void test99()
    {
        File dest = new File(destDir, "Bug_761890.java");
        (new FileCopy(
                new File(cleanDir, "Bug_761890.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyleOwnline(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_761890.java in error",
                new File(checkDir, "Bug_761890_after.java"),
                dest);
                
        dest = new File(destDir, "Bug_761890.java");
        (new FileCopy(
                new File(cleanDir, "Bug_761890.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyleOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_761890.java in error",
                new File(checkDir, "Bug_761890_online.java"),
                dest);
        dest = new File(destDir, "Bug_761890.java");
        (new FileCopy(
                new File(cleanDir, "Bug_761890.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(true);
        tppf.setCStyleOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_761890.java in error",
                new File(checkDir, "Bug_761890_after2.java"),
                dest);
    }

    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test100() {
        File dest = new File(destDir, "Bug_500410.java");
        (new FileCopy(
                new File(cleanDir, "Bug_500410.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_500410.java in error",
                new File(checkDir, "Bug_500410.java"),
                dest);
    }
   
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test101() {
        File dest = new File(destDir, "Bug_539934.java");
        (new FileCopy(
                new File(cleanDir, "Bug_539934.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setLineUpParams(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_539934.java in error",
                new File(checkDir, "Bug_539934.java"),
                dest);
    }
   
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test102() {
        File dest = new File(destDir, "Bug_554735.java");
        (new FileCopy(
                new File(cleanDir, "Bug_554735.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_554735.java in error",
                new File(checkDir, "Bug_554735.java"),
                dest);
    }
   
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test103() {
        File dest = new File(destDir, "Bug_465568.java");
        (new FileCopy(
                new File(cleanDir, "Bug_465568.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_465568.java in error",
                new File(checkDir, "Bug_465568.java"),
                dest);
    }
   
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test104() {
        File dest = new File(destDir, "RFE_446066.java");
        (new FileCopy(
                new File(cleanDir, "RFE_446066.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_446066.java in error",
                new File(checkDir, "RFE_446066.java"),
                dest);
    }
   
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test105() {
        File dest = new File(destDir, "RFE_465239.java");
        (new FileCopy(
                new File(cleanDir, "RFE_465239.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_465239.java in error",
                new File(checkDir, "RFE_465239.java"),
                dest);
    }
   
    
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.04
     */
    public void test106() {
        File dest = new File(destDir, "RFE_465236.java");
        (new FileCopy(
                new File(cleanDir, "RFE_465236.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setLineUpParams(true);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_465236.java in error",
                new File(checkDir, "RFE_465236.java"),
                dest);
    }
   
    /**
     *  A unit test for JUnit
     *@since JRefactory 2.7.05
     */
    public void test107() {
        File dest = new File(destDir, "JEditBug_746217.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_746217.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_746217.java in error",
                new File(checkDir, "JEditBug_746217.java"),
                dest);
    }
   
    
    
    /**
     *  A unit test for JUnit.
     *  This tests the various assert statements formats, a regression
     *  was introduced that the current JUnit tests were not picking up,
     *  c.f. RFE 755940 - jdk 1.4 assert syntax
     *@since JRefactory 2.7.06
     */
    public void test108() {
        File dest = new File(destDir, "AssertTest.java");
        (new FileCopy(
                new File(cleanDir, "AssertTest.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("AssertTest.java in error",
                new File(checkDir, "AssertTest.java"),
                dest);
    }
   
    /**
     *  A unit test for JUnit.
     *  This is for a bug reported by Frederic Vernier
     *@since JRefactory 2.8.05
     */
    public void test109() {
        File dest = new File(destDir, "Bug_810715.java");
        (new FileCopy(
                new File(cleanDir, "Bug_810715.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setRemoveExcessBlocks(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_810715.java in error",
                new File(checkDir, "Bug_810715.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.8.07
     */
    public void test110() {
        File dest = new File(destDir, "Bug_813428.java");
        (new FileCopy(
                new File(cleanDir, "Bug_813428.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setRemoveExcessBlocks(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_813428.java in error",
                new File(checkDir, "Bug_813428.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.0
     */
    public void test111() {
        File dest = new File(destDir, "Bug_825576.java");
        (new FileCopy(
                new File(cleanDir, "Bug_825576.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_825576.java in error",
                new File(checkDir, "Bug_825576.java"),
                dest);
                
        dest = new File(destDir, "Bug_825576a.java");
        (new FileCopy(
                new File(cleanDir, "Bug_825576a.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setLineUpParams(true);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_825576a.java in error",
                new File(checkDir, "Bug_825576a.java"),
                dest);
    }



    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.1
     */
    public void test112() {
        File dest = new File(destDir, "CopyConstructor.java");
        (new FileCopy(
                new File(cleanDir, "CopyConstructor.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.apply(dest);

        FileCompare.assertEquals("CopyConstructor.java in error",
                new File(checkDir, "CopyConstructor.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *  This is for a bug reported by Frederic Vernier
     *@since JRefactory 2.9.1
     */
    public void test113() {
        File dest = new File(destDir, "Movie.java");
        (new FileCopy(
                new File(cleanDir, "Movie.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Movie.java in error",
                new File(checkDir, "Movie.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.2
     */
    public void test114() {
        File dest = new File(destDir, "RFE_446056extends.java");
        (new FileCopy(
                new File(cleanDir, "RFE_446056extends.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSortExtendsStatement(true);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_446056extends.java in error",
                new File(checkDir, "RFE_446056extends.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.2
     */
    public void test115() {
        File dest = new File(destDir, "RFE_446056implements.java");
        (new FileCopy(
                new File(cleanDir, "RFE_446056implements.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSortImplementsStatement(true);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_446056implements.java in error",
                new File(checkDir, "RFE_446056implements.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.2
     */
    public void test116() {
        File dest = new File(destDir, "RFE_446058throws.java");
        (new FileCopy(
                new File(cleanDir, "RFE_446058throws.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSortThrowsStatement(true);
        tppf.apply(dest);

        FileCompare.assertEquals("RFE_446058throws.java in error",
                new File(checkDir, "RFE_446058throws.java"),
                dest);
    }

    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.2
     */
    public void test117() {
        File dest = new File(destDir, "JEditBug_833328.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_833328.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        //tppf.setSortTop(true);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_833328.java in error",
                new File(checkDir, "JEditBug_833328.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.2
     */
    public void test118() {
        File dest = new File(destDir, "JEditBug_833328.java");
        (new FileCopy(
                new File(cleanDir, "JEditBug_833328.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSortTop(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("JEditBug_833328_noSort.java in error",
                new File(checkDir, "JEditBug_833328_noSort.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.4
     */
    public void test119() {
        File dest = new File(destDir, "Bug_517495.java");
        (new FileCopy(
                new File(cleanDir, "Bug_517495.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSortTop(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_517495.java in error",
                new File(checkDir, "Bug_517495.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.5
     */
    public void test120() {
        File dest = new File(destDir, "Bug_516386.java");
        (new FileCopy(
                new File(cleanDir, "Bug_516386.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSortTop(false);
        tppf.setSingleLineCommentOwnline(false);
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_516386.java in error",
                new File(checkDir, "Bug_516386.java"),
                dest);
    }


    /**
     *  A unit test for JUnit.
     *@since JRefactory 2.9.15
     */
    public void test121() {
        File dest = new File(destDir, "Bug_929006.java");
        (new FileCopy(
                new File(cleanDir, "Bug_929006.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setParamDescr("");
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_929006.java in error",
                new File(checkDir, "Bug_929006.java"),
                dest);

                
        dest = new File(destDir, "Bug_929006a.java");
        (new FileCopy(
                new File(cleanDir, "Bug_929006a.java"),
                dest, false)).run();

        tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setParamDescr("New description");
        tppf.apply(dest);

        FileCompare.assertEquals("Bug_929006a.java in error",
                new File(checkDir, "Bug_929006a.java"),
                dest);
    }


    /**
     *  The JUnit setup method
     */
    protected void setUp()
    {
        cleanDir = new File("test/clean/pretty");
        //try {
        //    System.out.println("file="+cleanDir.getCanonicalFile());
        //} catch (java.io.IOException e) {
        //    e.printStackTrace();
        //}
        destDir = new File("test/temp");
        checkDir = new File("test/check/pretty");
        (new RefactoryInstaller(false)).run();
    }
    

       public static TestSuite suite() {
          TestSuite result = new TestSuite();
/*        
          result.addTest(new TestPrettyPrinter("test68"));
          //result.addTest(new TestPrettyPrinter("test88"));
          //result.addTest(new TestPrettyPrinter("test89"));
          //result.addTest(new TestPrettyPrinter("test90"));
          //result.addTest(new TestPrettyPrinter("test91"));
          //result.addTest(new TestPrettyPrinter("test92"));
          //result.addTest(new TestPrettyPrinter("test93"));
          //result.addTest(new TestPrettyPrinter("test94"));
          //result.addTest(new TestPrettyPrinter("test95"));
          //result.addTest(new TestPrettyPrinter("test96"));
          //result.addTest(new TestPrettyPrinter("test97"));
          //result.addTest(new TestPrettyPrinter("test98"));
          //result.addTest(new TestPrettyPrinter("test99"));
          result.addTest(new TestPrettyPrinter("test100"));
          result.addTest(new TestPrettyPrinter("test101"));
          result.addTest(new TestPrettyPrinter("test102"));
          result.addTest(new TestPrettyPrinter("test103"));
          result.addTest(new TestPrettyPrinter("test104"));
          result.addTest(new TestPrettyPrinter("test105"));
          result.addTest(new TestPrettyPrinter("test106"));
          result.addTest(new TestPrettyPrinter("test107"));
          result.addTest(new TestPrettyPrinter("test108"));
          result.addTest(new TestPrettyPrinter("test109"));
          result.addTest(new TestPrettyPrinter("test110"));
          result.addTest(new TestPrettyPrinter("test111"));
          result.addTest(new TestPrettyPrinter("test112"));
          result.addTest(new TestPrettyPrinter("test113"));
          result.addTest(new TestPrettyPrinter("test114"));
          result.addTest(new TestPrettyPrinter("test115"));
          result.addTest(new TestPrettyPrinter("test116"));
          //result.addTest(new TestPrettyPrinter("test117"));
          //result.addTest(new TestPrettyPrinter("test118"));
          result.addTest(new TestPrettyPrinter("test119"));
          result.addTest(new TestPrettyPrinter("test120"));
*/
          result.addTest(new TestPrettyPrinter("test121"));
          return result;
       }

}
