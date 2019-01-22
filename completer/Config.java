package org.acm.seguin.completer;

import java.util.Properties;
import java.io.*;
import java.awt.Color;
import org.gjt.sp.jedit.jEdit;


public class Config {
   static boolean DEBUG = false;
   static {
   try{
   jEdit.getProperty("fony.value");
   }catch(NullPointerException e){
   DEBUG = true;
   }
   }
   
   static final String CONFIG_ROOT = "Completer"; // Completer.PLUGIN_NAME.toLowerCase();
   
   // member popup
   public static final Config DOT_POPUP =   makeConfig("dot.popup", Boolean.TRUE);
   public static final Config SHOW_RETURN_TYPES =   makeConfig("show.return.types", Boolean.TRUE);
   
   public static final Config SHOW_PARAMS =   makeConfig("show.params", Boolean.TRUE);
   
   /* add?  think through implementation more first
   public static final Config AUTO_INSERT_SINGLE = 
   makeConfig("auto.insert.single", Boolean.TRUE);
   */
   
   public static final Config POPUP_DELAY_MS =  makeConfig("popup.delay", new Integer(50));
   public static final Config SHOW_SIG_HELP_AFTER_INSERT =  makeConfig("show.sig.help", Boolean.FALSE);
   
   // class popup
   public static final Config SHOW_CONSTRUCTOR_HELP_AFTER_INSERT =  makeConfig("show.cons.help", Boolean.TRUE);
   public static final Config AUTO_IMPORT_CLASSES =  makeConfig("auto.import.classes", Boolean.FALSE);
   public static final Config USE_ALL_PROJECT_CLASSES =  makeConfig("use.all.classes", Boolean.FALSE);
   public static final Config IMPORT_CLASS_FILTER_OUT_RE =   makeConfig("use.all.classes.filterout.re", "^sun\\..*|^COM\\.rsa\\..*|^com\\.sun\\..*|^org\\.omg\\..*|^java\\.beans\\..*");
   
   // misc
   public static final Config FLUSH_CACHE_ON_BUFFER_CHANGE = makeConfig("flush.cache.on.buffer.change", Boolean.TRUE);
   public static final Config RESIZE_POPUP_AS_DATA_NARROWS = makeConfig("resize.popup", Boolean.TRUE);
   // classpath
   public static final String CP_SRC_ANT_HELPER = "AntHelper";
   public static final String CP_SRC_JCOMPILER = "JCompiler";
   public static final String CP_SRC_CUSTOM = "Custom";
   public static final Config CLASS_PATH_SOURCE =  makeConfig("class.path.source", CP_SRC_ANT_HELPER);
   public static final Config CLASS_PATH_CUSTOM =  makeConfig("class.path.custom.value", "");
   String name = null;
   Object val = null;
   public Config(String name, Object defaultValue){
      this.name = name;
      val = defaultValue;
   }
   public String getName(){ return name; }
   public Object getDefault(){ return val; }
   public int getAsInt(){
      return Config.getInt(name, ((Integer)getDefault()).intValue());
   }
   public void setInt(int argNewVal){
      Config.setInt(getName(), argNewVal);
   }
   public Color getAsColor(){
      return Config.getColor(name, (Color)getDefault());
   }
   public void setColor(Color argColor){
      Config.setColor(getName(), argColor);
   }
   public boolean getAsBoolean(){
      return Config.getBoolean(name, ((Boolean)getDefault()).booleanValue());
   }
   public void setBoolean(boolean argNewVal){
      Config.setBoolean(getName(), argNewVal);
   }
   public String getAsString(){
      return Config.getString(name, (String)getDefault());
   }
   public void setString(String argNewVal){
      Config.setString(getName(), argNewVal);
   }
   public String toString(){
      return super.toString() + " - " + name + "=" + val.toString();
   }
   public static final Config makeConfig(String argName, Object argDefault){ 
      return new Config(CONFIG_ROOT + "." + argName, argDefault);
   }
   /* wrap jedit methods so we can test outside of jedit */
   public static int getInt(String argName, int argDefault){
      return DEBUG ? argDefault : jEdit.getIntegerProperty(argName, argDefault);
   }
   public static void setInt(String argName, int arg){
      if (!DEBUG){
         jEdit.setIntegerProperty(argName, arg);
      }
   }
   public static boolean getBoolean(String argName, boolean argDefault){
      return DEBUG ? argDefault : jEdit.getBooleanProperty(argName, argDefault);
   }
   public static void setBoolean(String argName, boolean argVal){
      if (!DEBUG){
         jEdit.setBooleanProperty(argName, argVal);
      }
   }
   
   public static String getString(String argName, String argDefault){
      return DEBUG ? argDefault : jEdit.getProperty(argName, argDefault);
   }
   
   public static Color getColor(String argName, Color argDefault){
      return DEBUG ? argDefault : jEdit.getColorProperty(argName, argDefault);
   }
   
   public static void setColor(String argName, Color argVal){
      if (!DEBUG){
         jEdit.setColorProperty(argName, argVal);
      }
   }
   public static void setString(String argName, String argVal){
      if (!DEBUG){
         jEdit.setProperty(argName, argVal);
      }
   }
}



