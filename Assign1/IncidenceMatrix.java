import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Incidence matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class IncidenceMatrix extends AbstractGraph
{

	final private int NOT_INC = 0, INC = 1, VERT_ONE = 0, VERT_TWO = 1;
	
	// Allows quick access to edge label strings
	private StrArray edgeLabels;
	
	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	matrix = new int[0][0];
    	indexMap = new IndexMap();
    	stateMap = new HashMap<String, SIRState>();
    	vertLabels = new StrArray();
    	edgeLabels = new StrArray();
    }

    public void addVertex(String vertLabel) {
    	
    	if(!exists(vertLabel)) {
    		
			int rows = vertLabels.getLength(),
	    	cols = edgeLabels.getLength(); 
			int[][] temp = matrix;
			
			// Resize matrix, re-assign values within loop
			matrix = new int[rows + 1][cols];

			for(int row = 0; row <= rows; row++) {
	    		for(int col = 0; col < cols; col++) {
	    			if(row==rows) {
	    				matrix[row][col] = NOT_INC;
	    			} else {
	    				matrix[row][col] = temp[row][col];
	    			}
	    		}
	    	}
	    	// Update maps and StrArray
	    	vertLabels.addString(vertLabel);
	    	indexMap.put(vertLabel, rows);
	    	stateMap.put(vertLabel, SIRState.S);
    	}
    }


    public void addEdge(String srcLabel, String tarLabel) {
    	// Checks existence and distinction of vertices
    	if(exists(srcLabel) && exists(tarLabel) && !srcLabel.equals(tarLabel)) {
    		
    		String edgeLabel = srcLabel+tarLabel,
    		inverseLabel = tarLabel+srcLabel;
    		
    		boolean noEdges = !edgeLabels.contains(edgeLabel) 
    				&& !edgeLabels.contains(inverseLabel);
    		
    		if(noEdges) {
    			
    			int rows = vertLabels.getLength(),
		    	cols = edgeLabels.getLength();
    			int[][] temp = matrix;
    			
    			// Resize matrix, re-assign values within loop
		    	matrix = new int[rows][cols + 1];
		    	
		    	for(int row = 0; row < rows; row++) {
		    		for(int col = 0; col <= cols; col++) {	
		    			if(col < cols) {
		    				matrix[row][col] = temp[row][col];
		    			} else {	
		    				// If vertex is in edge, set to incident. 
		    				// Nested if/else increases performance
		    				boolean inEdge = (row == index(srcLabel) 
		    						|| row == index(tarLabel));
		    				
		    				if(inEdge) {
		    					matrix[row][col] = INC;
		    				} else {
		    					matrix[row][col] = NOT_INC;
		    				}
		    			}
		    		}
		    	}
		    	
		    	// Update map and StrArray
		    	edgeLabels.addString(edgeLabel);
		    	indexMap.put(edgeLabel, cols);
    		}
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist or are identical");
    	}
    }

    public void deleteEdge(String srcLabel, String tarLabel) {
    	String edge = null;
    		
    	if(edgeLabels.contains(srcLabel + tarLabel)) {
    		edge = srcLabel + tarLabel;
    	} else if (edgeLabels.contains(tarLabel + srcLabel)) {
    		edge = tarLabel + srcLabel;
    	}
    	
    	// Checks whether edge or inverseEdge already exists
    	if (edge != null) {
    		// Update StrArray
    		edgeLabels.delString(edge);
    		
			int rows = vertLabels.getLength(),
	    	cols = edgeLabels.getLength();
	    	int[][] temp = matrix;
	    	
	    	// Resize matrix, re-assign values within loop
	    	matrix = new int[rows][cols];
	    	
	    	for(int row = 0; row < rows; row++) {
	    		for(int col = 0; col < cols; col++) {
	    			if(col >= index(edge)) {
	    				matrix[row][col] = temp[row][col + 1];
	    			} else {
	    				matrix[row][col] = temp[row][col];
	    			}
	    		}
	    	}
	    	// Update map
	    	indexMap.adjustKeys(edge, false);
    	} else {
    		System.err.println("ERROR: Edge does not exist");
    	}
    }

    public void deleteVertex(String vertLabel) {
    	
    	if(exists(vertLabel)) {
    		vertLabels.delString(vertLabel);
    		
			int rows = vertLabels.getLength(),
			cols = edgeLabels.getLength();			
			int[][] temp = matrix;
			
			// Resize matrix, re-assign values within loop
	    	matrix = new int[rows][cols];
	    	
	    	for(int row = 0; row < rows; row++) {
	    		for(int col = 0; col < cols; col++) {
	    			if(row >= index(vertLabel)) {
	    				matrix[row][col] = temp[row + 1][col];
	    			} else {
	    				matrix[row][col] = temp[row][col];
	    			}
	    		}
	    	}   	

	    	// Delete edges containing vertex
	    	for(String edge : edgeLabels.getArr()) {
	    		if (edge.contains(vertLabel)) {
	    			String vertOne = edge.substring(VERT_ONE, VERT_TWO),
	    			vertTwo = edge.substring(VERT_TWO);
	    			deleteEdge(vertOne, vertTwo);
	    		}
	    	}
	    	// Update maps
	    	indexMap.adjustKeys(vertLabel, true);
	    	stateMap.remove(vertLabel);
    	}
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
    	StrArray neighbours = new StrArray();
    	
    	if(exists(vertLabel)) {
	    	int vertIndex = index(vertLabel);	
    		
	    	for(String edge : edgeLabels.getArr()) {
	    		// Find edges which vertex is incident to
	    		
	    		if (matrix[vertIndex][index(edge)] == INC) {
	    			// Other vertex which forms edge
	    			String vert = edge.replace(vertLabel, "");
	    			if(k == ONE_HOP) {
		    			neighbours.addString(vert);
	    			} else if(k > ONE_HOP) {
	    				// Calls function recursively
		    			StrArray add = new StrArray(kHopNeighbours(k-1, vert));
		    			add.addString(vert);
		    			neighbours.addStrArr(add.getArr(), vertLabel);
	    			}
	    		}
    		}
    	} else {
    		System.err.println("ERROR: Vertex does not exist");
    	}
        return neighbours.getArr();
    }
    
    
    // This method assumes vertices are assigned labels of a single character 
    // and therefore edges contain two characters ie. 'A' is a valid vertex, 'A1' is not
    
    public void printEdges(PrintWriter os) {
    	for(String edge: edgeLabels.getArr()) {
    		os.write(edge.charAt(VERT_ONE) + " " + edge.charAt(VERT_TWO) + "\n");
    		os.write(edge.charAt(VERT_TWO) + " " + edge.charAt(VERT_ONE) + "\n");
    	}
    	os.flush();
    }

} // end of class IncidenceMatrix