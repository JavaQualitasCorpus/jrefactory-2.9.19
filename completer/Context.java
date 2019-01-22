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



//import anthelper.utils.SrcInfoUtils;
//import anthelper.utils.JEditLogger;
import java.io.File;
import java.lang.CloneNotSupportedException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.tools.ant.Project;
import org.gjt.sp.jedit.*;
import org.acm.seguin.ide.jedit.Navigator;

public class Context {
    // @TODO:  would be nice to have equals, clone, and test driver
    final static Navigator.NavigatorLogger logger = Completer.getLogger(ClassPathSrcMgr.class);
    
    private static final class Keys {
        static final String FILE_BUILD = "FILE_BUILD";
        static final String FILE_OUTPUT_DIR = "FILE_OUTPUT_DIR";
        static final String FILE_SRC_DIR = "FILE_SRC_DIR";
        static final String TASKA = "TASKA";
        static final String TASKB = "TASKB";
        static final String TASKC = "TASKC";
        static final String TASKD = "TASKD";
        static final String CLASSPATH = "CLASSPATH";
        static final String CLASSPATH_REF_ID = "CLASSPATH_REF_ID";
        static final String SOURCEPATH = "SOURCE_PATH";
        static final String SOURCEPATH_REF_ID = "SOURCE_PATH_REF_ID";

        static final String JVM_OPTIONS = "JVM_OPTIONS";
        static final String RUN_PARAMS = "RUN_PARAMS";
        static final String B_SAVE_BEFORE_COMPILE = "B_SAVE_BEFORE_COMPILE";
        static final String B_SAVE_BEFORE_RUN = "B_SAVE_BEFORE_RUN";
        static final String B_SYNC_TO_SPEEDJAVA = "B_SYNC_TO_SPEEDJAVA";
        static final String L_LAST_MOD = "L_LAST_MOD";
        static final String CLASS_NAME_CACHE = "CLASS_NAME_CACHE";
        static final String PROJECT = "PROJECT";

    }
    
    // the main data map
    Map _mapData = new Hashtable();
    // a cache of source dirs
    File[]  _fileSourceDirs = null;
    
    Context(File argFile){
        _put(Keys.FILE_BUILD, argFile);
        // defaults
        _put(Keys.B_SAVE_BEFORE_COMPILE, Boolean.TRUE);
        _put(Keys.B_SAVE_BEFORE_RUN, Boolean.TRUE);
        _put(Keys.B_SYNC_TO_SPEEDJAVA, Boolean.TRUE);
        _put(Keys.TASKA, "compile");
        _put(Keys.TASKB, "dist");
        _put(Keys.TASKC, "clean");
    }

    public String getAntTaskA(){ return _getString(Keys.TASKA); }
	public String getAntTaskB(){ return _getString(Keys.TASKB); }
	public String getAntTaskC(){ return _getString(Keys.TASKC); }
	public String getAntTaskD(){ return _getString(Keys.TASKD); }
    
    public void setAntTaskA(String argTaskName){ _put(Keys.TASKA, argTaskName); }
	public void setAntTaskB(String argTaskName){ _put(Keys.TASKB, argTaskName); }
	public void setAntTaskC(String argTaskName){ _put(Keys.TASKC, argTaskName); }
	public void setAntTaskD(String argTaskName){ _put(Keys.TASKD, argTaskName); }
    
    public File getBuildFile(){ return _getFile(Keys.FILE_BUILD); }
    
	public File getOutputDirectory(){ return _getFile(Keys.FILE_OUTPUT_DIR); }
	public void setOutputDirectory(File argFile){ _put(Keys.FILE_OUTPUT_DIR, argFile); }
    
    /*
	public File getSourceDirectory(){ return _getFile(Keys.FILE_SRC_DIR); }
	public void setSourceDirectory(File argFile){ _put(Keys.FILE_SRC_DIR, argFile); }
	*/
    
    public String getJVMOptions(){ return _getString(Keys.JVM_OPTIONS); }
    public void setJVMOptions(String arg){ _put(Keys.JVM_OPTIONS, arg); }
    
    public String getRunParams(){ return _getString(Keys.RUN_PARAMS); }
    public void setRunParams(String arg){ _put(Keys.RUN_PARAMS, arg); }
	
	public String getClassPath(){ return _getString(Keys.CLASSPATH); }
    public void setClassPath(String argClassPath){ _put(Keys.CLASSPATH, argClassPath); }
    
    public String getClassPathRefID(){ return _getString(Keys.CLASSPATH_REF_ID); }
    public void setClassPathRefID(String argRefID){ _put(Keys.CLASSPATH_REF_ID, argRefID); }

	public String getSourcePath(){ return _getString(Keys.SOURCEPATH); }
    public void setSourcePath(String argSourcePath){ 
        _put(Keys.SOURCEPATH, argSourcePath);
        if (argSourcePath == null){
            _fileSourceDirs = null;
        } else {
            _fileSourceDirs = pathToList(argSourcePath);
        }
    }
    public File[] getSourceDirectories(){
        return _fileSourceDirs;
    }
    
