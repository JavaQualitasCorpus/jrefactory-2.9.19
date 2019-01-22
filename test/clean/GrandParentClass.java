package abstracter;

public class GrandParentClass {
	public GrandParentClass() {
		System.out.println("Creating nameless object");
	}

	public GrandParentClass(String name) {
		System.out.println("Creating:  " + name);
	}

	public String getParentName() {
		return "My great grandparent";
	}
}
