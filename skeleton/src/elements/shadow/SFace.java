package elements.shadow;

import java.util.HashSet;

public class SFace {
	public String faceName;
	public HashSet<String> setNames = new HashSet<>();
		
	public SFace(String faceName, HashSet<String> setNames) {
		this.faceName = faceName;
		this.setNames = setNames;
	}		
}
