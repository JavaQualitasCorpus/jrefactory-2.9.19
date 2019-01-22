/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.ai;

import java.text.MessageFormat;

import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTMemberValuePairs;
import net.sourceforge.jrefactory.ast.ASTMemberValuePair;
import net.sourceforge.jrefactory.ast.ASTMemberValue;
import net.sourceforge.jrefactory.ast.ASTMemberValueArrayInitializer;

import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclarator;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTNameList;

import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTResultType;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTTypeParameters;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.DescriptionPadder;
import org.acm.seguin.pretty.JavaDocableImpl;
import org.acm.seguin.pretty.JavadocTags;
import org.acm.seguin.util.FileSettings;
import org.acm.seguin.util.MissingSettingsException;

/**
 *  Basis for the artificial intelligence that analyzes the method and determines the appropriate javadoc descriptions
 *
 * @author    Chris Seguin
 * @author    Mike Atkinson
 */
public class MethodAnalyzer {
   private ASTMethodDeclaration node;
   private JavaDocableImpl jdi;
   private ParseVariableName pvn;
   private JavadocTags jt;
   private String operationType = null;


   /**
    *  Constructor for the MethodAnalyzer object
    *
    * @param  node  Description of Parameter
    * @param  jdi   Description of Parameter
    */
   public MethodAnalyzer(ASTMethodDeclaration node, JavaDocableImpl jdi) {
      this.node = node;
      this.jdi = jdi;
      pvn = new ParseVariableName();
      jt = JavadocTags.get();
   }


   /**
    *  Makes sure all the java doc components are present. For methods and constructors we need to do more work -
    *  checking parameters, return types, and exceptions.
    *
    * @param  className  Description of Parameter
    */
   public void finish(String className) {
      //  Get the resource bundle
      FileSettings bundle = FileSettings.getRefactoryPrettySettings();

      //  Require a description of this method
      requireDescription(bundle, className);

      String methodTags = "return,param,exception,throws";
      try {
         methodTags = bundle.getString("method.tags");
      } catch (MissingSettingsException mse) {
      }

      //  Check that if there is a return type
      if (methodTags.indexOf("return") >= 0) {
         finishReturn(bundle);
      }

      //  Check for parameters
      if (methodTags.indexOf("param") >= 0) {
         finishParameters(bundle);
      }
      sortParameters();
      // sort parameters into order the occur in the method

      //  Check for exceptions
      if ((methodTags.indexOf("exception") >= 0) ||
            (methodTags.indexOf("throws") >= 0) ||
            (methodTags.indexOf(jt.getExceptionTag()) >= 0)) {
         finishExceptions(bundle);
      }
   }


   /**
    *  Determine if this is a setter method
    *
    * @return    true if it is a setter
    */
   private boolean isSetter() {
      String name = getName();
      return ((name.length() > 3) && name.startsWith("set") &&
            Character.isUpperCase(name.charAt(3)));
   }


   /**
    *  Determine if this is a getter method
    *
    * @return    true if it is a getter
    */
   private boolean isGetter() {
      String name = getName();
      return ((name.length() > 3) && (name.startsWith("get") && Character.isUpperCase(name.charAt(3))) ||
            ((name.length() > 2) && name.startsWith("is") && Character.isUpperCase(name.charAt(2))));
   }


   /**
    *  Determine if this is a getter method
    *
    * @return    true if it is a getter
    */
   private boolean isAdder() {
      String name = getName();
      return (name.length() > 3) && (name.startsWith("add") && Character.isUpperCase(name.charAt(3)));
   }

   /**
    *  Determine if this is a getter method
    *
    * @return    true if it is a getter
    */
   private boolean isListener() {
      String name = getName();
      return (name.length() > 11) && (name.endsWith("Listener")) && (name.startsWith("add") || name.startsWith("remove"));
   }


   /**
    *  Determine if this is a run method
    *
    * @return    true if it is a run method
    */
   private boolean isRunMethod() {
      String name = getName();
      return name.equals("run");
   }


