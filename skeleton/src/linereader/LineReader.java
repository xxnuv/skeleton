package linereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.Cell;
import elements.Edge;
import elements.Face;
import elements.Node;
import elements.shadow.SCell;
import elements.shadow.SEdge;
import elements.shadow.SFace;
import skeleton.Display;

/**
 * An object of this class reads lines from a file. Each line contain parameters of
 * new object, during reading the file the lineReader is making needed object(node, 
 * edge, face, cell). In this version of the class, the file must contain descriptions 
 * of objects, starting with the lowest rank.
 * @author rozhk
 *
 */
public class LineReader {
	
	BufferedReader reader;
	private String line;
	/*
	 * Every map contains the name of object as key, and the object itself as value.
	 */
	public static Map<String, Node> nodemap = new HashMap<>();
	public static Map<String, Edge> edgemap = new HashMap<>();
	public static Map<String, Face> facemap = new HashMap<>();
	public static Map<String, Cell> cellmap = new HashMap<>();
	
	//Shadows objects sets
	public static Set <SEdge> edgeSet = new HashSet<>();
	public static Set <SFace> faceSet = new HashSet<>();
	public static Set <SCell> cellSet = new HashSet<>();
	
	//contains commands for constructing topological tables
	public static ArrayList<String> construct = new ArrayList<>();
	
	/**
	 * Creating new lineReader. This version doesn't have error handling logic.
	 * If file just did't exist, program will print the stacktrace.
	 * @param filename
	 */
	public LineReader(String filename) {
		try {
			// Using Buffered reader class, which is inherited from abstract
			// class Reader and have readLine() method. To open a file via FileReader
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method hasNext() calls readLines() from BufferedReader. Method saves reading
	 * line to variable Line. Method hasNext() will return false, if currentLine 
	 * contain nothing(null).
	 * @return
	 */
	public boolean hasNext() {
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (line != null) return true;
		else {
			/*
			 * When file has read, hasNext calls shadowSearch. ShadowSearch is
			 * constructing edgemap, facemap, cellmap using shadows structures.
			 */
			shadowSearch(edgeSet, faceSet, cellSet);
			//construction tables
			for(String command : construct) {
				switch (command) {
				case "nodenode": Display.printNodeNodeTable(nodemap);
					break;
				case "nodeedge": Display.printNodeEdgeTable(edgemap);
					break;
				case "nodeface": Display.printNodeFaceTable(facemap);
					break;
				case "nodecell": Display.printNodeCellTable(cellmap);
					break;
				case "edgenode": Display.printEdgeNodeTable(edgemap);
					break;
				case "edgeedge": Display.printEdgeEdgeTable(edgemap);
					break;
				case "edgeface": Display.printEdgeFaceTable(facemap);
					break;
				case "edgecell": Display.printEdgeCellTable(cellmap);
					break;
				case "facenode": Display.printFaceNodeTable(facemap);
					break;
				case "faceedge": Display.printFaceEdgeTable(facemap);
					break;
				case "faceface": Display.printFaceFaceTable(facemap);
					break;
				case "facecell": Display.printFaceCellTable(cellmap);
					break;
				case "cellnode": Display.printCellNodeTable(cellmap);
					break;
				case "celledge": Display.printCellEdgeTable(cellmap);
					break;
				case "cellface": Display.printCellFaceTable(cellmap);
					break;
				case "cellcell": Display.printCellCellTable(cellmap);
					break;
				default:
					System.out.println("syntax error in command arguments");
					break;
				}
			}
			return false;
		}
	}
		
	/**
	 * This method determines which object will be created after reading the line with using the Shadow's Object.
	 */
	public void read() {
		String currentLine = line;
		currentLine = currentLine.replaceAll(" ", "");// remove all spaces
		// Create an array of strings from the original string using delimiters:
		// ")","(",","
		// if first element from array contain command-word: node, edge, face, cell
		String[] lines = currentLine.split("\\)|\\(|\\,");
		if (lines[0].equals("node")) {
			 String name = lines[1]; //lines[1] contains the node's name
			 Double x1 = Double.parseDouble(lines[2]); //lines[2] contains the node's coord
			 Double x2 = Double.parseDouble(lines[3]); //lines[3] contains the node's coord
			 Double x3 = Double.parseDouble(lines[4]); //lines[4] contains the node's coord
			 nodemap.put(name, new Node(name, x1, x2, x3));//putting the read item into a nodemap, key = name : value = object
		}
		else if (lines[0].equals("edge")) edgeSet.add(new SEdge(lines[1], lines[2], lines[3]));//put the new Shadow Edge to edgeSet
		else if (lines[0].equals("face")) {
			Set<String> namesEdge = new HashSet<>();//Set with edges names
			for (int k = 2; k < lines.length; k++) {
				namesEdge.add(lines[k]);//adding a reading edge to a set
			}
			faceSet.add(new SFace(lines[1], namesEdge)); //put the new Shadow Face to faceSet
		}
		else if (lines[0].equals("cell")) {
			Set<String> namesFace = new HashSet<>(); //Set with faces names
			for (int k = 2; k < lines.length; k++) {
				namesFace.add(lines[k]); //adding a reading face to a set
			}
			cellSet.add(new SCell(lines[1], namesFace)); //put the new Shadow Cell to cellSet
		}
		else if (lines[0].equals("table")) {
			String instruction = lines[1]+lines[2];
			construct.add(instruction);//add instruction to ArrayList for constructing topological tables
		}
	}
	/**
	 * This method is searching real elements using shadows objects set and constructing edgemap, facemap, cellmap.
	 * 
	 * @param edge - set of shadows edges
	 * @param face - set of shadows faces
	 * @param cell - set of shadows cells
	 */
	public void shadowSearch(Set<SEdge> edge, Set<SFace> face, Set<SCell> cell) {
		//loop in the edgeSet
		for (SEdge shadow : edge) {
			String edgeName = shadow.edgeName; //reading the object's name
			Node node1 = nodemap.get(shadow.nodeName1); //searching node in the nodemap using the node's name
			Node node2 = nodemap.get(shadow.nodeName2); //searching node in the nodemap using the node's name
			edgemap.put(edgeName, new Edge(edgeName, node1, node2)); //putting the item into a edgemap, key = name : value = object
		}
		//loop in the faceSet
		for (SFace shadow : face) {
			String faceName = shadow.faceName; //reading the object's name
			Set<Edge> edgetmp = new HashSet<>(); //Set with real edges
			for (String line : shadow.setNames) {
				//searching edge in edgemap using the edge's name, after object would have found - program would add edge to set.
				edgetmp.add(edgemap.get(line));
			}
			facemap.put(faceName, new Face(faceName, edgetmp)); //putting the item into a facemap, key = name : value = object
		}
		//loop in the cellSet
		for (SCell shadow : cell) {
			String cellName = shadow.cellName; //reading the object's name
			Set<Face> facetmp = new HashSet<>(); //Set with real faces
			for (String line : shadow.setNames) {
				//searching face in facemap using the face's name, after object would have found - program would add face to set.
				facetmp.add(facemap.get(line));
			}
			cellmap.put(cellName, new Cell(cellName, facetmp)); //putting the item into a cellmap, key = name : value = object
		}
		edge.clear(); //clearing sets
		face.clear();
		cell.clear();
	}
	
	/**
	 * The method closes lineReader stream
	 */
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
