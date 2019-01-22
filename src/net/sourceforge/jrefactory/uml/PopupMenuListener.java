/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2003 JRefactory.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "JRefactory" must not be used to endorse or promote
 *    products derived from this software without prior written
 *    permission. For written permission, please contact seguin@acm.org.
 *
 * 5. Products derived from this software may not be called "JRefactory",
 *    nor may "JRefactory" appear in their name, without prior written
 *    permission of Chris Seguin.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of JRefactory.  For more information on
 * JRefactory, please see
 * <http://www.sourceforge.org/projects/jrefactory>.
 */
package net.sourceforge.jrefactory.uml;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import org.acm.seguin.summary.TypeSummary;



/**
 *  Create a mouse listener for a method or a field or a title
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: PopupMenuListener.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class PopupMenuListener extends MouseAdapter implements ActionListener {
   private JPopupMenu menu;
   private JMenuItem menuItem;


   /**
    *  Constructor for the PopupMenuListener object
    *
    * @param  initMenu  Description of Parameter
    * @param  initItem  Description of Parameter
    * @since            2.9.12
    */
   public PopupMenuListener(JPopupMenu initMenu, JMenuItem initItem) {
      menu = initMenu;
      menuItem = initItem;
   }


   /**
    *  Gets the menuItem attribute of the PopupMenuListener object
    *
    * @return    The menuItem value
    * @since     2.9.12
    */
   public JMenuItem getMenuItem() {
      return menuItem;
   }


   /**
    *  Gets the typeSummaries attribute of the PopupMenuListener object
    *
    * @return    The typeSummaries value
    * @since     2.9.12
    */
   public TypeSummary[] getTypeSummaries() {
      return new TypeSummary[0];
   }


   /**
    *  A menu item has been selected
    *
    * @param  evt  Description of Parameter
    * @since       2.9.12
    */
   public void actionPerformed(ActionEvent evt) {
      if (menuItem == null) {
         return;
      }

      if (menuItem instanceof JMenu) {
         //  Do nothing
      } else {
         menu.setVisible(false);
      }
   }


   /**
    *  A menu item has been selected
    *
    * @param  mevt  mouse event
    * @since        2.9.12
    */
   public void mouseEntered(MouseEvent mevt) {
      menuItem.setSelected(true);
   }


   /**
    *  A menu item has been selected
    *
    * @param  mevt  mouse event
    * @since        2.9.12
    */
   public void mouseExited(MouseEvent mevt) {
      menuItem.setSelected(false);
   }
}

