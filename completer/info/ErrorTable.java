/*
 * ErrorTable.java - part of the CodeAid plugin.
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
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

import errorlist.DefaultErrorSource;
import errorlist.ErrorSource;
import org.gjt.sp.util.Log;


/**
 * Contains a list of error information stored in <code>ErrorInfo</code>
 * objects.  All methods are thread-safe.
 *
 * @author Jason Ginchereau
**/
public final class ErrorTable {
    private static Map staticInstanceTable = new HashMap();
    private static final Object DEFAULT_KEY = new String();


    /**
     * Gets a static instance of a <code>ErrorTable</code> that can
     * be shared by everyone with the same key.
    **/
    public static synchronized ErrorTable getInstance(Object key) {
        ErrorTable instance;
        if ((instance = (ErrorTable) staticInstanceTable.get(key)) == null) {
            staticInstanceTable.put(key, instance = new ErrorTable());
        }
        return instance;
    }


    /**
     * Gets a static instance of a <code>ErrorTable</code> using
     * the default key.
    **/
    public static ErrorTable getInstance() {
        return getInstance(DEFAULT_KEY);
    }


    private SortedSet errorList;

    private static ErrorSource errorSource;


    public /*synchronized*/ static void setErrorSource(ErrorSource errSource)
    {
        errorSource = errSource;
        Log.log(Log.DEBUG, ErrorTable.class, "errorSource: " + ((errSource == null) ? "null" : errSource.getClass().getName()));
    }


    public ErrorTable() {
        errorList = new TreeSet();
    }


    public synchronized boolean put(ErrorInfo error) {
        notify();
        // Log.log(Log.DEBUG, this, error.toString() + " errorSource: " + ((errorSource == null) ? "null" : errorSource.getClass().getName()));
        if (errorSource != null && errorSource instanceof DefaultErrorSource) {
            int line = error.getSourceLocation().getLine() - 1;
            int column = error.getSourceLocation().getColumn() - 1;
            int endColumn = column;
            if (error.getSourceLocation().getLine() == error.getSourceLocation().getEndLine()) {
                if (error.getSourceLocation().getEndColumn() > 0) {
                    endColumn = error.getSourceLocation().getEndColumn();
                }
            }
            ((DefaultErrorSource)errorSource).addError(ErrorSource.WARNING,
                error.getSourceLocation().getPath(), line, column, endColumn,
                error.getDescription() + " " + error.getSourceLocation().toString()
            );
        }
        return errorList.add(error);
    }


    public synchronized void replace(String path, ErrorTable newTable) {
        for (Iterator i = errorList.iterator(); i.hasNext(); ) {
            ErrorInfo ei = (ErrorInfo) i.next();
            String epath = ei.getSourceLocation().getPath();
            if ((epath == null && path == null) ||
                (epath != null && path != null && epath.equals(path))
            ) {
                i.remove();
            }
        }
        errorList.addAll(newTable.errorList);
        notify();
    }


    public synchronized boolean remove(ErrorInfo error) {
        notify();
        return errorList.remove(error);
    }


    public synchronized void clear() {
        errorList.clear();
        Log.log(Log.DEBUG, this, "Clearing Errors");
        if (errorSource != null && errorSource instanceof DefaultErrorSource) {
            ((DefaultErrorSource)errorSource).clear();
        }
        notify();
    }


    public synchronized List getErrors() {
        return new LinkedList(errorList);
    }
}
