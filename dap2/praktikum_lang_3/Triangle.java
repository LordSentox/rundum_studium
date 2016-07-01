public class Triangle extends Simplex {

	public Triangle(Point... points) {
		//Aufruf des geerbten Konstruktors von Simplex mit den Parametern '2' und den Werten vom Triangle-Konstruktor
		super(2, points);		
	}

	@Override
	public boolean validate() {
		return d == 2 && points.length == 3 && points[0].dim() == 2
				&& points[1].dim() == 2 && points[2].dim() == 2;
		/*
	     * this.d = Überprüfung auf zwei Dimensionen
	     * this.points.length = Überprüfung auf das Vorhandensein dreier Punkte
	     * points[0] = Überprüfen ob Punkt 1 genau zwei Dimensionen hat
	     * points[1] = Überprüfen ob Punkt 2 genau zwei Dimensionen hat
	     * points[2] = Überprüfen ob Punkt 3 genau zwei Dimensionen hat
	     */
	}

}
