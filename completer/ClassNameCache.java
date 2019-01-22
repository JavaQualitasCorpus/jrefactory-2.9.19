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

      

import java.net.URLClassLoader;
//import gnu.regexp.REException;
//import gnu.regexp.RE;
import java.util.regex.*;
import java.io.*;
import java.io.FilenameFilter;
import java.net.*;
import java.util.*;
import java.util.zip.*;
import org.gjt.sp.jedit.*;
import org.acm.seguin.ide.jedit.Navigator;

public class ClassNameCache {
    final static Navigator.NavigatorLogger logger = Completer.getLogger(ClassPathSrcMgr.class);
    private static Map __mapBootCP;
    private static List __listBootClasses;
    static {
        try{
            Map mapBootCP = new Hashtable();
            String strBootCP = System.getProperty("sun.boot.class.path");
            ClassNameCache cnc = new ClassNameCache(strBootCP,mapBootCP);
            __mapBootCP = Collections.unmodifiableMap(mapBootCP);
            __listBootClasses = getClassInfoList(__mapBootCP);
            Collections.sort(__listBootClasses);
        }catch(Exception e){
            logger.error("Error loading boot CP", e);
            e.printStackTrace();
        }
    }
    public static List getBootClassList(){ return __listBootClasses; }
    
    public static class ClassInfo implements Comparable {
        
        String _strFullCN = null;
        File _fileSrc = null;
        ClassInfo(File argSrc, String argFullClassName){
            _fileSrc = argSrc;
            _strFullCN = argFullClassName;
        }
        

        public int compareTo(Object obj){
            if (obj instanceof ClassInfo){
                return _strFullCN.compareTo(((ClassInfo) obj).getFullClassName()); 
            }else{
                return 0;
            }
        }
        public String getSrc() { return _fileSrc.getPath(); }
        public File getSrcFile() { return _fileSrc; }
        public String getFullClassName() { return _strFullCN; }
        public String getClassName() { 
            return _strFullCN.substring(_strFullCN.lastIndexOf(".") + 1);
        }
        public String toString() { return getClassName(); }
        public boolean equals(Object obj){
            boolean bEquals = false;
            if (obj == this){
                bEquals = true;
            }else if (obj != null){
                ClassInfo ci = (ClassInfo)obj;
                bEquals = (ci.getSrcFile().equals(getSrcFile()) &&
                           ci.getFullClassName().equals(getFullClassName()));
            }
            return bEquals;
        }
    }
    
    static class JarFileMgr {
        static JarFileMgr _instance = new JarFileMgr();
        static JarFileMgr getInstance() { return _instance; }
        Map _mapJarInfos = new HashMap();
        JarFileMgr(){}
        
        synchronized int parseJarFile(File argFile, Map argMap) throws IOException {
            int iClassCount = 0;
            JarInfo jarInfo  = (JarInfo)_mapJarInfos.get(argFile);
            if (jarInfo == null ||
                argFile.lastModified() > jarInfo.lastModified()){
                
                Map mapData = new HashMap();
                int iCount = parseZipFile(argFile, mapData);
                jarInfo = new JarInfo(argFile, argFile.lastModified(), mapData, iCount);
                _mapJarInfos.put(argFile, jarInfo);
            }
            iClassCount = jarInfo.getClassCount();
            // now merge the jarinfo into the map.
            String strCN;
            Set setNew, setExisting;
            for (Iterator it = jarInfo.getData().keySet().iterator(); it.hasNext();){
                // retrieve new entries
                strCN = (String)it.next();
                setNew = (Set) jarInfo.getData().get(strCN);
                // merge into argMap
                setExisting = (Set) argMap.get(strCN);
                if (setExisting == null){
                    // new, so just add it
                    argMap.put(strCN, setNew);
                }else{
                    // class name entry exists, so just add to set
                    setExisting.addAll(setNew);
                }
            }
            return iClassCount;
        }
    }
    static class JarInfo {
        File _file;
        Map _mapData;
        long _lastMod;
        int _iClassCount;
        JarInfo(File argFile, long argLastMod, Map argData, int argClassCount){
            _file = argFile;
            _lastMod = argLastMod;
            _mapData = argData;
            _iClassCount = argClassCount;
        }
        int getClassCount() { return _iClassCount; }
        File getFile() { return _file; }
        Map getData(){ return _mapData; }
        long lastModified() { return _lastMod; }
    }

