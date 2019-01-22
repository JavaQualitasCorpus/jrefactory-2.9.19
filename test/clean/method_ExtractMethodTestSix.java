import java.io.*;

public class MyOtherObject {

    public MyOtherObject(double aDouble, MyObject aRWPN_incident) {
        System.out.println("Something before");
        this.accidentType = aRWPN_incident.fSourceType;
        this.theNumber = aRWPN_incident.fNumber;
        System.out.println("Something after");
    }

    public void exceptionProblemReport(int value) {
    	String message = "Hello";

    	try {
    		PrintWriter output = new PrintWriter(new FileWriter("c:\\temp\\temp.txt"));

    		output.println("Hello there!");
    		output.println("  Message:  " + message);
    		output.println("  Code:     " + value);

    		output.close();
    	}
    	catch (IOException ioe) {
    		System.out.println("Error:  " + ioe.getMessage());
    		ioe.printStackTrace();
    	}
    }
}

