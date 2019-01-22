package direct;

/**
 *  A test of allocation
 *
 */
public class AllocTest {
	public Object getNew() {
		return new test.TestClass();
	}

	public Object getSimpleRename() {
		return new ren.SimpleRename();
	}

	public Object getSimpleNameConflict() {
		return new ren.SimpleNameConflict();
	}

	public Object getComplexNameConflict() {
		return new ren.ComplexNameConflict();
	}
}
