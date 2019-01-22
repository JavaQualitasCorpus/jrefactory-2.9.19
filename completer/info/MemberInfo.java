/*
 * MemberInfo.java - part of the CodeAid plugin for jEdit
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

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.io.Serializable;
import java.util.StringTokenizer;


/**
 * The base class for class, field, constructor, and method info structures.
 *
 * @author Jason Ginchereau
 */
public abstract class MemberInfo implements Serializable, Comparable {
    private String declaringClass;
    private int    modifiers;
    private String name;
    private String comment;

    private SourceLocation location = SourceLocation.UNKNOWN;


    MemberInfo(Member m) {
        this.declaringClass = m.getDeclaringClass().getName();
        this.modifiers = m.getModifiers();
        this.name      = m.getName();
        this.comment   = null;
    }


    MemberInfo(String declaringClass, int modifiers,
               String name, String comment) {
        this.declaringClass = declaringClass;
        this.modifiers = modifiers;
        this.name      = name;
        setComment(comment);
    }


    /**
     * Gets the fully-qualified name of the class that declares this member.
    **/
    public final String getDeclaringClass() {
        return declaringClass;
    }


    /**
     * Gets the modifiers mask of this member, that can be decoded using the
     * <code>java.lang.reflect.Modifier</code> class.
    **/
    public final int getModifiers() {
        return modifiers;
    }


    /**
     * Gets the simple name of this member.
    **/
    public final String getName() {
        return name;
    }


    /**
     * Gets the fully-qualified type of this member.
    **/
    public abstract String getType();


    public abstract String getLine();


    /**
     * Gets the one-line comment associated with this member.
     *
     * @return the first-sentence portion of the javadoc comment, or
     *         <code>null</code> if there is no comment associated with
     *         this member.
    **/
    public final String getOneLineComment() {
        if(comment == null) { return null; }
        // TODO: return only first line
        int i = comment.indexOf(". ");
        int j = comment.indexOf(".\n");
        if (i >= 0 && (j < 0 || i < j)) {
            return comment.substring(0, i+1);
        } else if (j >= 0 && (i < 0 || j < i)) {
            return comment.substring(0, j+1);
        } else {
            return comment;
        }
    }


    /**
     * Gets the comment associated with this member.
     *
     * @return the javadoc comment, or <code>null</code> if there is no comment
     *         associated with this member.
    **/
    public final String getComment() {
        return comment;
    }


    public final void setComment(String comment) {
        
        if (comment != null) {
            StringTokenizer st = new StringTokenizer(comment, "\r\n");
            comment = "";
            while (st.hasMoreTokens()) {
                String line = st.nextToken().trim();
                if (line.startsWith("/**")) {
                    line = line.substring(3);
                } else if (line.startsWith("*/")) {
                    line = line.substring(2);
                } else if (line.startsWith("*")) {
                    line = line.substring(1);
                }
                comment += line.trim() + '\n';
            }
            comment = comment.trim();
        }
        this.comment = comment;
    }


    /**
     * Gets the location in the source code where this member was
     * declared.
     *
     * @return the <code>SourceLocation</code>, or
     *         <code>SourceLocation.UNKNOWN</code> if the location is unknown.
    **/
    public final SourceLocation getSourceLocation() {
        return location;
    }


    public final void setSourceLocation(SourceLocation location) {
        this.location = location;
    }


    static String modifiersToString(int modifiers) {
        String m = Modifier.toString(modifiers);
        if (m.length() > 0) { m += ' '; }
        return m;
    }


    public boolean equals(Object o) {
        return compareTo((MemberInfo) o) == 0;
    }


    /**
     * Just calls <code>compareTo(MemberInfo)</code>.
    **/
    public final int compareTo(Object o) {
        return compareTo((MemberInfo)o);
    }


    /**
     * Implements a consistent sorting policy for all <code>MemberInfo</code>
     * items that is primarily based on their simple name.
    **/
    public abstract int compareTo(MemberInfo mi);


    public String toString() {
        return getName();
    }
}

