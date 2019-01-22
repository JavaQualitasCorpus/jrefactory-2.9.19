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

import java.awt.Frame;
import javax.swing.text.Element;
import javax.swing.text.Document;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.netbeans.modules.java.JavaDataObject;

import net.sourceforge.jrefactory.action.CurrentSummary;
import org.acm.seguin.ide.common.MultipleDirClassDiagramReloader;



/**
 *  The object that determines the current summary for the Netbeans IDE.
 *
 *@author     Mike Atkinson
 *@created    30 April 2004
 */
public class NetbeansCurrentSummary extends CurrentSummary  {
    private Frame view;
    private JavaDataObject lastActiveBuffer;
    private MultipleDirClassDiagramReloader reloader = null;


    /**
     *  Constructor for the JBuilderCurrentSummary object
     */
    public NetbeansCurrentSummary(Frame view) {
        super();
        this.view = view;
    }


    /**
     *  Gets the reloader
     *
     *@return    The MetadataReloader value
     */
    protected MultipleDirClassDiagramReloader getMetadataReloader() {
      //System.err.println("NetbeansCurrentSummary.getMetadataReloader()");
        if (reloader==null) {
           reloader = new MultipleDirClassDiagramReloader();
        }
        return reloader;
    }


    /**
     *  Register the current summary listener with the current document
     */
    protected void registerWithCurrentDocument() {
        //System.err.println("NetbeansCurrentSummary.registerWithCurrentDocument() lastActiveBuffer="+lastActiveBuffer);
        org.netbeans.modules.java.JavaDataObject buffer = null;
        org.openide.windows.Mode mode = org.openide.windows.WindowManager.getDefault().findMode("editor");
        TopComponent[] tc = mode.getTopComponents();
        for (int n=0; n<tc.length; n++) {
            if (tc[n].isVisible() && tc[n] instanceof org.netbeans.modules.java.JavaEditor.JavaEditorComponent) {
               Lookup lookup = tc[n].getLookup();
               buffer = (org.netbeans.modules.java.JavaDataObject)lookup.lookup(org.netbeans.modules.java.JavaDataObject.class);
            }
        }

        if (buffer != lastActiveBuffer) {
            upToDate = false;

            if (lastActiveBuffer != null) {
                //lastActiveBuffer.removeBufferChangeListener(this);
            }

            lastActiveBuffer = buffer;

            if (buffer != null) {
                //buffer.addBufferChangeListener(this);
            }
        }
        //System.err.println("   new lastActiveBuffer="+lastActiveBuffer);
    }

}
