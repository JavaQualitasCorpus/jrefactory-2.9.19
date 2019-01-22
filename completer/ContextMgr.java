package org.acm.seguin.completer;

/*
 * Copyright (c) 2002, Beau Tateyama
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

      

//import anthelper.adapters.Plugin;
import org.gjt.sp.jedit.EBComponent;
//import anthelper.adapters.SpeedJavaUtils;
//import anthelper.ui.ConfigDialog;
//import anthelper.utils.JEditLogger;
import java.io.*;
import java.util.*;
import javax.swing.*;
import org.gjt.sp.jedit.*;
import org.gjt.sp.jedit.textarea.*;
import org.acm.seguin.ide.jedit.Navigator;

/**
 * Description of the Class
 *
 * @author    btateyama@yahoo.com
 * @created   September 9, 2002
 */
public class ContextMgr implements EBComponent {
    final static Navigator.NavigatorLogger logger = Completer.getLogger(ClassPathSrcMgr.class);
	final static String FILE_NAME = "Completer.props"; // AntHelperPlugin.PLUGIN_NAME + ".props";

	final static String CONTEXT_COUNT = "ContextCount";
	final static String BUILD_FILE = "BuildFile";
	final static String OUTPUT_DIR = "OutputDir";
	final static String SOURCEPATH = "SourcePath";
	final static String SOURCEPATH_ID = "SourcePathRefID";
	final static String CLASSPATH = "ClassPath";
	final static String CLASSPATH_ID = "ClassPathRefID";
	final static String TASKA = "TaskA";
	final static String TASKB = "TaskB";
	final static String TASKC = "TaskC";
	final static String TASKD = "TaskD";
	final static String SAVE_BEFORE_COMPILE = "SaveBeforeCompile";
	final static String SAVE_BEFORE_RUN = "SaveBeforeRun";
	final static String SYNC_TO_SPEEDJAVA = "SyncToSpeedJava";
	final static String LAST_MOD = "LastModDateMs";
	final static String RUN_PARAMS = "RunParams";
	final static String JVM_OPTIONS = "JVMOptions";
	
	static ContextMgr _instance = new ContextMgr();


	/**
	 * Gets the instance attribute of the ContextMgr class
	 *
	 * @return   The instance value
	 */
	public static ContextMgr getInstance() {
		return _instance;
	}


	/**
	 * Gets the context attribute of the ContextMgr class
	 *
	 * @param argView  Description of the Parameter
	 * @return         The context value
	 */
	public static Context getContext(View argView) {
		return getContext(argView, true);
	}

    public static Context getContext(View argView, boolean argAutoConfig){
        return getInstance().getContext(argView, argView== null ? null : argView.getBuffer(), argAutoConfig);
	}
    
    public static Context getContext(View argView, Buffer argBuffer, boolean argAutoConfig){
		return getInstance().getContext(argView, findBuildFile(argBuffer), argAutoConfig);
	}
	
	static File findBuildFile(Buffer argBuffer){
		File fileBuild = null;
        if (argBuffer != null){
            File file = new File(argBuffer.getPath());
			File[] files = null;
			while ((file = file.getParentFile()) != null && fileBuild == null) {
				files = file.listFiles();
                if (files != null){
                    for (int i=0; i < files.length; i++){
                        if (files[i].getName().equals("build.xml")){
                            fileBuild = files[i];
                            break;
                        }
                    }
                }
			}
		}
		return fileBuild;
    }

	
	/**
	 * Description of the Method
	 *
	 * @param i       Description of the Parameter
	 * @param argVal  Description of the Parameter
	 * @return        Description of the Return Value
	 */
	static String makeKey(int i, String argVal) {
		return i + "." + argVal;
	}


	Map _mapContexts = new Hashtable();
	File _fileConfig = null;

	/**
	 * Constructor for the ContextMgr object
	 */
	private ContextMgr() {
		_fileConfig = new File(jEdit.getSettingsDirectory(), FILE_NAME); 
		if (!_fileConfig.exists()) {
			// none exists
			logger.msg("No config exists.");
		} else {
			load(_fileConfig);
		}
        
        EditBus.addToBus(this);
	}
	
    public void handleMessage(EBMessage argMsg){
        if (argMsg instanceof ContextUpdateMessage){
            logger.debug("*****ContextUpdate!");
            ContextUpdateMessage updateMsg = (ContextUpdateMessage) argMsg;
            logger.debug("*****new.cp", updateMsg.getClassPath());
        }
    }

    /*
    public void handleMessage(EBMessage argMsg){
        if (argMsg instanceof anthelper.ContextUpdateMessage){
            anthelper.ContextUpdateMessage updateMsg = (anthelper.ContextUpdateMessage) argMsg;
            Log.log(Log.MESSAGE,this,"*****new.cp=" + updateMsg.getClassPath()););
        }
    }
    */
	
