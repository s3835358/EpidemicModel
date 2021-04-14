
public class MyLinkedList {
	
	private Node headNode;
	private int length;
	
	public MyLinkedList() {
		headNode = null;
		length = 0;
	}
	
	/**
	 * Adds a vertex to the list
	 * 
	 * @param vertex, the vertex to add
	 */
	public void add(String vertex) {
		if(!contains(vertex)) {
			Node add = new Node(vertex);
			if(headNode!=null) {
				add.setNext(headNode);
				headNode = add;
			} else {
				headNode = add;
			}
			length++;
		}
	}
	
	/**
	 * Gets a vertex from the list
	 * 
	 * @param index, the index of the vertex to return
	 * 
	 * @returns a vertex
	 */
	public String get(int index) {
		Node node = null;
		if(index < length && index >= 0) {
			node = headNode;
			for (int i = 0; i < index; i++) {
				node = node.getNext();
			}
		} else {
			throw new IndexOutOfBoundsException("Invalid index");
		}
		return node.getVertex();
	}
	
	public int getLength() {
		return length;		
	}
	
	/**
	 * Finds whether a vertex exists within list
	 * 
	 * @param vertex, the vertex to find existence of
	 * 
	 * @return found, a boolean value representing the existence of vertex
	 */
	private boolean contains(String vertex) {
		Node node = headNode;
		boolean found = false;
		while(node!=null) {
			if (node.getVertex() == vertex) {
				found = true;
			} else {
				node = node.getNext();
			}	
		}
		return found;		
	}
	/**
	 * Removes a vertex from list
	 * 
	 * @param vertex, the vertex to be removed
	 * 
	 * @return removed, boolean value representing success of removal
	 */
	public boolean remove(String vertex) {
		boolean removed = false;
		if(headNode!=null) {
			if(headNode.getVertex().equals(vertex)) {
				headNode = headNode.getNext();
				removed = true;
				length--;
			} else {
				Node node = headNode;
				Node next = node.getNext();
				while(next!=null) {
					if(next.getVertex().equals(vertex)) {
						node.setNext(next.getNext());
						removed = true;
						length--;
						next = null;
					} else {
						node = next;
						next = node.getNext();
					}
				}
			}
		}
		return removed;
	}
}
