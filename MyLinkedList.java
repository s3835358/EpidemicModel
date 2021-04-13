
public class MyLinkedList {
	
	private Node headNode;
	private int length;
	
	public MyLinkedList() {
		headNode = null;
		length = 0;
	}
	
	public void add(String vertex) {
		//Insert to check if valid value
		Node add = new Node(vertex);
		if(headNode!=null) {
			add.setNext(headNode);
			headNode = add;
		} else {
			headNode = add;
		}
		length++;
	}
	
	public String get(int index) {
		if(index < length && index >= 0) {
			Node node = headNode;
			for (int i = 0; i < index; i++) {
				node = node.getNext();
			}
			return node.getVertex();
		} else {
			throw new IndexOutOfBoundsException("Invalid index");
		}
	}
	
	public int getLength() {
		return length;		
	}
	
	// Finds whether a vertex exists within list
	public boolean search(String vertex) {
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
