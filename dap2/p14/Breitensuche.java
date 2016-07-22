import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

enum Colour {
	White,
	Grey,
	Black
}

public class Breitensuche {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("This program takes exactly two parameters as arguments. Please make sure to provide them.");
			return;
		}

		Graph graph = Graph.fromFile(args[0]);
		if (graph == null) {
			System.out.println("Please provide valid graph file.");
			return;
		}

		int start = -1;
		try {
			start = Integer.parseInt(args[1]);
			if (start < 0 || !graph.contains(start)) {
				throw new IndexOutOfBoundsException();
			}
		}
		catch (Exception e) {
			System.out.println("Could not read start knot. Please provide positive Integer contained in the Graph.");
			return;
		}

		ArrayList<Integer> d = bfs(graph, start);
		if (d == null) {
			System.out.println("Failed to run 'Breitensuche'.");
			return;
		}

		System.out.println("Distances checked: ");
		for (int i = 0; i < d.size(); ++i) {
			if (d.get(i) != -1)
				System.out.println(start + " -> " + i + " : " + d.get(i));
		}
	}

	public static ArrayList<Integer> bfs(Graph g, int s) {
		int maxId = g.maxId();
		ArrayList<Colour> colour = new ArrayList<Colour>();
		ArrayList<Integer> d = new ArrayList<Integer>();
		ArrayList<Node> p = new ArrayList<Node>();
		Queue<Node> q = new LinkedList<Node>();

		for (int i = 0; i <= maxId; ++i) {
			colour.add(Colour.White);
			d.add(-1);
			p.add(null);
		}

		// Initialise Starting node
		if (!g.contains(s)) {
			System.out.println("Invalid starting node.");
			return null;
		}
		colour.set(s, Colour.Grey);
		d.set(s, 0);
		q.add(g.getNode(s));

		while (!q.isEmpty()) {
			Node u = q.remove();
			for (Edge v : u.adjacentList()) {
				Node dst = v.dst();
				if (colour.get(dst.id()) == Colour.White) {
					colour.set(dst.id(), Colour.Grey);
					d.set(dst.id(), d.get(u.id()) + 1); p.set(dst.id(), u);
					q.add(dst);
				}
			}
			colour.set(u.id(), Colour.Black);
		}

		System.out.println("Paths taken: ");
		printPathsTaken(p);

		return d;
	}

	public static void printPathsTaken(ArrayList<Node> p) {
		for (int i = 0; i < p.size(); ++i) {
			Stack<Integer> path = new Stack<Integer>();
			int current = i;
			while (p.get(current) != null) {
				path.push(p.get(current).id());
				current = p.get(current).id();
			}
			while (!path.isEmpty()) {
				System.out.print(path.pop() + " -> ");
			}

			System.out.println(i);
		}
	}
}
