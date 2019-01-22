package org.acm.seguin.completer.popup;


import org.acm.seguin.completer.Config;
import org.acm.seguin.ide.jedit.Navigator;
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
public class SignaturePopupItem extends PopupItem {
    static final Navigator.NavigatorLogger logger = Completer.getLogger(SignaturePopupItem.class);
    
    public SignaturePopupItem(MemberInfo argMemberInfo){
        _objDataSource = argMemberInfo;
        
        if (argMemberInfo instanceof MethodInfo){
            initMethod((MethodInfo) argMemberInfo);
        }else if (argMemberInfo instanceof ConstructorInfo){
            initConstructor((ConstructorInfo) argMemberInfo);
        }
        // formatting extras
        initFormatForMods(argMemberInfo.getModifiers());
        // we insert nothing
        _strBeforeCaret = "";
        _strAfterCaret = "";
        //_bLeftHighLight = false;
        //_iIconWidth = PopupUtils.ICON_WIDTH;
    }
    
    void initMethod(MethodInfo argMethodInfo){
        StringBuffer sb = new StringBuffer();
        sb.append("  ");
        if (Config.SHOW_RETURN_TYPES.getAsBoolean()){
            sb.append(getPlainName(argMethodInfo.getReturnType()));
        }
        sb.append(" ").append(argMethodInfo.getName()).append("(");
        _strLeftText = sb.toString();
        _strData = getParamsDesc(argMethodInfo.getParameterTypes());
        _strData = _strData.substring(1, _strData.length());
    }
    
    void initConstructor(ConstructorInfo argInfo){
        StringBuffer sb = new StringBuffer();
        sb.append(" ").append(getPlainName(argInfo.getName())).append("(");
        _strLeftText = sb.toString();
        _strData = getParamsDesc(argInfo.getParameterTypes());
        _strData = _strData.substring(1, _strData.length());
    }
}

