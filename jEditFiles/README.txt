TO Run JavaStyle in jEdit
-------------------------

Unzip the jar files contained in JavaStyle-2.9.18.zip into either the <user.dir>.jedit/jars directory
or the jEdit 4.2pre12/jars directory.



To Compile JavaStyle
--------------------


Unzip the jar files contained in JavaStyle-2.9.18-source.zip into the jEdit 4.2pre12/jars directory.

There should also be ErrorList.jar and ProjectViewer.jar in that directory.

Load the build.xml file into AntFarm (or equivalent) and run the target "jEdit.JavaStyle.jar"

The resulting Jar file will be created in ant.build/lib



Jar Files Used
--------------

   bcel.jar                                     (avaiable from http://jakarta.apache.org/bcel/ )

   findbugs.jar findbugsGUI.jar, coreplugin.jar (avaiable from http://www.cs.umd.edu/~pugh/java/bugs/ )

   jaxen-core-1.0-fcs.jar                       (avaiable from http://jaxen.org/ )

   dom4j-full.jar                               (avaiable from http://dom4j.org/ )

   saxpath-1.0-fcs.jar                          (avaiable from http://saxpath.sourceforge.net/ )



