package org.acm.seguin.completer.popup;


import java.awt.event.MouseEvent;
import java.util.Collection;
import org.acm.seguin.completer.info.MemberInfo;
import java.util.Iterator;
import java.util.Set;
import org.acm.seguin.completer.info.ClassInfo;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.ide.jedit.Navigator;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import javax.swing.JList;
import org.gjt.sp.jedit.gui.KeyEventWorkaround;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.util.Vector;
import org.gjt.sp.jedit.View;



/**
 * Description of the Class
 *
 * @author    btateyama@yahoo.com
 * @created   December 14, 2002
 */
public class SignaturePopup extends CodePopup {
    static final Navigator.NavigatorLogger logger = Completer.getLogger(SignaturePopup.class);
    
    public static void showMethodPopup(View argView,
                                       Collection argMemberInfos,
                                       String argTag) {
        Vector vecItems = new Vector();
        
        for (Iterator it = argMemberInfos.iterator(); it.hasNext();){
            vecItems.add(new SignaturePopupItem((MemberInfo) it.next()));
        }
        SignaturePopup sp = new SignaturePopup(
            argView, "", vecItems, argTag);
        sp.show();
    }
    
    /**
     * Constructor for the SignaturePopup object
     *
     * @param argView    Description of the Parameter
     * @param argItems   Description of the Parameter
     * @param argTag     Description of the Parameter
     */
    public SignaturePopup(View argView,
                          Vector argItems,
                          String argTag) {
        super(argView, "", argItems, argTag);
    }
    
    /**
     * Constructor for the SignaturePopup object
     *
     * @param argView    Description of the Parameter
     * @param argItems   Description of the Parameter
     * @param argTag     Description of the Parameter
     */
    public SignaturePopup(View argView,
                          String argPrefix,
                          Vector argItems,
                          String argTag) {
        super(argView, argPrefix, argItems, argTag);
    }

    /**
     * Returns a KeyListener that will pass through key strokes
     * until the paren stack is 0 (matching close paren typed) or
     * ESC is pressed.
     * @param argPopup  Description of the Parameter
     * @param argView   Description of the Parameter
     * @return          Description of the Return Value
     */
    public KeyListener createKeyHandler(CodePopup argPopup,
                                        View argView) {
        return new ParenKeyHandler(argPopup, argView);
    }
    
        /**
     * Returns a mouse adapter that kills the popup on click
     *
     * @param argPopup  Description of the Parameter
     * @param argView   Description of the Parameter
     * @return          Description of the Return Value
     */
    public MouseListener createMouseHandler(final CodePopup argPopup,
                                                     View argView) {
        //logger.msg("creating click away handler");
        
        return
            new MouseAdapter() {
                public void mouseClicked(MouseEvent argEvent) {
                    argPopup.dispose();
                }
            };
    }
    class ParenKeyHandler extends KeyAdapter {
        CodePopup _codePopup = null;
        View _view = null;
        JList _listMembers = null;
        JEditTextArea _textArea = null;
        int _iParenStack = 0;
        int _iStartOffset = 0;
        ParenKeyHandler(CodePopup argPopup, View argView) {
            _codePopup = argPopup;
            _view = argView;
            _listMembers = argPopup.getListMembers();
            _textArea = argView.getTextArea();
            
            // find the first paren...maybe this should be passed in
            int iOffset = _textArea.getCaretPosition();
            for(char c='\b'; c != '(' && iOffset>=0 ; iOffset--){
                c = _view.getBuffer().getText(iOffset, 1).charAt(0);
                _iStartOffset = iOffset;
            }
        }
        
        
        char _cLast = '\b';
        boolean _bInSQuote = false;
        boolean _bInDQuote = false;
        boolean _bIsClosing = false;
        /**
         * Count the parens and pass through keys typed
         *
         * @param evt  Description of the Parameter
         */
        public void keyTyped(KeyEvent evt) {
            
            char ch = evt.getKeyChar();
            evt = KeyEventWorkaround.processKeyEvent(evt);
            if (evt == null) {
                return;
            } else if (ch != '\b' && !_bIsClosing) {
                
                // if char is not control character
                _textArea.userInput(ch);
                int iStack = getParenStack();
                // logger.msg("stack", iStack);
                
                if (iStack <= 0){
                    _codePopup.dispose();
                }
            }
        }
        
        int getParenStack(){
            int iStack = 0;
            
            // create stack of parens, hopefully ignoring string/char values
            int iEndOffset = _textArea.getCaretPosition();
            if (_iStartOffset >= 0 && iEndOffset > _iStartOffset){
                String strText = _view.getBuffer().getText(_iStartOffset, iEndOffset - _iStartOffset);
                char ch,cLast='\b';
                boolean bInDQuote = false, bInSQuote = false;
                for (int i=0, l = strText.length(); i < l; i++){
                    ch = strText.charAt(i);
                    if (ch == '(' && !bInDQuote && !bInSQuote){
                        iStack++;
                    }else if (ch == ')' && !bInDQuote && !bInSQuote){
                        iStack--;
                    }else if (ch == '"' && cLast != '\\'){
                        bInDQuote = !bInDQuote;
                    }else if (ch == '\'' && cLast != '\\'){
                        bInSQuote = !bInSQuote;
                    }
                    cLast = ch;
                }
            }
            return iStack;
        }
        
        /**
         * Description of the Method
         *
         * @param evt  Description of the Parameter
         */
        public void keyPressed(KeyEvent evt) {
            int iKeyCode = evt.getKeyCode();
            if (PopupUtils.isCompleteWord(evt)){
                // this is a hack to execute complete word
                iKeyCode = KeyEvent.VK_A;
            }
            /*
            if (evt.isShiftDown() && iKeyCode == KeyEvent.VK_TAB) {
                iKeyCode = KeyEvent.VK_DOWN;
            } else if (evt.isControlDown() && iKeyCode == KeyEvent.VK_P) {
                iKeyCode = KeyEvent.VK_UP;
            } else if (evt.isControlDown() && iKeyCode == KeyEvent.VK_N) {
                iKeyCode = KeyEvent.VK_DOWN;
            } 
            */
           switch (iKeyCode) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_TAB:
                
                case KeyEvent.VK_ESCAPE:
                    _codePopup.dispose();
                    evt.consume();
                    _bIsClosing = true;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    int offset = _textArea.getCaretPosition();
                    if (offset <= _iStartOffset+1){
                        _codePopup.dispose();
                        _bIsClosing = true;
                    }else{
                        _textArea.backspace();
                        evt.consume();
                    }
                    break;

            default:
                PopupUtils.processJEditCommand(
                       _view, _codePopup, evt);
                   break;
                   /*
                if (PopupUtils.processJEditCommand(_view, evt)){
                    _codePopup.dispose();
                } else if (evt.isActionKey()) {
                    _codePopup.dispose();
                    _view.processKeyEvent(evt);
                }
                break;
                */
            }
        }
    }
    
    
    
    public static void showConstructorPopup(View argView, 
                                            ClassInfo argClassInfo) {
        Vector vecItems = new Vector();
        Set setCons = argClassInfo.getConstructors();
        for (Iterator it = setCons.iterator(); it.hasNext();){
            vecItems.add(new SignaturePopupItem((MemberInfo) it.next()));
        }
        
        
        SignaturePopup sp = new SignaturePopup(
            argView, 
            vecItems, 
            argClassInfo.getFullName());
        sp.show();

    }


}



