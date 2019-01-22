/*
 *  Author:  Chris Seguin
 *
 *  This software has been developed under the copyleft
 *  rules of the GNU General Public License.  Please
 *  consult the GNU General Public License for more
 *  details about use and distribution of this software.
 */
package org.acm.seguin.pretty;

import java.io.File;
import org.acm.seguin.pretty.sort.MultipleOrdering;

/**
 *  Testing tool that creates a pretty print file that can be instrumented to
 *  update the print data object.
 *
 *@author    Chris Seguin
 */
class TestPrettyPrintFile extends PrettyPrintFile {
	private boolean reformatComments;
	private boolean ownline;
	private boolean standardIndent;
	private int singleLineIndent;
	private int fieldSpacing;
	private boolean keepAll;
	private boolean ownlineCode;
	private boolean sharedIncr;
	private int absoluteSpace;
	private boolean castSpace = true;
	private boolean documentNestedClasses = true;
	private int cStyleCommentForm = PrintData.CSC_ALIGN_STAR;
	private int cStyleIndent = 2;
	private boolean forceBlock = true;
	private int classBlockStyle = PrintData.BLOCK_STYLE_C;
	private int methodBlockStyle = PrintData.BLOCK_STYLE_C;
	private int codeBlockStyle = PrintData.BLOCK_STYLE_C;
	private boolean exprSpace = false;


	/**
	 *  Constructor for the TestPrettyPrintFile object
	 */
	public TestPrettyPrintFile()
	{
		super();

		reformatComments = true;
		ownline = true;
		standardIndent = false;
		singleLineIndent = 0;
		keepAll = false;
		ownlineCode = true;
		sharedIncr = true;
		absoluteSpace = 50;
	}


	/**
	 *  Sets the ReformatComments attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new ReformatComments value
	 */
	public void setReformatComments(boolean value)
	{
		reformatComments = value;
	}


	/**
	 *  Sets the SingleLineCommentOwnline attribute of the TestPrettyPrintFile
	 *  object
	 *
	 *@param  value  The new SingleLineCommentOwnline value
	 */
	public void setSingleLineCommentOwnline(boolean value)
	{
		ownline = value;
	}


	/**
	 *  Sets the SingleLineCommentStandardIndent attribute of the
	 *  TestPrettyPrintFile object
	 *
	 *@param  value  The new SingleLineCommentStandardIndent value
	 */
	public void setSingleLineCommentStandardIndent(boolean value)
	{
		standardIndent = value;
	}


	/**
	 *  Sets the SingleLineIndent attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new SingleLineIndent value
	 */
	public void setSingleLineIndent(int value)
	{
		singleLineIndent = value;
	}


	/**
	 *  Sets the KeepAll attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new KeepAll value
	 */
	public void setKeepAll(boolean value)
	{
		keepAll = value;
	}


	/**
	 *  Sets the SingleLineCommentOwnlineCode attribute of the
	 *  TestPrettyPrintFile object
	 *
	 *@param  value  The new SingleLineCommentOwnlineCode value
	 */
	public void setSingleLineCommentOwnlineCode(boolean value)
	{
		ownlineCode = value;
	}


	/**
	 *  Sets the SingleLineCommentSharedIncremental attribute of the
	 *  TestPrettyPrintFile object
	 *
	 *@param  value  The new SingleLineCommentSharedIncremental value
	 */
	public void setSingleLineCommentSharedIncremental(boolean value)
	{
		sharedIncr = value;
	}


	/**
	 *  Sets the AbsoluteSpace attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new AbsoluteSpace value
	 */
	public void setAbsoluteSpace(int value)
	{
		absoluteSpace = value;
	}


	/**
	 *  Sets the DynamicFieldSpacing attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new DynamicFieldSpacing value
	 */
	public void setDynamicFieldSpacing(int value)
	{
		fieldSpacing = value;
	}


	/**
	 *  Sets the CStyle attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new CStyle value
	 */
	public void setCStyle(int value)
	{
		cStyleCommentForm = value;
	}


	/**
	 *  Sets the CStyleIndent attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new CStyleIndent value
	 */
	public void setCStyleIndent(int value)
	{
		cStyleIndent = value;
	}


	/**
	 *  Sets the ForceBlock attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new ForceBlock value
	 */
	public void setForceBlock(boolean value)
	{
		forceBlock = value;
	}


	public void setClassBlockStyle(int value)
	{
		classBlockStyle = value;
	}


	/**
	 *  Sets the CastSpace attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new CastSpace value
	 */
	public void setCastSpace(boolean value)
	{
		castSpace = value;
	}


	/**
	 *  Sets the ExpressionSpace attribute of the TestPrettyPrintFile object
	 *
	 *@param  value  The new ExpressionSpace value
	 */
	public void setExpressionSpace(boolean value)
	{
		exprSpace = value;
	}


