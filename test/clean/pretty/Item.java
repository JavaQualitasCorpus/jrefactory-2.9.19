package test;

import java.util.Vector;

public class Item extends Object {
protected boolean shortValue;
Vector save;
private String name;
public final static int EXAMPLE = 53;
public Item() {
name = "name";

save = new Vector();
shortValue = true;
}
public void setShort(boolean value) {
shortValue = value;
}
public void setName(String value) {
name = value;
}
public void set(String key) {
if (save.contains(key)) {
name = name + "::" + key;
}
}
public boolean isShort() {
return shortValue;
}
public String getName() {
return name;
}
public String get(String name) {
return name + "::";
}
public boolean is(String value) {
return name.equals(value);
}
public void addKey(String key) {
save.addElement(key);
}
public void run() {
System.out.println("Name:  " + name);
for (int ndx = 0; ndx < save.size(); ndx++) {
System.out.println("..." +
save.elementAt(ndx).toString());
}
}
public static void main(String[] args) {
System.out.println("This is the main program");
}
public static void main(int stupid) {
System.out.println("This is not the main program");
}
}
