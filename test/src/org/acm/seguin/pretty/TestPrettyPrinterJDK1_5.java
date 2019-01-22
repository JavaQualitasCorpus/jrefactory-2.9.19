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
 *@author     Mike Atkinson
 *@created    June 17, 2003
 */
public class TestPrettyPrinterJDK1_5 extends DirSourceTestCase
{
    private File checkDir;
    private File cleanDir;
    private File destDir;


    /**
     *  Constructor for the TestPrettyPrinter object
     *
     *@param  name  Description of Parameter
     */
    public TestPrettyPrinterJDK1_5(String name)
    {
        super(name);
    }


    /**
     *  A unit test for JUnit
     */
    public void test01()
    {
        File dest = new File(destDir, "Enums.java");
        (new FileCopy(
                new File(cleanDir, "Enums.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Enums.java in error",
                new File(checkDir, "Enums.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test02()
    {
        File dest = new File(destDir, "Generics.java");
        (new FileCopy(
                new File(cleanDir, "Generics.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Generics.java in error",
                new File(checkDir, "Generics.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test03()
    {
        File dest = new File(destDir, "Card.java");
        (new FileCopy(
                new File(cleanDir, "Card.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Card.java in error",
                new File(checkDir, "Card.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test04()
    {
        File dest = new File(destDir, "GenericCollections.java");
        (new FileCopy(
                new File(cleanDir, "GenericCollections.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("GenericCollections.java in error",
                new File(checkDir, "GenericCollections.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test05()
    {
        File dest = new File(destDir, "Wildcard.java");
        (new FileCopy(
                new File(cleanDir, "Wildcard.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Wildcard.java in error",
                new File(checkDir, "Wildcard.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test06()
    {
        File dest = new File(destDir, "MoreTests.java");
        (new FileCopy(
                new File(cleanDir, "MoreTests.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("MoreTests.java in error",
                new File(checkDir, "MoreTests.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test07() {
        File dest = new File(destDir, "Annotations.java");
        (new FileCopy(
                new File(cleanDir, "Annotations.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Annotations.java in error",
                new File(checkDir, "Annotations.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test08() {
        File dest = new File(destDir, "Reviewer.java");
        (new FileCopy(
                new File(cleanDir, "Reviewer.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Reviewer.java in error",
                new File(checkDir, "Reviewer.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test09() {
        File dest = new File(destDir, "Author.java");
        (new FileCopy(
                new File(cleanDir, "Author.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Author.java in error",
                new File(checkDir, "Author.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test10() {
        File dest = new File(destDir, "Copyright.java");
        (new FileCopy(
                new File(cleanDir, "Copyright.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Copyright.java in error",
                new File(checkDir, "Copyright.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test11() {
        File dest = new File(destDir, "Endorsers.java");
        (new FileCopy(
                new File(cleanDir, "Endorsers.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Endorsers.java in error",
                new File(checkDir, "Endorsers.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test12() {
        File dest = new File(destDir, "Formatter.java");
        (new FileCopy(
                new File(cleanDir, "Formatter.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Formatter.java in error",
                new File(checkDir, "Formatter.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test13() {
        File dest = new File(destDir, "Name.java");
        (new FileCopy(
                new File(cleanDir, "Name.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Name.java in error",
                new File(checkDir, "Name.java"),
                dest);
    }


    /**
     *  A unit test for JUnit
     */
    public void test14() {
        File dest = new File(destDir, "Preliminary.java");
        (new FileCopy(
                new File(cleanDir, "Preliminary.java"),
                dest, false)).run();

        TestPrettyPrintFile tppf = new TestPrettyPrintFile();
        tppf.setAsk(false);
        tppf.isApplicable(dest);
        tppf.setSingleLineCommentOwnline(false);
        tppf.setCStyle(PrintData.CSC_LEAVE_UNTOUCHED);
        tppf.apply(dest);

        FileCompare.assertEquals("Preliminary.java in error",
                new File(checkDir, "Preliminary.java"),
                dest);
    }


    /**
     *  The JUnit setup method
     */
    protected void setUp()
    {
        cleanDir = new File("test/clean/pretty/jdk1_5");
        //try {
        //    System.out.println("file="+cleanDir.getCanonicalFile());
        //} catch (java.io.IOException e) {
        //    e.printStackTrace();
        //}
        destDir = new File("test/temp");
        checkDir = new File("test/check/pretty/jdk1_5");
        (new RefactoryInstaller(false)).run();
    }
    

       public static TestSuite suite() {
          TestSuite result = new TestSuite();
          result.addTest(new TestPrettyPrinterJDK1_5("test01"));
          result.addTest(new TestPrettyPrinterJDK1_5("test02"));
          result.addTest(new TestPrettyPrinterJDK1_5("test03"));
          result.addTest(new TestPrettyPrinterJDK1_5("test04"));
          result.addTest(new TestPrettyPrinterJDK1_5("test05"));
          result.addTest(new TestPrettyPrinterJDK1_5("test06"));
          result.addTest(new TestPrettyPrinterJDK1_5("test07"));
          result.addTest(new TestPrettyPrinterJDK1_5("test08"));
          result.addTest(new TestPrettyPrinterJDK1_5("test09"));
          result.addTest(new TestPrettyPrinterJDK1_5("test10"));
          result.addTest(new TestPrettyPrinterJDK1_5("test11"));
          result.addTest(new TestPrettyPrinterJDK1_5("test12"));
          result.addTest(new TestPrettyPrinterJDK1_5("test13"));
          result.addTest(new TestPrettyPrinterJDK1_5("test14"));
          return result;
       }

}
