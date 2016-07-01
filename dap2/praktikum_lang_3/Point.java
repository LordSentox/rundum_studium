public class Point {
	private double[] coordinates;

	public Point(int dim, double ... coordinates) {
		// Diese Abfrage überprüft ebenfalls, ob 
		if (coordinates.length != dim)
			throw new IllegalArgumentException();

		this.coordinates = coordinates;
	}
}
