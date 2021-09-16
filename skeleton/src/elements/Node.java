package elements;

/**
 * There is the node with names and coordinates as parameters. In this case
 * we are working with position node. The geometric attributes of a position 
 * node are the global coordinates of its position in space.
 * A position node is shown graphically at its location in space.
 * @author rozhk
 *
 */
public class Node implements Comparable<Node> {
	
	public String name;
	public double x;
	public double y;
	public double z;

	/**
	 * Creates note
	 * @param name
	 * @param x - first coordinate of the node
	 * @param y - second coordinate of the node
	 * @param z - third coordinate of the node
	 */
	public Node(String name, double x, double y, double z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Method for convenient display of results. Where elements of the one node is
	 * containing inside the round brackets.
	 * @return
	 */
	public String toString2() {
		return "(" + this.name + " " + this.x + " " + this.y + " " + this.z + ")";
	}
	
	/**
	 * Method for display only the node's name.
	 */
	public String toString() {
		return this.name;
		}

	@Override
	public int compareTo(Node o) {
		// TODO Auto-generated method stub
		int t1 = Integer.parseInt(this.name.substring(1));
		int t2 = Integer.parseInt(o.name.substring(1));
		return t1 > t2 ? 1 :  t1 == t2 ? 0 : -1;
	}	
}
