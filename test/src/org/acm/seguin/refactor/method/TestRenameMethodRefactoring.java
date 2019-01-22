package org.acm.seguin.refactor.method;

import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;
import org.acm.seguin.refactor.RefactoringException;
import org.acm.seguin.summary.SummaryTraversal;



/**
 *  Unit tests for the rename method refactoring.
 *
 * @author    Chris Seguin
 */
public class TestRenameMethodRefactoring extends DirSourceTestCase {
   private File impDest;
   private File dest;


   /**
    *  Constructor for the TestRenameMethodRefactoring object
    *
    * @param  name  the name of the unit test to run
    */
   public TestRenameMethodRefactoring(String name) {
      super(name);
   }


   /**
    *  A unit test for JUnit
    *
    * @exception  RefactoringException  Description of Exception
    */
   public void test01() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("code");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("newCode");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step1");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test02() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("newCode");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("code");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }



/*
   public void test03() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("protectedMethod");
      rmr.setParams(new String[] { "int" } );
      rmr.setNewMethodName("xxx");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test04() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("xxx");
      rmr.setParams(new String[] { "int" } );
      rmr.setNewMethodName("protectedMethod");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test05() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("packageProtectedMethod");
      rmr.setParams(new String[] { "int" } );
      rmr.setNewMethodName("xxx");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test06() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("xxx");
      rmr.setParams(new String[] { "int" } );
      rmr.setNewMethodName("packageProtectedMethod");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test07() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("privateMethod");
      rmr.setParams(new String[] { "int" } );
      rmr.setNewMethodName("xxx");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test08() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("xxx");
      rmr.setParams(new String[] { "int" } );
      rmr.setNewMethodName("privateMethod");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step2");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test03() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("height");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("width");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step3");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test04() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("width");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("height");

      rmr.run();

      //  Check things out
      File check = new File(this.check + "\\ut4\\step4");

      FileCompare.assertEquals("RenameMethodTest is incorrect",
               new File(check, "RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"));
      FileCompare.assertEquals("UsesMethodTest is incorrect",
               new File(check, "UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"));
      FileCompare.assertEquals("InheritMethodTest is incorrect",
               new File(check, "InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"));
   }


   public void test05() {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("code");
      rmr.setParams(new String[] { "int" });
      rmr.setNewMethodName("newCode");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "No method named crazy is contained in RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }



   public void test06() {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("newCode");
      rmr.setParams(new String[] { "int" });
      rmr.setNewMethodName("code");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }


   public void test07() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("height");
      rmr.setParams(new String[] { "int" });
      rmr.setNewMethodName("width");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }

   public void test08() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "RenameMethodTest");
      rmr.setMethod("width");
      rmr.setParams(new String[] { "int" });
      rmr.setNewMethodName("height");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }


   public void test09() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "InheritMethodTest");
      rmr.setMethod("code");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("newCode");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }


   public void test10() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "InheritMethodTest");
      rmr.setMethod("newCode");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("code");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }


   public void test11() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "InheritMethodTest");
      rmr.setMethod("height");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("width");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }


   public void test12() throws RefactoringException {
      RenameMethodRefactoring rmr = new RenameMethodRefactoring();
      rmr.setClass("method", "InheritMethodTest");
      rmr.setMethod("width");
      rmr.setParams(new String[0]);
      rmr.setNewMethodName("height");

      boolean exceptionThrown = false;

      try {
         rmr.run();
      } catch (RefactoringException re) {
         exceptionThrown = true;
         assertEquals("Incorrect exception", "A method named code already exists in class RenameMethodTest", re.getMessage());
      }

      assertTrue("No exception thrown", exceptionThrown);
   }
*/

   /**
    *  The JUnit setup method
    *
    */
   protected void setUp() {
      File cleanDir = new File(this.clean);
      dest = new File(root + "\\method");
      if (!dest.exists()) {
         dest.mkdirs();
      }

      (new FileCopy(
               new File(cleanDir, "method_RenameMethodTest.java"),
               new File(dest, "RenameMethodTest.java"),
               false)).run();
      (new FileCopy(
               new File(cleanDir, "method_UsesMethodTest.java"),
               new File(dest, "UsesMethodTest.java"),
               false)).run();
      (new FileCopy(
               new File(cleanDir, "method_InheritMethodTest.java"),
               new File(dest, "InheritMethodTest.java"),
               false)).run();

      (new SummaryTraversal(root)).run();
   }


   /**
    *  The teardown method for JUnit
    *
    */
   protected void tearDown() {
      (new File(dest, "RenameMethodTest.java")).delete();
      (new File(dest, "UsesMethodTest.java")).delete();
      (new File(dest, "InheritMethodTest.java")).delete();
   }
}

