// Import libraries.
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Graph implements GraphADT {
	// Declare instance variables.
	LinkedList<GraphEdge>[] edges;
	GraphNode[] nodes;
	
	
	public Graph(int n) {
		// Initialize instance variables using size given.
		edges = new LinkedList[n];
		nodes = new GraphNode[n];
		for (int i = 0; i < n; i++) {
			nodes[i] = new GraphNode(i);
			edges[i] = new LinkedList<GraphEdge>();
		}
	}
	
	
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label)
	throws GraphException {
		// Check to see if the edge already exists.
		if (getEdge(nodeu, nodev) != null) {
			throw new GraphException("Edge already exists.");
		}
		else {
			// Create a new edge and add it to both of the nodes' lists.
			GraphEdge edge = new GraphEdge(nodeu, nodev, type, label);
			edges[nodeu.getName()].add(edge);
			edges[nodev.getName()].add(edge);
		}
	}
	
	
	public GraphNode getNode(int u) throws GraphException {
		// Make sure that the index given is in the bounds of the array.
		if ((u >= nodes.length) || (u < 0)) {
			throw new GraphException("Node does not exist.");
		}
		else {
			// Return the corresponding node from the node array.
			return nodes[u];
		}
	}
	
	
	public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
		// Get the node.
		GraphNode node = getNode(u.getName());
		// Return an iterator for the edges of the node.
		return edges[node.getName()].iterator();
	}
	
	
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		// Make sure both nodes are different.
		if (u.getName() == v.getName()) {
			return null;
		}
		
		// Make sure both nodes exist.
		getNode(u.getName());
		getNode(v.getName());
		
		// Determine which node has less edges.
		Iterator<GraphEdge> iterator;
		int size1 = edges[u.getName()].size();
		int size2 = edges[v.getName()].size();
		if (size1 < size2) {
			// Iterate over the edges of node u.
			iterator = incidentEdges(u);
		}
		else {
			// Iterate over the edges of node v.
			iterator = incidentEdges(v);
		}
		
		// Search the list for the edge that contains both nodes.
		GraphEdge curr = null;
		while (iterator.hasNext()) {
			curr = iterator.next();
			if (((curr.firstEndpoint().getName() == u.getName()) ||
			(curr.firstEndpoint().getName() == v.getName())) && 
			((curr.secondEndpoint().getName() == u.getName()) ||
			(curr.secondEndpoint().getName() == v.getName()))) {
				// Return the current edge if it has both nodes.
				return curr;
			}
		}
		// Return null if the edge could not be found.
		return null;
	}
	
	
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		// Check to see if there is an edge between the two nodes.
		if (getEdge(u, v) != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
