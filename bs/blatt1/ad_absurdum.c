#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int division(int a, int b, const char *program) {
	if (b != 0) return a / b;

	int r = fork();
	if (r == 0) {
		exit(execlp(program, ""));
	}
	else if (r > 0) {
		int v;
		waitpid(r, &v, 0);
		return v;
	}

	printf("Ein Fehler ist aufgetreten.");
	exit(1);
}

int main(int argc, char* argv[]) {
	char* program = "ps";
	if (argc > 1) {
		program = argv[1];
	}

	while (1) {
		printf("Bitte Division eingeben: ");

		int a, b;
		if (scanf("%i/%i", &a, &b) == 2) {
			printf("Ergebnis: %i\n", division(a, b, program));
		}
		else {
			printf("Die übergebenen Werte sind in Fehlerhaften format.\n");
			printf("Bitte halten Sie sich an die Konvention <w1>/<w2>\n");
			return 1;
		}
	}

	return 0;
}
