/*
 * MethodInfo.java - part of the CodeAid plugin.
 * Copyright (C) 1999 Jason Ginchereau
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


package org.acm.seguin.completer.info;

import java.lang.reflect.Method;


public final class MethodInfo extends MemberInfo {
    private String   returnType;
    private String[] parameterTypes;
    private String[] parameterNames;
    private String[] exceptionTypes;


    public MethodInfo(Method m) {
        super(m);
        returnType = m.getReturnType().getName();
        Class[] pt = m.getParameterTypes();
        parameterTypes = new String[pt.length];
        for (int i = 0; i < pt.length; i++) {
            parameterTypes[i] = pt[i].getName();
        }
        parameterNames = null;
        Class[] et = m.getExceptionTypes();
        exceptionTypes = new String[et.length];
        for (int i = 0; i < et.length; i++) {
            exceptionTypes[i] = et[i].getName();
        }
    }


    public MethodInfo(String declaringClass, int modifiers,
                      String returnType, String name,
                      String[] parameterTypes, String[] parameterNames,
                      String[] exceptionTypes, String comment) {
        super(declaringClass, modifiers, name, comment);
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
        this.parameterNames = parameterNames;
        if (exceptionTypes == null) {
            exceptionTypes = new String[] { };
        }
        this.exceptionTypes = exceptionTypes;
    }


    public String getType() {
        return getReturnType();
    }


    public String getReturnType() {
        return returnType;
    }


    public String[] getParameterTypes() {
        return parameterTypes;
    }


    public String[] getParameterNames() {
        return parameterNames;
    }


    public String[] getExceptionTypes() {
        return exceptionTypes;
    }


    public String getLine() {
        return modifiersToString(getModifiers()) + getReturnType() +
            " " + getName() +
            parametersToString(parameterTypes, parameterNames, exceptionTypes);
    }


    static String parametersToString(Class[] parameterTypes,
                                     String[] parameterNames,
                                     Class[] exceptionTypes) {
        String[] pt = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            pt[i] = parameterTypes[i].getName();
        }
        String[] et = new String[exceptionTypes.length];
        for (int i = 0; i < exceptionTypes.length; i++) {
            et[i] = exceptionTypes[i].getName();
        }
        return parametersToString(pt, parameterNames, et);
    }


    static String parametersToString(String[] parameterTypes,
                                     String[] parameterNames,
                                     String[] exceptionTypes) {
        String p = "(";
        for (int i = 0; i < parameterTypes.length; i++) {
            p += parameterTypes[i];
            if (parameterNames != null) { p+= " " + parameterNames[i]; }
            if (i != parameterTypes.length - 1) { p += ", "; }
        }
        p += ")";
        if (exceptionTypes.length > 0) {
            p += " throws ";
            for (int i = 0; i < exceptionTypes.length; i++) {
                p += exceptionTypes[i];
                if (i != exceptionTypes.length - 1) { p += ", "; }
            }
        }

        return p;
    }


    public int compareTo(MemberInfo mi) {
        int nc = getName().compareTo(mi.getName());
        if (nc != 0) { return nc; }
        if (mi instanceof MethodInfo) {
            int rc = returnType.compareTo(((MethodInfo)mi).returnType);
            if (rc != 0) { return rc; }
            String[] opt = ((MethodInfo)mi).parameterTypes;
            if (parameterTypes.length < opt.length) {
                return -1;
            } else if (parameterTypes.length > opt.length) {
                return 1;
            } else {
                for (int i = 0; i < parameterTypes.length; i++) {
                    int pc = parameterTypes[i].compareTo(opt[i]);
                    if(pc != 0) { return pc; }
                }
            }
            return 0;
        } else {
            return 1;
        }
    }


    public String toString() {
        return getName() + "()";
    }
}
