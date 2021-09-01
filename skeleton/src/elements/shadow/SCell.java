package elements.shadow;

import java.util.HashSet;
import java.util.Set;

public class SCell {
	public String cellName;
	public Set<String> setNames = new HashSet<>();
	
	public SCell(String cellName, Set<String> setNames) {
		this.cellName = cellName;
		this.setNames = setNames;
	}	
}
