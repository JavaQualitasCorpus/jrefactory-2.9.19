package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.ASTBlock;
import net.sourceforge.jrefactory.ast.ASTForStatement;
import net.sourceforge.jrefactory.ast.ASTIfStatement;
import net.sourceforge.jrefactory.ast.ASTSwitchStatement;
import net.sourceforge.jrefactory.ast.ASTTryStatement;
import net.sourceforge.jrefactory.ast.SimpleNode;

public class LocalScopeEvaluator extends AbstractScopeEvaluator {
         public LocalScopeEvaluator() {
             triggers.add(ASTBlock.class);
             triggers.add(ASTTryStatement.class);
             triggers.add(ASTForStatement.class);
             triggers.add(ASTSwitchStatement.class);
             triggers.add(ASTIfStatement.class);
         }
         public Scope getScopeFor(SimpleNode node) {
             return new LocalScope();
         }
     }

