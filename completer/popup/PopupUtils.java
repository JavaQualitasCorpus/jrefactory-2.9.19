package org.acm.seguin.completer.popup;



import org.acm.seguin.ide.jedit.Navigator;
import org.acm.seguin.completer.Completer;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.gui.DefaultInputHandler;
import org.gjt.sp.jedit.gui.InputHandler;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.textarea.JEditTextArea;



/**

 * Utility functions for popups

 *

 * @author    btateyama@yahoo.com

 * @created   December 12, 2002

 */

public class PopupUtils {
    static final Navigator.NavigatorLogger logger = Completer.getLogger(PopupUtils.class);
    static final ImageIcon ICON_FIELD = 
        new ImageIcon(Navigator.class.getResource("icons/field.gif"));
    static final ImageIcon ICON_METHOD = 
        new ImageIcon(Navigator.class.getResource("icons/method.gif"));
    static final ImageIcon ICON_INNER = 
        new ImageIcon(Navigator.class.getResource("icons/inner-class.gif"));
    
    static final int ICON_WIDTH = 13;
    
    
    /**
     * Translate a text position to point where popup should appear.
     *
     * @param argView    Description of the Parameter
     * @param argPrefix  Description of the Parameter
     * @return           The pointOnTextArea value
     */
    public static Point getPointOnTextArea(View argView, String argPrefix) {
        
        JEditTextArea textArea = argView.getTextArea();
        int caretLine = textArea.getCaretLine();
        int caret = textArea.getCaretPosition();
        textArea.scrollToCaret(false);
        Point location = textArea.offsetToXY(caret - argPrefix.length());
        location.y += textArea.getPainter().getFontMetrics().getHeight();
        SwingUtilities.convertPointToScreen(location, textArea.getPainter());
        return location;
    }
    
    public static void requestFocusOnTextArea(JEditTextArea argTextArea){
        SwingUtilities.invokeLater(new TextAreaFocusHelper(argTextArea));
    }
    
    static class TextAreaFocusHelper implements Runnable{
        JEditTextArea _textArea = null;
        TextAreaFocusHelper(JEditTextArea argTextArea){
            _textArea = argTextArea;
        }
        public void run(){
            _textArea.requestFocus();
        }
    }
    
    public static void processJEditCommand(View argView,
                                    BasePopup argPopup,
                                    KeyEvent argEvent){
        if (hasBinding(argView,argEvent)){
            argPopup.dispose();
            //FIXME: argView.getInputHandler().keyPressed(argEvent);
        }
        
        if (!argEvent.isConsumed() && argEvent.isActionKey()){
            argPopup.dispose();
            argView.processKeyEvent(argEvent);
        }
    }
    
    private static boolean hasBinding(View argView, KeyEvent argEvent){
        boolean bHasBinding = false;
        StringBuffer sbBinding = new StringBuffer();
        sbBinding.append(DefaultInputHandler.getModifierString(argEvent));
        sbBinding.append("+");
        String strSym = getSymbolicName(argEvent.getKeyCode());
        if (strSym != null){
            sbBinding.append(strSym);
            DefaultInputHandler inputHandler = 
                (DefaultInputHandler) argView.getInputHandler();
            Object obj = inputHandler.getKeyBinding(sbBinding.toString());
            bHasBinding = obj!=null;
        }
        return bHasBinding;
    }
    
    
    private static String getSymbolicName(int keyCode)
	{
        // Copied from org.gjt.sp.jedit.gui.GrabKeyDialog
		if(keyCode == KeyEvent.VK_UNDEFINED)
			return null;
		/* else if(keyCode == KeyEvent.VK_OPEN_BRACKET)
			return "[";
		else if(keyCode == KeyEvent.VK_CLOSE_BRACKET)
			return "]"; */

		if(keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z)
			return String.valueOf(Character.toLowerCase((char)keyCode));

		try
		{
			Field[] fields = KeyEvent.class.getFields();
			for(int i = 0; i < fields.length; i++)
			{
				Field field = fields[i];
				String name = field.getName();
				if(name.startsWith("VK_")
					&& field.getInt(null) == keyCode)
				{
					return name.substring(3);
				}
			}
		}
		catch(Exception e)
		{
			logger.error("Error enum fields", e);
		}

		return null;
	} //}}}
    
    public static boolean isCompleteWord(KeyEvent argEvent){
        boolean bIsCompleteWord = false;
        StringBuffer sbBinding = new StringBuffer();
        sbBinding.append(DefaultInputHandler.getModifierString(argEvent));
        sbBinding.append("+");
        String strSym = getSymbolicName(argEvent.getKeyCode());
        if (strSym != null){
            sbBinding.append(strSym);
            String strB = sbBinding.toString();
            bIsCompleteWord = (strB.equals(jEdit.getProperty("complete-word.shortcut")) ||
                               strB.equals(jEdit.getProperty("complete-word.shortcut2")));
            // hmm..we could also do a cross ref of actions
        }
        return bIsCompleteWord;
    }

}

