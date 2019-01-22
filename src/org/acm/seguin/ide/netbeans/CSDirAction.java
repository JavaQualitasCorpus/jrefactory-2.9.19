/* ====================================================================
 * The JRefactory License, Version 1.0
 *
 * Copyright (c) 2001 JRefactory.  All rights reserved.
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
package org.acm.seguin.ide.netbeans;

import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.*;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.Presenter;
import org.openide.util.actions.SystemAction;
import org.openide.util.actions.CookieAction;
import org.openide.cookies.*;
import org.openide.src.*;
import org.openide.loaders.*;
import org.openide.nodes.*;
import org.netbeans.modules.java.JavaEditor;


/** Action which just holds a few other SystemAction's for grouping purposes.
 *
 * @author  Mike Atkinson
 * @version 1.0
 * @since 2.9.19
 * @created May 09, 2004
 */
public class CSDirAction extends CookieAction implements Presenter.Menu, Presenter.Toolbar {


    protected Class[] cookieClasses() {
        return new Class[] { JavaEditor.class }; //DataFolder.class, SourceCookie.class, ClassElement.class };
    }
    
    protected int mode() {
        return MODE_ALL;
    }
    
    protected void performAction(org.openide.nodes.Node[] node) {
        // do nothing; should not be called        
    }
    
    public String getName () {
        return NbBundle.getMessage (CSDirAction.class, "LBL_CSDirAction");
    }

    protected String iconResource () {
        return "CSCheckBufferActionIcon.gif";
    }


    public HelpCtx getHelpCtx () {
        return new HelpCtx(CSDirAction.class);
    }
    
    
    /** List of system actions to be displayed within this one's toolbar or submenu. */
    private static final SystemAction[] grouped = new SystemAction[] {
                SystemAction.get (CSCheckBufferAction.class),
                SystemAction.get (CSCheckAllBuffersAction.class),
                SystemAction.get (CSCheckDirAction.class),
                SystemAction.get (CSCheckDirRecursiveAction.class),
            };

    private static Icon icon = null;
    public JMenuItem getMenuPresenter () {
        JMenu menu = new LazyMenu (getName ());
        if (icon == null) icon = new ImageIcon (CSDirAction.class.getResource (iconResource ()));
        menu.setIcon (icon);
        return menu;
    }

    public JMenuItem getPopupPresenter () {
        JMenuItem item = new JMenuItem(SystemAction.get (CSCheckBufferAction_1.class));
        item.setIcon(null);
        return item;
    }

    public Component getToolbarPresenter () {
        JToolBar toolbar = new JToolBar (/* In JDK 1.3 you may add: getName () */);
        for (int i = 0; i < grouped.length; i++) {
            SystemAction action = grouped[i];
            if (action == null) {
                toolbar.addSeparator ();
            } else if (action instanceof Presenter.Toolbar) {
                toolbar.add (((Presenter.Toolbar) action).getToolbarPresenter ());
            }
        }
        return toolbar;
    }

    /**
     * Lazy menu which when added to its parent menu, will begin creating the
     * list of submenu items and finding their presenters.
     */
    private final class LazyMenu extends JMenu {
        
        public LazyMenu(String name) {
            super(name);
        }
                
        public JPopupMenu getPopupMenu() {
            if (getItemCount() == 0) {
                for (int i = 0; i < grouped.length; i++) {
                    SystemAction action = grouped[i];
                    if (action == null) {
                        addSeparator ();
                    } else if (action instanceof Presenter.Menu) {
                        add (((Presenter.Menu) action).getMenuPresenter ());
                    }
                }
            }
            return super.getPopupMenu();
        }
        
    }

}