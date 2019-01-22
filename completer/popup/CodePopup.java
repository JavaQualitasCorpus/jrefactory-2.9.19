package org.acm.seguin.completer.popup;

//import anthelper.adapters.Plugin;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JPanel;
//import anthelper.adapters.JIndexUtils;
//import anthelper.JavaUtils;
//import anthelper.AntHelperPlugin;
import org.acm.seguin.completer.Config;
import org.acm.seguin.ide.jedit.Navigator;
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
 * The popup
 *
 * @author    btateyama@yahoo.com
 * @created   December 10, 2002
 */
public class CodePopup extends BasePopup {

    final static Navigator.NavigatorLogger logger = Navigator.getLogger(CodePopup.class);
    String _strPrefix = null;
    JList _listMembers = null;
    Vector _dataAll = null;
    ArrayList _listInsertListeners = new ArrayList();
    PopupItemCellRenderer _cellRenderer = null;

    // used for resizing the popup as data narrows
    int _iLastDataSize = 0;

    /**
     * Create the key handler used by the JList as well as
     * handling intercepted key events from the jEditTextArea
     *
     * Default will collect letters and narrow list, handle
     * list up/down motion as well as TAB/ENTER (insertSelected())
     *
     * @param argOwningPopup  Description of the Parameter
     * @param argView         Description of the Parameter
     * @return                Description of the Return Value
     */
    protected KeyListener createKeyHandler(CodePopup argOwningPopup,
                                           View argView) {
        return new DefaultKeyHandler(argOwningPopup, argView);
    }


    /**
     * Create the Mouse Listener for hanlding mouse click events
     * on the list.
     * Default handler will calls insertSelected();
     *
     * @param argOwningPopup  Description of the Parameter
     * @param argView         Description of the Parameter
     * @return                Description of the Return Value
     */
    protected MouseListener createMouseHandler(CodePopup argOwningPopup,
                                            View argView) {
        return new DefaultMouseHandler(argOwningPopup);
    }

    /**
     * Constructor for the CodePopup object
     *
     * @param argView        Description of the Parameter
     * @param argPrefix      Description of the Parameter
     * @param argPopupItems  Description of the Parameter
     * @param argTag         Description of the Parameter
     */
    public CodePopup(View argView,
                     String argPrefix,
                     Vector argPopupItems,
                     String argTag) {
        super(argView);
        
        _strPrefix = argPrefix;
        _dataAll = argPopupItems;
        Collections.sort(_dataAll);
        _iLastDataSize = _dataAll.size();
        _cellRenderer = new PopupItemCellRenderer(_dataAll);
        _listMembers = createList(_dataAll, _cellRenderer);

        JPanel panel = createMainPanel();
        panel.add(createScrollPane(_listMembers), BorderLayout.CENTER);
        if (argTag != null && argTag.trim().length() > 0) {
            panel.add(createTypeLabel(argTag), BorderLayout.SOUTH);
        }
        
        setContentPane(panel);
        pack();
        // calculate left offset and position popup
        Point point = PopupUtils.getPointOnTextArea(argView, argPrefix);
        point.x -= _cellRenderer.getLeftOffset(_listMembers.getFont());
        setLocation(point);
        filterData();
    }
    
    protected void postShow(){
        KeyListener keyHandler = createKeyHandler(this, _view);
        addKeyListener(keyHandler);
        _listMembers.addKeyListener(keyHandler);
        _view.setKeyEventInterceptor(keyHandler);
        GUIUtilities.requestFocus(this, _listMembers);
    }

    

    JList createList(Vector argData, ListCellRenderer argRenderer) {
        JList listMembers = new JList(argData);
        listMembers.setFont(UIManager.getFont("TextArea.font"));
        listMembers.setVisibleRowCount(Math.min(argData.size(), 8));
        listMembers.setSelectionBackground(Color.blue);
        listMembers.setSelectionForeground(Color.white);
        //listMembers.addMouseListener(new MouseHandler());
        listMembers.addMouseListener(createMouseHandler(this, _view));
        listMembers.setSelectedIndex(0);
        listMembers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMembers.setCellRenderer(argRenderer);
        return listMembers;
    }

