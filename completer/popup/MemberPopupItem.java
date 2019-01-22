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
public class MemberPopupItem extends PopupItem {
    static final Navigator.NavigatorLogger logger = Completer.getLogger(MemberPopupItem.class);
    
    public MemberPopupItem(MemberInfo argMemberInfo){
        _objDataSource = argMemberInfo;
        if (argMemberInfo instanceof MethodInfo){
            initMethod((MethodInfo) argMemberInfo);
        }else if (argMemberInfo instanceof FieldInfo){
            initField((FieldInfo) argMemberInfo);
        }else if (argMemberInfo instanceof ClassInfo){
            initClass((ClassInfo) argMemberInfo);
        }
        // formatting extras
        initFormatForMods(argMemberInfo.getModifiers());
        if (!Config.SHOW_RETURN_TYPES.getAsBoolean()){
            _strLeftText = "";
        }
    }
    
    void initClass(ClassInfo argClassInfo){
        _strData = argClassInfo.getName();
        _strLeftText = " <inner> "; //getPlainName(argClassInfo.getType());
        _strBeforeCaret = _strData;
        _strAfterCaret = "";
        _icon = PopupUtils.ICON_INNER;
        _iIconWidth = PopupUtils.ICON_WIDTH;
    }
    
    void initField(FieldInfo argFieldInfo){
        _strData = argFieldInfo.getName();
        _strLeftText = " " + getPlainName(argFieldInfo.getType()) + " ";
        _strBeforeCaret = argFieldInfo.getName();
        _strAfterCaret = "";
        _icon = PopupUtils.ICON_FIELD;
        _iIconWidth = PopupUtils.ICON_WIDTH;
    }
    
    void initMethod(MethodInfo argMethodInfo){
        StringBuffer sb = new StringBuffer();
        sb.append(argMethodInfo.getName());
        if (Config.SHOW_PARAMS.getAsBoolean()){
            sb.append(getParamsDesc(argMethodInfo.getParameterTypes()));
        }else{
            sb.append("()");
        }
        _strData = sb.toString();
        _strLeftText = " " + getPlainName(argMethodInfo.getReturnType()) + " ";
        
        StringBuffer sbBefore = new StringBuffer();
        sbBefore.append(argMethodInfo.getName());
        if (argMethodInfo.getParameterTypes().length == 0) {
            sbBefore.append("()");
        } else {
            sbBefore.append("(");
            _strAfterCaret = ")";
        }
        _strBeforeCaret = sbBefore.toString();
        
        _icon = PopupUtils.ICON_METHOD;
        _iIconWidth = PopupUtils.ICON_WIDTH;
    }
}

