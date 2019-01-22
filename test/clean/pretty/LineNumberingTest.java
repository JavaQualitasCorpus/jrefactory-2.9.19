package org.acm.seguin.pretty.line;

import LineNumberTool;
import java.io.File;
import org.acm.seguin.io.FileCopy;
import junit.framework.TestCase;

public class LineNumberingTest extends TestCase {
private File cleanDir;
private File destDir;
private File checkDir;
public LineNumberingTest(String name) {
super(name);
}
protected void setUp() {
cleanDir = new File("C:\\chris\\test\\clean\\pretty");
destDir = new File("C:\\chris\\test\\temp");
checkDir = new File("C:\\chris\\test\\check\\lineno");
}
public void test01() {
File dest = new File(destDir, "yesnodialog.java");
(new FileCopy(
new File(cleanDir, "yesnodialog.java"),
dest, false)).run();
String path = dest.getPath();
String[] args = new String[]{
"-out",
destDir.getPath() + "\\shortfile.txt",
path
};
LineNumberTool.main(args);
assertFilesEqual("yesnodialog.java in error",
new File(checkDir, "shortfile.txt"),
new File(destDir, "shortfile.txt"));
}
public void test02() {
(new FileCopy(
new File(cleanDir, "yesnodialog.java"),
new File(destDir, "yesnodialog.java"),
false)).run();
(new FileCopy(
new File(cleanDir, "comdialog.java"),
new File(destDir, "comdialog.java"),
false)).run();
(new FileCopy(
new File(cleanDir, "CommentTag.java"),
new File(destDir, "CommentTag.java"),
false)).run();
String path = destDir.getPath();
String[] args = new String[]{
"-out",
path + "\\longfile.txt",
path + "\\yesnodialog.java",
path + "\\comdialog.java",
path + "\\CommentTag.java"
};
LineNumberTool.main(args);
assertFilesEqual("Multiple files in error",
new File(checkDir, "longfile.txt"),
new File(destDir, "longfile.txt"));
}
}
