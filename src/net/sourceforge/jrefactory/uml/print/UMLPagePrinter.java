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
package net.sourceforge.jrefactory.uml.print;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import net.sourceforge.jrefactory.uml.UMLPackage;
import org.acm.seguin.print.PagePrinter;



/**
 *  Handles printing the page
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: UMLPagePrinter.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class UMLPagePrinter extends PagePrinter {
   private UMLPackage currentPackage;


   /**
    *  Constructor for the UMLPagePrinter object
    *
    * @param  panel  the current package
    * @since         2.9.12
    */
   public UMLPagePrinter(UMLPackage panel) {
      currentPackage = panel;
   }


   /**
    *  Guess the number of pages
    *
    * @param  pf  Description of Parameter
    * @return     Description of the Returned Value
    * @since      2.9.12
    */
   public int calculatePageCount(PageFormat pf) {
      Dimension size = currentPackage.getPreferredSize();
      int pageHeight = (int)pf.getImageableHeight() - headerHeight;
      int pageWidth = (int)pf.getImageableWidth();

      int pagesWide = (int)(1 + getScale() * currentPackage.getScale() * size.width / pageWidth);
      int pagesHigh = (int)(1 + getScale() * currentPackage.getScale() * size.height / pageHeight);

      return pagesWide * pagesHigh;
   }


   /**
    *  Print the page
    *
    * @param  g           the graphics object
    * @param  pf          the page format
    * @param  pageNumber  the page number
    * @return             Description of the Returned Value
    * @since              2.9.12
    */
   public int print(Graphics g, PageFormat pf, int pageNumber) {
      Dimension size = currentPackage.getPreferredSize();
      int pageHeight = (int)pf.getImageableHeight() - headerHeight;
      int pageWidth = (int)pf.getImageableWidth();

      int pagesWide = (int)(1 + getScale() * currentPackage.getScale() * size.width / pageWidth);
      int pagesHigh = (int)(1 + getScale() * currentPackage.getScale() * size.height / pageHeight);
      if (pageNumber > pagesWide * pagesHigh) {
         return Printable.NO_SUCH_PAGE;
      }

      int row = pageNumber / pagesWide;
      int col = pageNumber % pagesWide;

      ((Graphics2D)g).setRenderingHints(UMLPackage.qualityHints);

      double scale = currentPackage.getScale();
      currentPackage.scale(scale * getScale());
      currentPackage.setPrinting(true);
      /*
         *  if (panelBuffer == null) {
         *  panelBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
         *  Graphics tempGraphics = panelBuffer.getGraphics();
         *  tempGraphics.setColor(Color.white);
         *  tempGraphics.fillRect(0, 0, size.width, size.height);
         *  currentPackage.print(tempGraphics, 0, 0);
         *  }
         *  g.drawImage(panelBuffer,
         *  ((int) pf.getImageableX()) - col * pageWidth,
         *  ((int) pf.getImageableY())- row * pageHeight, null);
         */
      double translateX = pf.getImageableX() - col * pageWidth;
      double translateY = pf.getImageableY() - row * pageHeight + headerHeight;
      ((Graphics2D)g).translate(translateX, translateY);
      //((Graphics2D) g).scale(getScale(), getScale());

      currentPackage.print(g);

      //((Graphics2D) g).scale(1 / getScale(), 1 / getScale());
      ((Graphics2D)g).translate(-translateX, -translateY);

      currentPackage.scale(scale);
      currentPackage.setPrinting(false);
      String packageName = currentPackage.getSummary().getName();
      if ((packageName == null) || (packageName.length() == 0)) {
         packageName = "Top Level Package";
      }
      printHeader(g, packageName,
               "(" + (1 + col) + ", " + (1 + row) + ")",
               "(" + pagesWide + ", " + pagesHigh + ")");

      return Printable.PAGE_EXISTS;
   }


   /**
    *  Returns the page
    *
    * @param  dialog  present a dialog screen if none
    * @return         the current page format
    * @since          2.9.12
    */
   public static PageFormat getPageFormat(boolean dialog) {
      PageFormat pf = PagePrinter.getPageFormat(dialog);
      setScale(0.8);
      return pf;
   }


   /**
    *  Return the width of the page
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public static int getPageHeight() {
      int result = PagePrinter.getPageHeight();
      if (result == -1) {
         return -1;
      }

      return (result - headerHeight);
   }
}

