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

import java.awt.Container;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JLabel;



/**
 *  This adapter handles dragging panels around on another panel. This is used to relocate the class images on the class
 *  diagram.
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: DragPanelAdapter.java,v 1.2 2004/05/04 15:41:36 mikeatkinson Exp $
 */
public class DragPanelAdapter implements MouseListener, MouseMotionListener {
   private EndPointPanel panel;
   private LinedPanel parent;
   private Point mouseStart;


   /**
    *  Constructor for the DragPanelAdapter object
    *
    * @param  initParent  Description of Parameter
    * @since              2.9.12
    */
   public DragPanelAdapter(LinedPanel initParent) {
      parent = initParent;
   }


   /**
    *  Process a mouse click action
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseClicked(MouseEvent mevt) {
      //System.out.println("mouseClicked("+mevt+")");
      Point scaledPoint = parent.getScaledPoint(mevt);
      Container component = (Container)parent.getComponentAt(scaledPoint.x, scaledPoint.y);
      if (component instanceof EndPointPanel) {
         panel = (EndPointPanel)component;
         boolean newState = !panel.isSelected();
         if ((mevt.getModifiers() & MouseEvent.BUTTON1_MASK) == 0) {
            return;
         } else if (!mevt.isControlDown()) {
            parent.deselectAll();
         }

         panel.setSelected(newState);
      }
   }


   /**
    *  Process the mouse entering the component
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseEntered(MouseEvent mevt) { }


   /**
    *  Process the mouse leaving the component
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseExited(MouseEvent mevt) { }


   /**
    *  Process a mouse button press
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mousePressed(MouseEvent mevt) {
      //System.out.println("mousePressed("+mevt+")");
      Point pt = parent.getScaledPoint(mevt);
      Container component = (Container)parent.getComponentAt(pt.x, pt.y);
      component = getTypeOrPackage(mevt, component);
      if (component instanceof EndPointPanel) {
         //System.out.println("  component is EndPointPanel - setting panel");
         panel = (EndPointPanel)component;
         mouseStart = parent.getPoint(mevt);
         if (parent instanceof UMLPackage) {
            javax.swing.JScrollPane scrollPane = ((UMLPackage)parent).getScrollPane();
            if (scrollPane != null) {
               javax.swing.JScrollBar hsb = scrollPane.getHorizontalScrollBar();
               javax.swing.JScrollBar vsb = scrollPane.getVerticalScrollBar();;
            }
         }

      }
   }


   /**
    *  User released the mouse button
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseReleased(MouseEvent mevt) {
      //System.out.println("mouseReleased("+mevt+")");
      Point scaledPoint = parent.getScaledPoint(mevt);
      Container component = (Container)parent.getComponentAt(scaledPoint.x, scaledPoint.y);
      component = getTypeOrPackage(mevt, component);
      if (component == panel) {
         if ((mevt.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
            if (parent instanceof LinedPanel) {
               ((LinedPanel)parent).drop();
            }
            if (parent instanceof UMLPackage) {
               ((UMLPackage)parent).setDirty();
            }
         }
      }
      panel = null;
   }


   /**
    *  Notifies this object that it is being dragged
    *
    * @param  mevt  The mouse object
    * @since        2.9.12
    */
   public void mouseDragged(MouseEvent mevt) {
      //System.out.println("mouseDragged("+mevt+") panel="+panel);
      if (panel != null && (mevt.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) {
         Point currentMouse = parent.getPoint(mevt);

         int deltaX = (int)((currentMouse.x - mouseStart.x) / parent.getScale());
         int deltaY = (int)((currentMouse.y - mouseStart.y) / parent.getScale());

         if (!panel.isSelected()) {
            panel.shift(deltaX, deltaY);
         } else {
            parent.shift(deltaX, deltaY);
         }
         if (parent instanceof UMLPackage && panel instanceof UMLType) {
            ((UMLPackage)parent).reset();
            ((UMLPackage)parent).jumpTo((UMLType)panel);
         }

         mouseStart = currentMouse;
         parent.repaint();
      }
   }


   /**
    *  What to do when the mouse moves. Nothing.
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseMoved(MouseEvent mevt) { }


   /**
    *  Gets the typeOrPackage attribute of the DragPanelAdapter object
    *
    * @param  mevt       Description of Parameter
    * @param  component  Description of Parameter
    * @return            The typeOrPackage value
    * @since             2.9.12
    */
   private Container getTypeOrPackage(MouseEvent mevt, Container component) {
      if (component instanceof UMLLine || component instanceof JLabel) {
         Point loc = component.getLocation();
         mevt.translatePoint(loc.x, loc.y);
         return (Container)component.getParent();
      }
      return component;
   }
}

