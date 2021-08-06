package elements;

import java.util.Set;

/**
 * A face has a set of edges. And that set construct a closed polygon.
 * @author rozhk
 *
 */
public class Face {
	
	public String name;
	public Set<Edge> edges;
	
	/**
	 * Creates new face with name and edges
	 * @param name
	 * @param edges
	 */
	public Face(String name, Set<Edge> edges) {
		this.name = name;
		this.edges = edges;
	}
	
	/**
	 * Method for convenient display of results. Where elements of the one face is
	 * containing inside the square brackets.
	 * @return
	 */
	public String toString2() {
		String result = this.name;
		for (Edge edge : this.edges)
			result += " " + edge.toString2();
		return "[" + result + "]";
	}
	
	/**
	 * Method for display only the face's name.
	 */
	public String toString() {
		return this.name;
		}	

	/**
	 * returns the border edge set
	 * @return
	 */
	public Set<Edge> getEdges(){
		return edges;
	}
}
