import java.util.ArrayList;

public class Node {
	private int id;
	private ArrayList<Edge> adjacent;

	public Node(int id) {
		this.id = id;
		this.adjacent = new ArrayList<Edge>();
	}

	public int id() {
		return this.id;
	}

	public ArrayList<Edge> adjacentList() {
		return this.adjacent;
	}

	public boolean addEdge(Node dst) {
		if (dst == null) return false;

		this.adjacent.add(new Edge(this, dst));
		return true;
	}

	public boolean equals(Object other) {
		if (other instanceof Node) {
			return ((Node)other).id() == this.id;
		}

		return false;
	}

	public String toString() {
		String res = new String();
		for (Edge e : adjacent) {
			res += e.toString() + "\n";
		}

		return res;
	}
}
