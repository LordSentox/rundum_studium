public abstract class Simplex {
	protected int d;
	protected Point[] points;

	protected Simplex(int d, Point[] points) {
		// Definition der Punkte in der Dimension d
		// Exception wenn Dimension inkorrekt
		if (d < 1 || points.length != d + 1)
			throw new IllegalArgumentException();
		this.d = d;
		this.points = points;

	}

	// Summe der Seitenl�ngen
	public double perimeter() {
		double per = 0;
		// Erstellen eines EuklidDistance-Objekts zur bestimmung der seitenl�ngen
		Distance dist = new EuklidDistance();
		for (int i = 0; i < d; i++)
			// Addieren der Distanz zwischen dem Punktepaar i und i+1 um je eine
			// Seitenl�nge zu bestimmen
			per += dist.distance(points[i], points[i + 1]);
		// Seitenl�nge zwischen erstem und letztem Punkt
		per += dist.distance(points[d], points[0]);
		return per;
	}

	public abstract boolean validate();
}
