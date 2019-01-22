/*
 * JSGeneralOptionPane.java - JavaStyle general options panel
 * Copyright (C) 2000,2001 Dirk Moebius
 *
 * jEdit buffer options:
 * :tabSize=4:indentSize=4:noTabs=false:maxLineLen=0:
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.acm.seguin.ide.common.options;


import javax.swing.JCheckBox;
import javax.swing.JTextField;


/**
 * @author    Mike Atkinson (<a href="mailto:javastyle@ladyshot.demon.co.uk">
 *      Mike@ladyshot.demon.co.uk</a> )
 * @version   $Version: $
 * @since     1.0, created   06 October 2003
 */
public class NavigatorOptionPane extends JSHelpOptionPane {

   private SelectedPanel navigatorEnable_sp;


   /**
    * Constructor for the JSGeneralOptionPane object
    *
    * @param project  Description of the Parameter
    */
   public NavigatorOptionPane(String project) {
      super("javastyle.navigator", "pretty", project);
   }


   /** Description of the Method  */
   public void _init() {
      // "Enable the Navigator "
      navigatorEnable_sp = addComponent("navigator.enable", "navigator.enable", new JCheckBox());
      //-- general options

      addHelpArea();
   }


   /**
    * Called when the options dialog's `OK' button is pressed. This should
    * save any properties saved in this option pane.
    */
   public void _save() {
      navigatorEnable_sp.save();
   }

}

