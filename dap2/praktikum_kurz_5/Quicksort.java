import java.util.Random;

public class Quicksort {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dieses Programm erwartet die Länge des zufällig zu befüllenden Arrays. Bitte geben Sie (nur) einen gültigen Wert ein.");
			System.exit(1);
		}

		try {
			int length = Integer.parseInt(args[0]);
			if (length <= 0) {
				System.out.println("Bitte geben Sie nur positive Integer-Werte als Array-Länge an.");
				System.exit(2);
			}

			SortingAlgs.benchmark = true;

			int[] a = gen_array(length);
			// Da jeder Algorithmus den gleichen Array erhalten soll, wird
			// dieser temporärer dafür sorgen, dass nicht alle späteren arrays
			// ein sortiertes erhalten.
			int[] tmp = new int[length];

			// BubbleSort
			System.arraycopy(a, 0, tmp, 0, a.length);
			System.out.print("Führe BubbleSort aus... ");
			Benchmark bubble = SortingAlgs.bubble_sort(tmp);
			System.out.println("Fertig");
			assert is_sorted(tmp) : "Fehler: Array wurde nicht korrekt sortiert.";

			// InsertionSort
			System.arraycopy(a, 0, tmp, 0, a.length);
			System.out.print("Führe InsertionSort aus... ");
			Benchmark insert = SortingAlgs.insertion_sort(tmp);
			System.out.println("Fertig");
			assert is_sorted(tmp) : "Fehler: Array wurde nicht korrekt sortiert.";

			// MergeSort
			System.arraycopy(a, 0, tmp, 0, a.length);
			System.out.print("Führe MergeSort aus... ");
			Benchmark merge = SortingAlgs.merge_sort(tmp);
			System.out.println("Fertig");
			assert is_sorted(tmp) : "Fehler: Array wurde nicht korrekt sortiert.";

			// QuickSort
			System.arraycopy(a, 0, tmp, 0, a.length);
			System.out.print("Führe QuickSort aus... ");
			Benchmark quick = SortingAlgs.quick_sort(tmp);
			System.out.println("Fertig");
			assert is_sorted(tmp) : "Fehler: Array wurde nicht korrekt sortiert.";

			System.out.println("Echtzeit: ");
			System.out.println("BubbleSort: " + bubble.msecs() + "ms");
			System.out.println("InsertionSort: " + insert.msecs() + "ms");
			System.out.println("MergeSort: " + merge.msecs() + "ms");
			System.out.println("QuickSort: " + quick.msecs() + "ms");

			System.out.println("% - BubbleSort: ");
			System.out.println("BubbleSort: " + 100 + "%");
			System.out.println("InsertionSort: " + (float)((double)insert.msecs() / bubble.msecs() * 100) + "%");
			System.out.println("MergeSort: " + (float)((double)merge.msecs() / bubble.msecs() * 100) + "%");
			System.out.println("QuickSort: " + (float)((double)quick.msecs() / bubble.msecs() * 100) + "%");
		}
		catch (Exception e) {
			System.out.println("Das Argument war kein gültiger Integer-Wert. Bitte versuchen Sie es nochmal.");
		}
	}

	public static int[] gen_array(int length) {
		Random gen = new Random();
		int[] a = new int[length];

		for (int i = 0; i < length; ++i) {
			a[i] = gen.nextInt();

			// Nur positive Zahlen, um es für Menschen einfacher erkenntlich
			// zu machen.
			if (a[i] < 0) a[i] *= -1;
		}

		return a;
	}

	public static boolean is_sorted(int[] a) {
		for (int i = 0; i < a.length - 1; ++i) {
			if (i > a[i+1])
				return false;
		}

		return true;
	}
}
