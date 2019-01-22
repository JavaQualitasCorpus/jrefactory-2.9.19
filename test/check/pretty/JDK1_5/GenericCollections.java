package jdk1_5;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author    Chris Seguin
 */
public class GenericCollections {
	String[] strings = new String[0];
	List<Map.Entry<String, String>> x = new ArrayList<Map.Entry<String, String>>();


	/**
	 *  The main program for the GenericCollections class
	 *
	 *@param  argv  The command line arguments
	 */
	public static void main(String[] argv) {
		List<String> list = new List<String>();
		List<Map.Entry<String, String>> x;
		x = new ArrayList<Map.Entry<String, String>>();
		List<Map.Entry<String, String>> shards = new ArrayList<Map.Entry<String, String>>();

		X:
		for (Map.Entry<String, String> entry : shards) {
			System.out.println("key=" + entry.getKey() + ", value=" + entry.getValue());
		}
	}
}

