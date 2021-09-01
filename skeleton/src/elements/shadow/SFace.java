package elements.shadow;

import java.util.HashSet;
import java.util.Set;

public class SFace {
	public String faceName;
	public Set<String> setNames = new HashSet<>();
		
	public SFace(String faceName, Set<String> setNames) {
		this.faceName = faceName;
		this.setNames = setNames;
	}		
}
