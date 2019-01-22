/*
 *  ClassTable.java - part of the CodeAid plugin.
 *  Copyright (C) 1999 Jason Ginchereau
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.acm.seguin.completer.info;

// Collections API
import org.acm.seguin.completer.*;
import java.util.StringTokenizer;
//import anthelper.ClassNameCache;
import org.acm.seguin.ide.jedit.Navigator.NavigatorLogger;
import org.acm.seguin.completer.Completer;
import gnu.regexp.RE;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Maps class names to information that is known about those classes.
 * Information about each class is stored in a <code>ClassInfo</code> object.
 * All methods are thread-safe.
 *
 * @author    Jason Ginchereau
 * @created   December 12, 2002
 * @see       ClassInfo
 */
public final class ClassTable implements java.io.Serializable {
    /**
     * Description of the Field
     */
    public final static String UNNAMED_PACKAGE = "<unnamed package>";

    private static Map staticInstanceTable = new HashMap();
    private final static Object DEFAULT_KEY = new String();

    final static NavigatorLogger logger = Completer.getLogger(ClassTable.class);
    final static boolean DEBUG = true;

    /**
     * Gets a static instance of a <code>ClassTable</code> that can be shared by
     * everyone with the same key.
     *
     * @param key  Description of the Parameter
     * @return     The instance value
     */
    private static synchronized ClassTable getInstance(Object key) {
        ClassTable instance = (ClassTable) staticInstanceTable.get(key);
        if (instance == null) {
            instance = new ClassTable();
            staticInstanceTable.put(key, instance);
        }
        return instance;
    }

    /**
     * Gets the existingInstance attribute of the ClassTable class
     *
     * @param argSource  the class path src
     * @return        The existingInstance value
     */
    public static synchronized ClassTable getExistingInstance(ClassPathSrc argSource) {
        return (ClassTable) staticInstanceTable.get(argSource.getKey());
    }

    /**
     * Remove the instance
     *
     * @param argSource  the class path src
     * @return        The existingInstance value
     */
    public static synchronized ClassTable removeInstance(ClassPathSrc argSource) {
        ClassTable ct = (ClassTable) staticInstanceTable.remove(argSource.getKey());
        //printAll();
        return ct;
    }
    
    /**
     * Gets a static instance of a <code>ClassTable</code> that can be shared by
     * everyone with the same key.
     *
     * @param argSource  the class path src
     * @return           The instance value
     */
    public static synchronized ClassTable getInstance(ClassPathSrc argSource) {
        ClassTable instance = (ClassTable) staticInstanceTable.get(argSource.getKey());
        if (instance == null) {
            ClassNameCache cnc = argSource.getCNC();
            // this will be a very verbose message...but helpful
            //logger.debug("** Creating ClassTable for ClassNameCache: ", cnc.toString());
            instance = new ClassTable(getInstance(), cnc.getAllClassInfos(), createClassLoader(cnc));
            staticInstanceTable.put(argSource.getKey(), instance);
        }
        //printAll();
        return instance;
    }

    static void printAll() {
        Object objKey;
        Object objVal;
        ClassNameCache cnc = null;
        for (Iterator it = staticInstanceTable.keySet().iterator(); it.hasNext(); ) {
            objKey = it.next();
            objVal = staticInstanceTable.get(objKey);
            if (objKey instanceof ClassNameCache) {
                cnc = (ClassNameCache) objKey;
                logger.debug("cnc=" + cnc.getName(), "ct=" + objVal.hashCode());
            } else {
                logger.debug("cnc=" + objKey.toString(), "ct=" + objVal.hashCode());
            }

        }
    }

    private static ClassLoader createClassLoader(ClassNameCache argCNC) {
        // 1 arg cons will make system class loader parent
        // which do we want?
        URLClassLoader classLoader = new URLClassLoader(
            argCNC.getClassPathAsURLs(),
            Completer.class.getClassLoader()
            );
        return classLoader;
    }

    /**
     * Gets a static instance of a <code>ClassTable</code> using the default
     * key.
     *
     * @return   The instance value
     */
    public static ClassTable getInstance() {
        return getInstance(DEFAULT_KEY);
    }


    private transient ClassTable parent;
    private transient SortedMap arrays;
    private SortedMap packages;
    private SortedMap classes;
    private ClassLoader _classLoader = null;


