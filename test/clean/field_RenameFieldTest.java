package field;

public class RenameFieldTest {
	private String simple;
	private int code;
	public double height;
	public static final int CODE_ON = 0;
	public static final int CODE_OFF = 1;
   private UsesFieldTest childObject;

	public RenameFieldTest() {
		simple = "ample";
		code = 52;
		height = 321.12;
	}

	public RenameFieldTest(int code) {
		simple = "ample";
		this.code = code;
		height = 321.12;
	}

	public String getSimple() {
		return simple;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return this.code;
	}

	public void setCodeAlt(int changed) {
		code = changed;
	}

	public void debugPrint() {
		System.out.println("String:  " + simple.toString() + "  " + simple());
	}

	public boolean equals(Object other) {
		if (other instanceof RenameFieldTest) {
			RenameFieldTest rft = (RenameFieldTest) other;
			return rft.code == this.code;
		}

		return false;
	}

	public double getVolume() {
		return height * this.height * height;
	}

   void usesField(UsesFieldTest anObject) {
        anObject.simple = "";
    }


    void upCasts(RenameFieldTest objectToDownCast) {
        ((InheritFieldTest)objectToDownCast).anOtherMember = "";
        ((InheritFieldTest)objectToDownCast).simple = "";
    }

    void downCasts(InheritFieldTest objectToDownCast) {
        ((RenameFieldTest)objectToDownCast).simple = "";
    }

    void linkedField(RenameFieldTest linkedObject) {
        linkedObject.childObject.simple = "";
    }
    

}
