package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTClassBodyDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedClassDeclaration;
import net.sourceforge.jrefactory.ast.ASTUnmodifiedInterfaceDeclaration;
import net.sourceforge.jrefactory.ast.SimpleNode;

public class ClassScopeEvaluator extends AbstractScopeEvaluator {
         public ClassScopeEvaluator() {
             triggers.add(ASTUnmodifiedClassDeclaration.class);
             triggers.add(ASTUnmodifiedInterfaceDeclaration.class);
             triggers.add(ASTClassBodyDeclaration.class);
         }
         public Scope getScopeFor(SimpleNode node) {
             if (node instanceof ASTClassBodyDeclaration) {
                 return new ClassScope();
             }
             return new ClassScope(node.getImage());
         }
     }

