/*
 * SourceLocation.java - part of the CodeAid plugin.
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


/**
 * Encapsulates a location (file path, line, and column) in Java source code.
 * The path may be an absolute path (such as
 * <code>/home/me/projects/test/Test.java</code>), or it may be a relative
 * path based on the package which the file belongs to
 * (such as <code>java/lang/Object.java</code>).
 *
 * @author Jason Ginchereau
**/
public final class SourceLocation implements java.io.Serializable, Comparable {
    public static final SourceLocation UNKNOWN = new SourceLocation(null, -1, -1);

    public /*final*/ String path;
    public /*final*/ int    line;
    public /*final*/ int    col;
    public /*final*/ int    endLine;
    public /*final*/ int    endCol;


    public SourceLocation(String path, int line, int col) {
        this(path, line, col, line, col);
    }


    public SourceLocation(String path, int line, int col,
                          int endLine, int endCol) {
        if (line == -1) {
            endLine = -1;
            col = -1;
            endCol = -1;
        } else {
            if (col < 0) { col = 0; }
            if (endCol < 0) { endCol = 0; }
        }
        if (line < -1 || endLine < line || (endLine == line && endCol < col)) {
            throw new IllegalArgumentException();
        }

        this.path = path;
        this.line = line;
        this.col = col;
        this.endLine = endLine;
        this.endCol = endCol;
    }


    public String getPath() {
        return path;
    }


    public int getLine() {
        return line;
    }


    public int getColumn() {
        return col;
    }


    public int getEndLine() {
        return endLine;
    }


    public int getEndColumn() {
        return endCol;
    }


    public boolean contains(SourceLocation sl) {
        return
            ((line    < sl.line    || (line    == sl.line    && col    <= sl.col)) &&
            (endLine > sl.endLine || (endLine == sl.endLine && endCol >= sl.col)));
    }


    public int compareTo(Object o) {
        SourceLocation sl = (SourceLocation) o;
        int pc = (path == null ? pc = (sl.path == null? 0 : 1)
                               : (sl.path == null? -1 : path.compareTo(sl.path)));
        if (pc != 0) { return pc; }
        else if (line < sl.line) { return -1; }
        else if (line > sl.line) { return 1; }
        else if (col < sl.col) { return -1; }
        else if (col > sl.col) { return 1; }
        else if (endLine < sl.endLine) { return -1; }
        else if (endLine > sl.endLine) { return 1; }
        else if (endCol < sl.endCol) { return -1; }
        else if (endCol > sl.endCol) { return 1; }
        else { return 0; }
    }


    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }


    public int hashCode() {
        return path.hashCode() + line + col + endLine + endCol;
    }


    public String toString() {
        if (this == UNKNOWN) { return "UNKNOWN"; }

        String path = this.path;
        if (path == null) { path = ""; }
        String middle = (path == null? "" : " ");
        String lineCol = "";
        if (line >= 0) {
            if (endLine == line && endCol == col) {
                lineCol = middle + "(" + line + ", " + col + ")";
            } else {
                lineCol = middle + "(" + line + ", " + col + " -> " +
                          endLine + ", " + endCol + ")";
            }
        }
        return path + lineCol;
    }
}