    /**
     * Creates a new <code>ClassTable</code>.
     */
    public ClassTable() {
        // by default, use
        this(null,
            ClassNameCache.getBootClassList(),
            Completer.class.getClassLoader());

    }


    /**
     * Creates a new <code>ClassTable</code> which will delegate to a parent
     * <code>ClassTable</code> for unrecognized classes.
     *
     * @param argParent             Description of the Parameter
     * @param argListCNCClassInfos  Description of the Parameter
     * @param argClassLoader        Description of the Parameter
     */

    /**
     * Constructor for the ClassTable object
     *
     * @param argParent             Description of the Parameter
     * @param argListCNCClassInfos  Description of the Parameter
     * @param argClassLoader        Description of the Parameter
     */
    public ClassTable(ClassTable argParent,
                      List argListCNCClassInfos,
                      ClassLoader argClassLoader) {
        this.parent = argParent;
        arrays = new TreeMap(new StringComparator());
        packages = new TreeMap(new StringComparator());
        classes = new TreeMap(new StringComparator());
        // load all classes
        init(argListCNCClassInfos, argClassLoader);
    }

    /**
     * Description of the Method
     *
     * @param argCNC  Description of the Parameter
     */
    public synchronized void reinit(ClassPathSrc argSource) {
        ClassNameCache cnc = argSource.getCNC();
        List listAll = cnc.getAllClassInfos();
        logger.msg("reinit", listAll.size());
        arrays.clear();
        packages.clear();
        classes.clear();
        init(listAll, createClassLoader(cnc));
    }

    private void init(List argListCNCClassInfos,
                      ClassLoader argClassLoader) {
        preLoad(argListCNCClassInfos);
        _classLoader = argClassLoader;

    }

    /**
     * Gets the parent <code>ClassTable</code>, which is be queried for classes
     * that are not recognized by this <code>ClassTable</code>.
     *
     * @return   The parent value
     */
    public ClassTable getParent() {
        return parent;
    }


    /**
     * Sets the parent <code>ClassTable</code>, which will be queried for
     * classes that are not recognized by this <code>ClassTable</code>.
     *
     * @param parent  The new parent value
     */
    public synchronized void setParent(ClassTable parent) {
        this.parent = parent;
    }


    /**
     * Gets a list of all packages in the database.
     *
     * @return   a sorted <code>List</code> of <code>Strings</code>, where each
     *      <code>String</code> is a name of a package in the database. This
     *      <code>List</code> may be safely modified by the user, as it is no
     *      longer connected to the database after it is returned.
     */
    public synchronized List getPackages() {
        return new LinkedList(packages.keySet());
    }


    /**
     * Gets a list of all known classes in a package.
     *
     * @param packageName  the name of the package, using the java . notation.
     * @return             a sorted <code>List</code> of <code>String</code>
     *      names, or <code>null</code> if the package is unknown. This <code>List</code>
     *      may be safely modified by the user, as it is no longer connected to
     *      the database after it is returned.
     */
    public synchronized List getClassesInPackage(String packageName) {
        Map pmap = (Map) packages.get(packageName);
        if (pmap != null) {
            return new LinkedList(pmap.keySet());
        } else if (parent != null) {
            return parent.getClassesInPackage(packageName);
        } else {
            return null;
        }
    }

    /**
     * Returns a list of all classes in the given package. Items in this list
     * may not have been fully loaded into the class table yet. The list
     * contains _full_ class names
     *
     * @param argPackage  The package name (java.util) or (java.util.*)
     * @return            The allClassesInPackage value
     */
    public synchronized List getAllClassesInPackage(String argPackage) {
        List listClasses = new ArrayList();
        String strPkg = argPackage;
        if (strPkg.endsWith(".*")) {
            strPkg = strPkg.substring(0, strPkg.length() - 2);
        }
        String strFullName;
        for (Iterator it = classes.keySet().iterator(); it.hasNext(); ) {
            strFullName = it.next().toString();
            if (strFullName.startsWith(strPkg) &&
                strFullName.indexOf(".", strPkg.length() + 1) == -1) {
                listClasses.add(strFullName);
            }
        }
        if (parent != null) {
            listClasses.addAll(parent.getAllClassesInPackage(strPkg));
        }
        return listClasses;
    }

