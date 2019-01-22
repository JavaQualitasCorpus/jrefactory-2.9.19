/*
 * CSCheckAllBuffersAction.java
 *
 * Created on 01 October 2003, 22:09
 */

package org.acm.seguin.ide.netbeans;

import javax.swing.*;
import org.openide.cookies.*;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.*;

import org.acm.seguin.ide.common.IDEPlugin;


/**
 *  Applies the JRefactory pretty printer to the currently selected editor. Will
 *  only be applied if only one editor is selected.
 *
 *@author     unknown
 *@created    October 18, 2001
 */
public class CSCheckAllBuffersAction extends CookieAction {

    /**
     *  Gets the helpCtx attribute of the PrettyPrinterAction object
     *
     *@return    The helpCtx value
     */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
        // (PENDING) context help
        // return new HelpCtx (PrettyPrinterAction.class);
    }


    /**
     *  Gets the name attribute of the PrettyPrinterAction object
     *
     *@return    The name value
     */
    public String getName() {
        return NbBundle.getMessage(CSCheckAllBuffersAction.class,
                "LBL_CSCheckAllBuffersAction");
    }


    /**
     *  Description of the Method
     *
     *@return    Description of the Return Value
     */
    protected Class[] cookieClasses() {
        return new Class[]{EditorCookie.class};
    }


    /**
     *  Perform special enablement check in addition to the normal one.
     *
     *@param  nodes  Description of the Parameter
     *@return        Description of the Return Value
     */
    protected boolean enable(Node[] nodes) {
        // Any additional checks ...
        return true;
    }


    /**
     *  Get the path to the icon to be displayed in menus, etc
     *
     *@return    Icon to be displayed in menus, etc.
     */
    protected String iconResource() {
        return "org/acm/seguin/ide/netbeans/CSCheckAllBuffersActionIcon.gif";
    }


    /**
     *  Perform extra initialization of this action's singleton. PLEASE do not
     *  use constructors for this purpose!
     */
    protected void initialize() {
        super.initialize();
        putProperty(CSCheckAllBuffersAction.SHORT_DESCRIPTION,
                NbBundle.getMessage(CSCheckAllBuffersAction.class,
                "HINT_CSCheckAllBuffersAction"));
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
    protected void performAction(Node[] nodes) {
        JRefactory.ensureVisible();
        java.awt.Frame frame = org.openide.windows.WindowManager.getDefault().getMainWindow();
        IDEPlugin.checkAllOpenBuffers(frame);
    }

}