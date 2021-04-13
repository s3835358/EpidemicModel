import java.io.PrintWriter;

/**
 * Incidence matrix implementation for the GraphInterface interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2021.
 */
public class IncidenceMatrix extends AbstractGraph
{

	/**
	 * Contructs empty graph.
	 */
    public IncidenceMatrix() {
    	// Implement me!
    } // end of IncidenceMatrix()


    public void addVertex(String vertLabel) {
        // Implement me!
    } // end of addVertex()


    public void addEdge(String srcLabel, String tarLabel) {
        // Implement me!
    } // end of addEdge()


    public void toggleVertexState(String vertLabel) {
        // Implement me!
    } // end of toggleVertexState()


    public void deleteEdge(String srcLabel, String tarLabel) {
        // Implement me!
    } // end of deleteEdge()


    public void deleteVertex(String vertLabel) {
        // Implement me!
    } // end of deleteVertex()


    public String[] kHopNeighbours(int k, String vertLabel) {
        // Implement me!

        // please update!
        return null;
    } // end of kHopNeighbours()


    public void printVertices(PrintWriter os) {
        // Implement me!
    } // end of printVertices()


    public void printEdges(PrintWriter os) {
        // Implement me!
    } // end of printEdges()


	@Override
	public SIRState getState(String vertLabel) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public StrArray getVertices() {
		// TODO Auto-generated method stub
		return null;
	}

} // end of class IncidenceMatrix