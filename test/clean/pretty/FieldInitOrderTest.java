package pretty;

public class FieldInitOrderTest {
	private String privateField;
	private String privateFieldInit = "Chris";
	int packageInt;
	int packageIntInit = 66;
	protected String protectedField;
	protected String protectedFieldInit = privateFieldInit + " Seguin";
	public String publicField;
	public String publicFieldInit = protectedFieldInit + " wrote this code";

	public FieldInitOrderTest() {}
	public void method() { System.out.println("Message:  " + publicFieldInit); }
}
