# EpidemicModel

Java program that simulates the spread of a virus through a community where the community is represented either as a adjacency list, adjacency matrix or incidence matrix based on user input.

Such data strcutures are all implemented in their own class using primative structures and the program does not rely upon the respective java.util libraries.

As such, each data structure may be analysed for efficiency, and the degree to which it is appropriate for each task.

Input commands are:
 - AV <vertLabel> – add a vertex with label ’vertLabel’ into the graph.  Has default SIR state of susceptible (S).
 - AE <srcLabel> <tarLabel> –  add  an  edge  with  source  vertex  ’srcLabel’,  targetvertex ’tarLabel’ into the graph.
 - TV <vertLabel> – toggle the SIR state of vertex ’vertLabel’.  Toggling means go tothe next state, i.e., from S to I, from I to R (if in R, remain in R).
 - DV <vertLabel> – delete vertex ’vertLabel’ from the graph.
 - DE <srcLabel> <tarLabel> – remove edge with source vertex ’srcLabel’ and target vertex ’tarLabel’ from the graph.
 - KN <k> <vertLabel> – Return the set of all neighbours for vertex ’vertLabel’ thatare up to k-hops away.  The ordering of the neighbours does not matter.  
 - PV – prints the vertex set of the graph.  See below for the required format.  The vertices can be printed in any order.
 - PE – prints the edge set of the graph.  See below for the required format.  The edgescan be printed in any order.
 - SIR <infected seed vertices, delimited by ;> <infection probability> <recoverprobability>
    – Run the networked SIR model simulation, using the current graphas the network, the specified seed vertices as additional set of infected vertices 
    (inaddition to any existing in the current graph) and the two infection and recover 5 probabilities.  
    Runs the model simulation to completion, which means two things
      a) there are no more vertices with Infected (I) state and there are no changes in thenumber of infected or recovered in the latest iteration of the model; 
      or b) if condi-tion there are still infected vertices but there has been no changes in the number ofinfected or recovered for 10 iterations, then can stop the simulation.  
      Outputs thelist of nodes infected at each iteration, see below for format.
 - Q – quits the program.
