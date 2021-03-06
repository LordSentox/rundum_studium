#include "aufgabe2.h"
#include <pthread.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>


#define ANZAHL_TEAMS 4
#define ANZAHL_RUNDEN 10


/* Diesen Code nicht modifizieren */
struct Team teams[] = {{.name = "The Runtime Exceptions", .runtime = 1, .staffelstab = 1, .hand = 1},
                       {.name = "Ueberholverbot", .runtime = 2, .staffelstab = 2, .hand = 2},
                       {.name = "WiSo nur", .runtime = 5, .staffelstab = 3, .hand = 3},
                       {.name = "Die laufenden Kopplungen", .runtime = 3, .staffelstab = 4, .hand = 4}};

/* Sockel zum Halten des Staffelstabs */
staffelstab_t sockel;

/* Ab hier euren Code einfuegen */
pthread_mutex_t sockel_lock;

#include <stdlib.h>
#include <assert.h>

// Dieses Signal braucht keinen Mutex, da es nur von der Main-Funktion geändert
// wird, und ansonsten ausschließlich lesende Zugriffe hat, die von einer
// Änderung nicht korrumpiert werden können.
char starting_signal = 0;

// Ein Team, welches disqualifiziert wird, wird hier eingetragen.
char disqualified[ANZAHL_TEAMS];

int winners[ANZAHL_TEAMS];
int current_place = 0;
pthread_mutex_t current_place_lock;

// Lässte das angegebene Team starten, sobald der Startschuss gegeben wurde.
void *handle_team(void *arg) {
	int *team_num = (int*) arg;

	printf("Team '%s' ist für den Lauf bereit.\n", teams[*team_num].name);

	// Warte bis der Startschuss für alle Teams gegeben wird.
	while (!starting_signal) {}

	// Laufe die Runden. Starte bei Runde 1, um die Zahl für Menschen leichter
	// verständlich zu machen.
	int r;
	for (r = 1; r <= ANZAHL_RUNDEN && !disqualified[*team_num]; ++r) {
		// Der erste Läufer kann zwar umgehend beginnen. Alle anderen dieses
		// Teams müssen allerdings erst den Stab aus dem Sockel holen, sobald
		// der Lauf beginnt, bevor sie die Runde beginnen können.
		if (r != 1) {
			// Es dauert eine Sekunde bis zum Sockel für alle bereitstehenden Läufer.
			sleep(1);

			// Versuche einen Stab aus dem Sockel herauszunehmen.
			if (sockel != LEER) {
				// Der Stab kann genommen werden!
				teams[*team_num].hand = sockel;
				sockel = LEER;
				pthread_mutex_unlock(&sockel_lock);
			}
			else {
				// Jemand anderes hat sich den Stab bereits geschnappt. Das Team
				// wird disqualifiziert.
				disqualified[*team_num] = 1;
				break;
			}
		}

		// Der Läufer läuft jetzt die Runde und benötigt dafür die teaminterne
		// Rundenzeit.
		sleep(teams[*team_num].runtime);

		// Wenn dies nicht die letzte Runde ist, legt der Läufer den Stab in
		// den Sockel. Ansonsten kann er den Stab in der Hand behalten.
		if (r != ANZAHL_RUNDEN) {
			pthread_mutex_lock(&sockel_lock);
			// Das Schloss darf nur geöffnet sein, wenn der Sockel auch wirklich
			// leer war. Ansonsten hat der Schiedsrichter einen Fehler gemacht.
			assert(sockel == LEER);
			sockel = teams[*team_num].hand;
			teams[*team_num].hand = LEER;
		}

		printf("'%s' hat Runde %i beendet.\n", teams[*team_num].name, r);
	}

	if (!disqualified[*team_num]) {
        // Team wird in die Siegerliste eingetragen
        pthread_mutex_lock(&current_place_lock);
        winners[current_place] = *team_num;
        ++current_place;
        pthread_mutex_unlock(&current_place_lock);

		printf("'%s' hat den Lauf erfolgreich beendet! Das Team hatte am Anfang Stab %i, jetzt Stab %i\n", teams[*team_num].name, teams[*team_num].staffelstab, teams[*team_num].hand);
	}
	else {
        // Das Team wurde disqualifiziert, weshalb es keinen Siegplatz erhält.
		printf("'%s' wurde leider in Runde %i disqualifiziert..\n", teams[*team_num].name, r);
	}

	free(arg);
	pthread_exit(NULL);
}

int main(void) {
    // Am Anfang ist der Sockel leer.
    sockel = LEER;

	// Jedes Team bekommt einen eigenen Thread zugeteilt.
	pthread_t threads[ANZAHL_TEAMS];

    // Die Gewinnerliste muss initialisiert werden, da disqualifizierte Teams
    // Als nicht vorhanden eingetragen sein müssen.
    for (int i = 0; i < ANZAHL_TEAMS; ++i) {
        winners[i] = -1;
    }

	// Initialisiere sockel_lock, damit die Teams nicht gleichzeitig auf den
	// Sockel zugreifen können.
	pthread_mutex_init(&sockel_lock, NULL);

    // Damit zwei Teams nicht auf dem gleichen Platz landen können wird das
    // Foto-finish hier durch einen Mutex ersetzt;
    pthread_mutex_init(&current_place_lock, NULL);

	// Starte die Teamthreads.
	for (int i = 0; i < ANZAHL_TEAMS; ++i) {
		// Erstelle Kopie der Teamnummer, die an den Thread übergeben wird.
		int *team_number = (int*) malloc(sizeof(int));
		*team_number = i;

		disqualified[i] = 0;
		if (pthread_create(&threads[i], NULL, &handle_team, (void*) team_number)) {
			printf("Thread für Team %i konnte nicht erstellt werden.\n", i);
			return 1;
		}
	}

	// Die Teams sind bereit. Der Startschuss wird gegeben.
	printf("Startschuss wird gegeben in..\n");
	for (int s = 3; s > 0; --s) {
		printf("%i\n", s);
		sleep(1);
	}
	printf("Start!\n");
	starting_signal = 1;

	// Warte, bis alle Teams fertig sind.
	for (int i = 0; i < ANZAHL_TEAMS; ++i) {
		if (pthread_join(threads[i], NULL)) {
			printf("Warnung: Team %i konnte nicht vernünftig gejoined werden, nachdem der Lauf beendet wurde.\n", i);
		}
	}

	pthread_mutex_destroy(&sockel_lock);
    pthread_mutex_destroy(&current_place_lock);

    // Nachdem alle Läufe beendet wurden und die Teams vernünftig synchronisiert
    // sind, beginnt hier die Siegerehrung.
    printf("\n");
    printf("Die Sieger des Wettbewebs sind:\n");
    for (int i = 0; i < ANZAHL_TEAMS; ++i) {
        if (winners[i] != -1)
            printf("%i. Platz: '%s'\n", i + 1, teams[winners[i]].name);
        else
            printf("%i. Platz: Nicht vergeben\n", i + 1);
    }

	return 0;
}
