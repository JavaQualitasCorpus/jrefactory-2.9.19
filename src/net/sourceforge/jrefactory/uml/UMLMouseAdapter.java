/*
 *  ====================================================================
 *  The JRefactory License, Version 1.0
 *
 *  Copyright (c) 2003 JRefactory.  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the
 *  distribution.
 *
 *  3. The end-user documentation included with the redistribution,
 *  if any, must include the following acknowledgment:
 *  "This product includes software developed by the
 *  JRefactory (http://www.sourceforge.org/projects/jrefactory)."
 *  Alternately, this acknowledgment may appear in the software itself,
 *  if and wherever such third-party acknowledgments normally appear.
 *
 *  4. The names "JRefactory" must not be used to endorse or promote
 *  products derived from this software without prior written
 *  permission. For written permission, please contact seguin@acm.org.
 *
 *  5. Products derived from this software may not be called "JRefactory",
 *  nor may "JRefactory" appear in their name, without prior written
 *  permission of Chris Seguin.
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED.  IN NO EVENT SHALL THE CHRIS SEGUIN OR
 *  ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  SUCH DAMAGE.
 *  ====================================================================
 *
 *  This software consists of voluntary contributions made by many
 *  individuals on behalf of JRefactory.  For more information on
 *  JRefactory, please see
 *  <http://www.sourceforge.org/projects/jrefactory>.
 */
package net.sourceforge.jrefactory.uml;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;



/**
 *  Create a mouse listener for a method or a field or a title
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLMouseAdapter.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLMouseAdapter extends MouseAdapter {
   private JPanel child;
   private UMLPackage current;


   /**
    *  Constructor for the UMLMouseAdapter object
    *
    * @param  currentPackage  Description of Parameter
    * @param  initChild       Single line
    * @since                  2.9.12
    */
   public UMLMouseAdapter(UMLPackage currentPackage, JPanel initChild) {
      current = currentPackage;
      child = initChild;
   }


   /**
    *  User has pressed a mouse button
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mousePressed(MouseEvent mevt) {
      if (mevt.isPopupTrigger()) {
         showMenu(mevt);
      }
   }


   /**
    *  User has released a mouse button
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseReleased(MouseEvent mevt) {
      if (mevt.isPopupTrigger()) {
         showMenu(mevt);
      }
   }


   /**
    *  Shows the popup menu
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   private void showMenu(MouseEvent mevt) {
      //System.out.println("showMenu("+mevt+")");
      Point pt = current.getScaledPoint(mevt);
      //System.out.println("pt="+pt);
      Component c2 = current.findComponentAt(pt.x, pt.y);
      System.out.println(c2);

      if (c2 != null) {
         if (!(c2 instanceof UMLLine || c2 instanceof UMLType)) {
            while (c2 != null && !(c2 instanceof UMLLine || c2 instanceof UMLType)) {
               c2 = c2.getParent();
            }
         }
         if (c2 instanceof UMLLine || c2 instanceof UMLType) {
            UMLPopupMenu upm = new UMLPopupMenu(current, (JComponent)c2);
            JPopupMenu menu = upm.getMenu();
            pt = current.getPoint(mevt);
            Point pt2 = current.getLocationOnScreen();
            menu.setLocation(pt.x + pt2.x, pt.y + pt2.y + 20);
            menu.setVisible(true);
         }
      }
   }
}

