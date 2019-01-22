/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import net.sourceforge.jrefactory.parser.JavaParserConstants;
import net.sourceforge.jrefactory.parser.Token;
import net.sourceforge.jrefactory.ast.ASTAnnotation;
import net.sourceforge.jrefactory.ast.ASTMemberValuePairs;
import net.sourceforge.jrefactory.ast.ASTMemberValuePair;
import net.sourceforge.jrefactory.ast.ASTMemberValue;
import net.sourceforge.jrefactory.ast.ASTMemberValueArrayInitializer;
import net.sourceforge.jrefactory.ast.ASTClassOrInterfaceType;

import net.sourceforge.jrefactory.ast.ASTFieldDeclaration;

import net.sourceforge.jrefactory.ast.ASTIdentifier;
import net.sourceforge.jrefactory.ast.ASTPrimitiveType;
import net.sourceforge.jrefactory.ast.ASTReferenceType;
import net.sourceforge.jrefactory.ast.ASTType;
import net.sourceforge.jrefactory.ast.ASTTypeArguments;
import net.sourceforge.jrefactory.ast.ASTVariableDeclaratorId;
import net.sourceforge.jrefactory.ast.AccessNode;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.Node;

/**
 *  Helps determine the size of a field for spacing purposes
 *
 * @author    Chris Seguin
 * @author    Mike Atkinson
 */
class FieldSizeLookAhead {
   private FieldSize fieldSize;
   private int code;


   /**
    *  Constructor for the FieldSizeLookAhead object
    *
    * @param  init  Description of Parameter
    */
   public FieldSizeLookAhead(int init) {
      fieldSize = new FieldSize();
      code = init;
   }


   /**
    *  Main processing method for the FieldSizeLookAhead object
    *
    * @param  body  Description of Parameter
    * @return       Description of the Returned Value
    */
   public FieldSize run(SimpleNode body) {
      int last = body.jjtGetNumChildren();
      for (int ndx = 0; ndx < last; ndx++) {
         Node child = body.jjtGetChild(ndx);
         Node grandchild = child.jjtGetFirstChild();
         if (grandchild instanceof ASTFieldDeclaration) {
            ASTFieldDeclaration field = (ASTFieldDeclaration)grandchild;
            if ((code != PrintData.DFS_NOT_WITH_JAVADOC) || !isJavadocAttached(field)) {
               int equalsLength = computeEqualsLength(field);
               fieldSize.setMinimumEquals(equalsLength);
            }
         }
      }

      return fieldSize;
   }


   /**
    *  Compute the size of the modifiers, type, and name
    *
    * @param  field  the field in question
    * @return        the size of the modifiers, type, and name
    */
   public int computeEqualsLength(ASTFieldDeclaration field) {
      int modifierLength = computeModifierLength(field);
      int typeLength = computeTypeLength(field);
      int nameLength = computeNameLength(field);

      int equalsLength = modifierLength + typeLength + nameLength;
      return equalsLength;
   }


   /**
    *  Computes the length of the field declaration type
    *
    * @param  field  the field
    * @return        the number
    */
   public int computeTypeLength(ASTFieldDeclaration field) {
      int childNo = field.skipAnnotations();
      ASTType typeNode = (ASTType)field.jjtGetChild(childNo);
      int typeLength = 0;
      //2 * typeNode.getArrayCount();
      if (typeNode.jjtGetFirstChild() instanceof ASTPrimitiveType) {
         ASTPrimitiveType primitive = (ASTPrimitiveType)typeNode.jjtGetFirstChild();
         typeLength += primitive.getName().length();
      } else if (typeNode.jjtGetFirstChild() instanceof ASTReferenceType) {
         typeLength += computeReferenceTypeLength((ASTReferenceType)typeNode.jjtGetFirstChild());
      } else {
         //System.out.println("ERROR: computeTypeLength typeNode.jjtGetFirstChild()="+typeNode.jjtGetFirstChild());
         //ASTName name = (ASTName) typeNode.jjtGetFirstChild();
         //typeLength += name.getName().length();
      }
      fieldSize.setTypeLength(typeLength);
      return typeLength;
   }


