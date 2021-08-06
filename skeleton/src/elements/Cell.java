package elements;

import java.util.HashSet;
import java.util.Set;

public class Cell {
	
	public String name;
	public Set<Face> faces;
	public Set<String> faceNames;
	
	public Cell(String name, Set<Face> faces) {
		this.name = name;
		this.faces = faces;
	}
	
	
	
	public Cell(String name) {
		this.name = name;
		faceNames = new HashSet<>();
	}
	
	public String toString2() {
		String result = this.name;
		for (Face face : this.faces)
			result += " " + face.toString2();
		return result;
	}
	
	public String toString() {
		return this.name;
		}	

	public Set<Face> getFaces() {
		return this.faces;
	}
}
