package elements;

/**
 * An edge has two nodes as parameters. In the second case we can use nodes names as parameters
 * then during implementation program, the application will be searching the existing node with
 * the given name in nodeset.
 * @author rozhk
 *
 */
public class Edge {
	
	public String name;
	public Node node1, node2;
	public String nameNode1, nameNode2;
	
	/**
	 * Create Edge with defined input nodes names.
	 * @param name
	 * @param node1
	 * @param node2
	 */
	public Edge(String name, Node node1, Node node2) {
		this.name = name;
		this.node1 = node1;
		this.node2 = node2;
	}

	/**
	 * Create Edge with defined input nodes names.
	 * @param name
	 * @param nameNode1
	 * @param nameNode2
	 */
	public Edge(String name, String nameNode1, String nameNode2) {
		this.name = name;
		this.nameNode1 = nameNode1;
		this.nameNode2 = nameNode2;
	}
	
	/**
	 * Method for convenient display of results. Where elements of the one edge is
	 * containing inside the "{}" brackets.
	 * @return
	 */
	public String toString2() {
		return "{" + this.name + " " + this.node1.toString2() + " " + this.node2.toString2() + "}";
	}
	
	/**
	 * Method for display only the edge's name.
	 */
	public String toString() {
		return this.name;
	}
}