	String _strPath = null;
	URL[] _urls = null;
	Map _mapClassNameToClassList;
    URLClassLoader _urlClassLoader = null;
    int _iClassCount = 0;
    String _strName = null;
    private ClassNameCache(String argPath, Map argMap){
        _mapClassNameToClassList = argMap;
        parseClasses(argPath);
        
        // add a class loader for fun!
        _urlClassLoader = new URLClassLoader(getClassPathAsURLs()); 
                                             //jEdit.class.getClassLoader());
                                             //null);
                                             //getClass().getClassLoader());
        
    }
    
    public void setName(String argName){
        _strName = argName;
    }
    
    public String getName() {
        return _strName;
    }
	public ClassNameCache(String argPath){
        this(argPath, new Hashtable()); //__mapBootCP));
    }
    
    public URL[] getClassPathAsURLs(){
        return _urls;
    }
    
    public URLClassLoader getDynamicClassLoader(){
        return _urlClassLoader;
    }
    

    private void parseClasses(String argPath){
        _strPath = argPath; 
        //+ System.getProperty("path.separator") + System.getProperty("sun.boot.class.path");
		_urls = pathToURLs(_strPath);
		File file = null;
		for (int i=0; i < _urls.length; i++){
			file = new File(_urls[i].getFile());
            if (file.isDirectory()){
                
                logger.msg("Parsing directory: " + file.getPath());
                long lStart = System.currentTimeMillis();
				_iClassCount += parseDirectory(file, _mapClassNameToClassList);
                //logger.msg("time (ms)", System.currentTimeMillis()-lStart);
			}else if (isJarFile(file)){
				logger.msg("Parsing jar file: " + file.getPath());
				try{
                    long lStart = System.currentTimeMillis();
                    // we use a cache mgr for jar files
                    _iClassCount += JarFileMgr.getInstance().parseJarFile(file, _mapClassNameToClassList);
					//parseZipFile(new ZipFile(file), _mapClassNameToClassList);
                    //logger.msg("time (ms)", System.currentTimeMillis()-lStart);
				}catch(IOException ze){
                    logger.error("Error parsing jar file : " + file.getPath(), ze);
				}
			}else{
				logger.msg("Skipping item: " + file.getPath());
			}
		}
	}
	
    
	static boolean isJarFile(File argFile){
		String strFileName = argFile.getName().toLowerCase();
		return strFileName.endsWith(".jar") || strFileName.endsWith(".zip");
	}
	public Set getClassListForClassName(String argClassName){
        Set setResult = null;
		Set setClasses = (Set) _mapClassNameToClassList.get(argClassName);
        Set setBootClasses = (Set) __mapBootCP.get(argClassName);
        if (setClasses == null && setBootClasses != null){
            setResult = setBootClasses;
        }else if (setClasses != null && setBootClasses == null){
            setResult = setClasses;
        }else if (setClasses != null && setBootClasses != null) {
            setClasses.addAll(setBootClasses);
            setResult = setClasses;
        }
        
        if (setResult != null){
            Set setTemp = new TreeSet();
            ClassInfo ci = null;
            for (Iterator it = setResult.iterator(); it.hasNext();){
                ci = (ClassInfo) it.next();
                setTemp.add(ci.getFullClassName());
            }
            setResult = setTemp;
        }
		return setResult;
	}
    
    public List getAllClassInfos(){ return getClassInfoList(_mapClassNameToClassList); }
    
    static List getClassInfoList(Map argMap){
        List list = new ArrayList();
		Set setFullClassNames = null;
		Set setClasses = new TreeSet(argMap.keySet());
		String strClassName = null;
        for (Iterator iter = setClasses.iterator(); iter.hasNext(); ){
            strClassName = (String) iter.next();
            setFullClassNames = (Set) argMap.get(strClassName);
			for (Iterator it = setFullClassNames.iterator(); it.hasNext(); ){
                list.add(it.next());
			}
		}
        return list;
    }
	public String listContents(){
		return listContents(_mapClassNameToClassList);
	}
	
