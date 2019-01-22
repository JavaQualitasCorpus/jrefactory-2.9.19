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

import javax.swing.*;
import org.openide.cookies.*;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.*;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
import org.openide.windows.TopComponent;
import org.netbeans.modules.java.JavaDataObject;
import org.netbeans.modules.java.JavaEditor;

import org.acm.seguin.ide.common.IDEPlugin;


/**
 *  Checks the buffer (if it is Java source) for coding standards.
 *
 *@author     Mike Atkinson
 *@created    May 09, 2004
 *@since 2.9.19
 */
public class CPDCheckBufferAction_1 extends CookieAction implements Presenter.Menu, Presenter.Popup {

    /**
     *  Gets the helpCtx attribute of the CPDCheckBufferAction_1 object
     *
     *@return    The helpCtx value
     */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        // (PENDING) context help
        // return new HelpCtx (CSCheckBufferAction_1.class);
    }


    /**
     *  Gets the menuPresenter attribute of the CPDCheckBufferAction_1 object
     *
     *@return    The menuPresenter value
     */
//    public JMenuItem getMenuPresenter() {
//        JMenuItem item = new JMenuItem(getName());
//        item.addActionListener(this);
//        return item;
//    }


    /**
     *  Gets the name attribute of the CPDCheckBufferAction_1 object
     *
     *@return    The name value
     */
    public String getName() {
        return NbBundle.getMessage(CPDCheckBufferAction_1.class,
                "LBL_CPDCheckBufferAction_1");
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    protected Class[] cookieClasses() {
        return new Class[]{JavaEditor.class};
    }


    /**
     *  Perform special enablement check in addition to the normal one.
     *
     *@param  nodes  Description of the Parameter
     *@return        Description of the Return Value
     */
    protected boolean enable(Node[] nodes) {
        if (!super.enable(nodes)) {
            return false;
        }
        // Any additional checks ...
        return true;
    }


    /**
     *  Get the path to the icon to be displayed in menus, etc
     *
     *@return    Icon to be displayed in menus, etc.
     */
    protected String iconResource() {
        return null;
    }

    public JMenuItem getMenuPresenter () {
        return null;
    }

    public JMenuItem getPopupPresenter () {
        return new JMenuItem(this);
    }
    
    /**
     *  Perform extra initialization of this action's singleton. PLEASE do not
     *  use constructors for this purpose!
     */
    protected void initialize() {
        super.initialize();
        putProperty(CSCheckBufferAction_1.SHORT_DESCRIPTION,
                NbBundle.getMessage(CSCheckBufferAction_1.class,
                "HINT_CSCheckBufferAction"));
    }


    /**
     *@return    MODE_EXACTLY_ONE
     */
    protected int mode() {
        return MODE_EXACTLY_ONE;
    }

   
    /** this means that performAction is expecting to be run in the event thread */
    protected boolean asynchronous() {
        return false;
    }

    /**
     *  Description of the Method
     *
     *@param  nodes  Description of the Parameter
     */
    protected void performAction(final Node[] nodes) {
            //IDEPlugin.runInAWTThread(new Runnable() {
            //    public void run() {
                    JRefactory.ensureVisible();
                    JavaDataObject cookie = (JavaDataObject) nodes[0].getCookie(JavaDataObject.class);
                    java.awt.Frame frame = org.openide.windows.WindowManager.getDefault().getMainWindow();
                    try {
                        IDEPlugin.cpdBuffer(frame,cookie);
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
            //    }
            //});
    }
}

