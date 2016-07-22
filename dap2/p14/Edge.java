// An Edge between two Nodes. This Edge is only one way, from source (src) to
// the destination (dst).
public class Edge {
	private Node src;
	private Node dst;

	// Create a new Edge from src to dst.
	public Edge(Node src, Node dst) {
		this.src = src;
		this.dst = dst;
	}

	// Get the starting Node of the Edge (source).
	public Node src() {
		return src;
	}

	// Get the End-Point of the Edge (destination).
	public Node dst() {
		return dst;
	}

	public String toString() {
		return Integer.toString(src.id()) + "," + Integer.toString(dst.id());
	}
}
