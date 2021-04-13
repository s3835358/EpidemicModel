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

	int[][] matrix;
	final private int NO_EDGE = 0, EDGE = 1; 
	private Map<String, Integer> indexMap;
	private StrArray vertLabels;
	private Map<String, SIRState> stateMap;
	/**
	 * Contructs empty graph.
	 */
    public AdjacencyMatrix() {
    	// Implement me!
    	matrix = new int[0][0];
    	indexMap = new HashMap<String, Integer>();
    	stateMap = new HashMap<String, SIRState>();
    	vertLabels = new StrArray();
    } // end of AdjacencyMatrix()


    public void addVertex(String vertLabel) {
    	if(!indexMap.containsKey(vertLabel)) {
			int rows = matrix.length,
	    	cols = rows;
	    	int[][] temp = matrix;
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
	    	vertLabels.addString(vertLabel);
	    	indexMap.put(vertLabel, rows);
	    	stateMap.put(vertLabel, SIRState.S);
    	}
    	
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
        // Implement me!
    	if(indexMap.containsKey(srcLabel) && indexMap.containsKey(tarLabel)) {
    		int vertOne = indexMap.get(srcLabel),
    		vertTwo = indexMap.get(tarLabel);
    		matrix[vertOne][vertTwo] = EDGE;
    		matrix[vertTwo][vertOne] = EDGE;
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    } // end of addEdge()


    public void toggleVertexState(String vertLabel) {
        if (stateMap.containsKey(vertLabel)) {
    		if(stateMap.get(vertLabel) == SIRState.S) {
    			stateMap.replace(vertLabel, SIRState.S, SIRState.I);
    		} else if(stateMap.get(vertLabel) == SIRState.I) {
    			stateMap.replace(vertLabel, SIRState.I, SIRState.R);
    		}
    	} else {
    		System.err.println("ERROR: Vertex does not exist");
    	}
    } // end of toggleVertexState()


    public void deleteEdge(String srcLabel, String tarLabel) {
        // Implement me!
    	if(indexMap.containsKey(srcLabel) && indexMap.containsKey(tarLabel)) {
    		int vertOne = indexMap.get(srcLabel),
    		vertTwo = indexMap.get(tarLabel);
    		if(matrix[vertOne][vertTwo] == EDGE) {
        		matrix[vertOne][vertTwo] = NO_EDGE;
        		matrix[vertTwo][vertOne] = NO_EDGE;    			
    		} else {
    			System.err.println("ERROR: Edge does not exist");
    		}
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    } // end of deleteEdge()


    public void deleteVertex(String vertLabel) {
    	if(indexMap.containsKey(vertLabel)) {
			int rows = matrix.length - 1,
	    	cols = rows,
	    	vertIndex = indexMap.get(vertLabel);
	    	int[][] temp = matrix;
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
	    	vertLabels.delString(vertLabel);
	    	indexMap.remove(vertLabel);
	    	stateMap.remove(vertLabel);
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
    	StrArray neighbours = new StrArray();
    	int vertRow = indexMap.get(vertLabel);
    	
    	if(k == NEIGHBOUR) {
    		for(int col = 0; col < matrix.length; col++) {
    			if(matrix[vertRow][col] == EDGE) {
        			neighbours.addString(vertLabels.getString(col));
    			}
    		}
    	} else if (k > NEIGHBOUR) {    
    		for(int col = 0; col < matrix.length; col++) {
    			if(matrix[vertRow][col] == EDGE) {
    				String colLabel = vertLabels.getString(col);
    				StrArray add = new StrArray(kHopNeighbours(k-1, colLabel));
    				add.addString(colLabel);
    				neighbours.addStrArr(add.getArr(), vertLabel);
    			}
    		}
    	}
        return neighbours.getArr();
    } // end of kHopNeighbours()


    public void printVertices(PrintWriter os) {
        // Implement me!
    	for(String vertex: vertLabels.getArr()) {
    		os.write("(" + vertex + ", " + stateMap.get(vertex) + ") ");
    	}
    	os.write("\n");
    	os.flush();
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        int rows = matrix.length,
        cols = rows;
        for(int row = 0; row < rows; row++) {
        	for(int col = 0; col < cols; col++) {
            	if(matrix[row][col] == EDGE) {
            		String vertOne = vertLabels.getString(row); 
            		String vertTwo = vertLabels.getString(col);
            		os.write(vertOne + " " + vertTwo + "\n");
            	}
        		//System.out.print(matrix[row][col]);
            }
        	//System.out.print("\n");
        }
        os.flush();
        
    } // end of printEdges()


	@Override
	public SIRState getState(String vertLabel) {
		return stateMap.get(vertLabel);
	}


	@Override
	public StrArray getVertices() {
		return vertLabels;
	}

} // end of class AdjacencyMatrix
