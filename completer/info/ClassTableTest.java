package org.acm.seguin.completer.info;

import org.acm.seguin.completer.info.ClassInfo;
import java.util.*;
import java.util.Iterator;
import java.io.File;
import java.util.zip.ZipFile;
import org.gjt.sp.jedit.jEdit;
import java.io.IOException;
import org.acm.seguin.completer.Completer;
import org.acm.seguin.ide.jedit.Navigator;


public class ClassTableTest{

    static final Navigator.NavigatorLogger logger = Navigator.getLogger(ClassTableTest.class);

    public ClassTableTest(){
        ClassTable ct = new ClassTable();
        try{
            // load something
            ClassInfo ci = ct.get("java.util.Map");
            logger.msg("ci", ci == null ? "null" : ci.toString());
            // getClassesInPackage
            List listClasses = ct.getClassesInPackage("java.util");
            for (Iterator it = listClasses.iterator(); it.hasNext();){
                logger.msg(it.next().toString());
            }

            // packages
            /*
            for (Iterator it = ct.getPackages().iterator(); it.hasNext();){
                logger.msg(it.next().toString());
            }*/

            // get
            //logger.msg(ct.get("java.util.Vector").toString());

            logger.msg("ct.size()", ct.size());
        }catch(Exception e){
            logger.error("Error loading", e);
        }
    }

    public static void main(String[] args){
        try{
        }catch(Throwable t){
            t.printStackTrace();
        }
    }


}

