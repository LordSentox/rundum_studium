import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ConvexTriangleTest {

	public static void main(String[] args) {
		//Eckpunkte des Dreiecks
		Point tp1 = new Point(2, 10, 10);
		Point tp2 = new Point(2, 100, 10);
		Point tp3 = new Point(2, 10, 100);

		
		//Vergleichshülle
		List<Point> hullT = SimpleConvexHull.simpleConvex(tp1, tp2, tp3);

		//Default: 1000 Punkte - sollte ein integer übergeben werden so können auch mehr/weniger punkte generiert werden
		int count;
		try {
			count = Integer.parseInt(args[0]);
		} catch (Exception e) {
			count = 1000;
		}

		Point check;
		List<Point> hullCheck;
		List<Point> insideP = new LinkedList<Point>();
		List<Point> outsideP = new LinkedList<Point>();
		Random r = new Random();
		for (int i = 0; i < count; i++) {
			// Generiere Zufallspunkt und Vergleichshülle - kompletter
			// Integerbereich ist sinnlos also hier beschränkung auf <=150
			check = new Point(2, (r.nextDouble()) * r.nextInt(150),
					(r.nextDouble()) * r.nextInt(150));
			hullCheck = SimpleConvexHull.simpleConvex(tp1, tp2, tp3, check);

			// Wenn sich Mengen gegenseitig enthalten sind sie gleich
			if (hullCheck.containsAll(hullT) && hullT.containsAll(hullCheck))
				insideP.add(check);
			else
				outsideP.add(check);
		}
		//Ausgabe der Listen
		System.out.println("Inside: " + insideP.toString());
		System.out.println("Outside: " + outsideP.toString());

	}

}
