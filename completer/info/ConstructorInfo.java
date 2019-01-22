/*
 * ConstructorInfo.java - part of the CodeAid plugin.
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


//package codeaid.info;
package org.acm.seguin.completer.info;

import java.lang.reflect.Constructor;


public final class ConstructorInfo extends MemberInfo {
    private String[] parameterTypes;
    private String[] parameterNames;
    private String[] exceptionTypes;


    public ConstructorInfo(Constructor c) {
        super(c);
        Class[] pt = c.getParameterTypes();
        parameterTypes = new String[pt.length];
        for (int i = 0; i < pt.length; i++) {
            parameterTypes[i] = pt[i].getName();
        }
        parameterNames = null;
        Class[] et = c.getExceptionTypes();
        exceptionTypes = new String[et.length];
        for (int i = 0; i < et.length; i++) {
            exceptionTypes[i] = et[i].getName();
        }
    }


    public ConstructorInfo(String declaringClass, int modifiers, String name,
                           String[] parameterTypes, String[] parameterNames,
                           String[] exceptionTypes, String comment
    ) {
        super(declaringClass, modifiers, name, comment);
        this.parameterTypes = parameterTypes;
        this.parameterNames = parameterNames;
        if (exceptionTypes == null) {
            exceptionTypes = new String[] { };
        }
        this.exceptionTypes = exceptionTypes;
    }


    public String getType() {
        return getDeclaringClass();
    }


    public String getLine() {
        return modifiersToString(getModifiers()) + getName() +
            MethodInfo.parametersToString(parameterTypes, parameterNames,
                                          exceptionTypes);
    }


    public String[] getParameterNames() {
        return parameterNames;
    }


    public String[] getParameterTypes() {
        return parameterTypes;
    }


    public String[] getExceptionTypes() {
        return exceptionTypes;
    }


    public int compareTo(MemberInfo mi) {
        int nc = getName().compareTo(mi.getName());
        if (nc != 0) { return nc; }

        if (mi instanceof ConstructorInfo) {
            String[] opt = ((ConstructorInfo) mi).parameterTypes;
            if (parameterTypes.length < opt.length) {
                return -1;
            } else if (parameterTypes.length > opt.length) {
                return 1;
            } else {
                for (int i = 0; i < parameterTypes.length; i++) {
                    int tc = parameterTypes[i].compareTo(opt[i]);
                    if (tc != 0) { return tc; }
                }
            }
            return 0;
        } else if (mi instanceof MethodInfo) {
            return -1;
        } else {
            return 1;
        }
    }
}