    int _iLastClassCount = -1;
    Set _setClassNames = null;
    String _strExclusionRE = null;

    /**
     * Returns a set of all class names in this table except classes whose full
     * names match the given regexp.
     *
     * @param argExclusionRE  a regexp
     * @return                The allClassNames valuere
     */
    public synchronized Set getAllClassNames(String argExclusionRE) {

        if (_setClassNames == null ||
            _setClassNames.size() != _iLastClassCount ||
            !argExclusionRE.equals(_strExclusionRE)) {

            RE reExclude = null;
            try {
                reExclude = new RE(argExclusionRE);
            } catch (Exception e) {
                logger.error("Error filtering on: " + argExclusionRE, e);
            }
            _setClassNames = new HashSet();

            logger.msg("Creating new class list with RE", argExclusionRE);
            if (reExclude == null) {
                // reexp failed...return all
                for (Iterator it = classes.keySet().iterator(); it.hasNext(); ) {
                    _setClassNames.add(it.next().toString());
                }
            } else {
                String strCN;
                for (Iterator it = classes.keySet().iterator(); it.hasNext(); ) {
                    strCN = it.next().toString();
                    if (!reExclude.isMatch(strCN)) {
                        _setClassNames.add(strCN);
                    }
                }

            }
            if (parent != null) {
                // get parent's classes
                _setClassNames.addAll(this.parent.getAllClassNames(argExclusionRE));
            }
            _iLastClassCount = _setClassNames.size();
            _strExclusionRE = argExclusionRE;
        }
        return _setClassNames;
    }

