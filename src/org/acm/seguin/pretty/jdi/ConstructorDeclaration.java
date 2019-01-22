/*
 *  Author:  Mike Atkinson
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.jdi;

import java.text.MessageFormat;
import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTFormalParameters;
import net.sourceforge.jrefactory.ast.ASTFormalParameter;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;
import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.ASTNameList;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.ASTClassBody;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.Node;
import org.acm.seguin.pretty.ForceJavadocComments;
import org.acm.seguin.pretty.JavadocTags;
import org.acm.seguin.pretty.PrintData;
import org.acm.seguin.pretty.ai.RequiredTags;
import org.acm.seguin.util.FileSettings;


/**
 *  Description of the Class
 *
 * @author    Mike Atkinson
 * @since     jRefactory 2.9.0, created October 16, 2003
 */
public class ConstructorDeclaration extends BaseJDI {
   private ASTConstructorDeclaration constructor;



   /**
    *  Constructor for the ConstructorDeclaration JavaDoc creator.
    *
    * @param  constructor Create JavaDoc for this node.
    */
   public ConstructorDeclaration(ASTConstructorDeclaration constructor) {
      super();
      this.constructor = constructor;
   }


   /**
    *  Checks to see if it was printed
    *
    * @return    true if it still needs to be printed
    */
   public boolean isRequired() {
      return jdi.isRequired() && (new ForceJavadocComments()).isJavaDocRequired("method", constructor);
   }


   /**
    *  Prints all the java doc components
    *
    * @param  printData  the print data
    */
   public void printJavaDocComponents(PrintData printData) {
      jdi.printJavaDocComponents(printData, bundle.getString("method.tags"));
   }


   /**
    *  Makes sure all the java doc components are present. For methods and constructors we need to do more work -
    *  checking parameters, return types, and exceptions.
    */
   public void finish() {
      //  Local Variables
      int ndx;
      int childCount;

      //  Get the tags
      JavadocTags tags = JavadocTags.get();

      //  Description of the constructor
      Object[] nameArray = new Object[1];
      nameArray[0] = constructor.getName();
      String descr = tags.getConstructorDescr();
      if (isCopyConstructor()) {
         descr = bundle.getProperty("copyconstructor.descr","Copy constructor for the {0} object. Creates a copy of the {0} object parameter.");
      }
      String msg = MessageFormat.format(descr, nameArray);
      jdi.require("", msg);

      //  Check for parameters
      int childNo = constructor.skipAnnotations();
      ASTFormalParameters params = (ASTFormalParameters)constructor.jjtGetChild(childNo);
      childCount = params.jjtGetNumChildren();
      String[] constructorParams = new String[childCount];
      for (ndx = 0; ndx < childCount; ndx++) {
         ASTFormalParameter nextParam = (ASTFormalParameter)params.jjtGetChild(ndx);
         childNo = nextParam.skipAnnotations();
         ASTVariableDeclaratorId varID = (ASTVariableDeclaratorId)nextParam.jjtGetChild(childNo+1);
         jdi.require("@param", varID.getName(), getParameterDescription(bundle, tags, varID.getName(), "class"));

         constructorParams[ndx] = varID.getName();
      }
      // sort @param tags into order of constructor parameters
      jdi.sort("@param", constructorParams);

      //  Check for exceptions
      if ((constructor.jjtGetNumChildren() > childNo + 1) && (constructor.jjtGetChild(childNo + 1) instanceof ASTNameList)) {
         ASTNameList exceptions = (ASTNameList)constructor.jjtGetChild(childNo + 1);
         childCount = exceptions.jjtGetNumChildren();
         for (ndx = 0; ndx < childCount; ndx++) {
            ASTName name = (ASTName)exceptions.jjtGetChild(ndx);
            if (!(jdi.contains("@exception", name.getName()) ||
                  jdi.contains("@throws", name.getName()) ||
                  jdi.contains(tags.getExceptionTag(), name.getName())) ) {
               jdi.require(tags.getExceptionTag(), name.getName(), tags.getExceptionDescr());
            }
         }
      }

      RequiredTags.getTagger().addTags(bundle, "method", constructor.getName(), jdi);
   }


   /**
    *  Gets the parameterDescription attribute of the ASTConstructorDeclaration node.
    *
    * @param  bundle     Description of Parameter
    * @param  tags       Description of Parameter
    * @param  param      Description of Parameter
    * @param  className  Description of Parameter
    * @return            The parameterDescription value
    */
   private String getParameterDescription(FileSettings bundle, JavadocTags tags, String param, String className) {
      String pattern = "";
      Object[] nameArray = new Object[6];
      nameArray[0] = param;
      nameArray[1] = className;

      if (isCopyConstructor()) {
         pattern = bundle.getProperty("copyconstructor.param.descr","Object to copy.");
      } else {
         pattern = tags.getParamDescr();
      }

      if (pattern==null || pattern.equals("")) {
         return null;
      }

      return MessageFormat.format(pattern, nameArray);
   }


   /**
    *  Gets the copyConstructor attribute of the ASTConstructorDeclaration node.
    *
    * @return    The copyConstructor value
    */
   private boolean isCopyConstructor() {
      for (int childNo1 = 0; childNo1 < constructor.jjtGetNumChildren(); childNo1++) {
         Node child = constructor.jjtGetChild(childNo1);
         if (child instanceof ASTFormalParameters) {
            ASTFormalParameters params = (ASTFormalParameters)child;
            if (params.jjtGetNumChildren() == 1) {
               ASTFormalParameter param = (ASTFormalParameter)params.jjtGetChild(0);
               int childNo = param.skipAnnotations();
               ASTType type = (ASTType)param.jjtGetChild(childNo);
               if (type.jjtGetChild(0) instanceof ASTReferenceType) {
                  ASTReferenceType refType = (ASTReferenceType)type.jjtGetChild(0);
                  if (refType.getArrayCount() == 0) {
                     ASTClassOrInterfaceType clazz = (ASTClassOrInterfaceType)refType.jjtGetChild(0);
                     //System.out.println("isCopyConstructor refType name=" + refType.getName());
                     //System.out.println("isCopyConstructor type name=" + clazz.getName());
                     ASTClassBodyDeclaration classBodyDec = (ASTClassBodyDeclaration)constructor.jjtGetParent();
                     if (classBodyDec.jjtGetParent() instanceof ASTClassBody) {
                        ASTClassBody classBody = (ASTClassBody)classBodyDec.jjtGetParent();
                        //System.out.println("isCopyConstructor clazz name="+ clazz.getName() + ", compare with name="+ ((SimpleNode)classBody.jjtGetParent()).getName());
                        boolean flag = (clazz.getName().equals(((SimpleNode)classBody.jjtGetParent()).getName()));
                        //System.out.println("  -> "+flag);
                        return flag;
                     } else {
                        return false;
                     }
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }
      return false;
   }

}

