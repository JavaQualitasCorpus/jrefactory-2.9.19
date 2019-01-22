/*
 * FieldInfo.java - part of the CodeAid plugin.
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

import java.lang.reflect.Field;


public final class FieldInfo extends MemberInfo {
    private String type;


    public FieldInfo(Field f) {
        super(f);
        this.type = f.getType().getName();
    }


    public FieldInfo(String declaringClass, int modifiers,
                     String type, String name, String comment) {
        super(declaringClass, modifiers, name, comment);
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public String getLine() {
        return modifiersToString(getModifiers()) + getType() + " " + getName();
    }


    public int compareTo(MemberInfo mi) {
        int nc = getName().compareTo(mi.getName());
        if (nc != 0) { return nc; }
        if (mi instanceof FieldInfo) { return 0; }
        else if (mi instanceof ClassInfo) { return 1; }
        else { return -1; }
    }
}