	/**
	 * Gets the context attribute of the ContextMgr object
	 *
	 * @param argFilePath  Description of the Parameter
	 * @return             The context value
	 */
	 Context getContext(View argView, File argBuildFile, boolean argAutoConfig){
		 Context context = null;
		 if (argBuildFile == null){
             if (argAutoConfig){
                 JOptionPane.showMessageDialog(argView, "No build file found.");
             }else{
                 logger.warning("No build file found");
             }
		 }else{
			 context = (Context) _mapContexts.get(argBuildFile.getPath());

			 if (argAutoConfig && context == null){
				 // no context exists so create one
				 String strMsg = "Create profile for (" + argBuildFile.getPath() + ")?";
				 int iReturn = JOptionPane.showConfirmDialog(argView, strMsg, "Create Profile", JOptionPane.YES_NO_OPTION);
				 if (iReturn == JOptionPane.YES_OPTION){
					 context = new Context(argBuildFile);
					 context = doConfig(argView, context);
				 }
			 }else if (argAutoConfig && context.getLastMod() < argBuildFile.lastModified()){
                 // configuration to turn off build file changed nag message.
                 boolean bAutoUpdate = 
                 jEdit.getBooleanProperty("TOGGLE_UPDATE_ALERT", true); //FIXME: AntHelperPlugin.ACTION_TOGGLE_UPDATE_ALERT, true);
                 if (bAutoUpdate){
                     JOptionPane.showMessageDialog(argView, "Build file updated.  Please update configuration.");
                     context = doConfig(argView, context);
                 }
			 }else{
				 // do nothing, context is fine the way it is
			 }
		}
		 
		 /* should i update other components every time the context is retrieved (i.e. all actions)
		 if (context != null){
			 // things we always update on a context 
			 
			 // update antfarm by executing a bogus ("") task
			 AntHelperPlugin.executeTask(argView, context.getBuildFile(), "");
		 }
		 */
		return context;
	}

	 static Context doConfig(View argView, Context argContext){
/*
       Context context = null;
         ConfigDialog cd = new ConfigDialog(argView, argContext);
		 cd.show();
		 if (cd.cancel()){
             logger.msg("Cancelled profile edit\n(" + argContext.getBuildFile().getPath() + ")");
		 }else{
			 context = cd.getContext();
			 logger.msg(context.toString());
			 ContextMgr.getInstance().storeContext(context);
             ContextMgr.getInstance().save();
			 logger.msg("Saved profile\n(" + context.getBuildFile().getPath() + ")");
             // fire update message
             ContextUpdateMessage updateMsg = new ContextUpdateMessage(ContextMgr.getInstance(), 
                                                                       context);
             EditBus.send(updateMsg);
		 }
		 return context;
*/
	 }
	 
	 void storeContext(Context argContext){
		if (argContext != null){
			_mapContexts.put(argContext.getBuildFile().getPath(), argContext);
			if (argContext.getSyncToSpeedJava() && argContext.getClassPath() != null){
				// set the prop for speedjava and update the classpath mgr
				jEdit.setProperty("speedjava.classpath", argContext.getClassPath());
                //FIXME: if (Plugin.SPEEDJAVA.isInstalled()){
                //FIXME:     SpeedJavaUtils.updateClassPath(argContext.getClassPath());
                //FIXME: }
			}
		}
	}

