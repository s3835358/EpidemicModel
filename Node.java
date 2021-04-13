public class Node {
	
	private Node nextNode;
    private String vertex;
    private SIRState state;
    
    public Node(String vertex) {
    	nextNode = null;
        this.vertex = vertex;
        state = SIRState.S;
    }

    public String getVertex() {
        return vertex;
    }
    
    public void setVertex(String vertex) {
        this.vertex = vertex;
    }

    public Node getNext() {
        return nextNode;
    }

    public void setNext(Node nextNode) {
        this.nextNode = nextNode;
    }
    //Do we use state here or in map
    public SIRState getState() {
    	return state;
    }
    
    public void setState(SIRState state) {
    	this.state = state;
    }
}
