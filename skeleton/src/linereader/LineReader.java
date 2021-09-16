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
import java.util.TreeMap;
import java.util.TreeSet;

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
	private long startLRTime;;
	/*
	 * Every map contains the name of object as key, and the object itself as value.
	 */
	public static Map<String, Node> nodemap = new TreeMap<>();
	public static Map<String, Edge> edgemap = new TreeMap<>();
	public static Map<String, Face> facemap = new TreeMap<>();
	public static Map<String, Cell> cellmap = new TreeMap<>();
	
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
		startLRTime = System.nanoTime();
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
			
			/*
			 * construction tables
			 * reading commands from ArrayList construct. lines[1] and lines[2] are arguments of table command. Command table means that 
			 * program will construct topological tables of the form lines[1]:lines[2].
			 */
			for(String command : construct) {
				switch (command) {
				case "nodenode": Display.printNodeNodeTable(nodemap);//if lines[1]=="node" and lines[2]=="node", program calls printNodeNodeTable
					break;
				case "nodeedge": Display.printNodeEdgeTable(edgemap);//if lines[1]=="node" and lines[2]=="edge", program calls printNodeEdgeTable
					break;
				case "nodeface": Display.printNodeFaceTable(facemap);//if lines[1]=="node" and lines[2]=="face", program calls printNodeFaceTable
					break;
				case "nodecell": Display.printNodeCellTable(cellmap);//if lines[1]=="node" and lines[2]=="cell", program calls printNodeCellTable
					break;
				case "edgenode": Display.printEdgeNodeTable(edgemap);//if lines[1]=="edge" and lines[2]=="node", program calls printEdgeNodeTable
					break;
				case "edgeedge": Display.printEdgeEdgeTable(edgemap);//if lines[1]=="edge" and lines[2]=="edge", program calls printEdgeEdgeTable
					break;
				case "edgeface": Display.printEdgeFaceTable(facemap);//if lines[1]=="edge" and lines[2]=="face", program calls printEdgeFaceTable
					break;
				case "edgecell": Display.printEdgeCellTable(cellmap);//if lines[1]=="edge" and lines[2]=="cell", program calls printEdgeCellTable
					break;
				case "facenode": Display.printFaceNodeTable(facemap);//if lines[1]=="face" and lines[2]=="node", program calls printFaceNodeTable
					break;
				case "faceedge": Display.printFaceEdgeTable(facemap);//if lines[1]=="face" and lines[2]=="edge", program calls printFaceEdgeTable
					break;
				case "faceface": Display.printFaceFaceTable(facemap);//if lines[1]=="face" and lines[2]=="face", program calls printFaceFaceTable
					break;
				case "facecell": Display.printFaceCellTable(cellmap);//if lines[1]=="face" and lines[2]=="cell", program calls printFaceCellTable
					break;
				case "cellnode": Display.printCellNodeTable(cellmap);//if lines[1]=="cell" and lines[2]=="node", program calls printCellNodeTable
					break;
				case "celledge": Display.printCellEdgeTable(cellmap);//if lines[1]=="cell" and lines[2]=="edge", program calls printCellEdgeTable
					break;
				case "cellface": Display.printCellFaceTable(cellmap);//if lines[1]=="cell" and lines[2]=="face", program calls printCellFaceTable
					break;
				case "cellcell": Display.printCellCellTable(cellmap);//if lines[1]=="cell" and lines[2]=="cell", program calls printCellCellTable
					break;
				default:
					//if lines[1] or lines[2] didn't contain allowed commands("node", "edge", "face", "cell"), LR will give 
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
		else if (lines[0].equals("table")) { //reading command for construction topological tables
			String instruction = lines[1]+lines[2]; //saving commands arguments to String's variable
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
		long startTime = System.nanoTime();
		for (SEdge shadow : edge) {
			String edgeName = shadow.edgeName; //reading the object's name
			Node node1 = nodemap.get(shadow.nodeName1); //searching node in the nodemap using the node's name
			Node node2 = nodemap.get(shadow.nodeName2); //searching node in the nodemap using the node's name
			edgemap.put(edgeName, new Edge(edgeName, node1, node2)); //putting the item into a edgemap, key = name : value = object
		}
		long endTime = System.nanoTime();
		System.out.println("To build a edgemap from shadows edge's set, it was spent " + (endTime - startTime) + " ns");
		//loop in the faceSet
		startTime = System.nanoTime();
		for (SFace shadow : face) {
			String faceName = shadow.faceName; //reading the object's name
			Set<Edge> edgetmp = new TreeSet<>(); //Set with real edges
			for (String line : shadow.setNames) {
				//searching edge in edgemap using the edge's name, after object would have found - program would add edge to set.
				edgetmp.add(edgemap.get(line));
			}
			facemap.put(faceName, new Face(faceName, edgetmp)); //putting the item into a facemap, key = name : value = object
		}
		endTime = System.nanoTime();
		System.out.println("To build a facemap from shadows face's set, it was spent " + (endTime - startTime) + " ns");
		//loop in the cellSet
		startTime = System.nanoTime();
		for (SCell shadow : cell) {
			String cellName = shadow.cellName; //reading the object's name
			Set<Face> facetmp = new TreeSet<>(); //Set with real faces
			for (String line : shadow.setNames) {
				//searching face in facemap using the face's name, after object would have found - program would add face to set.
				facetmp.add(facemap.get(line));
			}
			cellmap.put(cellName, new Cell(cellName, facetmp)); //putting the item into a cellmap, key = name : value = object
		}
		endTime = System.nanoTime();
		System.out.println("To build a facemap from shadows cell's set, it was spent " + (endTime - startTime) + " ns");
		edge.clear(); //clearing sets
		face.clear();
		cell.clear();
	}
	
	/**
	 * The method closes lineReader stream
	 */
	public void close() {
		long endLRTime = System.nanoTime();
		System.out.println("\nTotal time for building maps, reading the input file and printing topological tables: " + (endLRTime - startLRTime) + " ns");
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