   /**
    *  Computes the length of the reference type declaration
    *
    * @param  reference  the field
    * @return            the number
    * @since             JRefactory 2.7.00
    */
   public int computeReferenceTypeLength(ASTReferenceType reference) {
      int typeLength = 0;
      Node child = reference.jjtGetFirstChild();
      if (child instanceof ASTPrimitiveType) {
         typeLength += ((ASTPrimitiveType)child).getName().length();
      } else if (child instanceof ASTClassOrInterfaceType) {
         typeLength += ((ASTClassOrInterfaceType)child).getName().length();
         // FIXME this does not take into account TypeArgument
      } else {
         // FIXME: sometimes the child is a CompilationUnit which should not be a child of a ASTReferenceType
         //System.out.println("Error: reference="+reference);
         //System.out.println("Error: reference.jjtGetChild(child)="+reference.jjtGetChild(child));
      }
      typeLength += reference.getArrayCount() * 2;
      for (int ndx = 1; ndx < reference.jjtGetNumChildren(); ndx++) {
         if (reference.jjtGetChild(ndx) instanceof ASTTypeArguments) {
            // FIXME: handle TypeArguments length
         }
      }

      return typeLength;
   }


   /**
    *  Gets the JavadocAttached attribute of the FieldSizeLookAhead object
    *
    * @param  node  Description of Parameter
    * @return       The JavadocAttached value
    */
   private boolean isJavadocAttached(ASTFieldDeclaration node) {
      int childNo = node.skipAnnotations();
      ASTType type = (ASTType)node.jjtGetChild(childNo);
      return
            hasJavadoc(node.getSpecial("static")) ||
            hasJavadoc(node.getSpecial("transient")) ||
            hasJavadoc(node.getSpecial("volatile")) ||
            hasJavadoc(node.getSpecial("final")) ||
            hasJavadoc(node.getSpecial("public")) ||
            hasJavadoc(node.getSpecial("protected")) ||
            hasJavadoc(node.getSpecial("private")) ||
            hasJavadoc(getInitialToken(type));
   }


   /**
    *  Check the initial token, and removes it from the object.
    *
    * @param  top  the type
    * @return      the initial token
    */
   private Token getInitialToken(ASTType top) {
      if (top.jjtGetFirstChild() instanceof ASTPrimitiveType) {
         ASTPrimitiveType primitiveType = (ASTPrimitiveType)top.jjtGetFirstChild();
         return primitiveType.getSpecial("primitive");
      } else if (top.jjtGetFirstChild() instanceof ASTReferenceType) {
         ASTReferenceType reference = (ASTReferenceType)top.jjtGetFirstChild();
         if (reference.jjtGetFirstChild() instanceof ASTPrimitiveType) {
            ASTPrimitiveType primitiveType = (ASTPrimitiveType)reference.jjtGetFirstChild();
            return primitiveType.getSpecial("primitive");
         } else {
            ASTClassOrInterfaceType name = (ASTClassOrInterfaceType)reference.jjtGetFirstChild();
            ASTIdentifier ident = (ASTIdentifier)name.jjtGetFirstChild();
            return ident.getSpecial("id");
         }
      } else {
         // FIXME: this should not occur now!
         ASTClassOrInterfaceType name = (ASTClassOrInterfaceType)top.jjtGetFirstChild();
         ASTIdentifier ident = (ASTIdentifier)name.jjtGetFirstChild();
         return ident.getSpecial("id");
      }
   }


   /**
    *  Description of the Method
    *
    * @param  field  Description of Parameter
    * @return        Description of the Returned Value
    */
   private int computeNameLength(ASTFieldDeclaration field) {
      int childNo = field.skipAnnotations();
      ASTVariableDeclaratorId id = (ASTVariableDeclaratorId)field.jjtGetChild(childNo+1).jjtGetFirstChild();
      int nameLength = id.getName().length();
      nameLength += id.getArrayCount()*2;
      fieldSize.setNameLength(nameLength);
      return nameLength;
   }


   /**
    *  Description of the Method
    *
    * @param  field  Description of Parameter
    * @return        Description of the Returned Value
    */
   private int computeModifierLength(ASTFieldDeclaration field) {
      int fieldLength = field.getModifiersString(PrintData.STANDARD_ORDER).length();
      fieldSize.setModifierLength(fieldLength);
      return fieldLength;
   }


   /**
    *  Description of the Method
    *
    * @param  tok  Description of Parameter
    * @return      Description of the Returned Value
    */
   private boolean hasJavadoc(Token tok) {
      Token current = tok;
      while (current != null) {
         if (current.kind == JavaParserConstants.FORMAL_COMMENT) {
            return true;
         }

         current = current.specialToken;
      }

      return false;
   }
}

