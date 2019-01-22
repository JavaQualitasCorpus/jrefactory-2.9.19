package org.acm.seguin.completer.popup;

import org.acm.seguin.ide.jedit.Navigator;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.completer.Completer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import javax.swing.Timer;
import javax.swing.border.*;
import javax.swing.event.*;
import org.gjt.sp.jedit.*;
import org.gjt.sp.jedit.gui.KeyEventWorkaround;
import org.gjt.sp.jedit.textarea.*;


/**
 * A message popup that will kill itself in a given time frame
 *
 * @author    btateyama@yahoo.com
 * @created   December 10, 2002
 */
public class SuicidalMsgPopup extends BasePopup {
    final static Navigator.NavigatorLogger logger = Completer.getLogger(SuicidalMsgPopup.class);
    

    String _strMsg = null;
    int _iTimeToLiveMs = 1000;
    Timer _timer = null;

    /**
     * Constructor for the CodePopup object
     */
    public SuicidalMsgPopup(View argView, 
                            String argMsg,
                            int argTimeToLiveMs){
        this(argView, argMsg, argTimeToLiveMs, Color.yellow, Color.black);
    }    
    /**
     * Constructor for the CodePopup object
     */
    public SuicidalMsgPopup(View argView, 
                            String argMsg,
                            int argTimeToLiveMs, 
                            Color argBGColor, 
                            Color argFGColor){

        super(argView);
        
        _view = argView;
        _buffer = argView.getBuffer();
        _textArea = argView.getTextArea();
        _iTimeToLiveMs = argTimeToLiveMs;
        _strMsg = argMsg;
        _timer = new Timer(_iTimeToLiveMs, new ActionListener(){
            public void actionPerformed(ActionEvent argEvent){
                dispose();
            }
        });
        
        setContentPane(createMainPanel());
        getContentPane().add(createLabel(argMsg, argBGColor, argFGColor), BorderLayout.CENTER);
        pack();
        
        Point point = PopupUtils.getPointOnTextArea(argView, "");
        //point.x += 2;
        point.y += 2;
        setLocation(point);
    }
    
    protected void postShow(){
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        _view.setKeyEventInterceptor(keyHandler);
        _timer.start();
    }
    
    public void dispose(){
        super.dispose();
        _timer.stop();
    }
    Component createLabel(String argText, Color argBGColor, Color argFGColor){
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(argFGColor, 1));
        panel.setBackground(argBGColor);
        
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        JLabel label = new JLabel(argText);
        label.setForeground(argFGColor);
        label.addMouseListener(new MouseHandler());
        panel.add(label, c);
        return panel;
    }
    
    
    class KeyHandler extends KeyAdapter {
        /**
         * Description of the Method
         *
         * @param evt  Description of the Parameter
         */
         public void keyPressed(KeyEvent evt) {
            // spoof keycodes to extend functionality
            int iKeyCode = evt.getKeyCode();
            if (PopupUtils.isCompleteWord(evt)){
                // this is a hack to execute complete word
                iKeyCode = KeyEvent.VK_A;
            }

            /*
            if (evt.isControlDown() || evt.isAltDown() ){
                iKeyCode = KeyEvent.VK_ESCAPE;
            }
            */
            switch (iKeyCode) {
                case KeyEvent.VK_TAB:
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_ENTER:
                    dispose();
                    evt.consume();
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    _textArea.backspace();
                    dispose();
                    evt.consume();
                    break;
                default:
                /*
                    if (PopupUtils.processJEditCommand(_view, evt)){
                        dispose();
                    } else if (evt.isActionKey()) {
                        dispose();
                        _view.processKeyEvent(evt);
                    }
                    */
                   PopupUtils.processJEditCommand(
                       _view, SuicidalMsgPopup.this, evt);
                   break;
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
                _textArea.userInput(ch);
            } 
            dispose();
        }
    }
    
    class MouseHandler extends MouseAdapter {
        /**
         * Description of the Method
         *
         * @param evt  Description of the Parameter
         */
        public void mouseClicked(MouseEvent evt) {
            dispose();
        }
    }
}

