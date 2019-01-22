/*
 * ErrorInfo.java - part of the CodeAid plugin.
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
// Collections API
// java.lang.Comparable


public final class ErrorInfo implements Comparable {
    private SourceLocation sourceLocation;
    private String         description;

    public ErrorInfo(SourceLocation sourceLocation, String description) {
        this.sourceLocation = sourceLocation;
        this.description = description;
    }


    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }


    public String getDescription() {
        return description;
    }


    public int compareTo(Object o) {
        ErrorInfo ei = (ErrorInfo) o;
        int sc = sourceLocation.compareTo(ei.sourceLocation);
        if (sc == 0) {
            if (description == null && ei.description == null) {
                return 0;
            } else if (description == null && ei.description != null) {
                return -1;
            } else if (description != null && ei.description == null) {
                return 1;
            }
            else {
                return description.compareTo(ei.description);
            }
        } else {
            return sc;
        }
    }


    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }


    public int hashCode() {
        return sourceLocation.hashCode();
    }


    public String toString() {
        return sourceLocation.toString() + ": " + description;
    }
}
