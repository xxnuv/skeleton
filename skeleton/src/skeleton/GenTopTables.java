package skeleton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.Cell;
import elements.Edge;
import elements.Face;
import elements.Node;
import linereader.LineReader;

/**
 * This class contain all topological tables and instruments for their construction
 * @author rozhk
 *
 */
public class GenTopTables {
	
	//Variables for Node-Element topological tables
	public static Map<String, Node> topNodeNode = LineReader.nodemap;
	public static Map<String, Set<Edge>> topNodeEdge = new HashMap<>();
	public static Map<String, Set<Face>> topNodeFace = new HashMap<>();
	public static Map<String, Set<Cell>> topNodeCell = new HashMap<>();
	
	//Variables for Edge-Element topological tables
	public static Map<String, Set<Node>> topEdgeNode = new HashMap<>();
	public static Map<String, Edge> topEdgeEdge = LineReader.edgemap;
	public static Map<String, Set<Face>> topEdgeFace = new HashMap<>();
	public static Map<String, Set<Cell>> topEdgeCell = new HashMap<>();
	
	//Variables for Face-Element topological tables
	public static Map<String, Set<Node>> topFaceNode = new HashMap<>();
	public static Map<String, Set<Edge>> topFaceEdge = new HashMap<>();
	public static Map<String, Face> topFaceFace = LineReader.facemap;
	public static Map<String, Set<Cell>> topFaceCell = new HashMap<>();
	
	//Variables for Cell-Element topological tables
	public static Map<String, Set<Node>> topCellNode = new HashMap<>();
	public static Map<String, Set<Edge>> topCellEdge = new HashMap<>();
	public static Map<String, Set<Face>> topCellFace = new HashMap<>();
	public static Map<String, Cell> topCellCell = LineReader.cellmap;
	
	public static long methodTime;
	public static int counter = 0;
	public static int operation = 0;
	public static int inner_operation = 0;
	