    static JButton createSmallButton(String argText){
        JButton buttonTiny = new JButton(argText);
        //buttonTiny.setBorder(BorderFactory.createEtchedBorder());
        //buttonTiny.setBorder(BorderFactory.createLineBorder(Color.blue));
        buttonTiny.setMargin(new Insets(0,2,0,2));
        return buttonTiny;
    }
    Component createTypeLabel(final String argText) {
        JLabel label = new JLabel(argText);
        JButton buttonCB = createSmallButton("CB");
        buttonCB.setToolTipText("Open class in AH Class Browser");
        buttonCB.addActionListener(new AbstractAction(){
            public void actionPerformed(ActionEvent argEvent){
                dispose();
                // FIXME : JavaUtils.viewClassInfo(_view, argText);
            }
        });
        
        JButton buttonJD = createSmallButton("JD");
        buttonJD.setToolTipText("Open Javadoc with JIndex");
        buttonJD.addActionListener(new AbstractAction(){
            public void actionPerformed(ActionEvent argEvent){
                dispose();
                // FIXME: JIndexUtils.lookUp(_view, argText);
            }
        });
        buttonJD.setEnabled(false); //FIXME: Plugin.JINDEX.isInstalled());
        
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalStrut(Math.max(_cellRenderer.getIconWidth(),
                                                     PopupUtils.ICON_WIDTH )));
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        panel.add(buttonCB);
        panel.add(buttonJD);
       
        return panel;
    }
    
    /**
     * Adds a feature to the InsertListeners attribute of the CodePopup object
     *
     * @param argListener  The feature to be added to the InsertListeners
     *      attribute
     */
    public void addInsertListener(InsertListener argListener) {
        _listInsertListeners.add(argListener);
    }

    public JList getListMembers() {
        return _listMembers;
    }

    String getPrefix() {
        return _strPrefix;
    }

    void setPrefix(String argVal) {
        _strPrefix = argVal;
    }

    void insertSelected() {
        PopupItem popupItem = (PopupItem) _listMembers.getSelectedValue();

        String strText = popupItem.getBeforeCaretString();
        strText += popupItem.getAfterCaretString();

        // add text
        strText = strText.substring(_strPrefix.length());
        _textArea.setSelectedText(strText);
        //_textArea.setSelectedText(strText.substring(_strPrefix.length()));

        // back step cursor to before the "after" text
        int iPos = _textArea.getCaretPosition();
        iPos -= popupItem.getAfterCaretString().length();
        _textArea.setCaretPosition(iPos);
        // position
        dispose();

        // alert listener, delegates
        InsertListener listener;
        for (Iterator it = _listInsertListeners.iterator(); it.hasNext(); ) {
            listener = (InsertListener) it.next();
            listener.insertPerformed(popupItem, _strPrefix, strText, _view);
        }
    }


    void filterData() {
        Vector vNewData = new Vector();
        
        Object objDataItem;
        for (Iterator it = _dataAll.iterator(); it.hasNext(); ) {
            objDataItem = it.next();
            if (objDataItem.toString().startsWith(_strPrefix)) {
                vNewData.add(objDataItem);
            }
        }

        if (vNewData.size() == 0) {
            dispose();
        } else {
            _listMembers.setListData(vNewData);
            _listMembers.setSelectedIndex(0);
            
            if (Config.RESIZE_POPUP_AS_DATA_NARROWS.getAsBoolean() &&
                Math.abs(_iLastDataSize - vNewData.size()) > 10){
                _listMembers.setVisibleRowCount(Math.min(vNewData.size(), 8));
                pack();
            }
            _iLastDataSize = vNewData.size();
        }
    }

    class DefaultMouseHandler extends MouseAdapter {
        CodePopup _popup = null;

        DefaultMouseHandler(CodePopup argPopup) {
            _popup = argPopup;
        }

        /**
         * Description of the Method
         *
         * @param evt  Description of the Parameter
         */
        public void mouseClicked(MouseEvent evt) {
            _popup.insertSelected();
        }
    }
    
    public static void main(String[] args){
        try{
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(300, 300);
            frame.setSize(200, 200);
            JButton buttonTiny = new JButton("<html><font size=-2>small</font></html>");
            buttonTiny.setMargin(new Insets(0,10,0,10));
            //buttonTiny.setBorder(BorderFactory.createEtchedBorder());
            JButton buttonTiny2 = new JButton("<html><font size=-2>small</font></html>");
            buttonTiny2.setMargin(new Insets(0,0,0,0));

            JPanel panel = new JPanel();
            //panel.add(new JButton("small"));
            //panel.add(new JButton("<html><font size=-1>small</font></html>"));
            panel.add(createSmallButton("foobar"));
            panel.add(createSmallButton("foobar"));
            panel.add(buttonTiny2);
            frame.setContentPane(panel);
            frame.show();
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

}



