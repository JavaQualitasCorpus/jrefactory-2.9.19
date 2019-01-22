package org.acm.seguin.completer.popup;


import java.io.FileNotFoundException;
import java.io.IOException;
import org.acm.seguin.ide.jedit.Navigator;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.completer.info.ClassInfo;
import org.acm.seguin.completer.info.FieldInfo;
import org.acm.seguin.completer.info.MemberInfo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.gui.DefaultInputHandler;
import org.gjt.sp.jedit.gui.InputHandler;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.textarea.JEditTextArea;

public class KeyStrokeTest {
    static final Navigator.NavigatorLogger logger =   Navigator.getLogger(KeyStrokeTest.class);

    class TestKeyListener extends KeyAdapter {

        public TestKeyListener(){
        }
        public void keyPressed2(KeyEvent argEvent){
            logger.msg("-pressed", argEvent.hashCode());
            int iKeyCode = argEvent.getKeyCode();
            if (iKeyCode == KeyEvent.VK_C && argEvent.isControlDown()){
                logger.msg("Self destructing...");
                jEdit.getActiveView().setKeyEventInterceptor(null);
            }else if (argEvent.isAltDown()){
                logger.msg("yum");
                argEvent.consume();
            }
        }
        public void keyTyped(KeyEvent argEvent){
            logger.msg("typed", argEvent.hashCode());
            
        }
        public void keyReleased(KeyEvent argEvent){
            logger.msg("released", argEvent.hashCode());
        }
        public void keyPressed(KeyEvent argEvent){
            int iKeyCode = argEvent.getKeyCode();
            if (iKeyCode == KeyEvent.VK_C && argEvent.isControlDown()){
                logger.msg("Self destructing...");
                jEdit.getActiveView().setKeyEventInterceptor(null);
            }else{
                logger.msg("isCompleteWord", "" + isCompleteWord(argEvent));
                /*

                KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent(argEvent);

                DefaultInputHandler inputHandler = (DefaultInputHandler) jEdit.getActiveView().getInputHandler();

                inputHandler.keyPressed(argEvent);

                if (argEvent.isConsumed()){

                    logger.msg("it ate it");

                }else{

                    logger.msg("still ok");

                }

                StringBuffer sbBinding = new StringBuffer();

                sbBinding.append(DefaultInputHandler.getModifierString(argEvent));

                sbBinding.append("+");

                

                String strSym = getSymbolicName(argEvent.getKeyCode());

                if (strSym != null){

                    sbBinding.append(strSym);

                    Object obj = inputHandler.getKeyBinding(sbBinding.toString());

                    if (obj != null){

                        logger.msg("obj", obj.toString());

                        logger.msg("obj", obj.getClass().getName());

                    }else{

                        logger.msg("obj is null");

                    }

                }

                logger.msg("binding", sbBinding.toString());

                */

                

            }

        }

        

    }

    

    static boolean isCompleteWord(KeyEvent argEvent){

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

    private static String getSymbolicName(int keyCode)

	{

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

    void testKeyStroke(){

        View view = jEdit.getActiveView();

        JEditTextArea textArea = view.getTextArea();

        Buffer buffer = view.getBuffer();

        view.setKeyEventInterceptor(new TestKeyListener());

        

        

    }

    

    void testFrame(){

        JFrame frame = new JFrame(){

            public boolean isDoubleBuffered(){return false;}

        };

        JPanel panel = new JPanel(){

            public boolean isDoubleBuffered(){return false;}

        };

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setLocation(200,200);

        frame.setSize(100, 100);

        //frame.pack();

        Color colorTest = new Color(255, 0, 0, 100);

        frame.setBackground(colorTest);

        frame.setContentPane(panel);

        frame.getContentPane().setBackground(colorTest);

        //frame.

        frame.show();

    }

    

    void testFrame2(){

        logger.msg("test frame 2");

        View view = jEdit.getActiveView();

        JEditTextArea textArea = view.getTextArea();

        Point p = PopupUtils.getPointOnTextArea(view, "");

        MyGlassPane glassPane = new MyGlassPane(view);

        glassPane.setBackground(new Color(255, 0, 0, 100));

        glassPane.setPoint(p);

        view.setGlassPane(glassPane);

        view.getGlassPane().setVisible(true);

    }

    

    class MyGlassPane extends JComponent implements MouseListener {

        JFrame _frame = null;

        Point _point = null;

        MyGlassPane(JFrame argFrame){

            _frame = argFrame;

            addMouseListener(this);

        }

        public void paintComponent(Graphics g) {

            Color colorBG = new Color(0, 0, 255, 100);

            Color colorFG = new Color(255, 0, 255);

            g.setColor(colorBG);

            g.fillRect(_point.x,_point.y,100, 100);

            g.setColor(colorFG);

            g.drawString("test", _point.x + 10, _point.y +2*g.getFontMetrics().getHeight());

            //g2.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER, 0.55f));

            

            //g2.setColor (getBackground());

            //            g2.setColor (calcColor);

            //          g2.setColor(new Color(255, 0, 0, 100));

            //          g2.fill`Rect (0, 0, getWidth(), getLastComponentYBound());

            //          paintChildren(g2);

        }

        

        public void mousePressed(MouseEvent argEvent){

            setVisible(false);

            //_frame.setGlassPane(null);

            logger.msg("killed");

        }

        public void setPoint(Point arg){

            _point = arg;

        }

        public void mouseClicked(MouseEvent argEvent){}

        public void mouseReleased(MouseEvent argEvent){}

        public void mouseEntered(MouseEvent argEvent){}

        public void mouseExited(MouseEvent argEvent){}

    }

    /**

 * We have to provide our own glass pane so that it can paint.

 

class MyGlassPane extends JComponent {

    Point point;



    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        //g2.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER, 0.55f));



        //g2.setColor (getBackground());

        //            g2.setColor (calcColor);

//          g2.setColor(new Color(255, 0, 0, 100));

//          g2.fillRect (0, 0, getWidth(), getLastComponentYBound());

//          paintChildren(g2);



         if (point != null) {

            g2.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC_OVER, 0.60f));

            g2.setColor (new java.awt.Color (25,15,2));

            g2.fillRect (0, 0, getWidth(), getHeight());

            g2.setColor (java.awt.Color.lightGray);

            g2.drawRect (2, 2, getWidth()-4, getHeight()-4);

            g2.drawRect (3, 3, getWidth()-6, getHeight() -6);

            g2.setColor (java.awt.Color.yellow);

            g2.setComposite (AlphaComposite.getInstance (AlphaComposite.SRC, 1.0f));

            FontMetrics fm = g.getFontMetrics();

            int sw = fm.stringWidth("foobar");

            int sh = fm.getHeight();

            int ascent = fm.getAscent();

            g.drawRect(4, 4, sw + 10, sh + 10);

            g.drawString("foobar", 4, 4 + ascent + 5);

            //super.paint(g);

        

//                g2.setColor(new Color(255, 0, 0, 100));

//                System.out.println(getWidth());

//                System.out.println(getLastComponentYBound());

//                g2.fillRect (point.x, point.y, getWidth(), 10);

//                //paintChildren(g2);

//                //g2.fillOval(point.x - 10, point.y - 10, 30, 30);

         }

    }

    */

    public KeyStrokeTest(){
        try{
            //testFrame();
            testFrame2();
            //testKeyStroke();
        }catch(Exception e){
            logger.error("Error testing JavaParser", e);
        }
    }

    public static void main(String[] args){
        try{            
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

}

