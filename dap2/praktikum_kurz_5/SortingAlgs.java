public class SortingAlgs {
	// Wenn benchmark auf true gesetzt wird, so wird immer dann, wenn eine
	// Sortierung stattfindet die Zeit gemessen, die der jeweilige Algorithmus
	// für die Eingabe benötigte und ausgegeben.
	public static boolean benchmark = false;

	public static Benchmark bubble_sort(int[] a) {
		if (benchmark) {
			Benchmark b = new Benchmark();
			b.start();
			bubble_sort_real(a);
			b.stop();

			return b;
		}
		else {
			bubble_sort_real(a);
			return null;
		}
	}

	private static void bubble_sort_real(int[] a) {
		for (int i = 0; i < a.length; ++i) {
			for (int j = a.length - 1; j > i; --j) {
				if (a[j-1] > a[j]) {
					int tmp = a[j];
					a[j] = a[j-1];
					a[j-1] = tmp;
				}
			}
		}
	}


	public static Benchmark insertion_sort(int[] a) {
		if (benchmark) {
			Benchmark b = new Benchmark();
			b.start();
			insertion_sort_real(a);
			b.stop();

			return b;
		}
		else {
			insertion_sort_real(a);
			return null;
		}
	}

	private static void insertion_sort_real(int[] a) {
		// Das erste Element ist dadurch, dass die Länge 1 ist bereits sortiert.
		// Deshalb beginnt der Algorithmus beim zweiten Element.
		for (int i = 1; i < a.length; ++i) {
			int comp = a[i];

			int j = i - 1;
			while (j >= 0 && comp < a[j]) {
				// Da das Element kleiner ist als die anderen muss der gesamte
				// Array nach rechts verschoben werden.
				a[j + 1] = a[j];
				--j;
			}

			// Einfügen an der korrekten Stelle des Arrays.
			a[j + 1] = comp;
		}
	}

	public static Benchmark merge_sort(int[] a) {
		if (benchmark) {
			Benchmark b = new Benchmark();
			b.start();
			merge_sort_real(a, 0, a.length - 1);
			b.stop();

			return b;
		}
		else {
			merge_sort_real(a, 0, a.length - 1);
			return null;
		}
	}

	// Sortiert das übergebene Array vom Index start zum Index end mittels
	// MergeSort.
	private static void merge_sort_real(int[] a, int start, int end) {
		if (start < end) {
			int middle = (start + end) / 2;
			merge_sort_real(a, start, middle);
			merge_sort_real(a, middle + 1, end);
			merge(a, start, middle, end);
		}
	}

	// Fügt zwei sortierte Teile zu einem gesamten sortierten Teil zusammen.
	private static void merge(int[] a, int start, int middle, int end) {
		// Helfer-Arrays, in die die Anfangswerte für das spätere
		// Reißverschlussverfahren in den Originiala zwischengespeichert
		// werden.
		int[] left = new int[middle - start + 1];
		int[] right = new int[end - middle];

		// Zunächst ein Backup des Anfangsas in die Hilfsas.
		for (int i = start; i <= end; ++i) {
			if (i <= middle) left[i - start] = a[i];
			else right[i - middle - 1] = a[i];
		}

		// Reißverschlussverfahren der Hilfsas in den Anfangsa.
		int lCount = 0;
		int rCount = 0;
		for (int i = start; i <= end; ++i) {
			// Beide Hilfsas besitzen noch unsortierte Elemente.
			if (lCount < left.length && rCount < right.length) {
				if (left[lCount] < right[rCount]) {
					a[i] = left[lCount];
					++lCount;
				}
				else {
					a[i] = right[rCount];
					++rCount;
				}
			}
			else if(lCount < left.length) {
				// Da nur noch das linke Hilfsa Elemente besitzt wird das
				// nächste eingefügt.
				a[i] = left[lCount];
				++lCount;
			}
			else {
				a[i] = right[rCount];
				++rCount;
			}
		}
	}

	// Implementiert die Nutzerfunktion für QuickSort, welcher eigentlich nur
	// einen vernünftigen Rekursionsaufruf auf die eigentliche Methode startet.
	// Gibt das Benchmark zurück, falls eins durchgeführt wurde, ansonsten null.
	public static Benchmark quick_sort(int[] a) {
		// Benchmark durchführen, falls gegeben.
		if (benchmark) {
			Benchmark b = new Benchmark();
			b.start();
			quick_sort_real(a, 0, a.length - 1);
			b.stop();

			return b;
		}
		else {
			quick_sort_real(a, 0, a.length - 1);
			return null;
		}
	}

	private static void quick_sort_real(int[] a, int l, int r) {
		// Rekursionsabbruch
		if (l >= r)
			return;

		int i = l;
		int j = r;
		int pivot = a[(l + r) / 2];

		// Das Umstellen der Werte bewerkstelligen, um die Invariante zu erfüllen.
		while (i <= j) {
			while (a[i] < pivot) i++;
			while (a[j] > pivot) j--;

			if (i <= j) {
				// Switchen der Werte in a[i] und a[j]
				int tmp = a[i];
				a[i] = a[j];
				a[j] = tmp;
				i++;
				j--;
			}
		}

		// Quicksort auf die einzelnen Partitionen durchführen.
		quick_sort_real(a, l, j);
		quick_sort_real(a, i, r);
	}
}
