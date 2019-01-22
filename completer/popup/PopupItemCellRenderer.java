package org.acm.seguin.completer.popup;



import java.util.Iterator;

import java.util.Vector;

import java.awt.Component;

import java.awt.Graphics;

import java.awt.Color;

import java.awt.FontMetrics;

import java.awt.Font;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

import javax.swing.JList;

import javax.swing.DefaultListCellRenderer;





public class PopupItemCellRenderer extends DefaultListCellRenderer {

    

    PopupItem _popupItem = null;

    boolean _bSel = false;

    

    int _iLeftOffsetStringLen = 0;

    int _iIconWidth = 0;

    int _iLeftOffset = -1;

    public PopupItemCellRenderer(Vector argPopupItems){

        String str = null;

        PopupItem pi;

        for (Iterator it = argPopupItems.iterator(); it.hasNext();){

            pi = (PopupItem) it.next();

            _iLeftOffsetStringLen = Math.max(pi.getLeftText().length(), 

                                             _iLeftOffsetStringLen);

            _iIconWidth = Math.max(pi.getIconWidth(),

                                   _iIconWidth);

        }

        

        for (int i=0, l = _iLeftOffsetStringLen; i < l; i++){

        }

    }

    

    int getIconWidth(){ return _iIconWidth; }

    int getLeftOffset(Font argFont){

        if (_iLeftOffset == -1){

            int iLen = 0;

            StringBuffer sb = new StringBuffer();

            if (_iLeftOffsetStringLen != 0){

                for (int i=0, l = _iLeftOffsetStringLen; i < l; i++){

                    sb.append(" ");

                }

                FontMetrics fontMetrics = getFontMetrics(argFont);

                iLen = fontMetrics.stringWidth(sb.toString());

            }

            _iLeftOffset = iLen + _iIconWidth;

        }

        return _iLeftOffset;

    }

    

    /**

     * Gets the listCellRendererComponent attribute of the PopupCellRenderer

     * object

     *

     * @param argList          Description of the Parameter

     * @param argValue         Description of the Parameter

     * @param argIndex         Description of the Parameter

     * @param argIsSelected    Description of the Parameter

     * @param argCellHasFocus  Description of the Parameter

     * @return                 The listCellRendererComponent value

     */

    public Component getListCellRendererComponent(JList argList,

                                                  Object argValue,

                                                  int argIndex,

                                                  boolean argIsSelected,

                                                  boolean argCellHasFocus) {

        _popupItem = null;

        _bSel = argIsSelected;

        Component comp = null;

        

        if (argValue instanceof PopupItem) {

            _popupItem = (PopupItem) argValue;

            comp = super.getListCellRendererComponent(

                argList, 

                _popupItem.toString(_iLeftOffsetStringLen),

                argIndex, argIsSelected, argCellHasFocus);

            

            if (_popupItem.getIcon()!=null){

                setIcon(_popupItem.getIcon());

            }

        }else{

            comp = super.getListCellRendererComponent(

                argList, argValue,

                argIndex, argIsSelected, argCellHasFocus);

        }

        

        return comp;

    }

    

    

    

    /**

     * Description of the Method

     *

     * @param argGraphics  Description of the Parameter

     */

    public void paint(Graphics argGraphics) {

        super.paint(argGraphics);

        if (_popupItem != null){

            Rectangle r = getBounds();

            Font font = getFont();

            FontMetrics fontMetrics = getFontMetrics(font);

            int iLeftOffset = getLeftOffset(font);

            if (_popupItem.underLine()) {

                String strText = getText();

                int w = fontMetrics.stringWidth(strText);                    

                //argGraphics.setColor(MemberRenderer.this.getForeground());

                argGraphics.drawLine(iLeftOffset, r.height - 2, 

                                     _iIconWidth + w, r.height - 2);

            }

            if (_popupItem.leftHighlight() && !_bSel && _iLeftOffsetStringLen !=0 ){

                argGraphics.setColor(new Color(141,197,246, 90));

                argGraphics.fillRect(_iIconWidth, 0, 

                                     iLeftOffset - _iIconWidth, r.height);

            }

        }

    }

}

