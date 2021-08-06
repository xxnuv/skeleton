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

public class LineReader {
	
	BufferedReader reader;
	private String line;
	public static Map<String, Node> nodemap = new HashMap<>();
	public static Map<String, Edge> edgemap = new HashMap<>();
	public static Map<String, Face> facemap = new HashMap<>();
	public static Map<String, Cell> cellmap = new HashMap<>();
	
	
	public LineReader(String filename) {
		try {
			reader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public void read() {
		String currentLine = line;
		currentLine = currentLine.replaceAll(" ", "");// remove all spaces
		// Create an array of strings from the original string using delimiters:
		// ")","(",","
		String[] lines = currentLine.split("\\)|\\(|\\,");
		 if (lines[0].equals("node")) {
			 String name = lines[1];
			 Double x1 = Double.parseDouble(lines[2]);
			 Double x2 = Double.parseDouble(lines[3]);
			 Double x3 = Double.parseDouble(lines[4]);
			 nodemap.put(name, new Node(name, x1, x2, x3));
		 }
		 
		 else if(lines[0].equals("edge")) {
			 String name = lines[1];
			 //Thinking about transformation between String and Node. Maybe Map
			 String nameNode1 = lines[2];
			 Node node1 = nodemap.get(nameNode1);
			 String nameNode2 = lines[3];
			 Node node2 = nodemap.get(nameNode2);
			 edgemap.put(name, new Edge(name, node1, node2));
		 }
		 
		 else if(lines[0].equals("face")) {
			 String name = lines[1];
			 Set<Edge> edges = new HashSet<>();
			 for (int k = 2; k < lines.length; k++) {
				 edges.add(edgemap.get(lines[k]));
			 }
			 facemap.put(name, new Face(name, edges));			 
		 }
		 
		 else if(lines[0].equals("cell")) {
			 String name = lines[1];
			 Set<Face> faces = new HashSet<>();
			 for (int k = 2; k < lines.length; k++) {
				 faces.add(facemap.get(lines[k]));
			 }
			 cellmap.put(name, new Cell(name, faces));
		 }
	}
	public void close() {
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
