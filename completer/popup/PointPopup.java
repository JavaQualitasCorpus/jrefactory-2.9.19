package org.acm.seguin.completer.popup;


//import anthelper.adapters.Plugin;
//import anthelper.adapters.Plugin;
import javax.swing.AbstractAction;
//import anthelper.adapters.JIndexUtils;
//import anthelper.utils.SrcInfoUtils;
//import anthelper.JavaUtils;
import javax.swing.BoxLayout;
import org.acm.seguin.ide.jedit.Navigator;
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
 * A popup offering some options about the code at point.
 *
 * @author    btateyama@yahoo.com
 * @created   December 10, 2002
 */
public class PointPopup extends BasePopup {
    final static Navigator.NavigatorLogger logger = Completer.getLogger(PointPopup.class);

    JMenuItem _miJavaDoc = null;
    JMenuItem _miClassBrowser = null;
    JMenuItem _miCopy = null;
    JMenuItem _miCancel = null;
    /**
     * Description of the Field
     */
    protected String _strType = null;
    /**
     * Description of the Field
     */
    protected View _view;


    /**
     * Constructor for the PointPopup object
     *
     * @param argView  Description of the Parameter
     * @param argType  Description of the Parameter
     */
    public PointPopup(View argView, String argType) {
        super(argView);
        _view = argView;
        _strType = argType;

        setContentPane(createRootPanel());
        pack();

        Point point = PopupUtils.getPointOnTextArea(_view, "");
        setLocation(point.x, point.y + 2);
    }


    /**
     * Description of the Method
     */
    protected void postShow() {
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        _view.setKeyEventInterceptor(keyHandler);
    }

    void handleByMnemonic(int argMnemonic) {
        if (argMnemonic == 'D' || argMnemonic == 'd') {
            //FIXME: if (Plugin.JINDEX.isInstalled()) {
            //FIXME:     JIndexUtils.lookUp(_view, _strType);
            //FIXME: }
        } else if (argMnemonic == 'B' || argMnemonic == 'b') {
            //FIXME: JavaUtils.viewClassInfo(_view, _strType);
        } else if (argMnemonic == 'C' || argMnemonic == 'c') {
            Registers.setRegister('$', _strType);
            logger.msg("Set register $", _strType);
        } else if (argMnemonic == 'N' || argMnemonic == 'n') {
            logger.msg("Closing");
        }
    }

    JPanel createRootPanel() {
        _miJavaDoc = createMenuItem("Java doc (JIndex)", 'D');
        _miJavaDoc.setEnabled(false); //FIXME: Plugin.JINDEX.isInstalled());
        _miClassBrowser = createMenuItem("View in Class Browser", 'B');
        _miCopy = createMenuItem("Copy", 'C');
        _miCancel = createMenuItem("Cancel", 'N');

        JLabel labelType = new JLabel(" " + _strType);
        labelType.setFont(labelType.getFont().deriveFont(Font.BOLD));
        labelType.addMouseListener(new MouseHandler());
        
        // get the default, focus handling panel
        JPanel panel = createMainPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(labelType);
        panel.add(new JPopupMenu.Separator());
        panel.add(_miClassBrowser);
        panel.add(_miJavaDoc);
        panel.add(new JPopupMenu.Separator());
        panel.add(_miCopy);
        panel.add(new JPopupMenu.Separator());
        panel.add(_miCancel);

        return panel;
    }

    JMenuItem createMenuItem(String argText, char argMnemonic) {
        JMenuItem mi = new JMenuItem(argText);
        mi.setMnemonic(argMnemonic);
        mi.addActionListener(
            new AbstractAction() {
                public void actionPerformed(ActionEvent argEvent) {
                    JMenuItem mi = (JMenuItem) argEvent.getSource();
                    handleByMnemonic(mi.getMnemonic());
                    dispose();
                }
            });
        return mi;
    }

    class KeyHandler extends KeyAdapter {
        /**
         * Description of the Method
         *
         * @param evt  Description of the Parameter
         */
        public void keyPressed(KeyEvent evt) {
            
            handleByMnemonic(evt.getKeyChar());
            
            int iKeyCode = evt.getKeyCode();
            if (PopupUtils.isCompleteWord(evt)) {
                // this is a hack to execute complete word
                iKeyCode = KeyEvent.VK_A;
            }
            switch (iKeyCode) {
                case KeyEvent.VK_TAB:
                case KeyEvent.VK_ESCAPE:
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_BACK_SPACE:
                    dispose();
                    evt.consume();
                    break;
                default:
                    //logger.msg("default");
                    PopupUtils.processJEditCommand(
                        _view, PointPopup.this, evt);
                    break;
            }
        }

        /**
         * Description of the Method
         *
         * @param evt  Description of the Parameter
         */
        public void keyTyped(KeyEvent evt) {
            logger.msg("typed");
            evt = KeyEventWorkaround.processKeyEvent(evt);
            if (evt == null) {
                return;
            } else if (evt.getKeyChar() != '\b') {
                // if char is not control character
                //_textArea.userInput(ch);
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

    /**
     * The main program for the PointPopup class
     *
     * @param args  The command line arguments
     */
    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(200, 200);
            frame.setLocation(200, 200);
            //frame.getContentPane().addMouseListener(new PopupListener());
            frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
            frame.getContentPane().add(new JButton("test1"));
            frame.getContentPane().add(new JButton("test2"));
            frame.getContentPane().add(new JButton("test3"));
            frame.show();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

