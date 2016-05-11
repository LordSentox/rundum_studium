#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

// Die Funktion rechnet a / b, falls möglich. Wenn das Argument b = 0 ist
// versucht die Funktion <program> auszuführen. In diesem Fall wird der
// Rückgabewert des Programmes zurückgegeben.
int division(int a, int b) {
	if (b != 0) return a / b;

	if (fork() < 0) {
		// Im Falle r < 0 ist ein Fehler beim forken aufgetreten und das Programm
		// wird beendet.
		printf("Ein Fehler ist aufgetreten.");
		exit(1);
	}

	execlp("ps", "");
	return 0;
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
