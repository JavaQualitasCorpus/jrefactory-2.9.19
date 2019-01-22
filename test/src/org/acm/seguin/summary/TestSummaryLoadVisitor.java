package org.acm.seguin.summary;

import java.io.File;
import java.util.Iterator;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;

public class TestSummaryLoadVisitor extends DirSourceTestCase {
	public TestSummaryLoadVisitor(String name) { super(name); }

	public void test1() {
		File cleanDir = new File(clean);
		File destDir = new File(root + "\\test");
		destDir.mkdirs();

		File currentFile = new File(destDir, "ClassInMethod.java");

		(new FileCopy(
				new File(cleanDir, "test_ClassInMethod.java"),
				currentFile,
				false)).run();

		FileSummary result = FileSummary.getFileSummary(currentFile);

		Iterator typeIterator = result.getTypes();
		assertTrue("No types found", (typeIterator != null) && (typeIterator.hasNext()));

		TypeSummary next = (TypeSummary) typeIterator.next();
		Iterator methodIterator = next.getMethods();

		assertTrue("No methods found", (methodIterator != null) && (methodIterator.hasNext()));
	}
}
