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
package org.acm.seguin.ide.jedit;

import javax.swing.text.Element;
import javax.swing.text.Document;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.buffer.BufferChangeListener;
import net.sourceforge.jrefactory.action.CurrentSummary;
import org.acm.seguin.ide.common.MultipleDirClassDiagramReloader;


/**
 *  The object that determines the current summary for the jEdit IDE.
 *
 *@author     Mike Atkinson
 *@created    16 November 2003
 */
public class JEditCurrentSummary extends CurrentSummary implements BufferChangeListener {
    private View view;
    private Buffer lastActiveBuffer;
    private MultipleDirClassDiagramReloader reloader = null;


    /**
     *  Constructor for the JBuilderCurrentSummary object
     */
    public JEditCurrentSummary(View view) {
        super();
        this.view = view;
    }


    /**
     *  Gets the reloader
     *
     *@return    The MetadataReloader value
     */
    protected MultipleDirClassDiagramReloader getMetadataReloader() {
      System.out.println("JEditCurrentSummary.registerWithCurrentDocument()");
        //return UMLNodeViewerFactory.getFactory().getReloader();
        if (reloader==null) {
           reloader = new MultipleDirClassDiagramReloader();
        }
        return reloader;
    }


    /**
     *  Register the current summary listener with the current document
     */
    protected void registerWithCurrentDocument() {
      System.out.println("JEditCurrentSummary.registerWithCurrentDocument()");
        Buffer buffer = view.getBuffer();
        if (buffer != lastActiveBuffer) {
            upToDate = false;

            if (lastActiveBuffer != null) {
                lastActiveBuffer.removeBufferChangeListener(this);
            }

            lastActiveBuffer = buffer;

            if ((buffer == null) || !(buffer.getPath().endsWith(".java")) ) {
                return;
            }

            buffer.addBufferChangeListener(this);
        }
    }

    public void contentInserted(Buffer buffer, int startLine, int offset, int numLines, int length) {
       insertUpdate(new MyDocumentEvent(buffer, offset, length));
    }
    public void contentRemoved(Buffer buffer, int startLine, int offset, int numLines, int length) {
       removeUpdate(new MyDocumentEvent(buffer, offset, length));
    }
    public void foldHandlerChanged(Buffer buffer) {
       changedUpdate(new MyDocumentEvent(buffer, -1, -1));
    }
    public void foldLevelChanged(Buffer buffer, int startLine, int endLine) {
       changedUpdate(new MyDocumentEvent(buffer, -1, -1));
    }
    public void preContentRemoved(Buffer buffer, int startLine, int offset, int numLines, int length) {
       changedUpdate(new MyDocumentEvent(buffer, -1, -1));
    }
    public void transactionComplete(Buffer buffer) {
       changedUpdate(new MyDocumentEvent(buffer, -1, -1));
    }
    public void wrapModeChanged(Buffer buffer) {
       changedUpdate(new MyDocumentEvent(buffer, -1, -1));
    }
    
    private class MyDocumentEvent implements DocumentEvent {
       private Buffer buffer;
       private int offset;
       private int length;
       public MyDocumentEvent(Buffer buffer, int offset, int length) {
          this.buffer = buffer;
          this.offset = offset;
          this.length = length;
       }
       public DocumentEvent.ElementChange getChange(Element elem) {
          return null;
       }
       public Document getDocument() {
          return null;
       }
       public int getLength() {
          return length;
       }
       public int getOffset() {
          return offset;
       }
       public DocumentEvent.EventType getType() {
          return null;
       }
   }
}

