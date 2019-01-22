package abstracter;

import imp.ParentRapFour;

public class ChildRapFourImp extends RapTestFour {
	public String getClassName() {
		return "ChildRapFourImp";
	}

	public ParentRapFour createGrandparent() {
		return new ParentRapFour();
	}
}
