#ifndef AUFGABE2_H
#define AUFGABE2_H

#define LEER 0

/* Diese Header-Datei wird nicht mit ins AsSESS uebertragen. 
 * Bitte keine Aenderungen an dieser Datei vornehmen.
 * /

/* Typdefinition fuer den Staffelstab */
typedef int staffelstab_t;

/* Struktur fuer Teamrepraesentation. 
 * name: Name des Teams
 * runtime: Zeit fuer eine Runde
 * staffelstab: Staffelstab, der der Gruppe zugeordnet ist
 * hand: Aktuelle Staffelstab in der Hand des Laeufers. Zu Beginn hat der erste Laeufer des Teams
 *       den Staffelstab des Teams bereits in der Hand. 
 */
struct Team {
    char* name;
    int runtime;
    staffelstab_t staffelstab;
    staffelstab_t hand;
};



#endif

