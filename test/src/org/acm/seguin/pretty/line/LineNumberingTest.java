/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty.line;

import org.acm.seguin.tools.builder.LineNumberTool;
import java.io.File;
import org.acm.seguin.io.FileCopy;
import org.acm.seguin.junit.DirSourceTestCase;
import org.acm.seguin.junit.FileCompare;


/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 *@created    February 22, 2002
 */
public class LineNumberingTest extends DirSourceTestCase
{
    private File checkDir;
    private File cleanDir;
    private File destDir;


    /**
     *  Constructor for the LineNumberingTest object
     *
     *@param  name  Description of Parameter
     */
    public LineNumberingTest(String name)
    {
        super(name);
    }


    /**
     *  A unit test for JUnit
     */
    public void test01()
    {
        File dest = new File(destDir, "SingleLineComments.java");
        (new FileCopy(
                new File(cleanDir, "SingleLineComments.java"),
                dest, false)).run();

        String path = dest.getPath();

        String[] args = new String[]{
                "-out",
                destDir.getPath() + "\\shortfile.txt",
                path
                };
        LineNumberTool.main(args);

        FileCompare.assertEquals("SingleLineComments.java in error",
                new File(checkDir, "shortfile.txt"),
                new File(destDir, "shortfile.txt"));
    }


    /**
     *  A unit test for JUnit
     */
    public void test02()
    {
        (new FileCopy(
                new File(cleanDir, "TryTest.java"),
                new File(destDir, "TryTest.java"),
                false)).run();
        (new FileCopy(
                new File(cleanDir, "LoadingProblem.java"),
                new File(destDir, "LoadingProblem.java"),
                false)).run();
        (new FileCopy(
                new File(cleanDir, "SingleLineComments.java"),
                new File(destDir, "SingleLineComments.java"),
                false)).run();

        String path = destDir.getPath();

        String[] args = new String[]{
                "-out",
                path + "\\longfile.txt",
                path + "\\TryTest.java",
                path + "\\LoadingProblem.java",
                path + "\\SingleLineComments.java"
                };
        LineNumberTool.main(args);

        FileCompare.assertEquals("Multiple files in error",
                new File(checkDir, "longfile.txt"),
                new File(destDir, "longfile.txt"));
    }


    /**
     *  The JUnit setup method
     */
    protected void setUp()
    {
        cleanDir = new File(clean + "/pretty");
        destDir = new File("./test/temp");
        checkDir = new File(check + "/lineno");
    }
}
