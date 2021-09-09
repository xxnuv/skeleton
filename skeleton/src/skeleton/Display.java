package skeleton;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.Cell;
import elements.Edge;
import elements.Face;
import elements.Node;

/**
 * Method for displaying tables
 * @author rozhk
 *
 */
public class Display {
	
	/*
	 * Method for for displaying read nodes
	 */
	public static void printNode(Map<String, Node> map) {
		//section for displaying read nodes
		System.out.println("List of nodes");
		//read the nodemap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Node> pair : map.entrySet())
		{
			String key = pair.getKey();
			Node value = pair.getValue();
			System.out.println(key + ": " + value.toString2());
		}
	}
	
	/*
	 * Method for displaying read edges
	 */
	public static void printEdge(Map<String, Edge> map) {
		//section for displaying read edges
		System.out.println("\nList of edges");
		//read the edgemap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Edge> pair1 : map.entrySet())
		{
			String key = pair1.getKey();
			Edge value = pair1.getValue();
			System.out.println(key + ": " + value.toString2());
		}
	}
	
	/*
	 * Method for displaying read faces
	 */
	public static void printFace(Map<String, Face> map) {
		//section for displaying read faces
		System.out.println("\nList of faces");
		//read the facemap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Face> pair2 : map.entrySet())
		{
			String key = pair2.getKey();
			Face value = pair2.getValue();
			System.out.println(key + ": " + value.toString2());
		}
	}
	
