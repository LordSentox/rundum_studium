import java.util.Random;

public class PriorityQueue {
	public static Random gen = new Random();

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Ungültige Paramater. Erwartete Parameter: ");
			System.out.println("1: Anzahl der zu bearbeiteten Jobs zu Anfang.");
			System.out.println("2: Anzahl der auszuführenden zufälligen Operationen.");
			System.exit(1);
		}

		try {
			int n = Integer.parseInt(args[0]);
			int k = Integer.parseInt(args[1]);
			if (n < 0 || k < 0) {
				System.out.println("Argumente müssen größer als 0 sein.");
				System.exit(2);
			}

			Heap h = new Heap(n + k);
			for (int i = 0; i < n; ++i) {
				h.insert(gen.nextInt(100 + 1));
			}

			System.out.println("Anfangs-Heap: ");
			h.print();

			// Führe k nach den Spezifikationen zufällige Operationen durch.
			for (int i = 0; i < k; ++i) {
				if (gen.nextInt(4) != 0) {
					// Der bearbeitete Job ist fertig und der nächste kann vom
					// Heap genommen werden.
					System.out.println("Ein Job mit Priorität " + h.extractMax() + " wird jetzt bearbeitet.");
				}
				else {
					int priority = gen.nextInt(100 + 1);
					System.out.println("Ein neuer Job mit Priorität " + priority + " wurde in die Warteschlange an Stelle " + h.insert(priority) + " eingefügt.");
				}

				System.out.println("Warteschlange: ");
				h.print();
			}
		}
		catch (Exception e) {
			System.out.println("Die übergebenen Argumente haben das falsche Format.");
			System.out.println("Bitte stellen Sie sicher, dass es zwei positive ganze Zahlen sind.");
		}
	}

	public void addRandomJob(Heap h) {
		// Job erhält eine zufällige Priorität zwischen 0 und 100;
	}
}
