public class Blatt3 {
	public static void main(String[] args) {
		// Liest die Zeit aus, mit der die gesuchte Feldgröße bestimmt werden
		// soll. Diese wird später mittels Intervallschachtelung bestimmt.
		if (args.length != 1) {
			System.out.println("Fehler. Bitte geben Sie die Laufzeit ein, die Bubblesort benötigen soll.");
			System.exit(1);
		}

		try {
			float chosenTime = Float.parseFloat(args[0]);

			if (chosenTime <= 0.0)
				throw new IllegalArgumentException();

			// Finde die korrekte länge für die angegebene Zeit.
			int correctAmount = lengthByTime(chosenTime);
			System.out.println("Die korrekte Länge für die angegebene Zeit (±100ms) für diese Maschine ist: " + correctAmount);
		}
		catch (Exception e) {
			System.out.println("Die Zeitangabe ist in einem ungültigen Format. Bitte geben Sie nur positive Dezimalzahlen an.");
		}
	}

	public static int lengthByTime(float time) {
		assert(time > 0.0) : "Übergebene Zeit war keine Dezimalzahl > 0";

		return lengthByTimeR(0, 1000, -1, -1, (long)(time * 1000.0));
	}

	public static int lengthByTimeR(int min, int max, long minMeasurement, long maxMeasurement, long timeMs) {
		System.out.println("Status: [min] = " + min + "(" + minMeasurement + "ms)" + " [max] = " + max + "(" + maxMeasurement + "ms)");

		// Ermittle die Zeiten, sofern sie noch nicht vorgegeben sind.
		Clock clock = new Clock();
		if (minMeasurement == -1) {
			int[] a = genArray(min);
			clock.restart();
			bubbleSort(a);
			minMeasurement = clock.elapsed();
		}

		System.gc();

		if (maxMeasurement == -1) {
			int[] a = genArray(max);
			clock.restart();
			bubbleSort(a);
			maxMeasurement = clock.elapsed();
		}

		System.gc();

		// Meaurement

		// Dies sollte nie passieren, wenn die Rekursion ohne unnötigen Overhead
		// ausgeführt wird und die Eingabegröße vernünftig ist.
		if (timeMs < minMeasurement) {
			return min;
		}


		// Ein größerer Array wird benötigt.
		if (timeMs > maxMeasurement) {
			return lengthByTimeR(max, max * 2, maxMeasurement, -1, timeMs);
		}

		// Mittlere Laufzeit ermitteln, um die Rekursion fortsetzen zu können.
		int mid = (min + max) / 2;
		long midMeasurement;
		{
			int[] a = genArray(mid);
			clock.restart();
			bubbleSort(a);
			midMeasurement = clock.elapsed();
		}

		System.gc();

		// Rekursionsabbruch, wenn die Zeit im Rahmen von 100 ms liegt.
		// Toleranz ist also ±100ms.
		if (timeMs >= midMeasurement - 100 && timeMs <= midMeasurement + 100) {
			System.out.println("Richtige Elementanzahl gefunden! Exakte Dauer war: " + midMeasurement + "ms");
			return mid;
		}
		else if (timeMs > midMeasurement) {
			return lengthByTimeR(mid, max, midMeasurement, maxMeasurement, timeMs);
		}
		else {
			return lengthByTimeR(min, mid, minMeasurement, midMeasurement, timeMs);
		}
	}

	public static int[] genArray(int length) {
		assert(length >= 0) : "Cannot create array of negative length.";

		int[] a = new int[length];
		for (int i = 0; i < a.length; ++i) {
			a[i] = (a.length - i);
		}

		return a;
	}

	public static void bubbleSort(int[] array) {
		for (int i = 0; i < array.length; ++i) {
			for (int j = array.length - 1; j > i; --j) {
				if (array[j-1] > array[j]) {
					int tmp = array[j];
					array[j] = array[j-1];
					array[j-1] = tmp;
				}
			}
		}
	}

	public static boolean isSorted(int[] array) {
		for (int i = 0; i < array.length - 1; ++i) {
			if (i > array[i+1])
				return false;
		}

		return true;
	}
}