   /**
    *  Gets the MainMethod attribute of the MethodAnalyzer object
    *
    * @return    The MainMethod value
    */
   private boolean isMainMethod() {
      String name = getName();
      if (!(name.equals("main") && node.isStatic())) {
         return false;
      }

      // If it has JDK 1.5 attributes ignore them
      int childNo = node.skipAnnotations();

      // If it has JDK 1.5 type parameters it cannot be a main() method.
      Node child = node.jjtGetChild(childNo);
      if (child instanceof ASTTypeParameters) {
         return false;
      }

      //  Check for the void return type
      ASTResultType result = (ASTResultType)child;
      if (result.hasAnyChildren()) {
         return false;
      }

      //  Check the parameters
      ASTMethodDeclarator decl = (ASTMethodDeclarator)node.jjtGetChild(childNo+1);
      ASTFormalParameters params = (ASTFormalParameters)decl.jjtGetFirstChild();
      int childCount = params.jjtGetNumChildren();
      if (childCount != 1) {
         return false;
      }

      ASTFormalParameter nextParam = (ASTFormalParameter)params.jjtGetFirstChild();
      childNo = nextParam.skipAnnotations();
      ASTType type = (ASTType)nextParam.jjtGetChild(childNo);
      child = type.jjtGetFirstChild();
      if (child instanceof ASTReferenceType) {
         ASTReferenceType reference = (ASTReferenceType)child;
         childCount = reference.jjtGetNumChildren();
         if (childCount != 1) {
            return false;
         }
         if (reference.jjtGetFirstChild() instanceof ASTClassOrInterfaceType) {
            ASTClassOrInterfaceType nameNode = (ASTClassOrInterfaceType)reference.jjtGetFirstChild();
            if (nameNode.getName().equals("String") || nameNode.getName().equals("java.lang.String")) {
               if (reference.getArrayCount() == 1) {
                  return true;
               }
            }
         }
      }

      return false;
   }


   /**
    *  Determine if this is a JUnit setUp method
    *
    * @return    true if it is a JUnit setUp method
    */
   private boolean isJUnitSetupMethod() {
      String name = getName();
      return name.equals("setUp");
   }


   /**
    *  Determine if this is a JUnit test method
    *
    * @return    true if it is a JUnit test method
    */
   private boolean isJUnitTestMethod() {
      String name = getName();
      return name.startsWith("test");
   }


   /**
    *  Determine if this is a JUnit tearDown method
    *
    * @return    true if it is a JUnit tearDown method
    */
   private boolean isJUnitTeardownMethod() {
      String name = getName();
      return name.equals("tearDown");
   }


