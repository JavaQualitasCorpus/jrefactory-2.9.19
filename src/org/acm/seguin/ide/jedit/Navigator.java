/*
    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more detaProjectTreeSelectionListenerils.
    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
  */
package org.acm.seguin.ide.jedit;

import javax.swing.JScrollPane;
import javax.swing.tree.*;

import net.sourceforge.jrefactory.ast.*;

// JRefactory imports
import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.buffer.BufferChangeListener;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.msg.*;
import org.gjt.sp.util.Log;



/**
 *  Main GUI for JRefactory
 *
 * @author     <a href="mailto:JRefactoryPlugin@ladyshot.demon.co.uk">Mike Atkinson</a>
 * @since      0.0.1
 * @created    23 July 2003
 * @version    $Id: Navigator.java,v 1.5 2003/12/02 23:39:37 mikeatkinson Exp $
 */
public final class Navigator extends JScrollPane implements BufferChangeListener, EBComponent {
   private View view;
   private org.acm.seguin.ide.common.Navigator navigator;


   /**
    *  Constructor for the Navigator object
    *
    * @param  aView  Description of Parameter
    * @since         v 1.0
    */
   public Navigator(View aView) {
      this.view = aView;
      navigator = new org.acm.seguin.ide.common.Navigator(view);
      navigator.viewCreated(view);
      Buffer[] buffers = jEdit.getBuffers();
      for (int i = 0; i < buffers.length; i++) {
         navigator.addBuffer(buffers[i]);
      }
      JavaStylePlugin.addNavigator(this);
      //add(new JScrollPane(navigator), BorderLayout.CENTER);
      setViewportView(navigator);
      Buffer buffer= aView.getBuffer();
      addBufferChangeListener(buffer, this);
   }


   /**
    *  Description of the Method
    *
    * @param  msg  Description of the Parameter
    * @since       v 1.0
    */
   public void handleMessage(EBMessage msg) {
      System.out.println("Navigator: handleMessage(" + msg + ")");
      try {
         if (msg instanceof ViewUpdate) {
            ViewUpdate update = (ViewUpdate)msg;
            System.out.println("Navigator.handleMessage(" + update + ")");
            if (update.getWhat() == ViewUpdate.CREATED) {
               navigator.viewCreated(update.getView());
            } else if (update.getWhat() == ViewUpdate.CLOSED) {
               navigator.viewClosed(update.getView());
            }
         } else if (msg instanceof BufferUpdate) {
            BufferUpdate update = (BufferUpdate)msg;
            System.out.println("Navigator.handleMessage(" + update + ")");
            String name = update.getBuffer().getName().toLowerCase();
            if (name.endsWith(".java")) {
               Buffer buffer = update.getBuffer();
               if (update.getWhat() == BufferUpdate.CREATED) {
                  Log.log(Log.MESSAGE, this, "created buffer " + name);
                  navigator.addBuffer(buffer);
                  addBufferChangeListener(buffer, this);
               } else if (update.getWhat() == BufferUpdate.SAVED) {
                  Log.log(Log.MESSAGE, this, "saving buffer " + name);
                  navigator.addBuffer(buffer);
                  addBufferChangeListener(buffer, this);
               } else if (update.getWhat() == BufferUpdate.CLOSED) {
                  Log.log(Log.MESSAGE, this, "removing buffer " + name);
                  navigator.removeBuffer(buffer);
                  buffer.removeBufferChangeListener(this);
               } else if (update.getWhat() == BufferUpdate.LOADED) {
                  Log.log(Log.MESSAGE, this, "loaded buffer " + name);
                  navigator.addBuffer(buffer);
                  addBufferChangeListener(buffer,this);
               } else if (update.getWhat() == BufferUpdate.DIRTY_CHANGED) {
                  Log.log(Log.MESSAGE, this, "dirty changed buffer " + name);
                  navigator.addBuffer(buffer);
                  addBufferChangeListener(buffer,this);
               }
            }
            //} else if (msg instanceof EditPaneUpdate) {
            //EditPaneUpdate epu = (EditPaneUpdate)msg;
            //View view = epu.getEditPane().getView();
            //Navigation nav = (Navigation)(viewToNavigation.get(view));
            //if (nav != null) {
            /*
                if (epu.getWhat() == EditPaneUpdate.BUFFER_CHANGED &&
                    Config.FLUSH_CACHE_ON_BUFFER_CHANGE.getAsBoolean()){
                    //logger.msg("****buffer.changed", epu.toString());
                    flushCacheForBuffer(view);
                }
                */
            //}
         } else if (msg instanceof PropertiesChanged) {
            navigator.reconfigure();
            /*
		   } else if (msg instanceof ContextUpdateMessage) {
            if (ClassPathSrcMgr.isAntHelperSrc()){
                ClassPathSrcMgr.getInstance().reload();
            }
*/
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void addBufferChangeListener(Buffer buffer, Navigator nav) {
       BufferChangeListener[] listeners = buffer.getBufferChangeListeners();
       for (int i=0; i<listeners.length; i++) {
          if (listeners[i] == nav) {
             return;
          }
       }
       buffer.addBufferChangeListener(nav);
   }
   
   
   /**
    *  Description of the Method
    *
    * @param  buffer     Description of Parameter
    * @param  startLine  Description of Parameter
    * @param  offset     Description of Parameter
    * @param  numLines   Description of Parameter
    * @param  length     Description of Parameter
    * @since             v 1.0
    */
   public void preContentInserted(Buffer buffer, int startLine, int offset, int numLines, int length) {
   }


   /**
    *  Description of the Method
    *
    * @param  buffer     Description of Parameter
    * @param  startLine  Description of Parameter
    * @param  offset     Description of Parameter
    * @param  numLines   Description of Parameter
    * @param  length     Description of Parameter
    * @since             v 1.0
    */
   public void contentInserted(Buffer buffer, int startLine, int offset, int numLines, int length) {
      navigator.contentInserted(view, buffer, offset, length);
   }


   /**
    *  Description of the Method
    *
    * @param  buffer     Description of Parameter
    * @param  startLine  Description of Parameter
    * @param  offset     Description of Parameter
    * @param  numLines   Description of Parameter
    * @param  length     Description of Parameter
    * @since             v 1.0
    */
   public void preContentRemoved(Buffer buffer, int startLine, int offset, int numLines, int length) {
   }


   /**
    *  Description of the Method
    *
    * @param  buffer     Description of Parameter
    * @param  startLine  Description of Parameter
    * @param  offset     Description of Parameter
    * @param  numLines   Description of Parameter
    * @param  length     Description of Parameter
    * @since             v 1.0
    */
   public void contentRemoved(Buffer buffer, int startLine, int offset, int numLines, int length) {
      navigator.contentRemoved(view, buffer, offset, length);
   }


   /**
    *  Description of the Method
    *
    * @param  buffer     Description of Parameter
    * @param  startLine  Description of Parameter
    * @param  endLine    Description of Parameter
    * @since             v 1.0
    */
   public void foldLevelChanged(Buffer buffer, int startLine, int endLine) {
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void wrapModeChanged(Buffer buffer) {
   }


   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void foldHandlerChanged(Buffer buffer) {
   }



   /**
    *  Description of the Method
    *
    * @param  buffer  Description of Parameter
    * @since          v 1.0
    */
   public void transactionComplete(Buffer buffer) {
      navigator.transactionComplete(view, buffer);
   }

}

