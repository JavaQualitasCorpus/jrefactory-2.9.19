package org.acm.seguin.completer;


//import anthelper.ClassNameCache;
//import anthelper.Context;
//import anthelper.ContextMgr;
//import anthelper.JavaUtils;
//import anthelper.utils.JEditLogger;
import org.acm.seguin.completer.info.ClassTable;
import org.acm.seguin.ide.jedit.Navigator;
import java.util.*;
import org.gjt.sp.jedit.Buffer;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.acm.seguin.ide.jedit.Navigator;

/**
 * Manages/Abstracts where the class path comes from.
 *
 * @author    btateyama@yahoo.com
 * @created   February 15, 2003
 */
public class ClassPathSrcMgr {
    final static Navigator.NavigatorLogger logger = Completer.getLogger(ClassPathSrcMgr.class);
    
    private static ClassPathSrcMgr instance = new ClassPathSrcMgr();
    private static Map mapPathToCNC = new Hashtable();
    private static ClassNameCache getCNCFromCache(String argPath){
        ClassNameCache cnc = null;
        
        if (argPath == null){
            argPath = "";
        }
        cnc = (ClassNameCache)mapPathToCNC.get(argPath);
        if (cnc == null){
            cnc = new ClassNameCache(argPath);
            mapPathToCNC.put(argPath, cnc);
        }
        
        return cnc;
    }
    public static ClassPathSrcMgr getInstance() {
        return instance;
    }
    
    static boolean isAntHelperSrc(){
        String strSrc = Config.CLASS_PATH_SOURCE.getAsString();
        return Config.CP_SRC_ANT_HELPER.equals(strSrc);
    }
    
    static boolean isJCompilerSrc(){
        String strSrc = Config.CLASS_PATH_SOURCE.getAsString();
        return Config.CP_SRC_JCOMPILER.equals(strSrc);
    }
    
    static boolean isCustomSrc(){
        String strSrc = Config.CLASS_PATH_SOURCE.getAsString();
        return Config.CP_SRC_CUSTOM.equals(strSrc);
    }
    
    Set _setClassPathSrcs = new HashSet();
    public ClassPathSrc getClassPathSrc(View argView, Buffer argBuffer){
        ClassPathSrc cpSrc = null;
        if (isAntHelperSrc()){
            Context context = ContextMgr.getContext(argView, argBuffer, false);
            if (context != null){
                cpSrc = new AntHelperClassPathSrc(context);
            }
        } else if (isJCompilerSrc()){
            cpSrc = new JCompilerSrc();
        } else if (isCustomSrc()){
            cpSrc = new CustomSrc();
        } else{
            logger.error("Unknown src type: " + Config.CLASS_PATH_SOURCE.getAsString());
        }
        
        // keep track of what's out there
        _setClassPathSrcs.add(cpSrc);
        return cpSrc;
    }
    
    public void reload(){
        // reload the cache!
        mapPathToCNC.clear();
        ClassPathSrc cpSrc;
        logger.msg("ClassPathSrcs", _setClassPathSrcs.size());
        for (Iterator it = _setClassPathSrcs.iterator(); it.hasNext();){
            cpSrc = (ClassPathSrc) it.next();
            logger.msg("Reloading classes for source: " + cpSrc.getDesc());
            cpSrc.reload();
            // now reinit the class table
            ClassTable.getInstance(cpSrc).reinit(cpSrc);
        }
    }
    
    public void cleanup(){
        ClassPathSrc cpSrc;
        for (Iterator it = _setClassPathSrcs.iterator(); it.hasNext();){
            cpSrc = (ClassPathSrc) it.next();
            ClassTable.removeInstance(cpSrc);
        }
        
        _setClassPathSrcs.clear();
        mapPathToCNC.clear();
    }

    static boolean equals(Object arg1, Object arg2){
        return ((arg1 == arg2) || (arg1 != null && arg1.equals(arg2)));
    }
    
    static class AntHelperClassPathSrc implements ClassPathSrc {
        Context _context = null;
        public AntHelperClassPathSrc(Context argContext){
            _context = argContext;
        }
        public String getDesc(){
            return "[AntHelperClassPathSrc, Context=" + _context.getBuildFile().getPath() + "]";
        }
        public ClassNameCache getCNC(){
            return _context.getClassNameCache();
        }
        public String getClassPath(){
            return _context.getClassPath();
        }
        public void reload(){
            // force the reload of the cache
            // @REVIEW: unfortunately, we can't use AntHelperPlugin.flushCache();
            _context.resetClassNameCache();
        }
        public Object getKey(){
            return _context;
        }
        
        public int hashCode(){
            return (getClassPath() != null ? getClassPath().hashCode() + 41577 : 0);
        }
        
        public boolean equals(Object obj){
            if (obj instanceof AntHelperClassPathSrc){
                AntHelperClassPathSrc antSrc = (AntHelperClassPathSrc) obj;
                return ClassPathSrcMgr.equals(antSrc.getClassPath(), getClassPath());
            }else{
                return false;
            }
        }
    }

    
    static abstract class AbstractPathSrc implements ClassPathSrc {
        String _strPath;
        String _strDesc;
        Object _objKey;
        ClassNameCache _cnc = null;
        public AbstractPathSrc(String argDesc, Object argKey){
            _strDesc = argDesc;
            _objKey = argKey;
        }
        public ClassNameCache getCNC(){
            return _cnc;
        }
        public void reload(){
            _strPath = getClassPath();
            _cnc = getCNCFromCache(_strPath);
        }
        public String getDesc(){
            return _strDesc;
        }
        public Object getKey(){
            return _objKey;
        }
        public int hashCode(){
            return (_strPath != null ? _strPath.hashCode()+41577 : 0);
        }
        public boolean equals(Object obj){
            if (obj instanceof AbstractPathSrc){
                AbstractPathSrc src = (AbstractPathSrc) obj;
                return (ClassPathSrcMgr.equals(src.getKey(), _objKey) && 
                        ClassPathSrcMgr.equals(src.getClassPath(), _strPath));
            }else{
                return false;
            }
        }
        
        public abstract String getClassPath();
    }
    
    static class JCompilerSrc extends AbstractPathSrc {
        public JCompilerSrc(){
            super("[JCompilerSrc]", Config.CP_SRC_JCOMPILER);
            reload();
        }
        public String getClassPath(){
            return jEdit.getProperty("jcompiler.classpath", "");
        }
    }
    static class CustomSrc extends AbstractPathSrc {
        public CustomSrc(){
            super("[Custom]", Config.CP_SRC_CUSTOM);
            reload();
        }
        public String getClassPath(){
            return Config.CLASS_PATH_CUSTOM.getAsString();
        }
    }
}

