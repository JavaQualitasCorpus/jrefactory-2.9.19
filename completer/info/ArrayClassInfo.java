/*
 * ArrayClassInfo.java - part of the CodeAid plugin.
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

import java.lang.reflect.Modifier;


/**
 * @see ClassInfo
 *
 * @author Jason Ginchereau
**/
public class ArrayClassInfo extends ClassInfo {
    private int       dimensions;
    private ClassInfo arrayType;


    public ArrayClassInfo(ClassInfo arrayType, int dimensions) {
        super(null, Modifier.PUBLIC, false, arrayType.getName(),
            "java.lang.Object", new String[] { }, null, null);
        this.dimensions = dimensions;
        this.arrayType = arrayType;

        add(new FieldInfo(getFullName(), Modifier.PUBLIC, "int", "length", null));
    }


    /**
     * Gets the fully-qualified name of the class.
    **/
    public String getFullName() {
        String s = arrayType.getFullName();
        for (int n = 0; n < dimensions; n++) { s += "[]"; }
        return s;
    }


    public String getLine() {
        return "class " + getFullName();
    }


    public ClassInfo getArrayType() {
        return arrayType;
    }


    public int getDimensions() {
        return dimensions;
    }


    public int compareTo(MemberInfo mi) {
        int nc = getName().compareTo(mi.getName());
        if (nc != 0) { return nc; }

        if (mi instanceof ArrayClassInfo) {
            if (dimensions < ((ArrayClassInfo)mi).dimensions) {
                return -1;
            } else if (dimensions > ((ArrayClassInfo)mi).dimensions) {
                return 1;
            } else {
                return 0;
            }
        } else if (mi instanceof ClassInfo) {
            return 1;
        } else {
            return -1;
        }
    }
}

