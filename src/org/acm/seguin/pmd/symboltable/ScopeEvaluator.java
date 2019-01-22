package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.SimpleNode;

public interface ScopeEvaluator {
    public Scope getScopeFor(SimpleNode node);
    public boolean isScopeCreatedBy(SimpleNode node);
}
