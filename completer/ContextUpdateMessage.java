package org.acm.seguin.completer;

/*
 * Copyright (c) 2002, Beau Tateyama
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


import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import java.io.File;
import org.gjt.sp.jedit.*;
//import anthelper.utils.JEditLogger;

public class ContextUpdateMessage extends EBMessage {
    Context _context = null;
    public ContextUpdateMessage(EBComponent argSource, Context argContext){
        super(argSource);
        _context = argContext;
    }
	
	public File getBuildFile(){ return _context.getBuildFile(); }
    public String getClassPath(){ return _context.getClassPath(); }
	public File getOutputDirectory(){ return _context.getOutputDirectory(); }
	public String getSourcePath(){ return _context.getSourcePath(); }
    public ClassNameCache getClassNameCache(){ return _context.getClassNameCache(); }
    
	// public boolean getSaveBeforeRun(){ return _context.getSaveBeforeRun(); }
	// public boolean getSyncToSpeedJava(){ return _context.getSyncToSpeedJava(); }
	// public long getLastMod(){ return _context.getLastMod(); }
    // public Project getProject(){ return _context.getProject(); }
    
    public String toString(){
		String NL = "\n", TAB = "\t";
		StringBuffer sb = new StringBuffer("[" + super.toString() + "]" + NL);
        sb.append(_context.toString());
		return sb.toString();
	}
    
    
    public static void main(String[] args){
        try{
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
 
}
