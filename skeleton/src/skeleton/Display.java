package skeleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

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
	
	static Comparator<String> compare = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			int t1 = Integer.parseInt(o1.substring(1));
			int t2 = Integer.parseInt(o2.substring(1));
			return t1 > t2 ? 1 :  t1 == t2 ? 0 : -1;
		}
	};
	
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
	public static void printNodeNodeTable() {
		//Printing node : node topological table
		System.out.println("\nNode-Node table");
		//printing this map as key : value
		GenTopTables.topNodeNode.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
	}
	/**
	 * method for printing node : edge topology table
	 * @param map
	 */
	public static void printNodeEdgeTable() {
		//Printing node : edge topological table
		System.out.println("\nNode-Edge table");
		//printing map as topological table (key = node's name : value = set of edges)
		//GenTopTables.topNodeEdge.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topNodeEdge.keySet());//put keys from topNodeEdge to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Edge> list = new ArrayList<>(GenTopTables.topNodeEdge.get(line));//put current values(Set<Edge>) of map to ArrayList<Edge>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);//printing output
		}
	}
	/**
	 * method for printing node : face topology table
	 * @param map
	 */
	public static void printNodeFaceTable() {
		//Printing node : face topological table
		System.out.println("\nNode-Face table");
		//printing map as topological table (key = node's name : value = set of faces)
		//GenTopTables.topNodeFace.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue().stream().sorted()));
		List<String> tmp = new ArrayList<>(GenTopTables.topNodeFace.keySet());//put keys from topNodeFace to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Face> list = new ArrayList<>(GenTopTables.topNodeFace.get(line));//put current values(Set<Face>) of map to ArrayList<Face>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);//printing output
		}
	}
	
	/**
	 * method for printing node : cell topology table
	 * @param map
	 */
	public static void printNodeCellTable() {
		//Printing node : cell topological table
		//printing map as topological table (key = node's name : value = set of cells)
		System.out.println("\nNode-Cell table");
		//GenTopTables.topNodeCell.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topNodeCell.keySet());//put keys from topNodeCell to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Cell> list = new ArrayList<>(GenTopTables.topNodeCell.get(line));//put current values(Set<Cell>) of map to ArrayList<Cell>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	
	
	//!!!!!!!!!!section where edge as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing edge : node topology table
	 * @param map
	 */
	public static void printEdgeNodeTable() {
		//Calculating edge : node topological table
		//printing map as topological table (key = edge's name : value = set of nodes)
		System.out.println("\nEdge-Node table");
		//GenTopTables.topEdgeNode.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topEdgeNode.keySet());//put keys from topEdgeNode to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Node> list = new ArrayList<>(GenTopTables.topEdgeNode.get(line));//put current values(Set<Node>) of map to ArrayList<Node>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	/**
	 * method for printing edge : edge topology table
	 * @param map
	 */
	public static void printEdgeEdgeTable() {
		//Printing edge : edge topological table
		System.out.println("\nEdge-Edge table");
		//printing map as topological table (key = edge's name : value = edge itself)
		GenTopTables.topEdgeEdge.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
	}
	/**
	 * method for printing edge : face topology table
	 * @param map
	 */
	public static void printEdgeFaceTable() {
		//Calculating edge : face topological table
		//printing map as topological table (key = edge's name : value = set of faces)
		System.out.println("\nEdge-Face table");
		//GenTopTables.topEdgeFace.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topEdgeFace.keySet());//put keys from topEdgeFace to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Face> list = new ArrayList<>(GenTopTables.topEdgeFace.get(line));//put current values(Set<Face>) of map to ArrayList<Face>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	/**
	 * method for printing edge : cell topology table
	 * @param map
	 */
	public static void printEdgeCellTable() {
		//Calculating edge : cell topological table
		//printing map as topological table (key = edge's name : value = set of cells)
		System.out.println("\nEdge-Cell table");
		//GenTopTables.topEdgeCell.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topEdgeCell.keySet());//put keys from topEdgeCell to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Cell> list = new ArrayList<>(GenTopTables.topEdgeCell.get(line));//put current values(Set<Cell>) of map to ArrayList<Cell>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	
	
	//!!!!!!!!!!section where face as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing face : node topology table
	 * @param map
	 */
	public static void printFaceNodeTable() {
		//Printing face : node topological table
		//printing map as topological table (key = face's name : value = set of nodes)
		System.out.println("\nFace-Node table:");
		//GenTopTables.topFaceNode.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topFaceNode.keySet());//put keys from topFaceNode to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Node> list = new ArrayList<>(GenTopTables.topFaceNode.get(line));//put current values(Set<Node>) of map to ArrayList<Node>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	/**
	 * method for printing face : edge topology table
	 * @param map
	 */
	public static void printFaceEdgeTable() {
		//Printing face : edge topological table
		System.out.println("\nFace-Edge table:");
		//printing map as topological table (key = face's name : value = set of edges)
		//Every face has set of edges as parameter
		//GenTopTables.topFaceEdge.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topFaceEdge.keySet());//put keys from topFaceEdge to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Edge> list = new ArrayList<>(GenTopTables.topFaceEdge.get(line));//put current values(Set<Edge>) of map to ArrayList<Edge>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	/**
	 * method for printing face : face topology table
	 * @param map
	 */
	public static void printFaceFaceTable() {
		//Printing face : face topological table
		System.out.println("\nFace-Face table:");
		//printing map as topological table (key = face's name : value = face itself)
		GenTopTables.topFaceFace.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
	}
	/**
	 * method for printing face : cell topology table
	 * @param map
	 */
	public static void printFaceCellTable() {
		//Calculating face : cell topological table
		//printing map as topological table (key = face's name : value = set of cells)
		System.out.println("\nFace-Cell table");
		//GenTopTables.topFaceCell.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topFaceCell.keySet());//put keys from topFaceCell to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Cell> list = new ArrayList<>(GenTopTables.topFaceCell.get(line));//put current values(Set<Cell>) of map to ArrayList<Cell>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	
	
	//!!!!!!!!!!section where cell as the first parameter!!!!!!!!!!
	
	
	/**
	 * method for printing cell : node topology table
	 * @param map
	 */
	public static void printCellNodeTable() {
		//Printing cell : node topological table
		//printing map as topological table (key = cell's name : value = set of nodes)
		System.out.println("\nCell-Node table:");
		//GenTopTables.topCellNode.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topCellNode.keySet());//put keys from topCellNode to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Node> list = new ArrayList<>(GenTopTables.topCellNode.get(line));//put current values(Set<Node>) of map to ArrayList<Node>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	
	/**
	 * method for printing cell : edge topology table
	 * @param map
	 */
	public static void printCellEdgeTable() {
		//Printing cell : edge topological table
		//printing map as topological table (key = cell's name : value = set of edges)
		System.out.println("\nCell-Edge table");
		//GenTopTables.topCellEdge.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topCellEdge.keySet());//put keys from topCellEdge to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Edge> list = new ArrayList<>(GenTopTables.topCellEdge.get(line));//put current values(Set<Edge>) of map to ArrayList<Edge>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	
	/**
	 * method for printing cell : face topology table
	 * @param map
	 */
	public static void printCellFaceTable() {
		//Printing cell : face topological table
		System.out.println("\nCell-Face table");
		//printing map as topological table (key = cell's name : value = set of faces)
		//GenTopTables.topCellFace.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
		List<String> tmp = new ArrayList<>(GenTopTables.topCellFace.keySet());//put keys from topCellFace to ArrayList
		Collections.sort(tmp, compare);//sorting that list using new comparator
		for (String line : tmp) {
			List<Face> list = new ArrayList<>(GenTopTables.topCellFace.get(line));//put current values(Set<Face>) of map to ArrayList<Face>
			Collections.sort(list);//sorting that list
			System.out.println(line + " : " + list);
		}
	}
	
	/**
	 * method for printing cell : cell topology table
	 * @param map
	 */
	public static void printCellCellTable() {
		//Calculating cell : cell topological table
		System.out.println("\nCell-Cell table");
		//printing map as topological table (key = cell's name : value = set of cell)
		GenTopTables.topCellCell.entrySet().stream().sorted(Entry.comparingByKey(compare)).forEach(e -> System.out.println(e.getKey() + ": " +e.getValue()));
	}
}
