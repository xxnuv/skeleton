package elements;

import java.util.HashSet;
import java.util.Set;

public class Face {
	
	public String name;
	public Set<Edge> edges;
	public Set<String> edgeNames;
	
	public Face(String name, Set<Edge> edges) {
		this.name = name;
		this.edges = edges;
	}
	
	
	
	public Face(String name) {
		this.name = name;
		edgeNames = new HashSet<>();
	}
	
	public String toString2() {
		String result = this.name;
		for (Edge edge : this.edges)
			result += " " + edge.toString2();
		return "[" + result + "]";
	}
	
	public String toString() {
		return this.name;
		}	

	public Set<Edge> getEdges(){
		return edges;
	}
}
