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


int main(void) {

}
