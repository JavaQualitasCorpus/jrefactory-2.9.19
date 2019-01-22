package org.acm.seguin.completer.popup;



import org.gjt.sp.jedit.gui.KeyEventWorkaround;

import java.awt.event.KeyEvent;

import javax.swing.JList;

import org.gjt.sp.jedit.textarea.JEditTextArea;

import org.gjt.sp.jedit.View;

import java.awt.event.KeyAdapter;



/**

 * Description of the Class

 *

 * @author    btateyama@yahoo.com

 * @created   December 13, 2002

 */

public class DefaultKeyHandler extends KeyAdapter {

    boolean _bDisposeOnNextChar = false;

    

    View _view = null;

    JEditTextArea _textArea = null;

    JList _listMembers = null;

    CodePopup _codePopup = null;



    DefaultKeyHandler(CodePopup argPopup, 

                      View argView){

        _codePopup = argPopup;

        _view = argView;

        _textArea = argView.getTextArea();

        _listMembers = _codePopup.getListMembers();

        

    }

    

    

    /**

     * Description of the Method

     *

     * @param evt  Description of the Parameter

     */

    public void keyPressed(KeyEvent evt) {

        // spoof keycodes to extend functionality

        int iKeyCode = evt.getKeyCode();

        



        

        if (evt.isShiftDown() && iKeyCode == KeyEvent.VK_TAB) {

            iKeyCode = KeyEvent.VK_DOWN;

        } else if (evt.isControlDown() && iKeyCode == KeyEvent.VK_P) {

            iKeyCode = KeyEvent.VK_UP;

        } else if (evt.isControlDown() && iKeyCode == KeyEvent.VK_N) {

            iKeyCode = KeyEvent.VK_DOWN;

        } else if (iKeyCode == KeyEvent.VK_TAB) {

            iKeyCode = KeyEvent.VK_ENTER;

        }

        switch (iKeyCode) {

            case KeyEvent.VK_TAB:

            case KeyEvent.VK_ENTER:

                _codePopup.insertSelected();

                evt.consume();

                break;

            case KeyEvent.VK_ESCAPE:

                _codePopup.dispose();

                evt.consume();

                break;

            case KeyEvent.VK_UP:

                int selected = _listMembers.getSelectedIndex();



                if (selected == 0) {

                    selected = _listMembers.getModel().getSize() - 1;

                } else if (_codePopup.getFocusOwner() == _listMembers) {

                    return;

                } else {

                    selected = selected - 1;

                }



                _listMembers.setSelectedIndex(selected);

                _listMembers.ensureIndexIsVisible(selected);



                evt.consume();

                break;

            case KeyEvent.VK_DOWN:

                /*

                 *  int

                 */

                selected = _listMembers.getSelectedIndex();



                if (selected == _listMembers.getModel().getSize() - 1) {

                    selected = 0;

                } else if (_codePopup.getFocusOwner() == _listMembers) {

                    return;

                } else {

                    selected = selected + 1;

                }



                _listMembers.setSelectedIndex(selected);

                _listMembers.ensureIndexIsVisible(selected);



                evt.consume();

                break;

            case KeyEvent.VK_BACK_SPACE:

                String strPrefix = _codePopup.getPrefix();

                if (strPrefix.length() <= 1) {

                    _textArea.backspace();

                    evt.consume();

                    _codePopup.dispose();

                } else {

                    _codePopup.setPrefix(strPrefix.substring(0, strPrefix.length() - 1));

                    _textArea.backspace();



                    // filter for data on current prefix

                    _codePopup.filterData();

                    evt.consume();

                }

                break;

            default:

                PopupUtils.processJEditCommand(

                       _view, _codePopup, evt);

                   break;

                   /*

                // we check to see if keystroke is jEdit command...if so, let jEdit do it's thing

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





    /**

     * Description of the Method

     *

     * @param evt  Description of the Parameter

     */

    public void keyTyped(KeyEvent evt) {

        char ch = evt.getKeyChar();

        evt = KeyEventWorkaround.processKeyEvent(evt);

        if (evt == null) {

            return;

        } else if (ch != '\b') {

            // if char is not control character

            if (_bDisposeOnNextChar) {

                _textArea.userInput(ch);

                _codePopup.dispose();

            } else {

                _codePopup.setPrefix(_codePopup.getPrefix() + ch);

                //_strPrefix = _strPrefix + ch;

                _textArea.userInput(ch);

                _codePopup.filterData();

                // setup to close after next char is typed after ( typed

                //_bDisposeOnNextChar = ch == '(';

                if (ch == '(') 

                    _codePopup.dispose();

            }

        }

    }

}



