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

import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import net.sourceforge.jrefactory.uml.line.*;



/**
 *  A panel that contains segmented lineList
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: LinedPanel.java,v 1.2 2003/12/04 13:39:55 mikeatkinson Exp $
 */
public abstract class LinedPanel extends JPanel {
   /**
    *  Description of the Field
    *
    * @since    2.9.12
    */
   protected double scale = 1.0;
   private LinkedList endPoints;
   private LinkedList lineList;


   /**
    *  Constructor for the LinedPanel object
    *
    * @since    2.9.12
    */
   public LinedPanel() {
      endPoints = new LinkedList();
      lineList = new LinkedList();
   }


   /**
    *  Constructor for the LinedPanel object
    *
    * @param  doubleBuffered  Description of Parameter
    * @since                  2.9.12
    */
   public LinedPanel(boolean doubleBuffered) {
      super(doubleBuffered);
      endPoints = new LinkedList();
      lineList = new LinkedList();
   }


   /**
    *  Constructor for the LinedPanel object
    *
    * @param  layout  Description of Parameter
    * @since          2.9.12
    */
   public LinedPanel(LayoutManager layout) {
      super(layout);
      endPoints = new LinkedList();
      lineList = new LinkedList();
   }


   /**
    *  Constructor for the LinedPanel object
    *
    * @param  layout          Description of Parameter
    * @param  doubleBuffered  Description of Parameter
    * @since                  2.9.12
    */
   public LinedPanel(LayoutManager layout, boolean doubleBuffered) {
      super(layout, doubleBuffered);
      endPoints = new LinkedList();
      lineList = new LinkedList();
   }


   /**
    *  Gets the scale attribute of the LinedPanel object
    *
    * @return    The scale value
    * @since     2.9.12
    */
   public double getScale() {
      return scale;
   }


   /**
    *  Gets the scaledPoint attribute of the LinedPanel object
    *
    * @param  mevt  Description of Parameter
    * @return       The scaledPoint value
    * @since        2.9.12
    */
   public Point getScaledPoint(MouseEvent mevt) {
      java.awt.Component component = mevt.getComponent();
      Point pt = mevt.getPoint();
      int x = pt.x;
      int y = pt.y;
      while (!(component instanceof UMLPackage)) {
         pt = component.getLocation();
         x += pt.x;
         y += pt.y;
         component = ((java.awt.Container)component).getParent();
      }
      return new Point((int)(x / scale), (int)(y / scale));
   }


   /**
    *  Gets the point attribute of the LinedPanel object
    *
    * @param  mevt  Description of Parameter
    * @return       The point value
    * @since        2.9.12
    */
   public Point getPoint(MouseEvent mevt) {
      java.awt.Component component = mevt.getComponent();
      Point pt = mevt.getPoint();
      int x = pt.x;
      int y = pt.y;
      while (!(component instanceof UMLPackage)) {
         pt = component.getLocation();
         x += pt.x;
         y += pt.y;
         component = ((java.awt.Container)component).getParent();
      }
      return new Point(x, y);
   }


   /**
    *  Returns a list of lines
    *
    * @return    the lines
    * @since     2.9.12
    */
   public List getLines() {
      return lineList;
   }


   /**
    *  Returns an iterator into the list of lines
    *
    * @return    the lines
    * @since     2.9.12
    */
   public Iterator getLineIterator() {
      return lineList.iterator();
   }


   /**
    *  Sends a message to determine if any line has been hit
    *
    * @param  point  The point that the mouse is at
    * @since         2.9.12
    */
   public abstract void hit(Point point);


   /**
    *  Dragging action
    *
    * @param  point  The mouse's current position
    * @since         2.9.12
    */
   public abstract void drag(Point point);


   /**
    *  User dropped an item
    *
    * @since    2.9.12
    */
   public abstract void drop();


   /**
    *  Description of the Method
    *
    * @param  panel        Description of Parameter
    * @param  constraints  Description of Parameter
    * @since               2.9.12
    */
   public void add(EndPointPanel panel, Object constraints) {
      endPoints.add(panel);
      super.add((JComponent)panel, constraints);
   }


   /**
    *  Description of the Method
    *
    * @param  panel  Description of Parameter
    * @since         2.9.12
    */
   public void remove(EndPointPanel panel) {
      endPoints.remove(panel);
      super.remove((JComponent)panel);
   }


   /**
    *  Description of the Method
    *
    * @param  value  Description of Parameter
    * @since         2.9.12
    */
   public void add(SegmentedLine value) {
      lineList.add(value);
   }


   /**
    *  Description of the Method
    *
    * @param  value  Description of Parameter
    * @since         2.9.12
    */
   public void remove(SegmentedLine value) {
      lineList.remove(value);
   }


   /**
    *  Description of the Method
    *
    * @param  value  Description of Parameter
    * @since         2.9.12
    */
   public void scale(double value) {
      scale = value;
   }


   /**
    *  Shifts the components of this lined panel
    *
    * @param  x  the amount to shift in the X axis
    * @param  y  the amount to shift in the Y axis
    * @since     2.9.12
    */
   public void shift(int x, int y) {
      java.awt.Component[] components = getComponents();
      for (int i = 0; i < components.length; i++) {
         if (components[i] instanceof EndPointPanel) {
            EndPointPanel next = (EndPointPanel)components[i];
            if (next.isSelected()) {
               next.shift(x, y);
            }
         }
      }

      Iterator iter = lineList.iterator();
      while (iter.hasNext()) {
         SegmentedLine next = (SegmentedLine)iter.next();
         if (next.isBothEndsSelected()) {
            next.shift(x, y);
         }
      }

      repaint();
   }


   /**
    *  Removes all the lihnes and classes from this diagram
    *
    * @since    2.9.12
    */
   public void clear() {
      endPoints.clear();
      lineList.clear();
   }


   /**
    *  Deselects all the end points
    *
    * @since    2.9.12
    */
   public void deselectAll() {
      Iterator iter = getEndPointIterator();
      while (iter.hasNext()) {
         ((EndPointPanel)iter.next()).setSelected(false);
      }
   }


   /**
    *  Return the list of panels
    *
    * @return    the panels
    * @since     2.9.12
    */
   protected List getEndPoints() {
      return endPoints;
   }


   /**
    *  Return an iterator into the list of panels
    *
    * @return    the panels
    * @since     2.9.12
    */
   protected Iterator getEndPointIterator() {
      return endPoints.iterator();
   }
}

