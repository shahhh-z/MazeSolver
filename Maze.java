// Import libraries.
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Maze {
	// Declare instance variables.
	Graph graph;
	GraphNode start;
	GraphNode end;
	int numCoins;
	LinkedList<GraphNode> path = new LinkedList<GraphNode>();
	
	
	public Maze(String inputFile) throws MazeException {
		// Try to read the file given.
		try {
			BufferedReader readInput = new BufferedReader(new FileReader(inputFile));
			readInput(readInput);
		}
		catch (GraphException e) {
			// Let the user know about the exception.
			System.out.println(e);
		}
		catch (IOException e) {
			// Throw a maze exception if the filename is invalid.
			throw new MazeException("File not found.");
		}
	}
	
	
	public Graph getGraph() throws MazeException {
		// Return the graph if it is not null.
		if (graph == null) {
			// Throw an exception if the graph is null.
			throw new MazeException("Graph is null.");
		}
		else {
			return graph;
		}
	}
	
	
	public Iterator<GraphNode> solve() {
		// Try to call the DFS function to find a path to the end.
		try {
			if (DFS(numCoins, start)) {
				// Return an iterator for the path.
				return path.iterator();
			}
			else {
				// Return null if there is no valid path.
				return null;
			}
		}
		catch (GraphException e) {
			// Return null if there was an exception.
			return null;
		}
	}
	
	
	private boolean DFS(int k, GraphNode go) throws GraphException {
		// If the number of coins is less than zero then return false.
		if (k < 0) {
			return false;
		}
		else {
			// Add the current node to the path and mark it.
			path.add(go);
			go.mark(true);
			
			// If the current node is the end node then return true.
			if (go.getName() == end.getName()) {
				return true;
			}
			
			// Iterate over the incident edges of the node.
			Iterator<GraphEdge> iterator = graph.incidentEdges(go);
			while (iterator.hasNext()) {
				GraphEdge edge = iterator.next();
				// Check to see that the edge is not labelled.
				if (edge.getLabel().equals("")) {
					// Get the node on the other side of the edge.
					GraphNode otherEnd;
					if (edge.firstEndpoint().getName() == go.getName()) {
						otherEnd = edge.secondEndpoint();
					}
					else {
						otherEnd = edge.firstEndpoint();
					}
					// Check to see if the other node is marked.
					if (!otherEnd.isMarked()) {
						// Label the edge.
						edge.setLabel("discovery");
						// Call DFS on the other node.
						if (DFS(k - edge.getType(), otherEnd)) {
							return true;
						}
						else {
							// Label the edge as back if DFS is false.
							edge.setLabel("back");
						}
					}
				}
			}
			// Remove the node and unmark it.
			path.removeLast();
			go.mark(false);
			// Return false.
			return false;
		}
	}
	
	
	private void readInput(BufferedReader inputReader) throws IOException,
	GraphException, MazeException {
		// Read the first four lines and initialize the instance variables.
		String input = inputReader.readLine();
		int width = Integer.parseInt(inputReader.readLine());
		int length = Integer.parseInt(inputReader.readLine());
		graph = new Graph(length * width);
		numCoins = Integer.parseInt(inputReader.readLine());
		
		// Initialize the node counter.
		int numNode = 0;
		
		// Read through the rest of the file.
		while ((input = inputReader.readLine()) != null) {
			// Go through each character in the line.
			for (int i = 0; i < input.length(); i++) {
				switch(input.charAt(i)) {
					case 's':
						// Set start equal to the current node.
						start = graph.getNode(numNode);
						// Increment the number of nodes.
						numNode++;
						break;
					case 'x':
						// Set end equal to the current node.
						end = graph.getNode(numNode);
						// Increment the number of nodes.
						numNode++;
						break;
					case 'o':
						// Increment the number of nodes.
						numNode++;
						break;
					case 'c':
						// Check to see if the edge is horizontal or vertical.
						if (i%2 == 0) {
							// Insert the edge vertically.
							insertEdge(numNode + (i/2) - width, numNode + (i/2),
							0, "");
						}
						else {
							// Insert the edge horizontally.
							insertEdge(numNode - 1, numNode, 0, "");
						}
						break;
					case 'w':
						break;
					case '0':
					case '1':
					case '2':
					case '3':
					case '4':
					case '5':
					case '6':
					case '7':
					case '8':
					case '9':
						// Initialize the cost of using the edge.
						int cost = Character.getNumericValue(input.charAt(i));
						// Check to see if the edge is horizontal or vertical.
						if (i%2 == 0) {
							// Insert the edge vertically.
							insertEdge(numNode + (i/2) - width, numNode + (i/2),
							cost, "");
						}
						else {
							// Insert the edge horizontally.
							insertEdge(numNode - 1, numNode, cost, "");
						}
						break;
					default:
						// Throw a maze exception if the character is invalid.
						throw new MazeException("Incorrect format.");
				}
			}
		}
	}
	
	
	private void insertEdge(int node1, int node2, int linkType, String label)
	throws GraphException {
		// Get the two nodes and call the insert function for the graph.
		GraphNode firstNode = graph.getNode(node1);
		GraphNode secondNode = graph.getNode(node2);
		graph.insertEdge(firstNode, secondNode, linkType, label);
	}

}
