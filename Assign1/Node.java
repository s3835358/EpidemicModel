public class Node {
	
	private Node nextNode;
    private String vertex;
    
    public Node(String vertex) {
    	nextNode = null;
        this.vertex = vertex;
    }

    public String getVertex() {
        return vertex;
    }

    public Node getNext() {
        return nextNode;
    }

    public void setNext(Node nextNode) {
        this.nextNode = nextNode;
    }
}
