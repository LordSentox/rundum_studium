/* Noetige Includes */
#define _POSIX_SOURCE	2
#define _POSIX_C_SOURCE	2
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <sys/types.h>
#include <signal.h>

/* Variablen fuer die beiden Mitarbeiter Threads */
pthread_t mitarbeiter_a_thread;
pthread_t mitarbeiter_b_thread;

/* Moegliche Status Werte eines Mitarbeiters */
enum STATUS {
	andere_arbeit,
	hole_klausuren,
	hole_liste,
	korrigieren};

/* Aktueller Status der beiden Mitarbeiter */
enum STATUS mitarbeiter_a_status = andere_arbeit;
enum STATUS mitarbeiter_b_status = andere_arbeit;

/* Semaphoren */
sem_t klausuren;
sem_t liste;

/* Deklaration der Funktionen */
void *mitarbeiter_a(void *);
void *mitarbeiter_b(void *);
void andere_arbeit_ausfuehren(char);
void deadlock_erkennung(void);
void programm_abbruch(int sig);

int main (void) {
	/*
	 * Signalhandler fuer den Programmabruch registrieren.
	 * Bei z.B. CTRL-C aus der Shell wird die Funktion programm_abbruch ausgefuehrt.
	 */
	struct sigaction aktion;
	aktion.sa_handler = &programm_abbruch;
	aktion.sa_flags = 0;
	sigemptyset(&aktion.sa_mask);
	if (sigaction(SIGINT, &aktion, NULL) == -1) {
		perror("Konnte Signalhandler nicht setzen");
		exit(EXIT_FAILURE);
	}

	/*
	 * Initialisiere Semaphoren, die die Liste mit den Studenten und den
	 * Klausurenstapel darstellen.
	 */
	if (sem_init(&klausuren, 0, 1) == -1) {
		perror("Klausuren-Semaphore konnte nicht initialisiert werden.");
		exit(EXIT_FAILURE);
	}
	if (sem_init(&liste, 0, 1) == -1) {
		perror("Listen-Semaphore konnte nicht initialisiert werden.");
		exit(EXIT_FAILURE);
	}

	/*
	 * Die Mitarbeiter-Threads werden nun erzeugt.
	 */
	if (pthread_create(&mitarbeiter_a_thread, NULL, &mitarbeiter_a, NULL) != 0) {
		perror("Konnte thread fuer Mitarbeiter A nicht erzeugen");
		exit(EXIT_FAILURE);
	}

	if (pthread_create(&mitarbeiter_b_thread, NULL, &mitarbeiter_b, NULL) != 0) {
		perror("Konnte thread fuer Mitarbeiter B nicht erzeugen");
		exit(EXIT_FAILURE);
	}

	/*
	 * Der Vaterprozess uebernimmt die deadlock_erkennung.
	 */
	deadlock_erkennung();

	return 0;
}

/*
 * Die Funktion der beiden Threads (Mitarbeiter_A und Mitarbeiter_B).
 */
void *mitarbeiter_a(void *arg) {
	char name = 'A';

	printf("Mitarbeiter %c: Ich fange jetzt an.\n", name);
	sleep(1);

	while (1) {
		/**
		 * Der Mitarbeiter führt eine andere Arbeit aus.
		 */
		printf("Mitarbeiter %c: Jetzt arbeite ich an etwas anderem.\n", name);
		mitarbeiter_a_status = andere_arbeit;
		andere_arbeit_ausfuehren(name);

		/**
		 * ! ! ! HIER EUREN CODE EINFÜGEN ! ! !
		 *
		 * Hier ist die weitere Vorgehensweise von Mitarbeiter A zu implementieren:
		 * - Klausuren holen (mit Hilfe von sleep() und Semaphore; Dauer: 4 Sekunden)
		 * - Liste holen (mit Hilfe von sleep() und Semaphore; Dauer: 3 Sekunden)
		 * - korrigieren (Dauer: 5 Sekunden)
		 * - Liste zurueckbringen
		 * - Klausuren zurueckbringen
		 */
		// Klausuren holen.
		printf("Mitarbeiter %c: Jetzt hole ich die Klausuren.\n", name);
		mitarbeiter_a_status = hole_klausuren;
		sleep(4);
		sem_wait(&klausuren);

		// Liste holen.
		printf("Mitarbeiter %c: Jetzt hole ich die Liste.\n", name);
		mitarbeiter_a_status = hole_liste;
		sleep(3);
		sem_wait(&liste);

		// Korrigieren.
		printf("Mitarbeiter %c: Jetzt korrigiere ich.\n", name);
		sleep(5);

		// Zurückbringen von Klausuren und Liste.
		// Da die Angaben in den Aufgaben nicht ganz eindeutig interpretieren
		// konnte, gehe ich hier einfach davon aus, dass hier, da keine weitere
		// Zeitangabe gemacht wurde, nach keiner zusätzlichen Verzögerung
		// ist.
		sem_post(&liste);
		sem_post(&klausuren);
	}
	pthread_exit(NULL);
}

