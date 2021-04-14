import java.io.PrintWriter;
import java.util.Map;

/**
 * Abstract class to allow you to implement common functionality or definitions.
 * All three graph implement classes extend this.
 *
 * Note, you should make sure to test after changes.  Note it is optional to
 * use this file.
 *
 * @author Jeffrey Chan, 2021.
 */
public abstract class AbstractGraph implements ContactsGraph
{
	protected final int ONE_HOP = 1;
	protected IndexMap indexMap;
	protected Map<String, SIRState> stateMap;
	protected StrArray vertLabels;
	protected int[][] matrix;
	
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
	}
	
	public void printVertices(PrintWriter os) {
    	for(String vertex: vertLabels.getArr()) {
    		os.write("(" + vertex + ", " + stateMap.get(vertex) + ") ");
    	}
    	os.write("\n");
    	os.flush();
    }
	
	public SIRState getState(String vertLabel) {
    	return stateMap.get(vertLabel);
    }
    
    public StrArray getVertices() {
    	return vertLabels;
    }  
    
    /** 
     * Checks whether vertex already exists
     * 
     * @param vertLabel vertex to check existence of
     * 
     * @returns boolean value representing existence
     */
    protected boolean exists(String vertLabel) {
    	return indexMap.containsKey(vertLabel);
    }
    
    /** 
     * Returns the index associated with a vertex
     * 
     * @param vertLabel vertex to find the index of
     * 
     * @returns index Integer associated with vertex
     */
    protected int index(String vertLabel) {
    	return indexMap.get(vertLabel);
    }
	
	
} // end of abstract class AbstractGraph
