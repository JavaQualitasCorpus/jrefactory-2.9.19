package experiment;

public class TryTest {
	public void run() {
		InputStream input = null;
		if (this.equals("works")) {
			System.out.println("Something is wrong");
		}
		else {
			System.out.println("Normal operation");
		}
		if (input == null) {
			System.out.println("Normal operation");
		}
		try {
			StringBuffer buf = new StringBuffer("message");
			input = new FileInputStream("c:\\temp\\test.txt");
			input.write(buf.toString().getBytes());
}
catch (IOException ioe) {
	ioe.printStackTrace();
}
finally {
	try {
		if (input != null) {
			input.close();
		}
	} catch (IOException ioe) {
		System.out.println("Unable to close the file");
	}
}
}
}
