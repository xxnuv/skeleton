package skeleton;

import linereader.LineReader;

import java.io.File;
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

/**
 * The application class is used to run against data file. This class contains methods for
 * displaying calculated topological tables (cell:edge, cell:node, face:node)
 * @author rozhk
 *
 */
public class Application {
	public static String dataFileName = "cube_without_octant";
	public static String additionalFile = "inner_cell";

	public static void main(String[] args) throws IOException {
		//setup full path to the file with construction commands
		String runDirectory = System.getProperty("user.dir");
		String fileName = runDirectory.concat(File.separator + "testData" + File.separator + dataFileName);
		// create an instance of CommandLineReader to read the commands from the file
		LineReader linereader = new LineReader(fileName);
		//loop to read a file
		while(linereader.hasNext()) linereader.read();
		linereader.close();
		
		//String addFileName = runDirectory.concat(File.separator + "testData" + File.separator + additionalFile);
		//GenTopTables.addInnerCell(addFileName);
		//System.out.println("counter " + GenTopTables.counter);
		//System.out.println("inner operations " + GenTopTables.inner_operation);
		//System.out.println("operations " + GenTopTables.operation);
		}
	
	
	public static void timeTest(String fileName, int times) {
		ArrayList<Long> listTopTable =new ArrayList<>();//array for time of construction topological tables
		ArrayList<Long> listProgramTime = new ArrayList<>();//array for time of all calculations
		
		// loop for time tests
		for(int i = 0; i < times; i++) {
		LineReader linereader = new LineReader(fileName);
		
		linereader.checker = true;//without that flag program will start printing topological tables. And for output it was constructed comparators
		//where for comparing using String variables. And for construction program will took more that 1000 times more time then linereader with checker flag.

		// loop to read a file
		while (linereader.hasNext()) {
			linereader.read();
		}
		if (linereader.hasErrors()) {
			System.out.println("\nTesting stopped due to errors");
			System.exit(0);
		}
		linereader.close(); //On this step lineReader closing the file.
		listTopTable.add(GenTopTables.methodTime);
		listProgramTime.add(linereader.timeImplementation);
		}
		//Calculation of time
		long gt = 0l;
		for (long t : listTopTable) gt += t;
		System.out.printf("\nAverage time of construction topological tables after %s times: " + gt/(long)listTopTable.size() + " ns", times);
		
		long time = 0l;
		for (long t : listProgramTime) time += t;
		System.out.printf("\nAverage time of all constructions after %s times: " + time/(long)listProgramTime.size() + " ns", times);
	}
}
