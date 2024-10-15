
public class GraphNode {
	// Declare and initialize instance variables.
	int name;
	boolean mark = false;
	
	
	public GraphNode(int name) {
		// Set name equal to the name given.
		this.name = name;
	}
	
	
	public void mark(boolean mark) {
		// Set mark equal to the mark given.
		this.mark = mark;
	}
	
	
	public boolean isMarked() {
		// Return mark.
		return mark;
	}
	
	
	public int getName() {
		// Return name.
		return name;
	}
}
