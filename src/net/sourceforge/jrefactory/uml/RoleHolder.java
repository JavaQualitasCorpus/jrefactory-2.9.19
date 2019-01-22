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

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 *  Holds the roles associated with a type
 *
 * @author     Chris Seguin
 * @author     Mike Atkinson
 * @since      2.9.12
 * @version    $Id: RoleHolder.java,v 1.1 2003/12/02 23:35:14 mikeatkinson Exp $
 */
public class RoleHolder extends JPanel {
   private LinkedList labels;
   private Dimension preferredSize;



   /**
    *  Constructor for the RoleHolder object
    *
    * @since    2.9.12
    */
   public RoleHolder() {
      setLayout(null);
      labels = new LinkedList();
      preferredSize = new Dimension(0, 0);
   }


   /**
    *  Gets the preferred size
    *
    * @return    the preferred size for this object
    * @since     2.9.12
    */
   public Dimension getPreferredSize() {
      return preferredSize;
   }


   /**
    *  Gets the minimum size
    *
    * @return    The minimum size for this object
    * @since     2.9.12
    */
   public Dimension getMinimumSize() {
      return preferredSize;
   }


   /**
    *  Adds a role
    *
    * @param  msg  the role name
    * @since       2.9.12
    */
   public void add(String msg) {
      JLabel roleLabel = new JLabel(msg);
      roleLabel.setFont(UMLSettings.defaultFont);
      roleLabel.setHorizontalAlignment(JLabel.CENTER);
      roleLabel.setLocation(0, preferredSize.height);
      add(roleLabel);

      Dimension dim = roleLabel.getPreferredSize();
      roleLabel.setSize(dim);
      preferredSize.width = Math.max(preferredSize.width, dim.width);
      preferredSize.height = preferredSize.height + dim.height;

      labels.add(roleLabel);
   }


   /**
    *  Description of the Method
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public int maxRoleWidth() {
      int maxLen = 1;
      for (Iterator i = labels.iterator(); i.hasNext(); ) {
         JLabel label = (JLabel)i.next();
         int len = label.getText().length();
         if (len > maxLen) {
            maxLen = len;
         }
      }
      return maxLen;
   }


   /**
    *  Determines if there are any roles
    *
    * @return    Description of the Returned Value
    * @since     2.9.12
    */
   public boolean hasAny() {
      return (labels.size() > 0);
   }


   /**
    *  Reset width
    *
    * @param  newWidth  the new width
    * @since            2.9.12
    */
   public void resetWidth(int newWidth) {
      java.awt.Insets insets = getInsets();
      preferredSize.width = newWidth-insets.left-insets.right;
      //preferredSize.width = newWidth + insets.left + insets.right;

      Iterator iter = labels.iterator();
      while (iter.hasNext()) {
         JLabel next = (JLabel)iter.next();
         insets = next.getInsets();
         Dimension temp = next.getPreferredSize();
         temp.width = preferredSize.width - insets.left - insets.right;
         next.setSize(temp);
      }
   }


   /**
    *  Description of the Method
    *
    * @param  g  Description of Parameter
    * @since     2.9.12
    */
   public void paint(Graphics g) {
      setBackground(getParent().getBackground());
      super.paint(g);
   }

}

