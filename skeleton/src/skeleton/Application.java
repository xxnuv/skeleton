package skeleton;

import linereader.LineReader;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.Cell;
import elements.Edge;
import elements.Face;
import elements.Node;

/**
 * The application class is used to run against data file. This class contains methods for
 * displaying calculated topological tables (cell:edge, cell:node, face:node)
 * @author rozhk
 *
 */
public class Application {
	
	public static String dataFileName = "the_cube_main";

	public static void main(String[] args) {
		//setup full path to the file with construction commands
		String runDirectory = System.getProperty("user.dir");
		String fileName = runDirectory.concat(File.separator + "testData" + File.separator + dataFileName);
		
		// create an instance of CommandLineReader to read the commands from the file
		LineReader linereader = new LineReader(fileName);
		
		// loop to read a file
		while (linereader.hasNext()) {
			linereader.read();
		}
		
		linereader.close(); //On this step lineReader closing the file.
		
		//section for displaying readed nodes
		System.out.println("List of nodes");
		//read the nodemap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Node> pair : linereader.nodemap.entrySet())
		{
			String key = pair.getKey();
			Node value = pair.getValue();
			System.out.println(key + ": " + value.toString2());
		}
		
		//section for displaying readed edges
		System.out.println("\nList of edges");
		//read the edgemap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Edge> pair1 : linereader.edgemap.entrySet())
		{
			String key = pair1.getKey();
			Edge value = pair1.getValue();
			System.out.println(key + ": " + value.toString2());
		}
		
		//section for displaying readed facees
		System.out.println("\nList of faces");
		//read the facemap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Face> pair2 : linereader.facemap.entrySet())
		{
			String key = pair2.getKey();
			Face value = pair2.getValue();
			System.out.println(key + ": " + value.toString2());
		}
		
		//section for displaying readed cells
		System.out.println("\nList of cells");
		//read the cellmap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Cell> pair3 : linereader.cellmap.entrySet())
		{
			String key = pair3.getKey();
			Cell value = pair3.getValue();
			System.out.println(key + ": " + value.toString2());
		}
		PrintCellEdgeTable(linereader.cellmap); //printing calculated cell:edge topological table
		PrintCellNodeTable(linereader.cellmap); //printing calculated cell:node topological table
		PrintFaceNodeTable(linereader.facemap); //printing calculated face:node topological table
	}
	
	/**
	 * method for printing cell : edge topology table
	 * @param map
	 */
	public static void PrintCellEdgeTable(Map<String, Cell> map) {
		//Calculating cell : edge topological table
		Map<String, Set<Edge>> tmp = new HashMap<>();//creating the map(key = name : value = set of edges)
		// loop in every cell
		for (Map.Entry<String, Cell> pair : map.entrySet()) {
			Set<Edge> set = new HashSet<>();//set with found edges
			String key = pair.getKey();//reading the cell's name
			Cell value = pair.getValue();//reading the cell object itself
			//Cell has attributes - it is set of faces
			//loop in every face
			for (Face face : value.faces) {
				//Face has attributes - it is set of edges.
				set.addAll(face.edges);//Let's add this edgeset to the set for topological table
			}
			tmp.put(key, set);//putting the calculated item into a map, key = cell's name : value = set of edges
		}
		
		//printing map as topological table (key = cell's name : value = set of edges)
		System.out.println("\nCell-Edge table");
		for (Map.Entry<String, Set<Edge>> pair : tmp.entrySet())
		{
			String key = pair.getKey();
			Set<Edge> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	/**
	 * method for printing cell : node topology table
	 * @param map
	 */
	public static void PrintCellNodeTable(Map<String, Cell> map) {
		//Calculating cell : node topological table
		Map<String, Set<Node>> tmp = new HashMap<>();//creating the map(key = name : value = set of nodes)
		// loop in every cell
		for (Map.Entry<String, Cell> pair : map.entrySet()) {
			Set<Node> set = new HashSet<>();//set with found nodes
			String key = pair.getKey();//reading the cell's name
			Cell value = pair.getValue();//reading the cell object itself
			//Cell has attributes - it is set of faces
			//loop in every face
			for (Face face : value.faces) {
				//Face has attributes - it is set of edges.
				//loop in every edge
				for (Edge edge : face.edges) {
					//Edge has a node itself as attributes
					set.add(edge.node1);//adding node1 to the set for cell
					set.add(edge.node2);//adding node2 to the set for cell
				}
			}
			tmp.put(key, set); //putting the calculated item into a map, key = cell's name : value = set of nodes
		}
		
		//printing map as topological table (key = cell's name : value = set of nodes)
		System.out.println("\nCell-Node table:");
		for (Map.Entry<String, Set<Node>> pair : tmp.entrySet())
		{
			String key = pair.getKey();
			Set<Node> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	/**
	 * method for printing face : node topology table
	 * @param map
	 */
	public static void PrintFaceNodeTable(Map<String, Face> map) {
		//Calculating face : node topological table
		Map<String, Set<Node>> tmp = new HashMap<>();//creating the map(key = name : value = set of nodes)
		//loop in every face
		for (Map.Entry<String, Face> pair : map.entrySet()) {
			Set<Node> set = new HashSet<>(); //set with found nodes
			String key = pair.getKey(); //reading the face's name
			Face value = pair.getValue();//reading the face object itself
			//Face has attributes - it is set of edges.
			//loop in every edge
			for (Edge edge : value.edges) {
				//Edge has a node itself as attributes
				set.add(edge.node1);//adding node1 to the set for face
				set.add(edge.node2);//adding node2 to the set for face
			}
			tmp.put(key, set);//putting the calculated item into a map, key = face's name : value = set of nodes
		}
		
		//printing map as topological table (key = face's name : value = set of nodes)
		System.out.println("\nFace-Node table:");
		for (Map.Entry<String, Set<Node>> pair : tmp.entrySet())
		{
			String key = pair.getKey();
			Set<Node> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}

}
