package org.acm.seguin.completer.popup;

import java.util.Vector;
import org.gjt.sp.jedit.jEdit;
//import anthelper.JavaUtils;
import org.gjt.sp.jedit.View;
import java.util.Set;
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
public class ClassPopupItem extends PopupItem { 

    static final Navigator.NavigatorLogger logger = Navigator.getLogger(ClassPopupItem.class);
    
    public ClassPopupItem(String argName){
        _objDataSource = argName;
        
        _strData = getPlainName(argName);
        int iTruncateLen = 20;
        String strPkg = argName.substring(0, argName.length() - _strData.length());
        if (strPkg.length() > iTruncateLen){
            strPkg = " " + strPkg.substring(0, iTruncateLen - 2) + "~.";
        }
        _strLeftText = strPkg;
        
        _strBeforeCaret = _strData;
        _strAfterCaret = "";
    }
    
    public static void main(String[] args){
        try{
            ClassPopupItem pi = new ClassPopupItem("java.util.foo.bar.NameFrame");
            logger.msg("left", pi._strLeftText);
            logger.msg("Data", pi._strData);
        }catch(Throwable t){
            t.printStackTrace();
        }
    }


    public static class ImportInsertListener implements InsertListener {
        Set _setImports = null;
        boolean _bAutoImport = false;
        boolean _bPopupMsg = false;
        public ImportInsertListener(Set argImportSet, 
                                    boolean argAutoImport, 
                                    boolean argPopupMsg){
            _setImports = argImportSet;
            _bAutoImport = argAutoImport;
            _bPopupMsg = argPopupMsg;
        }
        public void insertPerformed(PopupItem argItem, 
                                    String argPrefix, 
                                    String argInsertedText, 
                                    View argView){

            if (argItem instanceof ClassPopupItem){
                String strMsg, strCN = argItem.getDataSource().toString(); 
                if (!_setImports.contains(strCN)){
                    if(_bAutoImport){
                        //FIXME: JavaUtils.doImport(argView, strCN);
                        strMsg = "Imported " + strCN;
                    }else{
                        strMsg = "Missing import: " + strCN;
                    }
                    logger.msg(strMsg);
                    if (_bPopupMsg){
                        new SuicidalMsgPopup(argView, strMsg, 1000*3);
                    }
                }
            }
        }
    }


}



