package org.acm.seguin.completer.popup;

import org.acm.seguin.completer.info.FieldInfo;
import org.acm.seguin.completer.info.MemberInfo;
import java.util.Vector;
import java.util.Iterator;
import org.gjt.sp.jedit.jEdit;
import org.acm.seguin.completer.info.ClassInfo;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.ide.jedit.Navigator.NavigatorLogger;



public class CodePopupTest {
    static final NavigatorLogger logger =  Completer.getLogger(CodePopupTest.class);
    
    
    void testMemberPopup() {
        ClassInfo ci = new ClassInfo(Character.class);
        Vector vecItems = new Vector();
        for (Iterator it = ci.getMethods().iterator(); it.hasNext();){
            vecItems.add(new MemberPopupItem((MemberInfo)it.next()));
        }
        for (Iterator it = ci.getFields().iterator(); it.hasNext();){
            vecItems.add(new MemberPopupItem((FieldInfo)it.next()));
        }
        for (Iterator it = ci.getClasses().iterator(); it.hasNext();){
            vecItems.add(new MemberPopupItem((ClassInfo)it.next()));
        }
        
        CodePopup cp  = new CodePopup(jEdit.getActiveView(), 
                                        "",
                                        vecItems,
                                        ci.getFullName());
        cp.show();
        
        
    }
    void testConsSigPopup(){
        ClassInfo ci = new ClassInfo(String.class);
        SignaturePopup.showConstructorPopup(jEdit.getActiveView(), 
                                            ci);
    }
    void testClassPopup(){
        Vector vecItems = new Vector();
        vecItems.add(new ClassPopupItem("java.util.Vector"));
        vecItems.add(new ClassPopupItem("java.util.Map"));
        vecItems.add(new ClassPopupItem("java.util.HashMap"));
        vecItems.add(new ClassPopupItem("java.util.Hashtable"));
        
        new CodePopup(jEdit.getActiveView(), 
                      "",
                      vecItems, null);

    }
    public CodePopupTest(){
        try{
            testMemberPopup();
            
            //testConsSigPopup();
            //testClassPopup();
            logger.error("hellow2");
        }catch(Exception e){
            logger.error("Error testing JavaParser", e);
        }
    }
    
    public static void main(String[] args){
        try{
            new CodePopupTest();
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
    
}