	/**
	 *  Sets the DocumentNestedClasses attribute of the TestPrettyPrintFile
	 *  object
	 *
	 *@param  value  The new DocumentNestedClasses value
	 */
	public void setDocumentNestedClasses(boolean value)
	{
		documentNestedClasses = value;
	}


	/**
	 *  Return the appropriate print data
	 *
	 *@param  input  Description of Parameter
	 *@return        the print data
	 */
	protected PrintData getPrintData(File input)
	{
		//  Create the new stream
		PrintData pd = new PrintData(getWriter(input));

		pd.setReformatComments(reformatComments);
		pd.setOwnline(ownline);
		pd.setAllJavadocKept(keepAll);
		pd.setSharedIncr(sharedIncr);
		pd.setOwnlineCode(ownlineCode);
		pd.setAbsoluteCommentSpacing(absoluteSpace);
		pd.setDynamicFieldSpacing(fieldSpacing);
		pd.setCStyleFormatCode(cStyleCommentForm);
		pd.setCStyleIndent(cStyleIndent);
		pd.setForceBlock(forceBlock);
		pd.setClassBlockStyle(classBlockStyle);
		pd.setMethodBlockStyle(methodBlockStyle);
		pd.setCodeBlockStyle(codeBlockStyle);
		pd.setCastSpace(castSpace);
		pd.setExpressionSpace(exprSpace);
		pd.setDocumentNestedClasses(documentNestedClasses);
		pd.setMultipleOrdering(morder);
		pd.setSingleLineJavadoc(allowSingleLineJavadocs);
		pd.setVariablesAlignWithBlock(variablesAlignWithBlock);
		pd.setElseOnNewLine(elseOnNewLine);
		pd.setCaseIndent(caseIndent);
		pd.setImportSortImportant(importSortImportant);
        pd.setImportSortNeighbourhood(importSortNeighbourhood);
        pd.setSortTop(sortTop);
        pd.setEmptyBlockOnSingleLine(emptyBlock);
        pd.setInsertSpaceLocalVariables(localVariableSpaceInsert);
        pd.setRemoveExcessBlocks(removeExcessBlocks);
        pd.setLinesAfterPackage(linesAfterPackage);
        pd.setMaintainNewlinesAroundImports(maintainNewlinesAroundImports);
        pd.setLinesBeforeClass(linesBeforeClass);
        pd.setIndentInInitializer(indentInitializer);
        pd.setBangSpace(bangSpace);
        pd.setKeepErroneousJavadocTags(keepErroneousJavadocTags);
        pd.setSpaceInsideCast(spaceInsideCast);
        pd.setSpaceAfterMethod(spaceAfterMethod);
        pd.setSpaceAroundOperators(spaceAroundOps);
        pd.setSpaceAfterKeyword(spaceAfterKeyword);
        pd.setArrayInitializerIndented(indentBracesInitializer);

	     pd.setLineBeforeClassBody(lineBeforeClassBody);
        pd.setLineBeforeExtends(lineBeforeExtends);
        pd.setExtendsIndentation(extendsIndentation);
        pd.setLineBeforeImplements(lineBeforeImplements);
        pd.setLineBeforeMultistatementMethodBody(lineBeforeMultistatementMethodBody);
        pd.setImplementsIndentation(implementsIndentation);
        pd.setArrayInitializerIndented(arrayInitializerIndented);
        pd.setModifierOrder(modifierOrder);
        pd.setAlignStarsWithSlash(alignStarsWithSlash);
        pd.setCStyleOwnline(c_ownline);
        pd.setLineUpParams(lineUpParams);
        pd.setSortThrowsStatement(sortThrowsStatement);
        pd.setSortExtendsStatement(sortExtendsStatement);
        pd.setSortImplementsStatement(sortImplementsStatement);

        //  Set the javadoc tags values
        JavadocTags jt = JavadocTags.get();
        jt.reload();

        jt.setExceptionTag(exceptionTag);
        if (paramDescr!=null) {
           jt.setParamDescr(paramDescr);
        }

		return pd;
	}
   
   private boolean sortThrowsStatement;
   public void setSortThrowsStatement(boolean sortThrowsStatement) {
      this.sortThrowsStatement = sortThrowsStatement;
   }

   private boolean sortExtendsStatement;
   public void setSortExtendsStatement(boolean sortExtendsStatement) {
      this.sortExtendsStatement = sortExtendsStatement;
   }

   private boolean sortImplementsStatement;
   public void setSortImplementsStatement(boolean sortImplementsStatement) {
      this.sortImplementsStatement = sortImplementsStatement;
   }

   private boolean lineUpParams = false;
   public void setLineUpParams(boolean value) {
      lineUpParams = value;
   }
   
