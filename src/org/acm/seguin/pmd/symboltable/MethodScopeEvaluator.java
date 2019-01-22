package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTConstructorDeclaration;
import net.sourceforge.jrefactory.ast.ASTMethodDeclaration;
import net.sourceforge.jrefactory.ast.SimpleNode;

public class MethodScopeEvaluator extends AbstractScopeEvaluator {
         public MethodScopeEvaluator() {
             triggers.add(ASTConstructorDeclaration.class);
             triggers.add(ASTMethodDeclaration.class);
         }
         public Scope getScopeFor(SimpleNode node) {
             return new MethodScope();
         }
     }

