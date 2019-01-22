import java.io.*;

/**
 *  Description of the Class
 *
 */
public class TestClass {
	//  Instance Variables
	private AssociateTwo two;

	//  Class Variables
	public static final int code = 50;


	public static void main(String[] args) {
		(new TestClass()).run();
	}


	public void run() {
		System.out.println("This class is a:  " +
			this.getClass().getName());
	}
}
