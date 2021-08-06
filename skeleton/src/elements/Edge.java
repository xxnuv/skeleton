package elements;

import java.util.Set;

public class Edge {
	
	public String name;
	public Node node1;
	public Node node2;
	public String nameNode1, nameNode2;
	
	public Edge(String name, Node node1, Node node2) {
		this.name = name;
		this.node1 = node1;
		this.node2 = node2;
	}

	public Edge(String name, String nameNode1, String nameNode2) {
		this.name = name;
		this.nameNode1 = nameNode1;
		this.nameNode2 = nameNode2;
	}
	
	
	public String toString2() {
		return "{" + this.name + " " + this.node1.toString2() + " " + this.node2.toString2() + "}";
	}
	
	public String toString() {
		return this.name;
	}
}
