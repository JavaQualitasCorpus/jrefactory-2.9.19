package net.sourceforge.jrefactory.query;

import java.io.File;
import net.sourceforge.jrefactory.ast.ASTName;
import net.sourceforge.jrefactory.ast.SimpleNode;
import net.sourceforge.jrefactory.ast.ASTPackageDeclaration;
import net.sourceforge.jrefactory.factory.FileParserFactory;
import net.sourceforge.jrefactory.factory.ParserFactory;
import org.acm.seguin.awt.ExceptionPrinter;

/**
 *  Gets the package name
 *
 *@author     Chris Seguin
 *@created    November 23, 1999
 */
public class PackageNameGetter {
	/**
	 *  Return the package name
	 *
	 *@param  initialDir  Description of Parameter
	 *@param  filename    Description of Parameter
	 *@return             the package name
	 */
	public static ASTName query(File initialDir, String filename) {
		//  Create a factory to get a root
		File inputFile = new File(initialDir, filename);
		ParserFactory factory = new FileParserFactory(inputFile);
		SimpleNode root = factory.getAbstractSyntaxTree(false, ExceptionPrinter.getInstance());

		return query(root);
	}


	/**
	 *  Gets the package name
	 *
	 *@param  root  the syntax tree
	 *@return       the name of the package or null if there is none
	 */
	public static ASTName query(SimpleNode root) {
		if (root == null) {
			System.out.println("Unable to find the file!");
			return null;
		}

		SimpleNode first = (SimpleNode) root.jjtGetFirstChild();
		if (first instanceof ASTPackageDeclaration) {
			return (ASTName) first.jjtGetFirstChild();
		}

		return null;
	}
}
