package abstracter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
class XMLDocument extends AbstractDocument {
	/**
	 *  Constructor for the XMLDocument object
	 *
	 *@param  data  Description of Parameter
	 */
	protected XMLDocument(Content data) {
		super(data);
	}


	/**
	 *  Constructor for the XMLDocument object
	 *
	 *@param  data     Description of Parameter
	 *@param  context  Description of Parameter
	 */
	protected XMLDocument(Content data, AttributeContext context) {
		super(data, context);
	}


	/**
	 *  Gets the DefaultRootElement attribute of the XMLDocument object
	 *
	 *@return    The DefaultRootElement value
	 */
	public Element getDefaultRootElement() {
	}


	/**
	 *  Gets the ParagraphElement attribute of the XMLDocument object
	 *
	 *@param  pos  Description of Parameter
	 *@return      The ParagraphElement value
	 */
	public Element getParagraphElement(int pos) {
	}
}
