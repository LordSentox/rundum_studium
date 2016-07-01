import java.util.Random;

public class Application {

	public static void main(String[] args) {

		double x1 = 0, x2 = 0, x3 = 0, y1 = 0, y2 = 0, y3 = 0;
		// Bei manueller Eingabe von sechs Werten
		if (args.length == 6) {
			try {
				x1 = Double.parseDouble(args[0]);
				y1 = Double.parseDouble(args[1]);
				x2 = Double.parseDouble(args[2]);
				y2 = Double.parseDouble(args[3]);
				x3 = Double.parseDouble(args[4]);
				y3 = Double.parseDouble(args[5]);
			} catch (Exception e) {
				System.out.println("Could not parse parameter to Double");
				System.exit(-1);
			}
			// Zufälliges Dreieck bei keiner Eingabe
		} else if (args.length == 0) {
			Random rand = new Random();
			x1 = rand.nextInt(2000) - 1000;
			x2 = rand.nextInt(2000) - 1000;
			x3 = rand.nextInt(2000) - 1000;
			y1 = rand.nextInt(2000) - 1000;
			y2 = rand.nextInt(2000) - 1000;
			y3 = rand.nextInt(2000) - 1000;

		} else {
			System.out
					.println("Invalid number of parameters. Usage: java Application [x1 x2 x3 y1 y2 y3]");
			System.exit(-1);
		}
		// Erstellen der Eckpunkte aus den eingegebenen Wertepaaren
		Point p1 = new Point(2, x1, y1);
		Point p2 = new Point(2, x2, y2);
		Point p3 = new Point(2, x3, y3);
		// Erstellen des Dreiecks aus den Eckpunkten
		Triangle t = new Triangle(p1, p2, p3);
		System.out.println("Perimeter: " + t.perimeter() + "|(" + x1 + "," + y1
				+ "),(" + x2 + "," + y2 + "),(" + x3 + "," + y3 + ")");

	}
}
