import java.util.List;
import java.util.Random;

public class ConvexTest {
	public static void main(String[] args) {

		int count;
		try {
			count = Integer.parseInt(args[0]);
		} catch (Exception e) {
			count = 1000;
		}
		Point[] p = new Point[count];
		Random r = new Random();
		//Erzeugen von count (bzw 1000) zufälliger Punkte
		for (int i = 0; i < count; i++) {
			//nextDouble für nicht Punkte auch mit Dezimalkoordinaten
			p[i] = new Point(2, (r.nextDouble()) * r.nextInt(),
					(r.nextDouble()) * r.nextInt());
		}

		List<Point> hull = SimpleConvexHull.simpleConvex(p);
		String points = "";
		//Ausgabe aller eingegebenen Punkte
		for (Point point: p)points += point.toString();
		System.out.println("Points: " + points);
		System.out.println("Hull ("+hull.size()+" Elements): " + hull.toString());
	}
}