    /**
     * The main program for the ClassTable class
     *
     * @param args  The command line arguments
     */
    public static void main(String[] args) {
        try {

            ClassTable ct = new ClassTable();
            //ClassNameCache cnc = new ClassNameCache(System.getProperty("sun.boot.class.path"));

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    static void oldTest() {
        try {
            /*
             *  ClassTable ct = new ClassTable();
             *  List list = ct.getAllClassesInPackage("java.util.*");
             *  for (Iterator it = list.iterator(); it.hasNext(); ) {
             *  logger.msg(it.next().toString());
             *  }
             */
            ClassTable ct = new ClassTable();
            Set setClasses = null;
            for (int i = 0; i < 1; i++) {
                long lStart = System.currentTimeMillis();
                setClasses = ct.getAllClassNames("^sun\\..*|^COM\\.rsa\\..*|^com\\.sun\\..*|^org\\.omg\\.CORBA\\..*");
                logger.msg("time(ms)", ""+(System.currentTimeMillis() - lStart));
            }
            int iMax = 0;
            String str = null;
            ArrayList listMaxes = new ArrayList();
            for (Iterator it = setClasses.iterator(); it.hasNext(); ) {
                str = it.next().toString();
                //if (str.length() > iMax){
                if (str.length() > 40) {
                    iMax = str.length();
                    listMaxes.add(str);
                }
            }
            logger.msg("max size", iMax);
            String strCN = null;
            String strPkg = null;
            String strFN = null;
            for (Iterator it = listMaxes.iterator(); it.hasNext(); ) {
                strFN = it.next().toString();
                strPkg = strFN.substring(0, strFN.lastIndexOf(".") + 1);
                if (strPkg.endsWith(".")) {
                    //strPkg = strPkg.substring(0, strPkg.length() - 1);
                }
                StringTokenizer st = new StringTokenizer(strPkg, ".");
                int iN = st.countTokens();
                if (iN <= 2) {
                    // skip stuff liek java.util.Vector
                } else {
                    // remove parts, 3-> N-1
                    StringBuffer sbPkg = new StringBuffer();
                    sbPkg.append(st.nextToken()).append(".");
                    sbPkg.append(st.nextToken()).append(".");
                    for (; st.hasMoreTokens(); st.nextToken()) {
                        if (st.hasMoreTokens()) {
                            sbPkg.append("~");
                        } else {
                            sbPkg.append(".");
                        }
                    }
                    strPkg = sbPkg.toString();
                }
                strCN = strFN.substring(strFN.lastIndexOf(".") + 1);
                logger.msg(strPkg + strCN);
            }
            int iSize = setClasses.size();
            logger.msg("size", iSize);
            int i = 0;
            /*
             *  for (Iterator it = setClasses.iterator();
             *  i < 10 && it.hasNext(); i++) {
             *  logger.msg(it.next().toString());
             *  }
             */
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Gets information about a class, given a class name.
     *
     * @param fullClassName  a fully-qualified class name. Note that for inner
     *      classes, the <code>$</code> notation is used in the class name.
     *      Example: <code>java.lang.Character$Subset</code>
     * @return               the <code>ClassInfo</code> object for the class, or
     *      <code>null</code> if the class is not in the database.
     */
    public synchronized ClassInfo get(String fullClassName) {
        if (fullClassName.endsWith("[]")) {
            ClassInfo aci = (ClassInfo) arrays.get(fullClassName);

            if (aci == null) {
                String originalName = fullClassName;
                int dimensions = 0;
                do {
                    fullClassName = fullClassName.substring(0, fullClassName.length() - 2);
                    dimensions++;
                } while (fullClassName.endsWith("[]"));

                ClassInfo ci = get(fullClassName);
                if (ci != null) {
                    aci = new ArrayClassInfo(ci, dimensions);
                    arrays.put(originalName, aci);
                }
            }
            return aci;
        } else {
            ClassInfo ci = getAndLazyLoad(fullClassName);
            if (ci == null && parent != null) {
                // try parent
                return parent.get(fullClassName);
            } else {
                return ci;
            }

            /*
             *  OLD code
             *  Object o = classes.get(fullClassName);
             *  /logger.msg("[" + fullClassName + "] o is a ", o == null ? "null" : o.getClass().getName());
             *  if (o instanceof ClassNameCache.ClassInfo) {
             *  ClassNameCache.ClassInfo ci = (ClassNameCache.ClassInfo) o;
             *  logger.debug("Loading class" + ci.getClassName());
             *  load(fullClassName);
             *  o = classes.get(fullClassName);
             *  if (o instanceof ClassNameCache.ClassInfo) {
             *  logger.debug("ClassInfo could not be created for (" + ci.getClassName() +
             *  ") found in (" + ci.getSrc() + ")");
             *  }
             *  }
             *  /logger.msg("now, o is a", o.getClass().getName());
             *  if (o != null && o instanceof ClassInfo) {
             *  return (ClassInfo) o;
             *  } else if (parent != null) {
             *  return parent.get(fullClassName);
             *  } else {
             *  return null;
             *  }
             */
        }
    }

    /**
     * Attempts to get the ClassInfo object for the given class name. This
     * method does the lazy loading that will convert a ClassNameCache.ClassInfo
     * into ClassInfo. If not ClassInfo can be loaded, null is returned.
     *
     * @param argFullClassName  Description of the Parameter
     * @return                  The andLazyLoad value
     */
    public ClassInfo getAndLazyLoad(String argFullClassName) {
        ClassInfo ciResult = null;
        Object o = classes.get(argFullClassName);
        if (o == null) {
            //logger.msg("Class !found, class (" + argFullClassName + "), parent (" + parent + ")");
        } else if (o instanceof ClassNameCache.ClassInfo) {
            //logger.msg("Lazy loading, class", argFullClassName);
            ClassNameCache.ClassInfo ci = (ClassNameCache.ClassInfo) o;

            load(argFullClassName);
            o = classes.get(argFullClassName);
            if (o instanceof ClassNameCache.ClassInfo) {
                if (Completer.DEBUG) {
                    logger.debug("## ClassInfo could not be created for (" + ci.getFullClassName() +
                        ") found in (" + ci.getSrc() + ")");
                }
            } else {
                ciResult = (ClassInfo) o;
            }
        } else {
            //if (o instanceof ClassInfo){g
            ciResult = (ClassInfo) o;
        }
        return ciResult;
    }

    /**
     * Enters class information into the database.
     *
     * @param classInfo  a <code>ClassInfo</code> object that information about
     *      a class to be put in the database. This object should not be
     *      modified after it is put in the database.
     */
    public synchronized void put(ClassInfo classInfo) {
        if (classInfo instanceof ArrayClassInfo) {
            throw new IllegalArgumentException("Can't put array classes in ClassTable.");
        }
        //logger.msg("putting", classInfo.getFullName());
        // Don't put inner classes in the package map.
        if (classInfo.getDeclaringClass() == null) {
            String packageName = classInfo.getPackage();
            if (packageName == null) {
                packageName = UNNAMED_PACKAGE;
            }
            Map pmap = (Map) packages.get(packageName);
            if (pmap == null) {
                packages.put(packageName, pmap = new TreeMap(new StringComparator()));
            }
            pmap.put(classInfo.getName(), classInfo);
        } else {
            //if (DEBUG)logger.msg("----INNER: ", classInfo.getFullName());
        }
        String strFN = classInfo.getFullName();
        classes.put(classInfo.getFullName(), classInfo);
    }


    /**
     * Removes a class from the database.
     *
     * @param fullClassName  a fully-qualified class name. Note that for inner
     *      classes, the <code>$</code> notation is used in the class name.
     * @return               the <code>ClassInfo</code> object that was removed
     *      from the database, or <code>null</code> if there was not one.
     */
    public synchronized ClassInfo remove(String fullClassName) {
        logger.msg("Removing", fullClassName);

        ClassInfo ci = (ClassInfo) classes.remove(fullClassName);
        if (ci != null) {
            String packageName = ci.getPackage();
            if (packageName == null) {
                packageName = UNNAMED_PACKAGE;
            }
            Map pmap = (Map) packages.get(packageName);
            if (pmap != null) {
                pmap.remove(ci.getName());
                if (pmap.isEmpty()) {
                    packages.remove(packageName);
                }
            }
        }
        return ci;
    }


    /**
     * Tests if a class is contained in the database.
     *
     * @param fullClassName  a fully-qualified class name. Note that for inner
     *      classes, the <code>$</code> notation is used in the class name.
     * @return               <code>true</code> if information about the class is
     *      in the database.
     */
    public synchronized boolean contains(String fullClassName) {
        return classes.containsKey(fullClassName);
    }


    /**
     * Gets the assignableTo attribute of the ClassTable object
     *
     * @param fromClass      Description of the Parameter
     * @param toClass        Description of the Parameter
     * @param defaultAnswer  Description of the Parameter
     * @return               The assignableTo value
     */
    public boolean isAssignableTo(String fromClass, String toClass,
                                  boolean defaultAnswer
                                  ) {
        ClassInfo fci = get(fromClass);
        ClassInfo tci = get(toClass);

        // return defaultAnswer when one or both classes are unrecognized
        if (fci == null || tci == null) {
            return defaultAnswer;
        }

        // a class is assignable to itself
        if (fci.equals(tci)) {
            return true;
        }

        // handling arrays
        if (fci instanceof ArrayClassInfo && tci instanceof ArrayClassInfo) {
            ArrayClassInfo afci = (ArrayClassInfo) fci;
            ArrayClassInfo atci = (ArrayClassInfo) tci;
            if (afci.getDimensions() != atci.getDimensions()) {
                return false;
            }
            return isAssignableTo(afci.getArrayType().getFullName(),
                atci.getArrayType().getFullName(), defaultAnswer);
        }

        // a class is assignable if its superclass is assignable
        if (fci.getSuperclass() != null &&
            isAssignableTo(fci.getSuperclass(), toClass, defaultAnswer)
            ) {
            return true;
        }

        // a class is assignable if any of its interfaces are assignable
        String[] ifs = fci.getInterfaces();
        for (int i = 0; i < ifs.length; i++) {
            if (isAssignableTo(ifs[i], toClass, defaultAnswer)) {
                return true;
            }
        }

        return false;
    }


    /**
     * Gets the accessAllowed attribute of the ClassTable object
     *
     * @param in             Description of the Parameter
     * @param target         Description of the Parameter
     * @param requireStatic  Description of the Parameter
     * @param defaultAnswer  Description of the Parameter
     * @return               The accessAllowed value
     */
    public boolean isAccessAllowed(ClassInfo in, MemberInfo target,
                                   boolean requireStatic, boolean defaultAnswer
                                   ) {
        int mods = target.getModifiers();
        if (requireStatic && (mods & Modifier.STATIC) == 0) {
            return false;
        }

        String dc = target.getDeclaringClass();
        ClassInfo dci = (dc != null ? (ClassInfo) get(dc) : null);
        if (dci != null) {
            if (!isAccessAllowed(in, dci, false, defaultAnswer)) {
                return false;
            }
        } else if (dc != null) {
            if (!defaultAnswer) {
                return false;
            }
        }

        if (target instanceof ClassInfo && dc == null) {
            // top-level class
            if ((mods & Modifier.PUBLIC) != 0) {
                return true;
            } else {
                String ip = (in != null ? in.getPackage() : null);
                String cp = ((ClassInfo) target).getPackage();
                if ((ip == null && cp == null) ||
                    (ip != null && cp != null && ip.equals(cp))
                    ) {
                    return true;
                }
            }
            return false;
        }

        if ((mods & Modifier.PUBLIC) != 0) {
            // public access
            return true;
        }

        if (in != null && dci != null) {
            String cn = dci.getFullName();
            String cp = dci.getPackage();
            String icn = in.getFullName();
            String icp = in.getPackage();

            if ((mods & Modifier.PROTECTED) != 0) {
                // protected access
                if (isAssignableTo(icn, cn, false) ||
                    (cp == null && icp == null) ||
                    (cp != null && icp != null && cp.equals(icp))
                    ) {
                    return true;
                }
            } else if ((mods & Modifier.PRIVATE) == 0) {
                // package access
                if ((cp == null && icp == null) ||
                    (cp != null && icp != null && cp.equals(icp))
                    ) {
                    return true;
                }
            } else {
                // private access
                if (cn.equals(icn)) {
                    return true;
                }
            }

            // Allow inner classes to access anything that their declaring
            // classes can access.. is this correct?
            String idc = in.getDeclaringClass();
            if (idc != null) {
                ClassInfo idci = (ClassInfo) get(idc);
                if (idci != null) {
                    return isAccessAllowed(idci, target, requireStatic, defaultAnswer);
                }
            }
        }

        return defaultAnswer;
    }


    /**
     * Gets the size of the database.
     *
     * @return   the number of classes in the database.
     */
    public synchronized int size() {
        return classes.size();
    }

    private void load(String name) {

        ClassNameCache.ClassInfo cncCI = (ClassNameCache.ClassInfo) classes.get(name);
        Class cls = null;

        // 1st try the cnc class loader
        try {
            cls = _classLoader.loadClass(cncCI.getFullClassName());
        } catch (Exception ex) {
            logger.msg("Could not load class via cnc class loader: " +
                ex.toString());

        }

        // 2nd, try the parent
        if (cls == null && parent != null) {
            logger.msg("Calling parent.load");
            parent.load(name);
            return;
        }

        if (cls != null) {
            Class clsDec = null;
            try {
                clsDec = cls.getDeclaringClass();
            } catch (IllegalAccessError err) {
                logger.warning("Encountered IllegalAccessError getting declaring class for: (" + name + ")");
            }
            ClassInfo ci = new ClassInfo(cls, clsDec);
            put(ci);
            // put all inner classes
            Set setInnerClasses = ci.getClasses();
            for (Iterator it = setInnerClasses.iterator(); it.hasNext(); ) {
                put((ClassInfo) it.next());
            }

            //if (DEBUG)logger.msg("*********LOAD success", ci.getFullName());
        }
    }

    /**
     * Loads <code>ClassInfo</code> entry information out of a <code>ClassNameCache</code>
     * . The entries themselves will not actually be loaded into memory until
     * they are needed.
     *
     * @param argListCNCClassInfos  Description of the Parameter
     */
    public synchronized void preLoad(List argListCNCClassInfos) {
        List listAll = argListCNCClassInfos;
        String name;
        String packageName;
        int i = 0;
        Map pmap;
        ClassNameCache.ClassInfo ci = null;
        for (Iterator it = listAll.iterator(); it.hasNext(); ) {
            ci = (ClassNameCache.ClassInfo) it.next();
            name = ci.getFullClassName();
            packageName = UNNAMED_PACKAGE;
            i = name.lastIndexOf('.');
            if (i >= 0) {
                packageName = name.substring(0, i);
            }
            pmap = (Map) packages.get(packageName);
            if (pmap == null) {
                packages.put(packageName, pmap = new TreeMap(new StringComparator()));
            }
            classes.put(name, ci);
        }
    }

}