	public static String listContents(Map argMap){
		String NL = "\n", TAB = "\t";
		StringBuffer sb = new StringBuffer("[" + argMap.size() + " items]" + NL);
		
		Set setFullClassNames = null;
		Set setClasses = new TreeSet(argMap.keySet());
		String strClassName = null;
		for (Iterator iter = setClasses.iterator(); iter.hasNext(); ){
			strClassName = (String) iter.next();
			setFullClassNames = (Set) argMap.get(strClassName);
			sb.append(strClassName).append(NL);
			for (Iterator it = setFullClassNames.iterator(); it.hasNext(); ){
				sb.append(TAB).append(it.next().toString()).append(NL);
			}
		}
		return sb.toString();
		
	}
	public String toString(){
		String NL = "\n", TAB = "\t";
        StringBuffer sb = new StringBuffer("[" + super.toString() + "] - ");
        sb.append(getAllClassInfos().size()).append(" classes").append(NL);
		sb.append(TAB).append("path=").append(_strPath).append(NL);
		sb.append(TAB).append("urls=").append(NL);
		if (_urls!=null){
			for (int i=0; i < _urls.length; i++){
				sb.append(TAB).append(TAB).append(_urls[i].toString()).append(NL);
			}
		}
		return sb.toString();
	}
	
    public boolean equals(Object o){
        if (this == o)
            return true;
        if (!(o instanceof ClassNameCache)){
            return false;
        }
        ClassNameCache cnc = (ClassNameCache) o;
        return (cnc._iClassCount == this._iClassCount &&
                cnc._strPath.equals(this._strPath) && 
                cnc._mapClassNameToClassList.keySet().equals(
                    this._mapClassNameToClassList.keySet())
                );
    }
    public int hashCode(){
        return _strPath.hashCode();
    }
	/**
	* Convert the path to an array of <code>URL</code>s
     * @param argPath a <code>String</code> value
     * @return an <code>URL[]</code> value
     */
	 public static URL[] pathToURLs(String argPath) {
        try{
            ArrayList alURLs = new ArrayList();
            String strSep = System.getProperty("path.separator");
            File file = null;
            for (StringTokenizer st = new StringTokenizer(argPath,strSep);
                 st.hasMoreTokens(); ){
                file = new File(st.nextToken());
                if (file.exists()){
                    alURLs.add(file.toURL());
                }
                //alURLs.add(new URL("file:/" + st.nextToken()));
            }
            return (URL[]) alURLs.toArray(new URL[0]);
        }catch(MalformedURLException e){
            e.printStackTrace();
            return null;
        }
    }
	 
	 static String parseFullClassName(String argFileName){
		 argFileName = argFileName.replace('/', '.');
		 argFileName = argFileName.replace('\\', '.');
		 return argFileName.substring(0, argFileName.length() - 6);
	 }
	 
	 static String parseClassName(String argFullClassName){
		 int iDot = argFullClassName.lastIndexOf('.');
		 if (iDot == -1){
			 return argFullClassName;
		 }else{
			 return argFullClassName.substring(iDot + 1);
		 }
	 }
	 static int parseDirectory(File argDirectory, Map argMap){
         int iClassCount = 0;
		 List listFiles = new ArrayList();
		 JavaUtils.listAllFiles(argDirectory, new ClassFileFilter(), listFiles); //new ClassFileFilter(), listFiles);
		 String strFileName = null, strClassName = null, strFullClassName = null;
		 
		 int iDir = argDirectory.getPath().length();
		 Set setClassNames = null;
         ClassInfo ci = null;
         
		 for (Iterator iter = listFiles.iterator(); iter.hasNext(); ){
			 strFileName = ( (File)iter.next()).getPath();
			 strFileName = strFileName.substring(iDir + 1);
			 strFullClassName = parseFullClassName(strFileName);
			 strClassName = parseClassName(strFullClassName);
			 //System.out.println(strClassName + "->" + strFullClassName);
			 setClassNames = (Set) argMap.get(strClassName);
			 if (setClassNames == null){
				 setClassNames = new HashSet();
				 argMap.put(strClassName, setClassNames);
			 }
             ci = new ClassInfo(argDirectory, strFullClassName);
			 setClassNames.add(ci); //strFullClassName);
             iClassCount++;
		 }
         return iClassCount;
	 }
	 static int parseZipFile(File argFile, Map argMap) throws IOException, ZipException {
         
         int iClassCount = 0;
         ZipFile zipFile = null;
         try{
             zipFile = new ZipFile(argFile);
             ZipEntry zipEntry = null;
             String strFileName = null, strClassName = null, strFullClassName = null;
             Set setClassNames = null;
             ClassInfo ci = null;
             for(Enumeration enum = zipFile.entries(); enum.hasMoreElements(); ){
                 zipEntry = (ZipEntry) enum.nextElement();
                 strFileName = zipEntry.getName();
                 
                 // filter the elements
                 if (isClassFile(strFileName) ){
                     //&& !strFileName.startsWith("com/") && !strFileName.startsWith("sun/")){
                     strFullClassName = parseFullClassName(strFileName);
                     strClassName = parseClassName(strFullClassName);
                     //System.out.println(strClassName + "->" + strFullClassName);
                     setClassNames = (Set) argMap.get(strClassName);
                     if (setClassNames == null){
                         setClassNames = new HashSet();
                         argMap.put(strClassName, setClassNames);
                     }
                     ci = new ClassInfo(argFile, strFullClassName);
                     setClassNames.add(ci);
                     iClassCount++;
                 }
             }
         }finally{
             if (zipFile != null){
                 zipFile.close();
             }
         }
         return iClassCount;
	 }
	 
	 
	public static class ClassFileFilter implements FilenameFilter{
		public boolean accept(File argDir, String argName){
			return isClassFile(argName) || (new File(argDir, argName)).isDirectory();
		}
	}
    
