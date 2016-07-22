import java.util.ArrayList;
import java.io.*;

public class Graph {
	private ArrayList<Node> nodes;

	public Graph() {
		this.nodes = new ArrayList<Node>();
	}

	public boolean contains(int id) {
		// TODO: I was too lazy to implement a binary search, and since it
		// hadn't been asked for. Still, it would be better, obviously.
		for (Node n : nodes) {
			if (n.id() == id) return true;
		}

		return false;
	}

	public boolean addNode(int id) {
		if (this.contains(id)) return false;

		this.nodes.add(new Node(id));
		return true;
	}

	public Node getNode(int id) {
		for (Node n : nodes) {
			if (n.id() == id) return n;
		}

		return null;
	}

	public boolean addEdge(int src, int dst) {
		Node srcNode = null, dstNode = null;
		for (Node n : nodes) {
			if (n.id() == src) srcNode = n;
			if (n.id() == dst) dstNode = n;
			if (srcNode != null && dstNode != null) break;
		}

		if (srcNode == null || dstNode == null) return false;

		srcNode.addEdge(dstNode);
		dstNode.addEdge(srcNode);
		return true;
	}

	private void addEdgeCreateNecessary(int src, int dst) {
		Node srcNode = null, dstNode = null;
		for (Node n : nodes) {
			if (n.id() == src) srcNode = n;
			if (n.id() == dst) dstNode = n;
			if (srcNode != null && dstNode != null) break;
		}

		if (srcNode == null) {
			srcNode = new Node(src);
			nodes.add(srcNode);
		}
		if (dstNode == null) {
			 dstNode = new Node(dst);
			 nodes.add(dstNode);
		 }

		srcNode.addEdge(dstNode);
		dstNode.addEdge(srcNode);
	}

	public static Graph fromFile(String filepath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			Graph graph = new Graph();

			String line;
			while ((line = br.readLine()) != null) {
				String[] nodes = line.split(",");
				if (nodes.length != 2) {
					System.out.println("Could not read Graph file. Invalid file format.");
					return null;
				}

				int src, dst;
				src = Integer.parseInt(nodes[0]);
				dst = Integer.parseInt(nodes[1]);
				graph.addEdgeCreateNecessary(src, dst);
			}

			return graph;
		}
		catch (Exception e) {
			System.out.println("Could not open file containing Graph.\n" + e);
			return null;
		}
	}

	public int maxId() {
		int max = -1;
		for (Node n : nodes) {
			if (n.id() > max) max = n.id();
		}

		return max;
	}

	public String toString() {
		String res = new String();

		for (Node n : nodes) {
			res += n.toString();
		}

		return res;
	}
}
