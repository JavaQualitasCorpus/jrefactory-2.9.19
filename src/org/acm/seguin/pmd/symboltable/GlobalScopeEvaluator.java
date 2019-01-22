package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTCompilationUnit;
import net.sourceforge.jrefactory.ast.SimpleNode;

public class GlobalScopeEvaluator extends AbstractScopeEvaluator {
         public GlobalScopeEvaluator() {
             triggers.add(ASTCompilationUnit.class);
         }
         public Scope getScopeFor(SimpleNode node) {
             return new GlobalScope();
         }
     }

