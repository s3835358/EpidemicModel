import java.io.PrintWriter;
import java.util.HashMap;
// import java.util.*;
import java.util.Map;

/**
 * Adjacency list implementation for the AssociationGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2019.
 */
public class AdjacencyList extends AbstractGraph
{
	
	private int length;
	private MyLinkedList[] vertArray;
	private Map<String, Integer> indexMap;
	private Map<String, SIRState> stateMap;
	private StrArray vertLabels;
    /**
	 * Contructs empty graph.
	 */
    public AdjacencyList() {
    	length = 0;
    	vertArray = new MyLinkedList[length];
    	indexMap = new HashMap<String, Integer>();
    	stateMap = new HashMap<String, SIRState>();
    	vertLabels = new StrArray();
    	
    } // end of AdjacencyList()


    public void addVertex(String vertLabel) {
    	//rewrite with vertices
    	if(!indexMap.containsKey(vertLabel)) {
    		// Resize array
    		MyLinkedList[] temp = vertArray;        	
    		vertArray = new MyLinkedList[length + 1];
        	for (int i = 0; i < length; i++) {
        		vertArray[i] = temp[i]; 
    		}
        	
        	vertArray[length] = new MyLinkedList();
        	vertLabels.addString(vertLabel);
        	indexMap.put(vertLabel,length);
        	stateMap.put(vertLabel, SIRState.S);
        	length++;
    	}
    } // end of addVertex()

    public void addEdge(String srcLabel, String tarLabel) {
    	if (indexMap.containsKey(srcLabel) && indexMap.containsKey(tarLabel)) {
    		MyLinkedList row = vertArray[indexMap.get(srcLabel)];
        	if(!row.search(tarLabel)) {
        		row.add(tarLabel);
        	}
        	// Since undirected we have to add inverse edge too ie. both A B and B A
        	row = vertArray[indexMap.get(tarLabel)];
        	if(!row.search(srcLabel)) {
        		row.add(srcLabel);
        	}
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
    	if (indexMap.containsKey(srcLabel) && indexMap.containsKey(tarLabel)) {
    		boolean edgeDel = !vertArray[indexMap.get(srcLabel)].remove(tarLabel),
    		inverseDel = !vertArray[indexMap.get(tarLabel)].remove(srcLabel);
    		if (edgeDel || inverseDel) {
    			System.err.println("ERROR: Edge does not exist");
    		}
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    } // end of deleteEdge()

    public void deleteVertex(String vertLabel) {
    	if (indexMap.containsKey(vertLabel)) {
    		
    		int vertIndex = indexMap.get(vertLabel);   		
    		MyLinkedList[] temp = vertArray;
    		
    		//Resize array
    		vertArray = new MyLinkedList[temp.length - 1];
    		for(int i = 0; i < vertArray.length; i++) {
    			if(i < vertIndex) {
    				vertArray[i] = temp[i];
    			} else {
    				vertArray[i] = temp[i+1];
    			}
    		}
    		
    		// Remove key map and change values of other keys to match array indexes
    		indexMap.remove(vertLabel);
    		indexMap.forEach((key,value) -> {
    			if (value > vertIndex) {
    				value = value - 1;
    				indexMap.replace(key, value);
    			}
    			vertArray[value].remove(vertLabel);
    		});
    		
    		length--;
    		vertLabels.delString(vertLabel);
    		stateMap.remove(vertLabel);
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    } // end of deleteVertex()

    public String[] kHopNeighbours(int k, String vertLabel) {
    	StrArray neighbours = new StrArray();
    	MyLinkedList list = vertArray[indexMap.get(vertLabel)];  	
    	
    	if(k == NEIGHBOUR) {
    		for(int edge = 0; edge < list.getLength(); edge++) {
    			neighbours.addString(list.get(edge));
    		}
    	} else if (k > NEIGHBOUR) {    		
    		for(int edge = 0; edge < list.getLength(); edge++) {
    			String vertex = list.get(edge);
    			// Calls the function recursively
    			StrArray add = new StrArray(kHopNeighbours(k-1, vertex));
    			add.addString(vertex); 
    			neighbours.addStrArr(add.getArr(), vertLabel);
    		}
    	}
        return neighbours.getArr();
    } // end of kHopNeighbours()


    public void printVertices(PrintWriter os) {
    	
    	for(String vertex: vertLabels.getArr()) {
    		os.write("(" + vertex + ", " + getState(vertex) + ") ");
    	}
    	os.write("\n");
    	os.flush();
    } // end of printVertices()

    public void printEdges(PrintWriter os) {
    	
    	for(String vertex: vertLabels.getArr()) {
    		MyLinkedList list = vertArray[indexMap.get(vertex)];
    		
    		for(int edge = 0; edge < list.getLength(); edge ++) {
    			os.write(vertex + " " + list.get(edge) + "\n");
    		}
    	}
    	os.flush();
    } // end of printEdges()
    
    public SIRState getState(String vertLabel) {
    	return stateMap.get(vertLabel);
    }
    
    public StrArray getVertices() {
    	return vertLabels;
    }  
    
} // end of class AdjacencyList
