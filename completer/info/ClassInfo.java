/*
 *  ClassInfo.java - part of the CodeAid plugin.
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

import org.acm.seguin.ide.jedit.Navigator;
//import anthelper.ClassNameCache;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

// Collections API
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Contains information about a class such as super classes, inner classes,
 * fields, and methods.
 *
 * @author    Jason Ginchereau
 * @created   December 11, 2002
 * @see       ClassInfoDatabase
 */
public class ClassInfo extends MemberInfo {
    static final Navigator.NavigatorLogger logger = Navigator.getLogger(ClassInfo.class);
    private boolean isInterface;
    private String packageName;
    private String superclass;
    private String[] interfaces;

    private SortedSet classes;
    private SortedSet fields;
    private SortedSet constructors;
    private SortedSet methods;

    public ClassInfo(Class c){
        this(c, c.getDeclaringClass());
    }
    /**
     * Creates a new <code>ClassInfo</code> structure using reflection to
     * determine information about superclasses, inner classes, fields, and
     * methods.
     *
     * @param c  Description of the Parameter
     */
    public ClassInfo(Class c, Class argDeclaringClass) {
        super((argDeclaringClass != null ?
            argDeclaringClass.getName() : null),
            c.getModifiers(), getSimpleName(c.getName()), null);
        
        this.isInterface = c.isInterface();
        
        if (isInterface) {
            superclass = "java.lang.Object";
        } else {
            superclass = (c.getSuperclass() != null ?
                c.getSuperclass().getName() : null);
        }

        Class[] ifs = c.getInterfaces();
        interfaces = new String[ifs.length];
        for (int i = 0; i < interfaces.length; i++) {
            interfaces[i] = ifs[i].getName();
        }

        
        //this.packageName = (c.getPackage() != null? c.getPackage().getName(): null);
        //logger.msg("pkg.name", this.packageName);
        
        int dot = c.getName().lastIndexOf('.');
        if (dot < 0) {
            this.packageName = null;
        } else {
            this.packageName = c.getName().substring(0, dot); //c.getName().length());
        }

        initSets();

        Class[] innerClasses = c.getDeclaredClasses();
        for (int i = 0; i < innerClasses.length; i++) {
            this.classes.add(new ClassInfo(innerClasses[i]));
        }
        try{
            Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                this.fields.add(new FieldInfo(fields[i]));
            }
        }catch(NoClassDefFoundError e){
           logger.warning(e.toString());
        }
        