	public static void generateTables(Map<String, Cell> map) {
		long startTime = System.nanoTime();
		//loop in every cell
		for (Map.Entry<String, Cell> pair : map.entrySet()) {
			counter++;
			Set<Node> cellnodeset = new HashSet<>();//set with found nodes for Cell-Node table
			Set<Edge> celledgeset = new HashSet<>();//set with found edges for Cell-Edge table
			String key = pair.getKey();
			Cell cell = pair.getValue();//reading the cell object itself
			//Cell-Face
			topCellFace.put(cell.name, cell.faces);//adding every cell with itself Set<Face> to topCellFace
			operation++;
			//loop in every face
			for (Face face : cell.faces) {
				counter++;
				Set<Node> facenodeset = new HashSet<>();//set with found nodes for Face-Node table
				celledgeset.addAll(face.edges);//adding every face edgeset to celledgeset for Cell-Edge table
				inner_operation++;
				//Face-Edge
				topFaceEdge.put(face.name, face.edges);//putting every face with itself Set<edge> to topFaceEdge
				operation++;
				//Face-Cell
				if (topFaceCell.containsKey(face.name)) {
					topFaceCell.get(face.name).add(cell);//if topFaceCell contained face.name as key, program will add(value) to Set<Cell> for current face
					operation++;
				}
				else {
					Set<Cell> set = new HashSet<>();//creating set<Cell> for current face
					set.add(cell);//add(cell) to set
					inner_operation++;
					topFaceCell.put(face.name, set);//putting the calculated item to topFaceCell
					operation++;
				}
				//loop in every edge
				for (Edge edge : face.edges) {
					//Node-Cell
					counter++;
					if(topNodeCell.containsKey(edge.node1.name)) {
						topNodeCell.get(edge.node1.name).add(cell);//if topNodeCell contained edge.node1.name, program will add(cell) to Set<Cell> for current node1
						operation++;
					}
					else {
						Set<Cell> set = new HashSet<>();//creating set<Cell> for current node1 
						set.add(cell);//add(cell) to set
						inner_operation++;
						topNodeCell.put(edge.node1.name, set);//putting the calculated item to topNodeCell
						operation++;
					}
					if(topNodeCell.containsKey(edge.node2.name)) {
						topNodeCell.get(edge.node2.name).add(cell);//if topNodeCell contained edge.node2.name, program will add(cell) to Set<Cell> for current node2
						operation++;
					}
					else {
						Set<Cell> set = new HashSet<>();//creating set<Cell> for current node2 
						set.add(cell);//add(cell) to set
						inner_operation++;
						topNodeCell.put(edge.node2.name, set);//putting the calculated item to topNodeCell
						operation++;
					}
					//Cell-Node
					cellnodeset.add(edge.node1);//adding node1 to the set for cellnodeset
					inner_operation++;
					cellnodeset.add(edge.node2);//adding node2 to the set for cellnodeset
					inner_operation++;
					//Node-Face
					if (topNodeFace.containsKey(edge.node1.name)) {
						topNodeFace.get(edge.node1.name).add(face);//if topNodeFace contained edge.node1.name, program will add(face) to Set<Face> for current node1
						operation++;
					}
					else {
						Set<Face> set = new HashSet<>();//creating set<Face> for current node1 
						set.add(face);//add(face) to set
						inner_operation++;
						topNodeFace.put(edge.node1.name, set);//putting the calculated item to topNodeFace
						operation++;
					}
					if (topNodeFace.containsKey(edge.node2.name)) {
						topNodeFace.get(edge.node2.name).add(face);//if topNodeFace contained edge.node2.name, program will add(face) to Set<Face> for current node2
						operation++;
					}
					else {
						Set<Face> set = new HashSet<>();//creating set<Face> for current node2 
						set.add(face);//add(face) to set
						inner_operation++;
						topNodeFace.put(edge.node2.name, set);//putting the calculated item to topNodeFace
						operation++;
					}
					//Face-Node
					facenodeset.add(edge.node1);//adding node1 to the set for facenodeset
					inner_operation++;
					facenodeset.add(edge.node2);//adding node2 to the set for facenodeset
					inner_operation++;
					//Node-Edge
					if (topNodeEdge.containsKey(edge.node1.name)) {
						topNodeEdge.get(edge.node1.name).add(edge);//if topNodeEdge contained edge.node1.name, program will add(edge) to Set<Edge> for current node1
						operation++;
					}
					else {
						Set<Edge> set = new HashSet<>();//creating set<Edge> for current node1
						set.add(edge);//add(edge) to set
						inner_operation++;
						topNodeEdge.put(edge.node1.name, set);//putting the calculated item to topNodeEdge
						operation++;
					}
					if (topNodeEdge.containsKey(edge.node2.name)) {
						topNodeEdge.get(edge.node2.name).add(edge);//if topNodeEdge contained edge.node1.name, program will add(edge) to Set<Edge> for current node2
						operation++;
					}
					else {
						Set<Edge> set = new HashSet<>();//creating set<Edge> for current node2
						set.add(edge);//add(edge) to set
						inner_operation++;
						topNodeEdge.put(edge.node2.name, set);//putting the calculated item to topNodeEdge
						operation++;
					}
					//Edge-Node
					Set<Node> edgenodeset = new HashSet<>();//creating set<Node> for current edge
					edgenodeset.add(edge.node1);//add(edge.node1) to edgenodeset
					inner_operation++;
					edgenodeset.add(edge.node2);//add(edge.node2) to edgenodeset
					inner_operation++;
					topEdgeNode.put(edge.name, edgenodeset);//putting the calculated item to topEdgeNode
					operation++;
					//Edge-Face
					if (topEdgeFace.containsKey(edge.name)) {
						topEdgeFace.get(edge.name).add(face);//if topEdgeFace contained edge.name as key, program will add(face) to Set<Face> for current edge
						operation++;
					}
					else {
						Set<Face> set = new HashSet<>();//creating set<Face> for current edge
						set.add(face);//add(face) to set
						inner_operation++;
						topEdgeFace.put(edge.name, set);//putting the calculated item to topEdgeFace
						operation++;
					}
					//Edge-Cell
					if (topEdgeCell.containsKey(edge.name)) {
						topEdgeCell.get(edge.name).add(cell);//if topEdgeCell contained edge.name as key, program will add(cell) to Set<Cell> for current edge
						operation++;
					}
					else {
						Set<Cell> set = new HashSet<>();//creating set<Cell> for current edge
						set.add(cell);//add(cell) to set
						inner_operation++;
						topEdgeCell.put(edge.name, set);//putting the calculated item to topEdgeCell
						operation++;
					}
				}
				//Face-Node
				topFaceNode.put(face.name, facenodeset);//putting face's name and facenodeset to Face-Node table
				operation++;
			}
			//Cell-Node
			topCellNode.put(cell.name, cellnodeset);//putting cell's name and cellnodeset to Cell-Node table
			operation++;
			//Cell-Edge
			topCellEdge.put(cell.name, celledgeset);//putting cell's name and celledgeset to Cell-Edge table
			operation++;
		}
		
		long endTime = System.nanoTime();
		methodTime = endTime - startTime;
		System.out.println("\nTime for building topological tables: " + GenTopTables.methodTime + " ns");
	}
	
