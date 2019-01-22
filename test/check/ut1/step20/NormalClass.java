package abstracter;

public class NormalClass extends GrandParentClass {
	public NormalClass() {
	}

	public NormalClass(String name) {
		super(name);
	}

	public NormalClass(String name, int index) {
		super(name);
		System.out.println("The name is:  " + name);
		System.out.println("The index is:  " + index);
	}

	public String getParentName() {
		return "My parent";
	}
}