	/**
	 * Load the saved contexts from the configuration file
	 *
	 * @param argConfigFile  Description of the Parameter
	 */
	void load(File argConfigFile) {

		logger.msg("Loading config file:" + argConfigFile.getPath());
		try {
			// load from properties
			Properties p = new Properties();
			FileInputStream fis = new FileInputStream(argConfigFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			p.load(fis);

			// now unserialize the contexts within
			// @TODO: some error checking...
			String strCount = p.getProperty(CONTEXT_COUNT);
			if (strCount != null) {
				int iCount = Integer.parseInt(strCount);
				Context context = null;
				String strVal = null;
				File fileBuild = null;
				for (int i = 0; i < iCount; i++) {
					try {
						// get the build file
						strVal = p.getProperty(makeKey(i, BUILD_FILE));
						fileBuild = new File(strVal);
						// create a context
						context = new Context(fileBuild.getCanonicalFile());

						// set other props
						context.setAntTaskA(p.getProperty(makeKey(i, TASKA)));
						context.setAntTaskB(p.getProperty(makeKey(i, TASKB)));
						context.setAntTaskC(p.getProperty(makeKey(i, TASKC)));
						context.setAntTaskD(p.getProperty(makeKey(i, TASKD)));
						context.setClassPath(p.getProperty(makeKey(i, CLASSPATH)));
						context.setClassPathRefID(p.getProperty(makeKey(i, CLASSPATH_ID)));
                        context.setSourcePath(p.getProperty(makeKey(i, SOURCEPATH)));
						context.setSourcePathRefID(p.getProperty(makeKey(i, SOURCEPATH_ID)));

						context.setOutputDirectory(new File(p.getProperty(makeKey(i, OUTPUT_DIR))));
						
						
						context.setSaveBeforeCompile(p.getProperty(makeKey(i,SAVE_BEFORE_COMPILE)) != null);
						context.setSaveBeforeRun(p.getProperty(makeKey(i,SAVE_BEFORE_RUN)) != null);
						context.setSyncToSpeedJava(p.getProperty(makeKey(i,SYNC_TO_SPEEDJAVA)) != null);
						
						context.setLastMod(Long.parseLong(p.getProperty(makeKey(i,LAST_MOD))));

						context.setJVMOptions(p.getProperty(makeKey(i, JVM_OPTIONS)));
						context.setRunParams(p.getProperty(makeKey(i, RUN_PARAMS)));
						
						storeContext(context);
						logger.msg("Loading context for build file(" + fileBuild.getPath() + ")");
					} catch (IOException e) {
                        logger.warning("Error loading context.  Proceeding to next...");
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			logger.error("Error loading config file");
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("Misc error parsing config file");
			e.printStackTrace();
		}

	}

	void save(){
		save(_fileConfig);
	}
	/**
	 * Description of the Method
	 *
	 * @param argFileConfig  Description of the Parameter
	 */
	void save(File argFileConfig) {
        logger.msg("file.save", argFileConfig.getPath());
		synchronized (_mapContexts) {
			Properties p = new Properties();

			Context c = null;
			int i = 0;
			File fileBuild = null, fileOutputDir = null;
			for (Iterator iter = _mapContexts.values().iterator(); iter.hasNext(); ) {
				c = (Context) iter.next();
				fileBuild = c.getBuildFile();
				fileOutputDir = c.getOutputDirectory();
				
				// null contexts are not saved
				if (fileBuild != null) {
					saveProperty(p, makeKey(i, BUILD_FILE), fileBuild.getPath());
					saveProperty(p, makeKey(i, OUTPUT_DIR), fileOutputDir == null ? null : fileOutputDir.getPath());

					saveProperty(p, makeKey(i, TASKA), c.getAntTaskA());
					saveProperty(p, makeKey(i, TASKB), c.getAntTaskB());
					saveProperty(p, makeKey(i, TASKC), c.getAntTaskC());
					saveProperty(p, makeKey(i, TASKD), c.getAntTaskD());

					saveProperty(p, makeKey(i, CLASSPATH), c.getClassPath());
					saveProperty(p, makeKey(i, CLASSPATH_ID), c.getClassPathRefID());
                    saveProperty(p, makeKey(i, SOURCEPATH), c.getSourcePath());
					saveProperty(p, makeKey(i, SOURCEPATH_ID), c.getSourcePathRefID());

					saveProperty(p, makeKey(i, JVM_OPTIONS), c.getJVMOptions());
					saveProperty(p, makeKey(i, RUN_PARAMS), c.getRunParams());
					
					if (c.getSaveBeforeCompile()){
						saveProperty(p, makeKey(i, SAVE_BEFORE_COMPILE), "true");
					}
					if (c.getSaveBeforeRun()){
						saveProperty(p, makeKey(i, SAVE_BEFORE_RUN), "true");
					}
					if (c.getSyncToSpeedJava()){
						saveProperty(p, makeKey(i, SYNC_TO_SPEEDJAVA), "true");
					}
					saveProperty(p, makeKey(i, LAST_MOD), String.valueOf(c.getLastMod()));
					i++;
				}
			}
			p.setProperty(CONTEXT_COUNT, String.valueOf(i));
			try {
				FileOutputStream fos = new FileOutputStream(argFileConfig);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				p.store(bos, "Saved at: " + new Date().toString());
			} catch (IOException e) {
				logger.error("Error saving contexts");
				e.printStackTrace();
			}
		}
	}


	/**
	 * Description of the Method
	 *
	 * @param argProps    Description of the Parameter
	 * @param argKeyName  Description of the Parameter
	 * @param argValue    Description of the Parameter
	 */
	static void saveProperty(Properties argProps, String argKeyName, String argValue) {
		if (argProps != null && argKeyName != null && argValue != null) {
			argProps.setProperty(argKeyName, argValue);
		}
	}
    
    
    
    public static void main(String[] args){
        try{
            
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
    
}

