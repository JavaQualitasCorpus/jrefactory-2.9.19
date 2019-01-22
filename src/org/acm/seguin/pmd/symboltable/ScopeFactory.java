package org.acm.seguin.pmd.symboltable;

import net.sourceforge.jrefactory.ast.SimpleNode;

import java.util.Stack;

public interface ScopeFactory {
    void openScope(Stack scopes, SimpleNode node);
}
