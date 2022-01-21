package linereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import skeleton.GenTopTables;

/**
 * An object of this class reads lines from a file. Each line contain parameters of
 * new object, during reading the file the lineReader is making needed object(node, 
 * edge, face, cell). In this version of the class, the file must contain descriptions 
 * of objects, starting with the lowest rank.
 * @author rozhk
 *
 */
public class LineReader {
	
	public boolean checker; //flag for checking: if was true, program wouldn't print information about time
	BufferedReader reader;
	private String line;
	private long startLRTime;
	public long timeImplementation;
	public static String sessionName;
	public static int doCounter = 0;
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
	
	//contains commands for printing topological tables
	public static ArrayList<String> construct = new ArrayList<>();
	
	private int lineNumber;// the line number
	private List<String> errors;// errors
	List<String> basis = Arrays.asList("node","edge","cell","face");// basis structure elements
	
	
	/**
	 * Creating new lineReader. This version doesn't have error handling logic.
	 * If file just did't exist, program will print the stack-trace.
	 * @param filename
	 */
	public LineReader(String filename) {
		startLRTime = System.nanoTime();
		errors = new ArrayList<>(); //an array for errors
		try {
			// Using Buffered reader class, which is inherited from abstract
			// class Reader and have readLine() method. To open a file via FileReader
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			errors.add("Access to the file failed");
			//e.printStackTrace();
		}catch (NullPointerException e) {
			errors.add("Access to the file failed");
		}
		lineNumber = 0;
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
			errors.add("Error reading line: " + lineNumber);
			//e.printStackTrace();
		}
		if (line != null) {
			lineNumber++;
			return true;
		}
		else if(lineNumber == 0) {
			System.out.println("The empty file");
			return false;
		}
		else if(errors.size() != 0) {
			printErrors();
			return false;
		}
		else return false;
	}
		
	/**
	 * This method determines which object will be created after reading the line with using the Shadow's Object.
	 */
	public void read() {
		String currentLine = line;
		currentLine = currentLine.replaceAll(" ", "").replaceAll("\\t", "");// remove all spaces and tabulation
		// Create an array of strings from the original string using delimiters:
		// ")","(",","
		// if first element from array contain command-word: node, edge, face, cell
		String[] lines = currentLine.split("\\)|\\(|\\,");
		if (lines[0].equals("session")) {
			String[] tmp = line.replaceAll("\\t", "").replaceFirst(" ","").split(",");
			sessionName = tmp[1];
			System.out.println(sessionName);
		}
		else if (lines[0].equals("node")) {
			if (lines.length == 5) {//checking number of arguments 
				String name = lines[1]; //lines[1] contains the node's name
				try {// Checking that x1, x2, x3 are numbers 
					Double x1 = Double.parseDouble(lines[2]); //lines[2] contains the node's coord
					Double x2 = Double.parseDouble(lines[3]); //lines[3] contains the node's coord
					Double x3 = Double.parseDouble(lines[4]); //lines[4] contains the node's coord
					if (nodemap.containsKey(name)) {
						Node tmp = nodemap.get(name);
						tmp.x = x1;
						tmp.y = x2;
						tmp.z = x3;
					}
					else nodemap.put(name, new Node(name, x1, x2, x3));//putting the read item into a nodemap, key = name : value = object
				}catch (NumberFormatException e) {
					errors.add(lineNumber + " :" + currentLine + " : Some arguments isn't number");
				}
			 }
			else errors.add(lineNumber + " :" + currentLine + " : Invalid number of arguments");
		}
		else if (lines[0].equals("edge")) {
			if(lines.length == 4) {//checking number of arguments 
				edgeSet.add(new SEdge(lines[1], lines[2], lines[3]));//put the new Shadow Edge to edgeSet
			}
			else errors.add(lineNumber + " :" + currentLine + " : Invalid number of arguments");
		}
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
		else if (lines[0].equals("do")) {
			//Display.printNode(nodemap);
			/*
			 * When LR see do command, it calls shadowSearch. ShadowSearch is
			 * constructing edgemap, facemap, cellmap using shadows structures.
			 */
			GenTopTables.clearTables();
			shadowSearch(edgeSet, faceSet, cellSet);
			GenTopTables.generateTables(cellmap); //building all topological tables
			edgeSet.clear();
			faceSet.clear();
			cellSet.clear();
		}
		else if (lines[0].equals("end")) {
			//outputTables(); //printing topological tables
		}
		else if (lines[0].equals("remove")) {
			//remove logic
			Set<String> removeSet = new HashSet<>();//Set for removing elements
			for (int i = 1; i < lines.length; i++) removeSet.add(lines[i]);
			removeLogic(removeSet);
			removeSet.clear();
		}
		else if (line.equals("")) {}
		else if (lines[0].equals("table")) { //reading command for construction topological tables
			if (lines.length == 3) {//checking number of arguments 
				if (basis.contains(lines[1]) && basis.contains(lines[2])) {//checking elements(node, edge, face, cell) 
					String instruction = lines[1]+lines[2]; //saving commands arguments to String's variable
					construct.add(instruction);//add instruction to ArrayList for constructing topological tables
				}else errors.add(lineNumber + " :" + currentLine + " : Unknown type of elements for building tables");
			}else errors.add(lineNumber + " :" + currentLine + " : Invalid number of arguments");
		}
		else errors.add(lineNumber + " :" + currentLine + " : Unsupported command");
		outputTables();
		construct.clear();
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
			if (edgemap.containsKey(edgeName)) {
				Edge tmp = edgemap.get(edgeName);
				tmp.node1 = nodemap.get(shadow.nodeName1);
				tmp.node2 = nodemap.get(shadow.nodeName2);
			}
			else {
			Node node1 = nodemap.get(shadow.nodeName1); //searching node in the nodemap using the node's name
			Node node2 = nodemap.get(shadow.nodeName2); //searching node in the nodemap using the node's name
			edgemap.put(edgeName, new Edge(edgeName, node1, node2)); //putting the item into a edgemap, key = name : value = object
			}
		}
		long endTime = System.nanoTime();
		if (!checker) System.out.println("\n" + "To build a edgemap from shadows edge's set, it was spent " + (endTime - startTime) + " ns");
		//loop in the faceSet
		startTime = System.nanoTime();
		for (SFace shadow : face) {
			String faceName = shadow.faceName; //reading the object's name
			Set<Edge> edgetmp = new HashSet<>(); //Set with real edges
			for (String line : shadow.setNames) {
				//searching edge in edgemap using the edge's name, after object would have found - program would add edge to set.
				edgetmp.add(edgemap.get(line));
			}
			if (facemap.containsKey(faceName)) facemap.get(faceName).edges = edgetmp;
			else facemap.put(faceName, new Face(faceName, edgetmp)); //putting the item into a facemap, key = name : value = object
		}
		endTime = System.nanoTime();
		if (!checker) System.out.println("To build a facemap from shadows face's set, it was spent " + (endTime - startTime) + " ns");
		//loop in the cellSet
		startTime = System.nanoTime();
		for (SCell shadow : cell) {
			String cellName = shadow.cellName; //reading the object's name
			Set<Face> facetmp = new HashSet<>(); //Set with real faces
			for (String line : shadow.setNames) {
				//searching face in facemap using the face's name, after object would have found - program would add face to set.
				facetmp.add(facemap.get(line));
			}
			if (cellmap.containsKey(cellName)) cellmap.get(cellName).faces = facetmp;
			else cellmap.put(cellName, new Cell(cellName, facetmp)); //putting the item into a cellmap, key = name : value = object
		}
		endTime = System.nanoTime();
		if (!checker) System.out.println("To build a facemap from shadows cell's set, it was spent " + (endTime - startTime) + " ns");
		edge.clear(); //clearing sets
		face.clear();
		cell.clear();
	}
	
	/**
	 * checking for errors
	 * 
	 * @return true if there is an error, otherwise false
	 */
	public boolean hasErrors() {
		if (errors.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Printing errors
	 */
	public void printErrors() {
		for(String line : errors) System.out.println(line);
	}
	
	/**
	 * The method closes lineReader stream
	 */
	public void close() {
		long endLRTime = System.nanoTime();
		timeImplementation = endLRTime - startLRTime;
		if (errors.size() == 0 & !checker) {
			//System.out.println("\nTime for building topological tables: " + GenTopTables.methodTime + " ns");
			System.out.println("\nTotal time for reading the input file, building shadows maps, calculating topological tables and printing the selected topological tables: " + (timeImplementation) + " ns");
		}
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeLogic(Set<String> tmpSet) {
		for (String line : tmpSet) {
			if (nodemap.containsKey(line)) {
				nodemap.remove(line);
				for (Entry<String, Edge> pair : edgemap.entrySet()) {
					Edge edge = pair.getValue();
					if (edge.node1.equals(nodemap.get(line))) edge.node1 = null;
					else if (edge.node2.equals(nodemap.get(line))) edge.node2 = null;
				}
			}
			else if (edgemap.containsKey(line)) edgemap.remove(line);
			else if (facemap.containsKey(line)) facemap.remove(line);
			else if (cellmap.containsKey(line)) cellmap.remove(line);
			else {
				errors.add("The " + line + " element to be deleted is missing from the system");
				break;
			}
		}
	}
	
	public void removeNode (String nodeName) {
	}
	
	public void outputTables() {
		/*
		 * construction tables
		 * reading commands from ArrayList construct. lines[1] and lines[2] are arguments of table command. Command table means that 
		 * program will construct topological tables of the form lines[1]:lines[2].
		 */
		if (!checker) {
		for(String command : construct) {
			switch (command) {
			case "nodenode": Display.printNodeNodeTable();//if lines[1]=="node" and lines[2]=="node", program calls printNodeNodeTable
				break;
			case "nodeedge": Display.printNodeEdgeTable();//if lines[1]=="node" and lines[2]=="edge", program calls printNodeEdgeTable
				break;
			case "nodeface": Display.printNodeFaceTable();//if lines[1]=="node" and lines[2]=="face", program calls printNodeFaceTable
				break;
			case "nodecell": Display.printNodeCellTable();//if lines[1]=="node" and lines[2]=="cell", program calls printNodeCellTable
				break;
			case "edgenode": Display.printEdgeNodeTable();//if lines[1]=="edge" and lines[2]=="node", program calls printEdgeNodeTable
				break;
			case "edgeedge": Display.printEdgeEdgeTable();//if lines[1]=="edge" and lines[2]=="edge", program calls printEdgeEdgeTable
				break;
			case "edgeface": Display.printEdgeFaceTable();//if lines[1]=="edge" and lines[2]=="face", program calls printEdgeFaceTable
				break;
			case "edgecell": Display.printEdgeCellTable();//if lines[1]=="edge" and lines[2]=="cell", program calls printEdgeCellTable
				break;
			case "facenode": Display.printFaceNodeTable();//if lines[1]=="face" and lines[2]=="node", program calls printFaceNodeTable
				break;
			case "faceedge": Display.printFaceEdgeTable();//if lines[1]=="face" and lines[2]=="edge", program calls printFaceEdgeTable
				break;
			case "faceface": Display.printFaceFaceTable();//if lines[1]=="face" and lines[2]=="face", program calls printFaceFaceTable
				break;
			case "facecell": Display.printFaceCellTable();//if lines[1]=="face" and lines[2]=="cell", program calls printFaceCellTable
				break;
			case "cellnode": Display.printCellNodeTable();//if lines[1]=="cell" and lines[2]=="node", program calls printCellNodeTable
				break;
			case "celledge": Display.printCellEdgeTable();//if lines[1]=="cell" and lines[2]=="edge", program calls printCellEdgeTable
				break;
			case "cellface": Display.printCellFaceTable();//if lines[1]=="cell" and lines[2]=="face", program calls printCellFaceTable
				break;
			case "cellcell": Display.printCellCellTable();//if lines[1]=="cell" and lines[2]=="cell", program calls printCellCellTable
				break;
			default:
				//if lines[1] or lines[2] didn't contain allowed commands("node", "edge", "face", "cell"), LR will give 
				System.out.println("syntax error in command arguments");
				break;
				}
			}
		}
	}
}
