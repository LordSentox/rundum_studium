import java.util.Arrays;

public class CoinMachine {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Ungültiger Aufruf. Korrekter Aufruf: CoinMachine <Währung> <Betrag>");
			System.exit(1);
		}

		try {
			int[] w = null;
			if (args[0].equalsIgnoreCase("euro")) {
				w = new int[]{200, 100, 50, 20, 10, 5, 2, 1};
			}
			else if (args[0].equalsIgnoreCase("alternative")) {
				w = new int[]{200, 100, 50, 20, 10, 5, 4, 2, 1};
			}
			else {
				System.out.println("Unbekannte Währung. Bitte wählen sie entweder 'Euro' oder 'Alternative'");
				System.exit(2);
			}

			int amount = Integer.parseInt(args[1]);
			if (amount < 0) {
				System.out.println("Der eingegebene Wert muss positiv sein.");
				System.exit(3);
			}

			System.out.println("Berechne Wechselgeld.");
			int[] c = change(amount, w);
			System.out.println("Wechselgeld: " + Arrays.toString(c));
		}
		catch (Exception e) {
			System.out.println("Ein Argument konnte nicht korrekt gelesen werden. Bitte stellen Sie sicher, dass die Argumente im richtigen Format sind.");
		}
	}

	public static int[] change(int value, int[] w) {
		int[] change_real = new int[w.length];

		// Beginnt mit der größten Einheit und arbeitet sich langsam runter.
		for (int i = 0; i < w.length && value > 0; i++) {
			if (w[i] < value) {
				int amount = value / w[i];
				value -= w[i] * amount;
				change_real[i] = amount;
			}
			else {
				change_real[i] = 0;
			}
		}

		// Der übrige Wert muss 0 sein, ansonsten war die Währung nicht komplett.
		assert value == 0 : "Bitte überprüfen Sie die Währung.";

		return change_real;
	}
}
