package org.acm.seguin.pmd;

import net.sourceforge.jrefactory.parser.JavaParser;

import java.io.InputStream;
import java.io.Reader;

public interface JLSVersion {
    public JavaParser createParser(InputStream in);
    public JavaParser createParser(Reader in);
}
