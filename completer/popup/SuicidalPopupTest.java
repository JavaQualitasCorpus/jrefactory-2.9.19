package org.acm.seguin.completer.popup;


import org.gjt.sp.jedit.jEdit;

import org.acm.seguin.ide.jedit.Navigator.NavigatorLogger;

import org.acm.seguin.completer.Completer;



public class SuicidalPopupTest{

    static final NavigatorLogger logger = Completer.getLogger(SuicidalPopupTest.class);

    public SuicidalPopupTest(){

        SuicidalMsgPopup sp = new SuicidalMsgPopup(jEdit.getActiveView(), 

                                                   "The temp message", 

                                                   1000*5);

        

    }

    public static void main(String[] args){

        try{

            

        }catch(Throwable t){

            t.printStackTrace();

        }

    }

    

}