   /**
    *  Determine if this is a JUnit suite method
    *
    * @return    true if it is a JUnit suite method
    */
   private boolean isJUnitSuiteMethod() {
      String name = getName();
      return name.equals("suite");
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isToStringMethod() {
      String name = getName();
      if (!name.equals("toString")) {
         return false;
      }

      // If it has JDK 1.5 attributes ignore them
      int childNo = node.skipAnnotations();

      // If it has JDK 1.5 type parameters it cannot be a main() method.
      Node child = node.jjtGetChild(childNo);
      if (child instanceof ASTTypeParameters) {
         return false;
      }

      //  Check for the void return type
      ASTResultType result = (ASTResultType)child;
      if (!result.hasAnyChildren()) {
         return false;
      }
      String type = result.getImage();
      
      ASTMethodDeclarator decl = (ASTMethodDeclarator)node.jjtGetChild(childNo+1);
      return hasNoParameters(decl);
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isEqualsMethod() {
      String name = getName();
      if (!name.equals("equals")) {
         return false;
      }
      return true;
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isHashCodeMethod() {
      String name = getName();
      if (!name.equals("hashCode")) {
         return false;
      }
      return true;
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isCloneMethod() {
      String name = getName();
      if (!name.equals("clone")) {
         return false;
      }
      return true;
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isFinalizeMethod() {
      String name = getName();
      if (!name.equals("finalize")) {
         return false;
      }
      return true;
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isListenerAddMethod() {
      String name = getName();
      if (!name.startsWith("add")) {
         return false;
      }
      if (!name.endsWith("Listener")) {
         return false;
      }
      operationType = "add";
      return true;
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isListenerRemoveMethod() {
      String name = getName();
      if (!name.startsWith("remove")) {
         return false;
      }
      if (!name.endsWith("Listener")) {
         return false;
      }
      operationType = "remove";
      return true;
   }


   /**
    *  Determine if this is s toString() method
    *
    * @return    true if it is a toString() suite method
    */
   private boolean isInstanceMethod() {
      String name = getName();
      if (!name.equals("instance")) {
         return false;
      }
      // If it has JDK 1.5 attributes ignore them
      int childNo = node.skipAnnotations();

      // If it has JDK 1.5 type parameters it cannot be a instance() method.
      Node child = node.jjtGetChild(childNo);
      if (child instanceof ASTTypeParameters) {
         return false;
      }

      //  Check for the void return type
      ASTResultType result = (ASTResultType)child;
      if (!result.hasAnyChildren()) {
         return false;
      }
      String type = result.getImage();
      System.out.println("isInstanceMethod(): type="+type);
      
      ASTMethodDeclarator decl = (ASTMethodDeclarator)node.jjtGetChild(childNo+1);
      return hasNoParameters(decl);
   }



   /**
    *  Returns the name of the method
    *
    * @return    the name
    */
   private String getName() {
      int childNo = node.skipAnnotations();
      if (node.jjtGetChild(childNo) instanceof ASTTypeParameters) {
         childNo++;
      }
      ASTMethodDeclarator decl = (ASTMethodDeclarator)node.jjtGetChild(childNo + 1);
      return decl.getName();
   }

   private boolean hasNoParameters(ASTMethodDeclarator method) {
      ASTFormalParameters decl = (ASTFormalParameters)method.jjtGetFirstChild();
      if (!decl.hasAnyChildren()) {
         return true;
      }
      //System.out.println("MethodAnalyser.hasNoParameters(): decl.jjtGetFirstChild()="+decl.jjtGetFirstChild());
      ASTFormalParameter params = (ASTFormalParameter)decl.jjtGetFirstChild();
      return (!params.hasAnyChildren());
   }


   /**
    *  Guesses the name ofthe setter or getter's attribute
    *
    * @return    the attribute name
    */
   private String getAttributeName() {
      String name = getName();

      if (!isGetter() && !isSetter() && !isAdder() && !isListener()) {
         return "";
      } else if (name.startsWith("is")) {
         return name.substring(2);
      } else if (name.endsWith("Listener")) {
         int start = (name.startsWith("add")) ? 3 : 6;
         return name.substring(start, name.length()-8);
      } else {
         return name.substring(3);
      }
   }


   /**
    *  Gets the ParameterDescription attribute of the MethodAnalyzer object
    *
    * @param  bundle  Description of Parameter
    * @param  param   Description of Parameter
    * @return         The ParameterDescription value
    */
   private String getParameterDescription(FileSettings bundle, String param) {
      String pattern = "";

      if (isSetter()) {
         pattern = bundle.getProperty("setter.param.descr","The new {0} value");
      } else if (isListener()) {
         pattern = bundle.getProperty("listener.param.descr","Contains the {0}Listener for {0}Event data.");
      } else if (isEqualsMethod()) {
         pattern = bundle.getProperty("equals.param.descr","the reference object with which to compare.");         
      } else if (isAdder()) {
         pattern = bundle.getProperty("adder.param.descr","The feature to be added to the {0} attribute");
      } else if (isMainMethod()) {
         pattern = bundle.getProperty("main.param.descr","The command line arguments");
      } else {
         pattern = jt.getParamDescr();
      }

      if (pattern==null || pattern.equals("")) {
         return null;
      }

      return createDescription(pattern, getAttributeName(), param);
   }


   /**
    *  Gets the ReturnDescription attribute of the MethodAnalyzer object
    *
    * @param  bundle  Description of Parameter
    * @return         The ReturnDescription value
    */
   private String getReturnDescription(FileSettings bundle) {
      String pattern = "";

      if (isToStringMethod()) {
         pattern = bundle.getProperty("tostring.return.descr","A string representation of the {0} object.");         
      } else if (isEqualsMethod()) {
         pattern = bundle.getProperty("equals.return.descr","<tt>true</tt> if this object is the same as the obj argument; <tt>false</tt> otherwise.");         
      } else if (isHashCodeMethod()) {
         pattern = bundle.getProperty("hashcode.return.descr","The hash value for this {0} object.");         
      } else if (isInstanceMethod()) {
         pattern = bundle.getProperty("instance.return.descr","An instance of {0}.");         
      } else if (isJUnitSuiteMethod()) {
         pattern = bundle.getProperty("junit.suite.return.descr","The test suite");
      } else if (isJUnitSuiteMethod()) {
         pattern = bundle.getProperty("clone.return.descr","A clone of this {0} object.");
      } else if (isGetter()) {
         pattern = bundle.getProperty("getter.return.descr","The {0} value");
      } else {
         pattern = jt.getReturnDescr();
      }

      return createDescription(pattern, getAttributeName(), "");
   }


   /**
    *  Description of the Method
    *
    * @param  bundle  Description of Parameter
    */
   private void finishReturn(FileSettings bundle) {
      int childNo = node.skipAnnotations();
      if (node.jjtGetChild(childNo) instanceof ASTTypeParameters) {
         childNo++;
      }
      ASTResultType result = (ASTResultType)node.jjtGetChild(childNo);
      if (result.hasAnyChildren()) {
         if (!jdi.contains("@return")) {
            jdi.require("@return", getReturnDescription(bundle));
         }
      }
   }


   /**
    *  Description of the Method
    *
    * @param  bundle  Description of Parameter
    */
   private void finishParameters(FileSettings bundle) {
      int childNo = node.skipAnnotations();
      if (node.jjtGetChild(childNo++) instanceof ASTTypeParameters) {
         childNo++;
      }
      ASTMethodDeclarator decl = (ASTMethodDeclarator)node.jjtGetChild(childNo);
      ASTFormalParameters params = (ASTFormalParameters)decl.jjtGetFirstChild();

      int childCount = params.jjtGetNumChildren();
      for (int ndx = 0; ndx < childCount; ndx++) {
         ASTFormalParameter nextParam = (ASTFormalParameter)params.jjtGetChild(ndx);
         childNo = nextParam.skipAnnotations();
         ASTVariableDeclaratorId id = (ASTVariableDeclaratorId)nextParam.jjtGetChild(childNo+1);
         if (!jdi.contains("@param", id.getName())) {
            jdi.require("@param", id.getName(), getParameterDescription(bundle, id.getName()));
         }
      }
   }


   /**
    *  Sort the "@param" elements of the method.
    *
    * @since    JRefactory 2.7.00
    */
   private void sortParameters() {
      int childNo = node.skipAnnotations();
      if (node.jjtGetChild(childNo++) instanceof ASTTypeParameters) {
         childNo++;
      }
      ASTMethodDeclarator decl = (ASTMethodDeclarator)node.jjtGetChild(childNo);
      ASTFormalParameters params = (ASTFormalParameters)decl.jjtGetFirstChild();

      int childCount = params.jjtGetNumChildren();
      String[] methodParams = new String[childCount];
      for (int ndx = 0; ndx < childCount; ndx++) {
         ASTFormalParameter nextParam = (ASTFormalParameter)params.jjtGetChild(ndx);
         childNo = nextParam.skipAnnotations();
         ASTVariableDeclaratorId id = (ASTVariableDeclaratorId)nextParam.jjtGetChild(childNo+1);
         methodParams[ndx] = id.getName();
      }
      jdi.sort("@param", methodParams);
   }


   /**
    *  Description of the Method
    *
    * @param  bundle  Description of Parameter
    */
   private void finishExceptions(FileSettings bundle) {
      if ((node.jjtGetNumChildren() > 2) && (node.jjtGetChild(2) instanceof ASTNameList)) {
         String exceptionTagName = jt.getExceptionTag();

         ASTNameList exceptions = (ASTNameList)node.jjtGetChild(2);
         int childCount = exceptions.jjtGetNumChildren();
         for (int ndx = 0; ndx < childCount; ndx++) {
            ASTName name = (ASTName)exceptions.jjtGetChild(ndx);
            if (!(jdi.contains("@exception", name.getName()) ||
                  jdi.contains("@throws", name.getName()) ||
                  jdi.contains(exceptionTagName, name.getName())) ) {
               jdi.require(exceptionTagName, name.getName(), jt.getExceptionDescr());
            }
         }
      }
   }


   /**
    *  Create the description string
    *
    * @param  pattern    Description of Parameter
    * @param  attribute  Description of Parameter
    * @param  className  Description of Parameter
    * @return            the expanded string
    */
   private String createDescription(String pattern, String attribute, String className) {
      if (pattern==null || pattern.length()==0) {
         return "";
      }
      //  Description of the constructor
      Object[] nameArray = new Object[6];
      nameArray[0] = attribute;
      nameArray[1] = className;
      nameArray[2] = (node.isStatic()) ? "class" : "object";
      nameArray[3] = lowerCaseFirstLetter(attribute);
      nameArray[4] = pvn.parse(attribute);
      nameArray[5] = operationType;  // add, remove, etc.

      return MessageFormat.format(pattern, nameArray);
   }


   /**
    *  Require the description
    *
    * @param  bundle     Description of Parameter
    * @param  className  Description of Parameter
    */
   private void requireDescription(FileSettings bundle, String className) {
      String pattern = "";

      try {
         if (isJUnitSetupMethod()) {
            pattern = bundle.getProperty("junit.setUp.descr","The JUnit setup method");
         } else if (isJUnitTestMethod()) {
            pattern = bundle.getProperty("junit.test.descr","A unit test for JUnit");
         } else if (isJUnitTeardownMethod()) {
            pattern = bundle.getProperty("junit.tearDown.descr","The teardown method for JUnit");
         } else if (isJUnitSuiteMethod()) {
            pattern = bundle.getProperty("junit.suite.descr","A suite of unit tests for JUnit");
         } else if (isListenerAddMethod()) {
            pattern = bundle.getProperty("listener.add.descr","Adds the specified {0} listener to receive {0} events from this component. If listener l is null, no exception is thrown and no action is performed.");
         } else if (isListenerRemoveMethod()) {
            pattern = bundle.getProperty("listener.remove.descr","Removes the specified {0} listener so that it no longer receives {0} events from this component. This method performs no function, nor does it throw an exception, if the listener specified by the argument was not previously added to this component. If listener l is null, no exception is thrown and no action is performed.");
         } else if (isGetter()) {
            pattern = bundle.getProperty("getter.descr","Gets the {0} attribute of the {1} {2}");
         } else if (isSetter()) {
            pattern = bundle.getProperty("setter.descr","Sets the {0} attribute of the {1} {2}");
         } else if (isRunMethod()) {
            pattern = bundle.getProperty("run.descr","Main processing method for the {1} {2}");
         } else if (isMainMethod()) {
            pattern = bundle.getProperty("main.descr","The main program for the {1} {2}");
         } else if (isToStringMethod()) {
            pattern = bundle.getProperty("tostring.descr","Converts to a String representation of the {0} object.");
         } else if (isEqualsMethod()) {
            pattern = bundle.getProperty("equals.descr","Compares this {0} to the parameter.");
         } else if (isHashCodeMethod()) {
            pattern = bundle.getProperty("hashcode.descr","Computes a hash value for this {0} object.");
         } else if (isCloneMethod()) {
            pattern = bundle.getProperty("clone.descr","Creates an exact copy of this {0} object.");
         } else if (isFinalizeMethod()) {
            pattern = bundle.getProperty("finalize.descr","Overrides the finalize method to dispose of system resources or to perform other cleanup when the {1} object is garbage collected.");
         } else if (isInstanceMethod()) {
            pattern = bundle.getProperty("instance.descr","Gets an instance of this {0} class.");
         } else if (isAdder()) {
            pattern = bundle.getProperty("adder.descr","Adds a feature to the {0} attribute of the {1} {2}");
         } else {
            pattern = bundle.getProperty("method.descr","Description of the Method");
         }
      } catch (Exception e) {
         pattern = "";
      }

      String message = createDescription(pattern, getAttributeName(), className);
      if (bundle.getInteger("javadoc.indent") < 1) {
         message = " " + message;
      } else {
         message = DescriptionPadder.padBuffer(message, bundle);
      }
      jdi.require("", message);
   }


   /**
    *  Description of the Method
    *
    * @param  value  Description of Parameter
    * @return        Description of the Returned Value
    */
   private String lowerCaseFirstLetter(String value) {
      if ((value == null) || (value.length() == 0)) {
         return "";
      }
      if (value.length() == 1) {
         return value.toLowerCase();
      }
      return Character.toLowerCase(value.charAt(0)) + value.substring(1);
   }
}

