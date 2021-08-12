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
	
	public static String dataFileName = "the_room";

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
		
		//Display.printNode(LineReader.nodemap); //printing all nodes with coords
		//Display.printEdge(LineReader.edgemap); //printing all edges with nodes
		//Display.printFace(LineReader.facemap); //printing all faces with edges
		//Display.printCell(LineReader.cellmap); //printing all cells with faces
		
		//Display.PrintCellEdgeTable(LineReader.cellmap); //printing calculated cell:edge topological table
		//Display.PrintCellNodeTable(LineReader.cellmap); //printing calculated cell:node topological table
		Display.PrintFaceNodeTable(LineReader.facemap); //printing calculated face:node topological table
	}
	


}
