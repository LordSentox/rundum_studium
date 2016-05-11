
public class Point {
	private double[] values;

	// Konstruktor mit Eingaben für die Dimension d und den dazugehörigen Werten values
	public Point(int d, double... values) {
		// Überprüfung von korrekter Dimension
		if (d < 1 || values.length != d) throw new IllegalArgumentException();
		// Zuweisung der Werte auf das neu erstellte Point-Objekt
		this.values = values;
	}

	// Rückgabe der Koordinate
	public double get(int i) {
		// Exception bei Eingabe zu hoher Parameter
		if (i >= values.length || i < 0) throw new IllegalArgumentException();
		// Rückgabe der Koordinate des Punktes i
		return values[i];
	}

	// Rückgabe der Dimension des aktuellen Objekts
	public int dim() {
		return values.length;
	}

	//Ausgabe der Dimension und Koordinaten als String
	@Override
	public String toString() {
		String ret = "Dim: " + d;
		for(double coord: values){
			ret += "|" + coord;
		}
		return ret;
	}
}
