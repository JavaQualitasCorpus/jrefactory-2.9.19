package org.acm.seguin.completer.popup;

import org.gjt.sp.jedit.View;

public interface InsertListener {
    public void insertPerformed(PopupItem argItem, 
                                String argPrefix, 
                                String argInsertedText, 
                                View argView);
}