    public static File[] pathToList(String argCP) {
        File[] files = null;
        if (argCP != null) {
            List listFiles = new ArrayList();
            for (StringTokenizer st = new StringTokenizer(argCP, File.pathSeparator);
                st.hasMoreTokens(); ) {
                listFiles.add(new File(st.nextToken()));
            }
            files = (File[]) listFiles.toArray(new File[listFiles.size()]);
        }
        return files;
    }

    public String getSourcePathRefID(){ return _getString(Keys.SOURCEPATH_REF_ID); }
    public void setSourcePathRefID(String argRefID){ _put(Keys.SOURCEPATH_REF_ID, argRefID); }
	
    public boolean getSaveBeforeCompile(){ return _getBoolean(Keys.B_SAVE_BEFORE_COMPILE); }
    public boolean getSaveBeforeRun(){ return _getBoolean(Keys.B_SAVE_BEFORE_RUN); }
    public boolean getSyncToSpeedJava(){ return _getBoolean(Keys.B_SYNC_TO_SPEEDJAVA); }
	
    public void setSaveBeforeCompile(boolean arg){ _put(Keys.B_SAVE_BEFORE_COMPILE, new Boolean(arg)); }
	public void setSaveBeforeRun(boolean arg){ _put(Keys.B_SAVE_BEFORE_RUN, new Boolean(arg)); }
	public void setSyncToSpeedJava(boolean arg){ _put(Keys.B_SYNC_TO_SPEEDJAVA, new Boolean(arg)); }
    
    public long getLastMod(){
        Long lastMod = (Long) _mapData.get(Keys.L_LAST_MOD);
        if (lastMod == null){
            lastMod = new Long(getBuildFile().lastModified());
            _put(Keys.L_LAST_MOD, lastMod);
		}
        return lastMod.longValue();
	}
	public void setLastMod(long arg){ _put(Keys.L_LAST_MOD, new Long(arg));}
    
    public synchronized ClassNameCache getClassNameCache(){
        ClassNameCache cnc = (ClassNameCache) _mapData.get(Keys.CLASS_NAME_CACHE);
        if (cnc == null){
            String strCP = getClassPath();
            if (strCP == null) {
                // null cp, so guess something...if source dir is null, this is a new context
                strCP = (getSourcePath() == null ? "" : getSourcePath());
            }
            cnc = new ClassNameCache(strCP);
            _put(Keys.CLASS_NAME_CACHE, cnc);
		}
		return cnc;
	}
    public synchronized void resetClassNameCache(){ _mapData.remove(Keys.CLASS_NAME_CACHE); }
    
    public synchronized Project getProject(){
        Project proj = (Project) _mapData.get(Keys.PROJECT);
        if (proj == null){
        //FIXME:    proj = AntUtils.createAndInitProject(getBuildFile());
        }
        return proj;     
    }
    public synchronized void setProject(Project arg){ _put(Keys.PROJECT, arg); }
    
    
    class ToStringComparator implements Comparator {
        public int compare(Object o1, Object o2){
            return o1.toString().compareTo(o2.toString());
        }
    }
	public String toString(){
        
        TreeSet setKeys = new TreeSet();
        Field[] fields = Keys.class.getDeclaredFields();
        for (int i=0, l = fields.length; i < l; i++){
            setKeys.add(fields[i].getName());
        }
        
		String NL = "\n", TAB = "\t";
		StringBuffer sb = new StringBuffer("[" + super.toString() + "]" + NL);
        String strKey;
        Object objVal;
        for (Iterator it = setKeys.iterator(); it.hasNext(); ){
            strKey = it.next().toString();
            objVal = _mapData.get(strKey);
            sb.append(TAB).append(strKey);
            sb.append("=");
            sb.append(objVal == null ? "" : objVal.toString()).append(NL);
        }
		return sb.toString();
	}
	
    private void _put(Object objKey, Object objVal){
        if (objKey != null && objVal != null){
            _mapData.put(objKey, objVal);
        }
    }
    
    private String _getString(Object objKey){
        return (String) (objKey == null ?  null : _mapData.get(objKey)); 
    }
    
    private File _getFile(Object objKey){
        return (File) (objKey == null ?  null : _mapData.get(objKey)); 
    }
    
    private boolean _getBoolean(Object objKey){
        boolean b = false;
        if (objKey != null) {
            Object objVal = _mapData.get(objKey);
            b = objVal == null ? false : ((Boolean)_mapData.get(objKey)).booleanValue();
        }
        return b;
    }
    
    public int hashCode() { 
        // we just add a random offset
        return _mapData.hashCode() + 10220;
    }
    
    public boolean equals(Object o){
        if (this == o) {  // Step 1: Perform an == test
            return true;
        }
        
        if(!(o instanceof Context )){
            return false;
        }
        Context context = (Context) o;
        return context._mapData.equals(this._mapData);
    }
    


    
    public static Context getTestContext(){
        return new Context(new File("C:/Documents and Settings/ueab/.jedit/jars/AntHelper/build.xml"));
    }
    public static void main(String[] args){
         try{
             logger.msg(getTestContext().toString());
         }catch(Throwable t){
             t.printStackTrace();
         }
     }
     
}
