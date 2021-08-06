package skeleton;

import linereader.LineReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import elements.Cell;
import elements.Edge;
import elements.Face;
import elements.Node;

public class Application {
	
	public static String fileName = "C:\\cube\\the_cube_main.txt";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LineReader linereader = new LineReader(fileName);
		
		while (linereader.hasNext()) {
			linereader.read();
		}
		
		linereader.close();
		System.out.println("List of nodes");
		for (Map.Entry<String, Node> pair : linereader.nodemap.entrySet())
		{
			String key = pair.getKey();
			Node value = pair.getValue();
			System.out.println(key + ": " + value.toString2());
		}

		System.out.println("\nList of edges");
		for (Map.Entry<String, Edge> pair1 : linereader.edgemap.entrySet())
		{
			String key = pair1.getKey();
			Edge value = pair1.getValue();
			System.out.println(key + ": " + value.toString2());
		}

		System.out.println("\nList of faces");		
		for (Map.Entry<String, Face> pair2 : linereader.facemap.entrySet())
		{
			String key = pair2.getKey();
			Face value = pair2.getValue();
			System.out.println(key + ": " + value.toString2());
		}

		System.out.println("\nList of cells");		
		for (Map.Entry<String, Cell> pair3 : linereader.cellmap.entrySet())
		{
			String key = pair3.getKey();
			Cell value = pair3.getValue();
			System.out.println(key + ": " + value.toString2());
		}
		PrintCellEdgeTable(linereader.cellmap);
		PrintCellNodeTable(linereader.cellmap);
		PrintFaceNodeTable(linereader.facemap);
	}
	
	public static void PrintCellEdgeTable(Map<String, Cell> map) {
		Map<String, Set<Edge>> tmp = new HashMap<>();
		for (Map.Entry<String, Cell> pair : map.entrySet()) {
			Set<Edge> set = new HashSet<>();
			String key = pair.getKey();
			Cell value = pair.getValue();
			for (Face face : value.faces) {
				set.addAll(face.edges);
			}
			tmp.put(key, set);
		}
		System.out.println("\nCell-Edge table");
		for (Map.Entry<String, Set<Edge>> pair : tmp.entrySet())
		{
			String key = pair.getKey();
			Set<Edge> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	public static void PrintCellNodeTable(Map<String, Cell> map) {
		Map<String, Set<Node>> tmp = new HashMap<>();
		for (Map.Entry<String, Cell> pair : map.entrySet()) {
			Set<Node> set = new HashSet<>();
			String key = pair.getKey();
			Cell value = pair.getValue();
			for (Face face : value.faces) {
				for (Edge edge : face.edges) {
					set.add(edge.node1);
					set.add(edge.node2);
				}
			}
			tmp.put(key, set);
		}
		
		System.out.println("\nCell-Node table:");
		for (Map.Entry<String, Set<Node>> pair : tmp.entrySet())
		{
			String key = pair.getKey();
			Set<Node> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}
	
	public static void PrintFaceNodeTable(Map<String, Face> map) {
		Map<String, Set<Node>> tmp = new HashMap<>();
		for (Map.Entry<String, Face> pair : map.entrySet()) {
			Set<Node> set = new HashSet<>();
			String key = pair.getKey();
			Face value = pair.getValue();
			for (Edge edge : value.edges) {
				set.add(edge.node1);
				set.add(edge.node2);
			}
			tmp.put(key, set);
		}
		
		System.out.println("\nFace-Node table:");
		for (Map.Entry<String, Set<Node>> pair : tmp.entrySet())
		{
			String key = pair.getKey();
			Set<Node> value = pair.getValue();
			System.out.println(key + ": " + value.toString());
		}
	}

}
