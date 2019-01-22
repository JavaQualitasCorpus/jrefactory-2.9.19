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

import javax.swing.JOptionPane;
import org.acm.seguin.refactor.RefactoringException;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.netbeans.modules.java.JavaDataObject;
import org.openide.windows.WindowManager;

import net.sourceforge.jrefactory.action.SelectedFileSet;



/**
 *  Description of the Class
 *
 *@author     Mike Atkinson
 *@created    May 05, 2004
 */
public class AddChildClassAction extends CookieAction {

    /**
     *  Gets the HelpCtx attribute of the AddChildClassAction object
     *
     *@return    The HelpCtx value
     */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        // return new HelpCtx (AddChildClassAction.class);
    }


    /**
     *  Gets the Name attribute of the AddChildClassAction object
     *
     *@return    The Name value
     */
    public String getName() {
        return NbBundle.getMessage(AddChildClassAction.class, "LBL_AddChildClassAction");
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     */
    protected Class[] cookieClasses() {
        return new Class[]{JavaDataObject.class};
    }


    /**
     *  Perform special enablement check in addition to the normal one.
     *
     *@param  nodes  Description of Parameter
     *@return        Description of the Returned Value
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
        return "org/acm/seguin/ide/netbeans/AddChildClassActionIcon.gif";
    }


    /**
     *  Perform extra initialization of this action's singleton. PLEASE do not
     *  use constructors for this purpose!
     */
    protected void initialize() {
        super.initialize();
        putProperty(PrettyPrinterAction.SHORT_DESCRIPTION,
                NbBundle.getMessage(AddChildClassAction.class,
                "HINT_AddChildClassAction"));
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     */
    protected int mode() {
        return MODE_EXACTLY_ONE;
    }


    /**
     *  Description of the Method
     *
     *@param  nodes  Description of Parameter
     */
    protected void performAction(Node[] nodes) {
        JRefactory.ensureVisible();
        JavaDataObject cookie = (JavaDataObject) nodes[0].getCookie(JavaDataObject.class);
        //System.err.println("JRefactory.addChildClassAction(view," + cookie + ")");
        //System.err.flush();
        SelectedFileSet sfs = new NetbeansSelectedFileSet(WindowManager.getDefault().getMainWindow(), cookie);
        net.sourceforge.jrefactory.action.AddChildClassAction addChildClassAction = new net.sourceforge.jrefactory.action.AddChildClassAction(sfs);
        addChildClassAction.actionPerformed(new java.awt.event.ActionEvent(cookie, -1, "addChildClassAction"));
        //System.err.println("JRefactory.addChildClassAction(view," + cookie + ") - end");
        //System.err.flush();
    }

}