        Constructor[] constructors = c.getDeclaredConstructors();
        for (int i = 0; i < constructors.length; i++) {
            this.constructors.add(new ConstructorInfo(constructors[i]));
        }
        try{
            Method[] methods = c.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                this.methods.add(new MethodInfo(methods[i]));
            }
        }catch(NoClassDefFoundError e){
            logger.warning(e.toString());
        }
    }


    /**
     * Creates a new <code>ClassInfo</code> structure using all specified
     * information.
     *
     * @param declaringClass  Description of the Parameter
     * @param modifiers       Description of the Parameter
     * @param isInterface     Description of the Parameter
     * @param name            Description of the Parameter
     * @param superclass      Description of the Parameter
     * @param interfaces      Description of the Parameter
     * @param packageName     Description of the Parameter
     * @param comment         Description of the Parameter
     */
    public ClassInfo(String declaringClass,
                     int modifiers, boolean isInterface, String name,
                     String superclass, String[] interfaces,
                     String packageName, String comment
                     ) {
        super(declaringClass, modifiers, name, comment);
        this.isInterface = isInterface;
        if (isInterface && superclass == null) {
            superclass = "java.lang.Object";
        }
        if (interfaces == null) {
            interfaces = new String[0];
        }
        this.superclass = superclass;
        this.interfaces = interfaces;
        this.packageName = packageName;

        initSets();
    }


    /**
     * Creates an empty <code>ClassInfo</code> structure that is a placeholder
     * for when nothing about a class is yet known.
     *
     * @param name         Description of the Parameter
     * @param packageName  Description of the Parameter
     */
    public ClassInfo(String name, String packageName) {
        super(null, Modifier.PUBLIC, name, null);
        this.isInterface = false;
        if ((packageName + "." + name).equals("java.lang.Object")) {
            this.superclass = null;
        } else {
            this.superclass = "java.lang.Object";
        }
        this.interfaces = new String[0];
        this.packageName = packageName;
        initSets();
    }


    static String getSimpleName(String className) {
        int i = className.lastIndexOf('$');
        int j = className.lastIndexOf('.');
        return className.substring(Math.max(i, j) + 1);
    }


    private void initSets() {
        classes = new TreeSet(new StringComparator());
        fields = new TreeSet();
        constructors = new TreeSet();
        methods = new TreeSet();
        //new 
    }


    /**
     * Adds the class, field, constructor, or method info to the class.
     *
     * @param mi  Description of the Parameter
     */
    public void add(MemberInfo mi) {
        if (mi.getDeclaringClass() == null ||
            !mi.getDeclaringClass().equals(getFullName())
            ) {
            throw new IllegalArgumentException(
                "Cannot add a member to a class that is not its declaring class.");
        }

        if (mi instanceof ClassInfo) {
            classes.add(((ClassInfo) mi).getName());
        } else if (mi instanceof FieldInfo) {
            fields.add(mi);
        } else if (mi instanceof ConstructorInfo) {
            constructors.add(mi);
        } else if (mi instanceof MethodInfo) {
            methods.add(mi);
        } else {
            throw new IllegalArgumentException("Unknown MemberInfo type.");
        }
    }


    /**
     * Gets the type attribute of the ClassInfo object
     *
     * @return   The type value
     */
    public String getType() {
        return getFullName();
    }


    /**
     * Gets the fully-qualified name of the class.
     *
     * @return   The fullName value
     */
    public String getFullName() {
        if (getDeclaringClass() != null) {
            return getDeclaringClass() + '$' + getName();
        } else if (getPackage() != null) {
            return getPackage() + '.' + getName();
        } else {
            return getName();
        }
    }


    /**
     * Gets the interface attribute of the ClassInfo object
     *
     * @return   The interface value
     */
    public boolean isInterface() {
        return isInterface;
    }


    /**
     * Gets the line attribute of the ClassInfo object
     *
     * @return   The line value
     */
    public String getLine() {
        return modifiersToString(getModifiers()) +
            (isInterface ? "interface " : "class ") + getName();
    }


    /**
     * Gets the superclass of this class. If this class is an interface, the
     * returned classes will be <code>Object</code>. If this class is <code>Object</code>
     * , <code>null</code> will be returned.
     *
     * @return   The superclass value
     */
    public String getSuperclass() {
        return superclass;
    }


    /**
     * Gets the set of interfaces which this class implements. If this class is
     * an interface, the returned array will be the set of super-interfaces
     * extended by this interface. If this class is implements no interfaces, an
     * array of length 0 will be returned.
     *
     * @return   The interfaces value
     */
    public String[] getInterfaces() {
        return interfaces;
    }


    /**
     * Gets the name of the package this class is a part of, or <code>null</code>
     * if this class is part of the unnamed package.
     *
     * @return   The package value
     */
    public String getPackage() {
        return packageName;
    }


    /**
     * Gets the set of inner classes that are declared by this class. Note that
     * inner classes declared by a superclass are not included in this set.
     *
     * @return   a <code>SortedSet</code> of <code>Strings</code> that are the
     *      simple names of inner classes of this class. This set should not be
     *      modified directly.
     */
    public SortedSet getClasses() {
        return classes;
    }


    /**
     * Gets the set of fields that are declared by this class. Note that fields
     * declared by a superclass are not included in this set.
     *
     * @return   a <code>SortedSet</code> of <code>FieldInfo</code> objects.
     *      This set should not be modified directly.
     */
    public SortedSet getFields() {
        return fields;
    }


    /**
     * Gets the set of constructors that are declared by this class.
     *
     * @return   a <code>SortedSet</code> of <code>ConstructorInfo</code>
     *      objects. This set should not be modified directly.
     */
    public SortedSet getConstructors() {
        return constructors;
    }


    /**
     * Gets the set of methods that are declared by this class. Note that
     * methods declared by a superclass are not included in this set.
     *
     * @return   a <code>SortedSet</code> of <code>MethodInfo</code> objects.
     *      This set should not be modified directly.
     */
    public SortedSet getMethods() {
        return methods;
    }


    /**
     * Description of the Method
     *
     * @param mi  Description of the Parameter
     * @return    Description of the Return Value
     */
    public int compareTo(MemberInfo mi) {
        int nc = getName().compareTo(mi.getName());
        if (nc != 0) {
            return nc;
        }

        if (mi instanceof ArrayClassInfo) {
            return -1;
        } else if (mi instanceof ClassInfo) {
            return 0;
        } else {
            return -1;
        }
    }
    
    public static void main(String[] args){
        try{
            String name = "ClassNameCache$ClassInfo";
            String strClassName = name.substring(0, name.indexOf("$"));
            logger.msg(strClassName);
            if (1==1) System.exit(0);
            Class cls =  org.gjt.sp.jedit.gui.DockableWindowManager.class;
            Class[] classes = cls.getDeclaredClasses();
            for (int i=0, l = classes.length; i < l; i++){
                logger.msg(classes[i].getName());
                ClassInfo ci = new ClassInfo(classes[i]);
            }
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
    
    /*
     *  *
     *  Gets the list of members that are accessible from the specified class.
     *
     *  @param accessFrom The class from which the members are being
     *  accessed from.  If this parameter is null, only
     *  public members are returned.
     *  @param staticOnly If true, only include static members in the list.
     *
     *  @return a sorted List of members (FieldInfos and MethodInfos).
     *  This list should not be modified by the caller.
     */
    /*
     *  public List getMemberList(ClassInfo accessFrom, boolean staticOnly) {
     *  if (memberCache == null) { memberCache = new HashMap(); }
     *  String key = getMemberListKey(accessFrom, staticOnly);
     *  List memberList = memberCache.get(key);
     *  if(memberList == null) {
     *  }
     *  return memberList;
     *  }
     *  private String getMemberListKey(ClassInfo accessFrom, boolean staticOnly) {
     *  return "" + (staticOnly? "!" : "")
     *  + (accessFrom != null? accessFrom.getFullName() : null);
     *  }
     */
}

