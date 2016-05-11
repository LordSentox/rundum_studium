public class Edge extends Simplex {

	public Edge(Point... points) {
		super(1, points);
	}

	//�berpr�fung ob es ein zul�ssiges Simplex ist (Kante als einfachstes zweidimensionales Simplex)
	@Override
	public boolean validate() {
		return points.length == 2 && points[0].dim() == 2
				&& points[1].dim() == 2;
		/*
	     * this.points.length ^= �berpr�fung auf das Vorhandensein zweier Punkte
	     * points[0] ^= �berpr�fen ob Punkt 1 genau zwei Dimensionen hat
	     * points[1] ^= �berpr�fen ob Punkt 2 genau zwei Dimensionen hat
	     */
	}

}
