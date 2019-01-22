import java.io.*;

/**
 *  Description of the Class
 *
 *@created June 5, 1999
 */
public class TestClass {
	public static void main(String[] args) {
		(new TestClass()).run();
	}


	public void run() {
		System.out.println("This class is a:  " +
			this.getClass().getName());
	}
}
