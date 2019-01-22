package org.acm.seguin.refactor.field;

import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.SummaryTraversal;



/**
 *  Unit tests for the rename field refactoring.
 *
 * @author    Chris Seguin
 */
public class TestRenameFieldRefactoring extends DirSourceTestCase {
   private File impDest;
   private File dest;


   /**
    *  Constructor for the TestRenameFieldRefactoring object
    *
    * @param  name  the name of the unit test to run
    */
   public TestRenameFieldRefactoring(String name) {
      super(name);
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test01() throws RefactoringException {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("simple");
      rfr.setNewName("changed");

      rfr.run();

      //  Check things out
      File check = new File(this.check + "\\ut2\\step11");

      FileCompare.assertEquals("RenameFieldTest is incorrect",
               new File(check, "RenameFieldTest.java"),
               new File(dest, "RenameFieldTest.java"));
      FileCompare.assertEquals("UsesFieldTest is incorrect",
               new File(check, "UsesFieldTest.java"),
               new File(dest, "UsesFieldTest.java"));
      FileCompare.assertEquals("InheritFieldTest is incorrect",
               new File(check, "InheritFieldTest.java"),
               new File(dest, "InheritFieldTest.java"));
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test02() throws RefactoringException {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("code");
      rfr.setNewName("changed");

      rfr.run();

      //  Check things out
      File check = new File(this.check + "\\ut2\\step12");

      FileCompare.assertEquals("RenameFieldTest is incorrect",
               new File(check, "RenameFieldTest.java"),
               new File(dest, "RenameFieldTest.java"));
      FileCompare.assertEquals("UsesFieldTest is incorrect",
               new File(check, "UsesFieldTest.java"),
               new File(dest, "UsesFieldTest.java"));
      FileCompare.assertEquals("InheritFieldTest is incorrect",
               new File(check, "InheritFieldTest.java"),
               new File(dest, "InheritFieldTest.java"));
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test03() throws RefactoringException {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("height");
      rfr.setNewName("changed");

      rfr.run();

      //  Check things out
      File check = new File(this.check + "\\ut2\\step13");

      FileCompare.assertEquals("RenameFieldTest is incorrect",
               new File(check, "RenameFieldTest.java"),
               new File(dest, "RenameFieldTest.java"));
      FileCompare.assertEquals("UsesFieldTest is incorrect",
               new File(check, "UsesFieldTest.java"),
               new File(dest, "UsesFieldTest.java"));
      FileCompare.assertEquals("InheritFieldTest is incorrect",
               new File(check, "InheritFieldTest.java"),
               new File(dest, "InheritFieldTest.java"));
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test04() throws RefactoringException {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("CODE_ON");
      rfr.setNewName("changed");

      rfr.run();

      //  Check things out
      File check = new File(this.check + "\\ut2\\step14");

      FileCompare.assertEquals("RenameFieldTest is incorrect",
               new File(check, "RenameFieldTest.java"),
               new File(dest, "RenameFieldTest.java"));
      FileCompare.assertEquals("UsesFieldTest is incorrect",
               new File(check, "UsesFieldTest.java"),
               new File(dest, "UsesFieldTest.java"));
      FileCompare.assertEquals("InheritFieldTest is incorrect",
               new File(check, "InheritFieldTest.java"),
               new File(dest, "InheritFieldTest.java"));
   }


   /**
    *  A unit test for JUnit
    *
    */
   public void test05() {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("crazy");
      rfr.setNewName("changed");

      boolean exceptionThrown = false;

      try {
         rfr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "No field named crazy is contained in RenameFieldTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }



   /**
    *  A unit test for JUnit
    *
    */
   public void test06() {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("simple");
      rfr.setNewName("code");

      boolean exceptionThrown = false;

      try {
         rfr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A field named code already exists in class RenameFieldTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test07() throws RefactoringException {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("", "XDateChooser");
      rfr.setField("_maxDate");
      rfr.setNewName("_maximumDate");

      rfr.run();

      //  Check things out
      File check = new File(this.check + "\\ut2\\step15");

      FileCompare.assertEquals("XDateChooser is incorrect",
               new File(check, "XDateChooser.java"),
               new File(root + "\\XDateChooser.java"));
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test08() throws RefactoringException {
      RenameFieldRefactoring rfr = new RenameFieldRefactoring();
      rfr.setClass("field", "RenameFieldTest");
      rfr.setField("childObject");
      rfr.setNewName("myChild");

      rfr.run();

      //  Check things out
      File check = new File(this.check + "\\ut2\\step16");

      FileCompare.assertEquals("RenameFieldTest is incorrect",
               new File(check, "RenameFieldTest.java"),
               new File(dest, "RenameFieldTest.java"));
      FileCompare.assertEquals("UsesFieldTest is incorrect",
               new File(check, "UsesFieldTest.java"),
               new File(dest, "UsesFieldTest.java"));
      FileCompare.assertEquals("InheritFieldTest is incorrect",
               new File(check, "InheritFieldTest.java"),
               new File(dest, "InheritFieldTest.java"));
   }


   /**
    *  The JUnit setup method
    *
    */
   protected void setUp() {
      File cleanDir = new File(this.clean);
      dest = new File(root + "\\field");
      if (!dest.exists()) {
         dest.mkdirs();
      }

      (new FileCopy(
               new File(cleanDir, "field_RenameFieldTest.java"),
               new File(dest, "RenameFieldTest.java"),
               false)).run();
      (new FileCopy(
               new File(cleanDir, "field_UsesFieldTest.java"),
               new File(dest, "UsesFieldTest.java"),
               false)).run();
      (new FileCopy(
               new File(cleanDir, "field_InheritFieldTest.java"),
               new File(dest, "InheritFieldTest.java"),
               false)).run();
      (new FileCopy(
               new File(cleanDir, "XDateChooser.java"),
               new File(root + "\\XDateChooser.java"),
               false)).run();

      (new SummaryTraversal(root)).run();
   }


   /**
    *  The teardown method for JUnit
    *
    */
   protected void tearDown() {
      (new File(dest, "RenameFieldTest.java")).delete();
      (new File(dest, "UsesFieldTest.java")).delete();
      (new File(dest, "InheritFieldTest.java")).delete();
      (new File(root + "\\XDateChooser.java")).delete();
   }
}

