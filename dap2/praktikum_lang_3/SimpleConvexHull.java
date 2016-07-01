import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SimpleConvexHull {
	public static List<Point> simpleConvex(Point... p) {

		// Im R2 werden mehr als 3 Punkte ben�tigt, damit �berhaupt die
		// M�glichkeit besteht keine Konvexe H�lle bereits vorliegen zu haben
		if (p.length <= 3)
			return Arrays.asList(p);
		boolean valid;
		List<Point> hull = new LinkedList<Point>();
		List<Edge> hullEdge = new LinkedList<Edge>();

		// F�r Alle Nicht Identischen Paare:
		for (Point p1 : p) {
			for (Point p2 : p) {
				if (p1.equals(p2))
					continue;

				// Setze valid flag standardm��ig auf true
				valid = true;

				for (Point p3 : p) {
					if (p1.equals(p3) || p2.equals(p3))
						continue;
					// links von der gerichteten Gerade durch p1p2?
					if (side(p1, p2, p3) < 0) {
						// Sollte auch nur ein punkt links von der Gerade sein,
						// so geh�rt die Kante nicht zur Konvexen H�lle
						valid = false;
						break;
					}

				}
				// F�ge Punkt ggf zur Kantenh�lle Hinzu
				if (valid)
					hullEdge.add(new Edge(p1, p2));
			}
		}

		// Extrahiere StartPunkt f�r die Konvexe H�lle
		hull.add(hullEdge.get(0).points[0]);
		hullEdge.remove(0);

		// Merke letzten Punkt um zu vergleichen
		Point last = hull.get(0);

		// Solange noch Kanten �brig sind
		while (!hullEdge.isEmpty()) {
			// gehe alle Kanten durch um den n�chsten Punkt zu finden - f�ge
			// diesen dann der H�lle hinzu
			for (Edge e : hullEdge) {
				/*if (e.points[0].equals(last)) {
					hull.add(e.points[1]);
					hullEdge.remove(e);
					last = e.points[1];
					break;
				} else
					*/
					if (e.points[1].equals(last)) {
					hull.add(e.points[0]);
					hullEdge.remove(e);
					last = e.points[0];
					break;
				}
			}
		}

		return hull;
	}

	private static double side(Point p1, Point p2, Point compare) {
		// (x2-x1)*(yc-y1) - (xc-x1) * (y2-y1) - Simples Kreuzprodukt zur Abstandsberechnung 0=Auf der
		// Gerade, R+ = rechts, R- = links
		return (p2.get(0) - p1.get(0)) * (compare.get(1) - p1.get(1))
				- (compare.get(0) - p1.get(0)) * (p2.get(1) - p1.get(1));
	}
}