	private MultipleOrdering morder = null;
	public void setMultipleOrdering(MultipleOrdering value) {
		morder = value;
	}
	public void setCodeBlockStyle(int value) { codeBlockStyle = value; }
	public void setMethodBlockStyle(int value) { methodBlockStyle = value; }
	private boolean allowSingleLineJavadocs = false;
	public void setSingleLineJavadocs(boolean value) { allowSingleLineJavadocs = value; }
	private boolean variablesAlignWithBlock = false;
	public void setVariablesAlignWithBlock(boolean value) { variablesAlignWithBlock = value; }
	private boolean elseOnNewLine = true;
	public void setElseOnNewLine(boolean value) { elseOnNewLine = value; }
	private int caseIndent = 1;
	public void setCaseIndent(int value) { caseIndent = value; }
        private String[] importSortImportant;
        private int importSortNeighbourhood;
        public void setImportSortImportant(String[] newImportSortImportant) {
                importSortImportant = newImportSortImportant;
        }
        public void setImportSortNeighbourhood(int newImportSortNeighbourhood) {
                importSortNeighbourhood = newImportSortNeighbourhood;
        }
	private boolean sortTop = false;
	public void setSortTop(boolean value) { sortTop = value; }
	private boolean emptyBlock = false;
	public void setEmptyBlockOnSingleLine(boolean value) { emptyBlock = value; }
	private boolean localVariableSpaceInsert = false;
	public void setInsertSpaceLocalVariables(boolean value) { localVariableSpaceInsert = value; }
	private boolean removeExcessBlocks;
	public void setRemoveExcessBlocks(boolean value) { removeExcessBlocks = value; }
	private int linesAfterPackage = 1;
	public void setLinesAfterPackage(int value) { linesAfterPackage = value; }
	private boolean maintainNewlinesAroundImports = true;
	public void setMaintainNewlinesAroundImports(boolean value) { maintainNewlinesAroundImports = value; }
	private int linesBeforeClass = 0;
	public void setLinesBeforeClass(int value) { linesBeforeClass = value; }
	private boolean indentInitializer = false;
	public void setIndentInInitializer(boolean value) { indentInitializer = value; }
	private boolean bangSpace = false;
	public void setBangSpace(boolean value) { bangSpace = value; }
	private String exceptionTag = "exception";
	public void setExceptionTag(String value) { exceptionTag = value; }
    private boolean keepErroneousJavadocTags = false;
    public void setKeepErroneousJavadocTags(boolean value) { keepErroneousJavadocTags = value; }
    private boolean spaceInsideCast = false;
    public void setSpaceInsideCast(boolean way) { spaceInsideCast = way; }
    private boolean spaceAfterMethod = false;
    public void setSpaceAfterMethod(boolean way) { spaceAfterMethod = way; }
    private boolean spaceAroundOps = true;
    public void setSpaceAroundOperators(boolean way) { spaceAroundOps = way; }
    private boolean spaceAfterKeyword = true;
    public void setSpaceAfterKeyword(boolean way) { spaceAfterKeyword = way; }
    private boolean indentBracesInitializer = true;
    public void setIndentBracesInitializer(boolean way) { indentBracesInitializer = way; }

    private int javadocMaximum = 80;
    public void setJavadocWordWrapMaximum(int wrap) { javadocMaximum = wrap; }
    private int javadocMinimum = 80;
    public void setJavadocWordWrapMinimum(int wrap) { javadocMinimum = wrap; }
    
    private String paramDescr = null;
    public void setParamDescr(String paramDescr) { this.paramDescr = paramDescr; }

	private boolean lineBeforeClassBody = false;
	public void setLineBeforeClassBody(boolean value) {
		lineBeforeClassBody = value;
	}
	private boolean lineBeforeExtends = false;
	public void setLineBeforeExtends(boolean value) {
		lineBeforeExtends = value;
	}
	private int extendsIndentation = 0;
	public void setExtendsIndentation(int value)
	{
		extendsIndentation = value;
	}
	private boolean lineBeforeImplements = false;
	public void setLineBeforeImplements(boolean value)
	{
		lineBeforeImplements = value;
	}
	private boolean lineBeforeMultistatementMethodBody = false;
	public void setLineBeforeMultistatementMethodBody(boolean value)
	{
		lineBeforeMultistatementMethodBody = value;
	}
	private int implementsIndentation = 0;
	public void setImplementsIndentation(int value)
	{
		implementsIndentation = value;
	}
	private boolean arrayInitializerIndented = true;
	public void setArrayInitializerIndented(boolean value) { arrayInitializerIndented = value; }
	private int modifierOrder = PrintData.ALPHABETICAL_ORDER;
	public void setModifierOrder(int value) { modifierOrder = value; }
	private boolean alignStarsWithSlash = false;
	public void setAlignStarsWithSlash(boolean value) { alignStarsWithSlash = value; }
        
        
	private boolean c_ownline = true;
        public void setCStyleOwnline(boolean ownline) {
            c_ownline = ownline;
        }
}
