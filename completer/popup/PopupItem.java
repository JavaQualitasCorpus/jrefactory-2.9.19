package org.acm.seguin.completer.popup;



import org.acm.seguin.completer.Config;
import org.acm.seguin.ide.jedit.Navigator.NavigatorLogger;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.completer.info.*;
import javax.swing.ImageIcon;
import java.lang.reflect.*;
//import anthelper.utils.ReflectUtils;

/**
 * Description of the Class
 *
 * @author    btateyama@yahoo.com
 * @created   December 11, 2002
 */
public abstract class PopupItem implements Comparable {
    static final NavigatorLogger logger = Completer.getLogger(PopupItem.class);
    // text to view in popup
    String _strData = null;
    String _strLeftText = "void";
    String _strFormattedString = null;

    // text to insert
    String _strBeforeCaret = null;    String _strAfterCaret = null;
    // underlying data source
    Object _objDataSource = null;
    // formatting info
    boolean _bUnderline = false;
    boolean _bBold = false;
    boolean _bLeftHighLight = true;
    ImageIcon _icon = null;
    int _iIconWidth = 0;
    
    protected PopupItem(){}
    
    void initFormatForMods(int argMods) {
        // perhaps add more here for bg color, etc.
        _bUnderline = Modifier.isStatic(argMods);
    }

    /**
     * Gets the beforeCaretString attribute of the PopupItem object
     *
     * @return   The beforeCaretString value
     */
    public String getBeforeCaretString() {
        return _strBeforeCaret;
    }

    /**
     * Gets the afterCaretString attribute of the PopupItem object
     *
     * @return   The afterCaretString value
     */
    public String getAfterCaretString() {
        return _strAfterCaret == null ? "" : _strAfterCaret;
    }

    /**
     * Gets the icon attribute of the PopupItem object
     *
     * @return   The icon value
     */
    public ImageIcon getIcon() {
        return _icon;
    }

    /**
     * Gets the returnType attribute of the PopupItem object
     *
     * @return   The returnType value
     */
    public String getLeftText() {
        return _strLeftText;
    }

    /**
     * Gets the dataSource attribute of the PopupItem object
     *
     * @return   The dataSource value
     */
    public Object getDataSource() {
        return _objDataSource;
    }

    /**
     * Description of the Method
     *
     * @return   Description of the Return Value1
     */
    public String toString() {
        return _strData;
    }

    /**
     * Description of the Method
     *
     * @param argPadLen  Description of the Parameter
     * @return           Description of the Return Value
     */
    public String toString(int argPadLen) {

        synchronized (this) {
            
            if (_strFormattedString == null) {
                StringBuffer sb = new StringBuffer();
                if(getLeftText().length() != 0){
                    // 1st pad to len
                    String strLeft = getLeftText();
                    for (int i = 0, l = argPadLen - strLeft.length(); i < l; i++) {
                        sb.append(" ");
                    }
                    sb.append(strLeft);
                }
                sb.append(_strData);
                _strFormattedString = sb.toString();
            }
        }
        return _strFormattedString;
    }

    /**
     * Description of the Method
     *
     * @return   Description of the Return Value
     */
    public boolean underLine() {
        return _bUnderline;
    }
    
    public boolean leftHighlight(){ return _bLeftHighLight; }
    /**
     * Gets the iconWidth attribute of the PopupItem object
     *
     * @return   The iconWidth value
     */
    public int getIconWidth() {
        return _iIconWidth;
    }

    /**
     * Description of the Method
     *
     * @param obj  Description of the Parameter
     * @return     Description of the Return Value
     */
    public int compareTo(Object obj) {
        return (obj instanceof PopupItem ?
            toString().compareTo(obj.toString()) :
            -1);
    }

    /**
     * Description of the Method
     *
     * @param obj  Description of the Parameter
     * @return     Description of the Return Value
     */
    public boolean equals(Object obj) {
        boolean bEq = obj == this;
        if (!bEq && obj != null) {
            PopupItem pi = (PopupItem) obj;
            bEq = (pi.toString().equals(toString()) &&
                pi.getBeforeCaretString().equals(getBeforeCaretString()) &&
                pi.getAfterCaretString().equals(getAfterCaretString()) &&
                pi.getDataSource().equals(getDataSource()));
        }
        return bEq;
    }

    
    static String getParamsDesc(String[] argParamTypes){
        StringBuffer sb = new StringBuffer("(");
        for (int i=0, l = argParamTypes.length; i < l; i++){
            sb.append(getPlainName(argParamTypes[i]));
            if (i+1 < l)
                sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }
    
    static String decodeTypeArray(char argChar){
        String str = null;
        switch(argChar){
            case 'B':
                str = "byte";
                break;
            case 'C':
                str = "char";
                break;
            case 'D':
                str = "double";
                break;
            case 'F':
                str = "float";
                break;
            case 'I':
                str = "int";
                break;
            case 'J':
                str = "long";
                break;
            case 'S':
                str = "short";
                break;
            case 'Z':
                str = "boolean";
                break;
                // ?? V void
        }
        return str;
    }
    static String getPlainName(String argType){
        StringBuffer sb = new StringBuffer();
        //logger.msg("type", argType);
        if (argType.startsWith("[")){
            //logger.msg("in stuff");
            // it's an array of some kind and some dimension
            StringBuffer sbDim= new StringBuffer();
            for (int i=0, l = argType.length(); 
                 i < l && argType.charAt(i) == '['; i++){
                sbDim.append("[]");
            }
            
            // we have accounted for dimension, now decode type
            int iL = argType.indexOf("[L");
            if (iL != -1){
                // we have a class of somekind
                String strFullName = argType.substring(iL+2, argType.indexOf(";"));
                sb.append(getPlainName(strFullName));
            }else{
                // we have a native type array (see Class.getName())
                String strNativeType = decodeTypeArray(argType.charAt(sbDim.length()/2));
                if (strNativeType != null){
                    sb.append(strNativeType);
                }
            }
            sb.append(sbDim.toString());
        }else{
            sb.append(getPlainName(argType));
        }
        return sb.toString();
    }

    public static String getPlainName2(String argName){
        String strPlainName = argName.substring(argName.lastIndexOf(".") + 1);
        //@REVIEW
        // We make a special case for the static field class.
        // It appears as class$<className with $ instead of .>
        // Ex: class$anthelper$ui$ConfigDialog
        if (strPlainName.startsWith("class$")){
            strPlainName = "class";
        }
        strPlainName = strPlainName.replace('$', '.');
        return strPlainName;
    }
    

}

