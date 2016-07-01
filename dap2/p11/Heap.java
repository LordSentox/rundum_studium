public class Heap {
	private int[] data;

	public Heap(int maxSize) {
		// Der erste Slot bleibt zur Vereinfachung der Rechnung leer und wird
		// die momentane Größe des Heaps enthalten.
		data = new int[maxSize + 1];
		data[0] = 0;
	}

	// Gibt den Index des linken Kindknotens des übergebenen
	// Knotens zurück.
	public int left(int node) {
		return node * 2;
	}

	// Gibt den Index des rechten Kindknoten des übergebenen
	// Knotens zurück.
	public int right(int node) {
		return node * 2 + 1;
	}

	// Gibt den Index des Vaterknotens des übergebenen
	// Knotens zurück.
	public int parent(int node) {
		// Wird immer abgerundet. So erhält man auch für den
		// rechten Kindknoten den korrekten Vaterknoten.
		return node / 2;
	}

	// Stellt die Heap-Eigenschaft für das angegebene Element
	// wieder her.
	public void heapify(int node) {
		if (node > data[0] || node < 1) {
			throw new IndexOutOfBoundsException();
		}

		int l = left(node);
		int r = right(node);
		int largest = node;
		if (l <= data[0] && data[l] > data[largest]) largest = l;
		if (r <= data[0] && data[r] > data[largest]) largest = r;

		// Der Knoten mit dem größten Schlüssel wird der neue Vaterknoten
		// innerhalb des Trios.
		if (largest != node) {
			int buf = data[node];
			data[node] = data[largest];
			data[largest] = buf;

			// Da der kleinere Knoten in den größeren Unterbaum geschoben wurde,
			// muss dieser u.U. weiter bearbeitet werden, um die Heapeigenschaft
			// wiederherzustellen.
			heapify(largest);
		}
	}

	// Fügt den angeführten Wert in den Heap ein.
	// der Index des Knotens, wo key eingefügt wurde wird zurückgegeben.
	public int insert(int key) {
		// Pointer auf die erste leere Stelle des Heaps.
		int ref = data[0] + 1;
		while (ref > 1 && data[parent(ref)] < key) {
			data[ref] = data[parent(ref)];
			ref = parent(ref);
		}

		data[ref] = key;
		++data[0];
		return ref;
	}

	// Gibt den Wert des obersten Knotens (den höchsten Schlüssel) zurück und
	// entfernt diesen Knoten aus dem Heap.
	public int extractMax() {
		if (data[0] < 1) {
			System.out.println("Kein Maximum gefunden.");
			return -1;
		}
		int max = data[1];
		// Setze letztes Element des Heaps als Wurzel ein.
		data[1] = data[data[0]];
		data[0]--;
		heapify(1);

		return max;
	}

	// Gibt den gesamten Heap aus.
	public void print() {
		// Hilfsvariable um zu wissen, wann der letzte Zeilenwechsel
		// stattgefunden hat.
		int lastNewLine = 1;
		for (int i = 1; i <= data[0]; ++i) {
			if (i == 2 * lastNewLine) {
				System.out.println();
				lastNewLine = i;
			}

			System.out.print(data[i] + " ");
		}

		System.out.println();
	}
}
