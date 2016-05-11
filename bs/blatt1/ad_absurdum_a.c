#include <stdio.h>

// Rechnet a / b
int division(int a, int b) {
	return a / b;
}

int main(void) {
	while (1) {
		printf("Bitte Division eingeben: ");

		int a, b;
		if (scanf("%i/%i", &a, &b) == 2) {
			printf("Ergebnis: %i\n", division(a, b));
		}
		else {
			// Falls das Programm eine falsche Eingabe erhält, bricht es mit
			// Fehlercode 1 ab. (Sorry, aber das flushen des Input-Streams
			// wollte irgendwie nicht funktionieren.. :( )
			printf("Die übergebenen Werte sind in Fehlerhaften format.\n");
			printf("Bitte halten Sie sich an die Konvention <w1>/<w2>\n");
			return 1;
		}
	}

	return 0;
}
