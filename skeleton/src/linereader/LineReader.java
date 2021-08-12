package linereader;

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
		else return false;
	}
	
	/**
	 * This method determines which object will be created after reading the line.
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
		 
		 else if(lines[0].equals("edge")) {
			 String name = lines[1]; //lines[1] contains the edge's name
			 String nameNode1 = lines[2]; //lines[2] contains the node1 name
			 Node node1 = nodemap.get(nameNode1); //search for a node1 by its name in a nodeset
			 String nameNode2 = lines[3]; //lines[3] contains the node2 name
			 Node node2 = nodemap.get(nameNode2); //search for a node2 by its name in a nodeset
			 edgemap.put(name, new Edge(name, node1, node2));//putting the read item into a edgemap, key = name : value = object
		 }
		 
		 else if(lines[0].equals("face")) {
			 String name = lines[1]; //lines[1] contains the face's name
			 Set<Edge> edges = new HashSet<>();//creating an set into which we will write the reading edges
			 //loop for reading edges
			 for (int k = 2; k < lines.length; k++) {
				//adding a reading edge to a set, before adding program is searching the edge into the edgemap by edge's name 
				 edges.add(edgemap.get(lines[k]));
			 }
			 facemap.put(name, new Face(name, edges));//putting the read item into a facemap, key = name : value = object
		 }
		 
		 else if(lines[0].equals("cell")) {
			 String name = lines[1];//lines[1] contains the cell's name
			 Set<Face> faces = new HashSet<>();//creating an set into which we will write the reading faces
			 for (int k = 2; k < lines.length; k++) {
				//adding a reading face to a set, before adding program is searching the face into the facemap by edge's name 
				 faces.add(facemap.get(lines[k]));
			 }
			 cellmap.put(name, new Cell(name, faces));//putting the read item into a cellmap, key = name : value = object		 
		 }
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
