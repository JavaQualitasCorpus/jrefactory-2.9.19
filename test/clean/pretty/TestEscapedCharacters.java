package test;

public class TestEscapedCharacters {
	private char tab = '\t';;;;;
	private char newline = '\n';
	private char doublequote = '\"';
	private char unicode = '\u0099';
	private char backslash = '\\';

	private String tabString = "\t";
	private String newlineString = "\n";
	private String quotation = "The minister said, \"God be with you.\"";
	private String international = "\u0099\u0020\u0098\u0020\u0097";
	private String almostInterational = "\\u0099";
}
