package org.acm.seguin.pretty;

import junit.framework.TestCase;
import net.sourceforge.jrefactory.parser.Token;

public class TestJavadocTokenizer extends TestCase {
	public TestJavadocTokenizer(String name) { super(name); }

	public void test01() {
		String content = "****    \n" +
				"\t\t*  This software is supposed to <B>break</B>\n" +
				"\t\t*  this message up into <strong>line<em>s</em></strong>\n" +
				"\t\t*  @param input the input to this method\n" +
				"\t\t*  @version @version@\n" +
				"\t\t*";

		int[] desired = {
				JavadocTokenizer.NEWLINE,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.NEWLINE,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.WORD,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.WORD,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.WORD,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.WORD,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.WORD,
				JavadocTokenizer.SPACE,
				JavadocTokenizer.WORD,
				JavadocTokenizer.WORD,
				JavadocTokenizer.WORD,
				JavadocTokenizer.NEWLINE};
		JavadocTokenizer tokenizer = new JavadocTokenizer(content);

		int current = 0;
		while (tokenizer.hasNext()) {
			Token word = tokenizer.next();
			if (current < desired.length) {
				assertEquals("Correct type for index:  " + current, desired[current], word.kind);
			}
			current++;
		}

		assertEquals("Correct number of tokens", 54, current);
	}
}
