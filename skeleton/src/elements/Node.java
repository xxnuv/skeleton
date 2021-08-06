package elements;

public class Node {
	
	public String name;
	public double x;
	public double y;
	public double z;

	public Node(String name, double x, double y, double z) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public String toString2() {
		return "(" + this.name + " " + this.x + " " + this.y + " " + this.z + ")";
	}
	
	public String toString() {
		return this.name;
		}	
}
