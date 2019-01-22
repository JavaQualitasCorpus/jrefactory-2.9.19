package org.acm.seguin.completer.popup;

import org.acm.seguin.ide.jedit.Navigator.NavigatorLogger;
//import anthelper.utils.ReflectUtils;
import org.acm.seguin.completer.Completer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.event.*;
import org.gjt.sp.jedit.*;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.gui.KeyEventWorkaround;
import org.gjt.sp.jedit.textarea.*;

/**
 * The base popup
 *
 * @author    btateyama@yahoo.com
 * @created   December 10, 2002
 */
public abstract class BasePopup extends JWindow {
    final static NavigatorLogger logger = Completer.getLogger(BasePopup.class);

    /**
     * Description of the Field
     */
    protected View _view = null;
    
    /**
     * Description of the Field
     */
    protected JEditTextArea _textArea = null;
    /**
     * Description of the Field
     */
    protected Buffer _buffer = null;

    /**
     * Constructor for the BasePopup object
     *
     * @param argView  Description of the Parameter
     */
    protected BasePopup(View argView) {
        super(argView);
        _view = argView;
        _buffer = argView.getBuffer();
        _textArea = argView.getTextArea();
    }

    JScrollPane createScrollPane(JList argList) {
        // stupid scrollbar policy is an attempt to work around
        // bugs people have been seeing with IBM's JDK -- 7 Sep 2000
        JScrollPane scroller = new JScrollPane(
            argList,
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return scroller;
    }

    protected JPanel createMainPanel() {
        return
            new JPanel(new BorderLayout()) {
                /**
                 * Returns if this component can be traversed by pressing the
                 * Tab key. This returns false.
                 *
                 * @return   The managingFocus value
                 */
                public boolean isManagingFocus() {
                    return true;
                    //false;
                }

                /**
                 * Makes the tab key work in Java 1.4.
                 *
                 * @return   The focusTraversalKeysEnabled value
                 */
                public boolean getFocusTraversalKeysEnabled() {
                    return false;
                }
            };
    }
    /**
     * Called after super.show if popup is to be shown (i.e. no other visible popups)
     */
    protected abstract void postShow();

    /**
     * Description of the Method
     */
    public void show() {
        Completer dc = Completer.getDotCompleterForView(_view);
        if (dc.lockPopup(this)) {
            super.show();
            postShow();
        } else {
            super.dispose();
        }
    }
    
    /**
     * Dispose and unlock the popup
     */
    public void dispose() {
        super.dispose();
        try{
            Completer dc = Completer.getDotCompleterForView(_view);
            if (dc != null){
                // it will be null during final window closing
                dc.unlockPopup(this);
            }else{
                logger.warning("DC for view (" + _view.getName() + ") could not be found");
            }
            //DotCompletePlugin.getDotCompleterForView(_view).unlockPopup(this);
        }catch(Exception e){
            logger.error("Error while disposing popup!", e);
        }finally{
            // do this last in finally since it can mess up focus if not done.
            _view.setKeyEventInterceptor(null);
            PopupUtils.requestFocusOnTextArea(_textArea);
        }
    }

}