    static final String RE_ANONYMOUS_INNER_CLASS = "\\$\\d+(\\.class|\\$)";

    public static boolean isAnonymousInnerClass(String argClassName)  {
        //try{
            //RE regexp = new RE(RE_ANONYMOUS_INNER_CLASS);
            
            //return regexp.getMatch(argClassName) != null;
            Pattern pattern = Pattern.compile(RE_ANONYMOUS_INNER_CLASS);
            Matcher m = pattern.matcher(argClassName);
            return m.matches();
        //}catch(REException e){
        //    logger.error("Error parsing classname: " + argClassName);
        //    //e.printStackTrace();
        //    return false;
        //}
    }
	 static boolean isClassFile(String argFileName){
		 return argFileName.toLowerCase().endsWith(".class") && argFileName.indexOf("$") == -1;
         //return argFileName.toLowerCase().endsWith(".class") && !isAnonymousInnerClass(argFileName);
	 }
     static void testAnonymousInner() throws Exception {
         if (1==2) throw new Exception();
         String[] strPositiveTests = {
             "ClassListDialog$1$EnterKeyListener.class",
             "ClassTreeViewer$1.class",
             "ConfigDialog$1$CancelButtonHandler.class",
             "ConfigDialog$2.class"
         };
         for (int i=0; i < strPositiveTests.length; i++){
             if (!isAnonymousInnerClass(strPositiveTests[i])){
                 System.out.println("[+] Error - " + strPositiveTests[i]);
             }
         }
         String[] strNegTests = {
             "ClassNameCache$ClassFileFilter.class",
             "ClassNameCache$ClassInfo.class",
             "ClassNameCache.class",
             "JavaUtils$ClassSelectionDialog$ListKeyAdapter.class",
             "JavaUtils$ClassSelectionDialog.class"
         };
         for (int i=0; i < strNegTests.length; i++){
             if (isAnonymousInnerClass(strNegTests[i])){
                 System.out.println("[-] Error - " + strNegTests[i]);
             }
         }
        
     }
	 static void test(){
         File argFile = new File("C:/home/work/src/build");
			String strTestPath = "C:\\home\\work\\src\\build;C:\\home\\work\\src\\classes;C:\\home\\work\\src\\classes\\activation.jar;C:\\home\\work\\src\\classes\\classes12.zip;C:\\home\\work\\src\\classes\\crimson.jar;C:\\home\\work\\src\\classes\\ecs.jar;C:\\home\\work\\src\\classes\\HTTPClient.jar;C:\\home\\work\\src\\classes\\javax.servlet.jar;C:\\home\\work\\src\\classes\\jgen.jar;C:\\home\\work\\src\\classes\\jdom.jar;C:\\home\\work\\src\\classes\\jndi.jar;C:\\home\\work\\src\\classes\\junit.jar;C:\\home\\work\\src\\classes\\mail.jar;C:\\home\\work\\src\\classes\\gnu-regexp-1.1.4.jar;C:\\home\\work\\src\\classes\\sfc.jar;C:\\home\\work\\src\\classes\\symbeans.jar;C:\\home\\work\\src\\classes\\xaa_xmlc.jar;C:\\home\\work\\src\\classes\\xalan.jar;C:\\jdk131\\jre\\lib\\rt.jar;C:\\jdk131\\lib\\tools.jar;C:\\home\\work\\src\\classes\\jcert.jar;C:\\home\\work\\src\\classes\\jnet.jar;C:\\home\\work\\src\\classes\\jsse.jar;C:\\home\\work\\src\\classes\\jce1_2_1.jar;C:\\home\\work\\src\\classes\\local_policy.jar;C:\\home\\work\\src\\classes\\sunjce_provider.jar;C:\\home\\work\\src\\classes\\US_export_policy.jar";
			//;C\:\\home\\work\\src\\classes;C\:\\home\\work\\src\\classes\\activation.jar;C\:\\home\\work\\src\\classes\\classes12.zip;C\:\\home\\work\\src\\classes\\crimson.jar;C\:\\home\\work\\src\\classes\\ecs.jar;C\:\\home\\work\\src\\classes\\HTTPClient.jar;C\:\\home\\work\\src\\classes\\javax.servlet.jar;C\:\\home\\work\\src\\classes\\jgen.jar;C\:\\home\\work\\src\\classes\\jdom.jar;C\:\\home\\work\\src\\classes\\jndi.jar;C\:\\home\\work\\src\\classes\\junit.jar;C\:\\home\\work\\src\\classes\\mail.jar;C\:\\home\\work\\src\\classes\\gnu-regexp-1.1.4.jar;C\:\\home\\work\\src\\classes\\sfc.jar;C\:\\home\\work\\src\\classes\\symbeans.jar;C\:\\home\\work\\src\\classes\\xaa_xmlc.jar;C\:\\home\\work\\src\\classes\\xalan.jar;C\:\\jdk131\\jre\\lib\\rt.jar;C\:\\jdk131\\lib\\tools.jar;C\:\\home\\work\\src\\classes\\jcert.jar;C\:\\home\\work\\src\\classes\\jnet.jar;C\:\\home\\work\\src\\classes\\jsse.jar;C\:\\home\\work\\src\\classes\\jce1_2_1.jar;C\:\\home\\work\\src\\classes\\local_policy.jar;C\:\\home\\work\\src\\classes\\sunjce_provider.jar;C\:\\home\\work\\src\\classes\\US_export_policy.jar"; 
			/*
			"C:\\home\\work\\src\\build;C:\\home\\work\\src\\classes;C:\\home\\work\\src\\classes\\activation.jar;C:\\home\\work\\src\\classes\\classes12.zip;C:\\home\\work\\src\\classes\\crimson.jar;C:\\home\\work\\src\\classes\\ecs.jar;C:\\home\\work\\src\\classes\\HTTPClient.jar;C:\\home\\work\\src\\classes\\javax.servlet.jar;C:\\home\\work\\src\\classes\\jgen.jar;C:\\home\\work\\src\\classes\\jdom.jar;C:\\home\\work\\src\\classes\\jndi.jar;C:\\home\\work\\src\\classes\\junit.jar;C:\\home\\work\\src\\classes\\mail.jar;C:\\home\\work\\src\\classes\\gnu-regexp-1.1.4.jar;C:\\home\\work\\src\\classes\\sfc.jar;C:\\home\\work\\src\\classes\\symbeans.jar;C:\\home\\work\\src\\classes\\xaa_xmlc.jar;C:\\home\\work\\src\\classes\\xalan.jar;C:\\jdk131\\jre\\lib\\rt.jar;C:\\jdk131\\lib\\tools.jar;C:\\home\\work\\src\\classes\\jcert.jar;C:\\home\\work\\src\\classes\\jnet.jar;C:\\home\\work\\src\\classes\\jsse.jar;C:\\home\\work\\src\\classes\\jce1_2_1.jar;C:\\home\\work\\src\\classes\\local_policy.jar;C:\\home\\work\\src\\classes\\sunjce_provider.jar;C:\\home\\work\\src\\classes\\US_export_policy.jar";
			*/
            strTestPath = System.getProperty("sun.boot.class.path");
            long longStart, longEnd, longStartAll = System.currentTimeMillis();
            int iIter = 10;
            ClassNameCache cnc = null;
            for (int i=0, l = iIter; i < l; i++){
                longStart = System.currentTimeMillis();
                cnc = new ClassNameCache(strTestPath);
                longEnd = System.currentTimeMillis();
                logger.msg("[" + i + "] time(ms)=",""+(longEnd - longStart));
            }
            logger.msg("Size", cnc.getAllClassInfos().size());
            logger.msg("[AVE] time(ms)=", ""+(System.currentTimeMillis() - longStartAll) / (double)iIter);
			//System.out.println(strTestPath);
            //System.out.println(cnc.listContents());
	 }
	public static void main(String[] args){
		try{
            test();
            
            //testAnonymousInner();
            for (Iterator it = new HashMap().keySet().iterator(); it.hasNext();){
            }
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

}
