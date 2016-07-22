public class Test {
	public static void main(String[] args) {
		int tested = 0;
		int successful = 0;

		if (testEdge()) successful++;
		tested++;
		if (testNode()) successful++;
		tested++;
		if (testGraph()) successful++;
		tested++;

		System.out.println("Testing completed. Tested " + tested + ", Ok " + successful);
		if (tested == successful) {
			System.out.println("Everything Ok!");
		}
	}

	public static boolean testEdge() {
		boolean successful = true;

		Node a = new Node(1);
		Node b = new Node(2);

		Edge e = new Edge(a, b);
		if (!e.toString().equals("1,2")) {
			System.out.println("Incorrect Edge printing: " + e.toString());
			successful = false;
		}
		if (e.src() != a || e.dst() != b) {
			System.out.println("Incorrect Parameters set for Edge");
			successful = false;
		}

		Edge t = new Edge(b, a);
		if (e.toString().equals(t.toString())) {
			System.out.println("Edge should not default to be in both directions!");
			successful = false;
		}

		return successful;
	}

	public static boolean testNode() {
		boolean successful = true;

		Node a = new Node(1);
		Node b = new Node(2);
		Node c = new Node(3);

		if (a.id() != 1) {
			System.out.println("Node has incorrect ID.");
			successful = false;
		}
		if (!a.equals(a) || a.equals(b)) {
			System.out.println("equals() does not work correctly.");
			successful = false;
		}

		a.addEdge(b);
		b.addEdge(a);

		if (a.adjacentList().equals(b.adjacentList())) {
			System.out.println("Adjacency lists are multidirectional by default, but shouldn't");
			successful = false;
		}

		return successful;
	}

	public static boolean testGraph() {
		boolean successful = true;

		Graph fileGraph = Graph.fromFile("BspGraphKlein.graph");
		if (fileGraph == null) {
			System.out.println("Graph could not be loaded from file. Cannot continue testing.");
			return false;
		}
		if (!fileGraph.toString().equals(
		"0,2\n0,3\n0,4\n0,5\n2,0\n2,1\n2,3\n2,4\n2,5\n3,0\n3,2\n3,4\n3,5\n4,0\n4,2\n4,3\n4,5\n5,0\n5,1\n5,2\n5,3\n5,4\n1,2\n1,5\n"
		)) {
			System.out.println("Graph read from file differs from the Graph expected. Graph created: ");
			System.out.println(fileGraph.toString());
			successful = false;
		}

		return successful;
	}
}
