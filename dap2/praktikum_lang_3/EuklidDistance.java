//Ja, ein Distance könnte natürlich auch als funktionales Interface genutzt werden.
public class EuklidDistance implements Distance{

	//Implementierung der Methode distance aus dem Interface 'distance'
	//Berechnung nach Pythagoras 
	@Override
	public double distance(Point p1, Point p2) {
		//Prüfen ob die Dimension übereinstimmt
		if (p1.dim() != p2.dim()) throw new IllegalArgumentException();
		
		double sum = 0;		
		for (int i = 0; i < p1.dim(); i++){
			//(Koordinate Punkt 1 minus Koordinate Punkt 2) hoch 2.0
			sum += Math.pow(p1.get(i) - p2.get(i), 2);
		}
		//Rückgabe der Wurzel vom vorher errechneten 'sum' als Distanz
		return Math.sqrt(sum);
	}

}
