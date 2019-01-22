package imp;

import abstracter.RapTestOne;
import abstracter.ChildRapOne;
import abstracter.RapTestTwo;
import abstracter.ChildRapTwo;
import abstracter.RapTestThree;
import abstracter.ChildRapThree;
import abstracter.RapTestFour;
import abstracter.ChildRapFour;

public class AccessWrap {
	public RapTestOne getInstanceOne() {
		return new ChildRapOne();
	}
	public RapTestTwo getInstanceTwo() {
		return new ChildRapTwo();
	}
	public RapTestThree getInstanceThree() {
		return new ChildRapThree();
	}
	public RapTestFour getInstanceFour() {
		return new ChildRapFour();
	}
}
