package ren;
import java.awt.Panel;
/**
 *  Description of the Class
 *
 *@author     Chris Seguin
 */
public class SimpleRename {
	private String test;
	private Panel value1;
	private int value2;
	private String value3;

	public SimpleRename() {
		test = "Unit Test #4";
	}

	public String getTest() {
		return test;
	}

	public int getValue2() {
		return value2;
	}

	public static SimpleRename factory() {
		return new SimpleRename();
	}
}