	public static void addNode(String s) {
		s = s.replaceAll(" ", "").replaceAll("\\t", "");// remove all spaces and tabulation
		String[] lines = s.split("\\)|\\(|\\,");
		String name = lines[0];
		LineReader.nodemap.put(name, new Node(name, Double.parseDouble(lines[1]), Double.parseDouble(lines[2]), Double.parseDouble(lines[3])));
	}
	
	public static void addEdge(String s) {
		s = s.replaceAll(" ", "").replaceAll("\\t", "");// remove all spaces and tabulation
		String[] lines = s.split("\\)|\\(|\\,");
		String name = lines[0];
		Node node1 = LineReader.nodemap.get(lines[1]);
		Node node2 = LineReader.nodemap.get(lines[2]);
		LineReader.edgemap.put(name, new Edge(name, node1, node2));
	}
	
	public static void addFace(String s) {
		s = s.replaceAll(" ", "").replaceAll("\\t", "");// remove all spaces and tabulation
		String[] lines = s.split("\\)|\\(|\\,");
		String name = lines[0];
		HashSet<Edge> tmp = new HashSet<>();
		for (int k = 1; k < lines.length; k++) {
			tmp.add(LineReader.edgemap.get(lines[k]));
		}
		LineReader.facemap.put(name, new Face(name, tmp));
	}
	
	public static void addCell(String s) {
		s = s.replaceAll(" ", "").replaceAll("\\t", "");// remove all spaces and tabulation
		String[] lines = s.split("\\)|\\(|\\,");
		String name = lines[0];
		HashSet<Face> tmp = new HashSet<>();
		for (int k = 1; k < lines.length; k++) {
			tmp.add(LineReader.facemap.get(lines[k]));
		}
		LineReader.cellmap.put(name, new Cell(name, tmp));
	}
	
	public static void addInnerCell(String fileName) throws IOException {
		counter = 0;
		inner_operation = 0;
		operation = 0;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = reader.readLine();
		while (line != null) {
			if (line.substring(0, 4).equals("node")) {
				addNode(line.substring(5));
			}
			else if (line.substring(0, 4).equals("edge")) {
				addEdge(line.substring(5));
			}
			else if (line.substring(0, 4).equals("face")) {
				addFace(line.substring(5));
			}
			else if(line.substring(0, 4).equals("cell")) {
				addCell(line.substring(5));
			}
			line = reader.readLine();
		}
		generateTables(LineReader.cellmap);
		Display.printNodeEdgeTable();
		Display.printNodeFaceTable();
		Display.printNodeCellTable();
		Display.printEdgeNodeTable();
		Display.printEdgeFaceTable();
		Display.printEdgeCellTable();
		Display.printFaceNodeTable();
		Display.printFaceEdgeTable();
		Display.printFaceCellTable();
		Display.printCellNodeTable();
		Display.printCellEdgeTable();
		Display.printCellFaceTable();
		System.out.println("counter " + counter);
		System.out.println("inner operations " + inner_operation);
		System.out.println("operations " + operation);
	}
}