void *mitarbeiter_b(void *arg) {
	char name = 'B';

	printf("Mitarbeiter %c: Ich fange jetzt an.\n", name);

	while (1) {
		/**
		 * Der Mitarbeiter fuehrt eine andere Arbeit aus.
		 */
		printf("Mitarbeiter %c: Jetzt arbeite ich an etwas anderem.\n", name);
		mitarbeiter_b_status = andere_arbeit;
		andere_arbeit_ausfuehren(name);

		/**
		 * ! ! ! HIER EUREN CODE EINFÜGEN ! ! !
		 *
		 * Hier ist die weitere Vorgehensweise von Mitarbeiter B zu implementieren:
		 * - Liste holen (mit Hilfe von sleep() und Semaphore; Dauer: 3 Sekunden)
		 * - Klausuren holen (mit Hilfe von sleep() und Semaphore; Dauer: 4 Sekunden)
		 * - korrigieren (Dauer: 5 Sekunden)
		 * - Klausuren zurueckbringen
		 * - Liste zurueckbringen
		 */
		// Liste holen.
		printf("Mitarbeiter %c: Jetzt hole ich die Liste.\n", name);
		mitarbeiter_b_status = hole_liste;
		sleep(3);
		sem_wait(&liste);

		// Klausuren holen.
		printf("Mitarbeiter %c: Jetzt hole ich die Klausuren.\n", name);
		mitarbeiter_b_status = hole_klausuren;
		sleep(4);
		sem_wait(&klausuren);

		// Korrigieren.
		printf("Mitarbeiter %c: Jetzt korrigiere ich.\n", name);
		sleep(5);

		// Zurückbringen von Klausuren und Liste.
		// Da die Angaben in den Aufgaben nicht ganz eindeutig interpretieren
		// konnte, gehe ich hier einfach davon aus, dass hier, da keine weitere
		// Zeitangabe gemacht wurde, nach keiner zusätzlichen Verzögerung
		// ist.
		sem_post(&klausuren);
		sem_post(&liste);
	}
	pthread_exit(NULL);
}

/*
 * Die Funktion fuer einen Mitarbeiter-Thread.
 * Hier wird die andere Arbeit implementiert.
 */
void andere_arbeit_ausfuehren(char name) {
	static int i = 5;
	int dauer;

	/*
	 * Mitarbeiter A arbeitet immer fuer 8 Zeiteinheiten, Mitarbeiter B fuer unterschiedliche Zeiteinheiten.
	 * Durch diesen zeitlichen Ablauf wird ein Deadlock provoziert.
	 */
	if (name == 'A') {
		printf("Mitarbeiter %c: Meine andere Arbeit dauert 8 Sekunden.\n", name);
		sleep(8);
	} else {
		if (i >= 8) {
			i = 0;
		}
		dauer = (8 - i);
		printf("Mitarbeiter %c: Meine andere Arbeit dauert %d Sekunden.\n", name, dauer);
		sleep(dauer);
		i+=6;
	}
}

/*
 * Die Funktion des Vaterprozesses.
 * Hier findet die deadlock_erkennung und -beseitigung statt.
 */
void deadlock_erkennung(void) {
	/*
	 * Evlt. koent ihr diese Variablen gebrauchen:
	 * enum STATUS aktueller_status_a;
	 * enum STATUS aktueller_status_b;
	 */

	while (1) {
		sleep(12);

		/**
		 * ! ! ! HIER EUREN CODE EINFÜGEN ! ! !
		 */
		if (mitarbeiter_a_status == hole_liste && mitarbeiter_b_status == hole_klausuren) {
			printf("Deadlock erkannt!\n");

			// Der Prozess B wird gecanceled, um die Liste für A freizugeben und
			// somit das Deadlock aufzulösen.
			if (pthread_cancel(mitarbeiter_b_thread) != 0) {
				printf("Der Thread für Mitarbeiter B konnte nicht beendet werden.");
				exit(EXIT_FAILURE);
			}

			pthread_join(mitarbeiter_b_thread, NULL);
			// Die Liste wird freigegeben.
			sem_post(&liste);

			// Der Thread für Mitarbeiter B kann wieder gestartet werden.
			if (pthread_create(&mitarbeiter_b_thread, NULL, &mitarbeiter_b, NULL) != 0) {
				perror("Konnte thread fuer Mitarbeiter B nicht erzeugen");
				exit(EXIT_FAILURE);
			}

			printf("Deadlock erfolgreich aufgelöst.");
		}
	}
}

/*
 * Die Funktion, die bei einem programm_abbruch ausgefuehrt wird (z.B. bei
 * CTRL-C in der Shell). Hier werden die Mitarbeiter Threads abgebrochen und
 * die Semaphoren aufgeräumt.
 */
void programm_abbruch(int sig) {
	printf("Programm wurde abgebrochen.\n");

	/* Ueberpruefe ob Threds abgebrochen werden konnten */
	if (pthread_cancel(mitarbeiter_a_thread) == -1) {
		perror("Fehler beim Beenden der Threads");
		exit(EXIT_FAILURE);
	}
	if (pthread_cancel(mitarbeiter_b_thread) == -1) {
		perror("Fehler beim Beenden der Threads");
		exit(EXIT_FAILURE);
	}

	if (sem_destroy(&klausuren) == -1) {
		perror("Fehler beim Entfernen der Semaphore klausuren");
		exit(EXIT_FAILURE);
	}
	if (sem_destroy(&liste) == -1) {
		perror("Fehler beim Entfernen der Semaphore liste");
		exit(EXIT_FAILURE);
	}


	exit(0);
}
