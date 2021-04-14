import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Adjacency matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class AdjacencyMatrix extends AbstractGraph
{

	
	final private int NO_EDGE = 0, EDGE = 1; 
	
	/**
	 * Contructs empty graph.
	 */
    public AdjacencyMatrix() {
    	matrix = new int[0][0];
    	indexMap = new IndexMap();
    	stateMap = new HashMap<String, SIRState>();
    	vertLabels = new StrArray();
    }


    public void addVertex(String vertLabel) {
    	
    	if(!exists(vertLabel)) {
			int rows = matrix.length,
	    	cols = rows;
	    	int[][] temp = matrix;
	    	
	    	// Resize matrix, re-assign values within loop
	    	matrix = new int[rows + 1][cols + 1];
	    	
	    	for(int row = 0; row <= rows; row++) {
	    		for(int col = 0; col <= cols; col++) {
	    			if(row==rows || col==cols) {
	    				matrix[row][col] = NO_EDGE;
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
    		int vertOne = index(srcLabel),
    		vertTwo = index(tarLabel);
    		// Assign edge and inverse edge
    		matrix[vertOne][vertTwo] = EDGE;
    		matrix[vertTwo][vertOne] = EDGE;
    		
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist or are identical");
    	}
    }

    public void deleteEdge(String srcLabel, String tarLabel) {
    	if(exists(srcLabel) && exists(tarLabel)) {
    		int vertOne = index(srcLabel),
    		vertTwo = index(tarLabel);
    		
    		// Deletes both edge and inverse edge
    		if(matrix[vertOne][vertTwo] == EDGE) {
        		matrix[vertOne][vertTwo] = NO_EDGE;
        		matrix[vertTwo][vertOne] = NO_EDGE;    			
    		} else {
    			System.err.println("ERROR: Edge does not exist");
    		}
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    }


    public void deleteVertex(String vertLabel) {
    	
    	if(exists(vertLabel)) {
			int rows = matrix.length - 1,
	    	cols = rows,
	    	vertIndex = index(vertLabel);
	    	int[][] temp = matrix;
	    	
	    	// Resize matrix, re-assign values within loop
	    	matrix = new int[rows][cols];
	    	
	    	for(int row = 0; row < rows; row++) {
	    		for(int col = 0; col < cols; col++) {
	    			if(row < vertIndex && col < vertIndex) {
	    				matrix[row][col] = temp[row][col];
	    			} else if (row < vertIndex) {
	    				matrix[row][col] = temp[row][col + 1];
	    			} else if (col < vertIndex) {
	    				matrix[row][col] = temp[row + 1][col];
	    			} else {
	    				matrix[row][col] = temp[row + 1][col + 1];
	    			}
	    		}
	    	}
	    	// Update maps and StrArray
	    	vertLabels.delString(vertLabel);
	    	indexMap.adjustKeys(vertLabel, true);
	    	stateMap.remove(vertLabel);
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    }


    public String[] kHopNeighbours(int k, String vertLabel) {
    	StrArray neighbours = new StrArray();
    	if(exists(vertLabel)) {
        	int vertRow = index(vertLabel);
        	
        	for(int col = 0; col < matrix.length; col++) {
        		// Find edges
    			if(matrix[vertRow][col] == EDGE) {
    				// Other vertex which forms edge
    				String vert = vertLabels.getString(col);
    				if(k == ONE_HOP) {
    					neighbours.addString(vert);
    				} else if (k > ONE_HOP) {
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

    public void printEdges(PrintWriter os) {
        int rows = matrix.length,
        cols = rows;
        for(int row = 0; row < rows; row++) {
        	for(int col = 0; col < cols; col++) {
        		// Find edges
            	if(matrix[row][col] == EDGE) {
            		String vertOne = vertLabels.getString(row); 
            		String vertTwo = vertLabels.getString(col);
            		os.write(vertOne + " " + vertTwo + "\n");
            	}
            }
        }
        os.flush();     
    }
} // end of class AdjacencyMatrix
