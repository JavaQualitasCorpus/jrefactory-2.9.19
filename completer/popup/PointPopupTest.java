package org.acm.seguin.completer.popup;

import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.acm.seguin.completer.info.FieldInfo;
import org.acm.seguin.completer.info.MemberInfo;
import java.util.Vector;
import java.util.Iterator;
import org.gjt.sp.jedit.jEdit;
import org.acm.seguin.completer.info.ClassInfo;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.ide.jedit.Navigator;


public class PointPopupTest {
    static final Navigator.NavigatorLogger logger = Completer.getLogger(PointPopupTest.class);
    
    
    void testPointPopup() {
        JEditTextArea textArea = jEdit.getActiveView().getTextArea();
        PointPopup pp = new PointPopup(jEdit.getActiveView(), "java.lang.String");
        
        pp.show(); //textArea, 100,100); 
    }
    
    public PointPopupTest(){
        try{
            logger.debug("huh?");
            testPointPopup();
        }catch(Exception e){
            logger.error("Error testing JavaParser", e);
        }
    }
    
    public static void main(String[] args){
        try{
            //new CodePopupTest();
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

}

