package elements.shadow;

import java.util.HashSet;

public class SCell {
	public String cellName;
	public HashSet<String> setNames = new HashSet<>();
	
	public SCell(String cellName, HashSet<String> setNames) {
		this.cellName = cellName;
		this.setNames = setNames;
	}	
}
