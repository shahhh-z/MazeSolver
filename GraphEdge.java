
public class GraphEdge {
	// Declare instance variables.
	GraphNode origin;
	GraphNode destination;
	int type;
	String label;
	
	
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		// Set the instance variables equal to the parameters given.
		origin = u;
		destination = v;
		this.type = type;
		this.label = label;
	}
	
	
	public GraphNode firstEndpoint() {
		// Return origin.
		return origin;
	}
	
	
	public GraphNode secondEndpoint() {
		// Return destination.
		return destination;
	}
	
	
	public int getType() {
		// Return type.
		return type;
	}
	
	
	public void setType(int type) {
		// Set type equal to the type given.
		this.type = type;
		
	}
	
	
	public String getLabel() {
		// Return label.
		return label;
		
	}
	
	
	public void setLabel(String label) {
		// Set label equal to the label given.
		this.label = label;
	}
}