	/*
	 * section for displaying read cells
	 */
	public static void printCell(Map<String, Cell> map) {
		//section for displaying read cells
		System.out.println("\nList of cells");
		//read the cellmap that is created after reading the file
		//printing this map as key : value
		for (Map.Entry<String, Cell> pair3 : map.entrySet())
		{
			String key = pair3.getKey();
			Cell value = pair3.getValue();
			System.out.println(key + ": " + value.toString2());
		}
	}
	
	
	//!!!!!!!!!!section where node as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing node : node topology table
	 * @param map
	 */
	public static void printNodeNodeTable(Map<String, Node> map) {
		//Calculating node : node topological table
		System.out.println("\nNode-Node table");
		//read the nodemap that is created after reading the file
		//printing this map as key : value
		for(Map.Entry<String, Node> pair : map.entrySet()) {
			String key = pair.getKey();
			Node value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	/**
	 * method for printing node : edge topology table
	 * @param map
	 */
	public static void printNodeEdgeTable(Map<String, Edge> map) {
		//Calculating node : edge topological table
		Map<String, Set<Edge>> tmp = new HashMap<>(); //creating the map(key = name : value = set of edges)
		// loop in every edge
		for (Map.Entry<String, Edge> pair : map.entrySet()) {
			Edge value = pair.getValue(); //reading the edge object itself
			if (tmp.containsKey(value.node1.name)) {  //if tmp contained value.node1.name
				tmp.get(value.node1.name).add(value); //program will add(value) to Set<Edge> for current node1
			}
			else {
				Set<Edge> set = new HashSet<>(); //creating set<Edge> for current node1
				set.add(value);					 //add(value) to set
				tmp.put(value.node1.name, set);	 //putting the calculated item into a map, key = node's name : value = set of edges		
			}
			if (tmp.containsKey(value.node2.name)) {  //if tmp contained value.node2.name
				tmp.get(value.node2.name).add(value); //program wiil add(value) to Set<Edge> for current node2
			}
			else {
				Set<Edge> set = new HashSet<>(); //creating set<Edge> for current node2
				set.add(value);					 //add(value) to set
				tmp.put(value.node2.name, set);  //putting the calculated item into a map, key = node's name : value = set of edges					
			}
		}
		//printing map as topological table (key = node's name : value = set of edges)
		System.out.println("\nNode-Edge table");
		for(Map.Entry<String, Set<Edge>> couple : tmp.entrySet()) {
			String key = couple.getKey();
			Set<Edge> value = couple.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	/**
	 * method for printing node : face topology table
	 * @param map
	 */
	public static void printNodeFaceTable(Map<String, Face> map) {
		//Calculating node : face topological table
		Map<String, Set<Face>> tmp = new HashMap<>(); //creating the map(key = name : value = set of faces)
		//loop in every face
		for (Map.Entry<String, Face> pair : map.entrySet()) {
			Face value = pair.getValue(); //reading the face object itself
			//loop in every edge
			for(Edge edge : value.edges) {
				//if tmp contained value.node1.name, program will add(value) to Set<Face> for current node1
				if(tmp.containsKey(edge.node1.name)) tmp.get(edge.node1.name).add(value);
				else {
					Set<Face> set = new HashSet<>(); //creating set<Face> for current node1 
					set.add(value);					 //add(value) to set
					tmp.put(edge.node1.name, set);   //putting the calculated item into a map, key = node's name : value = set of faces
				}
				//if tmp contained value.node2.name, program will add(value) to Set<Face> for current node2
				if(tmp.containsKey(edge.node2.name)) tmp.get(edge.node2.name).add(value);
				else {
					Set<Face> set = new HashSet<>(); //creating set<Face> for current node1 
					set.add(value);					 //add(value) to set
					tmp.put(edge.node2.name, set);   //putting the calculated item into a map, key = node's name : value = set of faces
				}
			}
		}
		//printing map as topological table (key = node's name : value = set of faces)
		System.out.println("\nNode-Face table");
		for(Map.Entry<String, Set<Face>> couple : tmp.entrySet()) {
			String key = couple.getKey();
			Set<Face> value = couple.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	/**
	 * method for printing node : cell topology table
	 * @param map
	 */
	public static void printNodeCellTable(Map<String, Cell> map) {
		//Calculating node : cell topological table
		Map<String, Set<Cell>> tmp = new HashMap<>(); //creating the map(key = name : value = set of cells)
		//loop in every cell
		for (Map.Entry<String, Cell> pair : map.entrySet()) {
			Cell value = pair.getValue(); //reading the cell object itself
			//loop in every face
			for(Face face : value.faces) {
				//loop in every edge
				for(Edge edge : face.edges) {
					//if tmp contained value.node1.name, program will add(value) to Set<Cell> for current node1
					if(tmp.containsKey(edge.node1.name)) tmp.get(edge.node1.name).add(value);
					else {
						Set<Cell> set = new HashSet<>(); //creating set<Cell> for current node1 
						set.add(value);					 //add(value) to set
						tmp.put(edge.node1.name, set);   //putting the calculated item into a map, key = node's name : value = set of cells
					}
					//if tmp contained value.node2.name, program will add(value) to Set<Cell> for current node2
					if(tmp.containsKey(edge.node2.name)) tmp.get(edge.node2.name).add(value);
					else {
						Set<Cell> set = new HashSet<>(); //creating set<Cell> for current node2 
						set.add(value);					 //add(value) to set
						tmp.put(edge.node2.name, set);   //putting the calculated item into a map, key = node's name : value = set of cells
					}
				}
			}
		}
		//printing map as topological table (key = node's name : value = set of cells)
		System.out.println("\nNode-Cell table");
		for(Map.Entry<String, Set<Cell>> couple : tmp.entrySet()) {
			String key = couple.getKey();
			Set<Cell> value = couple.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	
	//!!!!!!!!!!section where edge as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing edge : node topology table
	 * @param map
	 */
	public static void printEdgeNodeTable(Map<String, Edge> map) {
		//Calculating edge : node topological table
		Map<String, Set<Node>> tmp = new HashMap<>(); //creating the map(key = name : value = set of nodes)
		//loop in every edge
		for(Map.Entry<String, Edge> pair : map.entrySet()) {
			Edge value = pair.getValue(); 	//reading the edge object itself
			Set<Node> set = new HashSet<>();//creating set<Node> for current edge
			set.add(value.node1);			//add(value.node1) to set
			set.add(value.node2);			//add(value.node2) to set
			tmp.put(value.name, set);		//putting the calculated item into a map, key = edge's name : value = set of nodes
		}
		//printing map as topological table (key = edge's name : value = set of nodes)
		System.out.println("\nEdge-Node table");
		for(Map.Entry<String, Set<Node>> couple : tmp.entrySet()) {
			String key = couple.getKey();
			Set<Node> value = couple.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	/**
	 * method for printing edge : edge topology table
	 * @param map
	 */
	public static void printEdgeEdgeTable(Map<String, Edge> map) {
		//Calculating edge : edge topological table
		System.out.println("\nEdge-Edge table");
		//printing map as topological table (key = edge's name : value = edge itself)
		for(Map.Entry<String, Edge> pair : map.entrySet()) {
			String key = pair.getKey();
			Edge value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	/**
	 * method for printing edge : face topology table
	 * @param map
	 */
	public static void printEdgeFaceTable(Map<String, Face> map) {
		//Calculating edge : face topological table
		Map<String, Set<Face>> tmp = new HashMap<>(); //creating the map(key = name : value = set of faces)
		//loop in every face
		for(Map.Entry<String, Face> pair : map.entrySet()) {
			Face value = pair.getValue(); //reading the face object itself
			//loop in every edge
			for(Edge edge : value.edges) {
				//if tmp contained edge.name as key, program will add(value) to Set<Face> for current edge
				if(tmp.containsKey(edge.name)) tmp.get(edge.name).add(value);
				else {
					Set<Face> set = new HashSet<>();//creating set<Face> for current edge
					set.add(value);					//add(value) to set
					tmp.put(edge.name, set);		//putting the calculated item into a map, key = edge's name : value = set of faces
				}
			}
		}
		//printing map as topological table (key = edge's name : value = set of faces)
		System.out.println("\nEdge-Face table");
		for(Map.Entry<String, Set<Face>> pair : tmp.entrySet()) {
			String key = pair.getKey();
			Set<Face> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	/**
	 * method for printing edge : cell topology table
	 * @param map
	 */
	public static void printEdgeCellTable(Map<String, Cell> map) {
		//Calculating edge : cell topological table
		Map<String, Set<Cell>> tmp = new HashMap<>(); //creating the map(key = name : value = set of cells)
		//loop in every cell
		for(Map.Entry<String, Cell> pair : map.entrySet()) {
			Cell value = pair.getValue(); //reading the cell object itself
			//loop in every face
			for(Face face : value.faces) {
				//loop in every edge
				for(Edge edge : face.edges) {
					//if tmp contained edge.name as key, program will add(value) to Set<Cell> for current edge
					if(tmp.containsKey(edge.name)) tmp.get(edge.name).add(value);
					else {
						Set<Cell> set = new HashSet<>();//creating set<Cell> for current edge
						set.add(value);					//add(value) to set
						tmp.put(edge.name, set);		//putting the calculated item into a map, key = edge's name : value = set of cells
					}
				}
			}
		}
		//printing map as topological table (key = edge's name : value = set of cells)
		System.out.println("\nEdge-Cell table");
		for(Map.Entry<String, Set<Cell>> pair : tmp.entrySet()) {
			String key = pair.getKey();
			Set<Cell> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	
	//!!!!!!!!!!section where face as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing face : node topology table
	 * @param map
	 */
	public static void printFaceNodeTable(Map<String, Face> map) {
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
	/**
	 * method for printing face : edge topology table
	 * @param map
	 */
	public static void printFaceEdgeTable(Map<String, Face> map) {
		//Calculating face : edge topological table
		System.out.println("\nFace-Edge table:");
		//printing map as topological table (key = face's name : value = set of edges)
		//Every face has set of edges as parameter
		for(Map.Entry<String, Face> pair : map.entrySet()) {
			Face value = pair.getValue(); //reading the face object itself
			System.out.println(value.name + ": " + value.edges);
		}
	}
	/**
	 * method for printing face : face topology table
	 * @param map
	 */
	public static void printFaceFaceTable(Map<String, Face> map) {
		//Calculating face : face topological table
		System.out.println("\nFace-Face table:");
		//printing map as topological table (key = face's name : value = face itself)
		for(Map.Entry<String, Face> pair : map.entrySet()) {
			String key = pair.getKey();
			Face value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	/**
	 * method for printing face : cell topology table
	 * @param map
	 */
	public static void printFaceCellTable(Map<String, Cell> map) {
		//Calculating face : cell topological table
		Map<String, Set<Cell>> tmp = new HashMap<>(); //creating the map(key = name : value = set of cells)
		//loop in every cell
		for(Map.Entry<String, Cell> pair : map.entrySet()) {
			Cell value = pair.getValue();//reading the cell object itself
			//loop in every face
			for(Face face : value.faces) {
				//if tmp contained face.name as key, program will add(value) to Set<Cell> for current face
				if(tmp.containsKey(face.name)) tmp.get(face.name).add(value);
				else {
					Set<Cell> set = new HashSet<>();//creating set<Cell> for current face
					set.add(value);					//add(value) to set
					tmp.put(face.name, set);		//putting the calculated item into a map, key = face's name : value = set of cells
				}
			}
		}
		//printing map as topological table (key = face's name : value = set of cells)
		System.out.println("\nFace-Cell table");
		for(Map.Entry<String, Set<Cell>> pair : tmp.entrySet()) {
			String key = pair.getKey();
			Set<Cell> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	
	//!!!!!!!!!!section where cell as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing cell : node topology table
	 * @param map
	 */
	public static void printCellNodeTable(Map<String, Cell> map) {
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
	 * method for printing cell : edge topology table
	 * @param map
	 */
	public static void printCellEdgeTable(Map<String, Cell> map) {
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
	 * method for printing cell : face topology table
	 * @param map
	 */
	public static void printCellFaceTable(Map<String, Cell> map) {
		//Calculating cell : face topological table
		System.out.println("\nCell-Face table");
		//printing map as topological table (key = cell's name : value = set of faces)
		//Every cell has set of faces as parameter
		for(Map.Entry<String, Cell> pair : map.entrySet()) {
			Cell value = pair.getValue(); //reading the cell object itself
			System.out.println(value.name + ": " + value.faces);
		}
	}
	
	/**
	 * method for printing cell : cell topology table
	 * @param map
	 */
	public static void printCellCellTable(Map<String, Cell> map) {
		//Calculating cell : cell topological table
		System.out.println("\nCell-Cell table");
		//printing map as topological table (key = cell's name : value = set of cell)
		for(Map.Entry<String, Cell> pair : map.entrySet()) {
			String key = pair.getKey();
			Cell value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
}
