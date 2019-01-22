package method;

import java.io.*;

public class ExtractMethodTestSeven {
	public void method(File file) {
		System.out.println("This is before");
		try {
			PrintWriter output = new PrintWriter(new FileWriter(file));

			output.println("Line #1");

			output.close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("This is after");
	}
}
