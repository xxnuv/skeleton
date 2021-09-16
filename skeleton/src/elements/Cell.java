package elements;

import java.util.Set;

/**
 * The boundary of a cell is a closed polyhedron with is not self-intersecting.
 * A polyhedron consisting of faces, edges, nodes.
 * @author rozhk
 *
 */
public class Cell implements Comparable<Cell> {
	
	public String name;
	public Set<Face> faces;
	
	/**
	 * CCreates new cell with name and faces
	 * @param name
	 * @param faces - set of faces
	 */
	public Cell(String name, Set<Face> faces) {
		this.name = name;
		this.faces = faces;
	}

	/**
	 * Method for convenient display of results. Where the one cell is
	 * containing all it's inner elements.
	 * @return
	 */
	public String toString2() {
		String result = this.name;
		for (Face face : this.faces)
			result += " " + face.toString2();
		return result;
	}
	
	/**
	 * Method for display only the cell's name.
	 */
	public String toString() {
		return this.name;
		}	

	/**
	 * returns the face set
	 * @return
	 */
	public Set<Face> getFaces() {
		return this.faces;
	}

	@Override
	public int compareTo(Cell o) {
		// TODO Auto-generated method stub
		int t1 = Integer.parseInt(this.name.substring(1));
		int t2 = Integer.parseInt(o.name.substring(1));
		return t1 > t2 ? 1 :  t1 == t2 ? 0 : -1;
	}
}
