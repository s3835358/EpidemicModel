import java.io.PrintWriter;
import java.util.Random;

/**
 * SIR model.
 *
 * @author Jeffrey Chan, 2021.
 */
public class SIRModel
{
	
	private final int NEIGHBOUR = 1;
	private final int STOP_SIM = 10;
    /**
     * Default constructor, modify as needed.
     */
    public SIRModel() {
    	
    } // end of SIRModel()


    /**
     * Run the SIR epidemic model to completion, i.e., until no more changes to the states of the vertices for a whole iteration.
     *
     * @param graph Input contracts graph.
     * @param seedVertices Set of seed, infected vertices.
     * @param infectionProb Probability of infection.
     * @param recoverProb Probability that a vertex can become recovered.
     * @param sirModelOutWriter PrintWriter to output the necessary information per iteration (see specs for details).
     */
    public void runSimulation(ContactsGraph graph, String[] seedVertices,
        float infectionProb, float recoverProb, PrintWriter sirModelOutWriter)
    {
    	// Instantiate variables
    	int t = 1,
    	unchanged = 0;
    	String[] newRecovered = new String[0], 
    	newInfected = new String[0],
    	vertices = graph.getVertices().getArr();
    	StrArray infected = new StrArray();
    	
    	// Infect graph with seed vertices
    	graph = updateGraph(newInfected, seedVertices, graph);
    	
    	// Update infected StrArray
    	for(String vertex: vertices) {
    		if(graph.getState(vertex)==SIRState.I) {
    			infected.addString(vertex);
    		}
    	}
    	// Begin simulation
    	while(unchanged < STOP_SIM) {
    		
	    	newInfected = updateInfected(vertices, graph, infectionProb);
	    		
	    	newRecovered = updateRecovered(vertices, graph, recoverProb);
	    	
	    	infected.addStrArr(newInfected);
	    	infected.delStrArr(newRecovered);
	    	
	    	graph = updateGraph(newRecovered, newInfected, graph);
	    	
	    	sirModelOutWriter.write(output(t, newInfected, newRecovered));
	    	sirModelOutWriter.flush();
	    	
	    	unchanged = updateStop(unchanged, infected, newRecovered, newInfected);
	    	
	    	t++;
    	}
    	
    } // end of runSimulation()
    
    private String output(int t, String[] infected, String[] recovered) {
    	String out = t + ": [";
    	for(String infStr: infected) {
    		out = out + infStr + " ";
    	}
    	out = out.trim();
    	out = out + "] : [";
    	for(String recStr: recovered) {
    		out = out + recStr + " ";
    	}
    	out = out.trim();
    	out = out + "]\n";
    	return out;
    }
    
    private String[] updateInfected(String[] vertices, ContactsGraph graph, float infectionProb) {
    	Random random = new Random();
    	StrArray newInfected = new StrArray();
    	for(String vertex: vertices) {
    		if(graph.getState(vertex) == SIRState.I) {
    			String[] neighbours = graph.kHopNeighbours(NEIGHBOUR, vertex);
        		for(String neighbour: neighbours) {
        			boolean infect = (random.nextFloat() < infectionProb);
        			if(infect && graph.getState(neighbour) == SIRState.S) {
        				newInfected.addString(neighbour);
        			}
        		}
    		}
    	}
    	return newInfected.getArr();
    }
    
    private String[] updateRecovered(String[] vertices, ContactsGraph graph, float recoverProb) {
    	Random random = new Random();
    	StrArray newRecovered = new StrArray();
    	for(String vertex: vertices) {
    		if((graph.getState(vertex) == SIRState.I) && (random.nextFloat() < recoverProb)) {
    			newRecovered.addString(vertex);
    		}
    	}
    	return newRecovered.getArr();
    }
    
    private ContactsGraph updateGraph(String[] newRecovered, String[] newInfected, ContactsGraph graph) {
    	for(String infString : newInfected) {
    		graph.toggleVertexState(infString);
    	}
    	for(String recString: newRecovered) {
    		graph.toggleVertexState(recString);
    	}
    	return graph;
    }
    
    private int updateStop(int unchanged, StrArray infected, 
    String[] newInfected, String[] newRecovered) {
    	boolean stagnant = (newInfected.length == 0 && newRecovered.length == 0);
    	if(infected.getArr().length == 0 && stagnant) {
    		unchanged = STOP_SIM;
    	} else if (stagnant) {
    		unchanged++;
    	} else {
    		unchanged = 0;
    	}
    	return unchanged;
    }
    
} // end of class SIRModel
