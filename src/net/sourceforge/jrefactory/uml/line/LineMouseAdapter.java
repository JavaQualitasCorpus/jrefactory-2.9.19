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
package net.sourceforge.jrefactory.uml.line;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import net.sourceforge.jrefactory.uml.LinedPanel;



/**
 *  MouseAdapter
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: LineMouseAdapter.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class LineMouseAdapter implements MouseListener, MouseMotionListener {
   private LinedPanel panel;


   /**
    *  Constructor for the LineMouseAdapter object
    *
    * @param  init  the panel that contains segmented lines
    * @since        2.9.12
    */
   public LineMouseAdapter(LinedPanel init) {
      panel = init;
   }


   /**
    *  Description of the Method
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseClicked(MouseEvent mevt) { }


   /**
    *  Description of the Method
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseEntered(MouseEvent mevt) { }


   /**
    *  Description of the Method
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseExited(MouseEvent mevt) { }


   /**
    *  Description of the Method
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mousePressed(MouseEvent mevt) {
      Point result = mevt.getPoint();
      panel.hit(result);
   }


   /**
    *  Description of the Method
    *
    * @param  mevt  the mouse event
    * @since        2.9.12
    */
   public void mouseReleased(MouseEvent mevt) {
      panel.drop();
   }


   /**
    *  Description of the Method
    *
    * @param  mevt  Description of Parameter
    * @since        2.9.12
    */
   public void mouseDragged(MouseEvent mevt) {
      panel.drag(mevt.getPoint());
   }


   /**
    *  Description of the Method
    *
    * @param  mevt  Description of Parameter
    * @since        2.9.12
    */
   public void mouseMoved(MouseEvent mevt) { }
}

