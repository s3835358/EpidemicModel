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

	private MyLinkedList[] vArray;	
    /**
	 * Contructs empty graph.
	 */
    public AdjacencyList() {
    	vArray = new MyLinkedList[0];
    	indexMap = new IndexMap();
    	stateMap = new HashMap<String, SIRState>();
    	vertLabels = new StrArray();
    	
    }

    public void addVertex(String vertLabel) {
    	if(!exists(vertLabel)) {
    		int arrLen = vArray.length;
    		MyLinkedList[] temp = vArray;
    		
    		// Resize array, re-assign values within loop
    		vArray = new MyLinkedList[arrLen + 1];
    		
    		for (int i = 0; i < arrLen; i++) {
    			vArray[i] = temp[i]; 
    		}
    		// Empty list created for new vertex
    		vArray[arrLen] = new MyLinkedList();
        	
    		// Update maps and StrArray
        	vertLabels.addString(vertLabel);
        	indexMap.put(vertLabel,arrLen);
        	stateMap.put(vertLabel, SIRState.S);
    	}
    }

    public void addEdge(String srcLabel, String tarLabel) {  
    	// Checks existence and distinction of vertices
    	if (exists(srcLabel) && exists(tarLabel) && !srcLabel.equals(tarLabel)) {
    		// Add edge, MyLinkedList.add() checks if edge exists
    		vArray[index(srcLabel)].add(tarLabel);
        	// Add the 'inverse' edge
    		vArray[index(tarLabel)].add(srcLabel);
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist or are identical");
    	}	
    }
    
    public void deleteEdge(String srcLabel, String tarLabel) {
    	if (exists(srcLabel) && exists(tarLabel)) {
    		// Delete edges, assigning the truth of deletion to variables
    		boolean edgeDel = vArray[index(srcLabel)].remove(tarLabel),
    		inverseDel = vArray[index(tarLabel)].remove(srcLabel);
    		
    		if (!edgeDel || !inverseDel) {
    			System.err.println("ERROR: Edge does not exist");
    		}
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    }

    public void deleteVertex(String vertLabel) {
    	
    	if (exists(vertLabel)) {
    		MyLinkedList[] temp = vArray;	
    		
    		//Resize array, re-assign values within loop
    		vArray = new MyLinkedList[temp.length - 1];
    		
    		for(int i = 0; i < vArray.length; i++) {
    			if(i < index(vertLabel)) {
    				vArray[i] = temp[i];
    			} else {
    				vArray[i] = temp[i+1];
    			}
    			
    			// Remove any edges containing vertLabel
    			vArray[i].remove(vertLabel);
    		}
    		// Update maps and StrArray
    		indexMap.adjustKeys(vertLabel, true);
    		vertLabels.delString(vertLabel);
    		stateMap.remove(vertLabel);
    	} else {
    		System.err.println("ERROR: One or both vertices do not exist");
    	}
    }

    public String[] kHopNeighbours(int k, String vertLabel) {
    	StrArray neighbours = new StrArray();
    	
    	if(exists(vertLabel)) {
    		
    		// List containing vertices that share edge with vertLabel
	    	MyLinkedList vList = vArray[index(vertLabel)];  	
	    	
	    	if(k == ONE_HOP) {
	    		for(int vert = 0; vert < vList.getLength(); vert++) {
	    			// Add to neighbours
	    			neighbours.addString(vList.get(vert));
	    		}
	    	} else if (k > ONE_HOP) {  
	    		for(int vert = 0; vert < vList.getLength(); vert++) {
	    			String neighbour = vList.get(vert);
	    			// Calls the function recursively, stores to StrArray
	    			StrArray add = new StrArray(kHopNeighbours(k-1, neighbour));
	    			add.addString(neighbour); 
	    			// Adds vertices returned via recusion to neighbours
	    			neighbours.addStrArr(add.getArr(), vertLabel);
	    		}
	    	}
    	} else {
    		System.err.println("ERROR: Vertex does not exist");
    	}
	    return neighbours.getArr();
    }

    public void printEdges(PrintWriter os) {
    	for(String vert: vertLabels.getArr()) {
    		// Iterate list of vertices that share edge with vert
    		for(int eVert = 0; eVert < vArray[index(vert)].getLength(); eVert ++) {
    			os.write(vert + " " + vArray[index(vert)].get(eVert) + "\n");
    		}
    	}
    	os.flush();
    }
} // end of class AdjacencyList
